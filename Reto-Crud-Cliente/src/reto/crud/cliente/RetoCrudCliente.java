/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto.crud.cliente;

import eus.tartangalh.crud.controladores.AlmacenFXMLControlador;
import eus.tartangalh.crud.controladores.ProveedorFXMLController;
import eus.tartangalh.crud.interfaces.ProveedorFactoria;
import eus.tartangalh.crud.entidades.Proveedor;
import java.time.LocalDate;
import java.util.List;
import javafx.application.Application;
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
