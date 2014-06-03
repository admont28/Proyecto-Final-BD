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
public class AuxCumplimientoActividadesPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_AUXILIAR", nullable = false, length = 20)
    private String idAuxiliar;
    @Basic(optional = false)
    @Column(name = "ID_CUMPLIMIENTO_ACTIVIDADES", nullable = false, length = 20)
    private String idCumplimientoActividades;

    public AuxCumplimientoActividadesPK() {
    }

    public AuxCumplimientoActividadesPK(String idAuxiliar, String idCumplimientoActividades) {
        this.idAuxiliar = idAuxiliar;
        this.idCumplimientoActividades = idCumplimientoActividades;
    }

    public String getIdAuxiliar() {
        return idAuxiliar;
    }

    public void setIdAuxiliar(String idAuxiliar) {
        this.idAuxiliar = idAuxiliar;
    }

    public String getIdCumplimientoActividades() {
        return idCumplimientoActividades;
    }

    public void setIdCumplimientoActividades(String idCumplimientoActividades) {
        this.idCumplimientoActividades = idCumplimientoActividades;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuxiliar != null ? idAuxiliar.hashCode() : 0);
        hash += (idCumplimientoActividades != null ? idCumplimientoActividades.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuxCumplimientoActividadesPK)) {
            return false;
        }
        AuxCumplimientoActividadesPK other = (AuxCumplimientoActividadesPK) object;
        if ((this.idAuxiliar == null && other.idAuxiliar != null) || (this.idAuxiliar != null && !this.idAuxiliar.equals(other.idAuxiliar))) {
            return false;
        }
        if ((this.idCumplimientoActividades == null && other.idCumplimientoActividades != null) || (this.idCumplimientoActividades != null && !this.idCumplimientoActividades.equals(other.idCumplimientoActividades))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.AuxCumplimientoActividadesPK[ idAuxiliar=" + idAuxiliar + ", idCumplimientoActividades=" + idCumplimientoActividades + " ]";
    }
    
}
