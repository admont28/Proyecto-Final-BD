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
@Table(name = "SELECCION_AUXILIARES", catalog = "", schema = "PROYECTO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SeleccionAuxiliares.findAll", query = "SELECT s FROM SeleccionAuxiliares s"),
    @NamedQuery(name = "SeleccionAuxiliares.findByCalificacion", query = "SELECT s FROM SeleccionAuxiliares s WHERE s.calificacion = :calificacion"),
    @NamedQuery(name = "SeleccionAuxiliares.findByEstado", query = "SELECT s FROM SeleccionAuxiliares s WHERE s.estado = :estado"),
    @NamedQuery(name = "SeleccionAuxiliares.findByPromedio", query = "SELECT s FROM SeleccionAuxiliares s WHERE s.promedio = :promedio"),
    @NamedQuery(name = "SeleccionAuxiliares.findByIdAuxiliares", query = "SELECT s FROM SeleccionAuxiliares s WHERE s.seleccionAuxiliaresPK.idAuxiliares = :idAuxiliares"),
    @NamedQuery(name = "SeleccionAuxiliares.findByIdEvaluacionAuxiliares", query = "SELECT s FROM SeleccionAuxiliares s WHERE s.seleccionAuxiliaresPK.idEvaluacionAuxiliares = :idEvaluacionAuxiliares")})
public class SeleccionAuxiliares implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SeleccionAuxiliaresPK seleccionAuxiliaresPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "CALIFICACION", nullable = false, precision = 2, scale = 1)
    private BigDecimal calificacion;
    @Basic(optional = false)
    @Column(name = "ESTADO", nullable = false)
    private short estado;
    @Basic(optional = false)
    @Column(name = "PROMEDIO", nullable = false, precision = 2, scale = 1)
    private BigDecimal promedio;
    @JoinColumn(name = "ID_EVALUACION_AUXILIARES", referencedColumnName = "EVALUACION_AUXILIARES_ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EvaluacionAuxiliares evaluacionAuxiliares;
    @JoinColumn(name = "ID_AUXILIARES", referencedColumnName = "AUXILIAR_ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Auxiliar auxiliar;

    public SeleccionAuxiliares() {
    }

    public SeleccionAuxiliares(SeleccionAuxiliaresPK seleccionAuxiliaresPK) {
        this.seleccionAuxiliaresPK = seleccionAuxiliaresPK;
    }

    public SeleccionAuxiliares(SeleccionAuxiliaresPK seleccionAuxiliaresPK, BigDecimal calificacion, short estado, BigDecimal promedio) {
        this.seleccionAuxiliaresPK = seleccionAuxiliaresPK;
        this.calificacion = calificacion;
        this.estado = estado;
        this.promedio = promedio;
    }

    public SeleccionAuxiliares(String idAuxiliares, String idEvaluacionAuxiliares) {
        this.seleccionAuxiliaresPK = new SeleccionAuxiliaresPK(idAuxiliares, idEvaluacionAuxiliares);
    }

    public SeleccionAuxiliaresPK getSeleccionAuxiliaresPK() {
        return seleccionAuxiliaresPK;
    }

    public void setSeleccionAuxiliaresPK(SeleccionAuxiliaresPK seleccionAuxiliaresPK) {
        this.seleccionAuxiliaresPK = seleccionAuxiliaresPK;
    }

    public BigDecimal getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(BigDecimal calificacion) {
        this.calificacion = calificacion;
    }

    public short getEstado() {
        return estado;
    }

    public void setEstado(short estado) {
        this.estado = estado;
    }

    public BigDecimal getPromedio() {
        return promedio;
    }

    public void setPromedio(BigDecimal promedio) {
        this.promedio = promedio;
    }

    public EvaluacionAuxiliares getEvaluacionAuxiliares() {
        return evaluacionAuxiliares;
    }

    public void setEvaluacionAuxiliares(EvaluacionAuxiliares evaluacionAuxiliares) {
        this.evaluacionAuxiliares = evaluacionAuxiliares;
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
        hash += (seleccionAuxiliaresPK != null ? seleccionAuxiliaresPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SeleccionAuxiliares)) {
            return false;
        }
        SeleccionAuxiliares other = (SeleccionAuxiliares) object;
        if ((this.seleccionAuxiliaresPK == null && other.seleccionAuxiliaresPK != null) || (this.seleccionAuxiliaresPK != null && !this.seleccionAuxiliaresPK.equals(other.seleccionAuxiliaresPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SeleccionAuxiliares[ seleccionAuxiliaresPK=" + seleccionAuxiliaresPK + " ]";
    }
    
}
