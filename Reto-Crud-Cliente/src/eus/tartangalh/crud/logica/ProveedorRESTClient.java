/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.logica;

import eus.tartangalh.crud.entidades.Proveedor;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import eus.tartangalh.crud.interfaces.ProveedorInterfaz;

/**
 * Jersey REST client generated for REST resource:ProveedorFacadeREST
 * [eus.tartangalh.crud.create.proveedor]<br>
 * USAGE:
 * <pre>
 *        ProveedorRESTClient client = new ProveedorRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author 2dam
 */
public class ProveedorRESTClient implements ProveedorInterfaz {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/Reto-crud-server/webresources";

    public ProveedorRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("eus.tartangalh.crud.create.proveedor");
    }

    @Override
    public void crearProveedor_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    @Override
    public void borrarProducto(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("proveedorPorId/{0}", new Object[]{id})).request().delete();
    }

    @Override
    public <T> T mostrarProveedor_XML(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("proveedorPorId/{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public void actualizarProveedor_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    @Override
    public <T> T mostrarTodosProveedores_XML(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T mostrarsProveedoresFecha_XML(GenericType<T> responseType, String fecha) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("mostrarProveedoresPorFecha/{0}", new Object[]{fecha}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public void close() {
        client.close();
    }

}
