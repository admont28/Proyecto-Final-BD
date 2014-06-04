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
public class CumplimientoActividadesDTO implements Serializable{

    private String nombreAuxiliar;

    private String apellidoAuxiliar;

    private String descripcionCumplimientoActividades;

    /**
     * Metodo constructor de la clase CumplimientoActividadesDTO
     * @param nombreAuxiliar es el nombre del auxiliar.
     * @param apellidoAuxiliar es el apellido del auxiliar.
     * @param descripcionCumplimientoActividades  es la descripcion del documento de cumplimiento de actividades.
     */
    public CumplimientoActividadesDTO(String nombreAuxiliar, String apellidoAuxiliar, String descripcionCumplimientoActividades) {
        this.nombreAuxiliar = nombreAuxiliar;
        this.apellidoAuxiliar = apellidoAuxiliar;
        this.descripcionCumplimientoActividades = descripcionCumplimientoActividades;
    }

    /**
     * Get the value of descripcionCumplimientoActividades
     *
     * @return the value of descripcionCumplimientoActividades
     */
    public String getDescripcionCumplimientoActividades() {
        return descripcionCumplimientoActividades;
    }

    /**
     * Set the value of descripcionCumplimientoActividades
     *
     * @param descripcionCumplimientoActividades new value of
     * descripcionCumplimientoActividades
     */
    public void setDescripcionCumplimientoActividades(String descripcionCumplimientoActividades) {
        this.descripcionCumplimientoActividades = descripcionCumplimientoActividades;
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
