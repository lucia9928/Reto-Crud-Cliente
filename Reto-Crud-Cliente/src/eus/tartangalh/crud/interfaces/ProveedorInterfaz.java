/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 * Interfaz que define los métodos para interactuar con el servicio relacionado con proveedores.
 * Esta interfaz proporciona métodos para crear, borrar, actualizar y consultar proveedores,
 * tanto individualmente como en conjunto.
 * 
 * @author Markel
 */
public interface ProveedorInterfaz {

    /**
     * Crea un nuevo proveedor utilizando datos en formato XML.
     * 
     * @param requestEntity los datos del proveedor a crear en formato XML.
     * @throws ClientErrorException si ocurre un error en la solicitud del cliente.
     */
    public void crearProveedor_XML(Object requestEntity) throws ClientErrorException;

    /**
     * Borra un proveedor dado su identificador.
     * 
     * @param id el identificador del proveedor a eliminar.
     * @throws ClientErrorException si ocurre un error en la solicitud del cliente.
     */
    public void borrarProveedor(String id) throws ClientErrorException;

    /**
     * Muestra un proveedor específico dado su identificador, en formato XML.
     * 
     * @param responseType el tipo de respuesta esperada.
     * @param id el identificador del proveedor a mostrar.
     * @param <T> el tipo de la respuesta esperada.
     * @return el proveedor solicitado en formato XML.
     * @throws ClientErrorException si ocurre un error en la solicitud del cliente.
     */
    public <T> T mostrarProveedor_XML(Class<T> responseType, String id) throws ClientErrorException;

    /**
     * Actualiza los datos de un proveedor utilizando datos en formato XML.
     * 
     * @param requestEntity los nuevos datos del proveedor en formato XML.
     * @throws ClientErrorException si ocurre un error en la solicitud del cliente.
     */
    public void actualizarProveedor_XML(Object requestEntity) throws ClientErrorException;

    /**
     * Muestra todos los proveedores en formato XML.
     * 
     * @param responseType el tipo de respuesta esperada.
     * @param <T> el tipo de la respuesta esperada.
     * @return una lista de todos los proveedores en formato XML.
     */
    public <T> T mostrarTodosProveedores_XML(GenericType<T> responseType);

    /**
     * Muestra los proveedores cuya fecha coincide con el valor proporcionado.
     * 
     * @param responseType el tipo de respuesta esperada.
     * @param fecha la fecha con la que se deben filtrar los proveedores.
     * @param <T> el tipo de la respuesta esperada.
     * @return una lista de proveedores filtrados por la fecha proporcionada en formato XML.
     */
    public <T> T mostrarsProveedoresFecha_XML(GenericType<T> responseType, String fecha);
}