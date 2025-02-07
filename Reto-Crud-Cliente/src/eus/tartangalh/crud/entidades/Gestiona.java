/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.entidades;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase que representa la gestión de productos farmacéuticos por parte de un trabajador.
 * <p>
 * Esta clase almacena la información sobre la relación entre un {@link Trabajador} y un 
 * {@link ProductoFarmaceutico}, incluyendo la cantidad gestionada y la fecha de compra.
 * </p>
 * 
 * <p>Está anotada con {@code @XmlRootElement} para permitir su serialización y 
 * deserialización en formato XML.</p>
 * 
 * @author Markel
 */
@XmlRootElement
public class Gestiona implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Identificador compuesto que representa la relación entre trabajador y producto. */
    private GestionaId gestionaId;

    /** Trabajador que gestiona el producto farmacéutico. */
    private Trabajador trabajador;

    /** Producto farmacéutico que está siendo gestionado. */
    private ProductoFarmaceutico productoFarmaceutico;

    /** Fecha en la que se realizó la compra del producto. */
    private Date fechaCompra;

    /** Cantidad del producto gestionada. */
    private Integer cantidad;

    /**
     * Constructor que inicializa un objeto Gestiona con los valores especificados.
     * 
     * @param gestionaId Identificador compuesto de la gestión.
     * @param trabajador Trabajador responsable de la gestión.
     * @param productoFarmaceutico Producto farmacéutico gestionado.
     * @param cantidad Cantidad del producto gestionada.
     * @param fechaCompra Fecha en la que se realizó la compra del producto.
     */
    public Gestiona(GestionaId gestionaId, Trabajador trabajador, ProductoFarmaceutico productoFarmaceutico, Integer cantidad, Date fechaCompra) {
        this.gestionaId = gestionaId;
        this.trabajador = trabajador;
        this.productoFarmaceutico = productoFarmaceutico;
        this.cantidad = cantidad;
        this.fechaCompra = fechaCompra;
    }

    /**
     * Constructor vacío para la creación de un objeto Gestiona sin valores iniciales.
     */
    public Gestiona() {
    }

    /**
     * Obtiene la fecha en la que se realizó la compra del producto.
     *
     * @return La fecha de compra.
     */
    public Date getFechaCompra() {
        return fechaCompra;
    }

    /**
     * Establece la fecha en la que se realizó la compra del producto.
     *
     * @param fechaCompra La nueva fecha de compra.
     */
    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    /**
     * Obtiene el identificador compuesto de la gestión.
     *
     * @return El identificador de la gestión.
     */
    public GestionaId getGestionaId() {
        return gestionaId;
    }

    /**
     * Establece el identificador compuesto de la gestión.
     *
     * @param gestionaId El nuevo identificador de la gestión.
     */
    public void setGestionaId(GestionaId gestionaId) {
        this.gestionaId = gestionaId;
    }

    /**
     * Obtiene el trabajador responsable de la gestión.
     *
     * @return El trabajador asociado a la gestión.
     */
    public Trabajador getTrabajador() {
        return trabajador;
    }

    /**
     * Establece el trabajador responsable de la gestión.
     *
     * @param trabajador El nuevo trabajador responsable.
     */
    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    /**
     * Obtiene el producto farmacéutico gestionado.
     *
     * @return El producto farmacéutico gestionado.
     */
    public ProductoFarmaceutico getProductoFarmaceutico() {
        return productoFarmaceutico;
    }

    /**
     * Establece el producto farmacéutico gestionado.
     *
     * @param productoFarmaceutico El nuevo producto farmacéutico gestionado.
     */
    public void setProductoFarmaceutico(ProductoFarmaceutico productoFarmaceutico) {
        this.productoFarmaceutico = productoFarmaceutico;
    }

    /**
     * Obtiene la cantidad del producto gestionada.
     *
     * @return La cantidad gestionada.
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad del producto gestionada.
     *
     * @param cantidad La nueva cantidad gestionada.
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Genera un código hash basado en el identificador de la gestión.
     *
     * @return El código hash de la gestión.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gestionaId != null ? gestionaId.hashCode() : 0);
        return hash;
    }

    /**
     * Compara si dos objetos Gestiona son iguales.
     * <p>
     * La igualdad se basa en el identificador compuesto de la gestión.
     * </p>
     *
     * @param object El objeto a comparar con esta instancia de Gestiona.
     * @return {@code true} si los objetos son iguales, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Gestiona)) {
            return false;
        }
        Gestiona other = (Gestiona) object;
        return (this.gestionaId != null || other.gestionaId == null) && (this.gestionaId == null || this.gestionaId.equals(other.gestionaId));
    }

    /**
     * Devuelve una representación en cadena del objeto Gestiona.
     * <p>
     * La representación incluye el identificador de la gestión.
     * </p>
     *
     * @return Una cadena con los detalles de la gestión.
     */
    @Override
    public String toString() {
        return "entity.Gestiona[ id=" + gestionaId + " ]";
    }
}
