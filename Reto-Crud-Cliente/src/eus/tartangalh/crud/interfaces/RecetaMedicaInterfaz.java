/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import eus.tartangalh.crud.entidades.RecetaMedica;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author 2dam
 */
public interface RecetaMedicaInterfaz {
    
 
    public void eliminarRecetamedica(String id) throws WebApplicationException;
    public <T> T obtenerProductosPorReceta(GenericType<T> responseType, String id) throws WebApplicationException;
    public void modificarRecetaMedica_XML(Object requestEntity) throws WebApplicationException;
    public void modificarRecetaMedica_JSON(Object requestEntity) throws WebApplicationException;
    public <T> T encontrarRecetaPorId_XML(GenericType<T> responseType, String id) throws WebApplicationException;
    public <T> T encontrarRecetaPorId_JSON(GenericType<T> responseType, String id) throws WebApplicationException;
    public <T> T encontrarTodasLasRecetas_XML(GenericType<T> responseType) throws WebApplicationException;
    public <T> T encontrarTodasLasRecetas_JSON(GenericType<T> responseType) throws WebApplicationException ;
    public void crearRecetaMedica_XML(Object requestEntity, String id) throws WebApplicationException ;
    public void crearRecetaMedica_JSON(Object requestEntity, String id) throws WebApplicationException ;
    public <T> T encontrarRecetasPorFecha_XML(GenericType<T> responseType, String fechaInicio, String fechaFin) throws WebApplicationException;
    public <T> T encontrarRecetasPorFecha_JSON(GenericType<T> responseType, String fechaInicio, String fechaFin) throws WebApplicationException;
        public void close() ;

    

}