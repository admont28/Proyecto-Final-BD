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
import Entities.EvaluacionAuxiliares;
import Entities.SeleccionAuxiliares;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class EvaluacionAuxiliaresJpaController implements Serializable {

    public EvaluacionAuxiliaresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EvaluacionAuxiliares evaluacionAuxiliares) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (evaluacionAuxiliares.getSeleccionAuxiliaresList() == null) {
            evaluacionAuxiliares.setSeleccionAuxiliaresList(new ArrayList<SeleccionAuxiliares>());
        }
        List<String> illegalOrphanMessages = null;
        Convocatoria idConvocatoriaOrphanCheck = evaluacionAuxiliares.getIdConvocatoria();
        if (idConvocatoriaOrphanCheck != null) {
            EvaluacionAuxiliares oldEvaluacionAuxiliaresOfIdConvocatoria = idConvocatoriaOrphanCheck.getEvaluacionAuxiliares();
            if (oldEvaluacionAuxiliaresOfIdConvocatoria != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Convocatoria " + idConvocatoriaOrphanCheck + " already has an item of type EvaluacionAuxiliares whose idConvocatoria column cannot be null. Please make another selection for the idConvocatoria field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Solicitante idSolicitante = evaluacionAuxiliares.getIdSolicitante();
            if (idSolicitante != null) {
                idSolicitante = em.getReference(idSolicitante.getClass(), idSolicitante.getSolicitanteId());
                evaluacionAuxiliares.setIdSolicitante(idSolicitante);
            }
            Convocatoria idConvocatoria = evaluacionAuxiliares.getIdConvocatoria();
            if (idConvocatoria != null) {
                idConvocatoria = em.getReference(idConvocatoria.getClass(), idConvocatoria.getConvocatoriaId());
                evaluacionAuxiliares.setIdConvocatoria(idConvocatoria);
            }
            List<SeleccionAuxiliares> attachedSeleccionAuxiliaresList = new ArrayList<SeleccionAuxiliares>();
            for (SeleccionAuxiliares seleccionAuxiliaresListSeleccionAuxiliaresToAttach : evaluacionAuxiliares.getSeleccionAuxiliaresList()) {
                seleccionAuxiliaresListSeleccionAuxiliaresToAttach = em.getReference(seleccionAuxiliaresListSeleccionAuxiliaresToAttach.getClass(), seleccionAuxiliaresListSeleccionAuxiliaresToAttach.getSeleccionAuxiliaresPK());
                attachedSeleccionAuxiliaresList.add(seleccionAuxiliaresListSeleccionAuxiliaresToAttach);
            }
            evaluacionAuxiliares.setSeleccionAuxiliaresList(attachedSeleccionAuxiliaresList);
            em.persist(evaluacionAuxiliares);
            if (idSolicitante != null) {
                idSolicitante.getEvaluacionAuxiliaresList().add(evaluacionAuxiliares);
                idSolicitante = em.merge(idSolicitante);
            }
            if (idConvocatoria != null) {
                idConvocatoria.setEvaluacionAuxiliares(evaluacionAuxiliares);
                idConvocatoria = em.merge(idConvocatoria);
            }
            for (SeleccionAuxiliares seleccionAuxiliaresListSeleccionAuxiliares : evaluacionAuxiliares.getSeleccionAuxiliaresList()) {
                EvaluacionAuxiliares oldEvaluacionAuxiliaresOfSeleccionAuxiliaresListSeleccionAuxiliares = seleccionAuxiliaresListSeleccionAuxiliares.getEvaluacionAuxiliares();
                seleccionAuxiliaresListSeleccionAuxiliares.setEvaluacionAuxiliares(evaluacionAuxiliares);
                seleccionAuxiliaresListSeleccionAuxiliares = em.merge(seleccionAuxiliaresListSeleccionAuxiliares);
                if (oldEvaluacionAuxiliaresOfSeleccionAuxiliaresListSeleccionAuxiliares != null) {
                    oldEvaluacionAuxiliaresOfSeleccionAuxiliaresListSeleccionAuxiliares.getSeleccionAuxiliaresList().remove(seleccionAuxiliaresListSeleccionAuxiliares);
                    oldEvaluacionAuxiliaresOfSeleccionAuxiliaresListSeleccionAuxiliares = em.merge(oldEvaluacionAuxiliaresOfSeleccionAuxiliaresListSeleccionAuxiliares);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEvaluacionAuxiliares(evaluacionAuxiliares.getEvaluacionAuxiliaresId()) != null) {
                throw new PreexistingEntityException("EvaluacionAuxiliares " + evaluacionAuxiliares + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EvaluacionAuxiliares evaluacionAuxiliares) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EvaluacionAuxiliares persistentEvaluacionAuxiliares = em.find(EvaluacionAuxiliares.class, evaluacionAuxiliares.getEvaluacionAuxiliaresId());
            Solicitante idSolicitanteOld = persistentEvaluacionAuxiliares.getIdSolicitante();
            Solicitante idSolicitanteNew = evaluacionAuxiliares.getIdSolicitante();
            Convocatoria idConvocatoriaOld = persistentEvaluacionAuxiliares.getIdConvocatoria();
            Convocatoria idConvocatoriaNew = evaluacionAuxiliares.getIdConvocatoria();
            List<SeleccionAuxiliares> seleccionAuxiliaresListOld = persistentEvaluacionAuxiliares.getSeleccionAuxiliaresList();
            List<SeleccionAuxiliares> seleccionAuxiliaresListNew = evaluacionAuxiliares.getSeleccionAuxiliaresList();
            List<String> illegalOrphanMessages = null;
            if (idConvocatoriaNew != null && !idConvocatoriaNew.equals(idConvocatoriaOld)) {
                EvaluacionAuxiliares oldEvaluacionAuxiliaresOfIdConvocatoria = idConvocatoriaNew.getEvaluacionAuxiliares();
                if (oldEvaluacionAuxiliaresOfIdConvocatoria != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Convocatoria " + idConvocatoriaNew + " already has an item of type EvaluacionAuxiliares whose idConvocatoria column cannot be null. Please make another selection for the idConvocatoria field.");
                }
            }
            for (SeleccionAuxiliares seleccionAuxiliaresListOldSeleccionAuxiliares : seleccionAuxiliaresListOld) {
                if (!seleccionAuxiliaresListNew.contains(seleccionAuxiliaresListOldSeleccionAuxiliares)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SeleccionAuxiliares " + seleccionAuxiliaresListOldSeleccionAuxiliares + " since its evaluacionAuxiliares field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idSolicitanteNew != null) {
                idSolicitanteNew = em.getReference(idSolicitanteNew.getClass(), idSolicitanteNew.getSolicitanteId());
                evaluacionAuxiliares.setIdSolicitante(idSolicitanteNew);
            }
            if (idConvocatoriaNew != null) {
                idConvocatoriaNew = em.getReference(idConvocatoriaNew.getClass(), idConvocatoriaNew.getConvocatoriaId());
                evaluacionAuxiliares.setIdConvocatoria(idConvocatoriaNew);
            }
            List<SeleccionAuxiliares> attachedSeleccionAuxiliaresListNew = new ArrayList<SeleccionAuxiliares>();
            for (SeleccionAuxiliares seleccionAuxiliaresListNewSeleccionAuxiliaresToAttach : seleccionAuxiliaresListNew) {
                seleccionAuxiliaresListNewSeleccionAuxiliaresToAttach = em.getReference(seleccionAuxiliaresListNewSeleccionAuxiliaresToAttach.getClass(), seleccionAuxiliaresListNewSeleccionAuxiliaresToAttach.getSeleccionAuxiliaresPK());
                attachedSeleccionAuxiliaresListNew.add(seleccionAuxiliaresListNewSeleccionAuxiliaresToAttach);
            }
            seleccionAuxiliaresListNew = attachedSeleccionAuxiliaresListNew;
            evaluacionAuxiliares.setSeleccionAuxiliaresList(seleccionAuxiliaresListNew);
            evaluacionAuxiliares = em.merge(evaluacionAuxiliares);
            if (idSolicitanteOld != null && !idSolicitanteOld.equals(idSolicitanteNew)) {
                idSolicitanteOld.getEvaluacionAuxiliaresList().remove(evaluacionAuxiliares);
                idSolicitanteOld = em.merge(idSolicitanteOld);
            }
            if (idSolicitanteNew != null && !idSolicitanteNew.equals(idSolicitanteOld)) {
                idSolicitanteNew.getEvaluacionAuxiliaresList().add(evaluacionAuxiliares);
                idSolicitanteNew = em.merge(idSolicitanteNew);
            }
            if (idConvocatoriaOld != null && !idConvocatoriaOld.equals(idConvocatoriaNew)) {
                idConvocatoriaOld.setEvaluacionAuxiliares(null);
                idConvocatoriaOld = em.merge(idConvocatoriaOld);
            }
            if (idConvocatoriaNew != null && !idConvocatoriaNew.equals(idConvocatoriaOld)) {
                idConvocatoriaNew.setEvaluacionAuxiliares(evaluacionAuxiliares);
                idConvocatoriaNew = em.merge(idConvocatoriaNew);
            }
            for (SeleccionAuxiliares seleccionAuxiliaresListNewSeleccionAuxiliares : seleccionAuxiliaresListNew) {
                if (!seleccionAuxiliaresListOld.contains(seleccionAuxiliaresListNewSeleccionAuxiliares)) {
                    EvaluacionAuxiliares oldEvaluacionAuxiliaresOfSeleccionAuxiliaresListNewSeleccionAuxiliares = seleccionAuxiliaresListNewSeleccionAuxiliares.getEvaluacionAuxiliares();
                    seleccionAuxiliaresListNewSeleccionAuxiliares.setEvaluacionAuxiliares(evaluacionAuxiliares);
                    seleccionAuxiliaresListNewSeleccionAuxiliares = em.merge(seleccionAuxiliaresListNewSeleccionAuxiliares);
                    if (oldEvaluacionAuxiliaresOfSeleccionAuxiliaresListNewSeleccionAuxiliares != null && !oldEvaluacionAuxiliaresOfSeleccionAuxiliaresListNewSeleccionAuxiliares.equals(evaluacionAuxiliares)) {
                        oldEvaluacionAuxiliaresOfSeleccionAuxiliaresListNewSeleccionAuxiliares.getSeleccionAuxiliaresList().remove(seleccionAuxiliaresListNewSeleccionAuxiliares);
                        oldEvaluacionAuxiliaresOfSeleccionAuxiliaresListNewSeleccionAuxiliares = em.merge(oldEvaluacionAuxiliaresOfSeleccionAuxiliaresListNewSeleccionAuxiliares);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = evaluacionAuxiliares.getEvaluacionAuxiliaresId();
                if (findEvaluacionAuxiliares(id) == null) {
                    throw new NonexistentEntityException("The evaluacionAuxiliares with id " + id + " no longer exists.");
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
            EvaluacionAuxiliares evaluacionAuxiliares;
            try {
                evaluacionAuxiliares = em.getReference(EvaluacionAuxiliares.class, id);
                evaluacionAuxiliares.getEvaluacionAuxiliaresId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evaluacionAuxiliares with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SeleccionAuxiliares> seleccionAuxiliaresListOrphanCheck = evaluacionAuxiliares.getSeleccionAuxiliaresList();
            for (SeleccionAuxiliares seleccionAuxiliaresListOrphanCheckSeleccionAuxiliares : seleccionAuxiliaresListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EvaluacionAuxiliares (" + evaluacionAuxiliares + ") cannot be destroyed since the SeleccionAuxiliares " + seleccionAuxiliaresListOrphanCheckSeleccionAuxiliares + " in its seleccionAuxiliaresList field has a non-nullable evaluacionAuxiliares field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Solicitante idSolicitante = evaluacionAuxiliares.getIdSolicitante();
            if (idSolicitante != null) {
                idSolicitante.getEvaluacionAuxiliaresList().remove(evaluacionAuxiliares);
                idSolicitante = em.merge(idSolicitante);
            }
            Convocatoria idConvocatoria = evaluacionAuxiliares.getIdConvocatoria();
            if (idConvocatoria != null) {
                idConvocatoria.setEvaluacionAuxiliares(null);
                idConvocatoria = em.merge(idConvocatoria);
            }
            em.remove(evaluacionAuxiliares);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EvaluacionAuxiliares> findEvaluacionAuxiliaresEntities() {
        return findEvaluacionAuxiliaresEntities(true, -1, -1);
    }

    public List<EvaluacionAuxiliares> findEvaluacionAuxiliaresEntities(int maxResults, int firstResult) {
        return findEvaluacionAuxiliaresEntities(false, maxResults, firstResult);
    }

    private List<EvaluacionAuxiliares> findEvaluacionAuxiliaresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EvaluacionAuxiliares.class));
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

    public EvaluacionAuxiliares findEvaluacionAuxiliares(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EvaluacionAuxiliares.class, id);
        } finally {
            em.close();
        }
    }

    public int getEvaluacionAuxiliaresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EvaluacionAuxiliares> rt = cq.from(EvaluacionAuxiliares.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
