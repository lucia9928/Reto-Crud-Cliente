/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.logica;

import eus.tartangalh.crud.interfaces.ProveedorManager;
import eus.tartangalh.crud.entidades.Proveedor;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;



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
public class ProveedorRESTClient implements ProveedorManager{

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/Reto-Crud-Server/webresources";

    public ProveedorRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("eus.tartangalh.crud.create.proveedor");
    }

    public void crearProveedor_XML(Object requestEntity, String proveedor) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{proveedor})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }
/*
  *  public void crearProveedor_JSON(Object requestEntity, String proveedor) throws ClientErrorException {
   *     webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{proveedor})).request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    *}
*/
    public void borrarProveedor(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    public <T> T mostrarProveedor_XML(Class<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

  
    public void actualizarProveedor_XML(Object requestEntity, String proveedor) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{proveedor})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Proveedor.class);
    }

    

    public <T> T mostrarTodosProveedores_XML(Class<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }


    public <T> T mostrarsProveedoresFecha_XML(Class<T> responseType, String fecha) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("mostrarsProveedoresFecha/{0}", new Object[]{fecha}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

 

    public void close() {
        client.close();
    }
    
}
