/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.logica;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import eus.tartangalh.crud.interfaces.RecetaMedicaInterfaz;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:RecetaMedicaFacadeREST
 * [eus.tartangalh.crud.create.recetamedica]<br>
 * USAGE:
 * <pre>
        RecetaMedicaREST client = new RecetaMedicaREST();
        Object response = client.XXX(...);
        // do whatever with response
        client.close();
 </pre>
 *
 * @author melany
 */
public class RecetaMedicaREST implements RecetaMedicaInterfaz{

    private final WebTarget webTarget;
    private final Client client;
    private static final String BASE_URI = "http://localhost:8080/Reto-crud-server/webresources";

    public RecetaMedicaREST() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("Receta_Medica");
    }

    @Override
    public void eliminarRecetamedica(String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    @Override
    public void modificarRecetaMedica_XML(Object requestEntity, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id}))
                .request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                .put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    @Override
    public void modificarRecetaMedica_JSON(Object requestEntity, String id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    @Override
    public <T> T encontrarRecetaPorId_XML(Class<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T encontrarRecetaPorId_JSON(Class<T> responseType, String id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public <T> T encontrarTodasLasRecetas_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
          return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }


    @Override
    public <T> T encontrarTodasLasRecetas_JSON(Class<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public void crearRecetaMedica_XML(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    @Override
    public void crearRecetaMedica_JSON(Object requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    @Override
    public void close() {
        client.close();
    }

  
    
}
