/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

/**
 * Interfaz que define las operaciones relacionadas con la gestión de almacenes.
 * Esta interfaz proporciona métodos para realizar operaciones CRUD (crear, leer, actualizar, eliminar) 
 * sobre los almacenes, así como para obtener todos los almacenes en formato XML o JSON.
 * 
 * @author Andoni
 */
public interface AlmacenInterfaz {

    /**
     * Crea un nuevo almacén enviando los datos en formato XML.
     * 
     * @param requestEntity los datos del almacén a crear.
     * @throws WebApplicationException si ocurre un error durante la operación.
     */
    public void crearAlmacen_XML(Object requestEntity) throws WebApplicationException;

    /**
     * Crea un nuevo almacén enviando los datos en formato JSON.
     * 
     * @param requestEntity los datos del almacén a crear.
     * @throws WebApplicationException si ocurre un error durante la operación.
     */
    public void crearAlmacen_JSON(Object requestEntity) throws WebApplicationException;

    /**
     * Encuentra un almacén por su ID y lo devuelve en formato XML.
     * 
     * @param responseType la clase de respuesta para el tipo genérico.
     * @param id el identificador del almacén a buscar.
     * @param <T> el tipo de respuesta.
     * @return los datos del almacén en formato XML.
     * @throws WebApplicationException si ocurre un error durante la operación.
     */
    public <T> T encontrar_XML(Class<T> responseType, String id) throws WebApplicationException;

    /**
     * Encuentra un almacén por su ID y lo devuelve en formato JSON.
     * 
     * @param responseType la clase de respuesta para el tipo genérico.
     * @param id el identificador del almacén a buscar.
     * @param <T> el tipo de respuesta.
     * @return los datos del almacén en formato JSON.
     * @throws WebApplicationException si ocurre un error durante la operación.
     */
    public <T> T encontrar_JSON(Class<T> responseType, String id) throws WebApplicationException;

    /**
     * Elimina un almacén por su ID.
     * 
     * @param id el identificador del almacén a eliminar.
     * @throws WebApplicationException si ocurre un error durante la operación.
     */
    public void borrarAlmacen(String id) throws WebApplicationException;

    /**
     * Actualiza los datos de un almacén enviando la información en formato XML.
     * 
     * @param requestEntity los datos actualizados del almacén.
     * @throws WebApplicationException si ocurre un error durante la operación.
     */
    public void actualizarAlmacen_XML(Object requestEntity) throws WebApplicationException;

    /**
     * Actualiza los datos de un almacén enviando la información en formato JSON.
     * 
     * @param requestEntity los datos actualizados del almacén.
     * @throws WebApplicationException si ocurre un error durante la operación.
     */
    public void actualizarAlmacen_JSON(Object requestEntity) throws WebApplicationException;

    /**
     * Encuentra todos los almacenes y los devuelve en formato XML utilizando un tipo genérico.
     * 
     * 
     * @param <T> el tipo de respuesta.
     * @return una lista de todos los almacenes en formato XML.
     * @throws WebApplicationException si ocurre un error durante la operación.
     */
    public <T> T findAll_XML(GenericType<T> responseTypee) throws WebApplicationException;

    /**
     * Encuentra todos los almacenes y los devuelve en formato JSON utilizando un tipo genérico.
     * 
     * @param responseType el tipo genérico de respuesta.
     * @param <T> el tipo de respuesta.
     * @return una lista de todos los almacenes en formato JSON.
     * @throws WebApplicationException si ocurre un error durante la operación.
     */
    public <T> T findAll_JSON(GenericType<T> responseType) throws WebApplicationException;
}