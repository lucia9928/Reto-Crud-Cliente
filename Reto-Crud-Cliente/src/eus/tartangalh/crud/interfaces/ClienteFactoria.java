/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import eus.tartangalh.crud.logica.ClienteREST;

/**
 * Factoría para obtener una instancia de la interfaz {@link ClienteInterfaz}.
 * Esta clase proporciona el acceso a la implementación del servicio RESTful relacionado con los clientes.
 * 
 * @author melany
 */
public class ClienteFactoria {

    /**
     * Obtiene una nueva instancia de {@link ClienteInterfaz}.
     * En este caso, retorna la implementación {@link ClienteREST}.
     * 
     * @return una nueva instancia de {@link ClienteInterfaz}.
     */
    public static ClienteInterfaz get() {
        return new ClienteREST();
    }
}