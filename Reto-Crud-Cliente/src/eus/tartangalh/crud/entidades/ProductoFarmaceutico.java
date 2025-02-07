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
 * Entidad que representa un producto farmacéutico en el sistema.
 * <p>
 * Define las propiedades, relaciones y métodos asociados a los productos farmacéuticos.
 * Esta clase es serializable y compatible con JPA para su persistencia en bases de datos.
 * </p>
 *
 * <p>Está anotada con {@code @XmlRootElement} para permitir su serialización y 
 * deserialización en formato XML.</p>
 *
 * @author Oscar
 */
@XmlRootElement
public class ProductoFarmaceutico implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Identificador único del producto. */
    private Integer idProducto;

    /** Nombre del producto farmacéutico. */
    private String nombreProducto;

    /** Código de lote al que pertenece el producto. */
    private String loteProducto;

    /** Fecha de caducidad del producto farmacéutico. */
    private Date fechaCaducidad;

    /** Descripción detallada del producto. */
    private String descripcion;

    /** Categoría del producto farmacéutico. */
    private CategoriaProducto categoria;

    /** Precio unitario del producto. */
    private Float precio;

    /** Almacén donde se encuentra el producto. */
    private Almacen almacen;

    /** Lista de registros de gestión asociados al producto. */
    private List<Gestiona> gestiona;

    /** Lista de recetas médicas en las que se incluye este producto. */
    private List<RecetaMedica> receta;

    /** Proveedor que suministra el producto farmacéutico. */
    private Proveedor proveedor;

    /**
     * Constructor vacío para la creación de un objeto {@code ProductoFarmaceutico} sin valores iniciales.
     */
    public ProductoFarmaceutico() {
    }

    /**
     * Constructor con parámetros para inicializar las propiedades del producto.
     *
     * @param idProducto Identificador único del producto.
     * @param nombreProducto Nombre del producto farmacéutico.
     * @param loteProducto Código de lote del producto.
     * @param fechaCaducidad Fecha de caducidad del producto.
     * @param descripcion Descripción detallada del producto.
     * @param categoria Categoría del producto farmacéutico.
     * @param precio Precio unitario del producto.
     */
    public ProductoFarmaceutico(Integer idProducto, String nombreProducto, String loteProducto, Date fechaCaducidad, String descripcion, CategoriaProducto categoria, Float precio) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.loteProducto = loteProducto;
        this.fechaCaducidad = fechaCaducidad;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
    }

    /**
     * Obtiene el identificador del producto.
     *
     * @return El identificador del producto.
     */
    public Integer getIdProducto() {
        return idProducto;
    }

    /**
     * Establece el identificador del producto.
     *
     * @param idProducto Nuevo identificador del producto.
     */
    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Obtiene el nombre del producto farmacéutico.
     *
     * @return El nombre del producto.
     */
    public String getNombreProducto() {
        return nombreProducto;
    }

    /**
     * Establece el nombre del producto farmacéutico.
     *
     * @param nombreProducto Nuevo nombre del producto.
     */
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    /**
     * Obtiene el código de lote del producto.
     *
     * @return El código de lote del producto.
     */
    public String getLoteProducto() {
        return loteProducto;
    }

    /**
     * Establece el código de lote del producto.
     *
     * @param loteProducto Nuevo código de lote del producto.
     */
    public void setLoteProducto(String loteProducto) {
        this.loteProducto = loteProducto;
    }

    /**
     * Obtiene la fecha de caducidad del producto.
     *
     * @return La fecha de caducidad del producto.
     */
    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    /**
     * Establece la fecha de caducidad del producto.
     *
     * @param fechaCaducidad Nueva fecha de caducidad del producto.
     */
    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    /**
     * Obtiene la descripción del producto farmacéutico.
     *
     * @return La descripción del producto.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del producto farmacéutico.
     *
     * @param descripcion Nueva descripción del producto.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la categoría del producto farmacéutico.
     *
     * @return La categoría del producto.
     */
    public CategoriaProducto getCategoria() {
        return categoria;
    }

    /**
     * Establece la categoría del producto farmacéutico.
     *
     * @param categoria Nueva categoría del producto.
     */
    public void setCategoria(CategoriaProducto categoria) {
        this.categoria = categoria;
    }

    /**
     * Obtiene el precio del producto farmacéutico.
     *
     * @return El precio del producto.
     */
    public Float getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto farmacéutico.
     *
     * @param precio Nuevo precio del producto.
     */
    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el almacén donde está ubicado el producto.
     *
     * @return El almacén donde se encuentra el producto.
     */
    public Almacen getAlmacen() {
        return almacen;
    }

    /**
     * Establece el almacén donde se encuentra el producto.
     *
     * @param almacen Nuevo almacén del producto.
     */
    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    /**
     * Obtiene la lista de registros de gestión asociados al producto.
     *
     * @return Lista de gestiones del producto.
     */
    public List<Gestiona> getGestiona() {
        return gestiona;
    }

    /**
     * Establece la lista de registros de gestión del producto.
     *
     * @param gestiona Nueva lista de gestiones del producto.
     */
    public void setGestiona(List<Gestiona> gestiona) {
        this.gestiona = gestiona;
    }

    /**
     * Obtiene la lista de recetas médicas que incluyen este producto.
     *
     * @return Lista de recetas médicas.
     */
    public List<RecetaMedica> getReceta() {
        return receta;
    }

    /**
     * Establece la lista de recetas médicas del producto.
     *
     * @param receta Nueva lista de recetas médicas.
     */
    public void setReceta(List<RecetaMedica> receta) {
        this.receta = receta;
    }

    /**
     * Obtiene el proveedor del producto farmacéutico.
     *
     * @return El proveedor del producto.
     */
    public Proveedor getProveedor() {
        return proveedor;
    }

    /**
     * Establece el proveedor del producto farmacéutico.
     *
     * @param proveedor Nuevo proveedor del producto.
     */
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    @Override
    public int hashCode() {
        return (idProducto != null ? idProducto.hashCode() : 0);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProductoFarmaceutico)) {
            return false;
        }
        ProductoFarmaceutico other = (ProductoFarmaceutico) object;
        return (this.idProducto != null && this.idProducto.equals(other.idProducto));
    }

    @Override
    public String toString() {
        return "ProductoFarmaceutico{id=" + idProducto + ", nombre=" + nombreProducto + ", categoria=" + categoria + "}";
    }
}