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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DavidMontoya
 */
@Entity
@Table(name = "DIRECTORES", catalog = "", schema = "PROYECTO_FINAL", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ID_SOLICITANTE"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Directores.findAll", query = "SELECT d FROM Directores d"),
    @NamedQuery(name = "Directores.findByIdPrograma", query = "SELECT d FROM Directores d WHERE d.directoresPK.idPrograma = :idPrograma"),
    @NamedQuery(name = "Directores.findByIdJornada", query = "SELECT d FROM Directores d WHERE d.directoresPK.idJornada = :idJornada")})
public class Directores implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DirectoresPK directoresPK;
    @JoinColumn(name = "ID_SOLICITANTE", referencedColumnName = "SOLICITANTE_ID", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Solicitante idSolicitante;
    @JoinColumn(name = "ID_PROGRAMA", referencedColumnName = "PROGRAMA_ACADEMICO_ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ProgramaAcademico programaAcademico;
    @JoinColumn(name = "ID_JORNADA", referencedColumnName = "JORNADA_ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Jornada jornada;

    public Directores() {
    }

    public Directores(DirectoresPK directoresPK) {
        this.directoresPK = directoresPK;
    }

    public Directores(String idPrograma, String idJornada) {
        this.directoresPK = new DirectoresPK(idPrograma, idJornada);
    }

    public DirectoresPK getDirectoresPK() {
        return directoresPK;
    }

    public void setDirectoresPK(DirectoresPK directoresPK) {
        this.directoresPK = directoresPK;
    }

    public Solicitante getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(Solicitante idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public ProgramaAcademico getProgramaAcademico() {
        return programaAcademico;
    }

    public void setProgramaAcademico(ProgramaAcademico programaAcademico) {
        this.programaAcademico = programaAcademico;
    }

    public Jornada getJornada() {
        return jornada;
    }

    public void setJornada(Jornada jornada) {
        this.jornada = jornada;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (directoresPK != null ? directoresPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Directores)) {
            return false;
        }
        Directores other = (Directores) object;
        if ((this.directoresPK == null && other.directoresPK != null) || (this.directoresPK != null && !this.directoresPK.equals(other.directoresPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Directores[ directoresPK=" + directoresPK + " ]";
    }
    
}
