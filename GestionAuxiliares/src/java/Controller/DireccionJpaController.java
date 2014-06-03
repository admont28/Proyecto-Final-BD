/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Ciudad;
import Entities.Universidad;
import Entities.Solicitante;
import java.util.ArrayList;
import java.util.List;
import Entities.Auxiliar;
import Entities.Direccion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class DireccionJpaController implements Serializable {

    public DireccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Direccion direccion) throws PreexistingEntityException, Exception {
        if (direccion.getSolicitanteList() == null) {
            direccion.setSolicitanteList(new ArrayList<Solicitante>());
        }
        if (direccion.getAuxiliarList() == null) {
            direccion.setAuxiliarList(new ArrayList<Auxiliar>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad idCiudad = direccion.getIdCiudad();
            if (idCiudad != null) {
                idCiudad = em.getReference(idCiudad.getClass(), idCiudad.getCiudadId());
                direccion.setIdCiudad(idCiudad);
            }
            Universidad universidad = direccion.getUniversidad();
            if (universidad != null) {
                universidad = em.getReference(universidad.getClass(), universidad.getUniversidadId());
                direccion.setUniversidad(universidad);
            }
            List<Solicitante> attachedSolicitanteList = new ArrayList<Solicitante>();
            for (Solicitante solicitanteListSolicitanteToAttach : direccion.getSolicitanteList()) {
                solicitanteListSolicitanteToAttach = em.getReference(solicitanteListSolicitanteToAttach.getClass(), solicitanteListSolicitanteToAttach.getSolicitanteId());
                attachedSolicitanteList.add(solicitanteListSolicitanteToAttach);
            }
            direccion.setSolicitanteList(attachedSolicitanteList);
            List<Auxiliar> attachedAuxiliarList = new ArrayList<Auxiliar>();
            for (Auxiliar auxiliarListAuxiliarToAttach : direccion.getAuxiliarList()) {
                auxiliarListAuxiliarToAttach = em.getReference(auxiliarListAuxiliarToAttach.getClass(), auxiliarListAuxiliarToAttach.getAuxiliarId());
                attachedAuxiliarList.add(auxiliarListAuxiliarToAttach);
            }
            direccion.setAuxiliarList(attachedAuxiliarList);
            em.persist(direccion);
            if (idCiudad != null) {
                idCiudad.getDireccionList().add(direccion);
                idCiudad = em.merge(idCiudad);
            }
            if (universidad != null) {
                Direccion oldIdDireccionOfUniversidad = universidad.getIdDireccion();
                if (oldIdDireccionOfUniversidad != null) {
                    oldIdDireccionOfUniversidad.setUniversidad(null);
                    oldIdDireccionOfUniversidad = em.merge(oldIdDireccionOfUniversidad);
                }
                universidad.setIdDireccion(direccion);
                universidad = em.merge(universidad);
            }
            for (Solicitante solicitanteListSolicitante : direccion.getSolicitanteList()) {
                Direccion oldIdDireccionOfSolicitanteListSolicitante = solicitanteListSolicitante.getIdDireccion();
                solicitanteListSolicitante.setIdDireccion(direccion);
                solicitanteListSolicitante = em.merge(solicitanteListSolicitante);
                if (oldIdDireccionOfSolicitanteListSolicitante != null) {
                    oldIdDireccionOfSolicitanteListSolicitante.getSolicitanteList().remove(solicitanteListSolicitante);
                    oldIdDireccionOfSolicitanteListSolicitante = em.merge(oldIdDireccionOfSolicitanteListSolicitante);
                }
            }
            for (Auxiliar auxiliarListAuxiliar : direccion.getAuxiliarList()) {
                Direccion oldIdDireccionOfAuxiliarListAuxiliar = auxiliarListAuxiliar.getIdDireccion();
                auxiliarListAuxiliar.setIdDireccion(direccion);
                auxiliarListAuxiliar = em.merge(auxiliarListAuxiliar);
                if (oldIdDireccionOfAuxiliarListAuxiliar != null) {
                    oldIdDireccionOfAuxiliarListAuxiliar.getAuxiliarList().remove(auxiliarListAuxiliar);
                    oldIdDireccionOfAuxiliarListAuxiliar = em.merge(oldIdDireccionOfAuxiliarListAuxiliar);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDireccion(direccion.getDireccionId()) != null) {
                throw new PreexistingEntityException("Direccion " + direccion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Direccion direccion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Direccion persistentDireccion = em.find(Direccion.class, direccion.getDireccionId());
            Ciudad idCiudadOld = persistentDireccion.getIdCiudad();
            Ciudad idCiudadNew = direccion.getIdCiudad();
            Universidad universidadOld = persistentDireccion.getUniversidad();
            Universidad universidadNew = direccion.getUniversidad();
            List<Solicitante> solicitanteListOld = persistentDireccion.getSolicitanteList();
            List<Solicitante> solicitanteListNew = direccion.getSolicitanteList();
            List<Auxiliar> auxiliarListOld = persistentDireccion.getAuxiliarList();
            List<Auxiliar> auxiliarListNew = direccion.getAuxiliarList();
            List<String> illegalOrphanMessages = null;
            if (universidadOld != null && !universidadOld.equals(universidadNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Universidad " + universidadOld + " since its idDireccion field is not nullable.");
            }
            for (Solicitante solicitanteListOldSolicitante : solicitanteListOld) {
                if (!solicitanteListNew.contains(solicitanteListOldSolicitante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Solicitante " + solicitanteListOldSolicitante + " since its idDireccion field is not nullable.");
                }
            }
            for (Auxiliar auxiliarListOldAuxiliar : auxiliarListOld) {
                if (!auxiliarListNew.contains(auxiliarListOldAuxiliar)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Auxiliar " + auxiliarListOldAuxiliar + " since its idDireccion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idCiudadNew != null) {
                idCiudadNew = em.getReference(idCiudadNew.getClass(), idCiudadNew.getCiudadId());
                direccion.setIdCiudad(idCiudadNew);
            }
            if (universidadNew != null) {
                universidadNew = em.getReference(universidadNew.getClass(), universidadNew.getUniversidadId());
                direccion.setUniversidad(universidadNew);
            }
            List<Solicitante> attachedSolicitanteListNew = new ArrayList<Solicitante>();
            for (Solicitante solicitanteListNewSolicitanteToAttach : solicitanteListNew) {
                solicitanteListNewSolicitanteToAttach = em.getReference(solicitanteListNewSolicitanteToAttach.getClass(), solicitanteListNewSolicitanteToAttach.getSolicitanteId());
                attachedSolicitanteListNew.add(solicitanteListNewSolicitanteToAttach);
            }
            solicitanteListNew = attachedSolicitanteListNew;
            direccion.setSolicitanteList(solicitanteListNew);
            List<Auxiliar> attachedAuxiliarListNew = new ArrayList<Auxiliar>();
            for (Auxiliar auxiliarListNewAuxiliarToAttach : auxiliarListNew) {
                auxiliarListNewAuxiliarToAttach = em.getReference(auxiliarListNewAuxiliarToAttach.getClass(), auxiliarListNewAuxiliarToAttach.getAuxiliarId());
                attachedAuxiliarListNew.add(auxiliarListNewAuxiliarToAttach);
            }
            auxiliarListNew = attachedAuxiliarListNew;
            direccion.setAuxiliarList(auxiliarListNew);
            direccion = em.merge(direccion);
            if (idCiudadOld != null && !idCiudadOld.equals(idCiudadNew)) {
                idCiudadOld.getDireccionList().remove(direccion);
                idCiudadOld = em.merge(idCiudadOld);
            }
            if (idCiudadNew != null && !idCiudadNew.equals(idCiudadOld)) {
                idCiudadNew.getDireccionList().add(direccion);
                idCiudadNew = em.merge(idCiudadNew);
            }
            if (universidadNew != null && !universidadNew.equals(universidadOld)) {
                Direccion oldIdDireccionOfUniversidad = universidadNew.getIdDireccion();
                if (oldIdDireccionOfUniversidad != null) {
                    oldIdDireccionOfUniversidad.setUniversidad(null);
                    oldIdDireccionOfUniversidad = em.merge(oldIdDireccionOfUniversidad);
                }
                universidadNew.setIdDireccion(direccion);
                universidadNew = em.merge(universidadNew);
            }
            for (Solicitante solicitanteListNewSolicitante : solicitanteListNew) {
                if (!solicitanteListOld.contains(solicitanteListNewSolicitante)) {
                    Direccion oldIdDireccionOfSolicitanteListNewSolicitante = solicitanteListNewSolicitante.getIdDireccion();
                    solicitanteListNewSolicitante.setIdDireccion(direccion);
                    solicitanteListNewSolicitante = em.merge(solicitanteListNewSolicitante);
                    if (oldIdDireccionOfSolicitanteListNewSolicitante != null && !oldIdDireccionOfSolicitanteListNewSolicitante.equals(direccion)) {
                        oldIdDireccionOfSolicitanteListNewSolicitante.getSolicitanteList().remove(solicitanteListNewSolicitante);
                        oldIdDireccionOfSolicitanteListNewSolicitante = em.merge(oldIdDireccionOfSolicitanteListNewSolicitante);
                    }
                }
            }
            for (Auxiliar auxiliarListNewAuxiliar : auxiliarListNew) {
                if (!auxiliarListOld.contains(auxiliarListNewAuxiliar)) {
                    Direccion oldIdDireccionOfAuxiliarListNewAuxiliar = auxiliarListNewAuxiliar.getIdDireccion();
                    auxiliarListNewAuxiliar.setIdDireccion(direccion);
                    auxiliarListNewAuxiliar = em.merge(auxiliarListNewAuxiliar);
                    if (oldIdDireccionOfAuxiliarListNewAuxiliar != null && !oldIdDireccionOfAuxiliarListNewAuxiliar.equals(direccion)) {
                        oldIdDireccionOfAuxiliarListNewAuxiliar.getAuxiliarList().remove(auxiliarListNewAuxiliar);
                        oldIdDireccionOfAuxiliarListNewAuxiliar = em.merge(oldIdDireccionOfAuxiliarListNewAuxiliar);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = direccion.getDireccionId();
                if (findDireccion(id) == null) {
                    throw new NonexistentEntityException("The direccion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Direccion direccion;
            try {
                direccion = em.getReference(Direccion.class, id);
                direccion.getDireccionId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The direccion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Universidad universidadOrphanCheck = direccion.getUniversidad();
            if (universidadOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Direccion (" + direccion + ") cannot be destroyed since the Universidad " + universidadOrphanCheck + " in its universidad field has a non-nullable idDireccion field.");
            }
            List<Solicitante> solicitanteListOrphanCheck = direccion.getSolicitanteList();
            for (Solicitante solicitanteListOrphanCheckSolicitante : solicitanteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Direccion (" + direccion + ") cannot be destroyed since the Solicitante " + solicitanteListOrphanCheckSolicitante + " in its solicitanteList field has a non-nullable idDireccion field.");
            }
            List<Auxiliar> auxiliarListOrphanCheck = direccion.getAuxiliarList();
            for (Auxiliar auxiliarListOrphanCheckAuxiliar : auxiliarListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Direccion (" + direccion + ") cannot be destroyed since the Auxiliar " + auxiliarListOrphanCheckAuxiliar + " in its auxiliarList field has a non-nullable idDireccion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Ciudad idCiudad = direccion.getIdCiudad();
            if (idCiudad != null) {
                idCiudad.getDireccionList().remove(direccion);
                idCiudad = em.merge(idCiudad);
            }
            em.remove(direccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Direccion> findDireccionEntities() {
        return findDireccionEntities(true, -1, -1);
    }

    public List<Direccion> findDireccionEntities(int maxResults, int firstResult) {
        return findDireccionEntities(false, maxResults, firstResult);
    }

    private List<Direccion> findDireccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Direccion.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Direccion findDireccion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Direccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getDireccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Direccion> rt = cq.from(Direccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
