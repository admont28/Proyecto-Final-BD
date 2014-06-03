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
import Entities.TipoRequisito;
import Entities.SolicitudAuxiliares;
import java.util.ArrayList;
import java.util.List;
import Entities.Convocatoria;
import Entities.Requisito;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class RequisitoJpaController implements Serializable {

    public RequisitoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Requisito requisito) throws PreexistingEntityException, Exception {
        if (requisito.getSolicitudAuxiliaresList() == null) {
            requisito.setSolicitudAuxiliaresList(new ArrayList<SolicitudAuxiliares>());
        }
        if (requisito.getConvocatoriaList() == null) {
            requisito.setConvocatoriaList(new ArrayList<Convocatoria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoRequisito idTipoRequisito = requisito.getIdTipoRequisito();
            if (idTipoRequisito != null) {
                idTipoRequisito = em.getReference(idTipoRequisito.getClass(), idTipoRequisito.getTipoRequisitoId());
                requisito.setIdTipoRequisito(idTipoRequisito);
            }
            List<SolicitudAuxiliares> attachedSolicitudAuxiliaresList = new ArrayList<SolicitudAuxiliares>();
            for (SolicitudAuxiliares solicitudAuxiliaresListSolicitudAuxiliaresToAttach : requisito.getSolicitudAuxiliaresList()) {
                solicitudAuxiliaresListSolicitudAuxiliaresToAttach = em.getReference(solicitudAuxiliaresListSolicitudAuxiliaresToAttach.getClass(), solicitudAuxiliaresListSolicitudAuxiliaresToAttach.getSolicitudAuxiliaresId());
                attachedSolicitudAuxiliaresList.add(solicitudAuxiliaresListSolicitudAuxiliaresToAttach);
            }
            requisito.setSolicitudAuxiliaresList(attachedSolicitudAuxiliaresList);
            List<Convocatoria> attachedConvocatoriaList = new ArrayList<Convocatoria>();
            for (Convocatoria convocatoriaListConvocatoriaToAttach : requisito.getConvocatoriaList()) {
                convocatoriaListConvocatoriaToAttach = em.getReference(convocatoriaListConvocatoriaToAttach.getClass(), convocatoriaListConvocatoriaToAttach.getConvocatoriaId());
                attachedConvocatoriaList.add(convocatoriaListConvocatoriaToAttach);
            }
            requisito.setConvocatoriaList(attachedConvocatoriaList);
            em.persist(requisito);
            if (idTipoRequisito != null) {
                idTipoRequisito.getRequisitoList().add(requisito);
                idTipoRequisito = em.merge(idTipoRequisito);
            }
            for (SolicitudAuxiliares solicitudAuxiliaresListSolicitudAuxiliares : requisito.getSolicitudAuxiliaresList()) {
                solicitudAuxiliaresListSolicitudAuxiliares.getRequisitoList().add(requisito);
                solicitudAuxiliaresListSolicitudAuxiliares = em.merge(solicitudAuxiliaresListSolicitudAuxiliares);
            }
            for (Convocatoria convocatoriaListConvocatoria : requisito.getConvocatoriaList()) {
                convocatoriaListConvocatoria.getRequisitoList().add(requisito);
                convocatoriaListConvocatoria = em.merge(convocatoriaListConvocatoria);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRequisito(requisito.getRequisitoId()) != null) {
                throw new PreexistingEntityException("Requisito " + requisito + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Requisito requisito) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Requisito persistentRequisito = em.find(Requisito.class, requisito.getRequisitoId());
            TipoRequisito idTipoRequisitoOld = persistentRequisito.getIdTipoRequisito();
            TipoRequisito idTipoRequisitoNew = requisito.getIdTipoRequisito();
            List<SolicitudAuxiliares> solicitudAuxiliaresListOld = persistentRequisito.getSolicitudAuxiliaresList();
            List<SolicitudAuxiliares> solicitudAuxiliaresListNew = requisito.getSolicitudAuxiliaresList();
            List<Convocatoria> convocatoriaListOld = persistentRequisito.getConvocatoriaList();
            List<Convocatoria> convocatoriaListNew = requisito.getConvocatoriaList();
            if (idTipoRequisitoNew != null) {
                idTipoRequisitoNew = em.getReference(idTipoRequisitoNew.getClass(), idTipoRequisitoNew.getTipoRequisitoId());
                requisito.setIdTipoRequisito(idTipoRequisitoNew);
            }
            List<SolicitudAuxiliares> attachedSolicitudAuxiliaresListNew = new ArrayList<SolicitudAuxiliares>();
            for (SolicitudAuxiliares solicitudAuxiliaresListNewSolicitudAuxiliaresToAttach : solicitudAuxiliaresListNew) {
                solicitudAuxiliaresListNewSolicitudAuxiliaresToAttach = em.getReference(solicitudAuxiliaresListNewSolicitudAuxiliaresToAttach.getClass(), solicitudAuxiliaresListNewSolicitudAuxiliaresToAttach.getSolicitudAuxiliaresId());
                attachedSolicitudAuxiliaresListNew.add(solicitudAuxiliaresListNewSolicitudAuxiliaresToAttach);
            }
            solicitudAuxiliaresListNew = attachedSolicitudAuxiliaresListNew;
            requisito.setSolicitudAuxiliaresList(solicitudAuxiliaresListNew);
            List<Convocatoria> attachedConvocatoriaListNew = new ArrayList<Convocatoria>();
            for (Convocatoria convocatoriaListNewConvocatoriaToAttach : convocatoriaListNew) {
                convocatoriaListNewConvocatoriaToAttach = em.getReference(convocatoriaListNewConvocatoriaToAttach.getClass(), convocatoriaListNewConvocatoriaToAttach.getConvocatoriaId());
                attachedConvocatoriaListNew.add(convocatoriaListNewConvocatoriaToAttach);
            }
            convocatoriaListNew = attachedConvocatoriaListNew;
            requisito.setConvocatoriaList(convocatoriaListNew);
            requisito = em.merge(requisito);
            if (idTipoRequisitoOld != null && !idTipoRequisitoOld.equals(idTipoRequisitoNew)) {
                idTipoRequisitoOld.getRequisitoList().remove(requisito);
                idTipoRequisitoOld = em.merge(idTipoRequisitoOld);
            }
            if (idTipoRequisitoNew != null && !idTipoRequisitoNew.equals(idTipoRequisitoOld)) {
                idTipoRequisitoNew.getRequisitoList().add(requisito);
                idTipoRequisitoNew = em.merge(idTipoRequisitoNew);
            }
            for (SolicitudAuxiliares solicitudAuxiliaresListOldSolicitudAuxiliares : solicitudAuxiliaresListOld) {
                if (!solicitudAuxiliaresListNew.contains(solicitudAuxiliaresListOldSolicitudAuxiliares)) {
                    solicitudAuxiliaresListOldSolicitudAuxiliares.getRequisitoList().remove(requisito);
                    solicitudAuxiliaresListOldSolicitudAuxiliares = em.merge(solicitudAuxiliaresListOldSolicitudAuxiliares);
                }
            }
            for (SolicitudAuxiliares solicitudAuxiliaresListNewSolicitudAuxiliares : solicitudAuxiliaresListNew) {
                if (!solicitudAuxiliaresListOld.contains(solicitudAuxiliaresListNewSolicitudAuxiliares)) {
                    solicitudAuxiliaresListNewSolicitudAuxiliares.getRequisitoList().add(requisito);
                    solicitudAuxiliaresListNewSolicitudAuxiliares = em.merge(solicitudAuxiliaresListNewSolicitudAuxiliares);
                }
            }
            for (Convocatoria convocatoriaListOldConvocatoria : convocatoriaListOld) {
                if (!convocatoriaListNew.contains(convocatoriaListOldConvocatoria)) {
                    convocatoriaListOldConvocatoria.getRequisitoList().remove(requisito);
                    convocatoriaListOldConvocatoria = em.merge(convocatoriaListOldConvocatoria);
                }
            }
            for (Convocatoria convocatoriaListNewConvocatoria : convocatoriaListNew) {
                if (!convocatoriaListOld.contains(convocatoriaListNewConvocatoria)) {
                    convocatoriaListNewConvocatoria.getRequisitoList().add(requisito);
                    convocatoriaListNewConvocatoria = em.merge(convocatoriaListNewConvocatoria);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = requisito.getRequisitoId();
                if (findRequisito(id) == null) {
                    throw new NonexistentEntityException("The requisito with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Requisito requisito;
            try {
                requisito = em.getReference(Requisito.class, id);
                requisito.getRequisitoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The requisito with id " + id + " no longer exists.", enfe);
            }
            TipoRequisito idTipoRequisito = requisito.getIdTipoRequisito();
            if (idTipoRequisito != null) {
                idTipoRequisito.getRequisitoList().remove(requisito);
                idTipoRequisito = em.merge(idTipoRequisito);
            }
            List<SolicitudAuxiliares> solicitudAuxiliaresList = requisito.getSolicitudAuxiliaresList();
            for (SolicitudAuxiliares solicitudAuxiliaresListSolicitudAuxiliares : solicitudAuxiliaresList) {
                solicitudAuxiliaresListSolicitudAuxiliares.getRequisitoList().remove(requisito);
                solicitudAuxiliaresListSolicitudAuxiliares = em.merge(solicitudAuxiliaresListSolicitudAuxiliares);
            }
            List<Convocatoria> convocatoriaList = requisito.getConvocatoriaList();
            for (Convocatoria convocatoriaListConvocatoria : convocatoriaList) {
                convocatoriaListConvocatoria.getRequisitoList().remove(requisito);
                convocatoriaListConvocatoria = em.merge(convocatoriaListConvocatoria);
            }
            em.remove(requisito);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Requisito> findRequisitoEntities() {
        return findRequisitoEntities(true, -1, -1);
    }

    public List<Requisito> findRequisitoEntities(int maxResults, int firstResult) {
        return findRequisitoEntities(false, maxResults, firstResult);
    }

    private List<Requisito> findRequisitoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Requisito.class));
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

    public Requisito findRequisito(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Requisito.class, id);
        } finally {
            em.close();
        }
    }

    public int getRequisitoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Requisito> rt = cq.from(Requisito.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
