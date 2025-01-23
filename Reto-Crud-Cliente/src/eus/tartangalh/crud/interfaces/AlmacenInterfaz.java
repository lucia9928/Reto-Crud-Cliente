/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

public interface AlmacenInterfaz {

    public void crearAlmacen_XML(Object requestEntity) throws WebApplicationException;

    public void crearAlmacen_JSON(Object requestEntity) throws WebApplicationException;

    public <T> T encontrar_XML(Class<T> responseType, String id) throws WebApplicationException;

    public <T> T encontrar_JSON(Class<T> responseType, String id) throws WebApplicationException;

    public void borrarAlmacen(String id) throws WebApplicationException;

    public void actualizarAlmacen_XML(Object requestEntity) throws WebApplicationException;

    public void actualizarAlmacen_JSON(Object requestEntity) throws WebApplicationException;

    public <T> T findAll_XML(GenericType<T> responseTypee) throws WebApplicationException;

    public <T> T findAll_JSON(GenericType<T> responseType) throws WebApplicationException;
}
