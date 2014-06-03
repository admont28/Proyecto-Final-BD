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
public class InscripcionConvocatoriaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_AUXILIAR", nullable = false, length = 20)
    private String idAuxiliar;
    @Basic(optional = false)
    @Column(name = "ID_CONVOCATORIA", nullable = false, length = 20)
    private String idConvocatoria;

    public InscripcionConvocatoriaPK() {
    }

    public InscripcionConvocatoriaPK(String idAuxiliar, String idConvocatoria) {
        this.idAuxiliar = idAuxiliar;
        this.idConvocatoria = idConvocatoria;
    }

    public String getIdAuxiliar() {
        return idAuxiliar;
    }

    public void setIdAuxiliar(String idAuxiliar) {
        this.idAuxiliar = idAuxiliar;
    }

    public String getIdConvocatoria() {
        return idConvocatoria;
    }

    public void setIdConvocatoria(String idConvocatoria) {
        this.idConvocatoria = idConvocatoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuxiliar != null ? idAuxiliar.hashCode() : 0);
        hash += (idConvocatoria != null ? idConvocatoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InscripcionConvocatoriaPK)) {
            return false;
        }
        InscripcionConvocatoriaPK other = (InscripcionConvocatoriaPK) object;
        if ((this.idAuxiliar == null && other.idAuxiliar != null) || (this.idAuxiliar != null && !this.idAuxiliar.equals(other.idAuxiliar))) {
            return false;
        }
        if ((this.idConvocatoria == null && other.idConvocatoria != null) || (this.idConvocatoria != null && !this.idConvocatoria.equals(other.idConvocatoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.InscripcionConvocatoriaPK[ idAuxiliar=" + idAuxiliar + ", idConvocatoria=" + idConvocatoria + " ]";
    }
    
}
