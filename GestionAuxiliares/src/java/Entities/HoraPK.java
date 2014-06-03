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
public class HoraPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "HORA_INICIO", nullable = false, length = 20)
    private String horaInicio;
    @Basic(optional = false)
    @Column(name = "ID_FECHA_HORARIO", nullable = false, length = 20)
    private String idFechaHorario;

    public HoraPK() {
    }

    public HoraPK(String horaInicio, String idFechaHorario) {
        this.horaInicio = horaInicio;
        this.idFechaHorario = idFechaHorario;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getIdFechaHorario() {
        return idFechaHorario;
    }

    public void setIdFechaHorario(String idFechaHorario) {
        this.idFechaHorario = idFechaHorario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (horaInicio != null ? horaInicio.hashCode() : 0);
        hash += (idFechaHorario != null ? idFechaHorario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HoraPK)) {
            return false;
        }
        HoraPK other = (HoraPK) object;
        if ((this.horaInicio == null && other.horaInicio != null) || (this.horaInicio != null && !this.horaInicio.equals(other.horaInicio))) {
            return false;
        }
        if ((this.idFechaHorario == null && other.idFechaHorario != null) || (this.idFechaHorario != null && !this.idFechaHorario.equals(other.idFechaHorario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.HoraPK[ horaInicio=" + horaInicio + ", idFechaHorario=" + idFechaHorario + " ]";
    }
    
}
