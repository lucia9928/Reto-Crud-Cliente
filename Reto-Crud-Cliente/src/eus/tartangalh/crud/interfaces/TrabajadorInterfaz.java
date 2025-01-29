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
 * @author melany
 */
public interface TrabajadorInterfaz {
    public void crearTrabajador_XML(Object requestEntity) throws WebApplicationException;
    public void crearTrabajador_JSON(Object requestEntity) throws WebApplicationException;
    public <T> T encontrarPorId_XML(Class<T> responseType, String id) throws WebApplicationException;
    public <T> T encontrarPorId_JSON(Class<T> responseType, String id) throws WebApplicationException;
    public void modificarTrabajador_XML(Object requestEntity, String id) throws WebApplicationException;
    public void modificarTrabajador_JSON(Object requestEntity, String id) throws WebApplicationException;
    public <T> T encontrarPorEmail_XML(GenericType<T> responseType, String Email) throws WebApplicationException;
    public <T> T encontrarPorEmail_JSON(GenericType<T> responseType, String Email) throws WebApplicationException;
    public void eliminarTrabajador(String id) throws WebApplicationException;
    public <T> T encontrarTodosLosTrabajdores_XML(GenericType<T> responseType) throws WebApplicationException ;
    public <T> T encontrarTodosLosTrabajdores_JSON(Class<T> responseType) throws WebApplicationException;
    public void close() ;
}
