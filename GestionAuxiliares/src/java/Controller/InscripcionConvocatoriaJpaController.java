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
import Entities.Convocatoria;
import Entities.Auxiliar;
import Entities.InscripcionConvocatoria;
import Entities.InscripcionConvocatoriaPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class InscripcionConvocatoriaJpaController implements Serializable {

    public InscripcionConvocatoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InscripcionConvocatoria inscripcionConvocatoria) throws PreexistingEntityException, Exception {
        if (inscripcionConvocatoria.getInscripcionConvocatoriaPK() == null) {
            inscripcionConvocatoria.setInscripcionConvocatoriaPK(new InscripcionConvocatoriaPK());
        }
        inscripcionConvocatoria.getInscripcionConvocatoriaPK().setIdAuxiliar(inscripcionConvocatoria.getAuxiliar().getAuxiliarId());
        inscripcionConvocatoria.getInscripcionConvocatoriaPK().setIdConvocatoria(inscripcionConvocatoria.getConvocatoria().getConvocatoriaId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Convocatoria convocatoria = inscripcionConvocatoria.getConvocatoria();
            if (convocatoria != null) {
                convocatoria = em.getReference(convocatoria.getClass(), convocatoria.getConvocatoriaId());
                inscripcionConvocatoria.setConvocatoria(convocatoria);
            }
            Auxiliar auxiliar = inscripcionConvocatoria.getAuxiliar();
            if (auxiliar != null) {
                auxiliar = em.getReference(auxiliar.getClass(), auxiliar.getAuxiliarId());
                inscripcionConvocatoria.setAuxiliar(auxiliar);
            }
            em.persist(inscripcionConvocatoria);
            if (convocatoria != null) {
                convocatoria.getInscripcionConvocatoriaList().add(inscripcionConvocatoria);
                convocatoria = em.merge(convocatoria);
            }
            if (auxiliar != null) {
                auxiliar.getInscripcionConvocatoriaList().add(inscripcionConvocatoria);
                auxiliar = em.merge(auxiliar);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInscripcionConvocatoria(inscripcionConvocatoria.getInscripcionConvocatoriaPK()) != null) {
                throw new PreexistingEntityException("InscripcionConvocatoria " + inscripcionConvocatoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InscripcionConvocatoria inscripcionConvocatoria) throws NonexistentEntityException, Exception {
        inscripcionConvocatoria.getInscripcionConvocatoriaPK().setIdAuxiliar(inscripcionConvocatoria.getAuxiliar().getAuxiliarId());
        inscripcionConvocatoria.getInscripcionConvocatoriaPK().setIdConvocatoria(inscripcionConvocatoria.getConvocatoria().getConvocatoriaId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InscripcionConvocatoria persistentInscripcionConvocatoria = em.find(InscripcionConvocatoria.class, inscripcionConvocatoria.getInscripcionConvocatoriaPK());
            Convocatoria convocatoriaOld = persistentInscripcionConvocatoria.getConvocatoria();
            Convocatoria convocatoriaNew = inscripcionConvocatoria.getConvocatoria();
            Auxiliar auxiliarOld = persistentInscripcionConvocatoria.getAuxiliar();
            Auxiliar auxiliarNew = inscripcionConvocatoria.getAuxiliar();
            if (convocatoriaNew != null) {
                convocatoriaNew = em.getReference(convocatoriaNew.getClass(), convocatoriaNew.getConvocatoriaId());
                inscripcionConvocatoria.setConvocatoria(convocatoriaNew);
            }
            if (auxiliarNew != null) {
                auxiliarNew = em.getReference(auxiliarNew.getClass(), auxiliarNew.getAuxiliarId());
                inscripcionConvocatoria.setAuxiliar(auxiliarNew);
            }
            inscripcionConvocatoria = em.merge(inscripcionConvocatoria);
            if (convocatoriaOld != null && !convocatoriaOld.equals(convocatoriaNew)) {
                convocatoriaOld.getInscripcionConvocatoriaList().remove(inscripcionConvocatoria);
                convocatoriaOld = em.merge(convocatoriaOld);
            }
            if (convocatoriaNew != null && !convocatoriaNew.equals(convocatoriaOld)) {
                convocatoriaNew.getInscripcionConvocatoriaList().add(inscripcionConvocatoria);
                convocatoriaNew = em.merge(convocatoriaNew);
            }
            if (auxiliarOld != null && !auxiliarOld.equals(auxiliarNew)) {
                auxiliarOld.getInscripcionConvocatoriaList().remove(inscripcionConvocatoria);
                auxiliarOld = em.merge(auxiliarOld);
            }
            if (auxiliarNew != null && !auxiliarNew.equals(auxiliarOld)) {
                auxiliarNew.getInscripcionConvocatoriaList().add(inscripcionConvocatoria);
                auxiliarNew = em.merge(auxiliarNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                InscripcionConvocatoriaPK id = inscripcionConvocatoria.getInscripcionConvocatoriaPK();
                if (findInscripcionConvocatoria(id) == null) {
                    throw new NonexistentEntityException("The inscripcionConvocatoria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(InscripcionConvocatoriaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            InscripcionConvocatoria inscripcionConvocatoria;
            try {
                inscripcionConvocatoria = em.getReference(InscripcionConvocatoria.class, id);
                inscripcionConvocatoria.getInscripcionConvocatoriaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inscripcionConvocatoria with id " + id + " no longer exists.", enfe);
            }
            Convocatoria convocatoria = inscripcionConvocatoria.getConvocatoria();
            if (convocatoria != null) {
                convocatoria.getInscripcionConvocatoriaList().remove(inscripcionConvocatoria);
                convocatoria = em.merge(convocatoria);
            }
            Auxiliar auxiliar = inscripcionConvocatoria.getAuxiliar();
            if (auxiliar != null) {
                auxiliar.getInscripcionConvocatoriaList().remove(inscripcionConvocatoria);
                auxiliar = em.merge(auxiliar);
            }
            em.remove(inscripcionConvocatoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<InscripcionConvocatoria> findInscripcionConvocatoriaEntities() {
        return findInscripcionConvocatoriaEntities(true, -1, -1);
    }

    public List<InscripcionConvocatoria> findInscripcionConvocatoriaEntities(int maxResults, int firstResult) {
        return findInscripcionConvocatoriaEntities(false, maxResults, firstResult);
    }

    private List<InscripcionConvocatoria> findInscripcionConvocatoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InscripcionConvocatoria.class));
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

    public InscripcionConvocatoria findInscripcionConvocatoria(InscripcionConvocatoriaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InscripcionConvocatoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getInscripcionConvocatoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InscripcionConvocatoria> rt = cq.from(InscripcionConvocatoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
