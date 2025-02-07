package eus.tartangalh.crud.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Representa un proveedor de productos farmacéuticos en el sistema.
 * <p>
 * Contiene información sobre la identificación, dirección y la relación con productos farmacéuticos.
 * La clase es serializable para su persistencia y está anotada con {@code @XmlRootElement} para 
 * permitir su conversión a XML.
 * </p>
 *
 * @author Markel
 */
@XmlRootElement
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Identificador único del proveedor. */
    private Integer idProveedor;

    /** Código de identificación fiscal (CIF) del proveedor. */
    private String cif;

    /** Nombre del proveedor. */
    private String nombreProveedor;

    /** Nombre de la calle donde se encuentra el proveedor. */
    private String calle;

    /** Código postal de la ubicación del proveedor. */
    private Integer codPostal;

    /** Ciudad donde se encuentra el proveedor. */
    private String ciudad;

    /** Fecha en la que el proveedor fue contratado. */
    private Date fechaContratacion;

    /** Lista de productos farmacéuticos suministrados por el proveedor. */
    private List<ProductoFarmaceutico> productoFarmaceutico;

    /**
     * Constructor con parámetros para inicializar las propiedades del proveedor.
     *
     * @param idProveedor Identificador único del proveedor.
     * @param cif Código de identificación fiscal del proveedor.
     * @param nombreProveedor Nombre del proveedor.
     * @param calle Calle donde se encuentra ubicado el proveedor.
     * @param codPostal Código postal de la dirección del proveedor.
     * @param ciudad Ciudad donde se encuentra el proveedor.
     * @param fechaContratacion Fecha en la que el proveedor fue contratado.
     * @param productoFarmaceutico Lista de productos farmacéuticos que proporciona el proveedor.
     */
    public Proveedor(Integer idProveedor, String cif, String nombreProveedor, String calle, Integer codPostal, String ciudad, Date fechaContratacion, List<ProductoFarmaceutico> productoFarmaceutico) {
        this.idProveedor = idProveedor;
        this.cif = cif;
        this.nombreProveedor = nombreProveedor;
        this.calle = calle;
        this.codPostal = codPostal;
        this.ciudad = ciudad;
        this.fechaContratacion = fechaContratacion;
        this.productoFarmaceutico = productoFarmaceutico;
    }

    /**
     * Constructor vacío para la creación de un objeto {@code Proveedor} sin valores iniciales.
     */
    public Proveedor() {
    }

    /**
     * Obtiene el identificador del proveedor.
     *
     * @return El identificador del proveedor.
     */
    public Integer getIdProveedor() {
        return idProveedor;
    }

    /**
     * Establece el identificador del proveedor.
     *
     * @param idProveedor Nuevo identificador del proveedor.
     */
    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    /**
     * Obtiene el CIF del proveedor.
     *
     * @return El CIF del proveedor.
     */
    public String getCif() {
        return cif;
    }

    /**
     * Establece el CIF del proveedor.
     *
     * @param cif Nuevo CIF del proveedor.
     */
    public void setCif(String cif) {
        this.cif = cif;
    }

    /**
     * Obtiene el nombre del proveedor.
     *
     * @return El nombre del proveedor.
     */
    public String getNombreProveedor() {
        return nombreProveedor;
    }

    /**
     * Establece el nombre del proveedor.
     *
     * @param nombreProveedor Nuevo nombre del proveedor.
     */
    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    /**
     * Obtiene la calle donde se encuentra el proveedor.
     *
     * @return El nombre de la calle.
     */
    public String getCalle() {
        return calle;
    }

    /**
     * Establece la calle donde se encuentra el proveedor.
     *
     * @param calle Nueva calle del proveedor.
     */
    public void setCalle(String calle) {
        this.calle = calle;
    }

    /**
     * Obtiene el código postal del proveedor.
     *
     * @return El código postal.
     */
    public Integer getCodPostal() {
        return codPostal;
    }

    /**
     * Establece el código postal del proveedor.
     *
     * @param codPostal Nuevo código postal.
     */
    public void setCodPostal(Integer codPostal) {
        this.codPostal = codPostal;
    }

    /**
     * Obtiene la ciudad del proveedor.
     *
     * @return La ciudad del proveedor.
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Establece la ciudad del proveedor.
     *
     * @param ciudad Nueva ciudad del proveedor.
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * Obtiene la fecha de contratación del proveedor.
     *
     * @return La fecha de contratación.
     */
    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    /**
     * Establece la fecha de contratación del proveedor.
     *
     * @param fechaContratacion Nueva fecha de contratación.
     */
    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    /**
     * Obtiene la lista de productos farmacéuticos proporcionados por el proveedor.
     *
     * @return Lista de productos farmacéuticos.
     */
    @XmlTransient
    public List<ProductoFarmaceutico> getProductoFarmaceutico() {
        return productoFarmaceutico;
    }

    /**
     * Establece la lista de productos farmacéuticos proporcionados por el proveedor.
     *
     * @param productoFarmaceutico Nueva lista de productos farmacéuticos.
     */
    public void setProductoFarmaceutico(List<ProductoFarmaceutico> productoFarmaceutico) {
        this.productoFarmaceutico = productoFarmaceutico;
    }

    /**
     * Calcula el código hash basado en el identificador del proveedor.
     *
     * @return Código hash del objeto.
     */
    @Override
    public int hashCode() {
        return (idProveedor != null ? idProveedor.hashCode() : 0);
    }

    /**
     * Compara este objeto con otro para verificar si son iguales.
     * 
     * @param object Objeto a comparar.
     * @return {@code true} si ambos objetos son iguales, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Proveedor)) {
            return false;
        }
        Proveedor other = (Proveedor) object;
        return (this.idProveedor != null && this.idProveedor.equals(other.idProveedor));
    }

    /**
     * Devuelve una representación en cadena del objeto.
     *
     * @return Cadena que representa el proveedor.
     */
    @Override
    public String toString() {
        return "Proveedor{id=" + idProveedor + ", nombre=" + nombreProveedor + ", ciudad=" + ciudad + "}";
    }
}

