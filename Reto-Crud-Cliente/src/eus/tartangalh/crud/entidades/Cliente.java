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
 * Clase que representa a un Cliente en el sistema.
 * <p>
 * Esta clase hereda de {@link Usuario} y añade atributos específicos de un cliente, 
 * como la fecha de registro y la lista de recetas médicas asociadas.
 * </p>
 * 
 * <p>Además, está anotada con {@code @XmlRootElement} para permitir la serialización y 
 * deserialización de objetos XML.</p>
 * 
 * @author melany
 */
@XmlRootElement(name = "cliente")
public class Cliente extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Fecha en la que el cliente se registró en el sistema. */
    private Date fechaRegistro;

    /** Lista de recetas médicas asociadas al cliente. */
    private List<RecetaMedica> recetas;

    /**
     * Constructor que inicializa un Cliente con la fecha de registro especificada.
     *
     * @param fechaRegistro La fecha en la que el cliente se registró.
     */
    public Cliente(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Constructor vacío que inicializa un Cliente sin valores definidos.
     * Llama al constructor de la clase base {@link Usuario}.
     */
    public Cliente() {
        super();
    }

    /**
     * Obtiene la lista de recetas médicas asociadas al cliente.
     *
     * @return Una lista de recetas médicas.
     */
    public List<RecetaMedica> getRecetas() {
        return recetas;
    }

    /**
     * Establece la lista de recetas médicas asociadas al cliente.
     *
     * @param recetas Una lista de recetas médicas.
     */
    public void setRecetas(List<RecetaMedica> recetas) {
        this.recetas = recetas;
    }

    /**
     * Obtiene la fecha de registro del cliente en el sistema.
     *
     * @return La fecha de registro del cliente.
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Establece la fecha de registro del cliente en el sistema.
     *
     * @param fechaRegistro La nueva fecha de registro del cliente.
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * Genera un código hash basado en el número de DNI del cliente.
     *
     * @return El código hash del cliente.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.getDni() != null ? super.getDni().hashCode() : 0);
        return hash;
    }

    /**
     * Compara si dos objetos Cliente son iguales.
     * <p>
     * La igualdad se determina mediante el método {@code equals()} de la clase {@link Usuario}.
     * </p>
     *
     * @param object El objeto a comparar con este cliente.
     * @return {@code true} si los objetos son iguales, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object object) {
        return super.equals(object);
    }

    /**
     * Devuelve una representación en cadena del Cliente.
     * <p>
     * La representación incluye el identificador del cliente, que en este caso es su DNI.
     * </p>
     *
     * @return Una cadena con los detalles del cliente.
     */
    @Override
    public String toString() {
        return "eus.tartangalh.crud.create.Cliente[ id=" + super.getDni() + " ]";
    }
}
