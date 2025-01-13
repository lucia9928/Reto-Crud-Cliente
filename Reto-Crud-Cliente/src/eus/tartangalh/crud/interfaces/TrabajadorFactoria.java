/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.interfaces;

/**
 *
 * @author melany
 */
public class TrabajadorFactoria {
     private static TrabajadorInterfaz trabajadorInterfaz ;
      public TrabajadorInterfaz get() {
        if (trabajadorInterfaz == null) {
            trabajadorInterfaz = TrabajadorREST(); // Lazy initialization
        }
        return trabajadorInterfaz;
    }
    private TrabajadorInterfaz TrabajadorREST() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
