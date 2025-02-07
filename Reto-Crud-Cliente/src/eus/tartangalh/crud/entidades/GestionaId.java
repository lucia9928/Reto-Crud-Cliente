/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.entidades;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase que representa la clave primaria compuesta para la entidad {@code Gestiona}.
 * <p>
 * Esta clase almacena el identificador de la relación entre un {@link Trabajador} (mediante su DNI)
 * y un {@link ProductoFarmaceutico} (mediante su ID de producto).
 * </p>
 * 
 * <p>Está anotada con {@code @XmlRootElement} para permitir su serialización y 
 * deserialización en formato XML.</p>
 * 
 * @author Markel
 */
@XmlRootElement
public class GestionaId implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** DNI del trabajador que gestiona el producto farmacéutico. */
    private String dni;

    /** Identificador del producto farmacéutico gestionado. */
    private Integer idProducto;

    /**
     * Constructor que inicializa un objeto {@code GestionaId} con los valores especificados.
     * 
     * @param dni DNI del trabajador que gestiona el producto.
     * @param idProducto Identificador del producto farmacéutico gestionado.
     */
    public GestionaId(String dni, Integer idProducto) {
        this.dni = dni;
        this.idProducto = idProducto;
    }

    /**
     * Constructor vacío para la creación de un objeto {@code GestionaId} sin valores iniciales.
     */
    public GestionaId() {
    }

    /**
     * Obtiene el DNI del trabajador que gestiona el producto.
     *
     * @return El DNI del trabajador.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Establece el DNI del trabajador que gestiona el producto.
     *
     * @param dni El nuevo DNI del trabajador.
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Obtiene el identificador del producto farmacéutico gestionado.
     *
     * @return El identificador del producto.
     */
    public Integer getIdProducto() {
        return idProducto;
    }

    /**
     * Establece el identificador del producto farmacéutico gestionado.
     *
     * @param idProducto El nuevo identificador del producto.
     */
    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Genera un código hash basado en el DNI y el identificador del producto.
     *
     * @return El código hash generado.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (dni != null ? dni.hashCode() : 0);
        hash = 31 * hash + (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    /**
     * Compara si dos objetos {@code GestionaId} son iguales.
     * <p>
     * La igualdad se basa en la coincidencia del DNI del trabajador y el identificador del producto.
     * </p>
     *
     * @param object El objeto a comparar con esta instancia de {@code GestionaId}.
     * @return {@code true} si los objetos son iguales, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof GestionaId)) {
            return false;
        }
        GestionaId other = (GestionaId) object;
        return (this.dni != null && this.dni.equals(other.dni)) 
            && (this.idProducto != null && this.idProducto.equals(other.idProducto));
    }

    /**
     * Devuelve una representación en cadena del objeto {@code GestionaId}.
     * <p>
     * La representación incluye el DNI del trabajador y el identificador del producto.
     * </p>
     *
     * @return Una cadena con los detalles del identificador de gestión.
     */
    @Override
    public String toString() {
        return "GestionaId{dni=" + dni + ", idProducto=" + idProducto + "}";
    }
}