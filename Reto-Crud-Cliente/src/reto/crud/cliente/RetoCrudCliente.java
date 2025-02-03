/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto.crud.cliente;

import eus.tartangalh.crud.controladores.InicioSesionFXMLControlador;
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

       /* FXMLLoader loader = new FXMLLoader(getClass().getResource("RecetaMedicaFXML.fxml"));

        Parent root = loader.load();

        RecetaMedicaFXMLController recetaMedica = (RecetaMedicaFXMLController) loader.getController();

        recetaMedica.setStage(stage);

        recetaMedica.initStage(root);*/

        FXMLLoader loader = new FXMLLoader(getClass().getResource("InicioSesionFXML.fxml"));

        Parent root = loader.load();

        InicioSesionFXMLControlador inicioSesion = (InicioSesionFXMLControlador) loader.getController();

        inicioSesion.setStage(stage);

        inicioSesion.initStage(root);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
