/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import eus.tartangalh.crud.entidades.ProductoFarmaceutico;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 2dam
 */

@XmlRootElement
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;
 
    private Integer idProveedor;
    private String cif;
    private String nombreProveedor;
    private String calle;
    private Integer codPostal;
    private String ciudad;
    private LocalDate fechaContratacion;

 
    private List<ProductoFarmaceutico> productoFarmaceutico;

    public Proveedor(Integer idProveedor, String cif, String nombreProveedor, String calle, Integer codPostal, String ciudad, LocalDate fechaContratacion, List<ProductoFarmaceutico> productoFarmaceutico) {
        this.idProveedor = idProveedor;
        this.cif = cif;
        this.nombreProveedor = nombreProveedor;
        this.calle = calle;
        this.codPostal = codPostal;
        this.ciudad = ciudad;
        this.fechaContratacion = fechaContratacion;
        this.productoFarmaceutico = productoFarmaceutico;
    }

    public Proveedor() {
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(Integer codPostal) {
        this.codPostal = codPostal;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    @XmlTransient
    public List<ProductoFarmaceutico> getProductoFarmaceutico() {
        return productoFarmaceutico;
    }

    public void setProductoFarmaceutico(List<ProductoFarmaceutico> productoFarmaceutico) {
        this.productoFarmaceutico = productoFarmaceutico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProveedor != null ? idProveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedor)) {
            return false;
        }
        Proveedor other = (Proveedor) object;
        if ((this.idProveedor == null && other.idProveedor != null) || (this.idProveedor != null && !this.idProveedor.equals(other.idProveedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eus.tartangalh.crud.create.Proveedor[ id=" + idProveedor + " ]";
    }

}
