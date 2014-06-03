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
import Entities.IncAuxAuxiliar;
import Entities.InconformidadAuxiliares;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class InconformidadAuxiliaresJpaController implements Serializable {

    public InconformidadAuxiliaresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InconformidadAuxiliares inconformidadAuxiliares) throws PreexistingEntityException, Exception {
        if (inconformidadAuxiliares.getIncAuxAuxiliarList() == null) {
            inconformidadAuxiliares.setIncAuxAuxiliarList(new ArrayList<IncAuxAuxiliar>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<IncAuxAuxiliar> attachedIncAuxAuxiliarList = new ArrayList<IncAuxAuxiliar>();
            for (IncAuxAuxiliar incAuxAuxiliarListIncAuxAuxiliarToAttach : inconformidadAuxiliares.getIncAuxAuxiliarList()) {
                incAuxAuxiliarListIncAuxAuxiliarToAttach = em.getReference(incAuxAuxiliarListIncAuxAuxiliarToAttach.getClass(), incAuxAuxiliarListIncAuxAuxiliarToAttach.getIncAuxAuxiliarPK());
                attachedIncAuxAuxiliarList.add(incAuxAuxiliarListIncAuxAuxiliarToAttach);
            }
            inconformidadAuxiliares.setIncAuxAuxiliarList(attachedIncAuxAuxiliarList);
            em.persist(inconformidadAuxiliares);
            for (IncAuxAuxiliar incAuxAuxiliarListIncAuxAuxiliar : inconformidadAuxiliares.getIncAuxAuxiliarList()) {
                InconformidadAuxiliares oldInconformidadAuxiliaresOfIncAuxAuxiliarListIncAuxAuxiliar = incAuxAuxiliarListIncAuxAuxiliar.getInconformidadAuxiliares();
                incAuxAuxiliarListIncAuxAuxiliar.setInconformidadAuxiliares(inconformidadAuxiliares);
                incAuxAuxiliarListIncAuxAuxiliar = em.merge(incAuxAuxiliarListIncAuxAuxiliar);
                if (oldInconformidadAuxiliaresOfIncAuxAuxiliarListIncAuxAuxiliar != null) {
                    oldInconformidadAuxiliaresOfIncAuxAuxiliarListIncAuxAuxiliar.getIncAuxAuxiliarList().remove(incAuxAuxiliarListIncAuxAuxiliar);
                    oldInconformidadAuxiliaresOfIncAuxAuxiliarListIncAuxAuxiliar = em.merge(oldInconformidadAuxiliaresOfIncAuxAuxiliarListIncAuxAuxiliar);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInconformidadAuxiliares(inconformidadAuxiliares.getInconformidadAuxiliaresId()) != null) {
                throw new PreexistingEntityException("InconformidadAuxiliares " + inconformidadAuxiliares + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InconformidadAuxiliares inconformidadAuxiliares) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InconformidadAuxiliares persistentInconformidadAuxiliares = em.find(InconformidadAuxiliares.class, inconformidadAuxiliares.getInconformidadAuxiliaresId());
            List<IncAuxAuxiliar> incAuxAuxiliarListOld = persistentInconformidadAuxiliares.getIncAuxAuxiliarList();
            List<IncAuxAuxiliar> incAuxAuxiliarListNew = inconformidadAuxiliares.getIncAuxAuxiliarList();
            List<String> illegalOrphanMessages = null;
            for (IncAuxAuxiliar incAuxAuxiliarListOldIncAuxAuxiliar : incAuxAuxiliarListOld) {
                if (!incAuxAuxiliarListNew.contains(incAuxAuxiliarListOldIncAuxAuxiliar)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain IncAuxAuxiliar " + incAuxAuxiliarListOldIncAuxAuxiliar + " since its inconformidadAuxiliares field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<IncAuxAuxiliar> attachedIncAuxAuxiliarListNew = new ArrayList<IncAuxAuxiliar>();
            for (IncAuxAuxiliar incAuxAuxiliarListNewIncAuxAuxiliarToAttach : incAuxAuxiliarListNew) {
                incAuxAuxiliarListNewIncAuxAuxiliarToAttach = em.getReference(incAuxAuxiliarListNewIncAuxAuxiliarToAttach.getClass(), incAuxAuxiliarListNewIncAuxAuxiliarToAttach.getIncAuxAuxiliarPK());
                attachedIncAuxAuxiliarListNew.add(incAuxAuxiliarListNewIncAuxAuxiliarToAttach);
            }
            incAuxAuxiliarListNew = attachedIncAuxAuxiliarListNew;
            inconformidadAuxiliares.setIncAuxAuxiliarList(incAuxAuxiliarListNew);
            inconformidadAuxiliares = em.merge(inconformidadAuxiliares);
            for (IncAuxAuxiliar incAuxAuxiliarListNewIncAuxAuxiliar : incAuxAuxiliarListNew) {
                if (!incAuxAuxiliarListOld.contains(incAuxAuxiliarListNewIncAuxAuxiliar)) {
                    InconformidadAuxiliares oldInconformidadAuxiliaresOfIncAuxAuxiliarListNewIncAuxAuxiliar = incAuxAuxiliarListNewIncAuxAuxiliar.getInconformidadAuxiliares();
                    incAuxAuxiliarListNewIncAuxAuxiliar.setInconformidadAuxiliares(inconformidadAuxiliares);
                    incAuxAuxiliarListNewIncAuxAuxiliar = em.merge(incAuxAuxiliarListNewIncAuxAuxiliar);
                    if (oldInconformidadAuxiliaresOfIncAuxAuxiliarListNewIncAuxAuxiliar != null && !oldInconformidadAuxiliaresOfIncAuxAuxiliarListNewIncAuxAuxiliar.equals(inconformidadAuxiliares)) {
                        oldInconformidadAuxiliaresOfIncAuxAuxiliarListNewIncAuxAuxiliar.getIncAuxAuxiliarList().remove(incAuxAuxiliarListNewIncAuxAuxiliar);
                        oldInconformidadAuxiliaresOfIncAuxAuxiliarListNewIncAuxAuxiliar = em.merge(oldInconformidadAuxiliaresOfIncAuxAuxiliarListNewIncAuxAuxiliar);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = inconformidadAuxiliares.getInconformidadAuxiliaresId();
                if (findInconformidadAuxiliares(id) == null) {
                    throw new NonexistentEntityException("The inconformidadAuxiliares with id " + id + " no longer exists.");
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
            InconformidadAuxiliares inconformidadAuxiliares;
            try {
                inconformidadAuxiliares = em.getReference(InconformidadAuxiliares.class, id);
                inconformidadAuxiliares.getInconformidadAuxiliaresId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inconformidadAuxiliares with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<IncAuxAuxiliar> incAuxAuxiliarListOrphanCheck = inconformidadAuxiliares.getIncAuxAuxiliarList();
            for (IncAuxAuxiliar incAuxAuxiliarListOrphanCheckIncAuxAuxiliar : incAuxAuxiliarListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This InconformidadAuxiliares (" + inconformidadAuxiliares + ") cannot be destroyed since the IncAuxAuxiliar " + incAuxAuxiliarListOrphanCheckIncAuxAuxiliar + " in its incAuxAuxiliarList field has a non-nullable inconformidadAuxiliares field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(inconformidadAuxiliares);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InconformidadAuxiliares> findInconformidadAuxiliaresEntities() {
        return findInconformidadAuxiliaresEntities(true, -1, -1);
    }

    public List<InconformidadAuxiliares> findInconformidadAuxiliaresEntities(int maxResults, int firstResult) {
        return findInconformidadAuxiliaresEntities(false, maxResults, firstResult);
    }

    private List<InconformidadAuxiliares> findInconformidadAuxiliaresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InconformidadAuxiliares.class));
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

    public InconformidadAuxiliares findInconformidadAuxiliares(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InconformidadAuxiliares.class, id);
        } finally {
            em.close();
        }
    }

    public int getInconformidadAuxiliaresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InconformidadAuxiliares> rt = cq.from(InconformidadAuxiliares.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
