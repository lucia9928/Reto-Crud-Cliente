/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author 2dam
 */
public interface ProveedorInterfaz {

    public void crearProveedor_XML(Object requestEntity) throws ClientErrorException;

    public void borrarProducto(String id) throws ClientErrorException;

    public <T> T mostrarProveedor_XML(Class<T> responseType, String id) throws ClientErrorException;

    public void actualizarProveedor_XML(Object requestEntity) throws ClientErrorException;

    public <T> T mostrarTodosProveedores_XML(GenericType<T> responseType);

    public <T> T mostrarsProveedoresFecha_XML(GenericType<T> responseType, String fecha);

}
