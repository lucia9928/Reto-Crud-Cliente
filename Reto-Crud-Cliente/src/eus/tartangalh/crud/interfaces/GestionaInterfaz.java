/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Interfaz que define las operaciones relacionadas con la gestión de productos por parte de los trabajadores.
 * Esta interfaz proporciona métodos para realizar operaciones sobre la relación de productos gestionados,
 * como la creación, lectura, actualización y eliminación de los productos gestionados por un trabajador específico.
 * 
 * @author Markel
 */
public interface GestionaInterfaz {

    /**
     * Actualiza la información de un producto gestionado enviando los datos en formato XML.
     * 
     * @param requestEntity los datos actualizados del producto gestionado.
     */
    public void actualizarGestiona_XML(Object requestEntity);

    /**
     * Actualiza la información de un producto gestionado enviando los datos en formato JSON.
     * 
     * @param requestEntity los datos actualizados del producto gestionado.
     */
    public void actualizarGestiona_JSON(Object requestEntity);

    /**
     * Encuentra la gestión de un producto por su ID y el DNI del trabajador, y lo devuelve en formato XML.
     * 
     * @param responseType la clase de respuesta para el tipo genérico.
     * @param dni el DNI del trabajador.
     * @param idProducto el identificador del producto gestionado.
     * @param <T> el tipo de respuesta.
     * @return los datos del producto gestionado en formato XML.
     */
    public <T> T encontrarGestiona_XML(Class<T> responseType, String dni, String idProducto);

    /**
     * Encuentra la gestión de un producto por su ID y el DNI del trabajador, y lo devuelve en formato JSON.
     * 
     * @param responseType la clase de respuesta para el tipo genérico.
     * @param dni el DNI del trabajador.
     * @param idProducto el identificador del producto gestionado.
     * @param <T> el tipo de respuesta.
     * @return los datos del producto gestionado en formato JSON.
     */
    public <T> T encontrarGestiona_JSON(Class<T> responseType, String dni, String idProducto);

    /**
     * Encuentra todas las gestiones de productos y las devuelve en formato XML utilizando un tipo genérico.
     * 
     * @param responseType el tipo genérico de respuesta.
     * @param <T> el tipo de respuesta.
     * @return una lista de todos los productos gestionados en formato XML.
     */
    public <T> T mostrarTodosGestiona_XML(GenericType<T> responseType);

    /**
     * Encuentra todas las gestiones de productos y las devuelve en formato JSON.
     * 
     * @param responseType la clase de respuesta para el tipo genérico.
     * @param <T> el tipo de respuesta.
     * @return una lista de todos los productos gestionados en formato JSON.
     */
    public <T> T mostrarTodosGestiona_JSON(Class<T> responseType);

    /**
     * Crea una nueva gestión de un producto y la envía en formato XML.
     * 
     * @param requestEntity los datos del producto a gestionar.
     */
    public void crearGestiona_XML(Object requestEntity);

    /**
     * Crea una nueva gestión de un producto y la envía en formato JSON.
     * 
     * @param requestEntity los datos del producto a gestionar.
     */
    public void crearGestiona_JSON(Object requestEntity);

    /**
     * Elimina la gestión de un producto por su ID y el DNI del trabajador.
     * 
     * @param dni el DNI del trabajador.
     * @param idProducto el identificador del producto a eliminar de la gestión.
     */
    public void borrarGestiona(String dni, String idProducto);
}
