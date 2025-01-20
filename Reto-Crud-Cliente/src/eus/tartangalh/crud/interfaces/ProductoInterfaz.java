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
 * @author Oscar
 */
public interface ProductoInterfaz {

    public void crearProducto_XML(Object requestEntity) throws WebApplicationException;

    public void crearProducto_JSON(Object requestEntity) throws WebApplicationException;

    public void borrarProducto(String id) throws WebApplicationException;

    public void actualizarProducto_XML(Object requestEntity) throws WebApplicationException;

    public void actualizarProducto_JSON(Object requestEntity) throws WebApplicationException;

    public <T> T encontrarProducto_XML(GenericType<T> responseType, String id) throws WebApplicationException;

    public <T> T encontrarProducto_JSON(GenericType<T> responseType, String id) throws WebApplicationException;

    public <T> T encontrarPorNombre_XML(GenericType<T> responseType, String nombre) throws WebApplicationException;

    public <T> T encontrarPorNombre_JSON(GenericType<T> responseType, String nombre) throws WebApplicationException;

    public <T> T encontrarTodos_XML(GenericType<T> responseTypee) throws WebApplicationException;

    public <T> T encontrarTodos_JSON(GenericType<T> responseType) throws WebApplicationException;

    public <T> T encontrarPorFechaCaducidad_XML(GenericType<T> responseType, String fechaLimite) throws WebApplicationException;

    public <T> T encontrarPorFechaCaducidad_JSON(GenericType<T> responseType, String fechaLimite) throws WebApplicationException;
}
