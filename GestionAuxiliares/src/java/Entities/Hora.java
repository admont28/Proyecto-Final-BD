/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DavidMontoya
 */
@Entity
@Table(name = "HORA", catalog = "", schema = "PROYECTO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hora.findAll", query = "SELECT h FROM Hora h"),
    @NamedQuery(name = "Hora.findByHoraInicio", query = "SELECT h FROM Hora h WHERE h.horaPK.horaInicio = :horaInicio"),
    @NamedQuery(name = "Hora.findByHoraFin", query = "SELECT h FROM Hora h WHERE h.horaFin = :horaFin"),
    @NamedQuery(name = "Hora.findByIdFechaHorario", query = "SELECT h FROM Hora h WHERE h.horaPK.idFechaHorario = :idFechaHorario")})
public class Hora implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HoraPK horaPK;
    @Basic(optional = false)
    @Column(name = "HORA_FIN", nullable = false, length = 20)
    private String horaFin;
    @JoinColumn(name = "ID_FECHA_HORARIO", referencedColumnName = "FECHA_HORARIO_ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private FechaHorario fechaHorario;

    public Hora() {
    }

    public Hora(HoraPK horaPK) {
        this.horaPK = horaPK;
    }

    public Hora(HoraPK horaPK, String horaFin) {
        this.horaPK = horaPK;
        this.horaFin = horaFin;
    }

    public Hora(String horaInicio, String idFechaHorario) {
        this.horaPK = new HoraPK(horaInicio, idFechaHorario);
    }

    public HoraPK getHoraPK() {
        return horaPK;
    }

    public void setHoraPK(HoraPK horaPK) {
        this.horaPK = horaPK;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public FechaHorario getFechaHorario() {
        return fechaHorario;
    }

    public void setFechaHorario(FechaHorario fechaHorario) {
        this.fechaHorario = fechaHorario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (horaPK != null ? horaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hora)) {
            return false;
        }
        Hora other = (Hora) object;
        if ((this.horaPK == null && other.horaPK != null) || (this.horaPK != null && !this.horaPK.equals(other.horaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Hora[ horaPK=" + horaPK + " ]";
    }
    
}
