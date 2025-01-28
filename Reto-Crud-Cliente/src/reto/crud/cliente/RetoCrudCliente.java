/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto.crud.cliente;

import eus.tartangalh.crud.controladores.AlmacenFXMLControlador;
import eus.tartangalh.crud.controladores.ProductoFarmaceuticoUIController;
import eus.tartangalh.crud.controladores.ProveedorFXMLController;
import eus.tartangalh.crud.controladores.RegistroClienteFXMLControlador;
import eus.tartangalh.crud.controladores.RegistroTrabajadorFXMLControlador;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 *
 * @author 2dam
 */
public class RetoCrudCliente extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws Exception {

        /*  FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistroTrabajadorFXML.fxml"));

        Parent root = loader.load();

        RegistroTrabajadorFXMLControlador registroTrabajador = (RegistroTrabajadorFXMLControlador) loader.getController();

        registroTrabajador.setStage(stage);

        registroTrabajador.initStage(root);*/

 /* FXMLLoader loader = new FXMLLoader(getClass().getResource("ProveedorFXML.fxml"));

        Parent root = loader.load();
                
        ProductoFarmaceuticoUIController proveedor = (ProductoFarmaceuticoUIController) loader.getController();

        proveedor.setStage(stage);

        proveedor.initStage(root);*/
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistroClienteFXML.fxml"));

        Parent root = loader.load();

        RegistroClienteFXMLControlador registroCliente = (RegistroClienteFXMLControlador) loader.getController();

        registroCliente.setStage(stage);

        registroCliente.initStage(root);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
