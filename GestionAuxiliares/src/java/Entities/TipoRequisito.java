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
@Table(name = "TIPO_REQUISITO", catalog = "", schema = "PROYECTO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoRequisito.findAll", query = "SELECT t FROM TipoRequisito t"),
    @NamedQuery(name = "TipoRequisito.findByTipoRequisitoId", query = "SELECT t FROM TipoRequisito t WHERE t.tipoRequisitoId = :tipoRequisitoId"),
    @NamedQuery(name = "TipoRequisito.findByDescripcionTipoRequisito", query = "SELECT t FROM TipoRequisito t WHERE t.descripcionTipoRequisito = :descripcionTipoRequisito")})
public class TipoRequisito implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TIPO_REQUISITO_ID", nullable = false, length = 20)
    private String tipoRequisitoId;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION_TIPO_REQUISITO", nullable = false, length = 255)
    private String descripcionTipoRequisito;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoRequisito", fetch = FetchType.LAZY)
    private List<Requisito> requisitoList;

    public TipoRequisito() {
    }

    public TipoRequisito(String tipoRequisitoId) {
        this.tipoRequisitoId = tipoRequisitoId;
    }

    public TipoRequisito(String tipoRequisitoId, String descripcionTipoRequisito) {
        this.tipoRequisitoId = tipoRequisitoId;
        this.descripcionTipoRequisito = descripcionTipoRequisito;
    }

    public String getTipoRequisitoId() {
        return tipoRequisitoId;
    }

    public void setTipoRequisitoId(String tipoRequisitoId) {
        this.tipoRequisitoId = tipoRequisitoId;
    }

    public String getDescripcionTipoRequisito() {
        return descripcionTipoRequisito;
    }

    public void setDescripcionTipoRequisito(String descripcionTipoRequisito) {
        this.descripcionTipoRequisito = descripcionTipoRequisito;
    }

    @XmlTransient
    public List<Requisito> getRequisitoList() {
        return requisitoList;
    }

    public void setRequisitoList(List<Requisito> requisitoList) {
        this.requisitoList = requisitoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoRequisitoId != null ? tipoRequisitoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoRequisito)) {
            return false;
        }
        TipoRequisito other = (TipoRequisito) object;
        if ((this.tipoRequisitoId == null && other.tipoRequisitoId != null) || (this.tipoRequisitoId != null && !this.tipoRequisitoId.equals(other.tipoRequisitoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.TipoRequisito[ tipoRequisitoId=" + tipoRequisitoId + " ]";
    }
    
}
