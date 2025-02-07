/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.logica;

import eus.tartangalh.crud.interfaces.AlmacenInterfaz;
import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Cliente REST generado para interactuar con el recurso REST de
 * AlmacenFacadeREST.
 *
 * Este cliente proporciona métodos para crear, actualizar, eliminar, y
 * encontrar almacenes. Los métodos permiten trabajar con datos en formato XML o
 * JSON, según sea necesario.
 *
 * USO:
 * <pre>
 *        AlmacenRESTful client = new AlmacenRESTful();
 *        Object response = client.XXX(...);
 *        // hacer lo que sea necesario con la respuesta
 *        client.close();
 * </pre>
 *
 * @author Andoni
 */
public class AlmacenRESTful implements AlmacenInterfaz {

    private WebTarget webTarget;
    private Client client;
    private final ResourceBundle bundle = ResourceBundle.getBundle("archivo.URL");
    private final String BASE_URI = bundle.getString("BASE_URI");

    /**
     * Constructor que inicializa el cliente y configura la URL base del
     * servicio.
     */
    public AlmacenRESTful() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("eus.tartangalh.crud.create.almacen");
    }

    /**
     * Crea un nuevo almacen enviando los datos en formato XML.
     *
     * @param requestEntity objeto que contiene los datos del almacen a crear.
     * @throws WebApplicationException si ocurre un error durante la creación
     * del almacen.
     */
    public void crearAlmacen_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Crea un nuevo almacen enviando los datos en formato JSON.
     *
     * @param requestEntity objeto que contiene los datos del almacen a crear.
     * @throws WebApplicationException si ocurre un error durante la creación
     * del almacen.
     */
    public void crearAlmacen_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Encuentra un almacen por su ID y devuelve la respuesta en formato XML.
     *
     * @param responseType tipo de respuesta esperada.
     * @param id identificador del almacen a buscar.
     * @param <T> tipo de respuesta esperada.
     * @return objeto con los datos del almacen encontrado.
     * @throws WebApplicationException si ocurre un error al buscar el almacen.
     */
    public <T> T encontrar_XML(Class<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Encuentra un almacen por su ID y devuelve la respuesta en formato JSON.
     *
     * @param responseType tipo de respuesta esperada.
     * @param id identificador del almacen a buscar.
     * @param <T> tipo de respuesta esperada.
     * @return objeto con los datos del almacen encontrado.
     * @throws WebApplicationException si ocurre un error al buscar el almacen.
     */
    public <T> T encontrar_JSON(Class<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Elimina un almacen por su ID.
     *
     * @param id identificador del almacen a eliminar.
     * @throws WebApplicationException si ocurre un error al eliminar el
     * almacen.
     */
    public void borrarAlmacen(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     * Actualiza los datos de un almacen en formato XML.
     *
     * @param requestEntity objeto con los nuevos datos del almacen.
     * @throws WebApplicationException si ocurre un error durante la
     * actualización del almacen.
     */
    public void actualizarAlmacen_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Actualiza los datos de un almacen en formato JSON.
     *
     * @param requestEntity objeto con los nuevos datos del almacen.
     * @throws WebApplicationException si ocurre un error durante la
     * actualización del almacen.
     */
    public void actualizarAlmacen_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Obtiene todos los almacenes en formato XML.
     *
     * @param responseType tipo de respuesta esperada.
     * @param <T> tipo de respuesta esperada.
     * @return lista de almacenes.
     * @throws ClientErrorException si ocurre un error al obtener los almacenes.
     */
    @Override
    public <T> T findAll_XML(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Obtiene todos los almacenes en formato JSON.
     *
     * @param responseType tipo de respuesta esperada.
     * @param <T> tipo de respuesta esperada.
     * @return lista de almacenes.
     * @throws ClientErrorException si ocurre un error al obtener los almacenes.
     */
    @Override
    public <T> T findAll_JSON(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Cierra la conexión del cliente REST.
     */
    public void close() {
        client.close();
    }
}
