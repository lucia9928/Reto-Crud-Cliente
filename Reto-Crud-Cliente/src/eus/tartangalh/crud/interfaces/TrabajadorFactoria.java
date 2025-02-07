/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import eus.tartangalh.crud.logica.TrabajadorREST;

/**
 * Fábrica para la creación de instancias de la interfaz {@link TrabajadorInterfaz}.
 * Esta clase proporciona un método estático para obtener una implementación de la interfaz 
 * {@link TrabajadorInterfaz}, la cual interactúa con los servicios REST relacionados con los trabajadores.
 * 
 * @author melany
 */
public class TrabajadorFactoria {

    /**
     * Obtiene una instancia de la implementación de la interfaz {@link TrabajadorInterfaz}.
     * Si aún no se ha creado una instancia, se crea y se devuelve una nueva.
     * 
     * @return una instancia de {@link TrabajadorInterfaz}.
     */
    public static TrabajadorInterfaz get() {
        return new TrabajadorREST();
    }
}