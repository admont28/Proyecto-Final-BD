/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author DavidMontoya
 */
@Embeddable
public class DirectoresPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_PROGRAMA", nullable = false, length = 20)
    private String idPrograma;
    @Basic(optional = false)
    @Column(name = "ID_JORNADA", nullable = false, length = 20)
    private String idJornada;

    public DirectoresPK() {
    }

    public DirectoresPK(String idPrograma, String idJornada) {
        this.idPrograma = idPrograma;
        this.idJornada = idJornada;
    }

    public String getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(String idPrograma) {
        this.idPrograma = idPrograma;
    }

    public String getIdJornada() {
        return idJornada;
    }

    public void setIdJornada(String idJornada) {
        this.idJornada = idJornada;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrograma != null ? idPrograma.hashCode() : 0);
        hash += (idJornada != null ? idJornada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DirectoresPK)) {
            return false;
        }
        DirectoresPK other = (DirectoresPK) object;
        if ((this.idPrograma == null && other.idPrograma != null) || (this.idPrograma != null && !this.idPrograma.equals(other.idPrograma))) {
            return false;
        }
        if ((this.idJornada == null && other.idJornada != null) || (this.idJornada != null && !this.idJornada.equals(other.idJornada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.DirectoresPK[ idPrograma=" + idPrograma + ", idJornada=" + idJornada + " ]";
    }
    
}
