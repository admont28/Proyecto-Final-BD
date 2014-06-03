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
import Entities.FechaHorario;
import Entities.Hora;
import Entities.HoraPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class HoraJpaController implements Serializable {

    public HoraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Hora hora) throws PreexistingEntityException, Exception {
        if (hora.getHoraPK() == null) {
            hora.setHoraPK(new HoraPK());
        }
        hora.getHoraPK().setIdFechaHorario(hora.getFechaHorario().getFechaHorarioId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FechaHorario fechaHorario = hora.getFechaHorario();
            if (fechaHorario != null) {
                fechaHorario = em.getReference(fechaHorario.getClass(), fechaHorario.getFechaHorarioId());
                hora.setFechaHorario(fechaHorario);
            }
            em.persist(hora);
            if (fechaHorario != null) {
                fechaHorario.getHoraList().add(hora);
                fechaHorario = em.merge(fechaHorario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHora(hora.getHoraPK()) != null) {
                throw new PreexistingEntityException("Hora " + hora + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Hora hora) throws NonexistentEntityException, Exception {
        hora.getHoraPK().setIdFechaHorario(hora.getFechaHorario().getFechaHorarioId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hora persistentHora = em.find(Hora.class, hora.getHoraPK());
            FechaHorario fechaHorarioOld = persistentHora.getFechaHorario();
            FechaHorario fechaHorarioNew = hora.getFechaHorario();
            if (fechaHorarioNew != null) {
                fechaHorarioNew = em.getReference(fechaHorarioNew.getClass(), fechaHorarioNew.getFechaHorarioId());
                hora.setFechaHorario(fechaHorarioNew);
            }
            hora = em.merge(hora);
            if (fechaHorarioOld != null && !fechaHorarioOld.equals(fechaHorarioNew)) {
                fechaHorarioOld.getHoraList().remove(hora);
                fechaHorarioOld = em.merge(fechaHorarioOld);
            }
            if (fechaHorarioNew != null && !fechaHorarioNew.equals(fechaHorarioOld)) {
                fechaHorarioNew.getHoraList().add(hora);
                fechaHorarioNew = em.merge(fechaHorarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                HoraPK id = hora.getHoraPK();
                if (findHora(id) == null) {
                    throw new NonexistentEntityException("The hora with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(HoraPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hora hora;
            try {
                hora = em.getReference(Hora.class, id);
                hora.getHoraPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hora with id " + id + " no longer exists.", enfe);
            }
            FechaHorario fechaHorario = hora.getFechaHorario();
            if (fechaHorario != null) {
                fechaHorario.getHoraList().remove(hora);
                fechaHorario = em.merge(fechaHorario);
            }
            em.remove(hora);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Hora> findHoraEntities() {
        return findHoraEntities(true, -1, -1);
    }

    public List<Hora> findHoraEntities(int maxResults, int firstResult) {
        return findHoraEntities(false, maxResults, firstResult);
    }

    private List<Hora> findHoraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Hora.class));
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

    public Hora findHora(HoraPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Hora.class, id);
        } finally {
            em.close();
        }
    }

    public int getHoraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Hora> rt = cq.from(Hora.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
