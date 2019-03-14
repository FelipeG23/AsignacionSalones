/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author Felipe
 */
public class SalonEntity {

    private String codigo;
    private String nombre;
    private Long codigoEdificio;
    private Long capacidad;
    private EdificioEntity edificio;

    public EdificioEntity getEdificio() {
        return edificio;
    }

    public void setEdificio(EdificioEntity edificio) {
        this.edificio = edificio;
    }
    
    

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getCodigoEdificio() {
        return codigoEdificio;
    }

    public void setCodigoEdificio(Long codigoEdificio) {
        this.codigoEdificio = codigoEdificio;
    }

    public Long getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Long capacidad) {
        this.capacidad = capacidad;
    }

}
