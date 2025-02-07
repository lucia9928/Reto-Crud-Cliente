/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

/**
 * Interfaz que define los métodos para interactuar con los productos farmacéuticos.
 * Incluye operaciones para crear, actualizar, eliminar y buscar productos, tanto en formato XML como JSON.
 * 
 * @author Oscar
 */
public interface ProductoInterfaz {

    /**
     * Crea un producto farmacéutico en formato XML.
     * 
     * @param requestEntity el objeto que contiene los datos del producto a crear.
     * @throws WebApplicationException si ocurre un error durante la creación.
     */
    public void crearProducto_XML(Object requestEntity) throws WebApplicationException;

    /**
     * Crea un producto farmacéutico en formato JSON.
     * 
     * @param requestEntity el objeto que contiene los datos del producto a crear.
     * @throws WebApplicationException si ocurre un error durante la creación.
     */
    public void crearProducto_JSON(Object requestEntity) throws WebApplicationException;

    /**
     * Elimina un producto farmacéutico por su ID.
     * 
     * @param id el identificador del producto a eliminar.
     * @throws WebApplicationException si ocurre un error durante la eliminación.
     */
    public void borrarProducto(String id) throws WebApplicationException;

    /**
     * Actualiza un producto farmacéutico en formato XML.
     * 
     * @param requestEntity el objeto con los nuevos datos del producto.
     * @throws WebApplicationException si ocurre un error durante la actualización.
     */
    public void actualizarProducto_XML(Object requestEntity) throws WebApplicationException;

    /**
     * Actualiza un producto farmacéutico en formato JSON.
     * 
     * @param requestEntity el objeto con los nuevos datos del producto.
     * @throws WebApplicationException si ocurre un error durante la actualización.
     */
    public void actualizarProducto_JSON(Object requestEntity) throws WebApplicationException;

    /**
     * Encuentra un producto por su ID en formato XML.
     * 
     * @param responseType el tipo de la respuesta esperada.
     * @param id el identificador del producto a encontrar.
     * @param <T> el tipo de la respuesta.
     * @return el producto encontrado.
     * @throws WebApplicationException si ocurre un error durante la búsqueda.
     */
    public <T> T encontrarProducto_XML(GenericType<T> responseType, String id) throws WebApplicationException;

    /**
     * Encuentra un producto por su ID en formato JSON.
     * 
     * @param responseType el tipo de la respuesta esperada.
     * @param id el identificador del producto a encontrar.
     * @param <T> el tipo de la respuesta.
     * @return el producto encontrado.
     * @throws WebApplicationException si ocurre un error durante la búsqueda.
     */
    public <T> T encontrarProducto_JSON(GenericType<T> responseType, String id) throws WebApplicationException;

    /**
     * Encuentra un producto por su nombre en formato XML.
     * 
     * @param responseType el tipo de la respuesta esperada.
     * @param nombre el nombre del producto a buscar.
     * @param <T> el tipo de la respuesta.
     * @return el producto encontrado.
     * @throws WebApplicationException si ocurre un error durante la búsqueda.
     */
    public <T> T encontrarPorNombre_XML(GenericType<T> responseType, String nombre) throws WebApplicationException;

    /**
     * Encuentra un producto por su nombre en formato JSON.
     * 
     * @param responseType el tipo de la respuesta esperada.
     * @param nombre el nombre del producto a buscar.
     * @param <T> el tipo de la respuesta.
     * @return el producto encontrado.
     * @throws WebApplicationException si ocurre un error durante la búsqueda.
     */
    public <T> T encontrarPorNombre_JSON(GenericType<T> responseType, String nombre) throws WebApplicationException;

    /**
     * Encuentra todos los productos en formato XML.
     * 
     * 
     * @param <T> el tipo de la respuesta.
     * @return la lista de productos.
     * @throws WebApplicationException si ocurre un error durante la búsqueda.
     */
    public <T> T encontrarTodos_XML(GenericType<T> responseTypee) throws WebApplicationException;

    /**
     * Encuentra todos los productos en formato JSON.
     * 
     * @param responseType el tipo de la respuesta esperada.
     * @param <T> el tipo de la respuesta.
     * @return la lista de productos.
     * @throws WebApplicationException si ocurre un error durante la búsqueda.
     */
    public <T> T encontrarTodos_JSON(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Encuentra productos por su fecha de caducidad en formato XML.
     * 
     * @param responseType el tipo de la respuesta esperada.
     * @param fechaLimite la fecha límite de caducidad para buscar los productos.
     * @param <T> el tipo de la respuesta.
     * @return la lista de productos que cumplen con la fecha de caducidad.
     * @throws WebApplicationException si ocurre un error durante la búsqueda.
     */
    public <T> T encontrarPorFechaCaducidad_XML(GenericType<T> responseType, String fechaLimite) throws WebApplicationException;

    /**
     * Encuentra productos por su fecha de caducidad en formato JSON.
     * 
     * @param responseType el tipo de la respuesta esperada.
     * @param fechaLimite la fecha límite de caducidad para buscar los productos.
     * @param <T> el tipo de la respuesta.
     * @return la lista de productos que cumplen con la fecha de caducidad.
     * @throws WebApplicationException si ocurre un error durante la búsqueda.
     */
    public <T> T encontrarPorFechaCaducidad_JSON(GenericType<T> responseType, String fechaLimite) throws WebApplicationException;
}