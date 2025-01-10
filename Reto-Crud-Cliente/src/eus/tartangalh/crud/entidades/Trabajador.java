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
@XmlRootElement(name="trabajador")
public class Trabajador extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    private LocalDate fechaContratacion;
    private TipoCargo tipoCargo;
    private List<Gestiona> gestionaProducto;

  

    public Trabajador(LocalDate fechaContratacion, TipoCargo tipoCargo) {
        this.fechaContratacion = fechaContratacion;
        this.tipoCargo = tipoCargo;
    }

    public Trabajador() {
        super();
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public TipoCargo getTipoCargo() {
        return tipoCargo;
    }

    public void setTipoCargo(TipoCargo tipoCargo) {
        this.tipoCargo = tipoCargo;
    }
  public List<Gestiona> getGestionaProducto() {
        return gestionaProducto;
    }

    public void setGestionaProducto(List<Gestiona> gestionaProducto) {
        this.gestionaProducto = gestionaProducto;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.getDni() != null ? super.getDni().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        super.equals(object);
        return true;
    }

    @Override
    public String toString() {
        return "eus.tartangalh.crud.create.Trabajador[ id=" + super.getDni() + " ]";
    }

}
