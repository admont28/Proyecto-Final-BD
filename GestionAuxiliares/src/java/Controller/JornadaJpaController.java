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
import Entities.Directores;
import Entities.Jornada;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class JornadaJpaController implements Serializable {

    public JornadaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Jornada jornada) throws PreexistingEntityException, Exception {
        if (jornada.getDirectoresList() == null) {
            jornada.setDirectoresList(new ArrayList<Directores>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Directores> attachedDirectoresList = new ArrayList<Directores>();
            for (Directores directoresListDirectoresToAttach : jornada.getDirectoresList()) {
                directoresListDirectoresToAttach = em.getReference(directoresListDirectoresToAttach.getClass(), directoresListDirectoresToAttach.getDirectoresPK());
                attachedDirectoresList.add(directoresListDirectoresToAttach);
            }
            jornada.setDirectoresList(attachedDirectoresList);
            em.persist(jornada);
            for (Directores directoresListDirectores : jornada.getDirectoresList()) {
                Jornada oldJornadaOfDirectoresListDirectores = directoresListDirectores.getJornada();
                directoresListDirectores.setJornada(jornada);
                directoresListDirectores = em.merge(directoresListDirectores);
                if (oldJornadaOfDirectoresListDirectores != null) {
                    oldJornadaOfDirectoresListDirectores.getDirectoresList().remove(directoresListDirectores);
                    oldJornadaOfDirectoresListDirectores = em.merge(oldJornadaOfDirectoresListDirectores);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findJornada(jornada.getJornadaId()) != null) {
                throw new PreexistingEntityException("Jornada " + jornada + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Jornada jornada) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jornada persistentJornada = em.find(Jornada.class, jornada.getJornadaId());
            List<Directores> directoresListOld = persistentJornada.getDirectoresList();
            List<Directores> directoresListNew = jornada.getDirectoresList();
            List<String> illegalOrphanMessages = null;
            for (Directores directoresListOldDirectores : directoresListOld) {
                if (!directoresListNew.contains(directoresListOldDirectores)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Directores " + directoresListOldDirectores + " since its jornada field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Directores> attachedDirectoresListNew = new ArrayList<Directores>();
            for (Directores directoresListNewDirectoresToAttach : directoresListNew) {
                directoresListNewDirectoresToAttach = em.getReference(directoresListNewDirectoresToAttach.getClass(), directoresListNewDirectoresToAttach.getDirectoresPK());
                attachedDirectoresListNew.add(directoresListNewDirectoresToAttach);
            }
            directoresListNew = attachedDirectoresListNew;
            jornada.setDirectoresList(directoresListNew);
            jornada = em.merge(jornada);
            for (Directores directoresListNewDirectores : directoresListNew) {
                if (!directoresListOld.contains(directoresListNewDirectores)) {
                    Jornada oldJornadaOfDirectoresListNewDirectores = directoresListNewDirectores.getJornada();
                    directoresListNewDirectores.setJornada(jornada);
                    directoresListNewDirectores = em.merge(directoresListNewDirectores);
                    if (oldJornadaOfDirectoresListNewDirectores != null && !oldJornadaOfDirectoresListNewDirectores.equals(jornada)) {
                        oldJornadaOfDirectoresListNewDirectores.getDirectoresList().remove(directoresListNewDirectores);
                        oldJornadaOfDirectoresListNewDirectores = em.merge(oldJornadaOfDirectoresListNewDirectores);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = jornada.getJornadaId();
                if (findJornada(id) == null) {
                    throw new NonexistentEntityException("The jornada with id " + id + " no longer exists.");
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
            Jornada jornada;
            try {
                jornada = em.getReference(Jornada.class, id);
                jornada.getJornadaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jornada with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Directores> directoresListOrphanCheck = jornada.getDirectoresList();
            for (Directores directoresListOrphanCheckDirectores : directoresListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Jornada (" + jornada + ") cannot be destroyed since the Directores " + directoresListOrphanCheckDirectores + " in its directoresList field has a non-nullable jornada field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(jornada);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Jornada> findJornadaEntities() {
        return findJornadaEntities(true, -1, -1);
    }

    public List<Jornada> findJornadaEntities(int maxResults, int firstResult) {
        return findJornadaEntities(false, maxResults, firstResult);
    }

    private List<Jornada> findJornadaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Jornada.class));
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

    public Jornada findJornada(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Jornada.class, id);
        } finally {
            em.close();
        }
    }

    public int getJornadaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Jornada> rt = cq.from(Jornada.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
