/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DavidMontoya
 */
@Entity
@Table(name = "TIPO_AUXILIAR", catalog = "", schema = "PROYECTO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoAuxiliar.findAll", query = "SELECT t FROM TipoAuxiliar t"),
    @NamedQuery(name = "TipoAuxiliar.findByTipoAuxiliarId", query = "SELECT t FROM TipoAuxiliar t WHERE t.tipoAuxiliarId = :tipoAuxiliarId"),
    @NamedQuery(name = "TipoAuxiliar.findByDescripcionTipoAuxiliar", query = "SELECT t FROM TipoAuxiliar t WHERE t.descripcionTipoAuxiliar = :descripcionTipoAuxiliar")})
public class TipoAuxiliar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TIPO_AUXILIAR_ID", nullable = false, length = 20)
    private String tipoAuxiliarId;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION_TIPO_AUXILIAR", nullable = false, length = 255)
    private String descripcionTipoAuxiliar;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoAuxiliar", fetch = FetchType.LAZY)
    private List<AuxCumplimientoActividades> auxCumplimientoActividadesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoAuxiliar", fetch = FetchType.LAZY)
    private List<Convocatoria> convocatoriaList;
    @OneToMany(mappedBy = "idTipoAuxiliar", fetch = FetchType.LAZY)
    private List<HorarioActividades> horarioActividadesList;

    public TipoAuxiliar() {
    }

    public TipoAuxiliar(String tipoAuxiliarId) {
        this.tipoAuxiliarId = tipoAuxiliarId;
    }

    public TipoAuxiliar(String tipoAuxiliarId, String descripcionTipoAuxiliar) {
        this.tipoAuxiliarId = tipoAuxiliarId;
        this.descripcionTipoAuxiliar = descripcionTipoAuxiliar;
    }

    public String getTipoAuxiliarId() {
        return tipoAuxiliarId;
    }

    public void setTipoAuxiliarId(String tipoAuxiliarId) {
        this.tipoAuxiliarId = tipoAuxiliarId;
    }

    public String getDescripcionTipoAuxiliar() {
        return descripcionTipoAuxiliar;
    }

    public void setDescripcionTipoAuxiliar(String descripcionTipoAuxiliar) {
        this.descripcionTipoAuxiliar = descripcionTipoAuxiliar;
    }

    @XmlTransient
    public List<AuxCumplimientoActividades> getAuxCumplimientoActividadesList() {
        return auxCumplimientoActividadesList;
    }

    public void setAuxCumplimientoActividadesList(List<AuxCumplimientoActividades> auxCumplimientoActividadesList) {
        this.auxCumplimientoActividadesList = auxCumplimientoActividadesList;
    }

    @XmlTransient
    public List<Convocatoria> getConvocatoriaList() {
        return convocatoriaList;
    }

    public void setConvocatoriaList(List<Convocatoria> convocatoriaList) {
        this.convocatoriaList = convocatoriaList;
    }

    @XmlTransient
    public List<HorarioActividades> getHorarioActividadesList() {
        return horarioActividadesList;
    }

    public void setHorarioActividadesList(List<HorarioActividades> horarioActividadesList) {
        this.horarioActividadesList = horarioActividadesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoAuxiliarId != null ? tipoAuxiliarId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoAuxiliar)) {
            return false;
        }
        TipoAuxiliar other = (TipoAuxiliar) object;
        if ((this.tipoAuxiliarId == null && other.tipoAuxiliarId != null) || (this.tipoAuxiliarId != null && !this.tipoAuxiliarId.equals(other.tipoAuxiliarId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.TipoAuxiliar[ tipoAuxiliarId=" + tipoAuxiliarId + " ]";
    }
    
}
