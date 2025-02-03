/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.logica;
import eus.tartangalh.crud.entidades.Trabajador;
import eus.tartangalh.crud.interfaces.TrabajadorInterfaz;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 * Jersey REST client generated for REST resource:TrabajadorFacadeREST
 * [eus.tartangalh.crud.create.trabajador]<br>
 * USAGE:
 * <pre>
 *        TrabajadorREST client = new TrabajadorREST();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author melany
 */
public class TrabajadorREST implements TrabajadorInterfaz {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/Reto-crud-server/webresources";
    private static final Logger LOGGER = Logger.getLogger("TrabajadorREST.class");

    public TrabajadorREST() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("Trabajador");
    }

    public void crearTrabajador_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    public void crearTrabajador_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    public <T> T encontrarPorId_XML(Class<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T encontrarPorId_JSON(Class<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void modificarTrabajador_XML(Object requestEntity, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    public void modificarTrabajador_JSON(Object requestEntity, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    public void eliminarTrabajador(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    public <T> T encontrarTodosLosTrabajdores_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T encontrarTodosLosTrabajdores_JSON(Class<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }
    
    @Override
    public <T> T buscarTrabajador(GenericType<T> respuesta, String userEmail) throws WebApplicationException {
        try {
            WebTarget resource = webTarget;
            LOGGER.info("Intentnado buscar cliente");
            LOGGER.log(Level.INFO, "URL de la solicitud: {0}", resource.getUri());
            int statusCode = resource.request().get().getStatus();
            LOGGER.log(Level.INFO, "Código de estado HTTP: {0}", statusCode);
            String responseContent = resource.request().get(String.class);
            LOGGER.log(Level.INFO, "Contenido de la respuesta: {0}", responseContent);
            resource = resource.path(java.text.MessageFormat.format("busqueda/{0}", new Object[]{userEmail}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(respuesta);
        } catch (Exception ex) {
            throw new WebApplicationException("No se encontro ningun cliente.");
        }
    }

    public void close() {
        client.close();
    }

    @Override
    public void resetarContrasena(Trabajador trabajador) throws WebApplicationException {
        try {
            webTarget.path("recoverPassword").request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(trabajador, javax.ws.rs.core.MediaType.APPLICATION_XML), Trabajador.class);
        } catch (Exception ex) {
            throw new WebApplicationException("An error occurred while trying to edit the clients password:" + ex.getMessage());
        }

    }

    @Override
    public void actualizarContrasena(Trabajador trabajador) throws WebApplicationException {
        try {
            webTarget.path("editPassword").request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(trabajador, javax.ws.rs.core.MediaType.APPLICATION_XML), Trabajador.class);
        } catch (Exception ex) {
            throw new WebApplicationException("An error occurred while trying to edit the clients password:" + ex.getMessage());
        }
    }

    @Override
    public <T> T iniciarSesion(GenericType<T> responseType, String dni, String passwd) throws WebApplicationException {
        try {
            LOGGER.log(Level.INFO, "Intentando iniciar sesion");
            WebTarget resource = webTarget;
            LOGGER.log(Level.INFO, "URL de la solicitud: {0}", resource.getUri());

            resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{dni, passwd}));
            int statusCode = resource.request().get().getStatus();
            LOGGER.log(Level.INFO, "Código de estado HTTP: {0}", statusCode);
            String responseContent = resource.request().get(String.class);
            LOGGER.log(Level.INFO, "Contenido de la respuesta: {0}", responseContent);

            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error durante el inicio de sesión", e);
            throw new WebApplicationException("User not found");
        }
    }

}
