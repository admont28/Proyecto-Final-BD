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
@Table(name = "JORNADA", catalog = "", schema = "PROYECTO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jornada.findAll", query = "SELECT j FROM Jornada j"),
    @NamedQuery(name = "Jornada.findByJornadaId", query = "SELECT j FROM Jornada j WHERE j.jornadaId = :jornadaId"),
    @NamedQuery(name = "Jornada.findByNombreJornada", query = "SELECT j FROM Jornada j WHERE j.nombreJornada = :nombreJornada")})
public class Jornada implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "JORNADA_ID", nullable = false, length = 20)
    private String jornadaId;
    @Basic(optional = false)
    @Column(name = "NOMBRE_JORNADA", nullable = false, length = 20)
    private String nombreJornada;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jornada", fetch = FetchType.LAZY)
    private List<Directores> directoresList;

    public Jornada() {
    }

    public Jornada(String jornadaId) {
        this.jornadaId = jornadaId;
    }

    public Jornada(String jornadaId, String nombreJornada) {
        this.jornadaId = jornadaId;
        this.nombreJornada = nombreJornada;
    }

    public String getJornadaId() {
        return jornadaId;
    }

    public void setJornadaId(String jornadaId) {
        this.jornadaId = jornadaId;
    }

    public String getNombreJornada() {
        return nombreJornada;
    }

    public void setNombreJornada(String nombreJornada) {
        this.nombreJornada = nombreJornada;
    }

    @XmlTransient
    public List<Directores> getDirectoresList() {
        return directoresList;
    }

    public void setDirectoresList(List<Directores> directoresList) {
        this.directoresList = directoresList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jornadaId != null ? jornadaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jornada)) {
            return false;
        }
        Jornada other = (Jornada) object;
        if ((this.jornadaId == null && other.jornadaId != null) || (this.jornadaId != null && !this.jornadaId.equals(other.jornadaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Jornada[ jornadaId=" + jornadaId + " ]";
    }
    
}
