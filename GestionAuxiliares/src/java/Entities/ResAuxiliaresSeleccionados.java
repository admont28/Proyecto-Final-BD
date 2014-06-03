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
@Table(name = "RES_AUXILIARES_SELECCIONADOS", catalog = "", schema = "PROYECTO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ResAuxiliaresSeleccionados.findAll", query = "SELECT r FROM ResAuxiliaresSeleccionados r"),
    @NamedQuery(name = "ResAuxiliaresSeleccionados.findByResAuxSelId", query = "SELECT r FROM ResAuxiliaresSeleccionados r WHERE r.resAuxSelId = :resAuxSelId"),
    @NamedQuery(name = "ResAuxiliaresSeleccionados.findByDescResAuxSel", query = "SELECT r FROM ResAuxiliaresSeleccionados r WHERE r.descResAuxSel = :descResAuxSel"),
    @NamedQuery(name = "ResAuxiliaresSeleccionados.findByFechaCreaResAuxSel", query = "SELECT r FROM ResAuxiliaresSeleccionados r WHERE r.fechaCreaResAuxSel = :fechaCreaResAuxSel")})
public class ResAuxiliaresSeleccionados implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "RES_AUX_SEL_ID", nullable = false, length = 20)
    private String resAuxSelId;
    @Basic(optional = false)
    @Column(name = "DESC_RES_AUX_SEL", nullable = false, length = 255)
    private String descResAuxSel;
    @Basic(optional = false)
    @Column(name = "FECHA_CREA_RES_AUX_SEL", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreaResAuxSel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resAuxiliaresSeleccionados", fetch = FetchType.LAZY)
    private List<DetalleResolucion> detalleResolucionList;
    @JoinColumn(name = "ID_SOLICITANTE", referencedColumnName = "SOLICITANTE_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Solicitante idSolicitante;

    public ResAuxiliaresSeleccionados() {
    }

    public ResAuxiliaresSeleccionados(String resAuxSelId) {
        this.resAuxSelId = resAuxSelId;
    }

    public ResAuxiliaresSeleccionados(String resAuxSelId, String descResAuxSel, Date fechaCreaResAuxSel) {
        this.resAuxSelId = resAuxSelId;
        this.descResAuxSel = descResAuxSel;
        this.fechaCreaResAuxSel = fechaCreaResAuxSel;
    }

    public String getResAuxSelId() {
        return resAuxSelId;
    }

    public void setResAuxSelId(String resAuxSelId) {
        this.resAuxSelId = resAuxSelId;
    }

    public String getDescResAuxSel() {
        return descResAuxSel;
    }

    public void setDescResAuxSel(String descResAuxSel) {
        this.descResAuxSel = descResAuxSel;
    }

    public Date getFechaCreaResAuxSel() {
        return fechaCreaResAuxSel;
    }

    public void setFechaCreaResAuxSel(Date fechaCreaResAuxSel) {
        this.fechaCreaResAuxSel = fechaCreaResAuxSel;
    }

    @XmlTransient
    public List<DetalleResolucion> getDetalleResolucionList() {
        return detalleResolucionList;
    }

    public void setDetalleResolucionList(List<DetalleResolucion> detalleResolucionList) {
        this.detalleResolucionList = detalleResolucionList;
    }

    public Solicitante getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(Solicitante idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resAuxSelId != null ? resAuxSelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResAuxiliaresSeleccionados)) {
            return false;
        }
        ResAuxiliaresSeleccionados other = (ResAuxiliaresSeleccionados) object;
        if ((this.resAuxSelId == null && other.resAuxSelId != null) || (this.resAuxSelId != null && !this.resAuxSelId.equals(other.resAuxSelId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ResAuxiliaresSeleccionados[ resAuxSelId=" + resAuxSelId + " ]";
    }
    
}
