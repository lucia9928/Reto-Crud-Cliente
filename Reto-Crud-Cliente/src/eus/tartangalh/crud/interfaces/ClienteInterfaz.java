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
 *
 * @author melany
 */
public interface ClienteInterfaz {

    public void modificarCliente_XML(Object requestEntity, String id) throws WebApplicationException;

    public void modificarCliente_JSON(Object requestEntity, String id) throws WebApplicationException;

    public <T> T encontrarTodosLosClientes_XML(Class<T> responseType) throws WebApplicationException;

    public <T> T encontrarTodosLosClientes_JSON(Class<T> responseType) throws WebApplicationException;

    public <T> T encontrarTodosLosClientes_XML(GenericType<T> responseType) throws WebApplicationException;

    public <T> T encontrarTodosLosClientes_JSON(GenericType<T> responseType) throws WebApplicationException;

    public void resetarContrasena(Cliente Cliente) throws WebApplicationException;

    public void actualizarContrasena(Cliente cliente) throws WebApplicationException;

    public <T> T encontrarPorId_XML(Class<T> responseType, String id) throws WebApplicationException;

    public <T> T encontrarPorId_JSON(Class<T> responseType, String id) throws WebApplicationException;

    public void eliminarCliente(String id) throws WebApplicationException;

    public void crearCliente_XML(Object requestEntity) throws WebApplicationException;

    public void crearCliente_JSON(Object requestEntity) throws WebApplicationException;

    public void close();
}
