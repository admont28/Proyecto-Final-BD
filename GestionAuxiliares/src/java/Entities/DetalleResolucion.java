/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
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
@Table(name = "DETALLE_RESOLUCION", catalog = "", schema = "PROYECTO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleResolucion.findAll", query = "SELECT d FROM DetalleResolucion d"),
    @NamedQuery(name = "DetalleResolucion.findByPromAuxDetRes", query = "SELECT d FROM DetalleResolucion d WHERE d.promAuxDetRes = :promAuxDetRes"),
    @NamedQuery(name = "DetalleResolucion.findByValorPagarDetalleRes", query = "SELECT d FROM DetalleResolucion d WHERE d.valorPagarDetalleRes = :valorPagarDetalleRes"),
    @NamedQuery(name = "DetalleResolucion.findByCantHorasDetalleRes", query = "SELECT d FROM DetalleResolucion d WHERE d.cantHorasDetalleRes = :cantHorasDetalleRes"),
    @NamedQuery(name = "DetalleResolucion.findByIdAuxiliar", query = "SELECT d FROM DetalleResolucion d WHERE d.detalleResolucionPK.idAuxiliar = :idAuxiliar"),
    @NamedQuery(name = "DetalleResolucion.findByIdResAuxSel", query = "SELECT d FROM DetalleResolucion d WHERE d.detalleResolucionPK.idResAuxSel = :idResAuxSel")})
public class DetalleResolucion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DetalleResolucionPK detalleResolucionPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "PROM_AUX_DET_RES", nullable = false, precision = 2, scale = 1)
    private BigDecimal promAuxDetRes;
    @Basic(optional = false)
    @Column(name = "VALOR_PAGAR_DETALLE_RES", nullable = false)
    private long valorPagarDetalleRes;
    @Basic(optional = false)
    @Column(name = "CANT_HORAS_DETALLE_RES", nullable = false)
    private int cantHorasDetalleRes;
    @JoinColumn(name = "ID_RES_AUX_SEL", referencedColumnName = "RES_AUX_SEL_ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ResAuxiliaresSeleccionados resAuxiliaresSeleccionados;
    @JoinColumn(name = "ID_AUXILIAR", referencedColumnName = "AUXILIAR_ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Auxiliar auxiliar;

    public DetalleResolucion() {
    }

    public DetalleResolucion(DetalleResolucionPK detalleResolucionPK) {
        this.detalleResolucionPK = detalleResolucionPK;
    }

    public DetalleResolucion(DetalleResolucionPK detalleResolucionPK, BigDecimal promAuxDetRes, long valorPagarDetalleRes, int cantHorasDetalleRes) {
        this.detalleResolucionPK = detalleResolucionPK;
        this.promAuxDetRes = promAuxDetRes;
        this.valorPagarDetalleRes = valorPagarDetalleRes;
        this.cantHorasDetalleRes = cantHorasDetalleRes;
    }

    public DetalleResolucion(String idAuxiliar, String idResAuxSel) {
        this.detalleResolucionPK = new DetalleResolucionPK(idAuxiliar, idResAuxSel);
    }

    public DetalleResolucionPK getDetalleResolucionPK() {
        return detalleResolucionPK;
    }

    public void setDetalleResolucionPK(DetalleResolucionPK detalleResolucionPK) {
        this.detalleResolucionPK = detalleResolucionPK;
    }

    public BigDecimal getPromAuxDetRes() {
        return promAuxDetRes;
    }

    public void setPromAuxDetRes(BigDecimal promAuxDetRes) {
        this.promAuxDetRes = promAuxDetRes;
    }

    public long getValorPagarDetalleRes() {
        return valorPagarDetalleRes;
    }

    public void setValorPagarDetalleRes(long valorPagarDetalleRes) {
        this.valorPagarDetalleRes = valorPagarDetalleRes;
    }

    public int getCantHorasDetalleRes() {
        return cantHorasDetalleRes;
    }

    public void setCantHorasDetalleRes(int cantHorasDetalleRes) {
        this.cantHorasDetalleRes = cantHorasDetalleRes;
    }

    public ResAuxiliaresSeleccionados getResAuxiliaresSeleccionados() {
        return resAuxiliaresSeleccionados;
    }

    public void setResAuxiliaresSeleccionados(ResAuxiliaresSeleccionados resAuxiliaresSeleccionados) {
        this.resAuxiliaresSeleccionados = resAuxiliaresSeleccionados;
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
        hash += (detalleResolucionPK != null ? detalleResolucionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleResolucion)) {
            return false;
        }
        DetalleResolucion other = (DetalleResolucion) object;
        if ((this.detalleResolucionPK == null && other.detalleResolucionPK != null) || (this.detalleResolucionPK != null && !this.detalleResolucionPK.equals(other.detalleResolucionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.DetalleResolucion[ detalleResolucionPK=" + detalleResolucionPK + " ]";
    }
    
}
