package eus.tartangalh.crud.entidades;
import eus.tartangalh.crud.entidades.Cliente;
import eus.tartangalh.crud.entidades.ProductoFarmaceutico;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Representa una receta médica en el sistema.
 * <p>
 * Contiene información sobre la receta, el cliente al que pertenece, la fecha de emisión, la descripción,
 * la cantidad de productos prescritos y la lista de productos farmacéuticos asociados.
 * </p>
 *
 * @author Melany
 */
@XmlRootElement
public class RecetaMedica implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Identificador único de la receta médica. */
    private Integer idReceta;

    /** Cliente al que pertenece la receta. */
    private Cliente cliente;

    /** Fecha en que se emitió la receta médica. */
    private Date fechaReceta;

    /** Descripción general de la receta médica. */
    private String descripcion;

    /** Cantidad de productos farmacéuticos prescritos en la receta. */
    private Integer cantidad;

    /** Lista de productos farmacéuticos asociados a la receta. */
    private List<ProductoFarmaceutico> productos;

    /**
     * Constructor vacío para la creación de un objeto {@code RecetaMedica} sin valores iniciales.
     */
    public RecetaMedica() {
    }

    /**
     * Constructor con parámetros para inicializar los atributos de la receta médica.
     *
     * @param idReceta Identificador único de la receta.
     * @param fechaReceta Fecha de emisión de la receta.
     * @param descripcion Descripción general de la receta.
     * @param cantidad Cantidad de productos prescritos en la receta.
     * @param productos Lista de productos farmacéuticos asociados a la receta.
     */
    public RecetaMedica(Integer idReceta, Date fechaReceta, String descripcion, Integer cantidad, List<ProductoFarmaceutico> productos) {
        this.idReceta = idReceta;
        this.fechaReceta = fechaReceta;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.productos = productos;
    }

    /**
     * Obtiene el identificador de la receta médica.
     *
     * @return El identificador de la receta.
     */
    public Integer getIdReceta() {
        return idReceta;
    }

    /**
     * Establece el identificador de la receta médica.
     *
     * @param idReceta Nuevo identificador de la receta.
     */
    public void setIdReceta(Integer idReceta) {
        this.idReceta = idReceta;
    }

    /**
     * Obtiene el cliente al que pertenece la receta médica.
     *
     * @return Cliente asociado a la receta.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Establece el cliente al que pertenece la receta médica.
     *
     * @param cliente Cliente asociado a la receta.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtiene la fecha de emisión de la receta médica.
     *
     * @return Fecha de emisión de la receta.
     */
    public Date getFechaReceta() {
        return fechaReceta;
    }

    /**
     * Establece la fecha de emisión de la receta médica.
     *
     * @param fechaReceta Nueva fecha de emisión de la receta.
     */
    public void setFechaReceta(Date fechaReceta) {
        this.fechaReceta = fechaReceta;
    }

    /**
     * Obtiene la descripción de la receta médica.
     *
     * @return Descripción de la receta.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la receta médica.
     *
     * @param descripcion Nueva descripción de la receta.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la cantidad de productos farmacéuticos prescritos en la receta.
     *
     * @return Cantidad de productos.
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad de productos farmacéuticos prescritos en la receta.
     *
     * @param cantidad Nueva cantidad de productos.
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene la lista de productos farmacéuticos asociados a la receta.
     *
     * @return Lista de productos farmacéuticos.
     */
    public List<ProductoFarmaceutico> getProductos() {
        return productos;
    }

    /**
     * Establece la lista de productos farmacéuticos asociados a la receta.
     *
     * @param productos Nueva lista de productos farmacéuticos.
     */
    public void setProductos(List<ProductoFarmaceutico> productos) {
        this.productos = productos;
    }

    /**
     * Obtiene el nombre del cliente si existe, de lo contrario devuelve "Sin cliente".
     *
     * @return Nombre del cliente o "Sin cliente" si no está asignado.
     */
    public String getNombreCliente() {
        return (cliente != null) ? cliente.getNombre() : "Sin cliente";
    }

    /**
     * Obtiene una lista de nombres de los productos farmacéuticos prescritos en la receta.
     *
     * @return Cadena con los nombres de los productos separados por comas, o "Sin productos" si la lista está vacía.
     */
    public String getListaProductos() {
        if (productos == null || productos.isEmpty()) {
            return "Sin productos";
        }

        StringBuilder nombresProductos = new StringBuilder();
        for (ProductoFarmaceutico producto : productos) {
            if (nombresProductos.length() > 0) {
                nombresProductos.append(", ");
            }
            nombresProductos.append(producto.getNombreProducto());
        }
        return nombresProductos.toString();
    }

    /**
     * Genera un código hash basado en el identificador de la receta médica.
     *
     * @return Código hash del objeto.
     */
    @Override
    public int hashCode() {
        return (idReceta != null ? idReceta.hashCode() : 0);
    }

    /**
     * Compara este objeto con otro para verificar si son iguales.
     *
     * @param object Objeto a comparar.
     * @return {@code true} si ambos objetos son iguales, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RecetaMedica)) {
            return false;
        }
        RecetaMedica other = (RecetaMedica) object;
        return (this.idReceta != null && this.idReceta.equals(other.idReceta));
    }

    /**
     * Devuelve una representación en cadena del objeto.
     *
     * @return Cadena que representa la receta médica.
     */
    @Override
    public String toString() {
        return "RecetaMedica{id=" + idReceta + ", cliente=" + getNombreCliente() + ", productos=" + getListaProductos() + "}";
    }
}