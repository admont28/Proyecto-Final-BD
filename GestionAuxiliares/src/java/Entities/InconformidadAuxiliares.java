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
@Table(name = "INCONFORMIDAD_AUXILIARES", catalog = "", schema = "PROYECTO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InconformidadAuxiliares.findAll", query = "SELECT i FROM InconformidadAuxiliares i"),
    @NamedQuery(name = "InconformidadAuxiliares.findByInconformidadAuxiliaresId", query = "SELECT i FROM InconformidadAuxiliares i WHERE i.inconformidadAuxiliaresId = :inconformidadAuxiliaresId"),
    @NamedQuery(name = "InconformidadAuxiliares.findByFechaCreaIncAux", query = "SELECT i FROM InconformidadAuxiliares i WHERE i.fechaCreaIncAux = :fechaCreaIncAux"),
    @NamedQuery(name = "InconformidadAuxiliares.findByDescIncAux", query = "SELECT i FROM InconformidadAuxiliares i WHERE i.descIncAux = :descIncAux")})
public class InconformidadAuxiliares implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "INCONFORMIDAD_AUXILIARES_ID", nullable = false, length = 20)
    private String inconformidadAuxiliaresId;
    @Basic(optional = false)
    @Column(name = "FECHA_CREA_INC_AUX", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreaIncAux;
    @Basic(optional = false)
    @Column(name = "DESC_INC_AUX", nullable = false, length = 255)
    private String descIncAux;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inconformidadAuxiliares", fetch = FetchType.LAZY)
    private List<IncAuxAuxiliar> incAuxAuxiliarList;

    public InconformidadAuxiliares() {
    }

    public InconformidadAuxiliares(String inconformidadAuxiliaresId) {
        this.inconformidadAuxiliaresId = inconformidadAuxiliaresId;
    }

    public InconformidadAuxiliares(String inconformidadAuxiliaresId, Date fechaCreaIncAux, String descIncAux) {
        this.inconformidadAuxiliaresId = inconformidadAuxiliaresId;
        this.fechaCreaIncAux = fechaCreaIncAux;
        this.descIncAux = descIncAux;
    }

    public String getInconformidadAuxiliaresId() {
        return inconformidadAuxiliaresId;
    }

    public void setInconformidadAuxiliaresId(String inconformidadAuxiliaresId) {
        this.inconformidadAuxiliaresId = inconformidadAuxiliaresId;
    }

    public Date getFechaCreaIncAux() {
        return fechaCreaIncAux;
    }

    public void setFechaCreaIncAux(Date fechaCreaIncAux) {
        this.fechaCreaIncAux = fechaCreaIncAux;
    }

    public String getDescIncAux() {
        return descIncAux;
    }

    public void setDescIncAux(String descIncAux) {
        this.descIncAux = descIncAux;
    }

    @XmlTransient
    public List<IncAuxAuxiliar> getIncAuxAuxiliarList() {
        return incAuxAuxiliarList;
    }

    public void setIncAuxAuxiliarList(List<IncAuxAuxiliar> incAuxAuxiliarList) {
        this.incAuxAuxiliarList = incAuxAuxiliarList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (inconformidadAuxiliaresId != null ? inconformidadAuxiliaresId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InconformidadAuxiliares)) {
            return false;
        }
        InconformidadAuxiliares other = (InconformidadAuxiliares) object;
        if ((this.inconformidadAuxiliaresId == null && other.inconformidadAuxiliaresId != null) || (this.inconformidadAuxiliaresId != null && !this.inconformidadAuxiliaresId.equals(other.inconformidadAuxiliaresId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.InconformidadAuxiliares[ inconformidadAuxiliaresId=" + inconformidadAuxiliaresId + " ]";
    }
    
}
