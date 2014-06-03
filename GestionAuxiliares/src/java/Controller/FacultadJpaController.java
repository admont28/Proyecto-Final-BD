/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Entities.Facultad;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Universidad;
import Entities.ProgramaAcademico;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class FacultadJpaController implements Serializable {

    public FacultadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Facultad facultad) throws PreexistingEntityException, Exception {
        if (facultad.getProgramaAcademicoList() == null) {
            facultad.setProgramaAcademicoList(new ArrayList<ProgramaAcademico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Universidad idUniversidad = facultad.getIdUniversidad();
            if (idUniversidad != null) {
                idUniversidad = em.getReference(idUniversidad.getClass(), idUniversidad.getUniversidadId());
                facultad.setIdUniversidad(idUniversidad);
            }
            List<ProgramaAcademico> attachedProgramaAcademicoList = new ArrayList<ProgramaAcademico>();
            for (ProgramaAcademico programaAcademicoListProgramaAcademicoToAttach : facultad.getProgramaAcademicoList()) {
                programaAcademicoListProgramaAcademicoToAttach = em.getReference(programaAcademicoListProgramaAcademicoToAttach.getClass(), programaAcademicoListProgramaAcademicoToAttach.getProgramaAcademicoId());
                attachedProgramaAcademicoList.add(programaAcademicoListProgramaAcademicoToAttach);
            }
            facultad.setProgramaAcademicoList(attachedProgramaAcademicoList);
            em.persist(facultad);
            if (idUniversidad != null) {
                idUniversidad.getFacultadList().add(facultad);
                idUniversidad = em.merge(idUniversidad);
            }
            for (ProgramaAcademico programaAcademicoListProgramaAcademico : facultad.getProgramaAcademicoList()) {
                Facultad oldIdFacultadOfProgramaAcademicoListProgramaAcademico = programaAcademicoListProgramaAcademico.getIdFacultad();
                programaAcademicoListProgramaAcademico.setIdFacultad(facultad);
                programaAcademicoListProgramaAcademico = em.merge(programaAcademicoListProgramaAcademico);
                if (oldIdFacultadOfProgramaAcademicoListProgramaAcademico != null) {
                    oldIdFacultadOfProgramaAcademicoListProgramaAcademico.getProgramaAcademicoList().remove(programaAcademicoListProgramaAcademico);
                    oldIdFacultadOfProgramaAcademicoListProgramaAcademico = em.merge(oldIdFacultadOfProgramaAcademicoListProgramaAcademico);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFacultad(facultad.getFacultadId()) != null) {
                throw new PreexistingEntityException("Facultad " + facultad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Facultad facultad) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Facultad persistentFacultad = em.find(Facultad.class, facultad.getFacultadId());
            Universidad idUniversidadOld = persistentFacultad.getIdUniversidad();
            Universidad idUniversidadNew = facultad.getIdUniversidad();
            List<ProgramaAcademico> programaAcademicoListOld = persistentFacultad.getProgramaAcademicoList();
            List<ProgramaAcademico> programaAcademicoListNew = facultad.getProgramaAcademicoList();
            List<String> illegalOrphanMessages = null;
            for (ProgramaAcademico programaAcademicoListOldProgramaAcademico : programaAcademicoListOld) {
                if (!programaAcademicoListNew.contains(programaAcademicoListOldProgramaAcademico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProgramaAcademico " + programaAcademicoListOldProgramaAcademico + " since its idFacultad field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idUniversidadNew != null) {
                idUniversidadNew = em.getReference(idUniversidadNew.getClass(), idUniversidadNew.getUniversidadId());
                facultad.setIdUniversidad(idUniversidadNew);
            }
            List<ProgramaAcademico> attachedProgramaAcademicoListNew = new ArrayList<ProgramaAcademico>();
            for (ProgramaAcademico programaAcademicoListNewProgramaAcademicoToAttach : programaAcademicoListNew) {
                programaAcademicoListNewProgramaAcademicoToAttach = em.getReference(programaAcademicoListNewProgramaAcademicoToAttach.getClass(), programaAcademicoListNewProgramaAcademicoToAttach.getProgramaAcademicoId());
                attachedProgramaAcademicoListNew.add(programaAcademicoListNewProgramaAcademicoToAttach);
            }
            programaAcademicoListNew = attachedProgramaAcademicoListNew;
            facultad.setProgramaAcademicoList(programaAcademicoListNew);
            facultad = em.merge(facultad);
            if (idUniversidadOld != null && !idUniversidadOld.equals(idUniversidadNew)) {
                idUniversidadOld.getFacultadList().remove(facultad);
                idUniversidadOld = em.merge(idUniversidadOld);
            }
            if (idUniversidadNew != null && !idUniversidadNew.equals(idUniversidadOld)) {
                idUniversidadNew.getFacultadList().add(facultad);
                idUniversidadNew = em.merge(idUniversidadNew);
            }
            for (ProgramaAcademico programaAcademicoListNewProgramaAcademico : programaAcademicoListNew) {
                if (!programaAcademicoListOld.contains(programaAcademicoListNewProgramaAcademico)) {
                    Facultad oldIdFacultadOfProgramaAcademicoListNewProgramaAcademico = programaAcademicoListNewProgramaAcademico.getIdFacultad();
                    programaAcademicoListNewProgramaAcademico.setIdFacultad(facultad);
                    programaAcademicoListNewProgramaAcademico = em.merge(programaAcademicoListNewProgramaAcademico);
                    if (oldIdFacultadOfProgramaAcademicoListNewProgramaAcademico != null && !oldIdFacultadOfProgramaAcademicoListNewProgramaAcademico.equals(facultad)) {
                        oldIdFacultadOfProgramaAcademicoListNewProgramaAcademico.getProgramaAcademicoList().remove(programaAcademicoListNewProgramaAcademico);
                        oldIdFacultadOfProgramaAcademicoListNewProgramaAcademico = em.merge(oldIdFacultadOfProgramaAcademicoListNewProgramaAcademico);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = facultad.getFacultadId();
                if (findFacultad(id) == null) {
                    throw new NonexistentEntityException("The facultad with id " + id + " no longer exists.");
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
            Facultad facultad;
            try {
                facultad = em.getReference(Facultad.class, id);
                facultad.getFacultadId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facultad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ProgramaAcademico> programaAcademicoListOrphanCheck = facultad.getProgramaAcademicoList();
            for (ProgramaAcademico programaAcademicoListOrphanCheckProgramaAcademico : programaAcademicoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Facultad (" + facultad + ") cannot be destroyed since the ProgramaAcademico " + programaAcademicoListOrphanCheckProgramaAcademico + " in its programaAcademicoList field has a non-nullable idFacultad field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Universidad idUniversidad = facultad.getIdUniversidad();
            if (idUniversidad != null) {
                idUniversidad.getFacultadList().remove(facultad);
                idUniversidad = em.merge(idUniversidad);
            }
            em.remove(facultad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Facultad> findFacultadEntities() {
        return findFacultadEntities(true, -1, -1);
    }

    public List<Facultad> findFacultadEntities(int maxResults, int firstResult) {
        return findFacultadEntities(false, maxResults, firstResult);
    }

    private List<Facultad> findFacultadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Facultad.class));
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

    public Facultad findFacultad(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Facultad.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacultadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Facultad> rt = cq.from(Facultad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
