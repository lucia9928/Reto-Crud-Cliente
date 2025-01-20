/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

import eus.tartangalh.crud.logica.ProveedorRESTClient;
import eus.tartangalh.crud.interfaces.ProveedorManager;

/**
 *
 * @author 2dam
 */
public class ProveedorManagerFactoria {
    
    public static ProveedorManager get(){
        return new ProveedorRESTClient();
    }
    
}
