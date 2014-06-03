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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DavidMontoya
 */
@Entity
@Table(name = "CONSEJO_CURRICULAR", catalog = "", schema = "PROYECTO_FINAL", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ID_SECRETARIA"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConsejoCurricular.findAll", query = "SELECT c FROM ConsejoCurricular c"),
    @NamedQuery(name = "ConsejoCurricular.findByConsejoCurricularId", query = "SELECT c FROM ConsejoCurricular c WHERE c.consejoCurricularId = :consejoCurricularId")})
public class ConsejoCurricular implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CONSEJO_CURRICULAR_ID", nullable = false, length = 20)
    private String consejoCurricularId;
    @JoinColumn(name = "ID_SECRETARIA", referencedColumnName = "SECRETARIA_ID", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Secretaria idSecretaria;
    @OneToMany(mappedBy = "idConsejoCurricular", fetch = FetchType.LAZY)
    private List<Solicitante> solicitanteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idConsejoCurricular", fetch = FetchType.LAZY)
    private List<ProgramaAcademico> programaAcademicoList;

    public ConsejoCurricular() {
    }

    public ConsejoCurricular(String consejoCurricularId) {
        this.consejoCurricularId = consejoCurricularId;
    }

    public String getConsejoCurricularId() {
        return consejoCurricularId;
    }

    public void setConsejoCurricularId(String consejoCurricularId) {
        this.consejoCurricularId = consejoCurricularId;
    }

    public Secretaria getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(Secretaria idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    @XmlTransient
    public List<Solicitante> getSolicitanteList() {
        return solicitanteList;
    }

    public void setSolicitanteList(List<Solicitante> solicitanteList) {
        this.solicitanteList = solicitanteList;
    }

    @XmlTransient
    public List<ProgramaAcademico> getProgramaAcademicoList() {
        return programaAcademicoList;
    }

    public void setProgramaAcademicoList(List<ProgramaAcademico> programaAcademicoList) {
        this.programaAcademicoList = programaAcademicoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consejoCurricularId != null ? consejoCurricularId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConsejoCurricular)) {
            return false;
        }
        ConsejoCurricular other = (ConsejoCurricular) object;
        if ((this.consejoCurricularId == null && other.consejoCurricularId != null) || (this.consejoCurricularId != null && !this.consejoCurricularId.equals(other.consejoCurricularId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ConsejoCurricular[ consejoCurricularId=" + consejoCurricularId + " ]";
    }
    
}
