/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Entities.Area;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Solicitante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class AreaJpaController implements Serializable {

    public AreaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Area area) throws PreexistingEntityException, Exception {
        if (area.getSolicitanteList() == null) {
            area.setSolicitanteList(new ArrayList<Solicitante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Solicitante> attachedSolicitanteList = new ArrayList<Solicitante>();
            for (Solicitante solicitanteListSolicitanteToAttach : area.getSolicitanteList()) {
                solicitanteListSolicitanteToAttach = em.getReference(solicitanteListSolicitanteToAttach.getClass(), solicitanteListSolicitanteToAttach.getSolicitanteId());
                attachedSolicitanteList.add(solicitanteListSolicitanteToAttach);
            }
            area.setSolicitanteList(attachedSolicitanteList);
            em.persist(area);
            for (Solicitante solicitanteListSolicitante : area.getSolicitanteList()) {
                Area oldIdAreaOfSolicitanteListSolicitante = solicitanteListSolicitante.getIdArea();
                solicitanteListSolicitante.setIdArea(area);
                solicitanteListSolicitante = em.merge(solicitanteListSolicitante);
                if (oldIdAreaOfSolicitanteListSolicitante != null) {
                    oldIdAreaOfSolicitanteListSolicitante.getSolicitanteList().remove(solicitanteListSolicitante);
                    oldIdAreaOfSolicitanteListSolicitante = em.merge(oldIdAreaOfSolicitanteListSolicitante);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findArea(area.getAreaId()) != null) {
                throw new PreexistingEntityException("Area " + area + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Area area) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Area persistentArea = em.find(Area.class, area.getAreaId());
            List<Solicitante> solicitanteListOld = persistentArea.getSolicitanteList();
            List<Solicitante> solicitanteListNew = area.getSolicitanteList();
            List<String> illegalOrphanMessages = null;
            for (Solicitante solicitanteListOldSolicitante : solicitanteListOld) {
                if (!solicitanteListNew.contains(solicitanteListOldSolicitante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Solicitante " + solicitanteListOldSolicitante + " since its idArea field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Solicitante> attachedSolicitanteListNew = new ArrayList<Solicitante>();
            for (Solicitante solicitanteListNewSolicitanteToAttach : solicitanteListNew) {
                solicitanteListNewSolicitanteToAttach = em.getReference(solicitanteListNewSolicitanteToAttach.getClass(), solicitanteListNewSolicitanteToAttach.getSolicitanteId());
                attachedSolicitanteListNew.add(solicitanteListNewSolicitanteToAttach);
            }
            solicitanteListNew = attachedSolicitanteListNew;
            area.setSolicitanteList(solicitanteListNew);
            area = em.merge(area);
            for (Solicitante solicitanteListNewSolicitante : solicitanteListNew) {
                if (!solicitanteListOld.contains(solicitanteListNewSolicitante)) {
                    Area oldIdAreaOfSolicitanteListNewSolicitante = solicitanteListNewSolicitante.getIdArea();
                    solicitanteListNewSolicitante.setIdArea(area);
                    solicitanteListNewSolicitante = em.merge(solicitanteListNewSolicitante);
                    if (oldIdAreaOfSolicitanteListNewSolicitante != null && !oldIdAreaOfSolicitanteListNewSolicitante.equals(area)) {
                        oldIdAreaOfSolicitanteListNewSolicitante.getSolicitanteList().remove(solicitanteListNewSolicitante);
                        oldIdAreaOfSolicitanteListNewSolicitante = em.merge(oldIdAreaOfSolicitanteListNewSolicitante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = area.getAreaId();
                if (findArea(id) == null) {
                    throw new NonexistentEntityException("The area with id " + id + " no longer exists.");
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
            Area area;
            try {
                area = em.getReference(Area.class, id);
                area.getAreaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The area with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Solicitante> solicitanteListOrphanCheck = area.getSolicitanteList();
            for (Solicitante solicitanteListOrphanCheckSolicitante : solicitanteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Area (" + area + ") cannot be destroyed since the Solicitante " + solicitanteListOrphanCheckSolicitante + " in its solicitanteList field has a non-nullable idArea field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(area);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Area> findAreaEntities() {
        return findAreaEntities(true, -1, -1);
    }

    public List<Area> findAreaEntities(int maxResults, int firstResult) {
        return findAreaEntities(false, maxResults, firstResult);
    }

    private List<Area> findAreaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Area.class));
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

    public Area findArea(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Area.class, id);
        } finally {
            em.close();
        }
    }

    public int getAreaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Area> rt = cq.from(Area.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
