/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import eus.tartangalh.crud.logica.ProductoFarmaceuticoRESTful;

/**
 * Fábrica para obtener una instancia de la interfaz `ProductoInterfaz`.
 * Esta clase implementa el patrón Singleton para asegurar que solo haya
 * una única instancia de `ProductoFarmaceuticoRESTful` y la proporciona
 * cuando se solicita. Esto permite un acceso controlado a la implementación
 * de los servicios relacionados con los productos farmacéuticos.
 * 
 * @author Oscar
 */
public class ProductoInterfazFactoria {

    // Instancia estática de ProductoInterfaz
    private static ProductoInterfaz productoInterfaz;

    /**
     * Obtiene la instancia de `ProductoInterfaz`.
     * Si la instancia no ha sido creada previamente, crea una nueva
     * instancia de `ProductoFarmaceuticoRESTful`.
     * 
     * @return una instancia de `ProductoInterfaz`.
     */
    public static ProductoInterfaz get() {

        if (productoInterfaz == null) {
            productoInterfaz = new ProductoFarmaceuticoRESTful();
        }
        return productoInterfaz;
    }
}