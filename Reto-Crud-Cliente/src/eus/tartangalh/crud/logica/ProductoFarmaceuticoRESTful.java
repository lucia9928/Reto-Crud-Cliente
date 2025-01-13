/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.logica;

import eus.tartangalh.crud.interfaces.ProductoInterfaz;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 * Jersey REST client generated for REST resource:ProductoFarmaceuticoFacadeREST
 * [eus.tartangalh.crud.create.productofarmaceutico]<br>
 * USAGE:
 * <pre>
 *        ProductoFarmaceuticoRESTful client = new ProductoFarmaceuticoRESTful();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Oscar
 */

/*
*Cambiar las exceptiones WebApplicationException
*
*/
public class ProductoFarmaceuticoRESTful implements ProductoInterfaz{

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/Reto-Crud-Server/webresources";

    public ProductoFarmaceuticoRESTful() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("eus.tartangalh.crud.create.productofarmaceutico");
    }

    public void crearProducto_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(MediaType.APPLICATION_XML)
                .post(Entity.entity(requestEntity, MediaType.APPLICATION_XML));
    }

    public void crearProducto_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(requestEntity, MediaType.APPLICATION_JSON));
    }

    public void borrarProducto(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", id))
                .request()
                .delete();
    }

    public void actualizarProducto_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(MediaType.APPLICATION_XML)
                .put(Entity.entity(requestEntity, MediaType.APPLICATION_XML));
    }

    public void actualizarProducto_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(requestEntity, MediaType.APPLICATION_JSON));
    }

    public <T> T encontrarProducto_XML(GenericType<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("{0}", id));
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T encontrarProducto_JSON(GenericType<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("{0}", id));
        return resource.request(MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T encontrarPorNombre_XML(GenericType<T> responseType, String nombre) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("nombre/{0}", nombre));
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T encontrarPorNombre_JSON(GenericType<T> responseType, String nombre) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("nombre/{0}", nombre));
        return resource.request(MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T encontrarTodos_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T encontrarTodos_JSON(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T encontrarPorFechaCaducidad_XML(GenericType<T> responseType, String fechaLimite) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("caducidad/{0}", fechaLimite));
        return resource.request(MediaType.APPLICATION_XML).get(responseType);
    }

    public <T> T encontrarPorFechaCaducidad_JSON(GenericType<T> responseType, String fechaLimite) throws WebApplicationException {
        WebTarget resource = webTarget.path(java.text.MessageFormat.format("caducidad/{0}", fechaLimite));
        return resource.request(MediaType.APPLICATION_JSON).get(responseType);
    }



    public void close() {
        client.close();
    }
    
}
