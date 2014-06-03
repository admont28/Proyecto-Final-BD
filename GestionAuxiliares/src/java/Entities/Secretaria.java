/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DavidMontoya
 */
@Entity
@Table(name = "SECRETARIA", catalog = "", schema = "PROYECTO_FINAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Secretaria.findAll", query = "SELECT s FROM Secretaria s"),
    @NamedQuery(name = "Secretaria.findBySecretariaId", query = "SELECT s FROM Secretaria s WHERE s.secretariaId = :secretariaId"),
    @NamedQuery(name = "Secretaria.findByNombreSecretaria", query = "SELECT s FROM Secretaria s WHERE s.nombreSecretaria = :nombreSecretaria"),
    @NamedQuery(name = "Secretaria.findByApellidoSecretaria", query = "SELECT s FROM Secretaria s WHERE s.apellidoSecretaria = :apellidoSecretaria"),
    @NamedQuery(name = "Secretaria.findByTelefonoSecretaria", query = "SELECT s FROM Secretaria s WHERE s.telefonoSecretaria = :telefonoSecretaria"),
    @NamedQuery(name = "Secretaria.findByPasswordSecretaria", query = "SELECT s FROM Secretaria s WHERE s.passwordSecretaria = :passwordSecretaria")})
public class Secretaria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SECRETARIA_ID", nullable = false, length = 20)
    private String secretariaId;
    @Basic(optional = false)
    @Column(name = "NOMBRE_SECRETARIA", nullable = false, length = 35)
    private String nombreSecretaria;
    @Basic(optional = false)
    @Column(name = "APELLIDO_SECRETARIA", nullable = false, length = 35)
    private String apellidoSecretaria;
    @Basic(optional = false)
    @Column(name = "TELEFONO_SECRETARIA", nullable = false, length = 20)
    private String telefonoSecretaria;
    @Basic(optional = false)
    @Column(name = "PASSWORD_SECRETARIA", nullable = false, length = 255)
    private String passwordSecretaria;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idSecretaria", fetch = FetchType.LAZY)
    private ConsejoCurricular consejoCurricular;

    public Secretaria() {
    }

    public Secretaria(String secretariaId) {
        this.secretariaId = secretariaId;
    }

    public Secretaria(String secretariaId, String nombreSecretaria, String apellidoSecretaria, String telefonoSecretaria, String passwordSecretaria) {
        this.secretariaId = secretariaId;
        this.nombreSecretaria = nombreSecretaria;
        this.apellidoSecretaria = apellidoSecretaria;
        this.telefonoSecretaria = telefonoSecretaria;
        this.passwordSecretaria = passwordSecretaria;
    }

    public String getSecretariaId() {
        return secretariaId;
    }

    public void setSecretariaId(String secretariaId) {
        this.secretariaId = secretariaId;
    }

    public String getNombreSecretaria() {
        return nombreSecretaria;
    }

    public void setNombreSecretaria(String nombreSecretaria) {
        this.nombreSecretaria = nombreSecretaria;
    }

    public String getApellidoSecretaria() {
        return apellidoSecretaria;
    }

    public void setApellidoSecretaria(String apellidoSecretaria) {
        this.apellidoSecretaria = apellidoSecretaria;
    }

    public String getTelefonoSecretaria() {
        return telefonoSecretaria;
    }

    public void setTelefonoSecretaria(String telefonoSecretaria) {
        this.telefonoSecretaria = telefonoSecretaria;
    }

    public String getPasswordSecretaria() {
        return passwordSecretaria;
    }

    public void setPasswordSecretaria(String passwordSecretaria) {
        this.passwordSecretaria = passwordSecretaria;
    }

    public ConsejoCurricular getConsejoCurricular() {
        return consejoCurricular;
    }

    public void setConsejoCurricular(ConsejoCurricular consejoCurricular) {
        this.consejoCurricular = consejoCurricular;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (secretariaId != null ? secretariaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Secretaria)) {
            return false;
        }
        Secretaria other = (Secretaria) object;
        if ((this.secretariaId == null && other.secretariaId != null) || (this.secretariaId != null && !this.secretariaId.equals(other.secretariaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Secretaria[ secretariaId=" + secretariaId + " ]";
    }
    
}
