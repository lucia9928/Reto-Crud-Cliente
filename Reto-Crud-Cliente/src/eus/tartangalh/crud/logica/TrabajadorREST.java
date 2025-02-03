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
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
public <T> T iniciarSesion(GenericType<T> responseType, String Tradni, String contrasenaTra) throws WebApplicationException {
    try {
        LOGGER.log(Level.INFO, "Intentando iniciar sesión con DNI: {0}", Tradni);

        // Construcción segura de la URL con queryParams en lugar de incluir credenciales en la URL
        WebTarget resource = webTarget.path("iniciarSesion")
                                      .queryParam("Tradni", Tradni)
                                      .queryParam("contrasenaTra", contrasenaTra);

        LOGGER.log(Level.INFO, "URL de la solicitud: {0}", resource.getUri());

        // Ejecutar la petición una sola vez
        Response response = resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get();

        int statusCode = response.getStatus();
        LOGGER.log(Level.INFO, "Código de estado HTTP: {0}", statusCode);

        // Leer contenido de la respuesta antes de evaluar errores
        String responseContent = response.readEntity(String.class);
        LOGGER.log(Level.INFO, "Contenido de la respuesta: {0}", responseContent);

        // Manejo de errores HTTP
        if (statusCode == Response.Status.OK.getStatusCode()) {
            return response.readEntity(responseType);
        } else if (statusCode == Response.Status.UNAUTHORIZED.getStatusCode()) {
            throw new WebApplicationException("Credenciales incorrectas", Response.Status.UNAUTHORIZED);
        } else if (statusCode == Response.Status.NOT_FOUND.getStatusCode()) {
            throw new WebApplicationException("Usuario no encontrado", Response.Status.NOT_FOUND);
        } else if (statusCode >= 500) {
            throw new WebApplicationException("Error interno en el servidor: " + responseContent, Response.Status.INTERNAL_SERVER_ERROR);
        }

        throw new WebApplicationException("Error inesperado: " + responseContent, statusCode);

    } catch (ProcessingException e) {
        LOGGER.log(Level.SEVERE, "Error de comunicación con el servidor", e);
        throw new WebApplicationException("No se pudo conectar con el servidor", Response.Status.SERVICE_UNAVAILABLE);
    } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Error inesperado durante el inicio de sesión", e);
        throw new WebApplicationException("Error inesperado en el inicio de sesión", Response.Status.INTERNAL_SERVER_ERROR);
    }
}

}
