/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DavidMontoya
 */
@Entity
@Table(name = "INSCRIPCION_CONVOCATORIA", catalog = "", schema = "PROYECTO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InscripcionConvocatoria.findAll", query = "SELECT i FROM InscripcionConvocatoria i"),
    @NamedQuery(name = "InscripcionConvocatoria.findByFechaInscripcionConvocatoria", query = "SELECT i FROM InscripcionConvocatoria i WHERE i.fechaInscripcionConvocatoria = :fechaInscripcionConvocatoria"),
    @NamedQuery(name = "InscripcionConvocatoria.findByIdAuxiliar", query = "SELECT i FROM InscripcionConvocatoria i WHERE i.inscripcionConvocatoriaPK.idAuxiliar = :idAuxiliar"),
    @NamedQuery(name = "InscripcionConvocatoria.findByIdConvocatoria", query = "SELECT i FROM InscripcionConvocatoria i WHERE i.inscripcionConvocatoriaPK.idConvocatoria = :idConvocatoria")})
public class InscripcionConvocatoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InscripcionConvocatoriaPK inscripcionConvocatoriaPK;
    @Basic(optional = false)
    @Column(name = "FECHA_INSCRIPCION_CONVOCATORIA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInscripcionConvocatoria;
    @JoinColumn(name = "ID_CONVOCATORIA", referencedColumnName = "CONVOCATORIA_ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Convocatoria convocatoria;
    @JoinColumn(name = "ID_AUXILIAR", referencedColumnName = "AUXILIAR_ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Auxiliar auxiliar;

    public InscripcionConvocatoria() {
    }

    public InscripcionConvocatoria(InscripcionConvocatoriaPK inscripcionConvocatoriaPK) {
        this.inscripcionConvocatoriaPK = inscripcionConvocatoriaPK;
    }

    public InscripcionConvocatoria(InscripcionConvocatoriaPK inscripcionConvocatoriaPK, Date fechaInscripcionConvocatoria) {
        this.inscripcionConvocatoriaPK = inscripcionConvocatoriaPK;
        this.fechaInscripcionConvocatoria = fechaInscripcionConvocatoria;
    }

    public InscripcionConvocatoria(String idAuxiliar, String idConvocatoria) {
        this.inscripcionConvocatoriaPK = new InscripcionConvocatoriaPK(idAuxiliar, idConvocatoria);
    }

    public InscripcionConvocatoriaPK getInscripcionConvocatoriaPK() {
        return inscripcionConvocatoriaPK;
    }

    public void setInscripcionConvocatoriaPK(InscripcionConvocatoriaPK inscripcionConvocatoriaPK) {
        this.inscripcionConvocatoriaPK = inscripcionConvocatoriaPK;
    }

    public Date getFechaInscripcionConvocatoria() {
        return fechaInscripcionConvocatoria;
    }

    public void setFechaInscripcionConvocatoria(Date fechaInscripcionConvocatoria) {
        this.fechaInscripcionConvocatoria = fechaInscripcionConvocatoria;
    }

    public Convocatoria getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(Convocatoria convocatoria) {
        this.convocatoria = convocatoria;
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
        hash += (inscripcionConvocatoriaPK != null ? inscripcionConvocatoriaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InscripcionConvocatoria)) {
            return false;
        }
        InscripcionConvocatoria other = (InscripcionConvocatoria) object;
        if ((this.inscripcionConvocatoriaPK == null && other.inscripcionConvocatoriaPK != null) || (this.inscripcionConvocatoriaPK != null && !this.inscripcionConvocatoriaPK.equals(other.inscripcionConvocatoriaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.InscripcionConvocatoria[ inscripcionConvocatoriaPK=" + inscripcionConvocatoriaPK + " ]";
    }
    
}
