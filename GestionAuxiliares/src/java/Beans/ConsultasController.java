/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;

import Controller.ConsultasJpaController;
import DTO.AuxiliaresPresentadosDTO;
import DTO.CumplimientoActividadesDTO;
import DTO.EvaluacionesDTO;
import DTO.HorarioAuxiliarDTO;
import DTO.SolicitudDTO;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.persistence.Persistence;

/**
 *
 * @author DavidMontoya
 */
@ManagedBean(name = "consultasController")
@SessionScoped
public class ConsultasController {

    private ConsultasJpaController jpaController;
    /**
     * Creates a new instance of ConsultasController
     */
    public ConsultasController() {
    }
    
    private ConsultasJpaController getJpaController() {
        if (jpaController == null) {
            jpaController = new ConsultasJpaController(Persistence.createEntityManagerFactory("GestionAuxiliaresPU"));
        }
        return jpaController;
    }
    
    /**
     * Metodo que se conecta con el controller para traer los auxiliares presentados en las convocatorias.
     * @return una lista de AuxiliaresPresentadosDTO para su facil acceso a los datos consultados.
     */
    public List<AuxiliaresPresentadosDTO> getAuxiliaresPresentados(){
        return getJpaController().getAuxiliaresPresentados();
    }
    
    /**
     * Metodo que se contecta con el controller para traer el promedio de calificacion de las evaluaciones.
     * @return una lista de EvaluacionesDTO para su facil acceso a los datos consultados.
     */
    public List<EvaluacionesDTO> getPromedioEvaluaciones(){
        return getJpaController().getPromedioEvaluaciones();
    }
    
    /**
     * Metodo que se conecta con el controller para traer los auxiliares seleccionados en las convocatorias.
     * @return una lista de EvaluacionesDTO para su facil acceso a los datos consultados.
     */
    public List<EvaluacionesDTO> getAuxiliaresSeleccionados(){
        return getJpaController().getAuxiliaresSeleccionados();
    }
    
    /**
     * Metodo que se conecta con el controller para traer las solicitudes hechas por los solicitantes.
     * @return una lista de SolicitudDTO para su facil acceso a los datos consultados.
     */
    public List<SolicitudDTO> getSolicitudes(){
        return getJpaController().getSolicitudes();
    }
    
    /**
     * Metodo que se conecta con el controller para traer los horarios de cada auxiliares.
     * @return una lista de HorarioAuxiliarDTO para su facil acceso a los datos consultados.
     */
    public List<HorarioAuxiliarDTO> getHorarioAuxiliares(){
        return getJpaController().getHorarioAuxiliares();
    }
    
    /**
     * Metodo que se conecta con el controller para traer los auxiliares que cumplieron exitosamente con las actividades planteadas.
     * @return una lista de CumplimientoActividadesDTO para su facil acceso a los datos consultados.
     */
    public List<CumplimientoActividadesDTO> getAuxiliaresCumplieron(){
        return getJpaController().getAuxiliaresCumplieron();
    }
    
    /**
     * Metodo que se conecta con el controller para traer la cantidad total de auxiliares solicitados por cada solicitante.
     * @return una lista de SolicitudDTO para su facil acceso a los datos consultados.
     */
    public List<SolicitudDTO> getTotalidadAuxiliaresSolicitados(){
        return getJpaController().getTotalidadAuxiliaresSolicitados();
    }
}
