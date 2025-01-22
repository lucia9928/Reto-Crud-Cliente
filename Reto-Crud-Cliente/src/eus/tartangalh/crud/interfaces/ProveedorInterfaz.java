/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author 2dam
 */
public interface ProveedorInterfaz {

    public void crearProveedor_XML(Object requestEntity, String proveedor) throws ClientErrorException;

    public void borrarProveedor(String id) throws ClientErrorException;

    public <T> T mostrarProveedor_XML(Class<T> responseType, String id) throws WebApplicationException;

    public void actualizarProveedor_XML(Object requestEntity, String proveedor) throws WebApplicationException;

    public <T> T mostrarTodosProveedores_XML(GenericType<T> responseType) throws WebApplicationException;

    public <T> T mostrarsProveedoresFecha_XML(Class<T> responseType, String fecha) throws WebApplicationException;

}
