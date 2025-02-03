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
 *
 * @author 2dam
 */
public interface GestionaInterfaz {

    public void actualizarGestiona_XML(Object requestEntity);

    public void actualizarGestiona_JSON(Object requestEntity);

    public <T> T encontrarGestiona_XML(Class<T> responseType, String dni, String idProducto);

    public <T> T encontrarGestiona_JSON(Class<T> responseType, String dni, String idProducto);

    public <T> T mostrarTodosGestiona_XML(GenericType<T> responseType);

    public <T> T mostrarTodosGestiona_JSON(Class<T> responseType);

    public void crearGestiona_XML(Object requestEntity);

    public void crearGestiona_JSON(Object requestEntity);

    public void borrarGestiona(String dni, String idProducto);

}
