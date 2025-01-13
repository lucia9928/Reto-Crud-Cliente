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
 * Entidad JPA que representa un producto farmacéutico. Define las propiedades,
 * relaciones y consultas asociadas a los productos farmacéuticos.
 *
 * @author Oscar
 */
@XmlRootElement(name = "producto_farmaceutico")
public class ProductoFarmaceutico implements Serializable {

    private static final long serialVersionUID = 1L;
    /*
     * Identificador único del producto.
     */
    private Integer idProducto;
    /*
     * Nombre del producto farmacéutico.
     */
    private String nombreProducto;
    /*
     * Lote al que pertenece el producto.
     */
    private String loteProducto;
    /*
     * Fecha de caducidad del producto.
     */
    private LocalDate fechaCaducidad;
    /*
     * Descripción del producto.
     */
    private String Description;
    /*
     * Categoría del producto farmacéutico (enum).
     */
    private CategoriaProducto categoria;
    /*
     * Precio del producto.
     */
    private Float precio;
    /*
     * Constructor vacío.
     */
    private Almacen almacen;
    private List<Gestiona> gestiona;
    private List<RecetaMedica> receta;
    private Proveedor proveedor;

    public ProductoFarmaceutico() {
    }

    /*
     * Constructor con parámetros para inicializar las propiedades del producto.
     */
    public ProductoFarmaceutico(Integer idProducto, String nombreProducto, String loteProducto, LocalDate fechaCaducidad, String Description, CategoriaProducto categoria, Float precio) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.loteProducto = loteProducto;
        this.fechaCaducidad = fechaCaducidad;
        this.Description = Description;
        this.categoria = categoria;
        this.precio = precio;
    }

    /*
     * Métodos getter y setter para las propiedades.
     */
    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getLoteProducto() {
        return loteProducto;
    }

    public void setLoteProducto(String loteProducto) {
        this.loteProducto = loteProducto;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public CategoriaProducto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaProducto categoria) {
        this.categoria = categoria;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    /*
     * Métodos de utilidad para comparar objetos y convertirlos a String.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProductoFarmaceutico)) {
            return false;
        }
        ProductoFarmaceutico other = (ProductoFarmaceutico) object;
        return (this.idProducto != null || other.idProducto == null) && (this.idProducto == null || this.idProducto.equals(other.idProducto));
    }

    @Override
    public String toString() {
        return idProducto + nombreProducto + categoria;
    }
}
