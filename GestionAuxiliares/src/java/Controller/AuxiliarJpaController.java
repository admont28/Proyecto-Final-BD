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
import Entities.ProgramaAcademico;
import Entities.Direccion;
import Entities.SeleccionAuxiliares;
import java.util.ArrayList;
import java.util.List;
import Entities.DetalleResolucion;
import Entities.InscripcionConvocatoria;
import Entities.IncAuxAuxiliar;
import Entities.AuxCumplimientoActividades;
import Entities.Auxiliar;
import Entities.HorarioActividades;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class AuxiliarJpaController implements Serializable {

    public AuxiliarJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Auxiliar auxiliar) throws PreexistingEntityException, Exception {
        if (auxiliar.getSeleccionAuxiliaresList() == null) {
            auxiliar.setSeleccionAuxiliaresList(new ArrayList<SeleccionAuxiliares>());
        }
        if (auxiliar.getDetalleResolucionList() == null) {
            auxiliar.setDetalleResolucionList(new ArrayList<DetalleResolucion>());
        }
        if (auxiliar.getInscripcionConvocatoriaList() == null) {
            auxiliar.setInscripcionConvocatoriaList(new ArrayList<InscripcionConvocatoria>());
        }
        if (auxiliar.getIncAuxAuxiliarList() == null) {
            auxiliar.setIncAuxAuxiliarList(new ArrayList<IncAuxAuxiliar>());
        }
        if (auxiliar.getAuxCumplimientoActividadesList() == null) {
            auxiliar.setAuxCumplimientoActividadesList(new ArrayList<AuxCumplimientoActividades>());
        }
        if (auxiliar.getHorarioActividadesList() == null) {
            auxiliar.setHorarioActividadesList(new ArrayList<HorarioActividades>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProgramaAcademico idProgramaAcademico = auxiliar.getIdProgramaAcademico();
            if (idProgramaAcademico != null) {
                idProgramaAcademico = em.getReference(idProgramaAcademico.getClass(), idProgramaAcademico.getProgramaAcademicoId());
                auxiliar.setIdProgramaAcademico(idProgramaAcademico);
            }
            Direccion idDireccion = auxiliar.getIdDireccion();
            if (idDireccion != null) {
                idDireccion = em.getReference(idDireccion.getClass(), idDireccion.getDireccionId());
                auxiliar.setIdDireccion(idDireccion);
            }
            List<SeleccionAuxiliares> attachedSeleccionAuxiliaresList = new ArrayList<SeleccionAuxiliares>();
            for (SeleccionAuxiliares seleccionAuxiliaresListSeleccionAuxiliaresToAttach : auxiliar.getSeleccionAuxiliaresList()) {
                seleccionAuxiliaresListSeleccionAuxiliaresToAttach = em.getReference(seleccionAuxiliaresListSeleccionAuxiliaresToAttach.getClass(), seleccionAuxiliaresListSeleccionAuxiliaresToAttach.getSeleccionAuxiliaresPK());
                attachedSeleccionAuxiliaresList.add(seleccionAuxiliaresListSeleccionAuxiliaresToAttach);
            }
            auxiliar.setSeleccionAuxiliaresList(attachedSeleccionAuxiliaresList);
            List<DetalleResolucion> attachedDetalleResolucionList = new ArrayList<DetalleResolucion>();
            for (DetalleResolucion detalleResolucionListDetalleResolucionToAttach : auxiliar.getDetalleResolucionList()) {
                detalleResolucionListDetalleResolucionToAttach = em.getReference(detalleResolucionListDetalleResolucionToAttach.getClass(), detalleResolucionListDetalleResolucionToAttach.getDetalleResolucionPK());
                attachedDetalleResolucionList.add(detalleResolucionListDetalleResolucionToAttach);
            }
            auxiliar.setDetalleResolucionList(attachedDetalleResolucionList);
            List<InscripcionConvocatoria> attachedInscripcionConvocatoriaList = new ArrayList<InscripcionConvocatoria>();
            for (InscripcionConvocatoria inscripcionConvocatoriaListInscripcionConvocatoriaToAttach : auxiliar.getInscripcionConvocatoriaList()) {
                inscripcionConvocatoriaListInscripcionConvocatoriaToAttach = em.getReference(inscripcionConvocatoriaListInscripcionConvocatoriaToAttach.getClass(), inscripcionConvocatoriaListInscripcionConvocatoriaToAttach.getInscripcionConvocatoriaPK());
                attachedInscripcionConvocatoriaList.add(inscripcionConvocatoriaListInscripcionConvocatoriaToAttach);
            }
            auxiliar.setInscripcionConvocatoriaList(attachedInscripcionConvocatoriaList);
            List<IncAuxAuxiliar> attachedIncAuxAuxiliarList = new ArrayList<IncAuxAuxiliar>();
            for (IncAuxAuxiliar incAuxAuxiliarListIncAuxAuxiliarToAttach : auxiliar.getIncAuxAuxiliarList()) {
                incAuxAuxiliarListIncAuxAuxiliarToAttach = em.getReference(incAuxAuxiliarListIncAuxAuxiliarToAttach.getClass(), incAuxAuxiliarListIncAuxAuxiliarToAttach.getIncAuxAuxiliarPK());
                attachedIncAuxAuxiliarList.add(incAuxAuxiliarListIncAuxAuxiliarToAttach);
            }
            auxiliar.setIncAuxAuxiliarList(attachedIncAuxAuxiliarList);
            List<AuxCumplimientoActividades> attachedAuxCumplimientoActividadesList = new ArrayList<AuxCumplimientoActividades>();
            for (AuxCumplimientoActividades auxCumplimientoActividadesListAuxCumplimientoActividadesToAttach : auxiliar.getAuxCumplimientoActividadesList()) {
                auxCumplimientoActividadesListAuxCumplimientoActividadesToAttach = em.getReference(auxCumplimientoActividadesListAuxCumplimientoActividadesToAttach.getClass(), auxCumplimientoActividadesListAuxCumplimientoActividadesToAttach.getAuxCumplimientoActividadesPK());
                attachedAuxCumplimientoActividadesList.add(auxCumplimientoActividadesListAuxCumplimientoActividadesToAttach);
            }
            auxiliar.setAuxCumplimientoActividadesList(attachedAuxCumplimientoActividadesList);
            List<HorarioActividades> attachedHorarioActividadesList = new ArrayList<HorarioActividades>();
            for (HorarioActividades horarioActividadesListHorarioActividadesToAttach : auxiliar.getHorarioActividadesList()) {
                horarioActividadesListHorarioActividadesToAttach = em.getReference(horarioActividadesListHorarioActividadesToAttach.getClass(), horarioActividadesListHorarioActividadesToAttach.getHorarioActividadesId());
                attachedHorarioActividadesList.add(horarioActividadesListHorarioActividadesToAttach);
            }
            auxiliar.setHorarioActividadesList(attachedHorarioActividadesList);
            em.persist(auxiliar);
            if (idProgramaAcademico != null) {
                idProgramaAcademico.getAuxiliarList().add(auxiliar);
                idProgramaAcademico = em.merge(idProgramaAcademico);
            }
            if (idDireccion != null) {
                idDireccion.getAuxiliarList().add(auxiliar);
                idDireccion = em.merge(idDireccion);
            }
            for (SeleccionAuxiliares seleccionAuxiliaresListSeleccionAuxiliares : auxiliar.getSeleccionAuxiliaresList()) {
                Auxiliar oldAuxiliarOfSeleccionAuxiliaresListSeleccionAuxiliares = seleccionAuxiliaresListSeleccionAuxiliares.getAuxiliar();
                seleccionAuxiliaresListSeleccionAuxiliares.setAuxiliar(auxiliar);
                seleccionAuxiliaresListSeleccionAuxiliares = em.merge(seleccionAuxiliaresListSeleccionAuxiliares);
                if (oldAuxiliarOfSeleccionAuxiliaresListSeleccionAuxiliares != null) {
                    oldAuxiliarOfSeleccionAuxiliaresListSeleccionAuxiliares.getSeleccionAuxiliaresList().remove(seleccionAuxiliaresListSeleccionAuxiliares);
                    oldAuxiliarOfSeleccionAuxiliaresListSeleccionAuxiliares = em.merge(oldAuxiliarOfSeleccionAuxiliaresListSeleccionAuxiliares);
                }
            }
            for (DetalleResolucion detalleResolucionListDetalleResolucion : auxiliar.getDetalleResolucionList()) {
                Auxiliar oldAuxiliarOfDetalleResolucionListDetalleResolucion = detalleResolucionListDetalleResolucion.getAuxiliar();
                detalleResolucionListDetalleResolucion.setAuxiliar(auxiliar);
                detalleResolucionListDetalleResolucion = em.merge(detalleResolucionListDetalleResolucion);
                if (oldAuxiliarOfDetalleResolucionListDetalleResolucion != null) {
                    oldAuxiliarOfDetalleResolucionListDetalleResolucion.getDetalleResolucionList().remove(detalleResolucionListDetalleResolucion);
                    oldAuxiliarOfDetalleResolucionListDetalleResolucion = em.merge(oldAuxiliarOfDetalleResolucionListDetalleResolucion);
                }
            }
            for (InscripcionConvocatoria inscripcionConvocatoriaListInscripcionConvocatoria : auxiliar.getInscripcionConvocatoriaList()) {
                Auxiliar oldAuxiliarOfInscripcionConvocatoriaListInscripcionConvocatoria = inscripcionConvocatoriaListInscripcionConvocatoria.getAuxiliar();
                inscripcionConvocatoriaListInscripcionConvocatoria.setAuxiliar(auxiliar);
                inscripcionConvocatoriaListInscripcionConvocatoria = em.merge(inscripcionConvocatoriaListInscripcionConvocatoria);
                if (oldAuxiliarOfInscripcionConvocatoriaListInscripcionConvocatoria != null) {
                    oldAuxiliarOfInscripcionConvocatoriaListInscripcionConvocatoria.getInscripcionConvocatoriaList().remove(inscripcionConvocatoriaListInscripcionConvocatoria);
                    oldAuxiliarOfInscripcionConvocatoriaListInscripcionConvocatoria = em.merge(oldAuxiliarOfInscripcionConvocatoriaListInscripcionConvocatoria);
                }
            }
            for (IncAuxAuxiliar incAuxAuxiliarListIncAuxAuxiliar : auxiliar.getIncAuxAuxiliarList()) {
                Auxiliar oldAuxiliarOfIncAuxAuxiliarListIncAuxAuxiliar = incAuxAuxiliarListIncAuxAuxiliar.getAuxiliar();
                incAuxAuxiliarListIncAuxAuxiliar.setAuxiliar(auxiliar);
                incAuxAuxiliarListIncAuxAuxiliar = em.merge(incAuxAuxiliarListIncAuxAuxiliar);
                if (oldAuxiliarOfIncAuxAuxiliarListIncAuxAuxiliar != null) {
                    oldAuxiliarOfIncAuxAuxiliarListIncAuxAuxiliar.getIncAuxAuxiliarList().remove(incAuxAuxiliarListIncAuxAuxiliar);
                    oldAuxiliarOfIncAuxAuxiliarListIncAuxAuxiliar = em.merge(oldAuxiliarOfIncAuxAuxiliarListIncAuxAuxiliar);
                }
            }
            for (AuxCumplimientoActividades auxCumplimientoActividadesListAuxCumplimientoActividades : auxiliar.getAuxCumplimientoActividadesList()) {
                Auxiliar oldAuxiliarOfAuxCumplimientoActividadesListAuxCumplimientoActividades = auxCumplimientoActividadesListAuxCumplimientoActividades.getAuxiliar();
                auxCumplimientoActividadesListAuxCumplimientoActividades.setAuxiliar(auxiliar);
                auxCumplimientoActividadesListAuxCumplimientoActividades = em.merge(auxCumplimientoActividadesListAuxCumplimientoActividades);
                if (oldAuxiliarOfAuxCumplimientoActividadesListAuxCumplimientoActividades != null) {
                    oldAuxiliarOfAuxCumplimientoActividadesListAuxCumplimientoActividades.getAuxCumplimientoActividadesList().remove(auxCumplimientoActividadesListAuxCumplimientoActividades);
                    oldAuxiliarOfAuxCumplimientoActividadesListAuxCumplimientoActividades = em.merge(oldAuxiliarOfAuxCumplimientoActividadesListAuxCumplimientoActividades);
                }
            }
            for (HorarioActividades horarioActividadesListHorarioActividades : auxiliar.getHorarioActividadesList()) {
                Auxiliar oldIdAuxiliarOfHorarioActividadesListHorarioActividades = horarioActividadesListHorarioActividades.getIdAuxiliar();
                horarioActividadesListHorarioActividades.setIdAuxiliar(auxiliar);
                horarioActividadesListHorarioActividades = em.merge(horarioActividadesListHorarioActividades);
                if (oldIdAuxiliarOfHorarioActividadesListHorarioActividades != null) {
                    oldIdAuxiliarOfHorarioActividadesListHorarioActividades.getHorarioActividadesList().remove(horarioActividadesListHorarioActividades);
                    oldIdAuxiliarOfHorarioActividadesListHorarioActividades = em.merge(oldIdAuxiliarOfHorarioActividadesListHorarioActividades);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAuxiliar(auxiliar.getAuxiliarId()) != null) {
                throw new PreexistingEntityException("Auxiliar " + auxiliar + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Auxiliar auxiliar) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Auxiliar persistentAuxiliar = em.find(Auxiliar.class, auxiliar.getAuxiliarId());
            ProgramaAcademico idProgramaAcademicoOld = persistentAuxiliar.getIdProgramaAcademico();
            ProgramaAcademico idProgramaAcademicoNew = auxiliar.getIdProgramaAcademico();
            Direccion idDireccionOld = persistentAuxiliar.getIdDireccion();
            Direccion idDireccionNew = auxiliar.getIdDireccion();
            List<SeleccionAuxiliares> seleccionAuxiliaresListOld = persistentAuxiliar.getSeleccionAuxiliaresList();
            List<SeleccionAuxiliares> seleccionAuxiliaresListNew = auxiliar.getSeleccionAuxiliaresList();
            List<DetalleResolucion> detalleResolucionListOld = persistentAuxiliar.getDetalleResolucionList();
            List<DetalleResolucion> detalleResolucionListNew = auxiliar.getDetalleResolucionList();
            List<InscripcionConvocatoria> inscripcionConvocatoriaListOld = persistentAuxiliar.getInscripcionConvocatoriaList();
            List<InscripcionConvocatoria> inscripcionConvocatoriaListNew = auxiliar.getInscripcionConvocatoriaList();
            List<IncAuxAuxiliar> incAuxAuxiliarListOld = persistentAuxiliar.getIncAuxAuxiliarList();
            List<IncAuxAuxiliar> incAuxAuxiliarListNew = auxiliar.getIncAuxAuxiliarList();
            List<AuxCumplimientoActividades> auxCumplimientoActividadesListOld = persistentAuxiliar.getAuxCumplimientoActividadesList();
            List<AuxCumplimientoActividades> auxCumplimientoActividadesListNew = auxiliar.getAuxCumplimientoActividadesList();
            List<HorarioActividades> horarioActividadesListOld = persistentAuxiliar.getHorarioActividadesList();
            List<HorarioActividades> horarioActividadesListNew = auxiliar.getHorarioActividadesList();
            List<String> illegalOrphanMessages = null;
            for (SeleccionAuxiliares seleccionAuxiliaresListOldSeleccionAuxiliares : seleccionAuxiliaresListOld) {
                if (!seleccionAuxiliaresListNew.contains(seleccionAuxiliaresListOldSeleccionAuxiliares)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SeleccionAuxiliares " + seleccionAuxiliaresListOldSeleccionAuxiliares + " since its auxiliar field is not nullable.");
                }
            }
            for (DetalleResolucion detalleResolucionListOldDetalleResolucion : detalleResolucionListOld) {
                if (!detalleResolucionListNew.contains(detalleResolucionListOldDetalleResolucion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleResolucion " + detalleResolucionListOldDetalleResolucion + " since its auxiliar field is not nullable.");
                }
            }
            for (InscripcionConvocatoria inscripcionConvocatoriaListOldInscripcionConvocatoria : inscripcionConvocatoriaListOld) {
                if (!inscripcionConvocatoriaListNew.contains(inscripcionConvocatoriaListOldInscripcionConvocatoria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InscripcionConvocatoria " + inscripcionConvocatoriaListOldInscripcionConvocatoria + " since its auxiliar field is not nullable.");
                }
            }
            for (IncAuxAuxiliar incAuxAuxiliarListOldIncAuxAuxiliar : incAuxAuxiliarListOld) {
                if (!incAuxAuxiliarListNew.contains(incAuxAuxiliarListOldIncAuxAuxiliar)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain IncAuxAuxiliar " + incAuxAuxiliarListOldIncAuxAuxiliar + " since its auxiliar field is not nullable.");
                }
            }
            for (AuxCumplimientoActividades auxCumplimientoActividadesListOldAuxCumplimientoActividades : auxCumplimientoActividadesListOld) {
                if (!auxCumplimientoActividadesListNew.contains(auxCumplimientoActividadesListOldAuxCumplimientoActividades)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AuxCumplimientoActividades " + auxCumplimientoActividadesListOldAuxCumplimientoActividades + " since its auxiliar field is not nullable.");
                }
            }
            for (HorarioActividades horarioActividadesListOldHorarioActividades : horarioActividadesListOld) {
                if (!horarioActividadesListNew.contains(horarioActividadesListOldHorarioActividades)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HorarioActividades " + horarioActividadesListOldHorarioActividades + " since its idAuxiliar field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idProgramaAcademicoNew != null) {
                idProgramaAcademicoNew = em.getReference(idProgramaAcademicoNew.getClass(), idProgramaAcademicoNew.getProgramaAcademicoId());
                auxiliar.setIdProgramaAcademico(idProgramaAcademicoNew);
            }
            if (idDireccionNew != null) {
                idDireccionNew = em.getReference(idDireccionNew.getClass(), idDireccionNew.getDireccionId());
                auxiliar.setIdDireccion(idDireccionNew);
            }
            List<SeleccionAuxiliares> attachedSeleccionAuxiliaresListNew = new ArrayList<SeleccionAuxiliares>();
            for (SeleccionAuxiliares seleccionAuxiliaresListNewSeleccionAuxiliaresToAttach : seleccionAuxiliaresListNew) {
                seleccionAuxiliaresListNewSeleccionAuxiliaresToAttach = em.getReference(seleccionAuxiliaresListNewSeleccionAuxiliaresToAttach.getClass(), seleccionAuxiliaresListNewSeleccionAuxiliaresToAttach.getSeleccionAuxiliaresPK());
                attachedSeleccionAuxiliaresListNew.add(seleccionAuxiliaresListNewSeleccionAuxiliaresToAttach);
            }
            seleccionAuxiliaresListNew = attachedSeleccionAuxiliaresListNew;
            auxiliar.setSeleccionAuxiliaresList(seleccionAuxiliaresListNew);
            List<DetalleResolucion> attachedDetalleResolucionListNew = new ArrayList<DetalleResolucion>();
            for (DetalleResolucion detalleResolucionListNewDetalleResolucionToAttach : detalleResolucionListNew) {
                detalleResolucionListNewDetalleResolucionToAttach = em.getReference(detalleResolucionListNewDetalleResolucionToAttach.getClass(), detalleResolucionListNewDetalleResolucionToAttach.getDetalleResolucionPK());
                attachedDetalleResolucionListNew.add(detalleResolucionListNewDetalleResolucionToAttach);
            }
            detalleResolucionListNew = attachedDetalleResolucionListNew;
            auxiliar.setDetalleResolucionList(detalleResolucionListNew);
            List<InscripcionConvocatoria> attachedInscripcionConvocatoriaListNew = new ArrayList<InscripcionConvocatoria>();
            for (InscripcionConvocatoria inscripcionConvocatoriaListNewInscripcionConvocatoriaToAttach : inscripcionConvocatoriaListNew) {
                inscripcionConvocatoriaListNewInscripcionConvocatoriaToAttach = em.getReference(inscripcionConvocatoriaListNewInscripcionConvocatoriaToAttach.getClass(), inscripcionConvocatoriaListNewInscripcionConvocatoriaToAttach.getInscripcionConvocatoriaPK());
                attachedInscripcionConvocatoriaListNew.add(inscripcionConvocatoriaListNewInscripcionConvocatoriaToAttach);
            }
            inscripcionConvocatoriaListNew = attachedInscripcionConvocatoriaListNew;
            auxiliar.setInscripcionConvocatoriaList(inscripcionConvocatoriaListNew);
            List<IncAuxAuxiliar> attachedIncAuxAuxiliarListNew = new ArrayList<IncAuxAuxiliar>();
            for (IncAuxAuxiliar incAuxAuxiliarListNewIncAuxAuxiliarToAttach : incAuxAuxiliarListNew) {
                incAuxAuxiliarListNewIncAuxAuxiliarToAttach = em.getReference(incAuxAuxiliarListNewIncAuxAuxiliarToAttach.getClass(), incAuxAuxiliarListNewIncAuxAuxiliarToAttach.getIncAuxAuxiliarPK());
                attachedIncAuxAuxiliarListNew.add(incAuxAuxiliarListNewIncAuxAuxiliarToAttach);
            }
            incAuxAuxiliarListNew = attachedIncAuxAuxiliarListNew;
            auxiliar.setIncAuxAuxiliarList(incAuxAuxiliarListNew);
            List<AuxCumplimientoActividades> attachedAuxCumplimientoActividadesListNew = new ArrayList<AuxCumplimientoActividades>();
            for (AuxCumplimientoActividades auxCumplimientoActividadesListNewAuxCumplimientoActividadesToAttach : auxCumplimientoActividadesListNew) {
                auxCumplimientoActividadesListNewAuxCumplimientoActividadesToAttach = em.getReference(auxCumplimientoActividadesListNewAuxCumplimientoActividadesToAttach.getClass(), auxCumplimientoActividadesListNewAuxCumplimientoActividadesToAttach.getAuxCumplimientoActividadesPK());
                attachedAuxCumplimientoActividadesListNew.add(auxCumplimientoActividadesListNewAuxCumplimientoActividadesToAttach);
            }
            auxCumplimientoActividadesListNew = attachedAuxCumplimientoActividadesListNew;
            auxiliar.setAuxCumplimientoActividadesList(auxCumplimientoActividadesListNew);
            List<HorarioActividades> attachedHorarioActividadesListNew = new ArrayList<HorarioActividades>();
            for (HorarioActividades horarioActividadesListNewHorarioActividadesToAttach : horarioActividadesListNew) {
                horarioActividadesListNewHorarioActividadesToAttach = em.getReference(horarioActividadesListNewHorarioActividadesToAttach.getClass(), horarioActividadesListNewHorarioActividadesToAttach.getHorarioActividadesId());
                attachedHorarioActividadesListNew.add(horarioActividadesListNewHorarioActividadesToAttach);
            }
            horarioActividadesListNew = attachedHorarioActividadesListNew;
            auxiliar.setHorarioActividadesList(horarioActividadesListNew);
            auxiliar = em.merge(auxiliar);
            if (idProgramaAcademicoOld != null && !idProgramaAcademicoOld.equals(idProgramaAcademicoNew)) {
                idProgramaAcademicoOld.getAuxiliarList().remove(auxiliar);
                idProgramaAcademicoOld = em.merge(idProgramaAcademicoOld);
            }
            if (idProgramaAcademicoNew != null && !idProgramaAcademicoNew.equals(idProgramaAcademicoOld)) {
                idProgramaAcademicoNew.getAuxiliarList().add(auxiliar);
                idProgramaAcademicoNew = em.merge(idProgramaAcademicoNew);
            }
            if (idDireccionOld != null && !idDireccionOld.equals(idDireccionNew)) {
                idDireccionOld.getAuxiliarList().remove(auxiliar);
                idDireccionOld = em.merge(idDireccionOld);
            }
            if (idDireccionNew != null && !idDireccionNew.equals(idDireccionOld)) {
                idDireccionNew.getAuxiliarList().add(auxiliar);
                idDireccionNew = em.merge(idDireccionNew);
            }
            for (SeleccionAuxiliares seleccionAuxiliaresListNewSeleccionAuxiliares : seleccionAuxiliaresListNew) {
                if (!seleccionAuxiliaresListOld.contains(seleccionAuxiliaresListNewSeleccionAuxiliares)) {
                    Auxiliar oldAuxiliarOfSeleccionAuxiliaresListNewSeleccionAuxiliares = seleccionAuxiliaresListNewSeleccionAuxiliares.getAuxiliar();
                    seleccionAuxiliaresListNewSeleccionAuxiliares.setAuxiliar(auxiliar);
                    seleccionAuxiliaresListNewSeleccionAuxiliares = em.merge(seleccionAuxiliaresListNewSeleccionAuxiliares);
                    if (oldAuxiliarOfSeleccionAuxiliaresListNewSeleccionAuxiliares != null && !oldAuxiliarOfSeleccionAuxiliaresListNewSeleccionAuxiliares.equals(auxiliar)) {
                        oldAuxiliarOfSeleccionAuxiliaresListNewSeleccionAuxiliares.getSeleccionAuxiliaresList().remove(seleccionAuxiliaresListNewSeleccionAuxiliares);
                        oldAuxiliarOfSeleccionAuxiliaresListNewSeleccionAuxiliares = em.merge(oldAuxiliarOfSeleccionAuxiliaresListNewSeleccionAuxiliares);
                    }
                }
            }
            for (DetalleResolucion detalleResolucionListNewDetalleResolucion : detalleResolucionListNew) {
                if (!detalleResolucionListOld.contains(detalleResolucionListNewDetalleResolucion)) {
                    Auxiliar oldAuxiliarOfDetalleResolucionListNewDetalleResolucion = detalleResolucionListNewDetalleResolucion.getAuxiliar();
                    detalleResolucionListNewDetalleResolucion.setAuxiliar(auxiliar);
                    detalleResolucionListNewDetalleResolucion = em.merge(detalleResolucionListNewDetalleResolucion);
                    if (oldAuxiliarOfDetalleResolucionListNewDetalleResolucion != null && !oldAuxiliarOfDetalleResolucionListNewDetalleResolucion.equals(auxiliar)) {
                        oldAuxiliarOfDetalleResolucionListNewDetalleResolucion.getDetalleResolucionList().remove(detalleResolucionListNewDetalleResolucion);
                        oldAuxiliarOfDetalleResolucionListNewDetalleResolucion = em.merge(oldAuxiliarOfDetalleResolucionListNewDetalleResolucion);
                    }
                }
            }
            for (InscripcionConvocatoria inscripcionConvocatoriaListNewInscripcionConvocatoria : inscripcionConvocatoriaListNew) {
                if (!inscripcionConvocatoriaListOld.contains(inscripcionConvocatoriaListNewInscripcionConvocatoria)) {
                    Auxiliar oldAuxiliarOfInscripcionConvocatoriaListNewInscripcionConvocatoria = inscripcionConvocatoriaListNewInscripcionConvocatoria.getAuxiliar();
                    inscripcionConvocatoriaListNewInscripcionConvocatoria.setAuxiliar(auxiliar);
                    inscripcionConvocatoriaListNewInscripcionConvocatoria = em.merge(inscripcionConvocatoriaListNewInscripcionConvocatoria);
                    if (oldAuxiliarOfInscripcionConvocatoriaListNewInscripcionConvocatoria != null && !oldAuxiliarOfInscripcionConvocatoriaListNewInscripcionConvocatoria.equals(auxiliar)) {
                        oldAuxiliarOfInscripcionConvocatoriaListNewInscripcionConvocatoria.getInscripcionConvocatoriaList().remove(inscripcionConvocatoriaListNewInscripcionConvocatoria);
                        oldAuxiliarOfInscripcionConvocatoriaListNewInscripcionConvocatoria = em.merge(oldAuxiliarOfInscripcionConvocatoriaListNewInscripcionConvocatoria);
                    }
                }
            }
            for (IncAuxAuxiliar incAuxAuxiliarListNewIncAuxAuxiliar : incAuxAuxiliarListNew) {
                if (!incAuxAuxiliarListOld.contains(incAuxAuxiliarListNewIncAuxAuxiliar)) {
                    Auxiliar oldAuxiliarOfIncAuxAuxiliarListNewIncAuxAuxiliar = incAuxAuxiliarListNewIncAuxAuxiliar.getAuxiliar();
                    incAuxAuxiliarListNewIncAuxAuxiliar.setAuxiliar(auxiliar);
                    incAuxAuxiliarListNewIncAuxAuxiliar = em.merge(incAuxAuxiliarListNewIncAuxAuxiliar);
                    if (oldAuxiliarOfIncAuxAuxiliarListNewIncAuxAuxiliar != null && !oldAuxiliarOfIncAuxAuxiliarListNewIncAuxAuxiliar.equals(auxiliar)) {
                        oldAuxiliarOfIncAuxAuxiliarListNewIncAuxAuxiliar.getIncAuxAuxiliarList().remove(incAuxAuxiliarListNewIncAuxAuxiliar);
                        oldAuxiliarOfIncAuxAuxiliarListNewIncAuxAuxiliar = em.merge(oldAuxiliarOfIncAuxAuxiliarListNewIncAuxAuxiliar);
                    }
                }
            }
            for (AuxCumplimientoActividades auxCumplimientoActividadesListNewAuxCumplimientoActividades : auxCumplimientoActividadesListNew) {
                if (!auxCumplimientoActividadesListOld.contains(auxCumplimientoActividadesListNewAuxCumplimientoActividades)) {
                    Auxiliar oldAuxiliarOfAuxCumplimientoActividadesListNewAuxCumplimientoActividades = auxCumplimientoActividadesListNewAuxCumplimientoActividades.getAuxiliar();
                    auxCumplimientoActividadesListNewAuxCumplimientoActividades.setAuxiliar(auxiliar);
                    auxCumplimientoActividadesListNewAuxCumplimientoActividades = em.merge(auxCumplimientoActividadesListNewAuxCumplimientoActividades);
                    if (oldAuxiliarOfAuxCumplimientoActividadesListNewAuxCumplimientoActividades != null && !oldAuxiliarOfAuxCumplimientoActividadesListNewAuxCumplimientoActividades.equals(auxiliar)) {
                        oldAuxiliarOfAuxCumplimientoActividadesListNewAuxCumplimientoActividades.getAuxCumplimientoActividadesList().remove(auxCumplimientoActividadesListNewAuxCumplimientoActividades);
                        oldAuxiliarOfAuxCumplimientoActividadesListNewAuxCumplimientoActividades = em.merge(oldAuxiliarOfAuxCumplimientoActividadesListNewAuxCumplimientoActividades);
                    }
                }
            }
            for (HorarioActividades horarioActividadesListNewHorarioActividades : horarioActividadesListNew) {
                if (!horarioActividadesListOld.contains(horarioActividadesListNewHorarioActividades)) {
                    Auxiliar oldIdAuxiliarOfHorarioActividadesListNewHorarioActividades = horarioActividadesListNewHorarioActividades.getIdAuxiliar();
                    horarioActividadesListNewHorarioActividades.setIdAuxiliar(auxiliar);
                    horarioActividadesListNewHorarioActividades = em.merge(horarioActividadesListNewHorarioActividades);
                    if (oldIdAuxiliarOfHorarioActividadesListNewHorarioActividades != null && !oldIdAuxiliarOfHorarioActividadesListNewHorarioActividades.equals(auxiliar)) {
                        oldIdAuxiliarOfHorarioActividadesListNewHorarioActividades.getHorarioActividadesList().remove(horarioActividadesListNewHorarioActividades);
                        oldIdAuxiliarOfHorarioActividadesListNewHorarioActividades = em.merge(oldIdAuxiliarOfHorarioActividadesListNewHorarioActividades);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = auxiliar.getAuxiliarId();
                if (findAuxiliar(id) == null) {
                    throw new NonexistentEntityException("The auxiliar with id " + id + " no longer exists.");
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
            Auxiliar auxiliar;
            try {
                auxiliar = em.getReference(Auxiliar.class, id);
                auxiliar.getAuxiliarId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The auxiliar with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SeleccionAuxiliares> seleccionAuxiliaresListOrphanCheck = auxiliar.getSeleccionAuxiliaresList();
            for (SeleccionAuxiliares seleccionAuxiliaresListOrphanCheckSeleccionAuxiliares : seleccionAuxiliaresListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Auxiliar (" + auxiliar + ") cannot be destroyed since the SeleccionAuxiliares " + seleccionAuxiliaresListOrphanCheckSeleccionAuxiliares + " in its seleccionAuxiliaresList field has a non-nullable auxiliar field.");
            }
            List<DetalleResolucion> detalleResolucionListOrphanCheck = auxiliar.getDetalleResolucionList();
            for (DetalleResolucion detalleResolucionListOrphanCheckDetalleResolucion : detalleResolucionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Auxiliar (" + auxiliar + ") cannot be destroyed since the DetalleResolucion " + detalleResolucionListOrphanCheckDetalleResolucion + " in its detalleResolucionList field has a non-nullable auxiliar field.");
            }
            List<InscripcionConvocatoria> inscripcionConvocatoriaListOrphanCheck = auxiliar.getInscripcionConvocatoriaList();
            for (InscripcionConvocatoria inscripcionConvocatoriaListOrphanCheckInscripcionConvocatoria : inscripcionConvocatoriaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Auxiliar (" + auxiliar + ") cannot be destroyed since the InscripcionConvocatoria " + inscripcionConvocatoriaListOrphanCheckInscripcionConvocatoria + " in its inscripcionConvocatoriaList field has a non-nullable auxiliar field.");
            }
            List<IncAuxAuxiliar> incAuxAuxiliarListOrphanCheck = auxiliar.getIncAuxAuxiliarList();
            for (IncAuxAuxiliar incAuxAuxiliarListOrphanCheckIncAuxAuxiliar : incAuxAuxiliarListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Auxiliar (" + auxiliar + ") cannot be destroyed since the IncAuxAuxiliar " + incAuxAuxiliarListOrphanCheckIncAuxAuxiliar + " in its incAuxAuxiliarList field has a non-nullable auxiliar field.");
            }
            List<AuxCumplimientoActividades> auxCumplimientoActividadesListOrphanCheck = auxiliar.getAuxCumplimientoActividadesList();
            for (AuxCumplimientoActividades auxCumplimientoActividadesListOrphanCheckAuxCumplimientoActividades : auxCumplimientoActividadesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Auxiliar (" + auxiliar + ") cannot be destroyed since the AuxCumplimientoActividades " + auxCumplimientoActividadesListOrphanCheckAuxCumplimientoActividades + " in its auxCumplimientoActividadesList field has a non-nullable auxiliar field.");
            }
            List<HorarioActividades> horarioActividadesListOrphanCheck = auxiliar.getHorarioActividadesList();
            for (HorarioActividades horarioActividadesListOrphanCheckHorarioActividades : horarioActividadesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Auxiliar (" + auxiliar + ") cannot be destroyed since the HorarioActividades " + horarioActividadesListOrphanCheckHorarioActividades + " in its horarioActividadesList field has a non-nullable idAuxiliar field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            ProgramaAcademico idProgramaAcademico = auxiliar.getIdProgramaAcademico();
            if (idProgramaAcademico != null) {
                idProgramaAcademico.getAuxiliarList().remove(auxiliar);
                idProgramaAcademico = em.merge(idProgramaAcademico);
            }
            Direccion idDireccion = auxiliar.getIdDireccion();
            if (idDireccion != null) {
                idDireccion.getAuxiliarList().remove(auxiliar);
                idDireccion = em.merge(idDireccion);
            }
            em.remove(auxiliar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Auxiliar> findAuxiliarEntities() {
        return findAuxiliarEntities(true, -1, -1);
    }

    public List<Auxiliar> findAuxiliarEntities(int maxResults, int firstResult) {
        return findAuxiliarEntities(false, maxResults, firstResult);
    }

    private List<Auxiliar> findAuxiliarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Auxiliar.class));
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

    public Auxiliar findAuxiliar(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Auxiliar.class, id);
        } finally {
            em.close();
        }
    }

    public int getAuxiliarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Auxiliar> rt = cq.from(Auxiliar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
