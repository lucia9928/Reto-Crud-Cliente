/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.logica;

import eus.tartangalh.crud.entidades.Trabajador;
import eus.tartangalh.crud.interfaces.TrabajadorInterfaz;
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
 * TrabajadorFacadeREST. Este cliente permite realizar operaciones CRUD sobre la
 * entidad Trabajador, incluyendo la creación, modificación, eliminación y
 * consulta de trabajadores.
 *
 * USO:
 * <pre>
 *        TrabajadorREST client = new TrabajadorREST();
 *        Object response = client.XXX(...);
 *        // realizar alguna acción con la respuesta
 *        client.close();
 * </pre>
 *
 * @author melany
 */
public class TrabajadorREST implements TrabajadorInterfaz {

    private WebTarget webTarget;
    private Client client;
    private final ResourceBundle bundle = ResourceBundle.getBundle("archivo.URL");
    private final String BASE_URI = bundle.getString("BASE_URI");
    private static final Logger LOGGER = Logger.getLogger("TrabajadorREST.class");

    /**
     * Constructor que inicializa el cliente REST y la URL base para acceder al
     * servicio de trabajadores.
     */
    public TrabajadorREST() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("Trabajador");
    }

    /**
     * Crea un nuevo trabajador utilizando formato XML.
     *
     * @param requestEntity objeto que contiene los datos del trabajador a
     * crear.
     * @throws WebApplicationException si ocurre un error durante la creación.
     */
    public void crearTrabajador_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Crea un nuevo trabajador utilizando formato JSON.
     *
     * @param requestEntity objeto que contiene los datos del trabajador a
     * crear.
     * @throws WebApplicationException si ocurre un error durante la creación.
     */
    public void crearTrabajador_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Encuentra un trabajador por su ID utilizando formato XML.
     *
     * @param responseType tipo de respuesta esperada.
     * @param id el ID del trabajador a buscar.
     * @param <T> tipo de respuesta esperada.
     * @return el trabajador correspondiente al ID.
     * @throws WebApplicationException si ocurre un error durante la búsqueda o
     * si el trabajador no es encontrado.
     */
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

    /**
     * Encuentra un trabajador por su ID utilizando formato JSON.
     *
     * @param responseType tipo de respuesta esperada.
     * @param id el ID del trabajador a buscar.
     * @param <T> tipo de respuesta esperada.
     * @return el trabajador correspondiente al ID.
     * @throws WebApplicationException si ocurre un error durante la búsqueda.
     */
    public <T> T encontrarPorId_JSON(Class<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Modifica un trabajador utilizando formato XML.
     *
     * @param requestEntity objeto con los nuevos datos del trabajador.
     * @param id el ID del trabajador a modificar.
     * @throws WebApplicationException si ocurre un error durante la
     * modificación.
     */
    public void modificarTrabajador_XML(Object requestEntity, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Modifica un trabajador utilizando formato JSON.
     *
     * @param requestEntity objeto con los nuevos datos del trabajador.
     * @param id el ID del trabajador a modificar.
     * @throws WebApplicationException si ocurre un error durante la
     * modificación.
     */
    public void modificarTrabajador_JSON(Object requestEntity, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Elimina un trabajador por su ID.
     *
     * @param id el ID del trabajador a eliminar.
     * @throws WebApplicationException si ocurre un error durante la
     * eliminación.
     */
    public void eliminarTrabajador(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     * Encuentra todos los trabajadores utilizando formato XML.
     *
     * @param responseType tipo de respuesta esperada.
     * @param <T> tipo de respuesta esperada.
     * @return la lista de todos los trabajadores.
     * @throws WebApplicationException si ocurre un error durante la consulta.
     */
    public <T> T encontrarTodosLosTrabajdores_XML(GenericType<T> responseType) throws WebApplicationException {
        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Encuentra todos los trabajadores utilizando formato JSON.
     *
     * @param responseType tipo de respuesta esperada.
     * @param <T> tipo de respuesta esperada.
     * @return la lista de todos los trabajadores.
     * @throws WebApplicationException si ocurre un error durante la consulta.
     */
    public <T> T encontrarTodosLosTrabajdores_JSON(Class<T> responseType) throws WebApplicationException {
        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Busca un trabajador por su correo electrónico.
     *
     * @param respuesta el tipo de respuesta esperado.
     * @param userEmail el correo electrónico del trabajador a buscar.
     * @param <T> tipo de respuesta esperada.
     * @return el trabajador encontrado.
     * @throws WebApplicationException si ocurre un error durante la búsqueda.
     */
    public <T> T buscarTrabajador(GenericType<T> respuesta, String userEmail) throws WebApplicationException {
        try {
            WebTarget resource = webTarget.path("busqueda").path(userEmail);
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

    /**
     * Cierra la conexión del cliente REST.
     */
    public void close() {
        client.close();
    }

    /**
     * Resetea la contraseña de un trabajador.
     *
     * @param trabajador el trabajador cuyo password será reseteado.
     * @throws WebApplicationException si ocurre un error durante el proceso.
     */
    @Override
    public void resetarContrasena(Trabajador trabajador) throws WebApplicationException {
        try {
            Response response = webTarget.path("recoverPassword")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.entity(trabajador, MediaType.APPLICATION_XML));

            if (response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
                String errorMessage = response.readEntity(String.class);
                throw new WebApplicationException("Error en servidor: " + errorMessage, response.getStatus());
            }
        } catch (ProcessingException e) {
            throw new WebApplicationException("Error de conexión con el servidor: " + e.getMessage());
        }
    }

    /**
     * Actualiza la contraseña de un trabajador.
     *
     * @param trabajador el trabajador cuya contraseña será actualizada.
     * @throws WebApplicationException si ocurre un error durante el proceso.
     */
    @Override
    public void actualizarContrasena(Trabajador trabajador) throws WebApplicationException {
        try {
            Response response = webTarget.path("editPassword")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.entity(trabajador, MediaType.APPLICATION_XML));

            if (response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
                String errorMessage = response.readEntity(String.class);
                throw new WebApplicationException("Error en servidor: " + errorMessage, response.getStatus());
            }
        } catch (ProcessingException e) {
            throw new WebApplicationException("Error de conexión con el servidor: " + e.getMessage());
        }
    }

    /**
     * Inicia sesión con el DNI y la contraseña del trabajador.
     *
     * @param responseType tipo de respuesta esperada.
     * @param dniTra el DNI del trabajador.
     * @param contrasenaTra la contraseña del trabajador.
     * @param <T> tipo de respuesta esperada.
     * @return la respuesta del servidor al intentar iniciar sesión.
     * @throws WebApplicationException si ocurre un error durante el inicio de
     * sesión.
     */
    @Override
    public <T> T iniciarSesion(GenericType<T> responseType, String dniTra, String contrasenaTra) throws WebApplicationException {
        try {
            LOGGER.log(Level.INFO, "Intentando iniciar sesión");

            WebTarget resource = webTarget.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{dniTra, contrasenaTra}));
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
