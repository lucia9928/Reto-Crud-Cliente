/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import eus.tartangalh.crud.entidades.Cliente;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

/**
 * Interfaz que define las operaciones relacionadas con la gestión de clientes.
 * Esta interfaz proporciona métodos para realizar operaciones CRUD (crear, leer, actualizar, eliminar) 
 * sobre los clientes, así como operaciones adicionales como iniciar sesión y gestionar contraseñas.
 * 
 * @author melany
 */
public interface ClienteInterfaz {

    /**
     * Modifica un cliente enviando los datos en formato XML.
     * 
     * @param requestEntity los datos del cliente a modificar.
     * @param id el identificador del cliente a modificar.
     * @throws WebApplicationException si ocurre un error durante la operación.
     */
    public void modificarCliente_XML(Object requestEntity, String id) throws WebApplicationException;

    /**
     * Modifica un cliente enviando los datos en formato JSON.
     * 
     * @param requestEntity los datos del cliente a modificar.
     * @param id el identificador del cliente a modificar.
     * @throws WebApplicationException si ocurre un error durante la operación.
     */
    public void modificarCliente_JSON(Object requestEntity, String id) throws WebApplicationException;

    /**
     * Encuentra todos los clientes y los devuelve en formato XML.
     * 
     * @param responseType la clase de respuesta para el tipo genérico.
     * @param <T> el tipo de respuesta.
     * @return una lista de todos los clientes en formato XML.
     * @throws WebApplicationException si ocurre un error durante la operación.
     */
    public <T> T encontrarTodosLosClientes_XML(Class<T> responseType) throws WebApplicationException;

    /**
     * Encuentra todos los clientes y los devuelve en formato JSON.
     * 
     * @param responseType la clase de respuesta para el tipo genérico.
     * @param <T> el tipo de respuesta.
     * @return una lista de todos los clientes en formato JSON.
     * @throws WebApplicationException si ocurre un error durante la operación.
     */
    public <T> T encontrarTodosLosClientes_JSON(Class<T> responseType) throws WebApplicationException;

    /**
     * Encuentra todos los clientes y los devuelve en formato XML utilizando un tipo genérico.
     * 
     * @param responseType el tipo genérico de respuesta.
     * @param <T> el tipo de respuesta.
     * @return una lista de todos los clientes en formato XML.
     * @throws WebApplicationException si ocurre un error durante la operación.
     */
    public <T> T encontrarTodosLosClientes_XML(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Encuentra todos los clientes y los devuelve en formato JSON utilizando un tipo genérico.
     * 
     * @param responseType el tipo genérico de respuesta.
     * @param <T> el tipo de respuesta.
     * @return una lista de todos los clientes en formato JSON.
     * @throws WebApplicationException si ocurre un error durante la operación.
     */
    public <T> T encontrarTodosLosClientes_JSON(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Inicia sesión para un cliente utilizando su DNI y contraseña.
     * 
     * @param responseType el tipo de respuesta genérica.
     * @param dni el DNI del cliente.
     * @param contrasenaCli la contraseña del cliente.
     * @param <T> el tipo de respuesta.
     * @return los datos del cliente autenticado.
     * @throws WebApplicationException si ocurre un error durante la operación.
     */
    public <T> T iniciarSesion(GenericType<T> responseType, String dni, String contrasenaCli) throws WebApplicationException;

    /**
     * Busca un cliente por su correo electrónico.
     * 
     * @param respuesta el tipo de respuesta genérica.
     * @param userEmail el correo electrónico del cliente.
     * @param <T> el tipo de respuesta.
     * @return los datos del cliente encontrado.
     * @throws WebApplicationException si ocurre un error durante la operación.
     */
    public <T> T buscarCliente(GenericType<T> respuesta, String userEmail) throws WebApplicationException;

    /**
     * Resetea la contraseña de un cliente.
     * 
     * @param Cliente el cliente cuya contraseña se desea resetear.
     * @throws WebApplicationException si ocurre un error durante la operación.
     */
    public void resetarContrasena(Cliente Cliente) throws WebApplicationException;

    /**
     * Actualiza la contraseña de un cliente.
     * 
     * @param cliente el cliente cuya contraseña se desea actualizar.
     * @throws WebApplicationException si ocurre un error durante la operación.
     */
    public void actualizarContrasena(Cliente cliente) throws WebApplicationException;

    /**
     * Encuentra un cliente por su ID y lo devuelve en formato XML.
     * 
     * @param responseType la clase de respuesta para el tipo genérico.
     * @param id el identificador del cliente a buscar.
     * @param <T> el tipo de respuesta.
     * @return los datos del cliente en formato XML.
     * @throws WebApplicationException si ocurre un error durante la operación.
     */
    public <T> T encontrarPorId_XML(Class<T> responseType, String id) throws WebApplicationException;

    /**
     * Encuentra un cliente por su ID y lo devuelve en formato JSON.
     * 
     * @param responseType la clase de respuesta para el tipo genérico.
     * @param id el identificador del cliente a buscar.
     * @param <T> el tipo de respuesta.
     * @return los datos del cliente en formato JSON.
     * @throws WebApplicationException si ocurre un error durante la operación.
     */
    public <T> T encontrarPorId_JSON(Class<T> responseType, String id) throws WebApplicationException;

    /**
     * Elimina un cliente por su ID.
     * 
     * @param id el identificador del cliente a eliminar.
     * @throws WebApplicationException si ocurre un error durante la operación.
     */
    public void eliminarCliente(String id) throws WebApplicationException;

    /**
     * Crea un nuevo cliente enviando los datos en formato XML.
     * 
     * @param requestEntity los datos del cliente a crear.
     * @throws WebApplicationException si ocurre un error durante la operación.
     */
    public void crearCliente_XML(Object requestEntity) throws WebApplicationException;

    /**
     * Crea un nuevo cliente enviando los datos en formato JSON.
     * 
     * @param requestEntity los datos del cliente a crear.
     * @throws WebApplicationException si ocurre un error durante la operación.
     */
    public void crearCliente_JSON(Object requestEntity) throws WebApplicationException;

    /**
     * Cierra la conexión de la interfaz de cliente.
     */
    public void close();
}