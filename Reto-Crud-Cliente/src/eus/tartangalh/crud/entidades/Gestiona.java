/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import eus.tartangalh.crud.entidades.GestionaId;
import eus.tartangalh.crud.entidades.ProductoFarmaceutico;
import eus.tartangalh.crud.entidades.Trabajador;
import java.io.Serializable;
import java.time.LocalDate;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 2dam
 */
@XmlRootElement
public class Gestiona implements Serializable {

    private static final long serialVersionUID = 1L;

    private GestionaId gestionaId;

    private Trabajador trabajador;

    private ProductoFarmaceutico productoFarmaceutico;
    private LocalDate fechaCompra;

    private Integer cantidad;

    public Gestiona(GestionaId gestionaId, Trabajador trabajador, ProductoFarmaceutico productoFarmaceutico, Integer cantidad) {
        this.gestionaId = gestionaId;
        this.trabajador = trabajador;
        this.productoFarmaceutico = productoFarmaceutico;
        this.cantidad = cantidad;
    }

    public Gestiona() {
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public GestionaId getGestionaId() {
        return gestionaId;
    }

    public void setGestionaId(GestionaId gestionaId) {
        this.gestionaId = gestionaId;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public ProductoFarmaceutico getProductoFarmaceutico() {
        return productoFarmaceutico;
    }

    public void setProductoFarmaceutico(ProductoFarmaceutico productoFarmaceutico) {
        this.productoFarmaceutico = productoFarmaceutico;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gestionaId != null ? gestionaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gestiona)) {
            return false;
        }
        Gestiona other = (Gestiona) object;
        if ((this.gestionaId == null && other.gestionaId != null) || (this.gestionaId != null && !this.gestionaId.equals(other.gestionaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Gestiona[ id=" + gestionaId + " ]";
    }

}
