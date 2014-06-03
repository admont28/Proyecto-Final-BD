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
public class DetalleResolucionPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_AUXILIAR", nullable = false, length = 20)
    private String idAuxiliar;
    @Basic(optional = false)
    @Column(name = "ID_RES_AUX_SEL", nullable = false, length = 20)
    private String idResAuxSel;

    public DetalleResolucionPK() {
    }

    public DetalleResolucionPK(String idAuxiliar, String idResAuxSel) {
        this.idAuxiliar = idAuxiliar;
        this.idResAuxSel = idResAuxSel;
    }

    public String getIdAuxiliar() {
        return idAuxiliar;
    }

    public void setIdAuxiliar(String idAuxiliar) {
        this.idAuxiliar = idAuxiliar;
    }

    public String getIdResAuxSel() {
        return idResAuxSel;
    }

    public void setIdResAuxSel(String idResAuxSel) {
        this.idResAuxSel = idResAuxSel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuxiliar != null ? idAuxiliar.hashCode() : 0);
        hash += (idResAuxSel != null ? idResAuxSel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleResolucionPK)) {
            return false;
        }
        DetalleResolucionPK other = (DetalleResolucionPK) object;
        if ((this.idAuxiliar == null && other.idAuxiliar != null) || (this.idAuxiliar != null && !this.idAuxiliar.equals(other.idAuxiliar))) {
            return false;
        }
        if ((this.idResAuxSel == null && other.idResAuxSel != null) || (this.idResAuxSel != null && !this.idResAuxSel.equals(other.idResAuxSel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.DetalleResolucionPK[ idAuxiliar=" + idAuxiliar + ", idResAuxSel=" + idResAuxSel + " ]";
    }
    
}
