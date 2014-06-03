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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DavidMontoya
 */
@Entity
@Table(name = "FACULTAD", catalog = "", schema = "PROYECTO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Facultad.findAll", query = "SELECT f FROM Facultad f"),
    @NamedQuery(name = "Facultad.findByFacultadId", query = "SELECT f FROM Facultad f WHERE f.facultadId = :facultadId"),
    @NamedQuery(name = "Facultad.findByNombreFacultad", query = "SELECT f FROM Facultad f WHERE f.nombreFacultad = :nombreFacultad")})
public class Facultad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "FACULTAD_ID", nullable = false, length = 20)
    private String facultadId;
    @Basic(optional = false)
    @Column(name = "NOMBRE_FACULTAD", nullable = false, length = 50)
    private String nombreFacultad;
    @JoinColumn(name = "ID_UNIVERSIDAD", referencedColumnName = "UNIVERSIDAD_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Universidad idUniversidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFacultad", fetch = FetchType.LAZY)
    private List<ProgramaAcademico> programaAcademicoList;

    public Facultad() {
    }

    public Facultad(String facultadId) {
        this.facultadId = facultadId;
    }

    public Facultad(String facultadId, String nombreFacultad) {
        this.facultadId = facultadId;
        this.nombreFacultad = nombreFacultad;
    }

    public String getFacultadId() {
        return facultadId;
    }

    public void setFacultadId(String facultadId) {
        this.facultadId = facultadId;
    }

    public String getNombreFacultad() {
        return nombreFacultad;
    }

    public void setNombreFacultad(String nombreFacultad) {
        this.nombreFacultad = nombreFacultad;
    }

    public Universidad getIdUniversidad() {
        return idUniversidad;
    }

    public void setIdUniversidad(Universidad idUniversidad) {
        this.idUniversidad = idUniversidad;
    }

    @XmlTransient
    public List<ProgramaAcademico> getProgramaAcademicoList() {
        return programaAcademicoList;
    }

    public void setProgramaAcademicoList(List<ProgramaAcademico> programaAcademicoList) {
        this.programaAcademicoList = programaAcademicoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facultadId != null ? facultadId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Facultad)) {
            return false;
        }
        Facultad other = (Facultad) object;
        if ((this.facultadId == null && other.facultadId != null) || (this.facultadId != null && !this.facultadId.equals(other.facultadId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Facultad[ facultadId=" + facultadId + " ]";
    }
    
}
