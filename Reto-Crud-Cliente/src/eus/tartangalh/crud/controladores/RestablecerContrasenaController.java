/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.controladores;

import eus.tartangalh.crud.entidades.Cliente;
import eus.tartangalh.crud.entidades.Trabajador;
import eus.tartangalh.crud.interfaces.ClienteFactoria;
import eus.tartangalh.crud.interfaces.TrabajadorFactoria;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;

public class RestablecerContrasenaController {
    
    private static final Logger LOGGER = Logger.getLogger(RestablecerContrasenaController.class.getName());

    @FXML
    private TextField textEmail;

    @FXML
    private void enviarRestablecimiento() {
        String emailIngresado = textEmail.getText().trim();

        if (emailIngresado.isEmpty()) {
            mostrarAlerta("Error", "El campo de correo no puede estar vacío.", Alert.AlertType.ERROR);
            return;
        }

        try {
            Trabajador trabajador = TrabajadorFactoria.get().buscarTrabajador(new GenericType<Trabajador>() {}, emailIngresado);
            Cliente cliente = ClienteFactoria.get().buscarCliente(new GenericType<Cliente>() {}, emailIngresado);
            
            if (trabajador != null) {
                TrabajadorFactoria.get().resetarContrasena(trabajador);
                mostrarAlerta("Éxito", "Se ha enviado un correo con instrucciones para restablecer la contraseña.", Alert.AlertType.INFORMATION);
            } else if (cliente != null) {
                ClienteFactoria.get().resetarContrasena(cliente);
                mostrarAlerta("Éxito", "Se ha enviado un correo con instrucciones para restablecer la contraseña.", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se encontró un usuario con este correo.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al recuperar la contraseña", e);
            mostrarAlerta("Error", "Hubo un problema al procesar la solicitud: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
