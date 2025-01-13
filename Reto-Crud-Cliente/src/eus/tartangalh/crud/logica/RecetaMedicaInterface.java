/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.logica;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author 2dam
 */
public interface RecetaMedicaInterface {
    

    public void eliminarRecetamedica(String id) throws WebApplicationException ;
    public void modificarRecetaMedica_XML(Object requestEntity, String id) throws WebApplicationException;

    public void modificarRecetaMedica_JSON(Object requestEntity, String id) throws WebApplicationException ;
    

    public <T> T encontrarRecetaPorId_XML(Class<T> responseType, String id) throws WebApplicationException;
    public <T> T encontrarRecetaPorId_JSON(Class<T> responseType, String id) throws WebApplicationException;

    public <T> T encontrarTodasLasRecetas_XML(Class<T> responseType) throws WebApplicationException;

    public <T> T encontrarTodasLasRecetas_JSON(Class<T> responseType) throws WebApplicationException;
    public void crearRecetaMedica_XML(Object requestEntity) throws WebApplicationException;

    public void crearRecetaMedica_JSON(Object requestEntity) throws WebApplicationException ;
    public void close();
}
