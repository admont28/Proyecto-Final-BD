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
import Entities.Facultad;
import Entities.ConsejoCurricular;
import Entities.Directores;
import java.util.ArrayList;
import java.util.List;
import Entities.Solicitante;
import Entities.Auxiliar;
import Entities.Convocatoria;
import Entities.ProgramaAcademico;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class ProgramaAcademicoJpaController implements Serializable {

    public ProgramaAcademicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProgramaAcademico programaAcademico) throws PreexistingEntityException, Exception {
        if (programaAcademico.getDirectoresList() == null) {
            programaAcademico.setDirectoresList(new ArrayList<Directores>());
        }
        if (programaAcademico.getSolicitanteList() == null) {
            programaAcademico.setSolicitanteList(new ArrayList<Solicitante>());
        }
        if (programaAcademico.getAuxiliarList() == null) {
            programaAcademico.setAuxiliarList(new ArrayList<Auxiliar>());
        }
        if (programaAcademico.getConvocatoriaList() == null) {
            programaAcademico.setConvocatoriaList(new ArrayList<Convocatoria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Facultad idFacultad = programaAcademico.getIdFacultad();
            if (idFacultad != null) {
                idFacultad = em.getReference(idFacultad.getClass(), idFacultad.getFacultadId());
                programaAcademico.setIdFacultad(idFacultad);
            }
            ConsejoCurricular idConsejoCurricular = programaAcademico.getIdConsejoCurricular();
            if (idConsejoCurricular != null) {
                idConsejoCurricular = em.getReference(idConsejoCurricular.getClass(), idConsejoCurricular.getConsejoCurricularId());
                programaAcademico.setIdConsejoCurricular(idConsejoCurricular);
            }
            List<Directores> attachedDirectoresList = new ArrayList<Directores>();
            for (Directores directoresListDirectoresToAttach : programaAcademico.getDirectoresList()) {
                directoresListDirectoresToAttach = em.getReference(directoresListDirectoresToAttach.getClass(), directoresListDirectoresToAttach.getDirectoresPK());
                attachedDirectoresList.add(directoresListDirectoresToAttach);
            }
            programaAcademico.setDirectoresList(attachedDirectoresList);
            List<Solicitante> attachedSolicitanteList = new ArrayList<Solicitante>();
            for (Solicitante solicitanteListSolicitanteToAttach : programaAcademico.getSolicitanteList()) {
                solicitanteListSolicitanteToAttach = em.getReference(solicitanteListSolicitanteToAttach.getClass(), solicitanteListSolicitanteToAttach.getSolicitanteId());
                attachedSolicitanteList.add(solicitanteListSolicitanteToAttach);
            }
            programaAcademico.setSolicitanteList(attachedSolicitanteList);
            List<Auxiliar> attachedAuxiliarList = new ArrayList<Auxiliar>();
            for (Auxiliar auxiliarListAuxiliarToAttach : programaAcademico.getAuxiliarList()) {
                auxiliarListAuxiliarToAttach = em.getReference(auxiliarListAuxiliarToAttach.getClass(), auxiliarListAuxiliarToAttach.getAuxiliarId());
                attachedAuxiliarList.add(auxiliarListAuxiliarToAttach);
            }
            programaAcademico.setAuxiliarList(attachedAuxiliarList);
            List<Convocatoria> attachedConvocatoriaList = new ArrayList<Convocatoria>();
            for (Convocatoria convocatoriaListConvocatoriaToAttach : programaAcademico.getConvocatoriaList()) {
                convocatoriaListConvocatoriaToAttach = em.getReference(convocatoriaListConvocatoriaToAttach.getClass(), convocatoriaListConvocatoriaToAttach.getConvocatoriaId());
                attachedConvocatoriaList.add(convocatoriaListConvocatoriaToAttach);
            }
            programaAcademico.setConvocatoriaList(attachedConvocatoriaList);
            em.persist(programaAcademico);
            if (idFacultad != null) {
                idFacultad.getProgramaAcademicoList().add(programaAcademico);
                idFacultad = em.merge(idFacultad);
            }
            if (idConsejoCurricular != null) {
                idConsejoCurricular.getProgramaAcademicoList().add(programaAcademico);
                idConsejoCurricular = em.merge(idConsejoCurricular);
            }
            for (Directores directoresListDirectores : programaAcademico.getDirectoresList()) {
                ProgramaAcademico oldProgramaAcademicoOfDirectoresListDirectores = directoresListDirectores.getProgramaAcademico();
                directoresListDirectores.setProgramaAcademico(programaAcademico);
                directoresListDirectores = em.merge(directoresListDirectores);
                if (oldProgramaAcademicoOfDirectoresListDirectores != null) {
                    oldProgramaAcademicoOfDirectoresListDirectores.getDirectoresList().remove(directoresListDirectores);
                    oldProgramaAcademicoOfDirectoresListDirectores = em.merge(oldProgramaAcademicoOfDirectoresListDirectores);
                }
            }
            for (Solicitante solicitanteListSolicitante : programaAcademico.getSolicitanteList()) {
                ProgramaAcademico oldIdProgramaAcademicoOfSolicitanteListSolicitante = solicitanteListSolicitante.getIdProgramaAcademico();
                solicitanteListSolicitante.setIdProgramaAcademico(programaAcademico);
                solicitanteListSolicitante = em.merge(solicitanteListSolicitante);
                if (oldIdProgramaAcademicoOfSolicitanteListSolicitante != null) {
                    oldIdProgramaAcademicoOfSolicitanteListSolicitante.getSolicitanteList().remove(solicitanteListSolicitante);
                    oldIdProgramaAcademicoOfSolicitanteListSolicitante = em.merge(oldIdProgramaAcademicoOfSolicitanteListSolicitante);
                }
            }
            for (Auxiliar auxiliarListAuxiliar : programaAcademico.getAuxiliarList()) {
                ProgramaAcademico oldIdProgramaAcademicoOfAuxiliarListAuxiliar = auxiliarListAuxiliar.getIdProgramaAcademico();
                auxiliarListAuxiliar.setIdProgramaAcademico(programaAcademico);
                auxiliarListAuxiliar = em.merge(auxiliarListAuxiliar);
                if (oldIdProgramaAcademicoOfAuxiliarListAuxiliar != null) {
                    oldIdProgramaAcademicoOfAuxiliarListAuxiliar.getAuxiliarList().remove(auxiliarListAuxiliar);
                    oldIdProgramaAcademicoOfAuxiliarListAuxiliar = em.merge(oldIdProgramaAcademicoOfAuxiliarListAuxiliar);
                }
            }
            for (Convocatoria convocatoriaListConvocatoria : programaAcademico.getConvocatoriaList()) {
                ProgramaAcademico oldIdProgramaAcademicoOfConvocatoriaListConvocatoria = convocatoriaListConvocatoria.getIdProgramaAcademico();
                convocatoriaListConvocatoria.setIdProgramaAcademico(programaAcademico);
                convocatoriaListConvocatoria = em.merge(convocatoriaListConvocatoria);
                if (oldIdProgramaAcademicoOfConvocatoriaListConvocatoria != null) {
                    oldIdProgramaAcademicoOfConvocatoriaListConvocatoria.getConvocatoriaList().remove(convocatoriaListConvocatoria);
                    oldIdProgramaAcademicoOfConvocatoriaListConvocatoria = em.merge(oldIdProgramaAcademicoOfConvocatoriaListConvocatoria);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProgramaAcademico(programaAcademico.getProgramaAcademicoId()) != null) {
                throw new PreexistingEntityException("ProgramaAcademico " + programaAcademico + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProgramaAcademico programaAcademico) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProgramaAcademico persistentProgramaAcademico = em.find(ProgramaAcademico.class, programaAcademico.getProgramaAcademicoId());
            Facultad idFacultadOld = persistentProgramaAcademico.getIdFacultad();
            Facultad idFacultadNew = programaAcademico.getIdFacultad();
            ConsejoCurricular idConsejoCurricularOld = persistentProgramaAcademico.getIdConsejoCurricular();
            ConsejoCurricular idConsejoCurricularNew = programaAcademico.getIdConsejoCurricular();
            List<Directores> directoresListOld = persistentProgramaAcademico.getDirectoresList();
            List<Directores> directoresListNew = programaAcademico.getDirectoresList();
            List<Solicitante> solicitanteListOld = persistentProgramaAcademico.getSolicitanteList();
            List<Solicitante> solicitanteListNew = programaAcademico.getSolicitanteList();
            List<Auxiliar> auxiliarListOld = persistentProgramaAcademico.getAuxiliarList();
            List<Auxiliar> auxiliarListNew = programaAcademico.getAuxiliarList();
            List<Convocatoria> convocatoriaListOld = persistentProgramaAcademico.getConvocatoriaList();
            List<Convocatoria> convocatoriaListNew = programaAcademico.getConvocatoriaList();
            List<String> illegalOrphanMessages = null;
            for (Directores directoresListOldDirectores : directoresListOld) {
                if (!directoresListNew.contains(directoresListOldDirectores)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Directores " + directoresListOldDirectores + " since its programaAcademico field is not nullable.");
                }
            }
            for (Solicitante solicitanteListOldSolicitante : solicitanteListOld) {
                if (!solicitanteListNew.contains(solicitanteListOldSolicitante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Solicitante " + solicitanteListOldSolicitante + " since its idProgramaAcademico field is not nullable.");
                }
            }
            for (Auxiliar auxiliarListOldAuxiliar : auxiliarListOld) {
                if (!auxiliarListNew.contains(auxiliarListOldAuxiliar)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Auxiliar " + auxiliarListOldAuxiliar + " since its idProgramaAcademico field is not nullable.");
                }
            }
            for (Convocatoria convocatoriaListOldConvocatoria : convocatoriaListOld) {
                if (!convocatoriaListNew.contains(convocatoriaListOldConvocatoria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Convocatoria " + convocatoriaListOldConvocatoria + " since its idProgramaAcademico field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idFacultadNew != null) {
                idFacultadNew = em.getReference(idFacultadNew.getClass(), idFacultadNew.getFacultadId());
                programaAcademico.setIdFacultad(idFacultadNew);
            }
            if (idConsejoCurricularNew != null) {
                idConsejoCurricularNew = em.getReference(idConsejoCurricularNew.getClass(), idConsejoCurricularNew.getConsejoCurricularId());
                programaAcademico.setIdConsejoCurricular(idConsejoCurricularNew);
            }
            List<Directores> attachedDirectoresListNew = new ArrayList<Directores>();
            for (Directores directoresListNewDirectoresToAttach : directoresListNew) {
                directoresListNewDirectoresToAttach = em.getReference(directoresListNewDirectoresToAttach.getClass(), directoresListNewDirectoresToAttach.getDirectoresPK());
                attachedDirectoresListNew.add(directoresListNewDirectoresToAttach);
            }
            directoresListNew = attachedDirectoresListNew;
            programaAcademico.setDirectoresList(directoresListNew);
            List<Solicitante> attachedSolicitanteListNew = new ArrayList<Solicitante>();
            for (Solicitante solicitanteListNewSolicitanteToAttach : solicitanteListNew) {
                solicitanteListNewSolicitanteToAttach = em.getReference(solicitanteListNewSolicitanteToAttach.getClass(), solicitanteListNewSolicitanteToAttach.getSolicitanteId());
                attachedSolicitanteListNew.add(solicitanteListNewSolicitanteToAttach);
            }
            solicitanteListNew = attachedSolicitanteListNew;
            programaAcademico.setSolicitanteList(solicitanteListNew);
            List<Auxiliar> attachedAuxiliarListNew = new ArrayList<Auxiliar>();
            for (Auxiliar auxiliarListNewAuxiliarToAttach : auxiliarListNew) {
                auxiliarListNewAuxiliarToAttach = em.getReference(auxiliarListNewAuxiliarToAttach.getClass(), auxiliarListNewAuxiliarToAttach.getAuxiliarId());
                attachedAuxiliarListNew.add(auxiliarListNewAuxiliarToAttach);
            }
            auxiliarListNew = attachedAuxiliarListNew;
            programaAcademico.setAuxiliarList(auxiliarListNew);
            List<Convocatoria> attachedConvocatoriaListNew = new ArrayList<Convocatoria>();
            for (Convocatoria convocatoriaListNewConvocatoriaToAttach : convocatoriaListNew) {
                convocatoriaListNewConvocatoriaToAttach = em.getReference(convocatoriaListNewConvocatoriaToAttach.getClass(), convocatoriaListNewConvocatoriaToAttach.getConvocatoriaId());
                attachedConvocatoriaListNew.add(convocatoriaListNewConvocatoriaToAttach);
            }
            convocatoriaListNew = attachedConvocatoriaListNew;
            programaAcademico.setConvocatoriaList(convocatoriaListNew);
            programaAcademico = em.merge(programaAcademico);
            if (idFacultadOld != null && !idFacultadOld.equals(idFacultadNew)) {
                idFacultadOld.getProgramaAcademicoList().remove(programaAcademico);
                idFacultadOld = em.merge(idFacultadOld);
            }
            if (idFacultadNew != null && !idFacultadNew.equals(idFacultadOld)) {
                idFacultadNew.getProgramaAcademicoList().add(programaAcademico);
                idFacultadNew = em.merge(idFacultadNew);
            }
            if (idConsejoCurricularOld != null && !idConsejoCurricularOld.equals(idConsejoCurricularNew)) {
                idConsejoCurricularOld.getProgramaAcademicoList().remove(programaAcademico);
                idConsejoCurricularOld = em.merge(idConsejoCurricularOld);
            }
            if (idConsejoCurricularNew != null && !idConsejoCurricularNew.equals(idConsejoCurricularOld)) {
                idConsejoCurricularNew.getProgramaAcademicoList().add(programaAcademico);
                idConsejoCurricularNew = em.merge(idConsejoCurricularNew);
            }
            for (Directores directoresListNewDirectores : directoresListNew) {
                if (!directoresListOld.contains(directoresListNewDirectores)) {
                    ProgramaAcademico oldProgramaAcademicoOfDirectoresListNewDirectores = directoresListNewDirectores.getProgramaAcademico();
                    directoresListNewDirectores.setProgramaAcademico(programaAcademico);
                    directoresListNewDirectores = em.merge(directoresListNewDirectores);
                    if (oldProgramaAcademicoOfDirectoresListNewDirectores != null && !oldProgramaAcademicoOfDirectoresListNewDirectores.equals(programaAcademico)) {
                        oldProgramaAcademicoOfDirectoresListNewDirectores.getDirectoresList().remove(directoresListNewDirectores);
                        oldProgramaAcademicoOfDirectoresListNewDirectores = em.merge(oldProgramaAcademicoOfDirectoresListNewDirectores);
                    }
                }
            }
            for (Solicitante solicitanteListNewSolicitante : solicitanteListNew) {
                if (!solicitanteListOld.contains(solicitanteListNewSolicitante)) {
                    ProgramaAcademico oldIdProgramaAcademicoOfSolicitanteListNewSolicitante = solicitanteListNewSolicitante.getIdProgramaAcademico();
                    solicitanteListNewSolicitante.setIdProgramaAcademico(programaAcademico);
                    solicitanteListNewSolicitante = em.merge(solicitanteListNewSolicitante);
                    if (oldIdProgramaAcademicoOfSolicitanteListNewSolicitante != null && !oldIdProgramaAcademicoOfSolicitanteListNewSolicitante.equals(programaAcademico)) {
                        oldIdProgramaAcademicoOfSolicitanteListNewSolicitante.getSolicitanteList().remove(solicitanteListNewSolicitante);
                        oldIdProgramaAcademicoOfSolicitanteListNewSolicitante = em.merge(oldIdProgramaAcademicoOfSolicitanteListNewSolicitante);
                    }
                }
            }
            for (Auxiliar auxiliarListNewAuxiliar : auxiliarListNew) {
                if (!auxiliarListOld.contains(auxiliarListNewAuxiliar)) {
                    ProgramaAcademico oldIdProgramaAcademicoOfAuxiliarListNewAuxiliar = auxiliarListNewAuxiliar.getIdProgramaAcademico();
                    auxiliarListNewAuxiliar.setIdProgramaAcademico(programaAcademico);
                    auxiliarListNewAuxiliar = em.merge(auxiliarListNewAuxiliar);
                    if (oldIdProgramaAcademicoOfAuxiliarListNewAuxiliar != null && !oldIdProgramaAcademicoOfAuxiliarListNewAuxiliar.equals(programaAcademico)) {
                        oldIdProgramaAcademicoOfAuxiliarListNewAuxiliar.getAuxiliarList().remove(auxiliarListNewAuxiliar);
                        oldIdProgramaAcademicoOfAuxiliarListNewAuxiliar = em.merge(oldIdProgramaAcademicoOfAuxiliarListNewAuxiliar);
                    }
                }
            }
            for (Convocatoria convocatoriaListNewConvocatoria : convocatoriaListNew) {
                if (!convocatoriaListOld.contains(convocatoriaListNewConvocatoria)) {
                    ProgramaAcademico oldIdProgramaAcademicoOfConvocatoriaListNewConvocatoria = convocatoriaListNewConvocatoria.getIdProgramaAcademico();
                    convocatoriaListNewConvocatoria.setIdProgramaAcademico(programaAcademico);
                    convocatoriaListNewConvocatoria = em.merge(convocatoriaListNewConvocatoria);
                    if (oldIdProgramaAcademicoOfConvocatoriaListNewConvocatoria != null && !oldIdProgramaAcademicoOfConvocatoriaListNewConvocatoria.equals(programaAcademico)) {
                        oldIdProgramaAcademicoOfConvocatoriaListNewConvocatoria.getConvocatoriaList().remove(convocatoriaListNewConvocatoria);
                        oldIdProgramaAcademicoOfConvocatoriaListNewConvocatoria = em.merge(oldIdProgramaAcademicoOfConvocatoriaListNewConvocatoria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = programaAcademico.getProgramaAcademicoId();
                if (findProgramaAcademico(id) == null) {
                    throw new NonexistentEntityException("The programaAcademico with id " + id + " no longer exists.");
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
            ProgramaAcademico programaAcademico;
            try {
                programaAcademico = em.getReference(ProgramaAcademico.class, id);
                programaAcademico.getProgramaAcademicoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The programaAcademico with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Directores> directoresListOrphanCheck = programaAcademico.getDirectoresList();
            for (Directores directoresListOrphanCheckDirectores : directoresListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProgramaAcademico (" + programaAcademico + ") cannot be destroyed since the Directores " + directoresListOrphanCheckDirectores + " in its directoresList field has a non-nullable programaAcademico field.");
            }
            List<Solicitante> solicitanteListOrphanCheck = programaAcademico.getSolicitanteList();
            for (Solicitante solicitanteListOrphanCheckSolicitante : solicitanteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProgramaAcademico (" + programaAcademico + ") cannot be destroyed since the Solicitante " + solicitanteListOrphanCheckSolicitante + " in its solicitanteList field has a non-nullable idProgramaAcademico field.");
            }
            List<Auxiliar> auxiliarListOrphanCheck = programaAcademico.getAuxiliarList();
            for (Auxiliar auxiliarListOrphanCheckAuxiliar : auxiliarListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProgramaAcademico (" + programaAcademico + ") cannot be destroyed since the Auxiliar " + auxiliarListOrphanCheckAuxiliar + " in its auxiliarList field has a non-nullable idProgramaAcademico field.");
            }
            List<Convocatoria> convocatoriaListOrphanCheck = programaAcademico.getConvocatoriaList();
            for (Convocatoria convocatoriaListOrphanCheckConvocatoria : convocatoriaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProgramaAcademico (" + programaAcademico + ") cannot be destroyed since the Convocatoria " + convocatoriaListOrphanCheckConvocatoria + " in its convocatoriaList field has a non-nullable idProgramaAcademico field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Facultad idFacultad = programaAcademico.getIdFacultad();
            if (idFacultad != null) {
                idFacultad.getProgramaAcademicoList().remove(programaAcademico);
                idFacultad = em.merge(idFacultad);
            }
            ConsejoCurricular idConsejoCurricular = programaAcademico.getIdConsejoCurricular();
            if (idConsejoCurricular != null) {
                idConsejoCurricular.getProgramaAcademicoList().remove(programaAcademico);
                idConsejoCurricular = em.merge(idConsejoCurricular);
            }
            em.remove(programaAcademico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProgramaAcademico> findProgramaAcademicoEntities() {
        return findProgramaAcademicoEntities(true, -1, -1);
    }

    public List<ProgramaAcademico> findProgramaAcademicoEntities(int maxResults, int firstResult) {
        return findProgramaAcademicoEntities(false, maxResults, firstResult);
    }

    private List<ProgramaAcademico> findProgramaAcademicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProgramaAcademico.class));
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

    public ProgramaAcademico findProgramaAcademico(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProgramaAcademico.class, id);
        } finally {
            em.close();
        }
    }

    public int getProgramaAcademicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProgramaAcademico> rt = cq.from(ProgramaAcademico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
