/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto.crud.cliente;


import eus.tartangalh.crud.entidades.ProductoFarmaceutico;
import eus.tartangalh.crud.interfaces.ProductoInterfazFactoria;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author 2dam
 */
public class RetoCrudCliente extends javafx.application.Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ProductoFarmaceuticoUI.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    launch(args);
        
    }
    
}
