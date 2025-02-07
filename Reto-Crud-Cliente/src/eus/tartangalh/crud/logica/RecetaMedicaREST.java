/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.logica;

import eus.tartangalh.crud.interfaces.RecetaMedicaInterfaz;
import java.util.ResourceBundle;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Cliente REST generado para interactuar con el recurso REST de
 * RecetaMedicaFacadeREST. Este cliente permite realizar operaciones de gestión
 * de recetas médicas, incluyendo la creación, modificación, eliminación, y
 * consulta de recetas.
 *
 * USO:
 * <pre>
 *        RecetaMedicaREST client = new RecetaMedicaREST();
 *        Object response = client.XXX(...);
 *        // realizar alguna acción con la respuesta
 *        client.close();
 * </pre>
 *
 * @author lucia_puwj3zw
 */
public class RecetaMedicaREST implements RecetaMedicaInterfaz {

    private final WebTarget webTarget;
    private final Client client;
    private final ResourceBundle bundle = ResourceBundle.getBundle("archivo.URL");
    private final String BASE_URI = bundle.getString("BASE_URI");

    /**
     * Constructor que inicializa el cliente REST y la URL base para acceder al
     * servicio.
     */
    public RecetaMedicaREST() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("Receta_Medica");
    }

    /**
     * Elimina una receta médica utilizando su ID.
     *
     * @param id el ID de la receta médica a eliminar.
     * @throws WebApplicationException si ocurre un error durante la
     * eliminación.
     */
    @Override
    public void eliminarRecetamedica(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     * Obtiene los productos asociados a una receta médica específica.
     *
     * @param responseType tipo de respuesta esperada.
     * @param id el ID de la receta médica.
     * @param <T> tipo de respuesta esperada.
     * @return los productos asociados a la receta médica.
     * @throws WebApplicationException si ocurre un error durante la consulta.
     */
    @Override
    public <T> T obtenerProductosPorReceta(GenericType<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("productos/{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Modifica una receta médica en formato XML.
     *
     * @param requestEntity objeto que contiene los nuevos datos para la receta
     * médica.
     * @throws WebApplicationException si ocurre un error durante la
     * modificación.
     */
    @Override
    public void modificarRecetaMedica_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Modifica una receta médica en formato JSON.
     *
     * @param requestEntity objeto que contiene los nuevos datos para la receta
     * médica.
     * @throws WebApplicationException si ocurre un error durante la
     * modificación.
     */
    @Override
    public void modificarRecetaMedica_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Encuentra una receta médica por su ID en formato XML.
     *
     * @param responseType tipo de respuesta esperada.
     * @param id el ID de la receta médica.
     * @param <T> tipo de respuesta esperada.
     * @return la receta médica asociada al ID.
     * @throws WebApplicationException si ocurre un error durante la consulta.
     */
    @Override
    public <T> T encontrarRecetaPorId_XML(GenericType<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Encuentra una receta médica por su ID en formato JSON.
     *
     * @param responseType tipo de respuesta esperada.
     * @param id el ID de la receta médica.
     * @param <T> tipo de respuesta esperada.
     * @return la receta médica asociada al ID.
     * @throws WebApplicationException si ocurre un error durante la consulta.
     */
    @Override
    public <T> T encontrarRecetaPorId_JSON(GenericType<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Encuentra todas las recetas médicas en formato XML.
     *
     * @param responseType tipo de respuesta esperada.
     * @param <T> tipo de respuesta esperada.
     * @return la lista de todas las recetas médicas.
     * @throws WebApplicationException si ocurre un error durante la consulta.
     */
    @Override
    public <T> T encontrarTodasLasRecetas_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Encuentra todas las recetas médicas en formato JSON.
     *
     * @param responseType tipo de respuesta esperada.
     * @param <T> tipo de respuesta esperada.
     * @return la lista de todas las recetas médicas.
     * @throws WebApplicationException si ocurre un error durante la consulta.
     */
    @Override
    public <T> T encontrarTodasLasRecetas_JSON(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Crea una receta médica en formato XML asociada a un ID.
     *
     * @param requestEntity objeto con los datos de la receta médica a crear.
     * @throws WebApplicationException si ocurre un error durante la creación.
     */
    @Override
    public void crearRecetaMedica_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Crea una receta médica en formato JSON asociada a un ID.
     *
     * @param requestEntity objeto con los datos de la receta médica a crear.
     * @throws WebApplicationException si ocurre un error durante la creación.
     */
    @Override
    public void crearRecetaMedica_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Encuentra recetas médicas por un rango de fechas (inicio y fin) en
     * formato XML.
     *
     * @param responseType tipo de respuesta esperada.
     * @param fechaInicio fecha de inicio del rango.
     * @param fechaFin fecha de fin del rango.
     * @param <T> tipo de respuesta esperada.
     * @return las recetas médicas dentro del rango de fechas.
     * @throws WebApplicationException si ocurre un error durante la consulta.
     */
    @Override
    public <T> T encontrarRecetasPorFecha_XML(GenericType<T> responseType, String fechaInicio, String fechaFin) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("fecha/{0}/{1}", new Object[]{fechaInicio, fechaFin}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Encuentra recetas médicas por un rango de fechas (inicio y fin) en
     * formato JSON.
     *
     * @param responseType tipo de respuesta esperada.
     * @param fechaInicio fecha de inicio del rango.
     * @param fechaFin fecha de fin del rango.
     * @param <T> tipo de respuesta esperada.
     * @return las recetas médicas dentro del rango de fechas.
     * @throws WebApplicationException si ocurre un error durante la consulta.
     */
    @Override
    public <T> T encontrarRecetasPorFecha_JSON(GenericType<T> responseType, String fechaInicio, String fechaFin) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("fecha/{0}/{1}", new Object[]{fechaInicio, fechaFin}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Cierra la conexión del cliente REST.
     */
    @Override
    public void close() {
        client.close();
    }

}
