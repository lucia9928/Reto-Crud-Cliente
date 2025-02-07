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
 * Representa a un trabajador en el sistema, extendiendo la clase
 * {@code Usuario}.
 * <p>
 * Contiene información sobre la fecha de contratación, el cargo del trabajador
 * y la gestión de productos farmacéuticos.
 * </p>
 *
 * @author Melany
 */
@XmlRootElement(name = "trabajador")
public class Trabajador extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Fecha en la que el trabajador fue contratado.
     */
    private Date fechaContratacion;

    /**
     * Cargo del trabajador dentro de la organización.
     */
    private TipoCargo tipoCargo;

    /**
     * Lista de productos farmacéuticos gestionados por el trabajador.
     */
    private List<Gestiona> gestionaProducto;

    /**
     * Constructor vacío que inicializa un objeto {@code Trabajador} sin valores
     * asignados. Llama al constructor de la superclase {@code Usuario}.
     */
    public Trabajador() {
        super();
    }

    /**
     * Constructor que inicializa un trabajador con una fecha de contratación y
     * un tipo de cargo.
     *
     * @param fechaContratacion Fecha en la que el trabajador fue contratado.
     * @param tipoCargo Cargo del trabajador dentro de la organización.
     */
    public Trabajador(Date fechaContratacion, TipoCargo tipoCargo) {
        this.fechaContratacion = fechaContratacion;
        this.tipoCargo = tipoCargo;
    }

    /**
     * Obtiene la fecha de contratación del trabajador.
     *
     * @return La fecha de contratación.
     */
    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    /**
     * Establece la fecha de contratación del trabajador.
     *
     * @param fechaContratacion Nueva fecha de contratación.
     */
    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    /**
     * Obtiene el cargo del trabajador.
     *
     * @return El cargo del trabajador.
     */
    public TipoCargo getTipoCargo() {
        return tipoCargo;
    }

    /**
     * Establece el cargo del trabajador.
     *
     * @param tipoCargo Nuevo cargo del trabajador.
     */
    public void setTipoCargo(TipoCargo tipoCargo) {
        this.tipoCargo = tipoCargo;
    }

    /**
     * Obtiene la lista de productos farmacéuticos gestionados por el
     * trabajador.
     *
     * @return Lista de productos gestionados.
     */
    public List<Gestiona> getGestionaProducto() {
        return gestionaProducto;
    }

    /**
     * Establece la lista de productos farmacéuticos gestionados por el
     * trabajador.
     *
     * @param gestionaProducto Nueva lista de productos gestionados.
     */
    public void setGestionaProducto(List<Gestiona> gestionaProducto) {
        this.gestionaProducto = gestionaProducto;
    }

    /**
     * Genera un código hash basado en el número de DNI del trabajador (heredado
     * de {@code Usuario}).
     *
     * @return Código hash del objeto.
     */
    @Override
    public int hashCode() {
        return (super.getDni() != null ? super.getDni().hashCode() : 0);
    }

    /**
     * Compara este objeto con otro para verificar si son iguales.
     * <p>
     * Se considera que dos trabajadores son iguales si tienen el mismo DNI.
     * </p>
     *
     * @param object Objeto a comparar.
     * @return {@code true} si ambos objetos son iguales, {@code false} en caso
     * contrario.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Trabajador)) {
            return false;
        }
        Trabajador other = (Trabajador) object;
        return this.getDni() != null && this.getDni().equals(other.getDni());
    }

    /**
     * Devuelve una representación en cadena del objeto {@code Trabajador}.
     *
     * @return Cadena que representa al trabajador.
     */
    @Override
    public String toString() {
        return "Trabajador{dni=" + super.getDni() + ", tipoCargo=" + tipoCargo + ", fechaContratacion=" + fechaContratacion + "}";
    }
}
