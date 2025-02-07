/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import eus.tartangalh.crud.logica.RecetaMedicaREST;

/**
 * Factoria para la creación e inicialización del servicio de receta médica.
 * Esta clase se encarga de proporcionar una instancia del interfaz {@link RecetaMedicaInterfaz}.
 * La implementación concreta utilizada es {@link RecetaMedicaREST}.
 * 
 * @author melany
 */
public class RecetaMedicaFactoria {

    /**
     * Instancia estática de {@link RecetaMedicaInterfaz} que se utiliza para interactuar con el servicio de receta médica.
     */
    private static RecetaMedicaInterfaz receta;

    /**
     * Método para obtener una instancia del servicio {@link RecetaMedicaInterfaz}.
     * Si la instancia no ha sido creada previamente, se crea una nueva instancia de {@link RecetaMedicaREST}.
     * 
     * @return una instancia de {@link RecetaMedicaInterfaz} que se utiliza para gestionar las recetas médicas.
     */
    public static RecetaMedicaInterfaz get() {
        if (receta == null) {
            receta = new RecetaMedicaREST(); 
        }
        return receta;
    }

}