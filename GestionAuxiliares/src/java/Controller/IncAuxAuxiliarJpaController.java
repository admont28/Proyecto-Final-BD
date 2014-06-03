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
import Entities.Solicitante;
import Entities.InconformidadAuxiliares;
import Entities.Auxiliar;
import Entities.IncAuxAuxiliar;
import Entities.IncAuxAuxiliarPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class IncAuxAuxiliarJpaController implements Serializable {

    public IncAuxAuxiliarJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IncAuxAuxiliar incAuxAuxiliar) throws PreexistingEntityException, Exception {
        if (incAuxAuxiliar.getIncAuxAuxiliarPK() == null) {
            incAuxAuxiliar.setIncAuxAuxiliarPK(new IncAuxAuxiliarPK());
        }
        incAuxAuxiliar.getIncAuxAuxiliarPK().setIdInconformidadAuxiliares(incAuxAuxiliar.getInconformidadAuxiliares().getInconformidadAuxiliaresId());
        incAuxAuxiliar.getIncAuxAuxiliarPK().setIdAuxiliar(incAuxAuxiliar.getAuxiliar().getAuxiliarId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Solicitante idSolicitante = incAuxAuxiliar.getIdSolicitante();
            if (idSolicitante != null) {
                idSolicitante = em.getReference(idSolicitante.getClass(), idSolicitante.getSolicitanteId());
                incAuxAuxiliar.setIdSolicitante(idSolicitante);
            }
            InconformidadAuxiliares inconformidadAuxiliares = incAuxAuxiliar.getInconformidadAuxiliares();
            if (inconformidadAuxiliares != null) {
                inconformidadAuxiliares = em.getReference(inconformidadAuxiliares.getClass(), inconformidadAuxiliares.getInconformidadAuxiliaresId());
                incAuxAuxiliar.setInconformidadAuxiliares(inconformidadAuxiliares);
            }
            Auxiliar auxiliar = incAuxAuxiliar.getAuxiliar();
            if (auxiliar != null) {
                auxiliar = em.getReference(auxiliar.getClass(), auxiliar.getAuxiliarId());
                incAuxAuxiliar.setAuxiliar(auxiliar);
            }
            em.persist(incAuxAuxiliar);
            if (idSolicitante != null) {
                idSolicitante.getIncAuxAuxiliarList().add(incAuxAuxiliar);
                idSolicitante = em.merge(idSolicitante);
            }
            if (inconformidadAuxiliares != null) {
                inconformidadAuxiliares.getIncAuxAuxiliarList().add(incAuxAuxiliar);
                inconformidadAuxiliares = em.merge(inconformidadAuxiliares);
            }
            if (auxiliar != null) {
                auxiliar.getIncAuxAuxiliarList().add(incAuxAuxiliar);
                auxiliar = em.merge(auxiliar);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIncAuxAuxiliar(incAuxAuxiliar.getIncAuxAuxiliarPK()) != null) {
                throw new PreexistingEntityException("IncAuxAuxiliar " + incAuxAuxiliar + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IncAuxAuxiliar incAuxAuxiliar) throws NonexistentEntityException, Exception {
        incAuxAuxiliar.getIncAuxAuxiliarPK().setIdInconformidadAuxiliares(incAuxAuxiliar.getInconformidadAuxiliares().getInconformidadAuxiliaresId());
        incAuxAuxiliar.getIncAuxAuxiliarPK().setIdAuxiliar(incAuxAuxiliar.getAuxiliar().getAuxiliarId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IncAuxAuxiliar persistentIncAuxAuxiliar = em.find(IncAuxAuxiliar.class, incAuxAuxiliar.getIncAuxAuxiliarPK());
            Solicitante idSolicitanteOld = persistentIncAuxAuxiliar.getIdSolicitante();
            Solicitante idSolicitanteNew = incAuxAuxiliar.getIdSolicitante();
            InconformidadAuxiliares inconformidadAuxiliaresOld = persistentIncAuxAuxiliar.getInconformidadAuxiliares();
            InconformidadAuxiliares inconformidadAuxiliaresNew = incAuxAuxiliar.getInconformidadAuxiliares();
            Auxiliar auxiliarOld = persistentIncAuxAuxiliar.getAuxiliar();
            Auxiliar auxiliarNew = incAuxAuxiliar.getAuxiliar();
            if (idSolicitanteNew != null) {
                idSolicitanteNew = em.getReference(idSolicitanteNew.getClass(), idSolicitanteNew.getSolicitanteId());
                incAuxAuxiliar.setIdSolicitante(idSolicitanteNew);
            }
            if (inconformidadAuxiliaresNew != null) {
                inconformidadAuxiliaresNew = em.getReference(inconformidadAuxiliaresNew.getClass(), inconformidadAuxiliaresNew.getInconformidadAuxiliaresId());
                incAuxAuxiliar.setInconformidadAuxiliares(inconformidadAuxiliaresNew);
            }
            if (auxiliarNew != null) {
                auxiliarNew = em.getReference(auxiliarNew.getClass(), auxiliarNew.getAuxiliarId());
                incAuxAuxiliar.setAuxiliar(auxiliarNew);
            }
            incAuxAuxiliar = em.merge(incAuxAuxiliar);
            if (idSolicitanteOld != null && !idSolicitanteOld.equals(idSolicitanteNew)) {
                idSolicitanteOld.getIncAuxAuxiliarList().remove(incAuxAuxiliar);
                idSolicitanteOld = em.merge(idSolicitanteOld);
            }
            if (idSolicitanteNew != null && !idSolicitanteNew.equals(idSolicitanteOld)) {
                idSolicitanteNew.getIncAuxAuxiliarList().add(incAuxAuxiliar);
                idSolicitanteNew = em.merge(idSolicitanteNew);
            }
            if (inconformidadAuxiliaresOld != null && !inconformidadAuxiliaresOld.equals(inconformidadAuxiliaresNew)) {
                inconformidadAuxiliaresOld.getIncAuxAuxiliarList().remove(incAuxAuxiliar);
                inconformidadAuxiliaresOld = em.merge(inconformidadAuxiliaresOld);
            }
            if (inconformidadAuxiliaresNew != null && !inconformidadAuxiliaresNew.equals(inconformidadAuxiliaresOld)) {
                inconformidadAuxiliaresNew.getIncAuxAuxiliarList().add(incAuxAuxiliar);
                inconformidadAuxiliaresNew = em.merge(inconformidadAuxiliaresNew);
            }
            if (auxiliarOld != null && !auxiliarOld.equals(auxiliarNew)) {
                auxiliarOld.getIncAuxAuxiliarList().remove(incAuxAuxiliar);
                auxiliarOld = em.merge(auxiliarOld);
            }
            if (auxiliarNew != null && !auxiliarNew.equals(auxiliarOld)) {
                auxiliarNew.getIncAuxAuxiliarList().add(incAuxAuxiliar);
                auxiliarNew = em.merge(auxiliarNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                IncAuxAuxiliarPK id = incAuxAuxiliar.getIncAuxAuxiliarPK();
                if (findIncAuxAuxiliar(id) == null) {
                    throw new NonexistentEntityException("The incAuxAuxiliar with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(IncAuxAuxiliarPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IncAuxAuxiliar incAuxAuxiliar;
            try {
                incAuxAuxiliar = em.getReference(IncAuxAuxiliar.class, id);
                incAuxAuxiliar.getIncAuxAuxiliarPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The incAuxAuxiliar with id " + id + " no longer exists.", enfe);
            }
            Solicitante idSolicitante = incAuxAuxiliar.getIdSolicitante();
            if (idSolicitante != null) {
                idSolicitante.getIncAuxAuxiliarList().remove(incAuxAuxiliar);
                idSolicitante = em.merge(idSolicitante);
            }
            InconformidadAuxiliares inconformidadAuxiliares = incAuxAuxiliar.getInconformidadAuxiliares();
            if (inconformidadAuxiliares != null) {
                inconformidadAuxiliares.getIncAuxAuxiliarList().remove(incAuxAuxiliar);
                inconformidadAuxiliares = em.merge(inconformidadAuxiliares);
            }
            Auxiliar auxiliar = incAuxAuxiliar.getAuxiliar();
            if (auxiliar != null) {
                auxiliar.getIncAuxAuxiliarList().remove(incAuxAuxiliar);
                auxiliar = em.merge(auxiliar);
            }
            em.remove(incAuxAuxiliar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IncAuxAuxiliar> findIncAuxAuxiliarEntities() {
        return findIncAuxAuxiliarEntities(true, -1, -1);
    }

    public List<IncAuxAuxiliar> findIncAuxAuxiliarEntities(int maxResults, int firstResult) {
        return findIncAuxAuxiliarEntities(false, maxResults, firstResult);
    }

    private List<IncAuxAuxiliar> findIncAuxAuxiliarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IncAuxAuxiliar.class));
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

    public IncAuxAuxiliar findIncAuxAuxiliar(IncAuxAuxiliarPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IncAuxAuxiliar.class, id);
        } finally {
            em.close();
        }
    }

    public int getIncAuxAuxiliarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IncAuxAuxiliar> rt = cq.from(IncAuxAuxiliar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
