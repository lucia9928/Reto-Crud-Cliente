/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import eus.tartangalh.crud.logica.ProductoFarmaceuticoRESTful;

/**
 *
 * @author Oscar
 */
public class ProductoInterfazFactoria {
    private static ProductoInterfaz productoInterfaz;
    
    public static ProductoInterfaz get() {
        if (productoInterfaz == null) {
            productoInterfaz = new ProductoFarmaceuticoRESTful();
        }
        return productoInterfaz;
    }
}

