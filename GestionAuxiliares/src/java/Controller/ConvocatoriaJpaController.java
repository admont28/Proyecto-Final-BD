/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Entities.Convocatoria;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.TipoAuxiliar;
import Entities.SolicitudAuxiliares;
import Entities.ProgramaAcademico;
import Entities.EvaluacionAuxiliares;
import Entities.Requisito;
import java.util.ArrayList;
import java.util.List;
import Entities.InscripcionConvocatoria;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class ConvocatoriaJpaController implements Serializable {

    public ConvocatoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Convocatoria convocatoria) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (convocatoria.getRequisitoList() == null) {
            convocatoria.setRequisitoList(new ArrayList<Requisito>());
        }
        if (convocatoria.getInscripcionConvocatoriaList() == null) {
            convocatoria.setInscripcionConvocatoriaList(new ArrayList<InscripcionConvocatoria>());
        }
        List<String> illegalOrphanMessages = null;
        SolicitudAuxiliares idSolicitudAuxiliaresOrphanCheck = convocatoria.getIdSolicitudAuxiliares();
        if (idSolicitudAuxiliaresOrphanCheck != null) {
            Convocatoria oldConvocatoriaOfIdSolicitudAuxiliares = idSolicitudAuxiliaresOrphanCheck.getConvocatoria();
            if (oldConvocatoriaOfIdSolicitudAuxiliares != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The SolicitudAuxiliares " + idSolicitudAuxiliaresOrphanCheck + " already has an item of type Convocatoria whose idSolicitudAuxiliares column cannot be null. Please make another selection for the idSolicitudAuxiliares field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoAuxiliar idTipoAuxiliar = convocatoria.getIdTipoAuxiliar();
            if (idTipoAuxiliar != null) {
                idTipoAuxiliar = em.getReference(idTipoAuxiliar.getClass(), idTipoAuxiliar.getTipoAuxiliarId());
                convocatoria.setIdTipoAuxiliar(idTipoAuxiliar);
            }
            SolicitudAuxiliares idSolicitudAuxiliares = convocatoria.getIdSolicitudAuxiliares();
            if (idSolicitudAuxiliares != null) {
                idSolicitudAuxiliares = em.getReference(idSolicitudAuxiliares.getClass(), idSolicitudAuxiliares.getSolicitudAuxiliaresId());
                convocatoria.setIdSolicitudAuxiliares(idSolicitudAuxiliares);
            }
            ProgramaAcademico idProgramaAcademico = convocatoria.getIdProgramaAcademico();
            if (idProgramaAcademico != null) {
                idProgramaAcademico = em.getReference(idProgramaAcademico.getClass(), idProgramaAcademico.getProgramaAcademicoId());
                convocatoria.setIdProgramaAcademico(idProgramaAcademico);
            }
            EvaluacionAuxiliares evaluacionAuxiliares = convocatoria.getEvaluacionAuxiliares();
            if (evaluacionAuxiliares != null) {
                evaluacionAuxiliares = em.getReference(evaluacionAuxiliares.getClass(), evaluacionAuxiliares.getEvaluacionAuxiliaresId());
                convocatoria.setEvaluacionAuxiliares(evaluacionAuxiliares);
            }
            List<Requisito> attachedRequisitoList = new ArrayList<Requisito>();
            for (Requisito requisitoListRequisitoToAttach : convocatoria.getRequisitoList()) {
                requisitoListRequisitoToAttach = em.getReference(requisitoListRequisitoToAttach.getClass(), requisitoListRequisitoToAttach.getRequisitoId());
                attachedRequisitoList.add(requisitoListRequisitoToAttach);
            }
            convocatoria.setRequisitoList(attachedRequisitoList);
            List<InscripcionConvocatoria> attachedInscripcionConvocatoriaList = new ArrayList<InscripcionConvocatoria>();
            for (InscripcionConvocatoria inscripcionConvocatoriaListInscripcionConvocatoriaToAttach : convocatoria.getInscripcionConvocatoriaList()) {
                inscripcionConvocatoriaListInscripcionConvocatoriaToAttach = em.getReference(inscripcionConvocatoriaListInscripcionConvocatoriaToAttach.getClass(), inscripcionConvocatoriaListInscripcionConvocatoriaToAttach.getInscripcionConvocatoriaPK());
                attachedInscripcionConvocatoriaList.add(inscripcionConvocatoriaListInscripcionConvocatoriaToAttach);
            }
            convocatoria.setInscripcionConvocatoriaList(attachedInscripcionConvocatoriaList);
            em.persist(convocatoria);
            if (idTipoAuxiliar != null) {
                idTipoAuxiliar.getConvocatoriaList().add(convocatoria);
                idTipoAuxiliar = em.merge(idTipoAuxiliar);
            }
            if (idSolicitudAuxiliares != null) {
                idSolicitudAuxiliares.setConvocatoria(convocatoria);
                idSolicitudAuxiliares = em.merge(idSolicitudAuxiliares);
            }
            if (idProgramaAcademico != null) {
                idProgramaAcademico.getConvocatoriaList().add(convocatoria);
                idProgramaAcademico = em.merge(idProgramaAcademico);
            }
            if (evaluacionAuxiliares != null) {
                Convocatoria oldIdConvocatoriaOfEvaluacionAuxiliares = evaluacionAuxiliares.getIdConvocatoria();
                if (oldIdConvocatoriaOfEvaluacionAuxiliares != null) {
                    oldIdConvocatoriaOfEvaluacionAuxiliares.setEvaluacionAuxiliares(null);
                    oldIdConvocatoriaOfEvaluacionAuxiliares = em.merge(oldIdConvocatoriaOfEvaluacionAuxiliares);
                }
                evaluacionAuxiliares.setIdConvocatoria(convocatoria);
                evaluacionAuxiliares = em.merge(evaluacionAuxiliares);
            }
            for (Requisito requisitoListRequisito : convocatoria.getRequisitoList()) {
                requisitoListRequisito.getConvocatoriaList().add(convocatoria);
                requisitoListRequisito = em.merge(requisitoListRequisito);
            }
            for (InscripcionConvocatoria inscripcionConvocatoriaListInscripcionConvocatoria : convocatoria.getInscripcionConvocatoriaList()) {
                Convocatoria oldConvocatoriaOfInscripcionConvocatoriaListInscripcionConvocatoria = inscripcionConvocatoriaListInscripcionConvocatoria.getConvocatoria();
                inscripcionConvocatoriaListInscripcionConvocatoria.setConvocatoria(convocatoria);
                inscripcionConvocatoriaListInscripcionConvocatoria = em.merge(inscripcionConvocatoriaListInscripcionConvocatoria);
                if (oldConvocatoriaOfInscripcionConvocatoriaListInscripcionConvocatoria != null) {
                    oldConvocatoriaOfInscripcionConvocatoriaListInscripcionConvocatoria.getInscripcionConvocatoriaList().remove(inscripcionConvocatoriaListInscripcionConvocatoria);
                    oldConvocatoriaOfInscripcionConvocatoriaListInscripcionConvocatoria = em.merge(oldConvocatoriaOfInscripcionConvocatoriaListInscripcionConvocatoria);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findConvocatoria(convocatoria.getConvocatoriaId()) != null) {
                throw new PreexistingEntityException("Convocatoria " + convocatoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Convocatoria convocatoria) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Convocatoria persistentConvocatoria = em.find(Convocatoria.class, convocatoria.getConvocatoriaId());
            TipoAuxiliar idTipoAuxiliarOld = persistentConvocatoria.getIdTipoAuxiliar();
            TipoAuxiliar idTipoAuxiliarNew = convocatoria.getIdTipoAuxiliar();
            SolicitudAuxiliares idSolicitudAuxiliaresOld = persistentConvocatoria.getIdSolicitudAuxiliares();
            SolicitudAuxiliares idSolicitudAuxiliaresNew = convocatoria.getIdSolicitudAuxiliares();
            ProgramaAcademico idProgramaAcademicoOld = persistentConvocatoria.getIdProgramaAcademico();
            ProgramaAcademico idProgramaAcademicoNew = convocatoria.getIdProgramaAcademico();
            EvaluacionAuxiliares evaluacionAuxiliaresOld = persistentConvocatoria.getEvaluacionAuxiliares();
            EvaluacionAuxiliares evaluacionAuxiliaresNew = convocatoria.getEvaluacionAuxiliares();
            List<Requisito> requisitoListOld = persistentConvocatoria.getRequisitoList();
            List<Requisito> requisitoListNew = convocatoria.getRequisitoList();
            List<InscripcionConvocatoria> inscripcionConvocatoriaListOld = persistentConvocatoria.getInscripcionConvocatoriaList();
            List<InscripcionConvocatoria> inscripcionConvocatoriaListNew = convocatoria.getInscripcionConvocatoriaList();
            List<String> illegalOrphanMessages = null;
            if (idSolicitudAuxiliaresNew != null && !idSolicitudAuxiliaresNew.equals(idSolicitudAuxiliaresOld)) {
                Convocatoria oldConvocatoriaOfIdSolicitudAuxiliares = idSolicitudAuxiliaresNew.getConvocatoria();
                if (oldConvocatoriaOfIdSolicitudAuxiliares != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The SolicitudAuxiliares " + idSolicitudAuxiliaresNew + " already has an item of type Convocatoria whose idSolicitudAuxiliares column cannot be null. Please make another selection for the idSolicitudAuxiliares field.");
                }
            }
            if (evaluacionAuxiliaresOld != null && !evaluacionAuxiliaresOld.equals(evaluacionAuxiliaresNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain EvaluacionAuxiliares " + evaluacionAuxiliaresOld + " since its idConvocatoria field is not nullable.");
            }
            for (InscripcionConvocatoria inscripcionConvocatoriaListOldInscripcionConvocatoria : inscripcionConvocatoriaListOld) {
                if (!inscripcionConvocatoriaListNew.contains(inscripcionConvocatoriaListOldInscripcionConvocatoria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InscripcionConvocatoria " + inscripcionConvocatoriaListOldInscripcionConvocatoria + " since its convocatoria field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idTipoAuxiliarNew != null) {
                idTipoAuxiliarNew = em.getReference(idTipoAuxiliarNew.getClass(), idTipoAuxiliarNew.getTipoAuxiliarId());
                convocatoria.setIdTipoAuxiliar(idTipoAuxiliarNew);
            }
            if (idSolicitudAuxiliaresNew != null) {
                idSolicitudAuxiliaresNew = em.getReference(idSolicitudAuxiliaresNew.getClass(), idSolicitudAuxiliaresNew.getSolicitudAuxiliaresId());
                convocatoria.setIdSolicitudAuxiliares(idSolicitudAuxiliaresNew);
            }
            if (idProgramaAcademicoNew != null) {
                idProgramaAcademicoNew = em.getReference(idProgramaAcademicoNew.getClass(), idProgramaAcademicoNew.getProgramaAcademicoId());
                convocatoria.setIdProgramaAcademico(idProgramaAcademicoNew);
            }
            if (evaluacionAuxiliaresNew != null) {
                evaluacionAuxiliaresNew = em.getReference(evaluacionAuxiliaresNew.getClass(), evaluacionAuxiliaresNew.getEvaluacionAuxiliaresId());
                convocatoria.setEvaluacionAuxiliares(evaluacionAuxiliaresNew);
            }
            List<Requisito> attachedRequisitoListNew = new ArrayList<Requisito>();
            for (Requisito requisitoListNewRequisitoToAttach : requisitoListNew) {
                requisitoListNewRequisitoToAttach = em.getReference(requisitoListNewRequisitoToAttach.getClass(), requisitoListNewRequisitoToAttach.getRequisitoId());
                attachedRequisitoListNew.add(requisitoListNewRequisitoToAttach);
            }
            requisitoListNew = attachedRequisitoListNew;
            convocatoria.setRequisitoList(requisitoListNew);
            List<InscripcionConvocatoria> attachedInscripcionConvocatoriaListNew = new ArrayList<InscripcionConvocatoria>();
            for (InscripcionConvocatoria inscripcionConvocatoriaListNewInscripcionConvocatoriaToAttach : inscripcionConvocatoriaListNew) {
                inscripcionConvocatoriaListNewInscripcionConvocatoriaToAttach = em.getReference(inscripcionConvocatoriaListNewInscripcionConvocatoriaToAttach.getClass(), inscripcionConvocatoriaListNewInscripcionConvocatoriaToAttach.getInscripcionConvocatoriaPK());
                attachedInscripcionConvocatoriaListNew.add(inscripcionConvocatoriaListNewInscripcionConvocatoriaToAttach);
            }
            inscripcionConvocatoriaListNew = attachedInscripcionConvocatoriaListNew;
            convocatoria.setInscripcionConvocatoriaList(inscripcionConvocatoriaListNew);
            convocatoria = em.merge(convocatoria);
            if (idTipoAuxiliarOld != null && !idTipoAuxiliarOld.equals(idTipoAuxiliarNew)) {
                idTipoAuxiliarOld.getConvocatoriaList().remove(convocatoria);
                idTipoAuxiliarOld = em.merge(idTipoAuxiliarOld);
            }
            if (idTipoAuxiliarNew != null && !idTipoAuxiliarNew.equals(idTipoAuxiliarOld)) {
                idTipoAuxiliarNew.getConvocatoriaList().add(convocatoria);
                idTipoAuxiliarNew = em.merge(idTipoAuxiliarNew);
            }
            if (idSolicitudAuxiliaresOld != null && !idSolicitudAuxiliaresOld.equals(idSolicitudAuxiliaresNew)) {
                idSolicitudAuxiliaresOld.setConvocatoria(null);
                idSolicitudAuxiliaresOld = em.merge(idSolicitudAuxiliaresOld);
            }
            if (idSolicitudAuxiliaresNew != null && !idSolicitudAuxiliaresNew.equals(idSolicitudAuxiliaresOld)) {
                idSolicitudAuxiliaresNew.setConvocatoria(convocatoria);
                idSolicitudAuxiliaresNew = em.merge(idSolicitudAuxiliaresNew);
            }
            if (idProgramaAcademicoOld != null && !idProgramaAcademicoOld.equals(idProgramaAcademicoNew)) {
                idProgramaAcademicoOld.getConvocatoriaList().remove(convocatoria);
                idProgramaAcademicoOld = em.merge(idProgramaAcademicoOld);
            }
            if (idProgramaAcademicoNew != null && !idProgramaAcademicoNew.equals(idProgramaAcademicoOld)) {
                idProgramaAcademicoNew.getConvocatoriaList().add(convocatoria);
                idProgramaAcademicoNew = em.merge(idProgramaAcademicoNew);
            }
            if (evaluacionAuxiliaresNew != null && !evaluacionAuxiliaresNew.equals(evaluacionAuxiliaresOld)) {
                Convocatoria oldIdConvocatoriaOfEvaluacionAuxiliares = evaluacionAuxiliaresNew.getIdConvocatoria();
                if (oldIdConvocatoriaOfEvaluacionAuxiliares != null) {
                    oldIdConvocatoriaOfEvaluacionAuxiliares.setEvaluacionAuxiliares(null);
                    oldIdConvocatoriaOfEvaluacionAuxiliares = em.merge(oldIdConvocatoriaOfEvaluacionAuxiliares);
                }
                evaluacionAuxiliaresNew.setIdConvocatoria(convocatoria);
                evaluacionAuxiliaresNew = em.merge(evaluacionAuxiliaresNew);
            }
            for (Requisito requisitoListOldRequisito : requisitoListOld) {
                if (!requisitoListNew.contains(requisitoListOldRequisito)) {
                    requisitoListOldRequisito.getConvocatoriaList().remove(convocatoria);
                    requisitoListOldRequisito = em.merge(requisitoListOldRequisito);
                }
            }
            for (Requisito requisitoListNewRequisito : requisitoListNew) {
                if (!requisitoListOld.contains(requisitoListNewRequisito)) {
                    requisitoListNewRequisito.getConvocatoriaList().add(convocatoria);
                    requisitoListNewRequisito = em.merge(requisitoListNewRequisito);
                }
            }
            for (InscripcionConvocatoria inscripcionConvocatoriaListNewInscripcionConvocatoria : inscripcionConvocatoriaListNew) {
                if (!inscripcionConvocatoriaListOld.contains(inscripcionConvocatoriaListNewInscripcionConvocatoria)) {
                    Convocatoria oldConvocatoriaOfInscripcionConvocatoriaListNewInscripcionConvocatoria = inscripcionConvocatoriaListNewInscripcionConvocatoria.getConvocatoria();
                    inscripcionConvocatoriaListNewInscripcionConvocatoria.setConvocatoria(convocatoria);
                    inscripcionConvocatoriaListNewInscripcionConvocatoria = em.merge(inscripcionConvocatoriaListNewInscripcionConvocatoria);
                    if (oldConvocatoriaOfInscripcionConvocatoriaListNewInscripcionConvocatoria != null && !oldConvocatoriaOfInscripcionConvocatoriaListNewInscripcionConvocatoria.equals(convocatoria)) {
                        oldConvocatoriaOfInscripcionConvocatoriaListNewInscripcionConvocatoria.getInscripcionConvocatoriaList().remove(inscripcionConvocatoriaListNewInscripcionConvocatoria);
                        oldConvocatoriaOfInscripcionConvocatoriaListNewInscripcionConvocatoria = em.merge(oldConvocatoriaOfInscripcionConvocatoriaListNewInscripcionConvocatoria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = convocatoria.getConvocatoriaId();
                if (findConvocatoria(id) == null) {
                    throw new NonexistentEntityException("The convocatoria with id " + id + " no longer exists.");
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
            Convocatoria convocatoria;
            try {
                convocatoria = em.getReference(Convocatoria.class, id);
                convocatoria.getConvocatoriaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The convocatoria with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            EvaluacionAuxiliares evaluacionAuxiliaresOrphanCheck = convocatoria.getEvaluacionAuxiliares();
            if (evaluacionAuxiliaresOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Convocatoria (" + convocatoria + ") cannot be destroyed since the EvaluacionAuxiliares " + evaluacionAuxiliaresOrphanCheck + " in its evaluacionAuxiliares field has a non-nullable idConvocatoria field.");
            }
            List<InscripcionConvocatoria> inscripcionConvocatoriaListOrphanCheck = convocatoria.getInscripcionConvocatoriaList();
            for (InscripcionConvocatoria inscripcionConvocatoriaListOrphanCheckInscripcionConvocatoria : inscripcionConvocatoriaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Convocatoria (" + convocatoria + ") cannot be destroyed since the InscripcionConvocatoria " + inscripcionConvocatoriaListOrphanCheckInscripcionConvocatoria + " in its inscripcionConvocatoriaList field has a non-nullable convocatoria field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TipoAuxiliar idTipoAuxiliar = convocatoria.getIdTipoAuxiliar();
            if (idTipoAuxiliar != null) {
                idTipoAuxiliar.getConvocatoriaList().remove(convocatoria);
                idTipoAuxiliar = em.merge(idTipoAuxiliar);
            }
            SolicitudAuxiliares idSolicitudAuxiliares = convocatoria.getIdSolicitudAuxiliares();
            if (idSolicitudAuxiliares != null) {
                idSolicitudAuxiliares.setConvocatoria(null);
                idSolicitudAuxiliares = em.merge(idSolicitudAuxiliares);
            }
            ProgramaAcademico idProgramaAcademico = convocatoria.getIdProgramaAcademico();
            if (idProgramaAcademico != null) {
                idProgramaAcademico.getConvocatoriaList().remove(convocatoria);
                idProgramaAcademico = em.merge(idProgramaAcademico);
            }
            List<Requisito> requisitoList = convocatoria.getRequisitoList();
            for (Requisito requisitoListRequisito : requisitoList) {
                requisitoListRequisito.getConvocatoriaList().remove(convocatoria);
                requisitoListRequisito = em.merge(requisitoListRequisito);
            }
            em.remove(convocatoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Convocatoria> findConvocatoriaEntities() {
        return findConvocatoriaEntities(true, -1, -1);
    }

    public List<Convocatoria> findConvocatoriaEntities(int maxResults, int firstResult) {
        return findConvocatoriaEntities(false, maxResults, firstResult);
    }

    private List<Convocatoria> findConvocatoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Convocatoria.class));
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

    public Convocatoria findConvocatoria(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Convocatoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getConvocatoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Convocatoria> rt = cq.from(Convocatoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
