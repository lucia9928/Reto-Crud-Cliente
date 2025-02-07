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
 * Clase que representa un almacén de productos farmacéuticos. 
 * El almacén tiene información sobre su ubicación, el tamaño, la fecha de adquisición
 * y los productos farmacéuticos almacenados en él.
 * 
 * <p>Esta clase está anotada con JAXB para la serialización y deserialización de objetos XML, 
 * y contiene los métodos necesarios para gestionar los datos de un almacén.</p>
 * 
 * @author andoni
 */
@XmlRootElement(name = "almacen")
public class Almacen implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idAlmacen; // Identificador único del almacén.
    private String pais; // País donde se encuentra el almacén.
    private String ciudad; // Ciudad donde se encuentra el almacén.
    private Integer metrosCuadrados; // Tamaño del almacén en metros cuadrados.
    private Date fechaAdquisicion; // Fecha de adquisición del almacén.
    private List<ProductoFarmaceutico> producto; // Lista de productos farmacéuticos almacenados.

    /**
     * Constructor con todos los atributos del almacén.
     *
     * @param idAlmacen El identificador único del almacén.
     * @param pais El país donde se encuentra el almacén.
     * @param ciudad La ciudad donde se encuentra el almacén.
     * @param metrosCuadrados El tamaño del almacén en metros cuadrados.
     * @param fechaAdquisicion La fecha de adquisición del almacén.
     */
    public Almacen(Integer idAlmacen, String pais, String ciudad, Integer metrosCuadrados, Date fechaAdquisicion) {
        this.idAlmacen = idAlmacen;
        this.pais = pais;
        this.ciudad = ciudad;
        this.metrosCuadrados = metrosCuadrados;
        this.fechaAdquisicion = fechaAdquisicion;
    }

    /**
     * Constructor vacío de la clase Almacen.
     */
    public Almacen() {
    }

    /**
     * Obtiene la lista de productos farmacéuticos almacenados en este almacén.
     *
     * @return Una lista de productos farmacéuticos.
     */
    public List<ProductoFarmaceutico> getProducto() {
        return producto;
    }

    /**
     * Establece la lista de productos farmacéuticos almacenados en este almacén.
     *
     * @param producto Una lista de productos farmacéuticos.
     */
    public void setProducto(List<ProductoFarmaceutico> producto) {
        this.producto = producto;
    }

    /**
     * Obtiene el identificador único del almacén.
     *
     * @return El identificador del almacén.
     */
    public Integer getIdAlmacen() {
        return idAlmacen;
    }

    /**
     * Establece el identificador único del almacén.
     *
     * @param idAlmacen El identificador del almacén.
     */
    public void setIdAlmacen(Integer idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    /**
     * Obtiene el país donde se encuentra el almacén.
     *
     * @return El país del almacén.
     */
    public String getPais() {
        return pais;
    }

    /**
     * Establece el país donde se encuentra el almacén.
     *
     * @param pais El país del almacén.
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * Obtiene la ciudad donde se encuentra el almacén.
     *
     * @return La ciudad del almacén.
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Establece la ciudad donde se encuentra el almacén.
     *
     * @param ciudad La ciudad del almacén.
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * Obtiene el tamaño del almacén en metros cuadrados.
     *
     * @return El tamaño del almacén en metros cuadrados.
     */
    public Integer getMetrosCuadrados() {
        return metrosCuadrados;
    }

    /**
     * Establece el tamaño del almacén en metros cuadrados.
     *
     * @param metrosCuadrados El tamaño del almacén en metros cuadrados.
     */
    public void setMetrosCuadrados(Integer metrosCuadrados) {
        this.metrosCuadrados = metrosCuadrados;
    }

    /**
     * Obtiene la fecha de adquisición del almacén.
     *
     * @return La fecha de adquisición del almacén.
     */
    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    /**
     * Establece la fecha de adquisición del almacén.
     *
     * @param fechaAdquisicion La fecha de adquisición del almacén.
     */
    public void setFechaAdquisicion(Date fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    /**
     * Calcula el código hash para este almacén, basado en el identificador único.
     *
     * @return El código hash del almacén.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAlmacen != null ? idAlmacen.hashCode() : 0);
        return hash;
    }

    /**
     * Compara si dos objetos Almacen son iguales. Se considera que dos almacenes son iguales si tienen el mismo
     * identificador único.
     *
     * @param object El objeto a comparar con este almacén.
     * @return true si los objetos son iguales, false si no lo son.
     */
    @Override
    public boolean equals(Object object) {
        // Verifica si el objeto es de tipo Almacen y compara los identificadores.
        if (!(object instanceof Almacen)) {
            return false;
        }
        Almacen other = (Almacen) object;
        if ((this.idAlmacen == null && other.idAlmacen != null) || (this.idAlmacen != null && !this.idAlmacen.equals(other.idAlmacen))) {
            return false;
        }
        return true;
    }

    /**
     * Devuelve una representación en cadena de texto de este almacén.
     * La representación incluye el identificador, país, ciudad, metros cuadrados, 
     * fecha de adquisición y la lista de productos.
     *
     * @return Una cadena con los detalles del almacén.
     */
    @Override
    public String toString() {
        return "Almacen{" + "idAlmacen=" + idAlmacen + ", pais=" + pais + ", ciudad=" + ciudad + 
               ", metrosCuadrados=" + metrosCuadrados + ", fechaAdquisicion=" + fechaAdquisicion + 
               ", producto=" + producto + '}';
    }
}
