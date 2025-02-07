/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.logica;

import eus.tartangalh.crud.interfaces.ProductoInterfaz;
import java.util.ResourceBundle;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 * Cliente REST generado para interactuar con el recurso REST de
 * ProductoFarmaceuticoFacadeREST. Este cliente proporciona métodos para crear,
 * modificar, eliminar y consultar productos farmacéuticos. Los métodos permiten
 * trabajar con datos en formato XML o JSON según sea necesario.
 *
 * USO:
 * <pre>
 *        ProductoFarmaceuticoRESTful client = new ProductoFarmaceuticoRESTful();
 *        Object response = client.XXX(...);
 *        // realizar alguna acción con la respuesta
 *        client.close();
 * </pre>
 *
 * @author Oscar
 */
public class ProductoFarmaceuticoRESTful implements ProductoInterfaz {

    private WebTarget webTarget;
    private Client client;
    private final ResourceBundle bundle = ResourceBundle.getBundle("archivo.URL");
    private final String BASE_URI = bundle.getString("BASE_URI");

    /**
     * Constructor que inicializa el cliente y configura la URL base del
     * servicio REST.
     */
    public ProductoFarmaceuticoRESTful() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("Producto_Farmaceutico");
    }

    /**
     * Crea un nuevo producto farmacéutico en formato XML.
     *
     * @param requestEntity objeto con los datos del producto a crear.
     * @throws WebApplicationException si ocurre un error durante la creación
     * del producto.
     */
    public void crearProducto_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(MediaType.APPLICATION_XML)
                .post(Entity.entity(requestEntity, MediaType.APPLICATION_XML));
    }

    /**
     * Crea un nuevo producto farmacéutico en formato JSON.
     *
     * @param requestEntity objeto con los datos del producto a crear.
     * @throws WebApplicationException si ocurre un error durante la creación
     * del producto.
     */
    public void crearProducto_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(requestEntity, MediaType.APPLICATION_JSON));
    }

    /**
     * Elimina un producto farmacéutico utilizando su ID.
     *
     * @param id el ID del producto a eliminar.
     * @throws WebApplicationException si ocurre un error durante la eliminación
     * del producto.
     */
    public void borrarProducto(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", id))
                .request()
                .delete();
    }

    /**
     * Actualiza un producto farmacéutico en formato XML.
     *
     * @param requestEntity objeto con los datos del producto a actualizar.
     * @throws WebApplicationException si ocurre un error durante la
     * actualización del producto.
     */
    public void actualizarProducto_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(MediaType.APPLICATION_XML)
                .put(Entity.entity(requestEntity, MediaType.APPLICATION_XML));
    }

    /**
     * Actualiza un producto farmacéutico en formato JSON.
     *
     * @param requestEntity objeto con los datos del producto a actualizar.
     * @throws WebApplicationException si ocurre un error durante la
     * actualización del producto.
     */
    public void actualizarProducto_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(requestEntity, MediaType.APPLICATION_JSON));
    }

    /**
     * Encuentra un producto farmacéutico utilizando su ID en formato XML.
     *
     * @param responseType tipo de respuesta esperada.
     * @param id el ID del producto.
     * @param <T> tipo de respuesta esperada.
     * @return los datos del producto.
     * @throws WebApplicationException si ocurre un error durante la búsqueda
     * del producto.
     */
    public <T> T encontrarProducto_XML(GenericType<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("{0}", id));
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Encuentra un producto farmacéutico utilizando su ID en formato JSON.
     *
     * @param responseType tipo de respuesta esperada.
     * @param id el ID del producto.
     * @param <T> tipo de respuesta esperada.
     * @return los datos del producto.
     * @throws WebApplicationException si ocurre un error durante la búsqueda
     * del producto.
     */
    public <T> T encontrarProducto_JSON(GenericType<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("{0}", id));
        return resource.request(MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Encuentra productos farmacéuticos por nombre en formato XML.
     *
     * @param responseType tipo de respuesta esperada.
     * @param nombre el nombre del producto.
     * @param <T> tipo de respuesta esperada.
     * @return los productos farmacéuticos encontrados.
     * @throws WebApplicationException si ocurre un error durante la búsqueda de
     * productos por nombre.
     */
    public <T> T encontrarPorNombre_XML(GenericType<T> responseType, String nombre) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("nombre/{0}", nombre));
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Encuentra productos farmacéuticos por nombre en formato JSON.
     *
     * @param responseType tipo de respuesta esperada.
     * @param nombre el nombre del producto.
     * @param <T> tipo de respuesta esperada.
     * @return los productos farmacéuticos encontrados.
     * @throws WebApplicationException si ocurre un error durante la búsqueda de
     * productos por nombre.
     */
    public <T> T encontrarPorNombre_JSON(GenericType<T> responseType, String nombre) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("nombre/{0}", nombre));
        return resource.request(MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Encuentra todos los productos farmacéuticos en formato XML.
     *
     * @param responseType tipo de respuesta esperada.
     * @param <T> tipo de respuesta esperada.
     * @return todos los productos farmacéuticos.
     * @throws WebApplicationException si ocurre un error durante la obtención
     * de los productos.
     */
    @Override
    public <T> T encontrarTodos_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Encuentra todos los productos farmacéuticos en formato JSON.
     *
     * @param responseType tipo de respuesta esperada.
     * @param <T> tipo de respuesta esperada.
     * @return todos los productos farmacéuticos.
     * @throws WebApplicationException si ocurre un error durante la obtención
     * de los productos.
     */
    public <T> T encontrarTodos_JSON(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Encuentra productos farmacéuticos por fecha de caducidad en formato XML.
     *
     * @param responseType tipo de respuesta esperada.
     * @param fechaLimite la fecha límite de caducidad.
     * @param <T> tipo de respuesta esperada.
     * @return los productos farmacéuticos encontrados por fecha de caducidad.
     * @throws WebApplicationException si ocurre un error durante la búsqueda de
     * productos por fecha de caducidad.
     */
    public <T> T encontrarPorFechaCaducidad_XML(GenericType<T> responseType, String fechaLimite) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("caducidad/{0}", fechaLimite));
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Encuentra productos farmacéuticos por fecha de caducidad en formato JSON.
     *
     * @param responseType tipo de respuesta esperada.
     * @param fechaLimite la fecha límite de caducidad.
     * @param <T> tipo de respuesta esperada.
     * @return los productos farmacéuticos encontrados por fecha de caducidad.
     * @throws WebApplicationException si ocurre un error durante la búsqueda de
     * productos por fecha de caducidad.
     */
    public <T> T encontrarPorFechaCaducidad_JSON(GenericType<T> responseType, String fechaLimite) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("caducidad/{0}", fechaLimite));
        return resource.request(MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Cierra la conexión del cliente REST.
     */
    public void close() {
        client.close();
    }
}
