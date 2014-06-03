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
import Entities.AuxCumplimientoActividades;
import java.util.ArrayList;
import java.util.List;
import Entities.Convocatoria;
import Entities.HorarioActividades;
import Entities.TipoAuxiliar;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class TipoAuxiliarJpaController implements Serializable {

    public TipoAuxiliarJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoAuxiliar tipoAuxiliar) throws PreexistingEntityException, Exception {
        if (tipoAuxiliar.getAuxCumplimientoActividadesList() == null) {
            tipoAuxiliar.setAuxCumplimientoActividadesList(new ArrayList<AuxCumplimientoActividades>());
        }
        if (tipoAuxiliar.getConvocatoriaList() == null) {
            tipoAuxiliar.setConvocatoriaList(new ArrayList<Convocatoria>());
        }
        if (tipoAuxiliar.getHorarioActividadesList() == null) {
            tipoAuxiliar.setHorarioActividadesList(new ArrayList<HorarioActividades>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<AuxCumplimientoActividades> attachedAuxCumplimientoActividadesList = new ArrayList<AuxCumplimientoActividades>();
            for (AuxCumplimientoActividades auxCumplimientoActividadesListAuxCumplimientoActividadesToAttach : tipoAuxiliar.getAuxCumplimientoActividadesList()) {
                auxCumplimientoActividadesListAuxCumplimientoActividadesToAttach = em.getReference(auxCumplimientoActividadesListAuxCumplimientoActividadesToAttach.getClass(), auxCumplimientoActividadesListAuxCumplimientoActividadesToAttach.getAuxCumplimientoActividadesPK());
                attachedAuxCumplimientoActividadesList.add(auxCumplimientoActividadesListAuxCumplimientoActividadesToAttach);
            }
            tipoAuxiliar.setAuxCumplimientoActividadesList(attachedAuxCumplimientoActividadesList);
            List<Convocatoria> attachedConvocatoriaList = new ArrayList<Convocatoria>();
            for (Convocatoria convocatoriaListConvocatoriaToAttach : tipoAuxiliar.getConvocatoriaList()) {
                convocatoriaListConvocatoriaToAttach = em.getReference(convocatoriaListConvocatoriaToAttach.getClass(), convocatoriaListConvocatoriaToAttach.getConvocatoriaId());
                attachedConvocatoriaList.add(convocatoriaListConvocatoriaToAttach);
            }
            tipoAuxiliar.setConvocatoriaList(attachedConvocatoriaList);
            List<HorarioActividades> attachedHorarioActividadesList = new ArrayList<HorarioActividades>();
            for (HorarioActividades horarioActividadesListHorarioActividadesToAttach : tipoAuxiliar.getHorarioActividadesList()) {
                horarioActividadesListHorarioActividadesToAttach = em.getReference(horarioActividadesListHorarioActividadesToAttach.getClass(), horarioActividadesListHorarioActividadesToAttach.getHorarioActividadesId());
                attachedHorarioActividadesList.add(horarioActividadesListHorarioActividadesToAttach);
            }
            tipoAuxiliar.setHorarioActividadesList(attachedHorarioActividadesList);
            em.persist(tipoAuxiliar);
            for (AuxCumplimientoActividades auxCumplimientoActividadesListAuxCumplimientoActividades : tipoAuxiliar.getAuxCumplimientoActividadesList()) {
                TipoAuxiliar oldIdTipoAuxiliarOfAuxCumplimientoActividadesListAuxCumplimientoActividades = auxCumplimientoActividadesListAuxCumplimientoActividades.getIdTipoAuxiliar();
                auxCumplimientoActividadesListAuxCumplimientoActividades.setIdTipoAuxiliar(tipoAuxiliar);
                auxCumplimientoActividadesListAuxCumplimientoActividades = em.merge(auxCumplimientoActividadesListAuxCumplimientoActividades);
                if (oldIdTipoAuxiliarOfAuxCumplimientoActividadesListAuxCumplimientoActividades != null) {
                    oldIdTipoAuxiliarOfAuxCumplimientoActividadesListAuxCumplimientoActividades.getAuxCumplimientoActividadesList().remove(auxCumplimientoActividadesListAuxCumplimientoActividades);
                    oldIdTipoAuxiliarOfAuxCumplimientoActividadesListAuxCumplimientoActividades = em.merge(oldIdTipoAuxiliarOfAuxCumplimientoActividadesListAuxCumplimientoActividades);
                }
            }
            for (Convocatoria convocatoriaListConvocatoria : tipoAuxiliar.getConvocatoriaList()) {
                TipoAuxiliar oldIdTipoAuxiliarOfConvocatoriaListConvocatoria = convocatoriaListConvocatoria.getIdTipoAuxiliar();
                convocatoriaListConvocatoria.setIdTipoAuxiliar(tipoAuxiliar);
                convocatoriaListConvocatoria = em.merge(convocatoriaListConvocatoria);
                if (oldIdTipoAuxiliarOfConvocatoriaListConvocatoria != null) {
                    oldIdTipoAuxiliarOfConvocatoriaListConvocatoria.getConvocatoriaList().remove(convocatoriaListConvocatoria);
                    oldIdTipoAuxiliarOfConvocatoriaListConvocatoria = em.merge(oldIdTipoAuxiliarOfConvocatoriaListConvocatoria);
                }
            }
            for (HorarioActividades horarioActividadesListHorarioActividades : tipoAuxiliar.getHorarioActividadesList()) {
                TipoAuxiliar oldIdTipoAuxiliarOfHorarioActividadesListHorarioActividades = horarioActividadesListHorarioActividades.getIdTipoAuxiliar();
                horarioActividadesListHorarioActividades.setIdTipoAuxiliar(tipoAuxiliar);
                horarioActividadesListHorarioActividades = em.merge(horarioActividadesListHorarioActividades);
                if (oldIdTipoAuxiliarOfHorarioActividadesListHorarioActividades != null) {
                    oldIdTipoAuxiliarOfHorarioActividadesListHorarioActividades.getHorarioActividadesList().remove(horarioActividadesListHorarioActividades);
                    oldIdTipoAuxiliarOfHorarioActividadesListHorarioActividades = em.merge(oldIdTipoAuxiliarOfHorarioActividadesListHorarioActividades);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoAuxiliar(tipoAuxiliar.getTipoAuxiliarId()) != null) {
                throw new PreexistingEntityException("TipoAuxiliar " + tipoAuxiliar + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoAuxiliar tipoAuxiliar) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoAuxiliar persistentTipoAuxiliar = em.find(TipoAuxiliar.class, tipoAuxiliar.getTipoAuxiliarId());
            List<AuxCumplimientoActividades> auxCumplimientoActividadesListOld = persistentTipoAuxiliar.getAuxCumplimientoActividadesList();
            List<AuxCumplimientoActividades> auxCumplimientoActividadesListNew = tipoAuxiliar.getAuxCumplimientoActividadesList();
            List<Convocatoria> convocatoriaListOld = persistentTipoAuxiliar.getConvocatoriaList();
            List<Convocatoria> convocatoriaListNew = tipoAuxiliar.getConvocatoriaList();
            List<HorarioActividades> horarioActividadesListOld = persistentTipoAuxiliar.getHorarioActividadesList();
            List<HorarioActividades> horarioActividadesListNew = tipoAuxiliar.getHorarioActividadesList();
            List<String> illegalOrphanMessages = null;
            for (AuxCumplimientoActividades auxCumplimientoActividadesListOldAuxCumplimientoActividades : auxCumplimientoActividadesListOld) {
                if (!auxCumplimientoActividadesListNew.contains(auxCumplimientoActividadesListOldAuxCumplimientoActividades)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AuxCumplimientoActividades " + auxCumplimientoActividadesListOldAuxCumplimientoActividades + " since its idTipoAuxiliar field is not nullable.");
                }
            }
            for (Convocatoria convocatoriaListOldConvocatoria : convocatoriaListOld) {
                if (!convocatoriaListNew.contains(convocatoriaListOldConvocatoria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Convocatoria " + convocatoriaListOldConvocatoria + " since its idTipoAuxiliar field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<AuxCumplimientoActividades> attachedAuxCumplimientoActividadesListNew = new ArrayList<AuxCumplimientoActividades>();
            for (AuxCumplimientoActividades auxCumplimientoActividadesListNewAuxCumplimientoActividadesToAttach : auxCumplimientoActividadesListNew) {
                auxCumplimientoActividadesListNewAuxCumplimientoActividadesToAttach = em.getReference(auxCumplimientoActividadesListNewAuxCumplimientoActividadesToAttach.getClass(), auxCumplimientoActividadesListNewAuxCumplimientoActividadesToAttach.getAuxCumplimientoActividadesPK());
                attachedAuxCumplimientoActividadesListNew.add(auxCumplimientoActividadesListNewAuxCumplimientoActividadesToAttach);
            }
            auxCumplimientoActividadesListNew = attachedAuxCumplimientoActividadesListNew;
            tipoAuxiliar.setAuxCumplimientoActividadesList(auxCumplimientoActividadesListNew);
            List<Convocatoria> attachedConvocatoriaListNew = new ArrayList<Convocatoria>();
            for (Convocatoria convocatoriaListNewConvocatoriaToAttach : convocatoriaListNew) {
                convocatoriaListNewConvocatoriaToAttach = em.getReference(convocatoriaListNewConvocatoriaToAttach.getClass(), convocatoriaListNewConvocatoriaToAttach.getConvocatoriaId());
                attachedConvocatoriaListNew.add(convocatoriaListNewConvocatoriaToAttach);
            }
            convocatoriaListNew = attachedConvocatoriaListNew;
            tipoAuxiliar.setConvocatoriaList(convocatoriaListNew);
            List<HorarioActividades> attachedHorarioActividadesListNew = new ArrayList<HorarioActividades>();
            for (HorarioActividades horarioActividadesListNewHorarioActividadesToAttach : horarioActividadesListNew) {
                horarioActividadesListNewHorarioActividadesToAttach = em.getReference(horarioActividadesListNewHorarioActividadesToAttach.getClass(), horarioActividadesListNewHorarioActividadesToAttach.getHorarioActividadesId());
                attachedHorarioActividadesListNew.add(horarioActividadesListNewHorarioActividadesToAttach);
            }
            horarioActividadesListNew = attachedHorarioActividadesListNew;
            tipoAuxiliar.setHorarioActividadesList(horarioActividadesListNew);
            tipoAuxiliar = em.merge(tipoAuxiliar);
            for (AuxCumplimientoActividades auxCumplimientoActividadesListNewAuxCumplimientoActividades : auxCumplimientoActividadesListNew) {
                if (!auxCumplimientoActividadesListOld.contains(auxCumplimientoActividadesListNewAuxCumplimientoActividades)) {
                    TipoAuxiliar oldIdTipoAuxiliarOfAuxCumplimientoActividadesListNewAuxCumplimientoActividades = auxCumplimientoActividadesListNewAuxCumplimientoActividades.getIdTipoAuxiliar();
                    auxCumplimientoActividadesListNewAuxCumplimientoActividades.setIdTipoAuxiliar(tipoAuxiliar);
                    auxCumplimientoActividadesListNewAuxCumplimientoActividades = em.merge(auxCumplimientoActividadesListNewAuxCumplimientoActividades);
                    if (oldIdTipoAuxiliarOfAuxCumplimientoActividadesListNewAuxCumplimientoActividades != null && !oldIdTipoAuxiliarOfAuxCumplimientoActividadesListNewAuxCumplimientoActividades.equals(tipoAuxiliar)) {
                        oldIdTipoAuxiliarOfAuxCumplimientoActividadesListNewAuxCumplimientoActividades.getAuxCumplimientoActividadesList().remove(auxCumplimientoActividadesListNewAuxCumplimientoActividades);
                        oldIdTipoAuxiliarOfAuxCumplimientoActividadesListNewAuxCumplimientoActividades = em.merge(oldIdTipoAuxiliarOfAuxCumplimientoActividadesListNewAuxCumplimientoActividades);
                    }
                }
            }
            for (Convocatoria convocatoriaListNewConvocatoria : convocatoriaListNew) {
                if (!convocatoriaListOld.contains(convocatoriaListNewConvocatoria)) {
                    TipoAuxiliar oldIdTipoAuxiliarOfConvocatoriaListNewConvocatoria = convocatoriaListNewConvocatoria.getIdTipoAuxiliar();
                    convocatoriaListNewConvocatoria.setIdTipoAuxiliar(tipoAuxiliar);
                    convocatoriaListNewConvocatoria = em.merge(convocatoriaListNewConvocatoria);
                    if (oldIdTipoAuxiliarOfConvocatoriaListNewConvocatoria != null && !oldIdTipoAuxiliarOfConvocatoriaListNewConvocatoria.equals(tipoAuxiliar)) {
                        oldIdTipoAuxiliarOfConvocatoriaListNewConvocatoria.getConvocatoriaList().remove(convocatoriaListNewConvocatoria);
                        oldIdTipoAuxiliarOfConvocatoriaListNewConvocatoria = em.merge(oldIdTipoAuxiliarOfConvocatoriaListNewConvocatoria);
                    }
                }
            }
            for (HorarioActividades horarioActividadesListOldHorarioActividades : horarioActividadesListOld) {
                if (!horarioActividadesListNew.contains(horarioActividadesListOldHorarioActividades)) {
                    horarioActividadesListOldHorarioActividades.setIdTipoAuxiliar(null);
                    horarioActividadesListOldHorarioActividades = em.merge(horarioActividadesListOldHorarioActividades);
                }
            }
            for (HorarioActividades horarioActividadesListNewHorarioActividades : horarioActividadesListNew) {
                if (!horarioActividadesListOld.contains(horarioActividadesListNewHorarioActividades)) {
                    TipoAuxiliar oldIdTipoAuxiliarOfHorarioActividadesListNewHorarioActividades = horarioActividadesListNewHorarioActividades.getIdTipoAuxiliar();
                    horarioActividadesListNewHorarioActividades.setIdTipoAuxiliar(tipoAuxiliar);
                    horarioActividadesListNewHorarioActividades = em.merge(horarioActividadesListNewHorarioActividades);
                    if (oldIdTipoAuxiliarOfHorarioActividadesListNewHorarioActividades != null && !oldIdTipoAuxiliarOfHorarioActividadesListNewHorarioActividades.equals(tipoAuxiliar)) {
                        oldIdTipoAuxiliarOfHorarioActividadesListNewHorarioActividades.getHorarioActividadesList().remove(horarioActividadesListNewHorarioActividades);
                        oldIdTipoAuxiliarOfHorarioActividadesListNewHorarioActividades = em.merge(oldIdTipoAuxiliarOfHorarioActividadesListNewHorarioActividades);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tipoAuxiliar.getTipoAuxiliarId();
                if (findTipoAuxiliar(id) == null) {
                    throw new NonexistentEntityException("The tipoAuxiliar with id " + id + " no longer exists.");
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
            TipoAuxiliar tipoAuxiliar;
            try {
                tipoAuxiliar = em.getReference(TipoAuxiliar.class, id);
                tipoAuxiliar.getTipoAuxiliarId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoAuxiliar with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<AuxCumplimientoActividades> auxCumplimientoActividadesListOrphanCheck = tipoAuxiliar.getAuxCumplimientoActividadesList();
            for (AuxCumplimientoActividades auxCumplimientoActividadesListOrphanCheckAuxCumplimientoActividades : auxCumplimientoActividadesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoAuxiliar (" + tipoAuxiliar + ") cannot be destroyed since the AuxCumplimientoActividades " + auxCumplimientoActividadesListOrphanCheckAuxCumplimientoActividades + " in its auxCumplimientoActividadesList field has a non-nullable idTipoAuxiliar field.");
            }
            List<Convocatoria> convocatoriaListOrphanCheck = tipoAuxiliar.getConvocatoriaList();
            for (Convocatoria convocatoriaListOrphanCheckConvocatoria : convocatoriaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoAuxiliar (" + tipoAuxiliar + ") cannot be destroyed since the Convocatoria " + convocatoriaListOrphanCheckConvocatoria + " in its convocatoriaList field has a non-nullable idTipoAuxiliar field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<HorarioActividades> horarioActividadesList = tipoAuxiliar.getHorarioActividadesList();
            for (HorarioActividades horarioActividadesListHorarioActividades : horarioActividadesList) {
                horarioActividadesListHorarioActividades.setIdTipoAuxiliar(null);
                horarioActividadesListHorarioActividades = em.merge(horarioActividadesListHorarioActividades);
            }
            em.remove(tipoAuxiliar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoAuxiliar> findTipoAuxiliarEntities() {
        return findTipoAuxiliarEntities(true, -1, -1);
    }

    public List<TipoAuxiliar> findTipoAuxiliarEntities(int maxResults, int firstResult) {
        return findTipoAuxiliarEntities(false, maxResults, firstResult);
    }

    private List<TipoAuxiliar> findTipoAuxiliarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoAuxiliar.class));
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

    public TipoAuxiliar findTipoAuxiliar(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoAuxiliar.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoAuxiliarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoAuxiliar> rt = cq.from(TipoAuxiliar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
