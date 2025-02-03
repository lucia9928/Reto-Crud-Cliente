/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author oscar
 */
public class CambioContrasenaController {

    private static final Logger LOGGER = Logger.getLogger(CambioContrasenaController.class.getName());
    
    @FXML
    private TextField textContraActual;
    
    @FXML
    private TextField txtNuevaContra1;
    
    @FXML
    private TextField textnuevaContra2;

    @FXML
    private void enviarCambioContrasena() {
        String contraActual = textContraActual.getText();
        String nuevaContra1 = txtNuevaContra1.getText();
        String nuevaContra2 = textnuevaContra2.getText();

        if (contraActual.isEmpty() || nuevaContra1.isEmpty() || nuevaContra2.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos deben estar llenos.", Alert.AlertType.ERROR);
            return;
        }

        if (!nuevaContra1.equals(nuevaContra2)) {
            mostrarAlerta("Error", "Las nuevas contraseñas no coinciden.", Alert.AlertType.ERROR);
            return;
        }
/*
        try {
            
            boolean cambiadoTrabajador = TrabajadorFactoria.get().actualizarContrasena(contraActual, nuevaContra1);
            boolean cambiadoCliente = ClienteFactory.getModelo().actualizarContrasena(contraActual, nuevaContra1);
            
            if (cambiadoTrabajador || cambiadoCliente) {
                LOGGER.log(Level.INFO, "Contraseña cambiada correctamente");
                mostrarAlerta("Éxito", "Tu contraseña ha sido cambiada exitosamente.", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "La contraseña actual no es correcta.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al cambiar la contraseña", e);
            mostrarAlerta("Error", "Hubo un problema al procesar la solicitud: " + e.getMessage(), Alert.AlertType.ERROR);
        }
        */
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
