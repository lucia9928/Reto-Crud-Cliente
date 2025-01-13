/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.logica;

import eus.tartangalh.crud.entidades.ProductoFarmaceutico;
import eus.tartangalh.crud.logica.ProductoFarmaceuticoRESTful;

/**
 *
 * @author 2dam
 */
public class ProductoFactory {
    private static ProductoInterfaz productoInterfaz;
    
    public ProductoInterfaz get() {
        if (productoInterfaz == null) {
            productoInterfaz = new ProductoFarmaceuticoRESTful(); // Lazy initialization
        }
        return productoInterfaz;
    }
}
