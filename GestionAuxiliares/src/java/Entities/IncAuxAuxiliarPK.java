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
public class IncAuxAuxiliarPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_INCONFORMIDAD_AUXILIARES", nullable = false, length = 20)
    private String idInconformidadAuxiliares;
    @Basic(optional = false)
    @Column(name = "ID_AUXILIAR", nullable = false, length = 20)
    private String idAuxiliar;

    public IncAuxAuxiliarPK() {
    }

    public IncAuxAuxiliarPK(String idInconformidadAuxiliares, String idAuxiliar) {
        this.idInconformidadAuxiliares = idInconformidadAuxiliares;
        this.idAuxiliar = idAuxiliar;
    }

    public String getIdInconformidadAuxiliares() {
        return idInconformidadAuxiliares;
    }

    public void setIdInconformidadAuxiliares(String idInconformidadAuxiliares) {
        this.idInconformidadAuxiliares = idInconformidadAuxiliares;
    }

    public String getIdAuxiliar() {
        return idAuxiliar;
    }

    public void setIdAuxiliar(String idAuxiliar) {
        this.idAuxiliar = idAuxiliar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInconformidadAuxiliares != null ? idInconformidadAuxiliares.hashCode() : 0);
        hash += (idAuxiliar != null ? idAuxiliar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IncAuxAuxiliarPK)) {
            return false;
        }
        IncAuxAuxiliarPK other = (IncAuxAuxiliarPK) object;
        if ((this.idInconformidadAuxiliares == null && other.idInconformidadAuxiliares != null) || (this.idInconformidadAuxiliares != null && !this.idInconformidadAuxiliares.equals(other.idInconformidadAuxiliares))) {
            return false;
        }
        if ((this.idAuxiliar == null && other.idAuxiliar != null) || (this.idAuxiliar != null && !this.idAuxiliar.equals(other.idAuxiliar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.IncAuxAuxiliarPK[ idInconformidadAuxiliares=" + idInconformidadAuxiliares + ", idAuxiliar=" + idAuxiliar + " ]";
    }
    
}
