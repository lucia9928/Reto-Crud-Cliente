/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import eus.tartangalh.crud.logica.GestionaRESTClient;
import eus.tartangalh.crud.logica.ProveedorRESTClient;

/**
 *
 * @author 2dam
 */
public class GestionaManagerFactoria {
    
   public static GestionaInterfaz get(){
        return new GestionaRESTClient() ;
    }
    
}
