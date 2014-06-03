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
@Table(name = "HORARIO_ACTIVIDADES", catalog = "", schema = "PROYECTO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HorarioActividades.findAll", query = "SELECT h FROM HorarioActividades h"),
    @NamedQuery(name = "HorarioActividades.findByHorarioActividadesId", query = "SELECT h FROM HorarioActividades h WHERE h.horarioActividadesId = :horarioActividadesId"),
    @NamedQuery(name = "HorarioActividades.findByFechaCreacionHorarioActiv", query = "SELECT h FROM HorarioActividades h WHERE h.fechaCreacionHorarioActiv = :fechaCreacionHorarioActiv")})
public class HorarioActividades implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "HORARIO_ACTIVIDADES_ID", nullable = false, length = 20)
    private String horarioActividadesId;
    @Basic(optional = false)
    @Column(name = "FECHA_CREACION_HORARIO_ACTIV", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacionHorarioActiv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idHorarioActividades", fetch = FetchType.LAZY)
    private List<FechaHorario> fechaHorarioList;
    @JoinColumn(name = "ID_TIPO_AUXILIAR", referencedColumnName = "TIPO_AUXILIAR_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoAuxiliar idTipoAuxiliar;
    @JoinColumn(name = "ID_AUXILIAR", referencedColumnName = "AUXILIAR_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Auxiliar idAuxiliar;

    public HorarioActividades() {
    }

    public HorarioActividades(String horarioActividadesId) {
        this.horarioActividadesId = horarioActividadesId;
    }

    public HorarioActividades(String horarioActividadesId, Date fechaCreacionHorarioActiv) {
        this.horarioActividadesId = horarioActividadesId;
        this.fechaCreacionHorarioActiv = fechaCreacionHorarioActiv;
    }

    public String getHorarioActividadesId() {
        return horarioActividadesId;
    }

    public void setHorarioActividadesId(String horarioActividadesId) {
        this.horarioActividadesId = horarioActividadesId;
    }

    public Date getFechaCreacionHorarioActiv() {
        return fechaCreacionHorarioActiv;
    }

    public void setFechaCreacionHorarioActiv(Date fechaCreacionHorarioActiv) {
        this.fechaCreacionHorarioActiv = fechaCreacionHorarioActiv;
    }

    @XmlTransient
    public List<FechaHorario> getFechaHorarioList() {
        return fechaHorarioList;
    }

    public void setFechaHorarioList(List<FechaHorario> fechaHorarioList) {
        this.fechaHorarioList = fechaHorarioList;
    }

    public TipoAuxiliar getIdTipoAuxiliar() {
        return idTipoAuxiliar;
    }

    public void setIdTipoAuxiliar(TipoAuxiliar idTipoAuxiliar) {
        this.idTipoAuxiliar = idTipoAuxiliar;
    }

    public Auxiliar getIdAuxiliar() {
        return idAuxiliar;
    }

    public void setIdAuxiliar(Auxiliar idAuxiliar) {
        this.idAuxiliar = idAuxiliar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (horarioActividadesId != null ? horarioActividadesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HorarioActividades)) {
            return false;
        }
        HorarioActividades other = (HorarioActividades) object;
        if ((this.horarioActividadesId == null && other.horarioActividadesId != null) || (this.horarioActividadesId != null && !this.horarioActividadesId.equals(other.horarioActividadesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.HorarioActividades[ horarioActividadesId=" + horarioActividadesId + " ]";
    }
    
}
