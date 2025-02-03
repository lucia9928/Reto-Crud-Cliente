/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author melany
 */
@XmlRootElement

public class Cliente extends Usuario implements Serializable {

  private static final long serialVersionUID = 1L;
  private Date fechaRegistro;
  private List<RecetaMedica>recetas;

    public Cliente() {
        super();
    }
    public List<RecetaMedica> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<RecetaMedica> recetas) {
        this.recetas = recetas;
    }

    public Cliente(Date fechaRegistro) {
         super();
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.getDni()!= null ? super.getDni().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
       return super.equals(object);
       
    }

    @Override
    public String toString() {
        return "eus.tartangalh.crud.create.Cliente[ id=" + super.getDni()  + " ]";
    }
    
}
