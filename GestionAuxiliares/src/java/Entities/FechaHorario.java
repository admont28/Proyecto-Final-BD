/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DavidMontoya
 */
@Entity
@Table(name = "FECHA_HORARIO", catalog = "", schema = "PROYECTO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FechaHorario.findAll", query = "SELECT f FROM FechaHorario f"),
    @NamedQuery(name = "FechaHorario.findByFechaHorarioId", query = "SELECT f FROM FechaHorario f WHERE f.fechaHorarioId = :fechaHorarioId"),
    @NamedQuery(name = "FechaHorario.findByFechaFechaHorario", query = "SELECT f FROM FechaHorario f WHERE f.fechaFechaHorario = :fechaFechaHorario")})
public class FechaHorario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "FECHA_HORARIO_ID", nullable = false, length = 20)
    private String fechaHorarioId;
    @Basic(optional = false)
    @Column(name = "FECHA_FECHA_HORARIO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFechaHorario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fechaHorario", fetch = FetchType.LAZY)
    private List<Hora> horaList;
    @JoinColumn(name = "ID_HORARIO_ACTIVIDADES", referencedColumnName = "HORARIO_ACTIVIDADES_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private HorarioActividades idHorarioActividades;

    public FechaHorario() {
    }

    public FechaHorario(String fechaHorarioId) {
        this.fechaHorarioId = fechaHorarioId;
    }

    public FechaHorario(String fechaHorarioId, Date fechaFechaHorario) {
        this.fechaHorarioId = fechaHorarioId;
        this.fechaFechaHorario = fechaFechaHorario;
    }

    public String getFechaHorarioId() {
        return fechaHorarioId;
    }

    public void setFechaHorarioId(String fechaHorarioId) {
        this.fechaHorarioId = fechaHorarioId;
    }

    public Date getFechaFechaHorario() {
        return fechaFechaHorario;
    }

    public void setFechaFechaHorario(Date fechaFechaHorario) {
        this.fechaFechaHorario = fechaFechaHorario;
    }

    @XmlTransient
    public List<Hora> getHoraList() {
        return horaList;
    }

    public void setHoraList(List<Hora> horaList) {
        this.horaList = horaList;
    }

    public HorarioActividades getIdHorarioActividades() {
        return idHorarioActividades;
    }

    public void setIdHorarioActividades(HorarioActividades idHorarioActividades) {
        this.idHorarioActividades = idHorarioActividades;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fechaHorarioId != null ? fechaHorarioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FechaHorario)) {
            return false;
        }
        FechaHorario other = (FechaHorario) object;
        if ((this.fechaHorarioId == null && other.fechaHorarioId != null) || (this.fechaHorarioId != null && !this.fechaHorarioId.equals(other.fechaHorarioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.FechaHorario[ fechaHorarioId=" + fechaHorarioId + " ]";
    }
    
}
