/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Entities.ConsejoCurricular;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Secretaria;
import Entities.Solicitante;
import java.util.ArrayList;
import java.util.List;
import Entities.ProgramaAcademico;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class ConsejoCurricularJpaController implements Serializable {

    public ConsejoCurricularJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConsejoCurricular consejoCurricular) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (consejoCurricular.getSolicitanteList() == null) {
            consejoCurricular.setSolicitanteList(new ArrayList<Solicitante>());
        }
        if (consejoCurricular.getProgramaAcademicoList() == null) {
            consejoCurricular.setProgramaAcademicoList(new ArrayList<ProgramaAcademico>());
        }
        List<String> illegalOrphanMessages = null;
        Secretaria idSecretariaOrphanCheck = consejoCurricular.getIdSecretaria();
        if (idSecretariaOrphanCheck != null) {
            ConsejoCurricular oldConsejoCurricularOfIdSecretaria = idSecretariaOrphanCheck.getConsejoCurricular();
            if (oldConsejoCurricularOfIdSecretaria != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Secretaria " + idSecretariaOrphanCheck + " already has an item of type ConsejoCurricular whose idSecretaria column cannot be null. Please make another selection for the idSecretaria field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Secretaria idSecretaria = consejoCurricular.getIdSecretaria();
            if (idSecretaria != null) {
                idSecretaria = em.getReference(idSecretaria.getClass(), idSecretaria.getSecretariaId());
                consejoCurricular.setIdSecretaria(idSecretaria);
            }
            List<Solicitante> attachedSolicitanteList = new ArrayList<Solicitante>();
            for (Solicitante solicitanteListSolicitanteToAttach : consejoCurricular.getSolicitanteList()) {
                solicitanteListSolicitanteToAttach = em.getReference(solicitanteListSolicitanteToAttach.getClass(), solicitanteListSolicitanteToAttach.getSolicitanteId());
                attachedSolicitanteList.add(solicitanteListSolicitanteToAttach);
            }
            consejoCurricular.setSolicitanteList(attachedSolicitanteList);
            List<ProgramaAcademico> attachedProgramaAcademicoList = new ArrayList<ProgramaAcademico>();
            for (ProgramaAcademico programaAcademicoListProgramaAcademicoToAttach : consejoCurricular.getProgramaAcademicoList()) {
                programaAcademicoListProgramaAcademicoToAttach = em.getReference(programaAcademicoListProgramaAcademicoToAttach.getClass(), programaAcademicoListProgramaAcademicoToAttach.getProgramaAcademicoId());
                attachedProgramaAcademicoList.add(programaAcademicoListProgramaAcademicoToAttach);
            }
            consejoCurricular.setProgramaAcademicoList(attachedProgramaAcademicoList);
            em.persist(consejoCurricular);
            if (idSecretaria != null) {
                idSecretaria.setConsejoCurricular(consejoCurricular);
                idSecretaria = em.merge(idSecretaria);
            }
            for (Solicitante solicitanteListSolicitante : consejoCurricular.getSolicitanteList()) {
                ConsejoCurricular oldIdConsejoCurricularOfSolicitanteListSolicitante = solicitanteListSolicitante.getIdConsejoCurricular();
                solicitanteListSolicitante.setIdConsejoCurricular(consejoCurricular);
                solicitanteListSolicitante = em.merge(solicitanteListSolicitante);
                if (oldIdConsejoCurricularOfSolicitanteListSolicitante != null) {
                    oldIdConsejoCurricularOfSolicitanteListSolicitante.getSolicitanteList().remove(solicitanteListSolicitante);
                    oldIdConsejoCurricularOfSolicitanteListSolicitante = em.merge(oldIdConsejoCurricularOfSolicitanteListSolicitante);
                }
            }
            for (ProgramaAcademico programaAcademicoListProgramaAcademico : consejoCurricular.getProgramaAcademicoList()) {
                ConsejoCurricular oldIdConsejoCurricularOfProgramaAcademicoListProgramaAcademico = programaAcademicoListProgramaAcademico.getIdConsejoCurricular();
                programaAcademicoListProgramaAcademico.setIdConsejoCurricular(consejoCurricular);
                programaAcademicoListProgramaAcademico = em.merge(programaAcademicoListProgramaAcademico);
                if (oldIdConsejoCurricularOfProgramaAcademicoListProgramaAcademico != null) {
                    oldIdConsejoCurricularOfProgramaAcademicoListProgramaAcademico.getProgramaAcademicoList().remove(programaAcademicoListProgramaAcademico);
                    oldIdConsejoCurricularOfProgramaAcademicoListProgramaAcademico = em.merge(oldIdConsejoCurricularOfProgramaAcademicoListProgramaAcademico);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findConsejoCurricular(consejoCurricular.getConsejoCurricularId()) != null) {
                throw new PreexistingEntityException("ConsejoCurricular " + consejoCurricular + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConsejoCurricular consejoCurricular) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ConsejoCurricular persistentConsejoCurricular = em.find(ConsejoCurricular.class, consejoCurricular.getConsejoCurricularId());
            Secretaria idSecretariaOld = persistentConsejoCurricular.getIdSecretaria();
            Secretaria idSecretariaNew = consejoCurricular.getIdSecretaria();
            List<Solicitante> solicitanteListOld = persistentConsejoCurricular.getSolicitanteList();
            List<Solicitante> solicitanteListNew = consejoCurricular.getSolicitanteList();
            List<ProgramaAcademico> programaAcademicoListOld = persistentConsejoCurricular.getProgramaAcademicoList();
            List<ProgramaAcademico> programaAcademicoListNew = consejoCurricular.getProgramaAcademicoList();
            List<String> illegalOrphanMessages = null;
            if (idSecretariaNew != null && !idSecretariaNew.equals(idSecretariaOld)) {
                ConsejoCurricular oldConsejoCurricularOfIdSecretaria = idSecretariaNew.getConsejoCurricular();
                if (oldConsejoCurricularOfIdSecretaria != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Secretaria " + idSecretariaNew + " already has an item of type ConsejoCurricular whose idSecretaria column cannot be null. Please make another selection for the idSecretaria field.");
                }
            }
            for (ProgramaAcademico programaAcademicoListOldProgramaAcademico : programaAcademicoListOld) {
                if (!programaAcademicoListNew.contains(programaAcademicoListOldProgramaAcademico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProgramaAcademico " + programaAcademicoListOldProgramaAcademico + " since its idConsejoCurricular field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idSecretariaNew != null) {
                idSecretariaNew = em.getReference(idSecretariaNew.getClass(), idSecretariaNew.getSecretariaId());
                consejoCurricular.setIdSecretaria(idSecretariaNew);
            }
            List<Solicitante> attachedSolicitanteListNew = new ArrayList<Solicitante>();
            for (Solicitante solicitanteListNewSolicitanteToAttach : solicitanteListNew) {
                solicitanteListNewSolicitanteToAttach = em.getReference(solicitanteListNewSolicitanteToAttach.getClass(), solicitanteListNewSolicitanteToAttach.getSolicitanteId());
                attachedSolicitanteListNew.add(solicitanteListNewSolicitanteToAttach);
            }
            solicitanteListNew = attachedSolicitanteListNew;
            consejoCurricular.setSolicitanteList(solicitanteListNew);
            List<ProgramaAcademico> attachedProgramaAcademicoListNew = new ArrayList<ProgramaAcademico>();
            for (ProgramaAcademico programaAcademicoListNewProgramaAcademicoToAttach : programaAcademicoListNew) {
                programaAcademicoListNewProgramaAcademicoToAttach = em.getReference(programaAcademicoListNewProgramaAcademicoToAttach.getClass(), programaAcademicoListNewProgramaAcademicoToAttach.getProgramaAcademicoId());
                attachedProgramaAcademicoListNew.add(programaAcademicoListNewProgramaAcademicoToAttach);
            }
            programaAcademicoListNew = attachedProgramaAcademicoListNew;
            consejoCurricular.setProgramaAcademicoList(programaAcademicoListNew);
            consejoCurricular = em.merge(consejoCurricular);
            if (idSecretariaOld != null && !idSecretariaOld.equals(idSecretariaNew)) {
                idSecretariaOld.setConsejoCurricular(null);
                idSecretariaOld = em.merge(idSecretariaOld);
            }
            if (idSecretariaNew != null && !idSecretariaNew.equals(idSecretariaOld)) {
                idSecretariaNew.setConsejoCurricular(consejoCurricular);
                idSecretariaNew = em.merge(idSecretariaNew);
            }
            for (Solicitante solicitanteListOldSolicitante : solicitanteListOld) {
                if (!solicitanteListNew.contains(solicitanteListOldSolicitante)) {
                    solicitanteListOldSolicitante.setIdConsejoCurricular(null);
                    solicitanteListOldSolicitante = em.merge(solicitanteListOldSolicitante);
                }
            }
            for (Solicitante solicitanteListNewSolicitante : solicitanteListNew) {
                if (!solicitanteListOld.contains(solicitanteListNewSolicitante)) {
                    ConsejoCurricular oldIdConsejoCurricularOfSolicitanteListNewSolicitante = solicitanteListNewSolicitante.getIdConsejoCurricular();
                    solicitanteListNewSolicitante.setIdConsejoCurricular(consejoCurricular);
                    solicitanteListNewSolicitante = em.merge(solicitanteListNewSolicitante);
                    if (oldIdConsejoCurricularOfSolicitanteListNewSolicitante != null && !oldIdConsejoCurricularOfSolicitanteListNewSolicitante.equals(consejoCurricular)) {
                        oldIdConsejoCurricularOfSolicitanteListNewSolicitante.getSolicitanteList().remove(solicitanteListNewSolicitante);
                        oldIdConsejoCurricularOfSolicitanteListNewSolicitante = em.merge(oldIdConsejoCurricularOfSolicitanteListNewSolicitante);
                    }
                }
            }
            for (ProgramaAcademico programaAcademicoListNewProgramaAcademico : programaAcademicoListNew) {
                if (!programaAcademicoListOld.contains(programaAcademicoListNewProgramaAcademico)) {
                    ConsejoCurricular oldIdConsejoCurricularOfProgramaAcademicoListNewProgramaAcademico = programaAcademicoListNewProgramaAcademico.getIdConsejoCurricular();
                    programaAcademicoListNewProgramaAcademico.setIdConsejoCurricular(consejoCurricular);
                    programaAcademicoListNewProgramaAcademico = em.merge(programaAcademicoListNewProgramaAcademico);
                    if (oldIdConsejoCurricularOfProgramaAcademicoListNewProgramaAcademico != null && !oldIdConsejoCurricularOfProgramaAcademicoListNewProgramaAcademico.equals(consejoCurricular)) {
                        oldIdConsejoCurricularOfProgramaAcademicoListNewProgramaAcademico.getProgramaAcademicoList().remove(programaAcademicoListNewProgramaAcademico);
                        oldIdConsejoCurricularOfProgramaAcademicoListNewProgramaAcademico = em.merge(oldIdConsejoCurricularOfProgramaAcademicoListNewProgramaAcademico);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = consejoCurricular.getConsejoCurricularId();
                if (findConsejoCurricular(id) == null) {
                    throw new NonexistentEntityException("The consejoCurricular with id " + id + " no longer exists.");
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
            ConsejoCurricular consejoCurricular;
            try {
                consejoCurricular = em.getReference(ConsejoCurricular.class, id);
                consejoCurricular.getConsejoCurricularId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The consejoCurricular with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ProgramaAcademico> programaAcademicoListOrphanCheck = consejoCurricular.getProgramaAcademicoList();
            for (ProgramaAcademico programaAcademicoListOrphanCheckProgramaAcademico : programaAcademicoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ConsejoCurricular (" + consejoCurricular + ") cannot be destroyed since the ProgramaAcademico " + programaAcademicoListOrphanCheckProgramaAcademico + " in its programaAcademicoList field has a non-nullable idConsejoCurricular field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Secretaria idSecretaria = consejoCurricular.getIdSecretaria();
            if (idSecretaria != null) {
                idSecretaria.setConsejoCurricular(null);
                idSecretaria = em.merge(idSecretaria);
            }
            List<Solicitante> solicitanteList = consejoCurricular.getSolicitanteList();
            for (Solicitante solicitanteListSolicitante : solicitanteList) {
                solicitanteListSolicitante.setIdConsejoCurricular(null);
                solicitanteListSolicitante = em.merge(solicitanteListSolicitante);
            }
            em.remove(consejoCurricular);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ConsejoCurricular> findConsejoCurricularEntities() {
        return findConsejoCurricularEntities(true, -1, -1);
    }

    public List<ConsejoCurricular> findConsejoCurricularEntities(int maxResults, int firstResult) {
        return findConsejoCurricularEntities(false, maxResults, firstResult);
    }

    private List<ConsejoCurricular> findConsejoCurricularEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConsejoCurricular.class));
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

    public ConsejoCurricular findConsejoCurricular(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConsejoCurricular.class, id);
        } finally {
            em.close();
        }
    }

    public int getConsejoCurricularCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ConsejoCurricular> rt = cq.from(ConsejoCurricular.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
