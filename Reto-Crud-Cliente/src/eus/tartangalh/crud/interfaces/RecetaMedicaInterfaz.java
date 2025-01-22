/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author 2dam
 */
public interface RecetaMedicaInterfaz {
    

    public void eliminarRecetamedica(String id) throws WebApplicationException ;
    public void modificarRecetaMedica_XML(Object requestEntity, String id) throws WebApplicationException;
    public void modificarRecetaMedica_JSON(Object requestEntity, String id) throws WebApplicationException ;
    public <T> T encontrarRecetaPorId_XML(Class<T> responseType, String id) throws WebApplicationException;
    public <T> T encontrarRecetaPorId_JSON(Class<T> responseType, String id) throws WebApplicationException;

    /**
     *
     * @param <T>
     * @param responseType
     * @param genericType
     * @return
     * @throws WebApplicationException
     */
    public <T> T  encontrarTodasLasRecetas_XML(GenericType<T> responseType) throws WebApplicationException;
    public <T> T encontrarTodasLasRecetas_JSON(Class<T> responseType) throws WebApplicationException;
    public void crearRecetaMedica_XML(Object requestEntity) throws WebApplicationException;
    public void crearRecetaMedica_JSON(Object requestEntity) throws WebApplicationException ;
    public void close();
}
