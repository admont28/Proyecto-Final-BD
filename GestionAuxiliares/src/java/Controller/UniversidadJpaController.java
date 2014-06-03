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
import Entities.Direccion;
import Entities.Facultad;
import Entities.Universidad;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class UniversidadJpaController implements Serializable {

    public UniversidadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Universidad universidad) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (universidad.getFacultadList() == null) {
            universidad.setFacultadList(new ArrayList<Facultad>());
        }
        List<String> illegalOrphanMessages = null;
        Direccion idDireccionOrphanCheck = universidad.getIdDireccion();
        if (idDireccionOrphanCheck != null) {
            Universidad oldUniversidadOfIdDireccion = idDireccionOrphanCheck.getUniversidad();
            if (oldUniversidadOfIdDireccion != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Direccion " + idDireccionOrphanCheck + " already has an item of type Universidad whose idDireccion column cannot be null. Please make another selection for the idDireccion field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Direccion idDireccion = universidad.getIdDireccion();
            if (idDireccion != null) {
                idDireccion = em.getReference(idDireccion.getClass(), idDireccion.getDireccionId());
                universidad.setIdDireccion(idDireccion);
            }
            List<Facultad> attachedFacultadList = new ArrayList<Facultad>();
            for (Facultad facultadListFacultadToAttach : universidad.getFacultadList()) {
                facultadListFacultadToAttach = em.getReference(facultadListFacultadToAttach.getClass(), facultadListFacultadToAttach.getFacultadId());
                attachedFacultadList.add(facultadListFacultadToAttach);
            }
            universidad.setFacultadList(attachedFacultadList);
            em.persist(universidad);
            if (idDireccion != null) {
                idDireccion.setUniversidad(universidad);
                idDireccion = em.merge(idDireccion);
            }
            for (Facultad facultadListFacultad : universidad.getFacultadList()) {
                Universidad oldIdUniversidadOfFacultadListFacultad = facultadListFacultad.getIdUniversidad();
                facultadListFacultad.setIdUniversidad(universidad);
                facultadListFacultad = em.merge(facultadListFacultad);
                if (oldIdUniversidadOfFacultadListFacultad != null) {
                    oldIdUniversidadOfFacultadListFacultad.getFacultadList().remove(facultadListFacultad);
                    oldIdUniversidadOfFacultadListFacultad = em.merge(oldIdUniversidadOfFacultadListFacultad);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUniversidad(universidad.getUniversidadId()) != null) {
                throw new PreexistingEntityException("Universidad " + universidad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Universidad universidad) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Universidad persistentUniversidad = em.find(Universidad.class, universidad.getUniversidadId());
            Direccion idDireccionOld = persistentUniversidad.getIdDireccion();
            Direccion idDireccionNew = universidad.getIdDireccion();
            List<Facultad> facultadListOld = persistentUniversidad.getFacultadList();
            List<Facultad> facultadListNew = universidad.getFacultadList();
            List<String> illegalOrphanMessages = null;
            if (idDireccionNew != null && !idDireccionNew.equals(idDireccionOld)) {
                Universidad oldUniversidadOfIdDireccion = idDireccionNew.getUniversidad();
                if (oldUniversidadOfIdDireccion != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Direccion " + idDireccionNew + " already has an item of type Universidad whose idDireccion column cannot be null. Please make another selection for the idDireccion field.");
                }
            }
            for (Facultad facultadListOldFacultad : facultadListOld) {
                if (!facultadListNew.contains(facultadListOldFacultad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Facultad " + facultadListOldFacultad + " since its idUniversidad field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idDireccionNew != null) {
                idDireccionNew = em.getReference(idDireccionNew.getClass(), idDireccionNew.getDireccionId());
                universidad.setIdDireccion(idDireccionNew);
            }
            List<Facultad> attachedFacultadListNew = new ArrayList<Facultad>();
            for (Facultad facultadListNewFacultadToAttach : facultadListNew) {
                facultadListNewFacultadToAttach = em.getReference(facultadListNewFacultadToAttach.getClass(), facultadListNewFacultadToAttach.getFacultadId());
                attachedFacultadListNew.add(facultadListNewFacultadToAttach);
            }
            facultadListNew = attachedFacultadListNew;
            universidad.setFacultadList(facultadListNew);
            universidad = em.merge(universidad);
            if (idDireccionOld != null && !idDireccionOld.equals(idDireccionNew)) {
                idDireccionOld.setUniversidad(null);
                idDireccionOld = em.merge(idDireccionOld);
            }
            if (idDireccionNew != null && !idDireccionNew.equals(idDireccionOld)) {
                idDireccionNew.setUniversidad(universidad);
                idDireccionNew = em.merge(idDireccionNew);
            }
            for (Facultad facultadListNewFacultad : facultadListNew) {
                if (!facultadListOld.contains(facultadListNewFacultad)) {
                    Universidad oldIdUniversidadOfFacultadListNewFacultad = facultadListNewFacultad.getIdUniversidad();
                    facultadListNewFacultad.setIdUniversidad(universidad);
                    facultadListNewFacultad = em.merge(facultadListNewFacultad);
                    if (oldIdUniversidadOfFacultadListNewFacultad != null && !oldIdUniversidadOfFacultadListNewFacultad.equals(universidad)) {
                        oldIdUniversidadOfFacultadListNewFacultad.getFacultadList().remove(facultadListNewFacultad);
                        oldIdUniversidadOfFacultadListNewFacultad = em.merge(oldIdUniversidadOfFacultadListNewFacultad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = universidad.getUniversidadId();
                if (findUniversidad(id) == null) {
                    throw new NonexistentEntityException("The universidad with id " + id + " no longer exists.");
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
            Universidad universidad;
            try {
                universidad = em.getReference(Universidad.class, id);
                universidad.getUniversidadId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The universidad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Facultad> facultadListOrphanCheck = universidad.getFacultadList();
            for (Facultad facultadListOrphanCheckFacultad : facultadListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Universidad (" + universidad + ") cannot be destroyed since the Facultad " + facultadListOrphanCheckFacultad + " in its facultadList field has a non-nullable idUniversidad field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Direccion idDireccion = universidad.getIdDireccion();
            if (idDireccion != null) {
                idDireccion.setUniversidad(null);
                idDireccion = em.merge(idDireccion);
            }
            em.remove(universidad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Universidad> findUniversidadEntities() {
        return findUniversidadEntities(true, -1, -1);
    }

    public List<Universidad> findUniversidadEntities(int maxResults, int firstResult) {
        return findUniversidadEntities(false, maxResults, firstResult);
    }

    private List<Universidad> findUniversidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Universidad.class));
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

    public Universidad findUniversidad(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Universidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getUniversidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Universidad> rt = cq.from(Universidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
