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
@XmlRootElement(name="receta_medica")
public class RecetaMedica implements Serializable {
   
    private Integer idReceta;
    private Cliente cliente;
    private LocalDate fechaReceta;
    private String descripcion;
    private Integer cantidad;
    private List<ProductoFarmaceutico>productos;

    
    public RecetaMedica() {
        
    }

    public RecetaMedica(Integer idReceta, LocalDate fechaReceta, String descripcion, Integer cantidad) {
        this.idReceta = idReceta;
        this.fechaReceta = fechaReceta;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }


   private static final long serialVersionUID = 1L;
  public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
  public List<ProductoFarmaceutico> getProductos() {
        return productos;
    }

    public void setListaProductos(List<ProductoFarmaceutico> productos) {
        this.productos = productos;
    }
    public LocalDate getFechaReceta() {
        return fechaReceta;
    }

    public void setFechaReceta(LocalDate fechaReceta) {
        this.fechaReceta = fechaReceta;
    }
    

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    public Integer getIdReceta() {
        return idReceta;
    }

    public void setId(Integer idReceta) {
        this.idReceta = idReceta;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReceta != null ? idReceta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecetaMedica)) {
            return false;
        }
        RecetaMedica other = (RecetaMedica) object;
        if ((this.idReceta == null && other.idReceta != null) || (this.idReceta != null && !this.idReceta.equals(other.idReceta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eus.tartangalh.crud.create.RecetaMedica[ id=" + idReceta + " ]";
    }
    
}
