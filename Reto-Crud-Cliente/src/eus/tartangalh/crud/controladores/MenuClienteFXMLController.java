/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.controladores;

import eus.tartangalh.crud.entidades.Cliente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Markel
 */
public class MenuClienteFXMLController {

    private Stage stage;
    private Cliente cliente;

    /**
     * Initializes the controller class.
     * @author markel
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
     public void setTrabajador(Cliente cliente) {
        this.cliente = cliente;
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setTitle("Menu Cliente");
        stage.setScene(scene);
        stage.show();
    }

}
