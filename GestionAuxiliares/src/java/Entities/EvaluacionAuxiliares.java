/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DavidMontoya
 */
@Entity
@Table(name = "EVALUACION_AUXILIARES", catalog = "", schema = "PROYECTO_FINAL", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ID_CONVOCATORIA"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EvaluacionAuxiliares.findAll", query = "SELECT e FROM EvaluacionAuxiliares e"),
    @NamedQuery(name = "EvaluacionAuxiliares.findByEvaluacionAuxiliaresId", query = "SELECT e FROM EvaluacionAuxiliares e WHERE e.evaluacionAuxiliaresId = :evaluacionAuxiliaresId"),
    @NamedQuery(name = "EvaluacionAuxiliares.findByDescEvaluacionAuxiliares", query = "SELECT e FROM EvaluacionAuxiliares e WHERE e.descEvaluacionAuxiliares = :descEvaluacionAuxiliares"),
    @NamedQuery(name = "EvaluacionAuxiliares.findByFechaCreacion", query = "SELECT e FROM EvaluacionAuxiliares e WHERE e.fechaCreacion = :fechaCreacion")})
public class EvaluacionAuxiliares implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "EVALUACION_AUXILIARES_ID", nullable = false, length = 20)
    private String evaluacionAuxiliaresId;
    @Basic(optional = false)
    @Column(name = "DESC_EVALUACION_AUXILIARES", nullable = false, length = 255)
    private String descEvaluacionAuxiliares;
    @Basic(optional = false)
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluacionAuxiliares", fetch = FetchType.LAZY)
    private List<SeleccionAuxiliares> seleccionAuxiliaresList;
    @JoinColumn(name = "ID_SOLICITANTE", referencedColumnName = "SOLICITANTE_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Solicitante idSolicitante;
    @JoinColumn(name = "ID_CONVOCATORIA", referencedColumnName = "CONVOCATORIA_ID", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Convocatoria idConvocatoria;

    public EvaluacionAuxiliares() {
    }

    public EvaluacionAuxiliares(String evaluacionAuxiliaresId) {
        this.evaluacionAuxiliaresId = evaluacionAuxiliaresId;
    }

    public EvaluacionAuxiliares(String evaluacionAuxiliaresId, String descEvaluacionAuxiliares, Date fechaCreacion) {
        this.evaluacionAuxiliaresId = evaluacionAuxiliaresId;
        this.descEvaluacionAuxiliares = descEvaluacionAuxiliares;
        this.fechaCreacion = fechaCreacion;
    }

    public String getEvaluacionAuxiliaresId() {
        return evaluacionAuxiliaresId;
    }

    public void setEvaluacionAuxiliaresId(String evaluacionAuxiliaresId) {
        this.evaluacionAuxiliaresId = evaluacionAuxiliaresId;
    }

    public String getDescEvaluacionAuxiliares() {
        return descEvaluacionAuxiliares;
    }

    public void setDescEvaluacionAuxiliares(String descEvaluacionAuxiliares) {
        this.descEvaluacionAuxiliares = descEvaluacionAuxiliares;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @XmlTransient
    public List<SeleccionAuxiliares> getSeleccionAuxiliaresList() {
        return seleccionAuxiliaresList;
    }

    public void setSeleccionAuxiliaresList(List<SeleccionAuxiliares> seleccionAuxiliaresList) {
        this.seleccionAuxiliaresList = seleccionAuxiliaresList;
    }

    public Solicitante getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(Solicitante idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public Convocatoria getIdConvocatoria() {
        return idConvocatoria;
    }

    public void setIdConvocatoria(Convocatoria idConvocatoria) {
        this.idConvocatoria = idConvocatoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evaluacionAuxiliaresId != null ? evaluacionAuxiliaresId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionAuxiliares)) {
            return false;
        }
        EvaluacionAuxiliares other = (EvaluacionAuxiliares) object;
        if ((this.evaluacionAuxiliaresId == null && other.evaluacionAuxiliaresId != null) || (this.evaluacionAuxiliaresId != null && !this.evaluacionAuxiliaresId.equals(other.evaluacionAuxiliaresId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.EvaluacionAuxiliares[ evaluacionAuxiliaresId=" + evaluacionAuxiliaresId + " ]";
    }
    
}
