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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DavidMontoya
 */
@Entity
@Table(name = "SOLICITUD_AUXILIARES", catalog = "", schema = "PROYECTO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SolicitudAuxiliares.findAll", query = "SELECT s FROM SolicitudAuxiliares s"),
    @NamedQuery(name = "SolicitudAuxiliares.findBySolicitudAuxiliaresId", query = "SELECT s FROM SolicitudAuxiliares s WHERE s.solicitudAuxiliaresId = :solicitudAuxiliaresId"),
    @NamedQuery(name = "SolicitudAuxiliares.findByCantidadAuxiliares", query = "SELECT s FROM SolicitudAuxiliares s WHERE s.cantidadAuxiliares = :cantidadAuxiliares"),
    @NamedQuery(name = "SolicitudAuxiliares.findByCantidadHoras", query = "SELECT s FROM SolicitudAuxiliares s WHERE s.cantidadHoras = :cantidadHoras"),
    @NamedQuery(name = "SolicitudAuxiliares.findByDescSolicitudAuxiliares", query = "SELECT s FROM SolicitudAuxiliares s WHERE s.descSolicitudAuxiliares = :descSolicitudAuxiliares"),
    @NamedQuery(name = "SolicitudAuxiliares.findByFechaCreacion", query = "SELECT s FROM SolicitudAuxiliares s WHERE s.fechaCreacion = :fechaCreacion")})
public class SolicitudAuxiliares implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SOLICITUD_AUXILIARES_ID", nullable = false, length = 20)
    private String solicitudAuxiliaresId;
    @Basic(optional = false)
    @Column(name = "CANTIDAD_AUXILIARES", nullable = false)
    private short cantidadAuxiliares;
    @Basic(optional = false)
    @Column(name = "CANTIDAD_HORAS", nullable = false)
    private short cantidadHoras;
    @Basic(optional = false)
    @Column(name = "DESC_SOLICITUD_AUXILIARES", nullable = false, length = 255)
    private String descSolicitudAuxiliares;
    @Basic(optional = false)
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @ManyToMany(mappedBy = "solicitudAuxiliaresList", fetch = FetchType.LAZY)
    private List<Requisito> requisitoList;
    @JoinColumn(name = "ID_SOLICITANTE", referencedColumnName = "SOLICITANTE_ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Solicitante idSolicitante;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idSolicitudAuxiliares", fetch = FetchType.LAZY)
    private Convocatoria convocatoria;

    public SolicitudAuxiliares() {
    }

    public SolicitudAuxiliares(String solicitudAuxiliaresId) {
        this.solicitudAuxiliaresId = solicitudAuxiliaresId;
    }

    public SolicitudAuxiliares(String solicitudAuxiliaresId, short cantidadAuxiliares, short cantidadHoras, String descSolicitudAuxiliares, Date fechaCreacion) {
        this.solicitudAuxiliaresId = solicitudAuxiliaresId;
        this.cantidadAuxiliares = cantidadAuxiliares;
        this.cantidadHoras = cantidadHoras;
        this.descSolicitudAuxiliares = descSolicitudAuxiliares;
        this.fechaCreacion = fechaCreacion;
    }

    public String getSolicitudAuxiliaresId() {
        return solicitudAuxiliaresId;
    }

    public void setSolicitudAuxiliaresId(String solicitudAuxiliaresId) {
        this.solicitudAuxiliaresId = solicitudAuxiliaresId;
    }

    public short getCantidadAuxiliares() {
        return cantidadAuxiliares;
    }

    public void setCantidadAuxiliares(short cantidadAuxiliares) {
        this.cantidadAuxiliares = cantidadAuxiliares;
    }

    public short getCantidadHoras() {
        return cantidadHoras;
    }

    public void setCantidadHoras(short cantidadHoras) {
        this.cantidadHoras = cantidadHoras;
    }

    public String getDescSolicitudAuxiliares() {
        return descSolicitudAuxiliares;
    }

    public void setDescSolicitudAuxiliares(String descSolicitudAuxiliares) {
        this.descSolicitudAuxiliares = descSolicitudAuxiliares;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @XmlTransient
    public List<Requisito> getRequisitoList() {
        return requisitoList;
    }

    public void setRequisitoList(List<Requisito> requisitoList) {
        this.requisitoList = requisitoList;
    }

    public Solicitante getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(Solicitante idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public Convocatoria getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(Convocatoria convocatoria) {
        this.convocatoria = convocatoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (solicitudAuxiliaresId != null ? solicitudAuxiliaresId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SolicitudAuxiliares)) {
            return false;
        }
        SolicitudAuxiliares other = (SolicitudAuxiliares) object;
        if ((this.solicitudAuxiliaresId == null && other.solicitudAuxiliaresId != null) || (this.solicitudAuxiliaresId != null && !this.solicitudAuxiliaresId.equals(other.solicitudAuxiliaresId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.SolicitudAuxiliares[ solicitudAuxiliaresId=" + solicitudAuxiliaresId + " ]";
    }
    
}
