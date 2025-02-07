/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import eus.tartangalh.crud.logica.ProveedorRESTClient;
/**
 * Fábrica para obtener una instancia de la interfaz `ProveedorInterfaz`.
 * Esta clase implementa el patrón Singleton para asegurar que solo haya
 * una única instancia de `ProveedorRESTClient` y la proporciona
 * cuando se solicita. Esto facilita el acceso al servicio relacionado
 * con los proveedores.
 * 
 * @author Markel
 */
public class ProveedorFactoria {
    
    /**
     * Obtiene la instancia de `ProveedorInterfaz`.
     * Crea una nueva instancia de `ProveedorRESTClient` y la devuelve.
     * 
     * @return una instancia de `ProveedorInterfaz`.
     */
    public static ProveedorInterfaz get() {
        return new ProveedorRESTClient();
    }
}