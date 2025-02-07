/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import eus.tartangalh.crud.logica.AlmacenRESTful;

/**
 * Clase de fábrica para gestionar la creación de instancias de {@link AlmacenInterfaz}.
 * Implementa el patrón Singleton para asegurar que solo exista una instancia de la interfaz.
 * 
 * Esta clase proporciona un punto de acceso global a la instancia de {@link AlmacenInterfaz},
 * garantizando que la instancia se cree solo cuando se necesite.
 * 
 * @author Andoni
 */
public class AlmacenFactoria {

    // Instancia estática de AlmacenInterfaz
    private static AlmacenInterfaz almacenInterfaz;
    
    /**
     * Obtiene la instancia única de {@link AlmacenInterfaz}.
     * Si la instancia aún no ha sido creada, se instancia un nuevo objeto {@link AlmacenRESTful}.
     *
     * @return la instancia de {@link AlmacenInterfaz}.
     */
    public static AlmacenInterfaz get() {

        // Si la instancia aún no ha sido creada, se crea una nueva
        if (almacenInterfaz == null) {
            almacenInterfaz = new AlmacenRESTful();
        }
        // Se devuelve la instancia única
        return almacenInterfaz;
    }
}
