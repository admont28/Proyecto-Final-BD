/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Entities.Cargo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Solicitante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class CargoJpaController implements Serializable {

    public CargoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cargo cargo) throws PreexistingEntityException, Exception {
        if (cargo.getSolicitanteList() == null) {
            cargo.setSolicitanteList(new ArrayList<Solicitante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Solicitante> attachedSolicitanteList = new ArrayList<Solicitante>();
            for (Solicitante solicitanteListSolicitanteToAttach : cargo.getSolicitanteList()) {
                solicitanteListSolicitanteToAttach = em.getReference(solicitanteListSolicitanteToAttach.getClass(), solicitanteListSolicitanteToAttach.getSolicitanteId());
                attachedSolicitanteList.add(solicitanteListSolicitanteToAttach);
            }
            cargo.setSolicitanteList(attachedSolicitanteList);
            em.persist(cargo);
            for (Solicitante solicitanteListSolicitante : cargo.getSolicitanteList()) {
                Cargo oldIdCargoOfSolicitanteListSolicitante = solicitanteListSolicitante.getIdCargo();
                solicitanteListSolicitante.setIdCargo(cargo);
                solicitanteListSolicitante = em.merge(solicitanteListSolicitante);
                if (oldIdCargoOfSolicitanteListSolicitante != null) {
                    oldIdCargoOfSolicitanteListSolicitante.getSolicitanteList().remove(solicitanteListSolicitante);
                    oldIdCargoOfSolicitanteListSolicitante = em.merge(oldIdCargoOfSolicitanteListSolicitante);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCargo(cargo.getCargoId()) != null) {
                throw new PreexistingEntityException("Cargo " + cargo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cargo cargo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargo persistentCargo = em.find(Cargo.class, cargo.getCargoId());
            List<Solicitante> solicitanteListOld = persistentCargo.getSolicitanteList();
            List<Solicitante> solicitanteListNew = cargo.getSolicitanteList();
            List<String> illegalOrphanMessages = null;
            for (Solicitante solicitanteListOldSolicitante : solicitanteListOld) {
                if (!solicitanteListNew.contains(solicitanteListOldSolicitante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Solicitante " + solicitanteListOldSolicitante + " since its idCargo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Solicitante> attachedSolicitanteListNew = new ArrayList<Solicitante>();
            for (Solicitante solicitanteListNewSolicitanteToAttach : solicitanteListNew) {
                solicitanteListNewSolicitanteToAttach = em.getReference(solicitanteListNewSolicitanteToAttach.getClass(), solicitanteListNewSolicitanteToAttach.getSolicitanteId());
                attachedSolicitanteListNew.add(solicitanteListNewSolicitanteToAttach);
            }
            solicitanteListNew = attachedSolicitanteListNew;
            cargo.setSolicitanteList(solicitanteListNew);
            cargo = em.merge(cargo);
            for (Solicitante solicitanteListNewSolicitante : solicitanteListNew) {
                if (!solicitanteListOld.contains(solicitanteListNewSolicitante)) {
                    Cargo oldIdCargoOfSolicitanteListNewSolicitante = solicitanteListNewSolicitante.getIdCargo();
                    solicitanteListNewSolicitante.setIdCargo(cargo);
                    solicitanteListNewSolicitante = em.merge(solicitanteListNewSolicitante);
                    if (oldIdCargoOfSolicitanteListNewSolicitante != null && !oldIdCargoOfSolicitanteListNewSolicitante.equals(cargo)) {
                        oldIdCargoOfSolicitanteListNewSolicitante.getSolicitanteList().remove(solicitanteListNewSolicitante);
                        oldIdCargoOfSolicitanteListNewSolicitante = em.merge(oldIdCargoOfSolicitanteListNewSolicitante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = cargo.getCargoId();
                if (findCargo(id) == null) {
                    throw new NonexistentEntityException("The cargo with id " + id + " no longer exists.");
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
            Cargo cargo;
            try {
                cargo = em.getReference(Cargo.class, id);
                cargo.getCargoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cargo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Solicitante> solicitanteListOrphanCheck = cargo.getSolicitanteList();
            for (Solicitante solicitanteListOrphanCheckSolicitante : solicitanteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cargo (" + cargo + ") cannot be destroyed since the Solicitante " + solicitanteListOrphanCheckSolicitante + " in its solicitanteList field has a non-nullable idCargo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cargo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cargo> findCargoEntities() {
        return findCargoEntities(true, -1, -1);
    }

    public List<Cargo> findCargoEntities(int maxResults, int firstResult) {
        return findCargoEntities(false, maxResults, firstResult);
    }

    private List<Cargo> findCargoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cargo.class));
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

    public Cargo findCargo(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cargo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCargoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cargo> rt = cq.from(Cargo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
