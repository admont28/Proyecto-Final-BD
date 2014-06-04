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
public class EvaluacionesDTO implements Serializable{
    
        private String idEvaluacion;
        
        private double promedio;
        
        private String nombreAuxiliar;
    
        private String apellidoAuxiliar;
        
        private double calificacionAuxiliar;
        
     /**
     * Metodo constructor de la clase EvaluacionesDTO.
     * @param idEvaluacion es el id de la evaluacion.
     * @param promedio  es el promedio de las calificaciones de la evaluacion.
     */    
    public EvaluacionesDTO(String idEvaluacion, double promedio) {
        this.idEvaluacion = idEvaluacion;
        this.promedio = promedio;
    }

    /**
     * Metodo constructor de la clase EvaluacionesDTO
     * @param idEvaluacion es el id de la evaluacion.
     * @param promedio es el promedio del auxiliar.
     * @param nombreAuxiliar es el nombre del auxiliar.
     * @param apellidoAuxiliar es el apellido del auxiliar.
     * @param calificacionAuxiliar es la calificacion del auxiliar en la prueba.
     */
    public EvaluacionesDTO(String idEvaluacion, double promedio, String nombreAuxiliar, String apellidoAuxiliar, double calificacionAuxiliar) {
        this.idEvaluacion = idEvaluacion;
        this.promedio = promedio;
        this.nombreAuxiliar = nombreAuxiliar;
        this.apellidoAuxiliar = apellidoAuxiliar;
        this.calificacionAuxiliar = calificacionAuxiliar;
    }
        
        

    /**
     * Get the value of calificacionAuxiliar
     *
     * @return the value of calificacionAuxiliar
     */
    public double getCalificacionAuxiliar() {
        return calificacionAuxiliar;
    }

    /**
     * Set the value of calificacionAuxiliar
     *
     * @param calificacionAuxiliar new value of calificacionAuxiliar
     */
    public void setCalificacionAuxiliar(double calificacionAuxiliar) {
        this.calificacionAuxiliar = calificacionAuxiliar;
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

    /**
     * Get the value of promedio
     *
     * @return the value of promedio
     */
    public double getPromedio() {
        return promedio;
    }

    /**
     * Set the value of promedio
     *
     * @param promedio new value of promedio
     */
    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }


    /**
     * Get the value of idEvaluacion
     *
     * @return the value of idEvaluacion
     */
    public String getIdEvaluacion() {
        return idEvaluacion;
    }

    /**
     * Set the value of idEvaluacion
     *
     * @param idEvaluacion new value of idEvaluacion
     */
    public void setIdEvaluacion(String idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

}
