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
public class AuxiliaresSeleccionadosDTO implements Serializable{
    
        private String nombreAuxiliar;
        
        private String apellidoAuxiliar;
        
        private String idConvocatoria;
        
        private String idEvaluacionAuxiliares;
        
        private short estadoAuxiliar;

        /**
         * Método constructor de la clase AuxiliaresSeleccionadosDTO.
         * @param nombreAuxiliar es el nombre del auxiliar.
         * @param apellidoAuxiliar es el apellido del auxiliar.
         * @param idConvocatoria es la convocatoria para la cual se selecciona el auxiliar.
         * @param idEvaluacionAuxiliares es la evaluacion de la cual se selecciona el auxiliar.
         * @param estadoAuxiliar es el estado del auxiliar, 1 seleccionado, 0 no seleccionado.
         */
    public AuxiliaresSeleccionadosDTO(String nombreAuxiliar, String apellidoAuxiliar, String idConvocatoria, String idEvaluacionAuxiliares, short estadoAuxiliar) {
        this.nombreAuxiliar = nombreAuxiliar;
        this.apellidoAuxiliar = apellidoAuxiliar;
        this.idConvocatoria = idConvocatoria;
        this.idEvaluacionAuxiliares = idEvaluacionAuxiliares;
        this.estadoAuxiliar = estadoAuxiliar;
    }

    /**
     * Get the value of estadoAuxiliar
     *
     * @return the value of estadoAuxiliar
     */
    public short getEstadoAuxiliar() {
        return estadoAuxiliar;
    }

    /**
     * Set the value of estadoAuxiliar
     *
     * @param estadoAuxiliar new value of estadoAuxiliar
     */
    public void setEstadoAuxiliar(short estadoAuxiliar) {
        this.estadoAuxiliar = estadoAuxiliar;
    }


    /**
     * Get the value of idEvaluacionAuxiliares
     *
     * @return the value of idEvaluacionAuxiliares
     */
    public String getIdEvaluacionAuxiliares() {
        return idEvaluacionAuxiliares;
    }

    /**
     * Set the value of idEvaluacionAuxiliares
     *
     * @param idEvaluacionAuxiliares new value of idEvaluacionAuxiliares
     */
    public void setIdEvaluacionAuxiliares(String idEvaluacionAuxiliares) {
        this.idEvaluacionAuxiliares = idEvaluacionAuxiliares;
    }


    /**
     * Get the value of idConvocatoria
     *
     * @return the value of idConvocatoria
     */
    public String getIdConvocatoria() {
        return idConvocatoria;
    }

    /**
     * Set the value of idConvocatoria
     *
     * @param idConvocatoria new value of idConvocatoria
     */
    public void setIdConvocatoria(String idConvocatoria) {
        this.idConvocatoria = idConvocatoria;
    }

    

    /**
     * Get the value of apellidoAuxiliar
     *
     * @return the value of apellidoAuxiliar
     */
    public String getApellidoAuxiliar() {
        return apellidoAuxiliar;
    }

    /**
     * Set the value of apellidoAuxiliar
     *
     * @param apellidoAuxiliar new value of apellidoAuxiliar
     */
    public void setApellidoAuxiliar(String apellidoAuxiliar) {
        this.apellidoAuxiliar = apellidoAuxiliar;
    }

    /**
     * Get the value of nombreAuxiliar
     *
     * @return the value of nombreAuxiliar
     */
    public String getNombreAuxiliar() {
        return nombreAuxiliar;
    }

    /**
     * Set the value of nombreAuxiliar
     *
     * @param nombreAuxiliar new value of nombreAuxiliar
     */
    public void setNombreAuxiliar(String nombreAuxiliar) {
        this.nombreAuxiliar = nombreAuxiliar;
    }
}
