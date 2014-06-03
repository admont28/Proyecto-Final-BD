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
@Table(name = "AUX_CUMPLIMIENTO_ACTIVIDADES", catalog = "", schema = "PROYECTO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuxCumplimientoActividades.findAll", query = "SELECT a FROM AuxCumplimientoActividades a"),
    @NamedQuery(name = "AuxCumplimientoActividades.findByIdAuxiliar", query = "SELECT a FROM AuxCumplimientoActividades a WHERE a.auxCumplimientoActividadesPK.idAuxiliar = :idAuxiliar"),
    @NamedQuery(name = "AuxCumplimientoActividades.findByIdCumplimientoActividades", query = "SELECT a FROM AuxCumplimientoActividades a WHERE a.auxCumplimientoActividadesPK.idCumplimientoActividades = :idCumplimientoActividades")})
public class AuxCumplimientoActividades implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AuxCumplimientoActividadesPK auxCumplimientoActividadesPK;
    @JoinColumn(name = "ID_TIPO_AUXILIAR", referencedColumnName = "TIPO_AUXILIAR_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoAuxiliar idTipoAuxiliar;
    @JoinColumn(name = "ID_CUMPLIMIENTO_ACTIVIDADES", referencedColumnName = "CUMP_ACT_ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CumplimientoActividades cumplimientoActividades;
    @JoinColumn(name = "ID_AUXILIAR", referencedColumnName = "AUXILIAR_ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Auxiliar auxiliar;

    public AuxCumplimientoActividades() {
    }

    public AuxCumplimientoActividades(AuxCumplimientoActividadesPK auxCumplimientoActividadesPK) {
        this.auxCumplimientoActividadesPK = auxCumplimientoActividadesPK;
    }

    public AuxCumplimientoActividades(String idAuxiliar, String idCumplimientoActividades) {
        this.auxCumplimientoActividadesPK = new AuxCumplimientoActividadesPK(idAuxiliar, idCumplimientoActividades);
    }

    public AuxCumplimientoActividadesPK getAuxCumplimientoActividadesPK() {
        return auxCumplimientoActividadesPK;
    }

    public void setAuxCumplimientoActividadesPK(AuxCumplimientoActividadesPK auxCumplimientoActividadesPK) {
        this.auxCumplimientoActividadesPK = auxCumplimientoActividadesPK;
    }

    public TipoAuxiliar getIdTipoAuxiliar() {
        return idTipoAuxiliar;
    }

    public void setIdTipoAuxiliar(TipoAuxiliar idTipoAuxiliar) {
        this.idTipoAuxiliar = idTipoAuxiliar;
    }

    public CumplimientoActividades getCumplimientoActividades() {
        return cumplimientoActividades;
    }

    public void setCumplimientoActividades(CumplimientoActividades cumplimientoActividades) {
        this.cumplimientoActividades = cumplimientoActividades;
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
        hash += (auxCumplimientoActividadesPK != null ? auxCumplimientoActividadesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuxCumplimientoActividades)) {
            return false;
        }
        AuxCumplimientoActividades other = (AuxCumplimientoActividades) object;
        if ((this.auxCumplimientoActividadesPK == null && other.auxCumplimientoActividadesPK != null) || (this.auxCumplimientoActividadesPK != null && !this.auxCumplimientoActividadesPK.equals(other.auxCumplimientoActividadesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.AuxCumplimientoActividades[ auxCumplimientoActividadesPK=" + auxCumplimientoActividadesPK + " ]";
    }
    
}
