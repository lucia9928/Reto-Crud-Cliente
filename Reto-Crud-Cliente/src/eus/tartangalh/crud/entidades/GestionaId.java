/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.entidades;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 2dam
 */
@XmlRootElement
public class GestionaId implements Serializable{
    
    private String dni;
    private Integer idProducto;

    public GestionaId(String dni, Integer idProducto) {
        this.dni = dni;
        this.idProducto = idProducto;
     
    }

    public GestionaId() {
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    
}
