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
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DavidMontoya
 */
@Entity
@Table(name = "UNIVERSIDAD", catalog = "", schema = "PROYECTO_FINAL", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ID_DIRECCION"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Universidad.findAll", query = "SELECT u FROM Universidad u"),
    @NamedQuery(name = "Universidad.findByUniversidadId", query = "SELECT u FROM Universidad u WHERE u.universidadId = :universidadId"),
    @NamedQuery(name = "Universidad.findByNombreUniversidad", query = "SELECT u FROM Universidad u WHERE u.nombreUniversidad = :nombreUniversidad")})
public class Universidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "UNIVERSIDAD_ID", nullable = false, length = 20)
    private String universidadId;
    @Basic(optional = false)
    @Column(name = "NOMBRE_UNIVERSIDAD", nullable = false, length = 50)
    private String nombreUniversidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUniversidad", fetch = FetchType.LAZY)
    private List<Facultad> facultadList;
    @JoinColumn(name = "ID_DIRECCION", referencedColumnName = "DIRECCION_ID", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Direccion idDireccion;

    public Universidad() {
    }

    public Universidad(String universidadId) {
        this.universidadId = universidadId;
    }

    public Universidad(String universidadId, String nombreUniversidad) {
        this.universidadId = universidadId;
        this.nombreUniversidad = nombreUniversidad;
    }

    public String getUniversidadId() {
        return universidadId;
    }

    public void setUniversidadId(String universidadId) {
        this.universidadId = universidadId;
    }

    public String getNombreUniversidad() {
        return nombreUniversidad;
    }

    public void setNombreUniversidad(String nombreUniversidad) {
        this.nombreUniversidad = nombreUniversidad;
    }

    @XmlTransient
    public List<Facultad> getFacultadList() {
        return facultadList;
    }

    public void setFacultadList(List<Facultad> facultadList) {
        this.facultadList = facultadList;
    }

    public Direccion getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Direccion idDireccion) {
        this.idDireccion = idDireccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (universidadId != null ? universidadId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Universidad)) {
            return false;
        }
        Universidad other = (Universidad) object;
        if ((this.universidadId == null && other.universidadId != null) || (this.universidadId != null && !this.universidadId.equals(other.universidadId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Universidad[ universidadId=" + universidadId + " ]";
    }
    
}
