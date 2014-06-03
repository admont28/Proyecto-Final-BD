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
@Table(name = "CARGO", catalog = "", schema = "PROYECTO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cargo.findAll", query = "SELECT c FROM Cargo c"),
    @NamedQuery(name = "Cargo.findByCargoId", query = "SELECT c FROM Cargo c WHERE c.cargoId = :cargoId"),
    @NamedQuery(name = "Cargo.findByNombreCargo", query = "SELECT c FROM Cargo c WHERE c.nombreCargo = :nombreCargo")})
public class Cargo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CARGO_ID", nullable = false, length = 20)
    private String cargoId;
    @Basic(optional = false)
    @Column(name = "NOMBRE_CARGO", nullable = false, length = 50)
    private String nombreCargo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCargo", fetch = FetchType.LAZY)
    private List<Solicitante> solicitanteList;

    public Cargo() {
    }

    public Cargo(String cargoId) {
        this.cargoId = cargoId;
    }

    public Cargo(String cargoId, String nombreCargo) {
        this.cargoId = cargoId;
        this.nombreCargo = nombreCargo;
    }

    public String getCargoId() {
        return cargoId;
    }

    public void setCargoId(String cargoId) {
        this.cargoId = cargoId;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    @XmlTransient
    public List<Solicitante> getSolicitanteList() {
        return solicitanteList;
    }

    public void setSolicitanteList(List<Solicitante> solicitanteList) {
        this.solicitanteList = solicitanteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cargoId != null ? cargoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cargo)) {
            return false;
        }
        Cargo other = (Cargo) object;
        if ((this.cargoId == null && other.cargoId != null) || (this.cargoId != null && !this.cargoId.equals(other.cargoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Cargo[ cargoId=" + cargoId + " ]";
    }
    
}
