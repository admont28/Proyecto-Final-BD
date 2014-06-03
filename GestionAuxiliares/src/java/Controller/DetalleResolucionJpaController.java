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
import Entities.ResAuxiliaresSeleccionados;
import Entities.Auxiliar;
import Entities.DetalleResolucion;
import Entities.DetalleResolucionPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class DetalleResolucionJpaController implements Serializable {

    public DetalleResolucionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleResolucion detalleResolucion) throws PreexistingEntityException, Exception {
        if (detalleResolucion.getDetalleResolucionPK() == null) {
            detalleResolucion.setDetalleResolucionPK(new DetalleResolucionPK());
        }
        detalleResolucion.getDetalleResolucionPK().setIdAuxiliar(detalleResolucion.getAuxiliar().getAuxiliarId());
        detalleResolucion.getDetalleResolucionPK().setIdResAuxSel(detalleResolucion.getResAuxiliaresSeleccionados().getResAuxSelId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ResAuxiliaresSeleccionados resAuxiliaresSeleccionados = detalleResolucion.getResAuxiliaresSeleccionados();
            if (resAuxiliaresSeleccionados != null) {
                resAuxiliaresSeleccionados = em.getReference(resAuxiliaresSeleccionados.getClass(), resAuxiliaresSeleccionados.getResAuxSelId());
                detalleResolucion.setResAuxiliaresSeleccionados(resAuxiliaresSeleccionados);
            }
            Auxiliar auxiliar = detalleResolucion.getAuxiliar();
            if (auxiliar != null) {
                auxiliar = em.getReference(auxiliar.getClass(), auxiliar.getAuxiliarId());
                detalleResolucion.setAuxiliar(auxiliar);
            }
            em.persist(detalleResolucion);
            if (resAuxiliaresSeleccionados != null) {
                resAuxiliaresSeleccionados.getDetalleResolucionList().add(detalleResolucion);
                resAuxiliaresSeleccionados = em.merge(resAuxiliaresSeleccionados);
            }
            if (auxiliar != null) {
                auxiliar.getDetalleResolucionList().add(detalleResolucion);
                auxiliar = em.merge(auxiliar);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDetalleResolucion(detalleResolucion.getDetalleResolucionPK()) != null) {
                throw new PreexistingEntityException("DetalleResolucion " + detalleResolucion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleResolucion detalleResolucion) throws NonexistentEntityException, Exception {
        detalleResolucion.getDetalleResolucionPK().setIdAuxiliar(detalleResolucion.getAuxiliar().getAuxiliarId());
        detalleResolucion.getDetalleResolucionPK().setIdResAuxSel(detalleResolucion.getResAuxiliaresSeleccionados().getResAuxSelId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleResolucion persistentDetalleResolucion = em.find(DetalleResolucion.class, detalleResolucion.getDetalleResolucionPK());
            ResAuxiliaresSeleccionados resAuxiliaresSeleccionadosOld = persistentDetalleResolucion.getResAuxiliaresSeleccionados();
            ResAuxiliaresSeleccionados resAuxiliaresSeleccionadosNew = detalleResolucion.getResAuxiliaresSeleccionados();
            Auxiliar auxiliarOld = persistentDetalleResolucion.getAuxiliar();
            Auxiliar auxiliarNew = detalleResolucion.getAuxiliar();
            if (resAuxiliaresSeleccionadosNew != null) {
                resAuxiliaresSeleccionadosNew = em.getReference(resAuxiliaresSeleccionadosNew.getClass(), resAuxiliaresSeleccionadosNew.getResAuxSelId());
                detalleResolucion.setResAuxiliaresSeleccionados(resAuxiliaresSeleccionadosNew);
            }
            if (auxiliarNew != null) {
                auxiliarNew = em.getReference(auxiliarNew.getClass(), auxiliarNew.getAuxiliarId());
                detalleResolucion.setAuxiliar(auxiliarNew);
            }
            detalleResolucion = em.merge(detalleResolucion);
            if (resAuxiliaresSeleccionadosOld != null && !resAuxiliaresSeleccionadosOld.equals(resAuxiliaresSeleccionadosNew)) {
                resAuxiliaresSeleccionadosOld.getDetalleResolucionList().remove(detalleResolucion);
                resAuxiliaresSeleccionadosOld = em.merge(resAuxiliaresSeleccionadosOld);
            }
            if (resAuxiliaresSeleccionadosNew != null && !resAuxiliaresSeleccionadosNew.equals(resAuxiliaresSeleccionadosOld)) {
                resAuxiliaresSeleccionadosNew.getDetalleResolucionList().add(detalleResolucion);
                resAuxiliaresSeleccionadosNew = em.merge(resAuxiliaresSeleccionadosNew);
            }
            if (auxiliarOld != null && !auxiliarOld.equals(auxiliarNew)) {
                auxiliarOld.getDetalleResolucionList().remove(detalleResolucion);
                auxiliarOld = em.merge(auxiliarOld);
            }
            if (auxiliarNew != null && !auxiliarNew.equals(auxiliarOld)) {
                auxiliarNew.getDetalleResolucionList().add(detalleResolucion);
                auxiliarNew = em.merge(auxiliarNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                DetalleResolucionPK id = detalleResolucion.getDetalleResolucionPK();
                if (findDetalleResolucion(id) == null) {
                    throw new NonexistentEntityException("The detalleResolucion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(DetalleResolucionPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleResolucion detalleResolucion;
            try {
                detalleResolucion = em.getReference(DetalleResolucion.class, id);
                detalleResolucion.getDetalleResolucionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleResolucion with id " + id + " no longer exists.", enfe);
            }
            ResAuxiliaresSeleccionados resAuxiliaresSeleccionados = detalleResolucion.getResAuxiliaresSeleccionados();
            if (resAuxiliaresSeleccionados != null) {
                resAuxiliaresSeleccionados.getDetalleResolucionList().remove(detalleResolucion);
                resAuxiliaresSeleccionados = em.merge(resAuxiliaresSeleccionados);
            }
            Auxiliar auxiliar = detalleResolucion.getAuxiliar();
            if (auxiliar != null) {
                auxiliar.getDetalleResolucionList().remove(detalleResolucion);
                auxiliar = em.merge(auxiliar);
            }
            em.remove(detalleResolucion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleResolucion> findDetalleResolucionEntities() {
        return findDetalleResolucionEntities(true, -1, -1);
    }

    public List<DetalleResolucion> findDetalleResolucionEntities(int maxResults, int firstResult) {
        return findDetalleResolucionEntities(false, maxResults, firstResult);
    }

    private List<DetalleResolucion> findDetalleResolucionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleResolucion.class));
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

    public DetalleResolucion findDetalleResolucion(DetalleResolucionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleResolucion.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleResolucionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleResolucion> rt = cq.from(DetalleResolucion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
