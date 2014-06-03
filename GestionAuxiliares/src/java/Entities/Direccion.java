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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DavidMontoya
 */
@Entity
@Table(name = "DIRECCION", catalog = "", schema = "PROYECTO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Direccion.findAll", query = "SELECT d FROM Direccion d"),
    @NamedQuery(name = "Direccion.findByDireccionId", query = "SELECT d FROM Direccion d WHERE d.direccionId = :direccionId"),
    @NamedQuery(name = "Direccion.findByCalle", query = "SELECT d FROM Direccion d WHERE d.calle = :calle"),
    @NamedQuery(name = "Direccion.findByCarrera", query = "SELECT d FROM Direccion d WHERE d.carrera = :carrera"),
    @NamedQuery(name = "Direccion.findByNumero", query = "SELECT d FROM Direccion d WHERE d.numero = :numero")})
public class Direccion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "DIRECCION_ID", nullable = false, length = 20)
    private String direccionId;
    @Basic(optional = false)
    @Column(name = "CALLE", nullable = false, length = 3)
    private String calle;
    @Basic(optional = false)
    @Column(name = "CARRERA", nullable = false, length = 3)
    private String carrera;
    @Basic(optional = false)
    @Column(name = "NUMERO", nullable = false, length = 3)
    private String numero;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDireccion", fetch = FetchType.LAZY)
    private List<Solicitante> solicitanteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDireccion", fetch = FetchType.LAZY)
    private List<Auxiliar> auxiliarList;
    @JoinColumn(name = "ID_CIUDAD", referencedColumnName = "CIUDAD_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Ciudad idCiudad;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idDireccion", fetch = FetchType.LAZY)
    private Universidad universidad;

    public Direccion() {
    }

    public Direccion(String direccionId) {
        this.direccionId = direccionId;
    }

    public Direccion(String direccionId, String calle, String carrera, String numero) {
        this.direccionId = direccionId;
        this.calle = calle;
        this.carrera = carrera;
        this.numero = numero;
    }

    public String getDireccionId() {
        return direccionId;
    }

    public void setDireccionId(String direccionId) {
        this.direccionId = direccionId;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @XmlTransient
    public List<Solicitante> getSolicitanteList() {
        return solicitanteList;
    }

    public void setSolicitanteList(List<Solicitante> solicitanteList) {
        this.solicitanteList = solicitanteList;
    }

    @XmlTransient
    public List<Auxiliar> getAuxiliarList() {
        return auxiliarList;
    }

    public void setAuxiliarList(List<Auxiliar> auxiliarList) {
        this.auxiliarList = auxiliarList;
    }

    public Ciudad getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Ciudad idCiudad) {
        this.idCiudad = idCiudad;
    }

    public Universidad getUniversidad() {
        return universidad;
    }

    public void setUniversidad(Universidad universidad) {
        this.universidad = universidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (direccionId != null ? direccionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Direccion)) {
            return false;
        }
        Direccion other = (Direccion) object;
        if ((this.direccionId == null && other.direccionId != null) || (this.direccionId != null && !this.direccionId.equals(other.direccionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Direccion[ direccionId=" + direccionId + " ]";
    }
    
}
