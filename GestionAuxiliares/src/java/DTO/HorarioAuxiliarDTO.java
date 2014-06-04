/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DTO;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author DavidMontoya
 */
public class HorarioAuxiliarDTO implements Serializable{

    private String nombreAuxiliar;

    private String apellidoAuxiliar;

    private String tipoAuxiliar;

    private String idHorarioActividades;

    private Date fechaHorario;

    private String horaInicio;

    private String horaFinal;

    /**
     * Metodo constructor de la clase HorarioAuxiliarDTO.
     * @param nombreAuxiliar es el nombre del auxiliar.
     * @param apellidoAuxiliar es el apellido del auxiliar.
     * @param tipoAuxiliar es el tipo de auxiliar.
     * @param idHorarioActividades es el horario de actividades del auxiliar.
     * @param fechaHorario es la fecha del horario de actividades.
     * @param horaInicio es la hora en que inicia labores el auxiliar.
     * @param horaFinal es la hora en que finaliza labores el auxiliar.
     */
    public HorarioAuxiliarDTO(String nombreAuxiliar, String apellidoAuxiliar, String tipoAuxiliar, String idHorarioActividades, Date fechaHorario, String horaInicio, String horaFinal) {
        this.nombreAuxiliar = nombreAuxiliar;
        this.apellidoAuxiliar = apellidoAuxiliar;
        this.tipoAuxiliar = tipoAuxiliar;
        this.idHorarioActividades = idHorarioActividades;
        this.fechaHorario = fechaHorario;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
    }

    /**
     * Get the value of horaFinal
     *
     * @return the value of horaFinal
     */
    public String getHoraFinal() {
        return horaFinal;
    }

    /**
     * Set the value of horaFinal
     *
     * @param horaFinal new value of horaFinal
     */
    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    /**
     * Get the value of horaInicio
     *
     * @return the value of horaInicio
     */
    public String getHoraInicio() {
        return horaInicio;
    }

    /**
     * Set the value of horaInicio
     *
     * @param horaInicio new value of horaInicio
     */
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * Get the value of fechaHorario
     *
     * @return the value of fechaHorario
     */
    public Date getFechaHorario() {
        return fechaHorario;
    }

    /**
     * Set the value of fechaHorario
     *
     * @param fechaHorario new value of fechaHorario
     */
    public void setFechaHorario(Date fechaHorario) {
        this.fechaHorario = fechaHorario;
    }

    /**
     * Get the value of idHorarioActividades
     *
     * @return the value of idHorarioActividades
     */
    public String getIdHorarioActividades() {
        return idHorarioActividades;
    }

    /**
     * Set the value of idHorarioActividades
     *
     * @param idHorarioActividades new value of idHorarioActividades
     */
    public void setIdHorarioActividades(String idHorarioActividades) {
        this.idHorarioActividades = idHorarioActividades;
    }

    /**
     * Get the value of tipoAuxiliar
     *
     * @return the value of tipoAuxiliar
     */
    public String getTipoAuxiliar() {
        return tipoAuxiliar;
    }

    /**
     * Set the value of tipoAuxiliar
     *
     * @param tipoAuxiliar new value of tipoAuxiliar
     */
    public void setTipoAuxiliar(String tipoAuxiliar) {
        this.tipoAuxiliar = tipoAuxiliar;
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
