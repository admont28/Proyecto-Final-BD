/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DavidMontoya
 */
@Entity
@Table(name = "REQUISITO", catalog = "", schema = "PROYECTO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Requisito.findAll", query = "SELECT r FROM Requisito r"),
    @NamedQuery(name = "Requisito.findByRequisitoId", query = "SELECT r FROM Requisito r WHERE r.requisitoId = :requisitoId"),
    @NamedQuery(name = "Requisito.findByDescripcionRequisito", query = "SELECT r FROM Requisito r WHERE r.descripcionRequisito = :descripcionRequisito")})
public class Requisito implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "REQUISITO_ID", nullable = false, length = 20)
    private String requisitoId;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION_REQUISITO", nullable = false, length = 255)
    private String descripcionRequisito;
    @JoinTable(name = "SOL_AUXILIARES_REQUISITOS", joinColumns = {
        @JoinColumn(name = "ID_REQUISITO", referencedColumnName = "REQUISITO_ID", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "ID_SOLICITUD_AUXILIARES", referencedColumnName = "SOLICITUD_AUXILIARES_ID", nullable = false)})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<SolicitudAuxiliares> solicitudAuxiliaresList;
    @ManyToMany(mappedBy = "requisitoList", fetch = FetchType.LAZY)
    private List<Convocatoria> convocatoriaList;
    @JoinColumn(name = "ID_TIPO_REQUISITO", referencedColumnName = "TIPO_REQUISITO_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoRequisito idTipoRequisito;

    public Requisito() {
    }

    public Requisito(String requisitoId) {
        this.requisitoId = requisitoId;
    }

    public Requisito(String requisitoId, String descripcionRequisito) {
        this.requisitoId = requisitoId;
        this.descripcionRequisito = descripcionRequisito;
    }

    public String getRequisitoId() {
        return requisitoId;
    }

    public void setRequisitoId(String requisitoId) {
        this.requisitoId = requisitoId;
    }

    public String getDescripcionRequisito() {
        return descripcionRequisito;
    }

    public void setDescripcionRequisito(String descripcionRequisito) {
        this.descripcionRequisito = descripcionRequisito;
    }

    @XmlTransient
    public List<SolicitudAuxiliares> getSolicitudAuxiliaresList() {
        return solicitudAuxiliaresList;
    }

    public void setSolicitudAuxiliaresList(List<SolicitudAuxiliares> solicitudAuxiliaresList) {
        this.solicitudAuxiliaresList = solicitudAuxiliaresList;
    }

    @XmlTransient
    public List<Convocatoria> getConvocatoriaList() {
        return convocatoriaList;
    }

    public void setConvocatoriaList(List<Convocatoria> convocatoriaList) {
        this.convocatoriaList = convocatoriaList;
    }

    public TipoRequisito getIdTipoRequisito() {
        return idTipoRequisito;
    }

    public void setIdTipoRequisito(TipoRequisito idTipoRequisito) {
        this.idTipoRequisito = idTipoRequisito;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (requisitoId != null ? requisitoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Requisito)) {
            return false;
        }
        Requisito other = (Requisito) object;
        if ((this.requisitoId == null && other.requisitoId != null) || (this.requisitoId != null && !this.requisitoId.equals(other.requisitoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Requisito[ requisitoId=" + requisitoId + " ]";
    }
    
}
