/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import eus.tartangalh.crud.logica.GestionaRESTClient;

/**
 * Factoria para obtener una instancia de la interfaz `GestionaInterfaz`. 
 * Esta clase se encarga de crear y devolver una instancia concreta del cliente REST 
 * para realizar las operaciones relacionadas con la gestión de productos de los trabajadores.
 * 
 * @author Markel
 */
public class GestionaManagerFactoria {

    /**
     * Obtiene una instancia del cliente REST para la interfaz `GestionaInterfaz`.
     * Esta instancia se utilizará para interactuar con los servicios relacionados con
     * las gestiones de productos.
     * 
     * @return una instancia de `GestionaInterfaz` implementada por `GestionaRESTClient`.
     */
   public static GestionaInterfaz get(){
        return new GestionaRESTClient();
    }
}