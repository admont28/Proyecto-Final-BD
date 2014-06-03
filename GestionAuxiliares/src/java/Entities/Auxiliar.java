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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DavidMontoya
 */
@Entity
@Table(name = "AUXILIAR", catalog = "", schema = "PROYECTO_FINAL", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"CODIGO_AUXILIAR"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Auxiliar.findAll", query = "SELECT a FROM Auxiliar a"),
    @NamedQuery(name = "Auxiliar.findByAuxiliarId", query = "SELECT a FROM Auxiliar a WHERE a.auxiliarId = :auxiliarId"),
    @NamedQuery(name = "Auxiliar.findByCodigoAuxiliar", query = "SELECT a FROM Auxiliar a WHERE a.codigoAuxiliar = :codigoAuxiliar"),
    @NamedQuery(name = "Auxiliar.findByNombreAuxiliar", query = "SELECT a FROM Auxiliar a WHERE a.nombreAuxiliar = :nombreAuxiliar"),
    @NamedQuery(name = "Auxiliar.findByApellidoAuxiliar", query = "SELECT a FROM Auxiliar a WHERE a.apellidoAuxiliar = :apellidoAuxiliar"),
    @NamedQuery(name = "Auxiliar.findByTelefonoAuxiliar", query = "SELECT a FROM Auxiliar a WHERE a.telefonoAuxiliar = :telefonoAuxiliar"),
    @NamedQuery(name = "Auxiliar.findByPasswordAux", query = "SELECT a FROM Auxiliar a WHERE a.passwordAux = :passwordAux")})
public class Auxiliar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "AUXILIAR_ID", nullable = false, length = 20)
    private String auxiliarId;
    @Basic(optional = false)
    @Column(name = "CODIGO_AUXILIAR", nullable = false, length = 20)
    private String codigoAuxiliar;
    @Basic(optional = false)
    @Column(name = "NOMBRE_AUXILIAR", nullable = false, length = 35)
    private String nombreAuxiliar;
    @Basic(optional = false)
    @Column(name = "APELLIDO_AUXILIAR", nullable = false, length = 35)
    private String apellidoAuxiliar;
    @Basic(optional = false)
    @Column(name = "TELEFONO_AUXILIAR", nullable = false, length = 20)
    private String telefonoAuxiliar;
    @Basic(optional = false)
    @Column(name = "PASSWORD_AUX", nullable = false, length = 255)
    private String passwordAux;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auxiliar", fetch = FetchType.LAZY)
    private List<SeleccionAuxiliares> seleccionAuxiliaresList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auxiliar", fetch = FetchType.LAZY)
    private List<DetalleResolucion> detalleResolucionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auxiliar", fetch = FetchType.LAZY)
    private List<InscripcionConvocatoria> inscripcionConvocatoriaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auxiliar", fetch = FetchType.LAZY)
    private List<IncAuxAuxiliar> incAuxAuxiliarList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auxiliar", fetch = FetchType.LAZY)
    private List<AuxCumplimientoActividades> auxCumplimientoActividadesList;
    @JoinColumn(name = "ID_PROGRAMA_ACADEMICO", referencedColumnName = "PROGRAMA_ACADEMICO_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ProgramaAcademico idProgramaAcademico;
    @JoinColumn(name = "ID_DIRECCION", referencedColumnName = "DIRECCION_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Direccion idDireccion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAuxiliar", fetch = FetchType.LAZY)
    private List<HorarioActividades> horarioActividadesList;

    public Auxiliar() {
    }

    public Auxiliar(String auxiliarId) {
        this.auxiliarId = auxiliarId;
    }

    public Auxiliar(String auxiliarId, String codigoAuxiliar, String nombreAuxiliar, String apellidoAuxiliar, String telefonoAuxiliar, String passwordAux) {
        this.auxiliarId = auxiliarId;
        this.codigoAuxiliar = codigoAuxiliar;
        this.nombreAuxiliar = nombreAuxiliar;
        this.apellidoAuxiliar = apellidoAuxiliar;
        this.telefonoAuxiliar = telefonoAuxiliar;
        this.passwordAux = passwordAux;
    }

    public String getAuxiliarId() {
        return auxiliarId;
    }

    public void setAuxiliarId(String auxiliarId) {
        this.auxiliarId = auxiliarId;
    }

    public String getCodigoAuxiliar() {
        return codigoAuxiliar;
    }

    public void setCodigoAuxiliar(String codigoAuxiliar) {
        this.codigoAuxiliar = codigoAuxiliar;
    }

    public String getNombreAuxiliar() {
        return nombreAuxiliar;
    }

    public void setNombreAuxiliar(String nombreAuxiliar) {
        this.nombreAuxiliar = nombreAuxiliar;
    }

    public String getApellidoAuxiliar() {
        return apellidoAuxiliar;
    }

    public void setApellidoAuxiliar(String apellidoAuxiliar) {
        this.apellidoAuxiliar = apellidoAuxiliar;
    }

    public String getTelefonoAuxiliar() {
        return telefonoAuxiliar;
    }

    public void setTelefonoAuxiliar(String telefonoAuxiliar) {
        this.telefonoAuxiliar = telefonoAuxiliar;
    }

    public String getPasswordAux() {
        return passwordAux;
    }

    public void setPasswordAux(String passwordAux) {
        this.passwordAux = passwordAux;
    }

    @XmlTransient
    public List<SeleccionAuxiliares> getSeleccionAuxiliaresList() {
        return seleccionAuxiliaresList;
    }

    public void setSeleccionAuxiliaresList(List<SeleccionAuxiliares> seleccionAuxiliaresList) {
        this.seleccionAuxiliaresList = seleccionAuxiliaresList;
    }

    @XmlTransient
    public List<DetalleResolucion> getDetalleResolucionList() {
        return detalleResolucionList;
    }

    public void setDetalleResolucionList(List<DetalleResolucion> detalleResolucionList) {
        this.detalleResolucionList = detalleResolucionList;
    }

    @XmlTransient
    public List<InscripcionConvocatoria> getInscripcionConvocatoriaList() {
        return inscripcionConvocatoriaList;
    }

    public void setInscripcionConvocatoriaList(List<InscripcionConvocatoria> inscripcionConvocatoriaList) {
        this.inscripcionConvocatoriaList = inscripcionConvocatoriaList;
    }

    @XmlTransient
    public List<IncAuxAuxiliar> getIncAuxAuxiliarList() {
        return incAuxAuxiliarList;
    }

    public void setIncAuxAuxiliarList(List<IncAuxAuxiliar> incAuxAuxiliarList) {
        this.incAuxAuxiliarList = incAuxAuxiliarList;
    }

    @XmlTransient
    public List<AuxCumplimientoActividades> getAuxCumplimientoActividadesList() {
        return auxCumplimientoActividadesList;
    }

    public void setAuxCumplimientoActividadesList(List<AuxCumplimientoActividades> auxCumplimientoActividadesList) {
        this.auxCumplimientoActividadesList = auxCumplimientoActividadesList;
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

    @XmlTransient
    public List<HorarioActividades> getHorarioActividadesList() {
        return horarioActividadesList;
    }

    public void setHorarioActividadesList(List<HorarioActividades> horarioActividadesList) {
        this.horarioActividadesList = horarioActividadesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (auxiliarId != null ? auxiliarId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Auxiliar)) {
            return false;
        }
        Auxiliar other = (Auxiliar) object;
        if ((this.auxiliarId == null && other.auxiliarId != null) || (this.auxiliarId != null && !this.auxiliarId.equals(other.auxiliarId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Auxiliar[ auxiliarId=" + auxiliarId + " ]";
    }
    
}
