/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;

import Controller.ConsultasJpaController;
import DTO.AuxiliaresPresentadosDTO;
import DTO.EvaluacionesDTO;
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
    
    public List<AuxiliaresPresentadosDTO> getAuxiliaresPresentados(){
        return getJpaController().getAuxiliaresPresentados();
    }
    
    public List<EvaluacionesDTO> getPromedioEvaluaciones(){
        return getJpaController().getPromedioEvaluaciones();
    }
    
    public List<EvaluacionesDTO> getAuxiliaresSeleccionados(){
        return getJpaController().getAuxiliaresSeleccionados();
    }
}
