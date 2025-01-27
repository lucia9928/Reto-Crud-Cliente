/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto.crud.cliente;

import eus.tartangalh.crud.controladores.ProveedorFXMLController;
import eus.tartangalh.crud.controladores.RegistroFXMLControlador;
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

        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistroFXML.fxml"));

        Parent root = loader.load();
                
        RegistroFXMLControlador registro = (RegistroFXMLControlador) loader.getController();

        registro.setStage(stage);

        registro.initStage(root);*/
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProveedorFXML.fxml"));

        Parent root = loader.load();
                
        ProveedorFXMLController proveedor = (ProveedorFXMLController) loader.getController();

        proveedor.setStage(stage);

        proveedor.initStage(root);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        //List<Proveedor> proveedores = ProveedorManagerFactoria.get().mostrarTodosProveedores_XML(List.class);

    }

}
