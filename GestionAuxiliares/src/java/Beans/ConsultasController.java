/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;

import Controller.ConsultasJpaController;
import DTO.AuxiliaresSeleccionadosDTO;
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
    
    public List<AuxiliaresSeleccionadosDTO> getAuxiliaresSeleccionados(){
        return getJpaController().getAuxiliaresSeleccionados();
    }
}
