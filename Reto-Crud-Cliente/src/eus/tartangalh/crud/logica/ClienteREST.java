/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.logica;

import archivo.AsymmetricCliente;
import eus.tartangalh.crud.entidades.Cliente;
import eus.tartangalh.crud.entidades.Trabajador;
import eus.tartangalh.crud.interfaces.ClienteInterfaz;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

/**
 * Jersey REST client generated for REST resource:ClienteFacadeREST
 * [eus.tartangalh.crud.create.cliente]<br>
 * USAGE:
 * <pre>
 *        ClienteREST client = new ClienteREST();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author melany
 */
public class ClienteREST implements ClienteInterfaz {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/Reto-crud-server/webresources";
    private static final Logger LOGGER = Logger.getLogger("ClienteREST.class");

    public ClienteREST() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("Cliente");
    }

    public void modificarCliente_XML(Object requestEntity, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    public void modificarCliente_JSON(Object requestEntity, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    public <T> T encontrarTodosLosClientes_XML(Class<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T encontrarTodosLosClientes_JSON(Class<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T encontrarTodosLosClientes_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T encontrarTodosLosClientes_JSON(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T encontrarPorId_XML(Class<T> responseType, String id) throws WebApplicationException {
        try {
            WebTarget resource = webTarget.path("id").path(id);
            LOGGER.log(Level.INFO, "URL de la solicitud: {0}", resource.getUri());

            Response response = resource.request(MediaType.APPLICATION_XML).get();

            int statusCode = response.getStatus();
            LOGGER.log(Level.INFO, "Código de estado HTTP: {0}", statusCode);

            if (statusCode == 404) {
                throw new WebApplicationException("No se encontró el trabajador con ID: " + id, Response.Status.NOT_FOUND);
            } else if (statusCode != 200) {
                throw new WebApplicationException("Error en la solicitud: " + response.readEntity(String.class), statusCode);
            }

            return response.readEntity(responseType);
        } catch (Exception ex) {
            throw new WebApplicationException("Error al buscar trabajador por ID: " + ex.getMessage());
        }
    }

    public <T> T encontrarPorId_JSON(Class<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void eliminarCliente(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    public void crearCliente_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    public void crearCliente_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    @Override
    public <T> T buscarCliente(GenericType<T> respuesta, String userEmail) throws WebApplicationException {
        try {
            WebTarget resource = webTarget.path("busqueda").path(userEmail); // Agrega la ruta correcta
            LOGGER.log(Level.INFO, "URL de la solicitud: {0}", resource.getUri());

            Response response = resource.request(MediaType.APPLICATION_XML).get();

            int statusCode = response.getStatus();
            LOGGER.log(Level.INFO, "Código de estado HTTP: {0}", statusCode);

            if (statusCode == 404) {
                throw new WebApplicationException("No se encontró ningún trabajador con el email: " + userEmail, Response.Status.NOT_FOUND);
            } else if (statusCode != 200) {
                throw new WebApplicationException("Error en la solicitud: " + response.readEntity(String.class), statusCode);
            }

            return response.readEntity(respuesta);
        } catch (Exception ex) {
            throw new WebApplicationException("Error al buscar trabajador: " + ex.getMessage());
        }
    }


    public void close() {
        client.close();
    }

    @Override
    public void resetarContrasena(Cliente cliente) throws WebApplicationException {
        try {
            Response response = webTarget.path("recoverPassword")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.entity(cliente, MediaType.APPLICATION_XML));

            if (response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
                String errorMessage = response.readEntity(String.class);
                throw new WebApplicationException("Error en servidor: " + errorMessage, response.getStatus());
            }
        } catch (ProcessingException e) {
            throw new WebApplicationException("Error de conexión con el servidor: " + e.getMessage());
        }
    }

    @Override
    public void actualizarContrasena(Cliente cliente) throws WebApplicationException {
        try {
            Response response = webTarget.path("editPassword")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.entity(cliente, MediaType.APPLICATION_XML));

            if (response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
                String errorMessage = response.readEntity(String.class);
                throw new WebApplicationException("Error en servidor: " + errorMessage, response.getStatus());
            }
        } catch (ProcessingException e) {
            throw new WebApplicationException("Error de conexión con el servidor: " + e.getMessage());
        }
    }

    @Override
    public <T> T iniciarSesion(Class<T> responseType, String Clidni, String contrasenaCli) throws WebApplicationException {
       try {
            LOGGER.log(Level.INFO, "Intentando iniciar sesion");
            WebTarget resource = webTarget;
            LOGGER.log(Level.INFO, "URL de la solicitud: {0}", resource.getUri());

            resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{Clidni, contrasenaCli}));
            Response response=resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get();
          //  int statusCode = resource.request().get().getStatus();
         //   LOGGER.log(Level.INFO, "Código de estado HTTP: {0}", statusCode);
            LOGGER.log(Level.INFO, "Código de estado HTTP: {0}", response.getStatus());
           // String responseContent = resource.request().get(String.class);
       //     LOGGER.log(Level.INFO, "Contenido de la respuesta: {0}", responseContent);

          //  return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
          return response.readEntity(responseType);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error durante el inicio de sesión", e);
            throw new WebApplicationException("User not found");
        }
    }

}
