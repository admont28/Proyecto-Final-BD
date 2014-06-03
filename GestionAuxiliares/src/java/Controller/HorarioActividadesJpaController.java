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
import Entities.TipoAuxiliar;
import Entities.Auxiliar;
import Entities.FechaHorario;
import Entities.HorarioActividades;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class HorarioActividadesJpaController implements Serializable {

    public HorarioActividadesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HorarioActividades horarioActividades) throws PreexistingEntityException, Exception {
        if (horarioActividades.getFechaHorarioList() == null) {
            horarioActividades.setFechaHorarioList(new ArrayList<FechaHorario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoAuxiliar idTipoAuxiliar = horarioActividades.getIdTipoAuxiliar();
            if (idTipoAuxiliar != null) {
                idTipoAuxiliar = em.getReference(idTipoAuxiliar.getClass(), idTipoAuxiliar.getTipoAuxiliarId());
                horarioActividades.setIdTipoAuxiliar(idTipoAuxiliar);
            }
            Auxiliar idAuxiliar = horarioActividades.getIdAuxiliar();
            if (idAuxiliar != null) {
                idAuxiliar = em.getReference(idAuxiliar.getClass(), idAuxiliar.getAuxiliarId());
                horarioActividades.setIdAuxiliar(idAuxiliar);
            }
            List<FechaHorario> attachedFechaHorarioList = new ArrayList<FechaHorario>();
            for (FechaHorario fechaHorarioListFechaHorarioToAttach : horarioActividades.getFechaHorarioList()) {
                fechaHorarioListFechaHorarioToAttach = em.getReference(fechaHorarioListFechaHorarioToAttach.getClass(), fechaHorarioListFechaHorarioToAttach.getFechaHorarioId());
                attachedFechaHorarioList.add(fechaHorarioListFechaHorarioToAttach);
            }
            horarioActividades.setFechaHorarioList(attachedFechaHorarioList);
            em.persist(horarioActividades);
            if (idTipoAuxiliar != null) {
                idTipoAuxiliar.getHorarioActividadesList().add(horarioActividades);
                idTipoAuxiliar = em.merge(idTipoAuxiliar);
            }
            if (idAuxiliar != null) {
                idAuxiliar.getHorarioActividadesList().add(horarioActividades);
                idAuxiliar = em.merge(idAuxiliar);
            }
            for (FechaHorario fechaHorarioListFechaHorario : horarioActividades.getFechaHorarioList()) {
                HorarioActividades oldIdHorarioActividadesOfFechaHorarioListFechaHorario = fechaHorarioListFechaHorario.getIdHorarioActividades();
                fechaHorarioListFechaHorario.setIdHorarioActividades(horarioActividades);
                fechaHorarioListFechaHorario = em.merge(fechaHorarioListFechaHorario);
                if (oldIdHorarioActividadesOfFechaHorarioListFechaHorario != null) {
                    oldIdHorarioActividadesOfFechaHorarioListFechaHorario.getFechaHorarioList().remove(fechaHorarioListFechaHorario);
                    oldIdHorarioActividadesOfFechaHorarioListFechaHorario = em.merge(oldIdHorarioActividadesOfFechaHorarioListFechaHorario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHorarioActividades(horarioActividades.getHorarioActividadesId()) != null) {
                throw new PreexistingEntityException("HorarioActividades " + horarioActividades + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HorarioActividades horarioActividades) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HorarioActividades persistentHorarioActividades = em.find(HorarioActividades.class, horarioActividades.getHorarioActividadesId());
            TipoAuxiliar idTipoAuxiliarOld = persistentHorarioActividades.getIdTipoAuxiliar();
            TipoAuxiliar idTipoAuxiliarNew = horarioActividades.getIdTipoAuxiliar();
            Auxiliar idAuxiliarOld = persistentHorarioActividades.getIdAuxiliar();
            Auxiliar idAuxiliarNew = horarioActividades.getIdAuxiliar();
            List<FechaHorario> fechaHorarioListOld = persistentHorarioActividades.getFechaHorarioList();
            List<FechaHorario> fechaHorarioListNew = horarioActividades.getFechaHorarioList();
            List<String> illegalOrphanMessages = null;
            for (FechaHorario fechaHorarioListOldFechaHorario : fechaHorarioListOld) {
                if (!fechaHorarioListNew.contains(fechaHorarioListOldFechaHorario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FechaHorario " + fechaHorarioListOldFechaHorario + " since its idHorarioActividades field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idTipoAuxiliarNew != null) {
                idTipoAuxiliarNew = em.getReference(idTipoAuxiliarNew.getClass(), idTipoAuxiliarNew.getTipoAuxiliarId());
                horarioActividades.setIdTipoAuxiliar(idTipoAuxiliarNew);
            }
            if (idAuxiliarNew != null) {
                idAuxiliarNew = em.getReference(idAuxiliarNew.getClass(), idAuxiliarNew.getAuxiliarId());
                horarioActividades.setIdAuxiliar(idAuxiliarNew);
            }
            List<FechaHorario> attachedFechaHorarioListNew = new ArrayList<FechaHorario>();
            for (FechaHorario fechaHorarioListNewFechaHorarioToAttach : fechaHorarioListNew) {
                fechaHorarioListNewFechaHorarioToAttach = em.getReference(fechaHorarioListNewFechaHorarioToAttach.getClass(), fechaHorarioListNewFechaHorarioToAttach.getFechaHorarioId());
                attachedFechaHorarioListNew.add(fechaHorarioListNewFechaHorarioToAttach);
            }
            fechaHorarioListNew = attachedFechaHorarioListNew;
            horarioActividades.setFechaHorarioList(fechaHorarioListNew);
            horarioActividades = em.merge(horarioActividades);
            if (idTipoAuxiliarOld != null && !idTipoAuxiliarOld.equals(idTipoAuxiliarNew)) {
                idTipoAuxiliarOld.getHorarioActividadesList().remove(horarioActividades);
                idTipoAuxiliarOld = em.merge(idTipoAuxiliarOld);
            }
            if (idTipoAuxiliarNew != null && !idTipoAuxiliarNew.equals(idTipoAuxiliarOld)) {
                idTipoAuxiliarNew.getHorarioActividadesList().add(horarioActividades);
                idTipoAuxiliarNew = em.merge(idTipoAuxiliarNew);
            }
            if (idAuxiliarOld != null && !idAuxiliarOld.equals(idAuxiliarNew)) {
                idAuxiliarOld.getHorarioActividadesList().remove(horarioActividades);
                idAuxiliarOld = em.merge(idAuxiliarOld);
            }
            if (idAuxiliarNew != null && !idAuxiliarNew.equals(idAuxiliarOld)) {
                idAuxiliarNew.getHorarioActividadesList().add(horarioActividades);
                idAuxiliarNew = em.merge(idAuxiliarNew);
            }
            for (FechaHorario fechaHorarioListNewFechaHorario : fechaHorarioListNew) {
                if (!fechaHorarioListOld.contains(fechaHorarioListNewFechaHorario)) {
                    HorarioActividades oldIdHorarioActividadesOfFechaHorarioListNewFechaHorario = fechaHorarioListNewFechaHorario.getIdHorarioActividades();
                    fechaHorarioListNewFechaHorario.setIdHorarioActividades(horarioActividades);
                    fechaHorarioListNewFechaHorario = em.merge(fechaHorarioListNewFechaHorario);
                    if (oldIdHorarioActividadesOfFechaHorarioListNewFechaHorario != null && !oldIdHorarioActividadesOfFechaHorarioListNewFechaHorario.equals(horarioActividades)) {
                        oldIdHorarioActividadesOfFechaHorarioListNewFechaHorario.getFechaHorarioList().remove(fechaHorarioListNewFechaHorario);
                        oldIdHorarioActividadesOfFechaHorarioListNewFechaHorario = em.merge(oldIdHorarioActividadesOfFechaHorarioListNewFechaHorario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = horarioActividades.getHorarioActividadesId();
                if (findHorarioActividades(id) == null) {
                    throw new NonexistentEntityException("The horarioActividades with id " + id + " no longer exists.");
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
            HorarioActividades horarioActividades;
            try {
                horarioActividades = em.getReference(HorarioActividades.class, id);
                horarioActividades.getHorarioActividadesId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The horarioActividades with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<FechaHorario> fechaHorarioListOrphanCheck = horarioActividades.getFechaHorarioList();
            for (FechaHorario fechaHorarioListOrphanCheckFechaHorario : fechaHorarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This HorarioActividades (" + horarioActividades + ") cannot be destroyed since the FechaHorario " + fechaHorarioListOrphanCheckFechaHorario + " in its fechaHorarioList field has a non-nullable idHorarioActividades field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TipoAuxiliar idTipoAuxiliar = horarioActividades.getIdTipoAuxiliar();
            if (idTipoAuxiliar != null) {
                idTipoAuxiliar.getHorarioActividadesList().remove(horarioActividades);
                idTipoAuxiliar = em.merge(idTipoAuxiliar);
            }
            Auxiliar idAuxiliar = horarioActividades.getIdAuxiliar();
            if (idAuxiliar != null) {
                idAuxiliar.getHorarioActividadesList().remove(horarioActividades);
                idAuxiliar = em.merge(idAuxiliar);
            }
            em.remove(horarioActividades);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HorarioActividades> findHorarioActividadesEntities() {
        return findHorarioActividadesEntities(true, -1, -1);
    }

    public List<HorarioActividades> findHorarioActividadesEntities(int maxResults, int firstResult) {
        return findHorarioActividadesEntities(false, maxResults, firstResult);
    }

    private List<HorarioActividades> findHorarioActividadesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HorarioActividades.class));
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

    public HorarioActividades findHorarioActividades(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HorarioActividades.class, id);
        } finally {
            em.close();
        }
    }

    public int getHorarioActividadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HorarioActividades> rt = cq.from(HorarioActividades.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
