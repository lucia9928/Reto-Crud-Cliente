/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import eus.tartangalh.crud.logica.RecetaMedicaREST;

/**
 *
 * @author melany
 */
public class RecetaMedicaFactoria {

    /**
     *
     */
    private static RecetaMedicaInterfaz recetaMedicaInterfaz ;
      public RecetaMedicaInterfaz get() {
        if (recetaMedicaInterfaz == null) {
            recetaMedicaInterfaz = new RecetaMedicaREST(); // Lazy initialization
        }
        return recetaMedicaInterfaz;
    }
}
