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
 * Interfaz que define las operaciones para la gestión de recetas médicas.
 * Las operaciones incluyen la creación, modificación, eliminación, y obtención de recetas médicas.
 * También incluye la capacidad de consultar recetas por fechas y obtener los productos asociados a una receta.
 * 
 * @author Melany
 */
public interface RecetaMedicaInterfaz {

    /**
     * Elimina una receta médica con el identificador especificado.
     * 
     * @param id el identificador de la receta médica a eliminar.
     * @throws WebApplicationException si ocurre un error durante la eliminación de la receta.
     */
    public void eliminarRecetamedica(String id) throws WebApplicationException;

    /**
     * Obtiene los productos farmacéuticos asociados a una receta médica.
     * 
     * @param responseType el tipo de respuesta esperado.
     * @param id el identificador de la receta médica.
     * @param <T> el tipo de la respuesta.
     * @return los productos asociados a la receta médica.
     * @throws WebApplicationException si ocurre un error durante la consulta.
     */
    public <T> T obtenerProductosPorReceta(GenericType<T> responseType, String id) throws WebApplicationException;

    /**
     * Modifica una receta médica, enviando la solicitud en formato XML.
     * 
     * @param requestEntity los datos de la receta médica a modificar.
     * @throws WebApplicationException si ocurre un error durante la modificación de la receta.
     */
    public void modificarRecetaMedica_XML(Object requestEntity) throws WebApplicationException;

    /**
     * Modifica una receta médica, enviando la solicitud en formato JSON.
     * 
     * @param requestEntity los datos de la receta médica a modificar.
     * @throws WebApplicationException si ocurre un error durante la modificación de la receta.
     */
    public void modificarRecetaMedica_JSON(Object requestEntity) throws WebApplicationException;

    /**
     * Encuentra una receta médica por su identificador en formato XML.
     * 
     * @param responseType el tipo de respuesta esperado.
     * @param id el identificador de la receta médica.
     * @param <T> el tipo de la respuesta.
     * @return la receta médica solicitada.
     * @throws WebApplicationException si ocurre un error durante la consulta.
     */
    public <T> T encontrarRecetaPorId_XML(GenericType<T> responseType, String id) throws WebApplicationException;

    /**
     * Encuentra una receta médica por su identificador en formato JSON.
     * 
     * @param responseType el tipo de respuesta esperado.
     * @param id el identificador de la receta médica.
     * @param <T> el tipo de la respuesta.
     * @return la receta médica solicitada.
     * @throws WebApplicationException si ocurre un error durante la consulta.
     */
    public <T> T encontrarRecetaPorId_JSON(GenericType<T> responseType, String id) throws WebApplicationException;

    /**
     * Encuentra todas las recetas médicas disponibles en formato XML.
     * 
     * @param responseType el tipo de respuesta esperado.
     * @param <T> el tipo de la respuesta.
     * @return todas las recetas médicas.
     * @throws WebApplicationException si ocurre un error durante la consulta.
     */
    public <T> T encontrarTodasLasRecetas_XML(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Encuentra todas las recetas médicas disponibles en formato JSON.
     * 
     * @param responseType el tipo de respuesta esperado.
     * @param <T> el tipo de la respuesta.
     * @return todas las recetas médicas.
     * @throws WebApplicationException si ocurre un error durante la consulta.
     */
    public <T> T encontrarTodasLasRecetas_JSON(GenericType<T> responseType) throws WebApplicationException;

    /**
     * Crea una nueva receta médica en formato XML.
     * 
     * @param requestEntity los datos de la receta médica a crear.
     * @param id el identificador del cliente asociado.
     * @throws WebApplicationException si ocurre un error durante la creación de la receta.
     */
    public void crearRecetaMedica_XML(Object requestEntity) throws WebApplicationException;

    /**
     * Crea una nueva receta médica en formato JSON.
     * 
     * @param requestEntity los datos de la receta médica a crear.
     * @param id el identificador del cliente asociado.
     * @throws WebApplicationException si ocurre un error durante la creación de la receta.
     */
    public void crearRecetaMedica_JSON(Object requestEntity) throws WebApplicationException;

    /**
     * Encuentra las recetas médicas por un rango de fechas en formato XML.
     * 
     * @param responseType el tipo de respuesta esperado.
     * @param fechaInicio la fecha de inicio del rango.
     * @param fechaFin la fecha final del rango.
     * @param <T> el tipo de la respuesta.
     * @return las recetas médicas dentro del rango de fechas especificado.
     * @throws WebApplicationException si ocurre un error durante la consulta.
     */
    public <T> T encontrarRecetasPorFecha_XML(GenericType<T> responseType, String fechaInicio, String fechaFin) throws WebApplicationException;

    /**
     * Encuentra las recetas médicas por un rango de fechas en formato JSON.
     * 
     * @param responseType el tipo de respuesta esperado.
     * @param fechaInicio la fecha de inicio del rango.
     * @param fechaFin la fecha final del rango.
     * @param <T> el tipo de la respuesta.
     * @return las recetas médicas dentro del rango de fechas especificado.
     * @throws WebApplicationException si ocurre un error durante la consulta.
     */
    public <T> T encontrarRecetasPorFecha_JSON(GenericType<T> responseType, String fechaInicio, String fechaFin) throws WebApplicationException;

    /**
     * Cierra la conexión con el servicio.
     */
    public void close();
}