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
@Table(name = "CUMPLIMIENTO_ACTIVIDADES", catalog = "", schema = "PROYECTO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CumplimientoActividades.findAll", query = "SELECT c FROM CumplimientoActividades c"),
    @NamedQuery(name = "CumplimientoActividades.findByCumpActId", query = "SELECT c FROM CumplimientoActividades c WHERE c.cumpActId = :cumpActId"),
    @NamedQuery(name = "CumplimientoActividades.findByDescCumpAct", query = "SELECT c FROM CumplimientoActividades c WHERE c.descCumpAct = :descCumpAct"),
    @NamedQuery(name = "CumplimientoActividades.findByFechaCreacionCumpAct", query = "SELECT c FROM CumplimientoActividades c WHERE c.fechaCreacionCumpAct = :fechaCreacionCumpAct")})
public class CumplimientoActividades implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CUMP_ACT_ID", nullable = false, length = 20)
    private String cumpActId;
    @Basic(optional = false)
    @Column(name = "DESC_CUMP_ACT", nullable = false, length = 255)
    private String descCumpAct;
    @Basic(optional = false)
    @Column(name = "FECHA_CREACION_CUMP_ACT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacionCumpAct;
    @JoinColumn(name = "ID_SOLICITANTE", referencedColumnName = "SOLICITANTE_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Solicitante idSolicitante;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cumplimientoActividades", fetch = FetchType.LAZY)
    private List<AuxCumplimientoActividades> auxCumplimientoActividadesList;

    public CumplimientoActividades() {
    }

    public CumplimientoActividades(String cumpActId) {
        this.cumpActId = cumpActId;
    }

    public CumplimientoActividades(String cumpActId, String descCumpAct, Date fechaCreacionCumpAct) {
        this.cumpActId = cumpActId;
        this.descCumpAct = descCumpAct;
        this.fechaCreacionCumpAct = fechaCreacionCumpAct;
    }

    public String getCumpActId() {
        return cumpActId;
    }

    public void setCumpActId(String cumpActId) {
        this.cumpActId = cumpActId;
    }

    public String getDescCumpAct() {
        return descCumpAct;
    }

    public void setDescCumpAct(String descCumpAct) {
        this.descCumpAct = descCumpAct;
    }

    public Date getFechaCreacionCumpAct() {
        return fechaCreacionCumpAct;
    }

    public void setFechaCreacionCumpAct(Date fechaCreacionCumpAct) {
        this.fechaCreacionCumpAct = fechaCreacionCumpAct;
    }

    public Solicitante getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(Solicitante idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    @XmlTransient
    public List<AuxCumplimientoActividades> getAuxCumplimientoActividadesList() {
        return auxCumplimientoActividadesList;
    }

    public void setAuxCumplimientoActividadesList(List<AuxCumplimientoActividades> auxCumplimientoActividadesList) {
        this.auxCumplimientoActividadesList = auxCumplimientoActividadesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cumpActId != null ? cumpActId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CumplimientoActividades)) {
            return false;
        }
        CumplimientoActividades other = (CumplimientoActividades) object;
        if ((this.cumpActId == null && other.cumpActId != null) || (this.cumpActId != null && !this.cumpActId.equals(other.cumpActId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.CumplimientoActividades[ cumpActId=" + cumpActId + " ]";
    }
    
}
