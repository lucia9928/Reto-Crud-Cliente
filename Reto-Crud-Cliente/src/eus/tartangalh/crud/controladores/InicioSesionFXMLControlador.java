/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.controladores;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author 2dam
 */
public class InicioSesionFXMLControlador {

    @FXML
    private TextField textEmail;

    @FXML
    private PasswordField pswContrasena;
    @FXML
    private Button btnIniciarSesion;
    @FXML
    private Button btnRegistrate;
    @FXML
    private Button btnMostrarContra;

    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
    private Button btnShowPassword;

    private boolean esPasswordVisible = false;
    private Stage stage;
    @FXML
    private TextField tfxContrasena;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setTitle("Inicio de sesion");
        stage.setScene(scene);
        stage.show();

        btnIniciarSesion.setOnAction(this::iniciarSesion);
        btnRegistrate.setOnAction(this::irARegistrar);
        btnMostrarContra.setOnAction(this::mostrarContra);

    }

    private void irARegistrar(ActionEvent event) {
        //RetoCrudCliente.navegarVentanas("RegistroFXML.fxml");
    }

    private void mostrarContra(ActionEvent event) {
        if (esPasswordVisible) {
            // Ocultar la contraseña
            tfxContrasena.setText(pswContrasena.getText());
            pswContrasena.setVisible(false);
            pswContrasena.setVisible(true);
            btnMostrarContra.setText("Mostrar");
        } else {
            tfxContrasena.setText(tfxContrasena.getText());
            tfxContrasena.setVisible(true);
            tfxContrasena.setVisible(false);
            btnMostrarContra.setText("Ocultar");
        }
    }

    private void iniciarSesion(ActionEvent event) {

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
