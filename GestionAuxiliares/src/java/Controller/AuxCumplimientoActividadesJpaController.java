/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Entities.AuxCumplimientoActividades;
import Entities.AuxCumplimientoActividadesPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.TipoAuxiliar;
import Entities.CumplimientoActividades;
import Entities.Auxiliar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class AuxCumplimientoActividadesJpaController implements Serializable {

    public AuxCumplimientoActividadesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AuxCumplimientoActividades auxCumplimientoActividades) throws PreexistingEntityException, Exception {
        if (auxCumplimientoActividades.getAuxCumplimientoActividadesPK() == null) {
            auxCumplimientoActividades.setAuxCumplimientoActividadesPK(new AuxCumplimientoActividadesPK());
        }
        auxCumplimientoActividades.getAuxCumplimientoActividadesPK().setIdCumplimientoActividades(auxCumplimientoActividades.getCumplimientoActividades().getCumpActId());
        auxCumplimientoActividades.getAuxCumplimientoActividadesPK().setIdAuxiliar(auxCumplimientoActividades.getAuxiliar().getAuxiliarId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoAuxiliar idTipoAuxiliar = auxCumplimientoActividades.getIdTipoAuxiliar();
            if (idTipoAuxiliar != null) {
                idTipoAuxiliar = em.getReference(idTipoAuxiliar.getClass(), idTipoAuxiliar.getTipoAuxiliarId());
                auxCumplimientoActividades.setIdTipoAuxiliar(idTipoAuxiliar);
            }
            CumplimientoActividades cumplimientoActividades = auxCumplimientoActividades.getCumplimientoActividades();
            if (cumplimientoActividades != null) {
                cumplimientoActividades = em.getReference(cumplimientoActividades.getClass(), cumplimientoActividades.getCumpActId());
                auxCumplimientoActividades.setCumplimientoActividades(cumplimientoActividades);
            }
            Auxiliar auxiliar = auxCumplimientoActividades.getAuxiliar();
            if (auxiliar != null) {
                auxiliar = em.getReference(auxiliar.getClass(), auxiliar.getAuxiliarId());
                auxCumplimientoActividades.setAuxiliar(auxiliar);
            }
            em.persist(auxCumplimientoActividades);
            if (idTipoAuxiliar != null) {
                idTipoAuxiliar.getAuxCumplimientoActividadesList().add(auxCumplimientoActividades);
                idTipoAuxiliar = em.merge(idTipoAuxiliar);
            }
            if (cumplimientoActividades != null) {
                cumplimientoActividades.getAuxCumplimientoActividadesList().add(auxCumplimientoActividades);
                cumplimientoActividades = em.merge(cumplimientoActividades);
            }
            if (auxiliar != null) {
                auxiliar.getAuxCumplimientoActividadesList().add(auxCumplimientoActividades);
                auxiliar = em.merge(auxiliar);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAuxCumplimientoActividades(auxCumplimientoActividades.getAuxCumplimientoActividadesPK()) != null) {
                throw new PreexistingEntityException("AuxCumplimientoActividades " + auxCumplimientoActividades + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AuxCumplimientoActividades auxCumplimientoActividades) throws NonexistentEntityException, Exception {
        auxCumplimientoActividades.getAuxCumplimientoActividadesPK().setIdCumplimientoActividades(auxCumplimientoActividades.getCumplimientoActividades().getCumpActId());
        auxCumplimientoActividades.getAuxCumplimientoActividadesPK().setIdAuxiliar(auxCumplimientoActividades.getAuxiliar().getAuxiliarId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AuxCumplimientoActividades persistentAuxCumplimientoActividades = em.find(AuxCumplimientoActividades.class, auxCumplimientoActividades.getAuxCumplimientoActividadesPK());
            TipoAuxiliar idTipoAuxiliarOld = persistentAuxCumplimientoActividades.getIdTipoAuxiliar();
            TipoAuxiliar idTipoAuxiliarNew = auxCumplimientoActividades.getIdTipoAuxiliar();
            CumplimientoActividades cumplimientoActividadesOld = persistentAuxCumplimientoActividades.getCumplimientoActividades();
            CumplimientoActividades cumplimientoActividadesNew = auxCumplimientoActividades.getCumplimientoActividades();
            Auxiliar auxiliarOld = persistentAuxCumplimientoActividades.getAuxiliar();
            Auxiliar auxiliarNew = auxCumplimientoActividades.getAuxiliar();
            if (idTipoAuxiliarNew != null) {
                idTipoAuxiliarNew = em.getReference(idTipoAuxiliarNew.getClass(), idTipoAuxiliarNew.getTipoAuxiliarId());
                auxCumplimientoActividades.setIdTipoAuxiliar(idTipoAuxiliarNew);
            }
            if (cumplimientoActividadesNew != null) {
                cumplimientoActividadesNew = em.getReference(cumplimientoActividadesNew.getClass(), cumplimientoActividadesNew.getCumpActId());
                auxCumplimientoActividades.setCumplimientoActividades(cumplimientoActividadesNew);
            }
            if (auxiliarNew != null) {
                auxiliarNew = em.getReference(auxiliarNew.getClass(), auxiliarNew.getAuxiliarId());
                auxCumplimientoActividades.setAuxiliar(auxiliarNew);
            }
            auxCumplimientoActividades = em.merge(auxCumplimientoActividades);
            if (idTipoAuxiliarOld != null && !idTipoAuxiliarOld.equals(idTipoAuxiliarNew)) {
                idTipoAuxiliarOld.getAuxCumplimientoActividadesList().remove(auxCumplimientoActividades);
                idTipoAuxiliarOld = em.merge(idTipoAuxiliarOld);
            }
            if (idTipoAuxiliarNew != null && !idTipoAuxiliarNew.equals(idTipoAuxiliarOld)) {
                idTipoAuxiliarNew.getAuxCumplimientoActividadesList().add(auxCumplimientoActividades);
                idTipoAuxiliarNew = em.merge(idTipoAuxiliarNew);
            }
            if (cumplimientoActividadesOld != null && !cumplimientoActividadesOld.equals(cumplimientoActividadesNew)) {
                cumplimientoActividadesOld.getAuxCumplimientoActividadesList().remove(auxCumplimientoActividades);
                cumplimientoActividadesOld = em.merge(cumplimientoActividadesOld);
            }
            if (cumplimientoActividadesNew != null && !cumplimientoActividadesNew.equals(cumplimientoActividadesOld)) {
                cumplimientoActividadesNew.getAuxCumplimientoActividadesList().add(auxCumplimientoActividades);
                cumplimientoActividadesNew = em.merge(cumplimientoActividadesNew);
            }
            if (auxiliarOld != null && !auxiliarOld.equals(auxiliarNew)) {
                auxiliarOld.getAuxCumplimientoActividadesList().remove(auxCumplimientoActividades);
                auxiliarOld = em.merge(auxiliarOld);
            }
            if (auxiliarNew != null && !auxiliarNew.equals(auxiliarOld)) {
                auxiliarNew.getAuxCumplimientoActividadesList().add(auxCumplimientoActividades);
                auxiliarNew = em.merge(auxiliarNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                AuxCumplimientoActividadesPK id = auxCumplimientoActividades.getAuxCumplimientoActividadesPK();
                if (findAuxCumplimientoActividades(id) == null) {
                    throw new NonexistentEntityException("The auxCumplimientoActividades with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(AuxCumplimientoActividadesPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AuxCumplimientoActividades auxCumplimientoActividades;
            try {
                auxCumplimientoActividades = em.getReference(AuxCumplimientoActividades.class, id);
                auxCumplimientoActividades.getAuxCumplimientoActividadesPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The auxCumplimientoActividades with id " + id + " no longer exists.", enfe);
            }
            TipoAuxiliar idTipoAuxiliar = auxCumplimientoActividades.getIdTipoAuxiliar();
            if (idTipoAuxiliar != null) {
                idTipoAuxiliar.getAuxCumplimientoActividadesList().remove(auxCumplimientoActividades);
                idTipoAuxiliar = em.merge(idTipoAuxiliar);
            }
            CumplimientoActividades cumplimientoActividades = auxCumplimientoActividades.getCumplimientoActividades();
            if (cumplimientoActividades != null) {
                cumplimientoActividades.getAuxCumplimientoActividadesList().remove(auxCumplimientoActividades);
                cumplimientoActividades = em.merge(cumplimientoActividades);
            }
            Auxiliar auxiliar = auxCumplimientoActividades.getAuxiliar();
            if (auxiliar != null) {
                auxiliar.getAuxCumplimientoActividadesList().remove(auxCumplimientoActividades);
                auxiliar = em.merge(auxiliar);
            }
            em.remove(auxCumplimientoActividades);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AuxCumplimientoActividades> findAuxCumplimientoActividadesEntities() {
        return findAuxCumplimientoActividadesEntities(true, -1, -1);
    }

    public List<AuxCumplimientoActividades> findAuxCumplimientoActividadesEntities(int maxResults, int firstResult) {
        return findAuxCumplimientoActividadesEntities(false, maxResults, firstResult);
    }

    private List<AuxCumplimientoActividades> findAuxCumplimientoActividadesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AuxCumplimientoActividades.class));
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

    public AuxCumplimientoActividades findAuxCumplimientoActividades(AuxCumplimientoActividadesPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AuxCumplimientoActividades.class, id);
        } finally {
            em.close();
        }
    }

    public int getAuxCumplimientoActividadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AuxCumplimientoActividades> rt = cq.from(AuxCumplimientoActividades.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
