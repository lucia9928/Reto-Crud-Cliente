/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import eus.tartangalh.crud.entidades.Trabajador;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

/**
 * Interfaz que define los métodos para interactuar con los servicios relacionados con los trabajadores.
 * Esta interfaz proporciona operaciones para la creación, modificación, eliminación y búsqueda de trabajadores,
 * así como la gestión de su contraseña.
 * 
 * @author melany
 */
public interface TrabajadorInterfaz {

    /**
     * Crea un nuevo trabajador enviando los datos en formato XML.
     * 
     * @param requestEntity objeto con los datos del trabajador a crear.
     * @throws WebApplicationException si ocurre un error al crear el trabajador.
     */
    public void crearTrabajador_XML(Object requestEntity) throws WebApplicationException;

    /**
     * Crea un nuevo trabajador enviando los datos en formato JSON.
     * 
     * @param requestEntity objeto con los datos del trabajador a crear.
     * @throws WebApplicationException si ocurre un error al crear el trabajador.
     */
    public void crearTrabajador_JSON(Object requestEntity) throws WebApplicationException;

    /**
     * Busca un trabajador por su ID en formato XML.
     * 
     * @param responseType tipo de respuesta esperada.
     * @param id identificador del trabajador a buscar.
     * @param <T> tipo de respuesta esperada.
     * @return objeto con los datos del trabajador encontrado.
     * @throws WebApplicationException si ocurre un error al buscar el trabajador.
     */
    public <T> T encontrarPorId_XML(Class<T> responseType, String id) throws WebApplicationException;

    /**
     * Busca un trabajador por su ID en formato JSON.
     * 
     * @param responseType tipo de respuesta esperada.
     * @param id identificador del trabajador a buscar.
     * @param <T> tipo de respuesta esperada.
     * @return objeto con los datos del trabajador encontrado.
     * @throws WebApplicationException si ocurre un error al buscar el trabajador.
     */
    public <T> T encontrarPorId_JSON(Class<T> responseType, String id) throws WebApplicationException;

    /**
     * Modifica los datos de un trabajador en formato XML.
     * 
     * @param requestEntity objeto con los nuevos datos del trabajador.
     * @param id identificador del trabajador a modificar.
     * @throws WebApplicationException si ocurre un error al modificar el trabajador.
     */
    public void modificarTrabajador_XML(Object requestEntity, String id) throws WebApplicationException;

    /**
     * Modifica los datos de un trabajador en formato JSON.
     * 
     * @param requestEntity objeto con los nuevos datos del trabajador.
     * @param id identificador del trabajador a modificar.
     * @throws WebApplicationException si ocurre un error al modificar el trabajador.
     */
    public void modificarTrabajador_JSON(Object requestEntity, String id) throws WebApplicationException;

    /**
     * Elimina un trabajador por su ID.
     * 
     * @param id identificador del trabajador a eliminar.
     * @throws WebApplicationException si ocurre un error al eliminar el trabajador.
     */
    public void eliminarTrabajador(String id) throws WebApplicationException;

    /**
     * Resetea la contraseña de un trabajador.
     * 
     * @param trabajador el trabajador cuyo password se quiere resetear.
     * @throws WebApplicationException si ocurre un error al resetear la contraseña.
     */
    public void resetarContrasena(Trabajador trabajador) throws WebApplicationException;

    /**
     * Actualiza la contraseña de un trabajador.
     * 
     * @param trabajador el trabajador cuya contraseña se quiere actualizar.
     * @throws WebApplicationException si ocurre un error al actualizar la contraseña.
     */
    public void actualizarContrasena(Trabajador trabajador) throws WebApplicationException;

    /**
     * Busca un trabajador por su email.
     * 
     * @param respuesta tipo de respuesta esperada.
     * @param userEmail email del trabajador a buscar.
     * @param <T> tipo de respuesta esperada.
     * @return objeto con los datos del trabajador encontrado.
     * @throws WebApplicationException si ocurre un error al buscar el trabajador.
     */
    public <T> T buscarTrabajador(GenericType<T> respuesta, String userEmail) throws WebApplicationException;

    /**
     * Inicia sesión con las credenciales de un trabajador.
     * 
     * @param responseType tipo de respuesta esperada.
     * @param Tradni dni del trabajador.
     * @param contrasenaTra contraseña del trabajador.
     * @param <T> tipo de respuesta esperada.
     * @return objeto con la respuesta de inicio de sesión.
     * @throws WebApplicationException si ocurre un error al iniciar sesión.
     */
    public <T> T iniciarSesion(GenericType<T> responseType, String Tradni, String contrasenaTra) throws WebApplicationException;

    /**
     * Encuentra todos los trabajadores en formato XML.
     * 
     * @param responseType tipo de respuesta esperada.
     * @param <T> tipo de respuesta esperada.
     * @return lista de trabajadores.
     * @throws WebApplicationException si ocurre un error al obtener la lista de trabajadores.
     */
    public <T> T encontrarTodosLosTrabajdores_XML(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Encuentra todos los trabajadores en formato JSON.
     * 
     * @param responseType tipo de respuesta esperada.
     * @param <T> tipo de respuesta esperada.
     * @return lista de trabajadores.
     * @throws WebApplicationException si ocurre un error al obtener la lista de trabajadores.
     */
    public <T> T encontrarTodosLosTrabajdores_JSON(Class<T> responseType) throws WebApplicationException;

    /**
     * Cierra la conexión con el servicio.
     */
    public void close();
}