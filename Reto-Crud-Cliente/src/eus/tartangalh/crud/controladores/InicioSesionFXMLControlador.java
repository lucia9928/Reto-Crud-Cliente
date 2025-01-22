/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.controladores;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author melany
 */
public class InicioSesionFXMLControlador implements Initializable {

    @FXML
    private TextField textEmail;

    @FXML
    private PasswordField pswContrasena;

    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
    
    

    @FXML
    private void irARegistrar() throws Exception {
        //RetoCrudCliente.navegarVentanas("RegistroFXML.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            Stage stage = (Stage) textEmail.getScene().getWindow();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    event.consume();
                    handleClose();
                }

            });

        });
    }

    private void handleClose() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Está seguro de que desea cerrar la aplicación?");
        alert.setContentText("Todos los cambios no guardados se perderán.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) textEmail.getScene().getWindow();
            stage.close();
        }
    }
}
