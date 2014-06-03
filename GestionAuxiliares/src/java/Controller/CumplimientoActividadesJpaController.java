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
import Entities.Solicitante;
import Entities.AuxCumplimientoActividades;
import Entities.CumplimientoActividades;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class CumplimientoActividadesJpaController implements Serializable {

    public CumplimientoActividadesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CumplimientoActividades cumplimientoActividades) throws PreexistingEntityException, Exception {
        if (cumplimientoActividades.getAuxCumplimientoActividadesList() == null) {
            cumplimientoActividades.setAuxCumplimientoActividadesList(new ArrayList<AuxCumplimientoActividades>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Solicitante idSolicitante = cumplimientoActividades.getIdSolicitante();
            if (idSolicitante != null) {
                idSolicitante = em.getReference(idSolicitante.getClass(), idSolicitante.getSolicitanteId());
                cumplimientoActividades.setIdSolicitante(idSolicitante);
            }
            List<AuxCumplimientoActividades> attachedAuxCumplimientoActividadesList = new ArrayList<AuxCumplimientoActividades>();
            for (AuxCumplimientoActividades auxCumplimientoActividadesListAuxCumplimientoActividadesToAttach : cumplimientoActividades.getAuxCumplimientoActividadesList()) {
                auxCumplimientoActividadesListAuxCumplimientoActividadesToAttach = em.getReference(auxCumplimientoActividadesListAuxCumplimientoActividadesToAttach.getClass(), auxCumplimientoActividadesListAuxCumplimientoActividadesToAttach.getAuxCumplimientoActividadesPK());
                attachedAuxCumplimientoActividadesList.add(auxCumplimientoActividadesListAuxCumplimientoActividadesToAttach);
            }
            cumplimientoActividades.setAuxCumplimientoActividadesList(attachedAuxCumplimientoActividadesList);
            em.persist(cumplimientoActividades);
            if (idSolicitante != null) {
                idSolicitante.getCumplimientoActividadesList().add(cumplimientoActividades);
                idSolicitante = em.merge(idSolicitante);
            }
            for (AuxCumplimientoActividades auxCumplimientoActividadesListAuxCumplimientoActividades : cumplimientoActividades.getAuxCumplimientoActividadesList()) {
                CumplimientoActividades oldCumplimientoActividadesOfAuxCumplimientoActividadesListAuxCumplimientoActividades = auxCumplimientoActividadesListAuxCumplimientoActividades.getCumplimientoActividades();
                auxCumplimientoActividadesListAuxCumplimientoActividades.setCumplimientoActividades(cumplimientoActividades);
                auxCumplimientoActividadesListAuxCumplimientoActividades = em.merge(auxCumplimientoActividadesListAuxCumplimientoActividades);
                if (oldCumplimientoActividadesOfAuxCumplimientoActividadesListAuxCumplimientoActividades != null) {
                    oldCumplimientoActividadesOfAuxCumplimientoActividadesListAuxCumplimientoActividades.getAuxCumplimientoActividadesList().remove(auxCumplimientoActividadesListAuxCumplimientoActividades);
                    oldCumplimientoActividadesOfAuxCumplimientoActividadesListAuxCumplimientoActividades = em.merge(oldCumplimientoActividadesOfAuxCumplimientoActividadesListAuxCumplimientoActividades);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCumplimientoActividades(cumplimientoActividades.getCumpActId()) != null) {
                throw new PreexistingEntityException("CumplimientoActividades " + cumplimientoActividades + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CumplimientoActividades cumplimientoActividades) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CumplimientoActividades persistentCumplimientoActividades = em.find(CumplimientoActividades.class, cumplimientoActividades.getCumpActId());
            Solicitante idSolicitanteOld = persistentCumplimientoActividades.getIdSolicitante();
            Solicitante idSolicitanteNew = cumplimientoActividades.getIdSolicitante();
            List<AuxCumplimientoActividades> auxCumplimientoActividadesListOld = persistentCumplimientoActividades.getAuxCumplimientoActividadesList();
            List<AuxCumplimientoActividades> auxCumplimientoActividadesListNew = cumplimientoActividades.getAuxCumplimientoActividadesList();
            List<String> illegalOrphanMessages = null;
            for (AuxCumplimientoActividades auxCumplimientoActividadesListOldAuxCumplimientoActividades : auxCumplimientoActividadesListOld) {
                if (!auxCumplimientoActividadesListNew.contains(auxCumplimientoActividadesListOldAuxCumplimientoActividades)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AuxCumplimientoActividades " + auxCumplimientoActividadesListOldAuxCumplimientoActividades + " since its cumplimientoActividades field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idSolicitanteNew != null) {
                idSolicitanteNew = em.getReference(idSolicitanteNew.getClass(), idSolicitanteNew.getSolicitanteId());
                cumplimientoActividades.setIdSolicitante(idSolicitanteNew);
            }
            List<AuxCumplimientoActividades> attachedAuxCumplimientoActividadesListNew = new ArrayList<AuxCumplimientoActividades>();
            for (AuxCumplimientoActividades auxCumplimientoActividadesListNewAuxCumplimientoActividadesToAttach : auxCumplimientoActividadesListNew) {
                auxCumplimientoActividadesListNewAuxCumplimientoActividadesToAttach = em.getReference(auxCumplimientoActividadesListNewAuxCumplimientoActividadesToAttach.getClass(), auxCumplimientoActividadesListNewAuxCumplimientoActividadesToAttach.getAuxCumplimientoActividadesPK());
                attachedAuxCumplimientoActividadesListNew.add(auxCumplimientoActividadesListNewAuxCumplimientoActividadesToAttach);
            }
            auxCumplimientoActividadesListNew = attachedAuxCumplimientoActividadesListNew;
            cumplimientoActividades.setAuxCumplimientoActividadesList(auxCumplimientoActividadesListNew);
            cumplimientoActividades = em.merge(cumplimientoActividades);
            if (idSolicitanteOld != null && !idSolicitanteOld.equals(idSolicitanteNew)) {
                idSolicitanteOld.getCumplimientoActividadesList().remove(cumplimientoActividades);
                idSolicitanteOld = em.merge(idSolicitanteOld);
            }
            if (idSolicitanteNew != null && !idSolicitanteNew.equals(idSolicitanteOld)) {
                idSolicitanteNew.getCumplimientoActividadesList().add(cumplimientoActividades);
                idSolicitanteNew = em.merge(idSolicitanteNew);
            }
            for (AuxCumplimientoActividades auxCumplimientoActividadesListNewAuxCumplimientoActividades : auxCumplimientoActividadesListNew) {
                if (!auxCumplimientoActividadesListOld.contains(auxCumplimientoActividadesListNewAuxCumplimientoActividades)) {
                    CumplimientoActividades oldCumplimientoActividadesOfAuxCumplimientoActividadesListNewAuxCumplimientoActividades = auxCumplimientoActividadesListNewAuxCumplimientoActividades.getCumplimientoActividades();
                    auxCumplimientoActividadesListNewAuxCumplimientoActividades.setCumplimientoActividades(cumplimientoActividades);
                    auxCumplimientoActividadesListNewAuxCumplimientoActividades = em.merge(auxCumplimientoActividadesListNewAuxCumplimientoActividades);
                    if (oldCumplimientoActividadesOfAuxCumplimientoActividadesListNewAuxCumplimientoActividades != null && !oldCumplimientoActividadesOfAuxCumplimientoActividadesListNewAuxCumplimientoActividades.equals(cumplimientoActividades)) {
                        oldCumplimientoActividadesOfAuxCumplimientoActividadesListNewAuxCumplimientoActividades.getAuxCumplimientoActividadesList().remove(auxCumplimientoActividadesListNewAuxCumplimientoActividades);
                        oldCumplimientoActividadesOfAuxCumplimientoActividadesListNewAuxCumplimientoActividades = em.merge(oldCumplimientoActividadesOfAuxCumplimientoActividadesListNewAuxCumplimientoActividades);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = cumplimientoActividades.getCumpActId();
                if (findCumplimientoActividades(id) == null) {
                    throw new NonexistentEntityException("The cumplimientoActividades with id " + id + " no longer exists.");
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
            CumplimientoActividades cumplimientoActividades;
            try {
                cumplimientoActividades = em.getReference(CumplimientoActividades.class, id);
                cumplimientoActividades.getCumpActId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cumplimientoActividades with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<AuxCumplimientoActividades> auxCumplimientoActividadesListOrphanCheck = cumplimientoActividades.getAuxCumplimientoActividadesList();
            for (AuxCumplimientoActividades auxCumplimientoActividadesListOrphanCheckAuxCumplimientoActividades : auxCumplimientoActividadesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CumplimientoActividades (" + cumplimientoActividades + ") cannot be destroyed since the AuxCumplimientoActividades " + auxCumplimientoActividadesListOrphanCheckAuxCumplimientoActividades + " in its auxCumplimientoActividadesList field has a non-nullable cumplimientoActividades field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Solicitante idSolicitante = cumplimientoActividades.getIdSolicitante();
            if (idSolicitante != null) {
                idSolicitante.getCumplimientoActividadesList().remove(cumplimientoActividades);
                idSolicitante = em.merge(idSolicitante);
            }
            em.remove(cumplimientoActividades);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CumplimientoActividades> findCumplimientoActividadesEntities() {
        return findCumplimientoActividadesEntities(true, -1, -1);
    }

    public List<CumplimientoActividades> findCumplimientoActividadesEntities(int maxResults, int firstResult) {
        return findCumplimientoActividadesEntities(false, maxResults, firstResult);
    }

    private List<CumplimientoActividades> findCumplimientoActividadesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CumplimientoActividades.class));
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

    public CumplimientoActividades findCumplimientoActividades(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CumplimientoActividades.class, id);
        } finally {
            em.close();
        }
    }

    public int getCumplimientoActividadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CumplimientoActividades> rt = cq.from(CumplimientoActividades.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
