/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Entities.FechaHorario;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.HorarioActividades;
import Entities.Hora;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class FechaHorarioJpaController implements Serializable {

    public FechaHorarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FechaHorario fechaHorario) throws PreexistingEntityException, Exception {
        if (fechaHorario.getHoraList() == null) {
            fechaHorario.setHoraList(new ArrayList<Hora>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HorarioActividades idHorarioActividades = fechaHorario.getIdHorarioActividades();
            if (idHorarioActividades != null) {
                idHorarioActividades = em.getReference(idHorarioActividades.getClass(), idHorarioActividades.getHorarioActividadesId());
                fechaHorario.setIdHorarioActividades(idHorarioActividades);
            }
            List<Hora> attachedHoraList = new ArrayList<Hora>();
            for (Hora horaListHoraToAttach : fechaHorario.getHoraList()) {
                horaListHoraToAttach = em.getReference(horaListHoraToAttach.getClass(), horaListHoraToAttach.getHoraPK());
                attachedHoraList.add(horaListHoraToAttach);
            }
            fechaHorario.setHoraList(attachedHoraList);
            em.persist(fechaHorario);
            if (idHorarioActividades != null) {
                idHorarioActividades.getFechaHorarioList().add(fechaHorario);
                idHorarioActividades = em.merge(idHorarioActividades);
            }
            for (Hora horaListHora : fechaHorario.getHoraList()) {
                FechaHorario oldFechaHorarioOfHoraListHora = horaListHora.getFechaHorario();
                horaListHora.setFechaHorario(fechaHorario);
                horaListHora = em.merge(horaListHora);
                if (oldFechaHorarioOfHoraListHora != null) {
                    oldFechaHorarioOfHoraListHora.getHoraList().remove(horaListHora);
                    oldFechaHorarioOfHoraListHora = em.merge(oldFechaHorarioOfHoraListHora);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFechaHorario(fechaHorario.getFechaHorarioId()) != null) {
                throw new PreexistingEntityException("FechaHorario " + fechaHorario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FechaHorario fechaHorario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FechaHorario persistentFechaHorario = em.find(FechaHorario.class, fechaHorario.getFechaHorarioId());
            HorarioActividades idHorarioActividadesOld = persistentFechaHorario.getIdHorarioActividades();
            HorarioActividades idHorarioActividadesNew = fechaHorario.getIdHorarioActividades();
            List<Hora> horaListOld = persistentFechaHorario.getHoraList();
            List<Hora> horaListNew = fechaHorario.getHoraList();
            List<String> illegalOrphanMessages = null;
            for (Hora horaListOldHora : horaListOld) {
                if (!horaListNew.contains(horaListOldHora)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Hora " + horaListOldHora + " since its fechaHorario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idHorarioActividadesNew != null) {
                idHorarioActividadesNew = em.getReference(idHorarioActividadesNew.getClass(), idHorarioActividadesNew.getHorarioActividadesId());
                fechaHorario.setIdHorarioActividades(idHorarioActividadesNew);
            }
            List<Hora> attachedHoraListNew = new ArrayList<Hora>();
            for (Hora horaListNewHoraToAttach : horaListNew) {
                horaListNewHoraToAttach = em.getReference(horaListNewHoraToAttach.getClass(), horaListNewHoraToAttach.getHoraPK());
                attachedHoraListNew.add(horaListNewHoraToAttach);
            }
            horaListNew = attachedHoraListNew;
            fechaHorario.setHoraList(horaListNew);
            fechaHorario = em.merge(fechaHorario);
            if (idHorarioActividadesOld != null && !idHorarioActividadesOld.equals(idHorarioActividadesNew)) {
                idHorarioActividadesOld.getFechaHorarioList().remove(fechaHorario);
                idHorarioActividadesOld = em.merge(idHorarioActividadesOld);
            }
            if (idHorarioActividadesNew != null && !idHorarioActividadesNew.equals(idHorarioActividadesOld)) {
                idHorarioActividadesNew.getFechaHorarioList().add(fechaHorario);
                idHorarioActividadesNew = em.merge(idHorarioActividadesNew);
            }
            for (Hora horaListNewHora : horaListNew) {
                if (!horaListOld.contains(horaListNewHora)) {
                    FechaHorario oldFechaHorarioOfHoraListNewHora = horaListNewHora.getFechaHorario();
                    horaListNewHora.setFechaHorario(fechaHorario);
                    horaListNewHora = em.merge(horaListNewHora);
                    if (oldFechaHorarioOfHoraListNewHora != null && !oldFechaHorarioOfHoraListNewHora.equals(fechaHorario)) {
                        oldFechaHorarioOfHoraListNewHora.getHoraList().remove(horaListNewHora);
                        oldFechaHorarioOfHoraListNewHora = em.merge(oldFechaHorarioOfHoraListNewHora);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = fechaHorario.getFechaHorarioId();
                if (findFechaHorario(id) == null) {
                    throw new NonexistentEntityException("The fechaHorario with id " + id + " no longer exists.");
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
            FechaHorario fechaHorario;
            try {
                fechaHorario = em.getReference(FechaHorario.class, id);
                fechaHorario.getFechaHorarioId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fechaHorario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Hora> horaListOrphanCheck = fechaHorario.getHoraList();
            for (Hora horaListOrphanCheckHora : horaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FechaHorario (" + fechaHorario + ") cannot be destroyed since the Hora " + horaListOrphanCheckHora + " in its horaList field has a non-nullable fechaHorario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            HorarioActividades idHorarioActividades = fechaHorario.getIdHorarioActividades();
            if (idHorarioActividades != null) {
                idHorarioActividades.getFechaHorarioList().remove(fechaHorario);
                idHorarioActividades = em.merge(idHorarioActividades);
            }
            em.remove(fechaHorario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FechaHorario> findFechaHorarioEntities() {
        return findFechaHorarioEntities(true, -1, -1);
    }

    public List<FechaHorario> findFechaHorarioEntities(int maxResults, int firstResult) {
        return findFechaHorarioEntities(false, maxResults, firstResult);
    }

    private List<FechaHorario> findFechaHorarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FechaHorario.class));
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

    public FechaHorario findFechaHorario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FechaHorario.class, id);
        } finally {
            em.close();
        }
    }

    public int getFechaHorarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FechaHorario> rt = cq.from(FechaHorario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
