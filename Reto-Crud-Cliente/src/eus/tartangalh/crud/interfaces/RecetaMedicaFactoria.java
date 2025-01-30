/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import eus.tartangalh.crud.logica.ClienteREST;
import eus.tartangalh.crud.logica.RecetaMedicaREST;

/**
 *
 * @author melany
 */
public class RecetaMedicaFactoria {

    /**
     *
     * @return 
     */
     private static RecetaMedicaInterfaz receta ;
     
      public static RecetaMedicaInterfaz get() {
        if (receta == null) {
            receta = new RecetaMedicaREST(); // Lazy initialization
        }
        return receta;
    }
       

}
