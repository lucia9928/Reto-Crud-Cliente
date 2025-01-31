/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import eus.tartangalh.crud.logica.TrabajadorREST;

/**
 *
 * @author melany
 */
public class TrabajadorFactoria {
     private static TrabajadorInterfaz trabajadorInterfaz ;
      public static TrabajadorInterfaz get() {
        if (trabajadorInterfaz == null) {
            trabajadorInterfaz = new TrabajadorREST(); // Lazy initialization
        }
        return trabajadorInterfaz;
    }
   
}
