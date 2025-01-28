/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import eus.tartangalh.crud.logica.ClienteREST;
import eus.tartangalh.crud.logica.ProveedorRESTClient;

/**
 *
 * @author melany
 */
public class ClienteFactoria {

    public static ClienteInterfaz get() {
        return new ClienteREST();
    }
}
