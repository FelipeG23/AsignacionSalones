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
public class EmpresaEntity {
    
    public Long id_Empresa;
    public String nombre_Empresa;

    public Long getId_Empresa() {
        return id_Empresa;
    }

    public void setId_Empresa(Long id_Empresa) {
        this.id_Empresa = id_Empresa;
    }

    public String getNombre_Empresa() {
        return nombre_Empresa;
    }

    public void setNombre_Empresa(String nombre_Empresa) {
        this.nombre_Empresa = nombre_Empresa;
    }
    
    
}
