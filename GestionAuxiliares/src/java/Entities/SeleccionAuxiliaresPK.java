/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author DavidMontoya
 */
@Embeddable
public class SeleccionAuxiliaresPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_AUXILIARES", nullable = false, length = 20)
    private String idAuxiliares;
    @Basic(optional = false)
    @Column(name = "ID_EVALUACION_AUXILIARES", nullable = false, length = 20)
    private String idEvaluacionAuxiliares;

    public SeleccionAuxiliaresPK() {
    }

    public SeleccionAuxiliaresPK(String idAuxiliares, String idEvaluacionAuxiliares) {
        this.idAuxiliares = idAuxiliares;
        this.idEvaluacionAuxiliares = idEvaluacionAuxiliares;
    }

    public String getIdAuxiliares() {
        return idAuxiliares;
    }

    public void setIdAuxiliares(String idAuxiliares) {
        this.idAuxiliares = idAuxiliares;
    }

    public String getIdEvaluacionAuxiliares() {
        return idEvaluacionAuxiliares;
    }

    public void setIdEvaluacionAuxiliares(String idEvaluacionAuxiliares) {
        this.idEvaluacionAuxiliares = idEvaluacionAuxiliares;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuxiliares != null ? idAuxiliares.hashCode() : 0);
        hash += (idEvaluacionAuxiliares != null ? idEvaluacionAuxiliares.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SeleccionAuxiliaresPK)) {
            return false;
        }
        SeleccionAuxiliaresPK other = (SeleccionAuxiliaresPK) object;
        if ((this.idAuxiliares == null && other.idAuxiliares != null) || (this.idAuxiliares != null && !this.idAuxiliares.equals(other.idAuxiliares))) {
            return false;
        }
        if ((this.idEvaluacionAuxiliares == null && other.idEvaluacionAuxiliares != null) || (this.idEvaluacionAuxiliares != null && !this.idEvaluacionAuxiliares.equals(other.idEvaluacionAuxiliares))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SeleccionAuxiliaresPK[ idAuxiliares=" + idAuxiliares + ", idEvaluacionAuxiliares=" + idEvaluacionAuxiliares + " ]";
    }
    
}
