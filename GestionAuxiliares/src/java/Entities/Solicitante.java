/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DavidMontoya
 */
@Entity
@Table(name = "SOLICITANTE", catalog = "", schema = "PROYECTO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitante.findAll", query = "SELECT s FROM Solicitante s"),
    @NamedQuery(name = "Solicitante.findBySolicitanteId", query = "SELECT s FROM Solicitante s WHERE s.solicitanteId = :solicitanteId"),
    @NamedQuery(name = "Solicitante.findByNombreSolicitante", query = "SELECT s FROM Solicitante s WHERE s.nombreSolicitante = :nombreSolicitante"),
    @NamedQuery(name = "Solicitante.findByApellidoSolicitante", query = "SELECT s FROM Solicitante s WHERE s.apellidoSolicitante = :apellidoSolicitante"),
    @NamedQuery(name = "Solicitante.findByTelefonoSolicitante", query = "SELECT s FROM Solicitante s WHERE s.telefonoSolicitante = :telefonoSolicitante"),
    @NamedQuery(name = "Solicitante.findByPasswordSolicitante", query = "SELECT s FROM Solicitante s WHERE s.passwordSolicitante = :passwordSolicitante")})
public class Solicitante implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SOLICITANTE_ID", nullable = false, length = 20)
    private String solicitanteId;
    @Basic(optional = false)
    @Column(name = "NOMBRE_SOLICITANTE", nullable = false, length = 35)
    private String nombreSolicitante;
    @Basic(optional = false)
    @Column(name = "APELLIDO_SOLICITANTE", nullable = false, length = 35)
    private String apellidoSolicitante;
    @Basic(optional = false)
    @Column(name = "TELEFONO_SOLICITANTE", nullable = false, length = 20)
    private String telefonoSolicitante;
    @Basic(optional = false)
    @Column(name = "PASSWORD_SOLICITANTE", nullable = false, length = 255)
    private String passwordSolicitante;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idSolicitante", fetch = FetchType.LAZY)
    private Directores directores;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSolicitante", fetch = FetchType.LAZY)
    private List<CumplimientoActividades> cumplimientoActividadesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSolicitante", fetch = FetchType.LAZY)
    private List<IncAuxAuxiliar> incAuxAuxiliarList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSolicitante", fetch = FetchType.LAZY)
    private List<SolicitudAuxiliares> solicitudAuxiliaresList;
    @JoinColumn(name = "ID_PROGRAMA_ACADEMICO", referencedColumnName = "PROGRAMA_ACADEMICO_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ProgramaAcademico idProgramaAcademico;
    @JoinColumn(name = "ID_DIRECCION", referencedColumnName = "DIRECCION_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Direccion idDireccion;
    @JoinColumn(name = "ID_CONSEJO_CURRICULAR", referencedColumnName = "CONSEJO_CURRICULAR_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private ConsejoCurricular idConsejoCurricular;
    @JoinColumn(name = "ID_CARGO", referencedColumnName = "CARGO_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cargo idCargo;
    @JoinColumn(name = "ID_AREA", referencedColumnName = "AREA_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Area idArea;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSolicitante", fetch = FetchType.LAZY)
    private List<ResAuxiliaresSeleccionados> resAuxiliaresSeleccionadosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSolicitante", fetch = FetchType.LAZY)
    private List<EvaluacionAuxiliares> evaluacionAuxiliaresList;

    public Solicitante() {
    }

    public Solicitante(String solicitanteId) {
        this.solicitanteId = solicitanteId;
    }

    public Solicitante(String solicitanteId, String nombreSolicitante, String apellidoSolicitante, String telefonoSolicitante, String passwordSolicitante) {
        this.solicitanteId = solicitanteId;
        this.nombreSolicitante = nombreSolicitante;
        this.apellidoSolicitante = apellidoSolicitante;
        this.telefonoSolicitante = telefonoSolicitante;
        this.passwordSolicitante = passwordSolicitante;
    }

    public String getSolicitanteId() {
        return solicitanteId;
    }

    public void setSolicitanteId(String solicitanteId) {
        this.solicitanteId = solicitanteId;
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    public String getApellidoSolicitante() {
        return apellidoSolicitante;
    }

    public void setApellidoSolicitante(String apellidoSolicitante) {
        this.apellidoSolicitante = apellidoSolicitante;
    }

    public String getTelefonoSolicitante() {
        return telefonoSolicitante;
    }

    public void setTelefonoSolicitante(String telefonoSolicitante) {
        this.telefonoSolicitante = telefonoSolicitante;
    }

    public String getPasswordSolicitante() {
        return passwordSolicitante;
    }

    public void setPasswordSolicitante(String passwordSolicitante) {
        this.passwordSolicitante = passwordSolicitante;
    }

    public Directores getDirectores() {
        return directores;
    }

    public void setDirectores(Directores directores) {
        this.directores = directores;
    }

    @XmlTransient
    public List<CumplimientoActividades> getCumplimientoActividadesList() {
        return cumplimientoActividadesList;
    }

    public void setCumplimientoActividadesList(List<CumplimientoActividades> cumplimientoActividadesList) {
        this.cumplimientoActividadesList = cumplimientoActividadesList;
    }

    @XmlTransient
    public List<IncAuxAuxiliar> getIncAuxAuxiliarList() {
        return incAuxAuxiliarList;
    }

    public void setIncAuxAuxiliarList(List<IncAuxAuxiliar> incAuxAuxiliarList) {
        this.incAuxAuxiliarList = incAuxAuxiliarList;
    }

    @XmlTransient
    public List<SolicitudAuxiliares> getSolicitudAuxiliaresList() {
        return solicitudAuxiliaresList;
    }

    public void setSolicitudAuxiliaresList(List<SolicitudAuxiliares> solicitudAuxiliaresList) {
        this.solicitudAuxiliaresList = solicitudAuxiliaresList;
    }

    public ProgramaAcademico getIdProgramaAcademico() {
        return idProgramaAcademico;
    }

    public void setIdProgramaAcademico(ProgramaAcademico idProgramaAcademico) {
        this.idProgramaAcademico = idProgramaAcademico;
    }

    public Direccion getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Direccion idDireccion) {
        this.idDireccion = idDireccion;
    }

    public ConsejoCurricular getIdConsejoCurricular() {
        return idConsejoCurricular;
    }

    public void setIdConsejoCurricular(ConsejoCurricular idConsejoCurricular) {
        this.idConsejoCurricular = idConsejoCurricular;
    }

    public Cargo getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Cargo idCargo) {
        this.idCargo = idCargo;
    }

    public Area getIdArea() {
        return idArea;
    }

    public void setIdArea(Area idArea) {
        this.idArea = idArea;
    }

    @XmlTransient
    public List<ResAuxiliaresSeleccionados> getResAuxiliaresSeleccionadosList() {
        return resAuxiliaresSeleccionadosList;
    }

    public void setResAuxiliaresSeleccionadosList(List<ResAuxiliaresSeleccionados> resAuxiliaresSeleccionadosList) {
        this.resAuxiliaresSeleccionadosList = resAuxiliaresSeleccionadosList;
    }

    @XmlTransient
    public List<EvaluacionAuxiliares> getEvaluacionAuxiliaresList() {
        return evaluacionAuxiliaresList;
    }

    public void setEvaluacionAuxiliaresList(List<EvaluacionAuxiliares> evaluacionAuxiliaresList) {
        this.evaluacionAuxiliaresList = evaluacionAuxiliaresList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (solicitanteId != null ? solicitanteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Solicitante)) {
            return false;
        }
        Solicitante other = (Solicitante) object;
        if ((this.solicitanteId == null && other.solicitanteId != null) || (this.solicitanteId != null && !this.solicitanteId.equals(other.solicitanteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Solicitante[ solicitanteId=" + solicitanteId + " ]";
    }
    
}
