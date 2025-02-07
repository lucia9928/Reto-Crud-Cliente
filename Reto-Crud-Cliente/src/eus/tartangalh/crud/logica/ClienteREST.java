/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.logica;

import eus.tartangalh.crud.entidades.Cliente;
import eus.tartangalh.crud.interfaces.ClienteInterfaz;
import java.util.ResourceBundle;
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

/**
 * Cliente REST generado para interactuar con el recurso REST de
 * ClienteFacadeREST.
 *
 * Este cliente proporciona métodos para crear, modificar, eliminar y buscar
 * clientes. Los métodos permiten trabajar con datos en formato XML o JSON,
 * según sea necesario.
 *
 * USO:
 * <pre>
 *        ClienteREST client = new ClienteREST();
 *        Object response = client.XXX(...);
 *        // hacer lo que sea necesario con la respuesta
 *        client.close();
 * </pre>
 *
 * @author melany
 */
public class ClienteREST implements ClienteInterfaz {

    private WebTarget webTarget;
    private Client client;
    private final ResourceBundle bundle = ResourceBundle.getBundle("archivo.URL");
    private final String BASE_URI = bundle.getString("BASE_URI");
    private static final Logger LOGGER = Logger.getLogger("ClienteREST.class");

    /**
     * Constructor que inicializa el cliente y configura la URL base del
     * servicio.
     */
    public ClienteREST() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("Cliente");
    }

    /**
     * Modifica un cliente en formato XML.
     *
     * @param requestEntity objeto con los datos del cliente a modificar.
     * @param id identificador del cliente a modificar.
     * @throws WebApplicationException si ocurre un error durante la
     * modificación del cliente.
     */
    public void modificarCliente_XML(Object requestEntity, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Modifica un cliente en formato JSON.
     *
     * @param requestEntity objeto con los datos del cliente a modificar.
     * @param id identificador del cliente a modificar.
     * @throws WebApplicationException si ocurre un error durante la
     * modificación del cliente.
     */
    public void modificarCliente_JSON(Object requestEntity, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Encuentra todos los clientes en formato XML.
     *
     * @param responseType tipo de respuesta esperada.
     * @param <T> tipo de respuesta esperada.
     * @return lista de clientes.
     * @throws WebApplicationException si ocurre un error al obtener los
     * clientes.
     */
    public <T> T encontrarTodosLosClientes_XML(Class<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Encuentra todos los clientes en formato JSON.
     *
     * @param responseType tipo de respuesta esperada.
     * @param <T> tipo de respuesta esperada.
     * @return lista de clientes.
     * @throws WebApplicationException si ocurre un error al obtener los
     * clientes.
     */
    public <T> T encontrarTodosLosClientes_JSON(Class<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Encuentra todos los clientes en formato XML, utilizando un tipo genérico
     * de respuesta.
     *
     * @param responseType tipo genérico de respuesta esperada.
     * @param <T> tipo de respuesta esperada.
     * @return lista de clientes.
     * @throws WebApplicationException si ocurre un error al obtener los
     * clientes.
     */
    public <T> T encontrarTodosLosClientes_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Encuentra todos los clientes en formato JSON, utilizando un tipo genérico
     * de respuesta.
     *
     * @param responseType tipo genérico de respuesta esperada.
     * @param <T> tipo de respuesta esperada.
     * @return lista de clientes.
     * @throws WebApplicationException si ocurre un error al obtener los
     * clientes.
     */
    public <T> T encontrarTodosLosClientes_JSON(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Encuentra un cliente por su ID en formato XML.
     *
     * @param responseType tipo de respuesta esperada.
     * @param id identificador del cliente.
     * @param <T> tipo de respuesta esperada.
     * @return cliente encontrado.
     * @throws WebApplicationException si ocurre un error al buscar el cliente.
     */
    public <T> T encontrarPorId_XML(Class<T> responseType, String id) throws WebApplicationException {
        try {
            WebTarget resource = webTarget.path("id").path(id);
            LOGGER.log(Level.INFO, "URL de la solicitud: {0}", resource.getUri());

            Response response = resource.request(MediaType.APPLICATION_XML).get();

            int statusCode = response.getStatus();
            LOGGER.log(Level.INFO, "Código de estado HTTP: {0}", statusCode);

            if (statusCode == 404) {
                throw new WebApplicationException("No se encontró el cliente con ID: " + id, Response.Status.NOT_FOUND);
            } else if (statusCode != 200) {
                throw new WebApplicationException("Error en la solicitud: " + response.readEntity(String.class), statusCode);
            }

            return response.readEntity(responseType);
        } catch (Exception ex) {
            throw new WebApplicationException("Error al buscar cliente por ID: " + ex.getMessage());
        }
    }

    /**
     * Encuentra un cliente por su ID en formato JSON.
     *
     * @param responseType tipo de respuesta esperada.
     * @param id identificador del cliente.
     * @param <T> tipo de respuesta esperada.
     * @return cliente encontrado.
     * @throws WebApplicationException si ocurre un error al buscar el cliente.
     */
    public <T> T encontrarPorId_JSON(Class<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Elimina un cliente por su ID.
     *
     * @param id identificador del cliente a eliminar.
     * @throws WebApplicationException si ocurre un error al eliminar el
     * cliente.
     */
    public void eliminarCliente(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     * Crea un nuevo cliente en formato XML.
     *
     * @param requestEntity objeto con los datos del cliente a crear.
     * @throws WebApplicationException si ocurre un error durante la creación
     * del cliente.
     */
    public void crearCliente_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Crea un nuevo cliente en formato JSON.
     *
     * @param requestEntity objeto con los datos del cliente a crear.
     * @throws WebApplicationException si ocurre un error durante la creación
     * del cliente.
     */
    public void crearCliente_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Realiza una búsqueda de cliente por su email.
     *
     * @param respuesta tipo de respuesta esperada.
     * @param userEmail email del cliente a buscar.
     * @param <T> tipo de respuesta esperada.
     * @return cliente encontrado.
     * @throws WebApplicationException si ocurre un error durante la búsqueda
     * del cliente.
     */
    @Override
    public <T> T buscarCliente(GenericType<T> respuesta, String userEmail) throws WebApplicationException {
        try {
            WebTarget resource = webTarget.path("busqueda").path(userEmail); // Agrega la ruta correcta
            LOGGER.log(Level.INFO, "URL de la solicitud: {0}", resource.getUri());

            Response response = resource.request(MediaType.APPLICATION_XML).get();

            int statusCode = response.getStatus();
            LOGGER.log(Level.INFO, "Código de estado HTTP: {0}", statusCode);

            if (statusCode == 404) {
                throw new WebApplicationException("No se encontró ningún cliente con el email: " + userEmail, Response.Status.NOT_FOUND);
            } else if (statusCode != 200) {
                throw new WebApplicationException("Error en la solicitud: " + response.readEntity(String.class), statusCode);
            }

            return response.readEntity(respuesta);
        } catch (Exception ex) {
            throw new WebApplicationException("Error al buscar cliente: " + ex.getMessage());
        }
    }

    /**
     * Cierra la conexión del cliente REST.
     */
    public void close() {
        client.close();
    }

    /**
     * Restablece la contraseña de un cliente.
     *
     * @param cliente objeto con los datos del cliente.
     * @throws WebApplicationException si ocurre un error al restablecer la
     * contraseña.
     */
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

    /**
     * Actualiza la contraseña de un cliente.
     *
     * @param cliente objeto con los datos del cliente.
     * @throws WebApplicationException si ocurre un error al actualizar la
     * contraseña.
     */
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

    /**
     * Inicia sesión para un cliente con su DNI y contraseña.
     *
     * @param responseType tipo de respuesta esperada.
     * @param Clidni DNI del cliente.
     * @param contrasenaCli contraseña del cliente.
     * @param <T> tipo de respuesta esperada.
     * @return datos de la sesión iniciada.
     * @throws WebApplicationException si ocurre un error durante el inicio de
     * sesión.
     */
    @Override
    public <T> T iniciarSesion(GenericType<T> responseType, String Clidni, String contrasenaCli) throws WebApplicationException {
        try {
            LOGGER.log(Level.INFO, "Intentando iniciar sesión");
            WebTarget resource = webTarget;
            LOGGER.log(Level.INFO, "URL de la solicitud: {0}", resource.getUri());

            resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{Clidni, contrasenaCli}));
            int statusCode = resource.request().get().getStatus();
            LOGGER.log(Level.INFO, "Código de estado HTTP: {0}", statusCode);
            String responseContent = resource.request().get(String.class);
            LOGGER.log(Level.INFO, "Contenido de la respuesta: {0}", responseContent);

            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error durante el inicio de sesión", e);
            throw new WebApplicationException("Usuario no encontrado");
        }
    }

}
