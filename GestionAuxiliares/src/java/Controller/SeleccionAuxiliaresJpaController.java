/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.EvaluacionAuxiliares;
import Entities.Auxiliar;
import Entities.SeleccionAuxiliares;
import Entities.SeleccionAuxiliaresPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class SeleccionAuxiliaresJpaController implements Serializable {

    public SeleccionAuxiliaresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SeleccionAuxiliares seleccionAuxiliares) throws PreexistingEntityException, Exception {
        if (seleccionAuxiliares.getSeleccionAuxiliaresPK() == null) {
            seleccionAuxiliares.setSeleccionAuxiliaresPK(new SeleccionAuxiliaresPK());
        }
        seleccionAuxiliares.getSeleccionAuxiliaresPK().setIdAuxiliares(seleccionAuxiliares.getAuxiliar().getAuxiliarId());
        seleccionAuxiliares.getSeleccionAuxiliaresPK().setIdEvaluacionAuxiliares(seleccionAuxiliares.getEvaluacionAuxiliares().getEvaluacionAuxiliaresId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EvaluacionAuxiliares evaluacionAuxiliares = seleccionAuxiliares.getEvaluacionAuxiliares();
            if (evaluacionAuxiliares != null) {
                evaluacionAuxiliares = em.getReference(evaluacionAuxiliares.getClass(), evaluacionAuxiliares.getEvaluacionAuxiliaresId());
                seleccionAuxiliares.setEvaluacionAuxiliares(evaluacionAuxiliares);
            }
            Auxiliar auxiliar = seleccionAuxiliares.getAuxiliar();
            if (auxiliar != null) {
                auxiliar = em.getReference(auxiliar.getClass(), auxiliar.getAuxiliarId());
                seleccionAuxiliares.setAuxiliar(auxiliar);
            }
            em.persist(seleccionAuxiliares);
            if (evaluacionAuxiliares != null) {
                evaluacionAuxiliares.getSeleccionAuxiliaresList().add(seleccionAuxiliares);
                evaluacionAuxiliares = em.merge(evaluacionAuxiliares);
            }
            if (auxiliar != null) {
                auxiliar.getSeleccionAuxiliaresList().add(seleccionAuxiliares);
                auxiliar = em.merge(auxiliar);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSeleccionAuxiliares(seleccionAuxiliares.getSeleccionAuxiliaresPK()) != null) {
                throw new PreexistingEntityException("SeleccionAuxiliares " + seleccionAuxiliares + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SeleccionAuxiliares seleccionAuxiliares) throws NonexistentEntityException, Exception {
        seleccionAuxiliares.getSeleccionAuxiliaresPK().setIdAuxiliares(seleccionAuxiliares.getAuxiliar().getAuxiliarId());
        seleccionAuxiliares.getSeleccionAuxiliaresPK().setIdEvaluacionAuxiliares(seleccionAuxiliares.getEvaluacionAuxiliares().getEvaluacionAuxiliaresId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SeleccionAuxiliares persistentSeleccionAuxiliares = em.find(SeleccionAuxiliares.class, seleccionAuxiliares.getSeleccionAuxiliaresPK());
            EvaluacionAuxiliares evaluacionAuxiliaresOld = persistentSeleccionAuxiliares.getEvaluacionAuxiliares();
            EvaluacionAuxiliares evaluacionAuxiliaresNew = seleccionAuxiliares.getEvaluacionAuxiliares();
            Auxiliar auxiliarOld = persistentSeleccionAuxiliares.getAuxiliar();
            Auxiliar auxiliarNew = seleccionAuxiliares.getAuxiliar();
            if (evaluacionAuxiliaresNew != null) {
                evaluacionAuxiliaresNew = em.getReference(evaluacionAuxiliaresNew.getClass(), evaluacionAuxiliaresNew.getEvaluacionAuxiliaresId());
                seleccionAuxiliares.setEvaluacionAuxiliares(evaluacionAuxiliaresNew);
            }
            if (auxiliarNew != null) {
                auxiliarNew = em.getReference(auxiliarNew.getClass(), auxiliarNew.getAuxiliarId());
                seleccionAuxiliares.setAuxiliar(auxiliarNew);
            }
            seleccionAuxiliares = em.merge(seleccionAuxiliares);
            if (evaluacionAuxiliaresOld != null && !evaluacionAuxiliaresOld.equals(evaluacionAuxiliaresNew)) {
                evaluacionAuxiliaresOld.getSeleccionAuxiliaresList().remove(seleccionAuxiliares);
                evaluacionAuxiliaresOld = em.merge(evaluacionAuxiliaresOld);
            }
            if (evaluacionAuxiliaresNew != null && !evaluacionAuxiliaresNew.equals(evaluacionAuxiliaresOld)) {
                evaluacionAuxiliaresNew.getSeleccionAuxiliaresList().add(seleccionAuxiliares);
                evaluacionAuxiliaresNew = em.merge(evaluacionAuxiliaresNew);
            }
            if (auxiliarOld != null && !auxiliarOld.equals(auxiliarNew)) {
                auxiliarOld.getSeleccionAuxiliaresList().remove(seleccionAuxiliares);
                auxiliarOld = em.merge(auxiliarOld);
            }
            if (auxiliarNew != null && !auxiliarNew.equals(auxiliarOld)) {
                auxiliarNew.getSeleccionAuxiliaresList().add(seleccionAuxiliares);
                auxiliarNew = em.merge(auxiliarNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SeleccionAuxiliaresPK id = seleccionAuxiliares.getSeleccionAuxiliaresPK();
                if (findSeleccionAuxiliares(id) == null) {
                    throw new NonexistentEntityException("The seleccionAuxiliares with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SeleccionAuxiliaresPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SeleccionAuxiliares seleccionAuxiliares;
            try {
                seleccionAuxiliares = em.getReference(SeleccionAuxiliares.class, id);
                seleccionAuxiliares.getSeleccionAuxiliaresPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The seleccionAuxiliares with id " + id + " no longer exists.", enfe);
            }
            EvaluacionAuxiliares evaluacionAuxiliares = seleccionAuxiliares.getEvaluacionAuxiliares();
            if (evaluacionAuxiliares != null) {
                evaluacionAuxiliares.getSeleccionAuxiliaresList().remove(seleccionAuxiliares);
                evaluacionAuxiliares = em.merge(evaluacionAuxiliares);
            }
            Auxiliar auxiliar = seleccionAuxiliares.getAuxiliar();
            if (auxiliar != null) {
                auxiliar.getSeleccionAuxiliaresList().remove(seleccionAuxiliares);
                auxiliar = em.merge(auxiliar);
            }
            em.remove(seleccionAuxiliares);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SeleccionAuxiliares> findSeleccionAuxiliaresEntities() {
        return findSeleccionAuxiliaresEntities(true, -1, -1);
    }

    public List<SeleccionAuxiliares> findSeleccionAuxiliaresEntities(int maxResults, int firstResult) {
        return findSeleccionAuxiliaresEntities(false, maxResults, firstResult);
    }

    private List<SeleccionAuxiliares> findSeleccionAuxiliaresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SeleccionAuxiliares.class));
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

    public SeleccionAuxiliares findSeleccionAuxiliares(SeleccionAuxiliaresPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SeleccionAuxiliares.class, id);
        } finally {
            em.close();
        }
    }

    public int getSeleccionAuxiliaresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SeleccionAuxiliares> rt = cq.from(SeleccionAuxiliares.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
