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
import Entities.ConsejoCurricular;
import Entities.Secretaria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class SecretariaJpaController implements Serializable {

    public SecretariaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Secretaria secretaria) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ConsejoCurricular consejoCurricular = secretaria.getConsejoCurricular();
            if (consejoCurricular != null) {
                consejoCurricular = em.getReference(consejoCurricular.getClass(), consejoCurricular.getConsejoCurricularId());
                secretaria.setConsejoCurricular(consejoCurricular);
            }
            em.persist(secretaria);
            if (consejoCurricular != null) {
                Secretaria oldIdSecretariaOfConsejoCurricular = consejoCurricular.getIdSecretaria();
                if (oldIdSecretariaOfConsejoCurricular != null) {
                    oldIdSecretariaOfConsejoCurricular.setConsejoCurricular(null);
                    oldIdSecretariaOfConsejoCurricular = em.merge(oldIdSecretariaOfConsejoCurricular);
                }
                consejoCurricular.setIdSecretaria(secretaria);
                consejoCurricular = em.merge(consejoCurricular);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSecretaria(secretaria.getSecretariaId()) != null) {
                throw new PreexistingEntityException("Secretaria " + secretaria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Secretaria secretaria) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Secretaria persistentSecretaria = em.find(Secretaria.class, secretaria.getSecretariaId());
            ConsejoCurricular consejoCurricularOld = persistentSecretaria.getConsejoCurricular();
            ConsejoCurricular consejoCurricularNew = secretaria.getConsejoCurricular();
            List<String> illegalOrphanMessages = null;
            if (consejoCurricularOld != null && !consejoCurricularOld.equals(consejoCurricularNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain ConsejoCurricular " + consejoCurricularOld + " since its idSecretaria field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (consejoCurricularNew != null) {
                consejoCurricularNew = em.getReference(consejoCurricularNew.getClass(), consejoCurricularNew.getConsejoCurricularId());
                secretaria.setConsejoCurricular(consejoCurricularNew);
            }
            secretaria = em.merge(secretaria);
            if (consejoCurricularNew != null && !consejoCurricularNew.equals(consejoCurricularOld)) {
                Secretaria oldIdSecretariaOfConsejoCurricular = consejoCurricularNew.getIdSecretaria();
                if (oldIdSecretariaOfConsejoCurricular != null) {
                    oldIdSecretariaOfConsejoCurricular.setConsejoCurricular(null);
                    oldIdSecretariaOfConsejoCurricular = em.merge(oldIdSecretariaOfConsejoCurricular);
                }
                consejoCurricularNew.setIdSecretaria(secretaria);
                consejoCurricularNew = em.merge(consejoCurricularNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = secretaria.getSecretariaId();
                if (findSecretaria(id) == null) {
                    throw new NonexistentEntityException("The secretaria with id " + id + " no longer exists.");
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
            Secretaria secretaria;
            try {
                secretaria = em.getReference(Secretaria.class, id);
                secretaria.getSecretariaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The secretaria with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            ConsejoCurricular consejoCurricularOrphanCheck = secretaria.getConsejoCurricular();
            if (consejoCurricularOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Secretaria (" + secretaria + ") cannot be destroyed since the ConsejoCurricular " + consejoCurricularOrphanCheck + " in its consejoCurricular field has a non-nullable idSecretaria field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(secretaria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Secretaria> findSecretariaEntities() {
        return findSecretariaEntities(true, -1, -1);
    }

    public List<Secretaria> findSecretariaEntities(int maxResults, int firstResult) {
        return findSecretariaEntities(false, maxResults, firstResult);
    }

    private List<Secretaria> findSecretariaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Secretaria.class));
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

    public Secretaria findSecretaria(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Secretaria.class, id);
        } finally {
            em.close();
        }
    }

    public int getSecretariaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Secretaria> rt = cq.from(Secretaria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
