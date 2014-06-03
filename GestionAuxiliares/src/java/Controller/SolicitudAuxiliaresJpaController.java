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
import Entities.Convocatoria;
import Entities.Requisito;
import Entities.SolicitudAuxiliares;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class SolicitudAuxiliaresJpaController implements Serializable {

    public SolicitudAuxiliaresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SolicitudAuxiliares solicitudAuxiliares) throws PreexistingEntityException, Exception {
        if (solicitudAuxiliares.getRequisitoList() == null) {
            solicitudAuxiliares.setRequisitoList(new ArrayList<Requisito>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Solicitante idSolicitante = solicitudAuxiliares.getIdSolicitante();
            if (idSolicitante != null) {
                idSolicitante = em.getReference(idSolicitante.getClass(), idSolicitante.getSolicitanteId());
                solicitudAuxiliares.setIdSolicitante(idSolicitante);
            }
            Convocatoria convocatoria = solicitudAuxiliares.getConvocatoria();
            if (convocatoria != null) {
                convocatoria = em.getReference(convocatoria.getClass(), convocatoria.getConvocatoriaId());
                solicitudAuxiliares.setConvocatoria(convocatoria);
            }
            List<Requisito> attachedRequisitoList = new ArrayList<Requisito>();
            for (Requisito requisitoListRequisitoToAttach : solicitudAuxiliares.getRequisitoList()) {
                requisitoListRequisitoToAttach = em.getReference(requisitoListRequisitoToAttach.getClass(), requisitoListRequisitoToAttach.getRequisitoId());
                attachedRequisitoList.add(requisitoListRequisitoToAttach);
            }
            solicitudAuxiliares.setRequisitoList(attachedRequisitoList);
            em.persist(solicitudAuxiliares);
            if (idSolicitante != null) {
                idSolicitante.getSolicitudAuxiliaresList().add(solicitudAuxiliares);
                idSolicitante = em.merge(idSolicitante);
            }
            if (convocatoria != null) {
                SolicitudAuxiliares oldIdSolicitudAuxiliaresOfConvocatoria = convocatoria.getIdSolicitudAuxiliares();
                if (oldIdSolicitudAuxiliaresOfConvocatoria != null) {
                    oldIdSolicitudAuxiliaresOfConvocatoria.setConvocatoria(null);
                    oldIdSolicitudAuxiliaresOfConvocatoria = em.merge(oldIdSolicitudAuxiliaresOfConvocatoria);
                }
                convocatoria.setIdSolicitudAuxiliares(solicitudAuxiliares);
                convocatoria = em.merge(convocatoria);
            }
            for (Requisito requisitoListRequisito : solicitudAuxiliares.getRequisitoList()) {
                requisitoListRequisito.getSolicitudAuxiliaresList().add(solicitudAuxiliares);
                requisitoListRequisito = em.merge(requisitoListRequisito);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSolicitudAuxiliares(solicitudAuxiliares.getSolicitudAuxiliaresId()) != null) {
                throw new PreexistingEntityException("SolicitudAuxiliares " + solicitudAuxiliares + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SolicitudAuxiliares solicitudAuxiliares) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SolicitudAuxiliares persistentSolicitudAuxiliares = em.find(SolicitudAuxiliares.class, solicitudAuxiliares.getSolicitudAuxiliaresId());
            Solicitante idSolicitanteOld = persistentSolicitudAuxiliares.getIdSolicitante();
            Solicitante idSolicitanteNew = solicitudAuxiliares.getIdSolicitante();
            Convocatoria convocatoriaOld = persistentSolicitudAuxiliares.getConvocatoria();
            Convocatoria convocatoriaNew = solicitudAuxiliares.getConvocatoria();
            List<Requisito> requisitoListOld = persistentSolicitudAuxiliares.getRequisitoList();
            List<Requisito> requisitoListNew = solicitudAuxiliares.getRequisitoList();
            List<String> illegalOrphanMessages = null;
            if (convocatoriaOld != null && !convocatoriaOld.equals(convocatoriaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Convocatoria " + convocatoriaOld + " since its idSolicitudAuxiliares field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idSolicitanteNew != null) {
                idSolicitanteNew = em.getReference(idSolicitanteNew.getClass(), idSolicitanteNew.getSolicitanteId());
                solicitudAuxiliares.setIdSolicitante(idSolicitanteNew);
            }
            if (convocatoriaNew != null) {
                convocatoriaNew = em.getReference(convocatoriaNew.getClass(), convocatoriaNew.getConvocatoriaId());
                solicitudAuxiliares.setConvocatoria(convocatoriaNew);
            }
            List<Requisito> attachedRequisitoListNew = new ArrayList<Requisito>();
            for (Requisito requisitoListNewRequisitoToAttach : requisitoListNew) {
                requisitoListNewRequisitoToAttach = em.getReference(requisitoListNewRequisitoToAttach.getClass(), requisitoListNewRequisitoToAttach.getRequisitoId());
                attachedRequisitoListNew.add(requisitoListNewRequisitoToAttach);
            }
            requisitoListNew = attachedRequisitoListNew;
            solicitudAuxiliares.setRequisitoList(requisitoListNew);
            solicitudAuxiliares = em.merge(solicitudAuxiliares);
            if (idSolicitanteOld != null && !idSolicitanteOld.equals(idSolicitanteNew)) {
                idSolicitanteOld.getSolicitudAuxiliaresList().remove(solicitudAuxiliares);
                idSolicitanteOld = em.merge(idSolicitanteOld);
            }
            if (idSolicitanteNew != null && !idSolicitanteNew.equals(idSolicitanteOld)) {
                idSolicitanteNew.getSolicitudAuxiliaresList().add(solicitudAuxiliares);
                idSolicitanteNew = em.merge(idSolicitanteNew);
            }
            if (convocatoriaNew != null && !convocatoriaNew.equals(convocatoriaOld)) {
                SolicitudAuxiliares oldIdSolicitudAuxiliaresOfConvocatoria = convocatoriaNew.getIdSolicitudAuxiliares();
                if (oldIdSolicitudAuxiliaresOfConvocatoria != null) {
                    oldIdSolicitudAuxiliaresOfConvocatoria.setConvocatoria(null);
                    oldIdSolicitudAuxiliaresOfConvocatoria = em.merge(oldIdSolicitudAuxiliaresOfConvocatoria);
                }
                convocatoriaNew.setIdSolicitudAuxiliares(solicitudAuxiliares);
                convocatoriaNew = em.merge(convocatoriaNew);
            }
            for (Requisito requisitoListOldRequisito : requisitoListOld) {
                if (!requisitoListNew.contains(requisitoListOldRequisito)) {
                    requisitoListOldRequisito.getSolicitudAuxiliaresList().remove(solicitudAuxiliares);
                    requisitoListOldRequisito = em.merge(requisitoListOldRequisito);
                }
            }
            for (Requisito requisitoListNewRequisito : requisitoListNew) {
                if (!requisitoListOld.contains(requisitoListNewRequisito)) {
                    requisitoListNewRequisito.getSolicitudAuxiliaresList().add(solicitudAuxiliares);
                    requisitoListNewRequisito = em.merge(requisitoListNewRequisito);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = solicitudAuxiliares.getSolicitudAuxiliaresId();
                if (findSolicitudAuxiliares(id) == null) {
                    throw new NonexistentEntityException("The solicitudAuxiliares with id " + id + " no longer exists.");
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
            SolicitudAuxiliares solicitudAuxiliares;
            try {
                solicitudAuxiliares = em.getReference(SolicitudAuxiliares.class, id);
                solicitudAuxiliares.getSolicitudAuxiliaresId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The solicitudAuxiliares with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Convocatoria convocatoriaOrphanCheck = solicitudAuxiliares.getConvocatoria();
            if (convocatoriaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SolicitudAuxiliares (" + solicitudAuxiliares + ") cannot be destroyed since the Convocatoria " + convocatoriaOrphanCheck + " in its convocatoria field has a non-nullable idSolicitudAuxiliares field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Solicitante idSolicitante = solicitudAuxiliares.getIdSolicitante();
            if (idSolicitante != null) {
                idSolicitante.getSolicitudAuxiliaresList().remove(solicitudAuxiliares);
                idSolicitante = em.merge(idSolicitante);
            }
            List<Requisito> requisitoList = solicitudAuxiliares.getRequisitoList();
            for (Requisito requisitoListRequisito : requisitoList) {
                requisitoListRequisito.getSolicitudAuxiliaresList().remove(solicitudAuxiliares);
                requisitoListRequisito = em.merge(requisitoListRequisito);
            }
            em.remove(solicitudAuxiliares);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SolicitudAuxiliares> findSolicitudAuxiliaresEntities() {
        return findSolicitudAuxiliaresEntities(true, -1, -1);
    }

    public List<SolicitudAuxiliares> findSolicitudAuxiliaresEntities(int maxResults, int firstResult) {
        return findSolicitudAuxiliaresEntities(false, maxResults, firstResult);
    }

    private List<SolicitudAuxiliares> findSolicitudAuxiliaresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SolicitudAuxiliares.class));
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

    public SolicitudAuxiliares findSolicitudAuxiliares(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SolicitudAuxiliares.class, id);
        } finally {
            em.close();
        }
    }

    public int getSolicitudAuxiliaresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SolicitudAuxiliares> rt = cq.from(SolicitudAuxiliares.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
