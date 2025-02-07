/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.logica;

import eus.tartangalh.crud.interfaces.GestionaInterfaz;
import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Cliente REST generado para interactuar con el recurso REST de
 * GestionaFacadeREST. Este cliente proporciona métodos para crear, modificar,
 * eliminar y consultar gestiones. Los métodos permiten trabajar con datos en
 * formato XML o JSON según sea necesario.
 *
 * USO:
 * <pre>
 *        GestionaRESTClient client = new GestionaRESTClient();
 *        Object response = client.XXX(...);
 *        // realizar alguna acción con la respuesta
 *        client.close();
 * </pre>
 *
 * @author Markel
 */
public class GestionaRESTClient implements GestionaInterfaz {

    private WebTarget webTarget;
    private Client client;
    private final ResourceBundle bundle = ResourceBundle.getBundle("archivo.URL");
    private final String BASE_URI = bundle.getString("BASE_URI");

    /**
     * Constructor que inicializa el cliente y configura la URL base del
     * servicio REST.
     */
    public GestionaRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("eus.tartangalh.crud.create.gestiona");
    }

    /**
     * Actualiza una gestión en formato XML.
     *
     * @param requestEntity objeto con los datos de la gestión a actualizar.
     * @throws ClientErrorException si ocurre un error durante la actualización
     * de la gestión.
     */
    public void actualizarGestiona_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Actualiza una gestión en formato JSON.
     *
     * @param requestEntity objeto con los datos de la gestión a actualizar.
     * @throws ClientErrorException si ocurre un error durante la actualización
     * de la gestión.
     */
    public void actualizarGestiona_JSON(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Encuentra una gestión específica utilizando su DNI y el ID del producto
     * en formato XML.
     *
     * @param responseType tipo de respuesta esperada.
     * @param dni el DNI del cliente.
     * @param idProducto el ID del producto.
     * @param <T> tipo de respuesta esperada.
     * @return los datos de la gestión.
     * @throws ClientErrorException si ocurre un error durante la búsqueda de la
     * gestión.
     */
    public <T> T encontrarGestiona_XML(Class<T> responseType, String dni, String idProducto) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{dni, idProducto}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Encuentra una gestión específica utilizando su DNI y el ID del producto
     * en formato JSON.
     *
     * @param responseType tipo de respuesta esperada.
     * @param dni el DNI del cliente.
     * @param idProducto el ID del producto.
     * @param <T> tipo de respuesta esperada.
     * @return los datos de la gestión.
     * @throws ClientErrorException si ocurre un error durante la búsqueda de la
     * gestión.
     */
    public <T> T encontrarGestiona_JSON(Class<T> responseType, String dni, String idProducto) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{dni, idProducto}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Muestra todas las gestiones en formato XML.
     *
     * @param responseType tipo de respuesta esperada.
     * @param <T> tipo de respuesta esperada.
     * @return una lista de todas las gestiones.
     * @throws ClientErrorException si ocurre un error durante la obtención de
     * las gestiones.
     */
    public <T> T mostrarTodosGestiona_XML(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Muestra todas las gestiones en formato JSON.
     *
     * @param responseType tipo de respuesta esperada.
     * @param <T> tipo de respuesta esperada.
     * @return una lista de todas las gestiones.
     * @throws ClientErrorException si ocurre un error durante la obtención de
     * las gestiones.
     */
    public <T> T mostrarTodosGestiona_JSON(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Crea una nueva gestión en formato XML.
     *
     * @param requestEntity objeto con los datos de la gestión a crear.
     * @throws ClientErrorException si ocurre un error durante la creación de la
     * gestión.
     */
    public void crearGestiona_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Crea una nueva gestión en formato JSON.
     *
     * @param requestEntity objeto con los datos de la gestión a crear.
     * @throws ClientErrorException si ocurre un error durante la creación de la
     * gestión.
     */
    public void crearGestiona_JSON(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Elimina una gestión utilizando el DNI y el ID del producto.
     *
     * @param dni el DNI del cliente.
     * @param idProducto el ID del producto.
     * @throws ClientErrorException si ocurre un error durante la eliminación de
     * la gestión.
     */
    public void borrarGestiona(String dni, String idProducto) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{dni, idProducto})).request().delete();
    }

    /**
     * Cierra la conexión del cliente REST.
     */
    public void close() {
        client.close();
    }
}
