/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import eus.tartangalh.crud.logica.AlmacenRESTful;

/**
 *
 * @author 2dam
 */
public class AlmacenFactoria {
    private static AlmacenInterfaz almacenInterfaz;
    
    public AlmacenInterfaz get() {
        if (almacenInterfaz == null) {
            almacenInterfaz = new AlmacenRESTful();
        }
        return almacenInterfaz;
    }
}
