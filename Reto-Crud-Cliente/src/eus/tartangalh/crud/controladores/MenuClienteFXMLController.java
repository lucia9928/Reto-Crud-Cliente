/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.controladores;

import eus.tartangalh.crud.entidades.Cliente;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Markel
 */
public class MenuClienteFXMLController {

    private Stage stage;

    private Cliente cliente;

    @FXML
    private Button btnCerrarSesion;
    @FXML
    private Button btnRecetasMedicas;
    @FXML
    private Button btnProducto;
    @FXML
    private Button btnCambioContrasena;

    /**
     * Initializes the controller class.
     *
     * @author markel
     * 
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setTitle("Menu Cliente");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        btnCerrarSesion.setOnAction(this::cerrarSesion);
        btnRecetasMedicas.setOnAction(this::recetaMedica);
        btnProducto.setOnAction(this::visualizarProducto);
        btnCambioContrasena.setOnAction(this::cambioContrasena);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                manejoCierre();
            }
        });
    }

    private void cerrarSesion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto/crud/cliente/InicioSesionFXML.fxml"));
            Parent root = loader.load();
            InicioSesionFXMLControlador inicioSesion = loader.getController();
            inicioSesion.setStage(stage);
            inicioSesion.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(MenuTrabajadorFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void recetaMedica(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto/crud/cliente/RecetaMedicaFXML.fxml"));
            Parent root = loader.load();
            RecetaMedicaFXMLController recetaMedica = loader.getController();
            recetaMedica.setStage(stage);
            recetaMedica.setCliente(cliente);
            recetaMedica.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(MenuTrabajadorFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void visualizarProducto(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto/crud/cliente/VisualizarProducto.fxml"));
            Parent root = loader.load();
            VisualizarProductoFXMLControlador visualizarProducto = loader.getController();
            visualizarProducto.setStage(stage);
            visualizarProducto.setCliente(cliente);
            visualizarProducto.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(MenuTrabajadorFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cambioContrasena(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto/crud/cliente/CambioContrasena.fxml"));
            Parent root = loader.load();
            CambioContrasenaController cambioContra = loader.getController();
            cambioContra.setStage(stage);
            cambioContra.setCliente(cliente);
            cambioContra.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(MenuTrabajadorFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void manejoCierre() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Está seguro de que desea cerrar la aplicación?");
        alert.setContentText("Todos los cambios no guardados se perderán.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();
            stage.close();
        }
    }
}
