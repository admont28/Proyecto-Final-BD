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
import Entities.DetalleResolucion;
import Entities.ResAuxiliaresSeleccionados;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class ResAuxiliaresSeleccionadosJpaController implements Serializable {

    public ResAuxiliaresSeleccionadosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ResAuxiliaresSeleccionados resAuxiliaresSeleccionados) throws PreexistingEntityException, Exception {
        if (resAuxiliaresSeleccionados.getDetalleResolucionList() == null) {
            resAuxiliaresSeleccionados.setDetalleResolucionList(new ArrayList<DetalleResolucion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Solicitante idSolicitante = resAuxiliaresSeleccionados.getIdSolicitante();
            if (idSolicitante != null) {
                idSolicitante = em.getReference(idSolicitante.getClass(), idSolicitante.getSolicitanteId());
                resAuxiliaresSeleccionados.setIdSolicitante(idSolicitante);
            }
            List<DetalleResolucion> attachedDetalleResolucionList = new ArrayList<DetalleResolucion>();
            for (DetalleResolucion detalleResolucionListDetalleResolucionToAttach : resAuxiliaresSeleccionados.getDetalleResolucionList()) {
                detalleResolucionListDetalleResolucionToAttach = em.getReference(detalleResolucionListDetalleResolucionToAttach.getClass(), detalleResolucionListDetalleResolucionToAttach.getDetalleResolucionPK());
                attachedDetalleResolucionList.add(detalleResolucionListDetalleResolucionToAttach);
            }
            resAuxiliaresSeleccionados.setDetalleResolucionList(attachedDetalleResolucionList);
            em.persist(resAuxiliaresSeleccionados);
            if (idSolicitante != null) {
                idSolicitante.getResAuxiliaresSeleccionadosList().add(resAuxiliaresSeleccionados);
                idSolicitante = em.merge(idSolicitante);
            }
            for (DetalleResolucion detalleResolucionListDetalleResolucion : resAuxiliaresSeleccionados.getDetalleResolucionList()) {
                ResAuxiliaresSeleccionados oldResAuxiliaresSeleccionadosOfDetalleResolucionListDetalleResolucion = detalleResolucionListDetalleResolucion.getResAuxiliaresSeleccionados();
                detalleResolucionListDetalleResolucion.setResAuxiliaresSeleccionados(resAuxiliaresSeleccionados);
                detalleResolucionListDetalleResolucion = em.merge(detalleResolucionListDetalleResolucion);
                if (oldResAuxiliaresSeleccionadosOfDetalleResolucionListDetalleResolucion != null) {
                    oldResAuxiliaresSeleccionadosOfDetalleResolucionListDetalleResolucion.getDetalleResolucionList().remove(detalleResolucionListDetalleResolucion);
                    oldResAuxiliaresSeleccionadosOfDetalleResolucionListDetalleResolucion = em.merge(oldResAuxiliaresSeleccionadosOfDetalleResolucionListDetalleResolucion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findResAuxiliaresSeleccionados(resAuxiliaresSeleccionados.getResAuxSelId()) != null) {
                throw new PreexistingEntityException("ResAuxiliaresSeleccionados " + resAuxiliaresSeleccionados + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ResAuxiliaresSeleccionados resAuxiliaresSeleccionados) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ResAuxiliaresSeleccionados persistentResAuxiliaresSeleccionados = em.find(ResAuxiliaresSeleccionados.class, resAuxiliaresSeleccionados.getResAuxSelId());
            Solicitante idSolicitanteOld = persistentResAuxiliaresSeleccionados.getIdSolicitante();
            Solicitante idSolicitanteNew = resAuxiliaresSeleccionados.getIdSolicitante();
            List<DetalleResolucion> detalleResolucionListOld = persistentResAuxiliaresSeleccionados.getDetalleResolucionList();
            List<DetalleResolucion> detalleResolucionListNew = resAuxiliaresSeleccionados.getDetalleResolucionList();
            List<String> illegalOrphanMessages = null;
            for (DetalleResolucion detalleResolucionListOldDetalleResolucion : detalleResolucionListOld) {
                if (!detalleResolucionListNew.contains(detalleResolucionListOldDetalleResolucion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleResolucion " + detalleResolucionListOldDetalleResolucion + " since its resAuxiliaresSeleccionados field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idSolicitanteNew != null) {
                idSolicitanteNew = em.getReference(idSolicitanteNew.getClass(), idSolicitanteNew.getSolicitanteId());
                resAuxiliaresSeleccionados.setIdSolicitante(idSolicitanteNew);
            }
            List<DetalleResolucion> attachedDetalleResolucionListNew = new ArrayList<DetalleResolucion>();
            for (DetalleResolucion detalleResolucionListNewDetalleResolucionToAttach : detalleResolucionListNew) {
                detalleResolucionListNewDetalleResolucionToAttach = em.getReference(detalleResolucionListNewDetalleResolucionToAttach.getClass(), detalleResolucionListNewDetalleResolucionToAttach.getDetalleResolucionPK());
                attachedDetalleResolucionListNew.add(detalleResolucionListNewDetalleResolucionToAttach);
            }
            detalleResolucionListNew = attachedDetalleResolucionListNew;
            resAuxiliaresSeleccionados.setDetalleResolucionList(detalleResolucionListNew);
            resAuxiliaresSeleccionados = em.merge(resAuxiliaresSeleccionados);
            if (idSolicitanteOld != null && !idSolicitanteOld.equals(idSolicitanteNew)) {
                idSolicitanteOld.getResAuxiliaresSeleccionadosList().remove(resAuxiliaresSeleccionados);
                idSolicitanteOld = em.merge(idSolicitanteOld);
            }
            if (idSolicitanteNew != null && !idSolicitanteNew.equals(idSolicitanteOld)) {
                idSolicitanteNew.getResAuxiliaresSeleccionadosList().add(resAuxiliaresSeleccionados);
                idSolicitanteNew = em.merge(idSolicitanteNew);
            }
            for (DetalleResolucion detalleResolucionListNewDetalleResolucion : detalleResolucionListNew) {
                if (!detalleResolucionListOld.contains(detalleResolucionListNewDetalleResolucion)) {
                    ResAuxiliaresSeleccionados oldResAuxiliaresSeleccionadosOfDetalleResolucionListNewDetalleResolucion = detalleResolucionListNewDetalleResolucion.getResAuxiliaresSeleccionados();
                    detalleResolucionListNewDetalleResolucion.setResAuxiliaresSeleccionados(resAuxiliaresSeleccionados);
                    detalleResolucionListNewDetalleResolucion = em.merge(detalleResolucionListNewDetalleResolucion);
                    if (oldResAuxiliaresSeleccionadosOfDetalleResolucionListNewDetalleResolucion != null && !oldResAuxiliaresSeleccionadosOfDetalleResolucionListNewDetalleResolucion.equals(resAuxiliaresSeleccionados)) {
                        oldResAuxiliaresSeleccionadosOfDetalleResolucionListNewDetalleResolucion.getDetalleResolucionList().remove(detalleResolucionListNewDetalleResolucion);
                        oldResAuxiliaresSeleccionadosOfDetalleResolucionListNewDetalleResolucion = em.merge(oldResAuxiliaresSeleccionadosOfDetalleResolucionListNewDetalleResolucion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = resAuxiliaresSeleccionados.getResAuxSelId();
                if (findResAuxiliaresSeleccionados(id) == null) {
                    throw new NonexistentEntityException("The resAuxiliaresSeleccionados with id " + id + " no longer exists.");
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
            ResAuxiliaresSeleccionados resAuxiliaresSeleccionados;
            try {
                resAuxiliaresSeleccionados = em.getReference(ResAuxiliaresSeleccionados.class, id);
                resAuxiliaresSeleccionados.getResAuxSelId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The resAuxiliaresSeleccionados with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DetalleResolucion> detalleResolucionListOrphanCheck = resAuxiliaresSeleccionados.getDetalleResolucionList();
            for (DetalleResolucion detalleResolucionListOrphanCheckDetalleResolucion : detalleResolucionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ResAuxiliaresSeleccionados (" + resAuxiliaresSeleccionados + ") cannot be destroyed since the DetalleResolucion " + detalleResolucionListOrphanCheckDetalleResolucion + " in its detalleResolucionList field has a non-nullable resAuxiliaresSeleccionados field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Solicitante idSolicitante = resAuxiliaresSeleccionados.getIdSolicitante();
            if (idSolicitante != null) {
                idSolicitante.getResAuxiliaresSeleccionadosList().remove(resAuxiliaresSeleccionados);
                idSolicitante = em.merge(idSolicitante);
            }
            em.remove(resAuxiliaresSeleccionados);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ResAuxiliaresSeleccionados> findResAuxiliaresSeleccionadosEntities() {
        return findResAuxiliaresSeleccionadosEntities(true, -1, -1);
    }

    public List<ResAuxiliaresSeleccionados> findResAuxiliaresSeleccionadosEntities(int maxResults, int firstResult) {
        return findResAuxiliaresSeleccionadosEntities(false, maxResults, firstResult);
    }

    private List<ResAuxiliaresSeleccionados> findResAuxiliaresSeleccionadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ResAuxiliaresSeleccionados.class));
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

    public ResAuxiliaresSeleccionados findResAuxiliaresSeleccionados(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ResAuxiliaresSeleccionados.class, id);
        } finally {
            em.close();
        }
    }

    public int getResAuxiliaresSeleccionadosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ResAuxiliaresSeleccionados> rt = cq.from(ResAuxiliaresSeleccionados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
