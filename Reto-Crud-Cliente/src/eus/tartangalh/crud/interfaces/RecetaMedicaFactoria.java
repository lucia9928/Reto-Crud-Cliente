/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import eus.tartangalh.crud.logica.RecetaMedicaRestCliente;

/**
 *
 * @author melany
 */
public class RecetaMedicaFactoria {

    /**
     *
     */
    private static RecetaMedicaInterface recetaMedicaInterfaz ;
      public RecetaMedicaInterface get() {
        if (recetaMedicaInterfaz == null) {
            recetaMedicaInterfaz = new RecetaMedicaRestCliente(); // Lazy initialization
        }
        return recetaMedicaInterfaz;
    }
}