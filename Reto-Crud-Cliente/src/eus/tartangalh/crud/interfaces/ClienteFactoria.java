/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import eus.tartangalh.crud.logica.ClienteREST;
/**
 *
 * @author melany
 */
public class ClienteFactoria {
      private static ClienteInterfaz clienteInterfaz ;
      public static ClienteInterfaz get() {
        if (clienteInterfaz == null) {
            clienteInterfaz = new ClienteREST(); // Lazy initialization
        }
        return clienteInterfaz;
    }
}
