/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.WebTarget;

/**
 *
 * @author 2dam
 */
public interface GestionaInterfaz {

    public void actualizarGestiona_XML(Object requestEntity, String id) throws ClientErrorException;

    public void crearGestiona_XML(Object requestEntity) throws ClientErrorException;

    public <T> T find_XML(Class<T> responseType, String id) throws ClientErrorException ;

    public <T> T findRange_XML(Class<T> responseType, String from, String to) throws ClientErrorException;

    public <T> T findAll_XML(Class<T> responseType) throws ClientErrorException;

    public void remove(String id) throws ClientErrorException;

}
