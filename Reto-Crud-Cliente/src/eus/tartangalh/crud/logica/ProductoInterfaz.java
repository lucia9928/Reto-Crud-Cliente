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
public interface ProductoInterfaz {

    public void crearProducto_XML(Object requestEntity) throws WebApplicationException;

    public void crearProducto_JSON(Object requestEntity) throws WebApplicationException;

    public void borrarProducto(String id) throws WebApplicationException;

    public void actualizarProducto_XML(Object requestEntity) throws WebApplicationException;

    public void actualizarProducto_JSON(Object requestEntity) throws WebApplicationException;

    public <T> T encontrarProducto_XML(Class<T> responseType, String id) throws WebApplicationException;

    public <T> T encontrarProducto_JSON(Class<T> responseType, String id) throws WebApplicationException;

    public <T> T encontrarPorNombre_XML(Class<T> responseType, String nombre) throws WebApplicationException;

    public <T> T encontrarPorNombre_JSON(Class<T> responseType, String nombre) throws WebApplicationException;

    public <T> T encontrarTodos_XML(Class<T> responseType) throws WebApplicationException;

    public <T> T encontrarTodos_JSON(Class<T> responseType) throws WebApplicationException;

    public <T> T encontrarPorFechaCaducidad_XML(Class<T> responseType, String fechaLimite) throws WebApplicationException;

    public <T> T encontrarPorFechaCaducidad_JSON(Class<T> responseType, String fechaLimite) throws WebApplicationException;
}
