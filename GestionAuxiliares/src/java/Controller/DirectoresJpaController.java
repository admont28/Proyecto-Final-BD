/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Entities.Directores;
import Entities.DirectoresPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Solicitante;
import Entities.ProgramaAcademico;
import Entities.Jornada;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class DirectoresJpaController implements Serializable {

    public DirectoresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Directores directores) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (directores.getDirectoresPK() == null) {
            directores.setDirectoresPK(new DirectoresPK());
        }
        directores.getDirectoresPK().setIdPrograma(directores.getProgramaAcademico().getProgramaAcademicoId());
        directores.getDirectoresPK().setIdJornada(directores.getJornada().getJornadaId());
        List<String> illegalOrphanMessages = null;
        Solicitante idSolicitanteOrphanCheck = directores.getIdSolicitante();
        if (idSolicitanteOrphanCheck != null) {
            Directores oldDirectoresOfIdSolicitante = idSolicitanteOrphanCheck.getDirectores();
            if (oldDirectoresOfIdSolicitante != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Solicitante " + idSolicitanteOrphanCheck + " already has an item of type Directores whose idSolicitante column cannot be null. Please make another selection for the idSolicitante field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Solicitante idSolicitante = directores.getIdSolicitante();
            if (idSolicitante != null) {
                idSolicitante = em.getReference(idSolicitante.getClass(), idSolicitante.getSolicitanteId());
                directores.setIdSolicitante(idSolicitante);
            }
            ProgramaAcademico programaAcademico = directores.getProgramaAcademico();
            if (programaAcademico != null) {
                programaAcademico = em.getReference(programaAcademico.getClass(), programaAcademico.getProgramaAcademicoId());
                directores.setProgramaAcademico(programaAcademico);
            }
            Jornada jornada = directores.getJornada();
            if (jornada != null) {
                jornada = em.getReference(jornada.getClass(), jornada.getJornadaId());
                directores.setJornada(jornada);
            }
            em.persist(directores);
            if (idSolicitante != null) {
                idSolicitante.setDirectores(directores);
                idSolicitante = em.merge(idSolicitante);
            }
            if (programaAcademico != null) {
                programaAcademico.getDirectoresList().add(directores);
                programaAcademico = em.merge(programaAcademico);
            }
            if (jornada != null) {
                jornada.getDirectoresList().add(directores);
                jornada = em.merge(jornada);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDirectores(directores.getDirectoresPK()) != null) {
                throw new PreexistingEntityException("Directores " + directores + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Directores directores) throws IllegalOrphanException, NonexistentEntityException, Exception {
        directores.getDirectoresPK().setIdPrograma(directores.getProgramaAcademico().getProgramaAcademicoId());
        directores.getDirectoresPK().setIdJornada(directores.getJornada().getJornadaId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Directores persistentDirectores = em.find(Directores.class, directores.getDirectoresPK());
            Solicitante idSolicitanteOld = persistentDirectores.getIdSolicitante();
            Solicitante idSolicitanteNew = directores.getIdSolicitante();
            ProgramaAcademico programaAcademicoOld = persistentDirectores.getProgramaAcademico();
            ProgramaAcademico programaAcademicoNew = directores.getProgramaAcademico();
            Jornada jornadaOld = persistentDirectores.getJornada();
            Jornada jornadaNew = directores.getJornada();
            List<String> illegalOrphanMessages = null;
            if (idSolicitanteNew != null && !idSolicitanteNew.equals(idSolicitanteOld)) {
                Directores oldDirectoresOfIdSolicitante = idSolicitanteNew.getDirectores();
                if (oldDirectoresOfIdSolicitante != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Solicitante " + idSolicitanteNew + " already has an item of type Directores whose idSolicitante column cannot be null. Please make another selection for the idSolicitante field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idSolicitanteNew != null) {
                idSolicitanteNew = em.getReference(idSolicitanteNew.getClass(), idSolicitanteNew.getSolicitanteId());
                directores.setIdSolicitante(idSolicitanteNew);
            }
            if (programaAcademicoNew != null) {
                programaAcademicoNew = em.getReference(programaAcademicoNew.getClass(), programaAcademicoNew.getProgramaAcademicoId());
                directores.setProgramaAcademico(programaAcademicoNew);
            }
            if (jornadaNew != null) {
                jornadaNew = em.getReference(jornadaNew.getClass(), jornadaNew.getJornadaId());
                directores.setJornada(jornadaNew);
            }
            directores = em.merge(directores);
            if (idSolicitanteOld != null && !idSolicitanteOld.equals(idSolicitanteNew)) {
                idSolicitanteOld.setDirectores(null);
                idSolicitanteOld = em.merge(idSolicitanteOld);
            }
            if (idSolicitanteNew != null && !idSolicitanteNew.equals(idSolicitanteOld)) {
                idSolicitanteNew.setDirectores(directores);
                idSolicitanteNew = em.merge(idSolicitanteNew);
            }
            if (programaAcademicoOld != null && !programaAcademicoOld.equals(programaAcademicoNew)) {
                programaAcademicoOld.getDirectoresList().remove(directores);
                programaAcademicoOld = em.merge(programaAcademicoOld);
            }
            if (programaAcademicoNew != null && !programaAcademicoNew.equals(programaAcademicoOld)) {
                programaAcademicoNew.getDirectoresList().add(directores);
                programaAcademicoNew = em.merge(programaAcademicoNew);
            }
            if (jornadaOld != null && !jornadaOld.equals(jornadaNew)) {
                jornadaOld.getDirectoresList().remove(directores);
                jornadaOld = em.merge(jornadaOld);
            }
            if (jornadaNew != null && !jornadaNew.equals(jornadaOld)) {
                jornadaNew.getDirectoresList().add(directores);
                jornadaNew = em.merge(jornadaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                DirectoresPK id = directores.getDirectoresPK();
                if (findDirectores(id) == null) {
                    throw new NonexistentEntityException("The directores with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(DirectoresPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Directores directores;
            try {
                directores = em.getReference(Directores.class, id);
                directores.getDirectoresPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The directores with id " + id + " no longer exists.", enfe);
            }
            Solicitante idSolicitante = directores.getIdSolicitante();
            if (idSolicitante != null) {
                idSolicitante.setDirectores(null);
                idSolicitante = em.merge(idSolicitante);
            }
            ProgramaAcademico programaAcademico = directores.getProgramaAcademico();
            if (programaAcademico != null) {
                programaAcademico.getDirectoresList().remove(directores);
                programaAcademico = em.merge(programaAcademico);
            }
            Jornada jornada = directores.getJornada();
            if (jornada != null) {
                jornada.getDirectoresList().remove(directores);
                jornada = em.merge(jornada);
            }
            em.remove(directores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Directores> findDirectoresEntities() {
        return findDirectoresEntities(true, -1, -1);
    }

    public List<Directores> findDirectoresEntities(int maxResults, int firstResult) {
        return findDirectoresEntities(false, maxResults, firstResult);
    }

    private List<Directores> findDirectoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Directores.class));
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

    public Directores findDirectores(DirectoresPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Directores.class, id);
        } finally {
            em.close();
        }
    }

    public int getDirectoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Directores> rt = cq.from(Directores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
