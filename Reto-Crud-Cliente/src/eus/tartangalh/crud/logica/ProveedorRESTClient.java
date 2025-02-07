/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.logica;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import eus.tartangalh.crud.interfaces.ProveedorInterfaz;
import java.util.ResourceBundle;

/**
 * Cliente REST generado para interactuar con el recurso REST de
 * ProveedorFacadeREST. Este cliente proporciona métodos para crear, modificar,
 * eliminar y consultar proveedores. Los métodos permiten trabajar con datos en
 * formato XML.
 *
 * USO:
 * <pre>
 *        ProveedorRESTClient client = new ProveedorRESTClient();
 *        Object response = client.XXX(...);
 *        // realizar alguna acción con la respuesta
 *        client.close();
 * </pre>
 *
 * @author Markel
 */
public class ProveedorRESTClient implements ProveedorInterfaz {

    private WebTarget webTarget;
    private Client client;
    private final ResourceBundle bundle = ResourceBundle.getBundle("archivo.URL");
    private final String BASE_URI = bundle.getString("BASE_URI");

    /**
     * Constructor que inicializa el cliente y configura la URL base del
     * servicio REST.
     */
    public ProveedorRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("eus.tartangalh.crud.create.proveedor");
    }

    /**
     * Crea un nuevo proveedor en formato XML.
     *
     * @param requestEntity objeto con los datos del proveedor a crear.
     * @throws ClientErrorException si ocurre un error durante la creación del
     * proveedor.
     */
    @Override
    public void crearProveedor_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Elimina un proveedor utilizando su ID.
     *
     * @param id el ID del proveedor a eliminar.
     * @throws ClientErrorException si ocurre un error durante la eliminación
     * del proveedor.
     */
    @Override
    public void borrarProveedor(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("proveedorPorId/{0}", new Object[]{id}))
                .request()
                .delete();
    }

    /**
     * Muestra los detalles de un proveedor en formato XML utilizando su ID.
     *
     * @param responseType tipo de respuesta esperada.
     * @param id el ID del proveedor.
     * @param <T> tipo de respuesta esperada.
     * @return los datos del proveedor.
     * @throws ClientErrorException si ocurre un error durante la consulta del
     * proveedor.
     */
    @Override
    public <T> T mostrarProveedor_XML(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("proveedorPorId/{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Actualiza los datos de un proveedor en formato XML.
     *
     * @param requestEntity objeto con los datos del proveedor a actualizar.
     * @throws ClientErrorException si ocurre un error durante la actualización
     * del proveedor.
     */
    @Override
    public void actualizarProveedor_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Muestra todos los proveedores en formato XML.
     *
     * @param responseType tipo de respuesta esperada.
     * @param <T> tipo de respuesta esperada.
     * @return la lista de todos los proveedores.
     * @throws ClientErrorException si ocurre un error durante la obtención de
     * los proveedores.
     */
    @Override
    public <T> T mostrarTodosProveedores_XML(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Muestra todos los proveedores que tienen productos o servicios
     * disponibles en una fecha específica en formato XML.
     *
     * @param responseType tipo de respuesta esperada.
     * @param fecha la fecha para filtrar proveedores por disponibilidad.
     * @param <T> tipo de respuesta esperada.
     * @return los proveedores con productos o servicios disponibles en la fecha
     * indicada.
     * @throws ClientErrorException si ocurre un error durante la consulta de
     * proveedores por fecha.
     */
    @Override
    public <T> T mostrarsProveedoresFecha_XML(GenericType<T> responseType, String fecha) throws ClientErrorException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("mostrarProveedoresPorFecha/{0}", new Object[]{fecha}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Cierra la conexión del cliente REST.
     */
    public void close() {
        client.close();
    }

}
