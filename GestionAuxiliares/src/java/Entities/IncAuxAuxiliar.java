/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DavidMontoya
 */
@Entity
@Table(name = "INC_AUX_AUXILIAR", catalog = "", schema = "PROYECTO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IncAuxAuxiliar.findAll", query = "SELECT i FROM IncAuxAuxiliar i"),
    @NamedQuery(name = "IncAuxAuxiliar.findByIdInconformidadAuxiliares", query = "SELECT i FROM IncAuxAuxiliar i WHERE i.incAuxAuxiliarPK.idInconformidadAuxiliares = :idInconformidadAuxiliares"),
    @NamedQuery(name = "IncAuxAuxiliar.findByIdAuxiliar", query = "SELECT i FROM IncAuxAuxiliar i WHERE i.incAuxAuxiliarPK.idAuxiliar = :idAuxiliar")})
public class IncAuxAuxiliar implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IncAuxAuxiliarPK incAuxAuxiliarPK;
    @JoinColumn(name = "ID_SOLICITANTE", referencedColumnName = "SOLICITANTE_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Solicitante idSolicitante;
    @JoinColumn(name = "ID_INCONFORMIDAD_AUXILIARES", referencedColumnName = "INCONFORMIDAD_AUXILIARES_ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private InconformidadAuxiliares inconformidadAuxiliares;
    @JoinColumn(name = "ID_AUXILIAR", referencedColumnName = "AUXILIAR_ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Auxiliar auxiliar;

    public IncAuxAuxiliar() {
    }

    public IncAuxAuxiliar(IncAuxAuxiliarPK incAuxAuxiliarPK) {
        this.incAuxAuxiliarPK = incAuxAuxiliarPK;
    }

    public IncAuxAuxiliar(String idInconformidadAuxiliares, String idAuxiliar) {
        this.incAuxAuxiliarPK = new IncAuxAuxiliarPK(idInconformidadAuxiliares, idAuxiliar);
    }

    public IncAuxAuxiliarPK getIncAuxAuxiliarPK() {
        return incAuxAuxiliarPK;
    }

    public void setIncAuxAuxiliarPK(IncAuxAuxiliarPK incAuxAuxiliarPK) {
        this.incAuxAuxiliarPK = incAuxAuxiliarPK;
    }

    public Solicitante getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(Solicitante idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public InconformidadAuxiliares getInconformidadAuxiliares() {
        return inconformidadAuxiliares;
    }

    public void setInconformidadAuxiliares(InconformidadAuxiliares inconformidadAuxiliares) {
        this.inconformidadAuxiliares = inconformidadAuxiliares;
    }

    public Auxiliar getAuxiliar() {
        return auxiliar;
    }

    public void setAuxiliar(Auxiliar auxiliar) {
        this.auxiliar = auxiliar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (incAuxAuxiliarPK != null ? incAuxAuxiliarPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IncAuxAuxiliar)) {
            return false;
        }
        IncAuxAuxiliar other = (IncAuxAuxiliar) object;
        if ((this.incAuxAuxiliarPK == null && other.incAuxAuxiliarPK != null) || (this.incAuxAuxiliarPK != null && !this.incAuxAuxiliarPK.equals(other.incAuxAuxiliarPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.IncAuxAuxiliar[ incAuxAuxiliarPK=" + incAuxAuxiliarPK + " ]";
    }
    
}
