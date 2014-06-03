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
import Entities.Directores;
import Entities.ProgramaAcademico;
import Entities.Direccion;
import Entities.ConsejoCurricular;
import Entities.Cargo;
import Entities.Area;
import Entities.CumplimientoActividades;
import java.util.ArrayList;
import java.util.List;
import Entities.IncAuxAuxiliar;
import Entities.SolicitudAuxiliares;
import Entities.ResAuxiliaresSeleccionados;
import Entities.EvaluacionAuxiliares;
import Entities.Solicitante;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author DavidMontoya
 */
public class SolicitanteJpaController implements Serializable {

    public SolicitanteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Solicitante solicitante) throws PreexistingEntityException, Exception {
        if (solicitante.getCumplimientoActividadesList() == null) {
            solicitante.setCumplimientoActividadesList(new ArrayList<CumplimientoActividades>());
        }
        if (solicitante.getIncAuxAuxiliarList() == null) {
            solicitante.setIncAuxAuxiliarList(new ArrayList<IncAuxAuxiliar>());
        }
        if (solicitante.getSolicitudAuxiliaresList() == null) {
            solicitante.setSolicitudAuxiliaresList(new ArrayList<SolicitudAuxiliares>());
        }
        if (solicitante.getResAuxiliaresSeleccionadosList() == null) {
            solicitante.setResAuxiliaresSeleccionadosList(new ArrayList<ResAuxiliaresSeleccionados>());
        }
        if (solicitante.getEvaluacionAuxiliaresList() == null) {
            solicitante.setEvaluacionAuxiliaresList(new ArrayList<EvaluacionAuxiliares>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Directores directores = solicitante.getDirectores();
            if (directores != null) {
                directores = em.getReference(directores.getClass(), directores.getDirectoresPK());
                solicitante.setDirectores(directores);
            }
            ProgramaAcademico idProgramaAcademico = solicitante.getIdProgramaAcademico();
            if (idProgramaAcademico != null) {
                idProgramaAcademico = em.getReference(idProgramaAcademico.getClass(), idProgramaAcademico.getProgramaAcademicoId());
                solicitante.setIdProgramaAcademico(idProgramaAcademico);
            }
            Direccion idDireccion = solicitante.getIdDireccion();
            if (idDireccion != null) {
                idDireccion = em.getReference(idDireccion.getClass(), idDireccion.getDireccionId());
                solicitante.setIdDireccion(idDireccion);
            }
            ConsejoCurricular idConsejoCurricular = solicitante.getIdConsejoCurricular();
            if (idConsejoCurricular != null) {
                idConsejoCurricular = em.getReference(idConsejoCurricular.getClass(), idConsejoCurricular.getConsejoCurricularId());
                solicitante.setIdConsejoCurricular(idConsejoCurricular);
            }
            Cargo idCargo = solicitante.getIdCargo();
            if (idCargo != null) {
                idCargo = em.getReference(idCargo.getClass(), idCargo.getCargoId());
                solicitante.setIdCargo(idCargo);
            }
            Area idArea = solicitante.getIdArea();
            if (idArea != null) {
                idArea = em.getReference(idArea.getClass(), idArea.getAreaId());
                solicitante.setIdArea(idArea);
            }
            List<CumplimientoActividades> attachedCumplimientoActividadesList = new ArrayList<CumplimientoActividades>();
            for (CumplimientoActividades cumplimientoActividadesListCumplimientoActividadesToAttach : solicitante.getCumplimientoActividadesList()) {
                cumplimientoActividadesListCumplimientoActividadesToAttach = em.getReference(cumplimientoActividadesListCumplimientoActividadesToAttach.getClass(), cumplimientoActividadesListCumplimientoActividadesToAttach.getCumpActId());
                attachedCumplimientoActividadesList.add(cumplimientoActividadesListCumplimientoActividadesToAttach);
            }
            solicitante.setCumplimientoActividadesList(attachedCumplimientoActividadesList);
            List<IncAuxAuxiliar> attachedIncAuxAuxiliarList = new ArrayList<IncAuxAuxiliar>();
            for (IncAuxAuxiliar incAuxAuxiliarListIncAuxAuxiliarToAttach : solicitante.getIncAuxAuxiliarList()) {
                incAuxAuxiliarListIncAuxAuxiliarToAttach = em.getReference(incAuxAuxiliarListIncAuxAuxiliarToAttach.getClass(), incAuxAuxiliarListIncAuxAuxiliarToAttach.getIncAuxAuxiliarPK());
                attachedIncAuxAuxiliarList.add(incAuxAuxiliarListIncAuxAuxiliarToAttach);
            }
            solicitante.setIncAuxAuxiliarList(attachedIncAuxAuxiliarList);
            List<SolicitudAuxiliares> attachedSolicitudAuxiliaresList = new ArrayList<SolicitudAuxiliares>();
            for (SolicitudAuxiliares solicitudAuxiliaresListSolicitudAuxiliaresToAttach : solicitante.getSolicitudAuxiliaresList()) {
                solicitudAuxiliaresListSolicitudAuxiliaresToAttach = em.getReference(solicitudAuxiliaresListSolicitudAuxiliaresToAttach.getClass(), solicitudAuxiliaresListSolicitudAuxiliaresToAttach.getSolicitudAuxiliaresId());
                attachedSolicitudAuxiliaresList.add(solicitudAuxiliaresListSolicitudAuxiliaresToAttach);
            }
            solicitante.setSolicitudAuxiliaresList(attachedSolicitudAuxiliaresList);
            List<ResAuxiliaresSeleccionados> attachedResAuxiliaresSeleccionadosList = new ArrayList<ResAuxiliaresSeleccionados>();
            for (ResAuxiliaresSeleccionados resAuxiliaresSeleccionadosListResAuxiliaresSeleccionadosToAttach : solicitante.getResAuxiliaresSeleccionadosList()) {
                resAuxiliaresSeleccionadosListResAuxiliaresSeleccionadosToAttach = em.getReference(resAuxiliaresSeleccionadosListResAuxiliaresSeleccionadosToAttach.getClass(), resAuxiliaresSeleccionadosListResAuxiliaresSeleccionadosToAttach.getResAuxSelId());
                attachedResAuxiliaresSeleccionadosList.add(resAuxiliaresSeleccionadosListResAuxiliaresSeleccionadosToAttach);
            }
            solicitante.setResAuxiliaresSeleccionadosList(attachedResAuxiliaresSeleccionadosList);
            List<EvaluacionAuxiliares> attachedEvaluacionAuxiliaresList = new ArrayList<EvaluacionAuxiliares>();
            for (EvaluacionAuxiliares evaluacionAuxiliaresListEvaluacionAuxiliaresToAttach : solicitante.getEvaluacionAuxiliaresList()) {
                evaluacionAuxiliaresListEvaluacionAuxiliaresToAttach = em.getReference(evaluacionAuxiliaresListEvaluacionAuxiliaresToAttach.getClass(), evaluacionAuxiliaresListEvaluacionAuxiliaresToAttach.getEvaluacionAuxiliaresId());
                attachedEvaluacionAuxiliaresList.add(evaluacionAuxiliaresListEvaluacionAuxiliaresToAttach);
            }
            solicitante.setEvaluacionAuxiliaresList(attachedEvaluacionAuxiliaresList);
            em.persist(solicitante);
            if (directores != null) {
                Solicitante oldIdSolicitanteOfDirectores = directores.getIdSolicitante();
                if (oldIdSolicitanteOfDirectores != null) {
                    oldIdSolicitanteOfDirectores.setDirectores(null);
                    oldIdSolicitanteOfDirectores = em.merge(oldIdSolicitanteOfDirectores);
                }
                directores.setIdSolicitante(solicitante);
                directores = em.merge(directores);
            }
            if (idProgramaAcademico != null) {
                idProgramaAcademico.getSolicitanteList().add(solicitante);
                idProgramaAcademico = em.merge(idProgramaAcademico);
            }
            if (idDireccion != null) {
                idDireccion.getSolicitanteList().add(solicitante);
                idDireccion = em.merge(idDireccion);
            }
            if (idConsejoCurricular != null) {
                idConsejoCurricular.getSolicitanteList().add(solicitante);
                idConsejoCurricular = em.merge(idConsejoCurricular);
            }
            if (idCargo != null) {
                idCargo.getSolicitanteList().add(solicitante);
                idCargo = em.merge(idCargo);
            }
            if (idArea != null) {
                idArea.getSolicitanteList().add(solicitante);
                idArea = em.merge(idArea);
            }
            for (CumplimientoActividades cumplimientoActividadesListCumplimientoActividades : solicitante.getCumplimientoActividadesList()) {
                Solicitante oldIdSolicitanteOfCumplimientoActividadesListCumplimientoActividades = cumplimientoActividadesListCumplimientoActividades.getIdSolicitante();
                cumplimientoActividadesListCumplimientoActividades.setIdSolicitante(solicitante);
                cumplimientoActividadesListCumplimientoActividades = em.merge(cumplimientoActividadesListCumplimientoActividades);
                if (oldIdSolicitanteOfCumplimientoActividadesListCumplimientoActividades != null) {
                    oldIdSolicitanteOfCumplimientoActividadesListCumplimientoActividades.getCumplimientoActividadesList().remove(cumplimientoActividadesListCumplimientoActividades);
                    oldIdSolicitanteOfCumplimientoActividadesListCumplimientoActividades = em.merge(oldIdSolicitanteOfCumplimientoActividadesListCumplimientoActividades);
                }
            }
            for (IncAuxAuxiliar incAuxAuxiliarListIncAuxAuxiliar : solicitante.getIncAuxAuxiliarList()) {
                Solicitante oldIdSolicitanteOfIncAuxAuxiliarListIncAuxAuxiliar = incAuxAuxiliarListIncAuxAuxiliar.getIdSolicitante();
                incAuxAuxiliarListIncAuxAuxiliar.setIdSolicitante(solicitante);
                incAuxAuxiliarListIncAuxAuxiliar = em.merge(incAuxAuxiliarListIncAuxAuxiliar);
                if (oldIdSolicitanteOfIncAuxAuxiliarListIncAuxAuxiliar != null) {
                    oldIdSolicitanteOfIncAuxAuxiliarListIncAuxAuxiliar.getIncAuxAuxiliarList().remove(incAuxAuxiliarListIncAuxAuxiliar);
                    oldIdSolicitanteOfIncAuxAuxiliarListIncAuxAuxiliar = em.merge(oldIdSolicitanteOfIncAuxAuxiliarListIncAuxAuxiliar);
                }
            }
            for (SolicitudAuxiliares solicitudAuxiliaresListSolicitudAuxiliares : solicitante.getSolicitudAuxiliaresList()) {
                Solicitante oldIdSolicitanteOfSolicitudAuxiliaresListSolicitudAuxiliares = solicitudAuxiliaresListSolicitudAuxiliares.getIdSolicitante();
                solicitudAuxiliaresListSolicitudAuxiliares.setIdSolicitante(solicitante);
                solicitudAuxiliaresListSolicitudAuxiliares = em.merge(solicitudAuxiliaresListSolicitudAuxiliares);
                if (oldIdSolicitanteOfSolicitudAuxiliaresListSolicitudAuxiliares != null) {
                    oldIdSolicitanteOfSolicitudAuxiliaresListSolicitudAuxiliares.getSolicitudAuxiliaresList().remove(solicitudAuxiliaresListSolicitudAuxiliares);
                    oldIdSolicitanteOfSolicitudAuxiliaresListSolicitudAuxiliares = em.merge(oldIdSolicitanteOfSolicitudAuxiliaresListSolicitudAuxiliares);
                }
            }
            for (ResAuxiliaresSeleccionados resAuxiliaresSeleccionadosListResAuxiliaresSeleccionados : solicitante.getResAuxiliaresSeleccionadosList()) {
                Solicitante oldIdSolicitanteOfResAuxiliaresSeleccionadosListResAuxiliaresSeleccionados = resAuxiliaresSeleccionadosListResAuxiliaresSeleccionados.getIdSolicitante();
                resAuxiliaresSeleccionadosListResAuxiliaresSeleccionados.setIdSolicitante(solicitante);
                resAuxiliaresSeleccionadosListResAuxiliaresSeleccionados = em.merge(resAuxiliaresSeleccionadosListResAuxiliaresSeleccionados);
                if (oldIdSolicitanteOfResAuxiliaresSeleccionadosListResAuxiliaresSeleccionados != null) {
                    oldIdSolicitanteOfResAuxiliaresSeleccionadosListResAuxiliaresSeleccionados.getResAuxiliaresSeleccionadosList().remove(resAuxiliaresSeleccionadosListResAuxiliaresSeleccionados);
                    oldIdSolicitanteOfResAuxiliaresSeleccionadosListResAuxiliaresSeleccionados = em.merge(oldIdSolicitanteOfResAuxiliaresSeleccionadosListResAuxiliaresSeleccionados);
                }
            }
            for (EvaluacionAuxiliares evaluacionAuxiliaresListEvaluacionAuxiliares : solicitante.getEvaluacionAuxiliaresList()) {
                Solicitante oldIdSolicitanteOfEvaluacionAuxiliaresListEvaluacionAuxiliares = evaluacionAuxiliaresListEvaluacionAuxiliares.getIdSolicitante();
                evaluacionAuxiliaresListEvaluacionAuxiliares.setIdSolicitante(solicitante);
                evaluacionAuxiliaresListEvaluacionAuxiliares = em.merge(evaluacionAuxiliaresListEvaluacionAuxiliares);
                if (oldIdSolicitanteOfEvaluacionAuxiliaresListEvaluacionAuxiliares != null) {
                    oldIdSolicitanteOfEvaluacionAuxiliaresListEvaluacionAuxiliares.getEvaluacionAuxiliaresList().remove(evaluacionAuxiliaresListEvaluacionAuxiliares);
                    oldIdSolicitanteOfEvaluacionAuxiliaresListEvaluacionAuxiliares = em.merge(oldIdSolicitanteOfEvaluacionAuxiliaresListEvaluacionAuxiliares);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSolicitante(solicitante.getSolicitanteId()) != null) {
                throw new PreexistingEntityException("Solicitante " + solicitante + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Solicitante solicitante) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Solicitante persistentSolicitante = em.find(Solicitante.class, solicitante.getSolicitanteId());
            Directores directoresOld = persistentSolicitante.getDirectores();
            Directores directoresNew = solicitante.getDirectores();
            ProgramaAcademico idProgramaAcademicoOld = persistentSolicitante.getIdProgramaAcademico();
            ProgramaAcademico idProgramaAcademicoNew = solicitante.getIdProgramaAcademico();
            Direccion idDireccionOld = persistentSolicitante.getIdDireccion();
            Direccion idDireccionNew = solicitante.getIdDireccion();
            ConsejoCurricular idConsejoCurricularOld = persistentSolicitante.getIdConsejoCurricular();
            ConsejoCurricular idConsejoCurricularNew = solicitante.getIdConsejoCurricular();
            Cargo idCargoOld = persistentSolicitante.getIdCargo();
            Cargo idCargoNew = solicitante.getIdCargo();
            Area idAreaOld = persistentSolicitante.getIdArea();
            Area idAreaNew = solicitante.getIdArea();
            List<CumplimientoActividades> cumplimientoActividadesListOld = persistentSolicitante.getCumplimientoActividadesList();
            List<CumplimientoActividades> cumplimientoActividadesListNew = solicitante.getCumplimientoActividadesList();
            List<IncAuxAuxiliar> incAuxAuxiliarListOld = persistentSolicitante.getIncAuxAuxiliarList();
            List<IncAuxAuxiliar> incAuxAuxiliarListNew = solicitante.getIncAuxAuxiliarList();
            List<SolicitudAuxiliares> solicitudAuxiliaresListOld = persistentSolicitante.getSolicitudAuxiliaresList();
            List<SolicitudAuxiliares> solicitudAuxiliaresListNew = solicitante.getSolicitudAuxiliaresList();
            List<ResAuxiliaresSeleccionados> resAuxiliaresSeleccionadosListOld = persistentSolicitante.getResAuxiliaresSeleccionadosList();
            List<ResAuxiliaresSeleccionados> resAuxiliaresSeleccionadosListNew = solicitante.getResAuxiliaresSeleccionadosList();
            List<EvaluacionAuxiliares> evaluacionAuxiliaresListOld = persistentSolicitante.getEvaluacionAuxiliaresList();
            List<EvaluacionAuxiliares> evaluacionAuxiliaresListNew = solicitante.getEvaluacionAuxiliaresList();
            List<String> illegalOrphanMessages = null;
            if (directoresOld != null && !directoresOld.equals(directoresNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Directores " + directoresOld + " since its idSolicitante field is not nullable.");
            }
            for (CumplimientoActividades cumplimientoActividadesListOldCumplimientoActividades : cumplimientoActividadesListOld) {
                if (!cumplimientoActividadesListNew.contains(cumplimientoActividadesListOldCumplimientoActividades)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CumplimientoActividades " + cumplimientoActividadesListOldCumplimientoActividades + " since its idSolicitante field is not nullable.");
                }
            }
            for (IncAuxAuxiliar incAuxAuxiliarListOldIncAuxAuxiliar : incAuxAuxiliarListOld) {
                if (!incAuxAuxiliarListNew.contains(incAuxAuxiliarListOldIncAuxAuxiliar)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain IncAuxAuxiliar " + incAuxAuxiliarListOldIncAuxAuxiliar + " since its idSolicitante field is not nullable.");
                }
            }
            for (SolicitudAuxiliares solicitudAuxiliaresListOldSolicitudAuxiliares : solicitudAuxiliaresListOld) {
                if (!solicitudAuxiliaresListNew.contains(solicitudAuxiliaresListOldSolicitudAuxiliares)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SolicitudAuxiliares " + solicitudAuxiliaresListOldSolicitudAuxiliares + " since its idSolicitante field is not nullable.");
                }
            }
            for (ResAuxiliaresSeleccionados resAuxiliaresSeleccionadosListOldResAuxiliaresSeleccionados : resAuxiliaresSeleccionadosListOld) {
                if (!resAuxiliaresSeleccionadosListNew.contains(resAuxiliaresSeleccionadosListOldResAuxiliaresSeleccionados)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ResAuxiliaresSeleccionados " + resAuxiliaresSeleccionadosListOldResAuxiliaresSeleccionados + " since its idSolicitante field is not nullable.");
                }
            }
            for (EvaluacionAuxiliares evaluacionAuxiliaresListOldEvaluacionAuxiliares : evaluacionAuxiliaresListOld) {
                if (!evaluacionAuxiliaresListNew.contains(evaluacionAuxiliaresListOldEvaluacionAuxiliares)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EvaluacionAuxiliares " + evaluacionAuxiliaresListOldEvaluacionAuxiliares + " since its idSolicitante field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (directoresNew != null) {
                directoresNew = em.getReference(directoresNew.getClass(), directoresNew.getDirectoresPK());
                solicitante.setDirectores(directoresNew);
            }
            if (idProgramaAcademicoNew != null) {
                idProgramaAcademicoNew = em.getReference(idProgramaAcademicoNew.getClass(), idProgramaAcademicoNew.getProgramaAcademicoId());
                solicitante.setIdProgramaAcademico(idProgramaAcademicoNew);
            }
            if (idDireccionNew != null) {
                idDireccionNew = em.getReference(idDireccionNew.getClass(), idDireccionNew.getDireccionId());
                solicitante.setIdDireccion(idDireccionNew);
            }
            if (idConsejoCurricularNew != null) {
                idConsejoCurricularNew = em.getReference(idConsejoCurricularNew.getClass(), idConsejoCurricularNew.getConsejoCurricularId());
                solicitante.setIdConsejoCurricular(idConsejoCurricularNew);
            }
            if (idCargoNew != null) {
                idCargoNew = em.getReference(idCargoNew.getClass(), idCargoNew.getCargoId());
                solicitante.setIdCargo(idCargoNew);
            }
            if (idAreaNew != null) {
                idAreaNew = em.getReference(idAreaNew.getClass(), idAreaNew.getAreaId());
                solicitante.setIdArea(idAreaNew);
            }
            List<CumplimientoActividades> attachedCumplimientoActividadesListNew = new ArrayList<CumplimientoActividades>();
            for (CumplimientoActividades cumplimientoActividadesListNewCumplimientoActividadesToAttach : cumplimientoActividadesListNew) {
                cumplimientoActividadesListNewCumplimientoActividadesToAttach = em.getReference(cumplimientoActividadesListNewCumplimientoActividadesToAttach.getClass(), cumplimientoActividadesListNewCumplimientoActividadesToAttach.getCumpActId());
                attachedCumplimientoActividadesListNew.add(cumplimientoActividadesListNewCumplimientoActividadesToAttach);
            }
            cumplimientoActividadesListNew = attachedCumplimientoActividadesListNew;
            solicitante.setCumplimientoActividadesList(cumplimientoActividadesListNew);
            List<IncAuxAuxiliar> attachedIncAuxAuxiliarListNew = new ArrayList<IncAuxAuxiliar>();
            for (IncAuxAuxiliar incAuxAuxiliarListNewIncAuxAuxiliarToAttach : incAuxAuxiliarListNew) {
                incAuxAuxiliarListNewIncAuxAuxiliarToAttach = em.getReference(incAuxAuxiliarListNewIncAuxAuxiliarToAttach.getClass(), incAuxAuxiliarListNewIncAuxAuxiliarToAttach.getIncAuxAuxiliarPK());
                attachedIncAuxAuxiliarListNew.add(incAuxAuxiliarListNewIncAuxAuxiliarToAttach);
            }
            incAuxAuxiliarListNew = attachedIncAuxAuxiliarListNew;
            solicitante.setIncAuxAuxiliarList(incAuxAuxiliarListNew);
            List<SolicitudAuxiliares> attachedSolicitudAuxiliaresListNew = new ArrayList<SolicitudAuxiliares>();
            for (SolicitudAuxiliares solicitudAuxiliaresListNewSolicitudAuxiliaresToAttach : solicitudAuxiliaresListNew) {
                solicitudAuxiliaresListNewSolicitudAuxiliaresToAttach = em.getReference(solicitudAuxiliaresListNewSolicitudAuxiliaresToAttach.getClass(), solicitudAuxiliaresListNewSolicitudAuxiliaresToAttach.getSolicitudAuxiliaresId());
                attachedSolicitudAuxiliaresListNew.add(solicitudAuxiliaresListNewSolicitudAuxiliaresToAttach);
            }
            solicitudAuxiliaresListNew = attachedSolicitudAuxiliaresListNew;
            solicitante.setSolicitudAuxiliaresList(solicitudAuxiliaresListNew);
            List<ResAuxiliaresSeleccionados> attachedResAuxiliaresSeleccionadosListNew = new ArrayList<ResAuxiliaresSeleccionados>();
            for (ResAuxiliaresSeleccionados resAuxiliaresSeleccionadosListNewResAuxiliaresSeleccionadosToAttach : resAuxiliaresSeleccionadosListNew) {
                resAuxiliaresSeleccionadosListNewResAuxiliaresSeleccionadosToAttach = em.getReference(resAuxiliaresSeleccionadosListNewResAuxiliaresSeleccionadosToAttach.getClass(), resAuxiliaresSeleccionadosListNewResAuxiliaresSeleccionadosToAttach.getResAuxSelId());
                attachedResAuxiliaresSeleccionadosListNew.add(resAuxiliaresSeleccionadosListNewResAuxiliaresSeleccionadosToAttach);
            }
            resAuxiliaresSeleccionadosListNew = attachedResAuxiliaresSeleccionadosListNew;
            solicitante.setResAuxiliaresSeleccionadosList(resAuxiliaresSeleccionadosListNew);
            List<EvaluacionAuxiliares> attachedEvaluacionAuxiliaresListNew = new ArrayList<EvaluacionAuxiliares>();
            for (EvaluacionAuxiliares evaluacionAuxiliaresListNewEvaluacionAuxiliaresToAttach : evaluacionAuxiliaresListNew) {
                evaluacionAuxiliaresListNewEvaluacionAuxiliaresToAttach = em.getReference(evaluacionAuxiliaresListNewEvaluacionAuxiliaresToAttach.getClass(), evaluacionAuxiliaresListNewEvaluacionAuxiliaresToAttach.getEvaluacionAuxiliaresId());
                attachedEvaluacionAuxiliaresListNew.add(evaluacionAuxiliaresListNewEvaluacionAuxiliaresToAttach);
            }
            evaluacionAuxiliaresListNew = attachedEvaluacionAuxiliaresListNew;
            solicitante.setEvaluacionAuxiliaresList(evaluacionAuxiliaresListNew);
            solicitante = em.merge(solicitante);
            if (directoresNew != null && !directoresNew.equals(directoresOld)) {
                Solicitante oldIdSolicitanteOfDirectores = directoresNew.getIdSolicitante();
                if (oldIdSolicitanteOfDirectores != null) {
                    oldIdSolicitanteOfDirectores.setDirectores(null);
                    oldIdSolicitanteOfDirectores = em.merge(oldIdSolicitanteOfDirectores);
                }
                directoresNew.setIdSolicitante(solicitante);
                directoresNew = em.merge(directoresNew);
            }
            if (idProgramaAcademicoOld != null && !idProgramaAcademicoOld.equals(idProgramaAcademicoNew)) {
                idProgramaAcademicoOld.getSolicitanteList().remove(solicitante);
                idProgramaAcademicoOld = em.merge(idProgramaAcademicoOld);
            }
            if (idProgramaAcademicoNew != null && !idProgramaAcademicoNew.equals(idProgramaAcademicoOld)) {
                idProgramaAcademicoNew.getSolicitanteList().add(solicitante);
                idProgramaAcademicoNew = em.merge(idProgramaAcademicoNew);
            }
            if (idDireccionOld != null && !idDireccionOld.equals(idDireccionNew)) {
                idDireccionOld.getSolicitanteList().remove(solicitante);
                idDireccionOld = em.merge(idDireccionOld);
            }
            if (idDireccionNew != null && !idDireccionNew.equals(idDireccionOld)) {
                idDireccionNew.getSolicitanteList().add(solicitante);
                idDireccionNew = em.merge(idDireccionNew);
            }
            if (idConsejoCurricularOld != null && !idConsejoCurricularOld.equals(idConsejoCurricularNew)) {
                idConsejoCurricularOld.getSolicitanteList().remove(solicitante);
                idConsejoCurricularOld = em.merge(idConsejoCurricularOld);
            }
            if (idConsejoCurricularNew != null && !idConsejoCurricularNew.equals(idConsejoCurricularOld)) {
                idConsejoCurricularNew.getSolicitanteList().add(solicitante);
                idConsejoCurricularNew = em.merge(idConsejoCurricularNew);
            }
            if (idCargoOld != null && !idCargoOld.equals(idCargoNew)) {
                idCargoOld.getSolicitanteList().remove(solicitante);
                idCargoOld = em.merge(idCargoOld);
            }
            if (idCargoNew != null && !idCargoNew.equals(idCargoOld)) {
                idCargoNew.getSolicitanteList().add(solicitante);
                idCargoNew = em.merge(idCargoNew);
            }
            if (idAreaOld != null && !idAreaOld.equals(idAreaNew)) {
                idAreaOld.getSolicitanteList().remove(solicitante);
                idAreaOld = em.merge(idAreaOld);
            }
            if (idAreaNew != null && !idAreaNew.equals(idAreaOld)) {
                idAreaNew.getSolicitanteList().add(solicitante);
                idAreaNew = em.merge(idAreaNew);
            }
            for (CumplimientoActividades cumplimientoActividadesListNewCumplimientoActividades : cumplimientoActividadesListNew) {
                if (!cumplimientoActividadesListOld.contains(cumplimientoActividadesListNewCumplimientoActividades)) {
                    Solicitante oldIdSolicitanteOfCumplimientoActividadesListNewCumplimientoActividades = cumplimientoActividadesListNewCumplimientoActividades.getIdSolicitante();
                    cumplimientoActividadesListNewCumplimientoActividades.setIdSolicitante(solicitante);
                    cumplimientoActividadesListNewCumplimientoActividades = em.merge(cumplimientoActividadesListNewCumplimientoActividades);
                    if (oldIdSolicitanteOfCumplimientoActividadesListNewCumplimientoActividades != null && !oldIdSolicitanteOfCumplimientoActividadesListNewCumplimientoActividades.equals(solicitante)) {
                        oldIdSolicitanteOfCumplimientoActividadesListNewCumplimientoActividades.getCumplimientoActividadesList().remove(cumplimientoActividadesListNewCumplimientoActividades);
                        oldIdSolicitanteOfCumplimientoActividadesListNewCumplimientoActividades = em.merge(oldIdSolicitanteOfCumplimientoActividadesListNewCumplimientoActividades);
                    }
                }
            }
            for (IncAuxAuxiliar incAuxAuxiliarListNewIncAuxAuxiliar : incAuxAuxiliarListNew) {
                if (!incAuxAuxiliarListOld.contains(incAuxAuxiliarListNewIncAuxAuxiliar)) {
                    Solicitante oldIdSolicitanteOfIncAuxAuxiliarListNewIncAuxAuxiliar = incAuxAuxiliarListNewIncAuxAuxiliar.getIdSolicitante();
                    incAuxAuxiliarListNewIncAuxAuxiliar.setIdSolicitante(solicitante);
                    incAuxAuxiliarListNewIncAuxAuxiliar = em.merge(incAuxAuxiliarListNewIncAuxAuxiliar);
                    if (oldIdSolicitanteOfIncAuxAuxiliarListNewIncAuxAuxiliar != null && !oldIdSolicitanteOfIncAuxAuxiliarListNewIncAuxAuxiliar.equals(solicitante)) {
                        oldIdSolicitanteOfIncAuxAuxiliarListNewIncAuxAuxiliar.getIncAuxAuxiliarList().remove(incAuxAuxiliarListNewIncAuxAuxiliar);
                        oldIdSolicitanteOfIncAuxAuxiliarListNewIncAuxAuxiliar = em.merge(oldIdSolicitanteOfIncAuxAuxiliarListNewIncAuxAuxiliar);
                    }
                }
            }
            for (SolicitudAuxiliares solicitudAuxiliaresListNewSolicitudAuxiliares : solicitudAuxiliaresListNew) {
                if (!solicitudAuxiliaresListOld.contains(solicitudAuxiliaresListNewSolicitudAuxiliares)) {
                    Solicitante oldIdSolicitanteOfSolicitudAuxiliaresListNewSolicitudAuxiliares = solicitudAuxiliaresListNewSolicitudAuxiliares.getIdSolicitante();
                    solicitudAuxiliaresListNewSolicitudAuxiliares.setIdSolicitante(solicitante);
                    solicitudAuxiliaresListNewSolicitudAuxiliares = em.merge(solicitudAuxiliaresListNewSolicitudAuxiliares);
                    if (oldIdSolicitanteOfSolicitudAuxiliaresListNewSolicitudAuxiliares != null && !oldIdSolicitanteOfSolicitudAuxiliaresListNewSolicitudAuxiliares.equals(solicitante)) {
                        oldIdSolicitanteOfSolicitudAuxiliaresListNewSolicitudAuxiliares.getSolicitudAuxiliaresList().remove(solicitudAuxiliaresListNewSolicitudAuxiliares);
                        oldIdSolicitanteOfSolicitudAuxiliaresListNewSolicitudAuxiliares = em.merge(oldIdSolicitanteOfSolicitudAuxiliaresListNewSolicitudAuxiliares);
                    }
                }
            }
            for (ResAuxiliaresSeleccionados resAuxiliaresSeleccionadosListNewResAuxiliaresSeleccionados : resAuxiliaresSeleccionadosListNew) {
                if (!resAuxiliaresSeleccionadosListOld.contains(resAuxiliaresSeleccionadosListNewResAuxiliaresSeleccionados)) {
                    Solicitante oldIdSolicitanteOfResAuxiliaresSeleccionadosListNewResAuxiliaresSeleccionados = resAuxiliaresSeleccionadosListNewResAuxiliaresSeleccionados.getIdSolicitante();
                    resAuxiliaresSeleccionadosListNewResAuxiliaresSeleccionados.setIdSolicitante(solicitante);
                    resAuxiliaresSeleccionadosListNewResAuxiliaresSeleccionados = em.merge(resAuxiliaresSeleccionadosListNewResAuxiliaresSeleccionados);
                    if (oldIdSolicitanteOfResAuxiliaresSeleccionadosListNewResAuxiliaresSeleccionados != null && !oldIdSolicitanteOfResAuxiliaresSeleccionadosListNewResAuxiliaresSeleccionados.equals(solicitante)) {
                        oldIdSolicitanteOfResAuxiliaresSeleccionadosListNewResAuxiliaresSeleccionados.getResAuxiliaresSeleccionadosList().remove(resAuxiliaresSeleccionadosListNewResAuxiliaresSeleccionados);
                        oldIdSolicitanteOfResAuxiliaresSeleccionadosListNewResAuxiliaresSeleccionados = em.merge(oldIdSolicitanteOfResAuxiliaresSeleccionadosListNewResAuxiliaresSeleccionados);
                    }
                }
            }
            for (EvaluacionAuxiliares evaluacionAuxiliaresListNewEvaluacionAuxiliares : evaluacionAuxiliaresListNew) {
                if (!evaluacionAuxiliaresListOld.contains(evaluacionAuxiliaresListNewEvaluacionAuxiliares)) {
                    Solicitante oldIdSolicitanteOfEvaluacionAuxiliaresListNewEvaluacionAuxiliares = evaluacionAuxiliaresListNewEvaluacionAuxiliares.getIdSolicitante();
                    evaluacionAuxiliaresListNewEvaluacionAuxiliares.setIdSolicitante(solicitante);
                    evaluacionAuxiliaresListNewEvaluacionAuxiliares = em.merge(evaluacionAuxiliaresListNewEvaluacionAuxiliares);
                    if (oldIdSolicitanteOfEvaluacionAuxiliaresListNewEvaluacionAuxiliares != null && !oldIdSolicitanteOfEvaluacionAuxiliaresListNewEvaluacionAuxiliares.equals(solicitante)) {
                        oldIdSolicitanteOfEvaluacionAuxiliaresListNewEvaluacionAuxiliares.getEvaluacionAuxiliaresList().remove(evaluacionAuxiliaresListNewEvaluacionAuxiliares);
                        oldIdSolicitanteOfEvaluacionAuxiliaresListNewEvaluacionAuxiliares = em.merge(oldIdSolicitanteOfEvaluacionAuxiliaresListNewEvaluacionAuxiliares);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = solicitante.getSolicitanteId();
                if (findSolicitante(id) == null) {
                    throw new NonexistentEntityException("The solicitante with id " + id + " no longer exists.");
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
            Solicitante solicitante;
            try {
                solicitante = em.getReference(Solicitante.class, id);
                solicitante.getSolicitanteId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The solicitante with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Directores directoresOrphanCheck = solicitante.getDirectores();
            if (directoresOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Solicitante (" + solicitante + ") cannot be destroyed since the Directores " + directoresOrphanCheck + " in its directores field has a non-nullable idSolicitante field.");
            }
            List<CumplimientoActividades> cumplimientoActividadesListOrphanCheck = solicitante.getCumplimientoActividadesList();
            for (CumplimientoActividades cumplimientoActividadesListOrphanCheckCumplimientoActividades : cumplimientoActividadesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Solicitante (" + solicitante + ") cannot be destroyed since the CumplimientoActividades " + cumplimientoActividadesListOrphanCheckCumplimientoActividades + " in its cumplimientoActividadesList field has a non-nullable idSolicitante field.");
            }
            List<IncAuxAuxiliar> incAuxAuxiliarListOrphanCheck = solicitante.getIncAuxAuxiliarList();
            for (IncAuxAuxiliar incAuxAuxiliarListOrphanCheckIncAuxAuxiliar : incAuxAuxiliarListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Solicitante (" + solicitante + ") cannot be destroyed since the IncAuxAuxiliar " + incAuxAuxiliarListOrphanCheckIncAuxAuxiliar + " in its incAuxAuxiliarList field has a non-nullable idSolicitante field.");
            }
            List<SolicitudAuxiliares> solicitudAuxiliaresListOrphanCheck = solicitante.getSolicitudAuxiliaresList();
            for (SolicitudAuxiliares solicitudAuxiliaresListOrphanCheckSolicitudAuxiliares : solicitudAuxiliaresListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Solicitante (" + solicitante + ") cannot be destroyed since the SolicitudAuxiliares " + solicitudAuxiliaresListOrphanCheckSolicitudAuxiliares + " in its solicitudAuxiliaresList field has a non-nullable idSolicitante field.");
            }
            List<ResAuxiliaresSeleccionados> resAuxiliaresSeleccionadosListOrphanCheck = solicitante.getResAuxiliaresSeleccionadosList();
            for (ResAuxiliaresSeleccionados resAuxiliaresSeleccionadosListOrphanCheckResAuxiliaresSeleccionados : resAuxiliaresSeleccionadosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Solicitante (" + solicitante + ") cannot be destroyed since the ResAuxiliaresSeleccionados " + resAuxiliaresSeleccionadosListOrphanCheckResAuxiliaresSeleccionados + " in its resAuxiliaresSeleccionadosList field has a non-nullable idSolicitante field.");
            }
            List<EvaluacionAuxiliares> evaluacionAuxiliaresListOrphanCheck = solicitante.getEvaluacionAuxiliaresList();
            for (EvaluacionAuxiliares evaluacionAuxiliaresListOrphanCheckEvaluacionAuxiliares : evaluacionAuxiliaresListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Solicitante (" + solicitante + ") cannot be destroyed since the EvaluacionAuxiliares " + evaluacionAuxiliaresListOrphanCheckEvaluacionAuxiliares + " in its evaluacionAuxiliaresList field has a non-nullable idSolicitante field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            ProgramaAcademico idProgramaAcademico = solicitante.getIdProgramaAcademico();
            if (idProgramaAcademico != null) {
                idProgramaAcademico.getSolicitanteList().remove(solicitante);
                idProgramaAcademico = em.merge(idProgramaAcademico);
            }
            Direccion idDireccion = solicitante.getIdDireccion();
            if (idDireccion != null) {
                idDireccion.getSolicitanteList().remove(solicitante);
                idDireccion = em.merge(idDireccion);
            }
            ConsejoCurricular idConsejoCurricular = solicitante.getIdConsejoCurricular();
            if (idConsejoCurricular != null) {
                idConsejoCurricular.getSolicitanteList().remove(solicitante);
                idConsejoCurricular = em.merge(idConsejoCurricular);
            }
            Cargo idCargo = solicitante.getIdCargo();
            if (idCargo != null) {
                idCargo.getSolicitanteList().remove(solicitante);
                idCargo = em.merge(idCargo);
            }
            Area idArea = solicitante.getIdArea();
            if (idArea != null) {
                idArea.getSolicitanteList().remove(solicitante);
                idArea = em.merge(idArea);
            }
            em.remove(solicitante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Solicitante> findSolicitanteEntities() {
        return findSolicitanteEntities(true, -1, -1);
    }

    public List<Solicitante> findSolicitanteEntities(int maxResults, int firstResult) {
        return findSolicitanteEntities(false, maxResults, firstResult);
    }

    private List<Solicitante> findSolicitanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Solicitante.class));
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

    public Solicitante findSolicitante(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Solicitante.class, id);
        } finally {
            em.close();
        }
    }

    public int getSolicitanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Solicitante> rt = cq.from(Solicitante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
