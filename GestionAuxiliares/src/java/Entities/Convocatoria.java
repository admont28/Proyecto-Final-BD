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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "CONVOCATORIA", catalog = "", schema = "PROYECTO_FINAL", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ID_SOLICITUD_AUXILIARES"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Convocatoria.findAll", query = "SELECT c FROM Convocatoria c"),
    @NamedQuery(name = "Convocatoria.findByConvocatoriaId", query = "SELECT c FROM Convocatoria c WHERE c.convocatoriaId = :convocatoriaId"),
    @NamedQuery(name = "Convocatoria.findByInfoAdicionalConvocatoria", query = "SELECT c FROM Convocatoria c WHERE c.infoAdicionalConvocatoria = :infoAdicionalConvocatoria"),
    @NamedQuery(name = "Convocatoria.findByFechaCreacion", query = "SELECT c FROM Convocatoria c WHERE c.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Convocatoria.findByFechaApertura", query = "SELECT c FROM Convocatoria c WHERE c.fechaApertura = :fechaApertura"),
    @NamedQuery(name = "Convocatoria.findByFechaCierre", query = "SELECT c FROM Convocatoria c WHERE c.fechaCierre = :fechaCierre"),
    @NamedQuery(name = "Convocatoria.findByFechaPrueba", query = "SELECT c FROM Convocatoria c WHERE c.fechaPrueba = :fechaPrueba")})
public class Convocatoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CONVOCATORIA_ID", nullable = false, length = 20)
    private String convocatoriaId;
    @Basic(optional = false)
    @Column(name = "INFO_ADICIONAL_CONVOCATORIA", nullable = false, length = 255)
    private String infoAdicionalConvocatoria;
    @Basic(optional = false)
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @Column(name = "FECHA_APERTURA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaApertura;
    @Basic(optional = false)
    @Column(name = "FECHA_CIERRE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierre;
    @Column(name = "FECHA_PRUEBA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPrueba;
    @JoinTable(name = "CONVOCATORIA_REQUISITOS", joinColumns = {
        @JoinColumn(name = "ID_CONVOCATORIA", referencedColumnName = "CONVOCATORIA_ID", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "ID_REQUISITOS", referencedColumnName = "REQUISITO_ID", nullable = false)})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Requisito> requisitoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "convocatoria", fetch = FetchType.LAZY)
    private List<InscripcionConvocatoria> inscripcionConvocatoriaList;
    @JoinColumn(name = "ID_TIPO_AUXILIAR", referencedColumnName = "TIPO_AUXILIAR_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoAuxiliar idTipoAuxiliar;
    @JoinColumn(name = "ID_SOLICITUD_AUXILIARES", referencedColumnName = "SOLICITUD_AUXILIARES_ID", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private SolicitudAuxiliares idSolicitudAuxiliares;
    @JoinColumn(name = "ID_PROGRAMA_ACADEMICO", referencedColumnName = "PROGRAMA_ACADEMICO_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ProgramaAcademico idProgramaAcademico;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idConvocatoria", fetch = FetchType.LAZY)
    private EvaluacionAuxiliares evaluacionAuxiliares;

    public Convocatoria() {
    }

    public Convocatoria(String convocatoriaId) {
        this.convocatoriaId = convocatoriaId;
    }

    public Convocatoria(String convocatoriaId, String infoAdicionalConvocatoria, Date fechaCreacion, Date fechaApertura, Date fechaCierre) {
        this.convocatoriaId = convocatoriaId;
        this.infoAdicionalConvocatoria = infoAdicionalConvocatoria;
        this.fechaCreacion = fechaCreacion;
        this.fechaApertura = fechaApertura;
        this.fechaCierre = fechaCierre;
    }

    public String getConvocatoriaId() {
        return convocatoriaId;
    }

    public void setConvocatoriaId(String convocatoriaId) {
        this.convocatoriaId = convocatoriaId;
    }

    public String getInfoAdicionalConvocatoria() {
        return infoAdicionalConvocatoria;
    }

    public void setInfoAdicionalConvocatoria(String infoAdicionalConvocatoria) {
        this.infoAdicionalConvocatoria = infoAdicionalConvocatoria;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Date getFechaPrueba() {
        return fechaPrueba;
    }

    public void setFechaPrueba(Date fechaPrueba) {
        this.fechaPrueba = fechaPrueba;
    }

    @XmlTransient
    public List<Requisito> getRequisitoList() {
        return requisitoList;
    }

    public void setRequisitoList(List<Requisito> requisitoList) {
        this.requisitoList = requisitoList;
    }

    @XmlTransient
    public List<InscripcionConvocatoria> getInscripcionConvocatoriaList() {
        return inscripcionConvocatoriaList;
    }

    public void setInscripcionConvocatoriaList(List<InscripcionConvocatoria> inscripcionConvocatoriaList) {
        this.inscripcionConvocatoriaList = inscripcionConvocatoriaList;
    }

    public TipoAuxiliar getIdTipoAuxiliar() {
        return idTipoAuxiliar;
    }

    public void setIdTipoAuxiliar(TipoAuxiliar idTipoAuxiliar) {
        this.idTipoAuxiliar = idTipoAuxiliar;
    }

    public SolicitudAuxiliares getIdSolicitudAuxiliares() {
        return idSolicitudAuxiliares;
    }

    public void setIdSolicitudAuxiliares(SolicitudAuxiliares idSolicitudAuxiliares) {
        this.idSolicitudAuxiliares = idSolicitudAuxiliares;
    }

    public ProgramaAcademico getIdProgramaAcademico() {
        return idProgramaAcademico;
    }

    public void setIdProgramaAcademico(ProgramaAcademico idProgramaAcademico) {
        this.idProgramaAcademico = idProgramaAcademico;
    }

    public EvaluacionAuxiliares getEvaluacionAuxiliares() {
        return evaluacionAuxiliares;
    }

    public void setEvaluacionAuxiliares(EvaluacionAuxiliares evaluacionAuxiliares) {
        this.evaluacionAuxiliares = evaluacionAuxiliares;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (convocatoriaId != null ? convocatoriaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Convocatoria)) {
            return false;
        }
        Convocatoria other = (Convocatoria) object;
        if ((this.convocatoriaId == null && other.convocatoriaId != null) || (this.convocatoriaId != null && !this.convocatoriaId.equals(other.convocatoriaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Convocatoria[ convocatoriaId=" + convocatoriaId + " ]";
    }
    
}
