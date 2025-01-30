/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author melany
 */
@XmlRootElement

public class Cliente extends Usuario implements Serializable {

  private static final long serialVersionUID = 1L;
  private LocalDate fechaRegistro;
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

    public Cliente(LocalDate fechaRegistro) {
         super();
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
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

    public void getNombre(String newValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
