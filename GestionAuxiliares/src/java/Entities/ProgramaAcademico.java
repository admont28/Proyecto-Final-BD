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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DavidMontoya
 */
@Entity
@Table(name = "PROGRAMA_ACADEMICO", catalog = "", schema = "PROYECTO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProgramaAcademico.findAll", query = "SELECT p FROM ProgramaAcademico p"),
    @NamedQuery(name = "ProgramaAcademico.findByProgramaAcademicoId", query = "SELECT p FROM ProgramaAcademico p WHERE p.programaAcademicoId = :programaAcademicoId"),
    @NamedQuery(name = "ProgramaAcademico.findByNombrePrograma", query = "SELECT p FROM ProgramaAcademico p WHERE p.nombrePrograma = :nombrePrograma")})
public class ProgramaAcademico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PROGRAMA_ACADEMICO_ID", nullable = false, length = 20)
    private String programaAcademicoId;
    @Basic(optional = false)
    @Column(name = "NOMBRE_PROGRAMA", nullable = false, length = 255)
    private String nombrePrograma;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "programaAcademico", fetch = FetchType.LAZY)
    private List<Directores> directoresList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProgramaAcademico", fetch = FetchType.LAZY)
    private List<Solicitante> solicitanteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProgramaAcademico", fetch = FetchType.LAZY)
    private List<Auxiliar> auxiliarList;
    @JoinColumn(name = "ID_FACULTAD", referencedColumnName = "FACULTAD_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Facultad idFacultad;
    @JoinColumn(name = "ID_CONSEJO_CURRICULAR", referencedColumnName = "CONSEJO_CURRICULAR_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ConsejoCurricular idConsejoCurricular;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProgramaAcademico", fetch = FetchType.LAZY)
    private List<Convocatoria> convocatoriaList;

    public ProgramaAcademico() {
    }

    public ProgramaAcademico(String programaAcademicoId) {
        this.programaAcademicoId = programaAcademicoId;
    }

    public ProgramaAcademico(String programaAcademicoId, String nombrePrograma) {
        this.programaAcademicoId = programaAcademicoId;
        this.nombrePrograma = nombrePrograma;
    }

    public String getProgramaAcademicoId() {
        return programaAcademicoId;
    }

    public void setProgramaAcademicoId(String programaAcademicoId) {
        this.programaAcademicoId = programaAcademicoId;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    @XmlTransient
    public List<Directores> getDirectoresList() {
        return directoresList;
    }

    public void setDirectoresList(List<Directores> directoresList) {
        this.directoresList = directoresList;
    }

    @XmlTransient
    public List<Solicitante> getSolicitanteList() {
        return solicitanteList;
    }

    public void setSolicitanteList(List<Solicitante> solicitanteList) {
        this.solicitanteList = solicitanteList;
    }

    @XmlTransient
    public List<Auxiliar> getAuxiliarList() {
        return auxiliarList;
    }

    public void setAuxiliarList(List<Auxiliar> auxiliarList) {
        this.auxiliarList = auxiliarList;
    }

    public Facultad getIdFacultad() {
        return idFacultad;
    }

    public void setIdFacultad(Facultad idFacultad) {
        this.idFacultad = idFacultad;
    }

    public ConsejoCurricular getIdConsejoCurricular() {
        return idConsejoCurricular;
    }

    public void setIdConsejoCurricular(ConsejoCurricular idConsejoCurricular) {
        this.idConsejoCurricular = idConsejoCurricular;
    }

    @XmlTransient
    public List<Convocatoria> getConvocatoriaList() {
        return convocatoriaList;
    }

    public void setConvocatoriaList(List<Convocatoria> convocatoriaList) {
        this.convocatoriaList = convocatoriaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (programaAcademicoId != null ? programaAcademicoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProgramaAcademico)) {
            return false;
        }
        ProgramaAcademico other = (ProgramaAcademico) object;
        if ((this.programaAcademicoId == null && other.programaAcademicoId != null) || (this.programaAcademicoId != null && !this.programaAcademicoId.equals(other.programaAcademicoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ProgramaAcademico[ programaAcademicoId=" + programaAcademicoId + " ]";
    }
    
}
