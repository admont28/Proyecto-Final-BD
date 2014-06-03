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
import Entities.Requisito;
import Entities.TipoRequisito;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class TipoRequisitoJpaController implements Serializable {

    public TipoRequisitoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoRequisito tipoRequisito) throws PreexistingEntityException, Exception {
        if (tipoRequisito.getRequisitoList() == null) {
            tipoRequisito.setRequisitoList(new ArrayList<Requisito>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Requisito> attachedRequisitoList = new ArrayList<Requisito>();
            for (Requisito requisitoListRequisitoToAttach : tipoRequisito.getRequisitoList()) {
                requisitoListRequisitoToAttach = em.getReference(requisitoListRequisitoToAttach.getClass(), requisitoListRequisitoToAttach.getRequisitoId());
                attachedRequisitoList.add(requisitoListRequisitoToAttach);
            }
            tipoRequisito.setRequisitoList(attachedRequisitoList);
            em.persist(tipoRequisito);
            for (Requisito requisitoListRequisito : tipoRequisito.getRequisitoList()) {
                TipoRequisito oldIdTipoRequisitoOfRequisitoListRequisito = requisitoListRequisito.getIdTipoRequisito();
                requisitoListRequisito.setIdTipoRequisito(tipoRequisito);
                requisitoListRequisito = em.merge(requisitoListRequisito);
                if (oldIdTipoRequisitoOfRequisitoListRequisito != null) {
                    oldIdTipoRequisitoOfRequisitoListRequisito.getRequisitoList().remove(requisitoListRequisito);
                    oldIdTipoRequisitoOfRequisitoListRequisito = em.merge(oldIdTipoRequisitoOfRequisitoListRequisito);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoRequisito(tipoRequisito.getTipoRequisitoId()) != null) {
                throw new PreexistingEntityException("TipoRequisito " + tipoRequisito + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoRequisito tipoRequisito) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoRequisito persistentTipoRequisito = em.find(TipoRequisito.class, tipoRequisito.getTipoRequisitoId());
            List<Requisito> requisitoListOld = persistentTipoRequisito.getRequisitoList();
            List<Requisito> requisitoListNew = tipoRequisito.getRequisitoList();
            List<String> illegalOrphanMessages = null;
            for (Requisito requisitoListOldRequisito : requisitoListOld) {
                if (!requisitoListNew.contains(requisitoListOldRequisito)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Requisito " + requisitoListOldRequisito + " since its idTipoRequisito field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Requisito> attachedRequisitoListNew = new ArrayList<Requisito>();
            for (Requisito requisitoListNewRequisitoToAttach : requisitoListNew) {
                requisitoListNewRequisitoToAttach = em.getReference(requisitoListNewRequisitoToAttach.getClass(), requisitoListNewRequisitoToAttach.getRequisitoId());
                attachedRequisitoListNew.add(requisitoListNewRequisitoToAttach);
            }
            requisitoListNew = attachedRequisitoListNew;
            tipoRequisito.setRequisitoList(requisitoListNew);
            tipoRequisito = em.merge(tipoRequisito);
            for (Requisito requisitoListNewRequisito : requisitoListNew) {
                if (!requisitoListOld.contains(requisitoListNewRequisito)) {
                    TipoRequisito oldIdTipoRequisitoOfRequisitoListNewRequisito = requisitoListNewRequisito.getIdTipoRequisito();
                    requisitoListNewRequisito.setIdTipoRequisito(tipoRequisito);
                    requisitoListNewRequisito = em.merge(requisitoListNewRequisito);
                    if (oldIdTipoRequisitoOfRequisitoListNewRequisito != null && !oldIdTipoRequisitoOfRequisitoListNewRequisito.equals(tipoRequisito)) {
                        oldIdTipoRequisitoOfRequisitoListNewRequisito.getRequisitoList().remove(requisitoListNewRequisito);
                        oldIdTipoRequisitoOfRequisitoListNewRequisito = em.merge(oldIdTipoRequisitoOfRequisitoListNewRequisito);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tipoRequisito.getTipoRequisitoId();
                if (findTipoRequisito(id) == null) {
                    throw new NonexistentEntityException("The tipoRequisito with id " + id + " no longer exists.");
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
            TipoRequisito tipoRequisito;
            try {
                tipoRequisito = em.getReference(TipoRequisito.class, id);
                tipoRequisito.getTipoRequisitoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoRequisito with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Requisito> requisitoListOrphanCheck = tipoRequisito.getRequisitoList();
            for (Requisito requisitoListOrphanCheckRequisito : requisitoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoRequisito (" + tipoRequisito + ") cannot be destroyed since the Requisito " + requisitoListOrphanCheckRequisito + " in its requisitoList field has a non-nullable idTipoRequisito field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoRequisito);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoRequisito> findTipoRequisitoEntities() {
        return findTipoRequisitoEntities(true, -1, -1);
    }

    public List<TipoRequisito> findTipoRequisitoEntities(int maxResults, int firstResult) {
        return findTipoRequisitoEntities(false, maxResults, firstResult);
    }

    private List<TipoRequisito> findTipoRequisitoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoRequisito.class));
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

    public TipoRequisito findTipoRequisito(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoRequisito.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoRequisitoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoRequisito> rt = cq.from(TipoRequisito.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
