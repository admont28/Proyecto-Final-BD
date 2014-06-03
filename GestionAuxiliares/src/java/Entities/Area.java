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
@Table(name = "AREA", catalog = "", schema = "PROYECTO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Area.findAll", query = "SELECT a FROM Area a"),
    @NamedQuery(name = "Area.findByAreaId", query = "SELECT a FROM Area a WHERE a.areaId = :areaId"),
    @NamedQuery(name = "Area.findByNombreArea", query = "SELECT a FROM Area a WHERE a.nombreArea = :nombreArea")})
public class Area implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "AREA_ID", nullable = false, length = 20)
    private String areaId;
    @Basic(optional = false)
    @Column(name = "NOMBRE_AREA", nullable = false, length = 50)
    private String nombreArea;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idArea", fetch = FetchType.LAZY)
    private List<Solicitante> solicitanteList;

    public Area() {
    }

    public Area(String areaId) {
        this.areaId = areaId;
    }

    public Area(String areaId, String nombreArea) {
        this.areaId = areaId;
        this.nombreArea = nombreArea;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
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
        hash += (areaId != null ? areaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Area)) {
            return false;
        }
        Area other = (Area) object;
        if ((this.areaId == null && other.areaId != null) || (this.areaId != null && !this.areaId.equals(other.areaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Area[ areaId=" + areaId + " ]";
    }
    
}
