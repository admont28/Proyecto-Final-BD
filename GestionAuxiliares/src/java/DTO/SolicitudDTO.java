/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DTO;

import java.io.Serializable;

/**
 *
 * @author DavidMontoya
 */
public class SolicitudDTO implements Serializable{

    private String idSolicitante;

    private String nombreSolicitante;

    private String apellidoSolicitante;

    private String cargoSolicitante;

    private String descripcionSolicitud;

    private short cantidadAuxiliaresSolicitud;

    private short cantidadHorasSolicitud;

    private String idSolicitud;

    private String requisitosSolicitud;

    /**
     * Metodo constructor de la clase SolicitudDTO
     * @param idSolicitante es el identificador del solicitante.
     * @param nombreSolicitante es el nombre del solicitante.
     * @param cantidadAuxiliaresSolicitud  es la cantidad de auxiliares que ha pedido el solicitante.
     */
    public SolicitudDTO(String idSolicitante, String nombreSolicitante, short cantidadAuxiliaresSolicitud) {
        this.idSolicitante = idSolicitante;
        this.nombreSolicitante = nombreSolicitante;
        this.cantidadAuxiliaresSolicitud = cantidadAuxiliaresSolicitud;
    }

    /**
     * Metodo constructor de la clase SolicitudDTO
     * @param nombreSolicitante es el nombre del solicitante
     * @param apellidoSolicitante es el apellido del solicitante
     * @param cargoSolicitante es el cargo del solicitante
     * @param descripcionSolicitud es la descripcion de para que va a ser la solicitud
     * @param cantidadAuxiliaresSolicitud es la cantidad de auxiliares de la solicitud
     * @param cantidadHorasSolicitud es la cantidad de horas de la solicitud
     * @param idSolicitud es el identificador de la solicitud
     * @param requisitosSolicitud  es el requisito que acompaña a la solicitud
     */
    public SolicitudDTO(String nombreSolicitante, String apellidoSolicitante, String cargoSolicitante, String descripcionSolicitud, short cantidadAuxiliaresSolicitud, short cantidadHorasSolicitud, String idSolicitud, String requisitosSolicitud) {
        this.nombreSolicitante = nombreSolicitante;
        this.apellidoSolicitante = apellidoSolicitante;
        this.cargoSolicitante = cargoSolicitante;
        this.descripcionSolicitud = descripcionSolicitud;
        this.cantidadAuxiliaresSolicitud = cantidadAuxiliaresSolicitud;
        this.cantidadHorasSolicitud = cantidadHorasSolicitud;
        this.idSolicitud = idSolicitud;
        this.requisitosSolicitud = requisitosSolicitud;
    }

    
    /**
     * Get the value of idSolicitante
     *
     * @return the value of idSolicitante
     */
    public String getIdSolicitante() {
        return idSolicitante;
    }

    /**
     * Set the value of idSolicitante
     *
     * @param idSolicitante new value of idSolicitante
     */
    public void setIdSolicitante(String idSolicitante) {
        this.idSolicitante = idSolicitante;
    }
    
    /**
     * Get the value of requisitosSolicitud
     *
     * @return the value of requisitosSolicitud
     */
    public String getRequisitosSolicitud() {
        return requisitosSolicitud;
    }

    /**
     * Set the value of requisitosSolicitud
     *
     * @param requisitosSolicitud new value of requisitosSolicitud
     */
    public void setRequisitosSolicitud(String requisitosSolicitud) {
        this.requisitosSolicitud = requisitosSolicitud;
    }

    /**
     * Get the value of idSolicitud
     *
     * @return the value of idSolicitud
     */
    public String getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * Set the value of idSolicitud
     *
     * @param idSolicitud new value of idSolicitud
     */
    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    /**
     * Get the value of cantidadHorasSolicitud
     *
     * @return the value of cantidadHorasSolicitud
     */
    public short getCantidadHorasSolicitud() {
        return cantidadHorasSolicitud;
    }

    /**
     * Set the value of cantidadHorasSolicitud
     *
     * @param cantidadHorasSolicitud new value of cantidadHorasSolicitud
     */
    public void setCantidadHorasSolicitud(short cantidadHorasSolicitud) {
        this.cantidadHorasSolicitud = cantidadHorasSolicitud;
    }

    /**
     * Get the value of cantidadAuxiliaresSolicitud
     *
     * @return the value of cantidadAuxiliaresSolicitud
     */
    public short getCantidadAuxiliaresSolicitud() {
        return cantidadAuxiliaresSolicitud;
    }

    /**
     * Set the value of cantidadAuxiliaresSolicitud
     *
     * @param cantidadAuxiliaresSolicitud new value of
     * cantidadAuxiliaresSolicitud
     */
    public void setCantidadAuxiliaresSolicitud(short cantidadAuxiliaresSolicitud) {
        this.cantidadAuxiliaresSolicitud = cantidadAuxiliaresSolicitud;
    }

    /**
     * Get the value of descripcionSolicitud
     *
     * @return the value of descripcionSolicitud
     */
    public String getDescripcionSolicitud() {
        return descripcionSolicitud;
    }

    /**
     * Set the value of descripcionSolicitud
     *
     * @param descripcionSolicitud new value of descripcionSolicitud
     */
    public void setDescripcionSolicitud(String descripcionSolicitud) {
        this.descripcionSolicitud = descripcionSolicitud;
    }

    /**
     * Get the value of cargoSolicitante
     *
     * @return the value of cargoSolicitante
     */
    public String getCargoSolicitante() {
        return cargoSolicitante;
    }

    /**
     * Set the value of cargoSolicitante
     *
     * @param cargoSolicitante new value of cargoSolicitante
     */
    public void setCargoSolicitante(String cargoSolicitante) {
        this.cargoSolicitante = cargoSolicitante;
    }

    /**
     * Get the value of apellidoSolicitante
     *
     * @return the value of apellidoSolicitante
     */
    public String getApellidoSolicitante() {
        return apellidoSolicitante;
    }

    /**
     * Set the value of apellidoSolicitante
     *
     * @param apellidoSolicitante new value of apellidoSolicitante
     */
    public void setApellidoSolicitante(String apellidoSolicitante) {
        this.apellidoSolicitante = apellidoSolicitante;
    }

    /**
     * Get the value of nombreSolicitante
     *
     * @return the value of nombreSolicitante
     */
    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    /**
     * Set the value of nombreSolicitante
     *
     * @param nombreSolicitante new value of nombreSolicitante
     */
    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

}
