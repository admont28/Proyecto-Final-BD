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
@Table(name = "CIUDAD", catalog = "", schema = "PROYECTO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ciudad.findAll", query = "SELECT c FROM Ciudad c"),
    @NamedQuery(name = "Ciudad.findByCiudadId", query = "SELECT c FROM Ciudad c WHERE c.ciudadId = :ciudadId"),
    @NamedQuery(name = "Ciudad.findByNombreCiudad", query = "SELECT c FROM Ciudad c WHERE c.nombreCiudad = :nombreCiudad")})
public class Ciudad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CIUDAD_ID", nullable = false, length = 20)
    private String ciudadId;
    @Basic(optional = false)
    @Column(name = "NOMBRE_CIUDAD", nullable = false, length = 20)
    private String nombreCiudad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCiudad", fetch = FetchType.LAZY)
    private List<Direccion> direccionList;

    public Ciudad() {
    }

    public Ciudad(String ciudadId) {
        this.ciudadId = ciudadId;
    }

    public Ciudad(String ciudadId, String nombreCiudad) {
        this.ciudadId = ciudadId;
        this.nombreCiudad = nombreCiudad;
    }

    public String getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(String ciudadId) {
        this.ciudadId = ciudadId;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    @XmlTransient
    public List<Direccion> getDireccionList() {
        return direccionList;
    }

    public void setDireccionList(List<Direccion> direccionList) {
        this.direccionList = direccionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ciudadId != null ? ciudadId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ciudad)) {
            return false;
        }
        Ciudad other = (Ciudad) object;
        if ((this.ciudadId == null && other.ciudadId != null) || (this.ciudadId != null && !this.ciudadId.equals(other.ciudadId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Ciudad[ ciudadId=" + ciudadId + " ]";
    }
    
}
