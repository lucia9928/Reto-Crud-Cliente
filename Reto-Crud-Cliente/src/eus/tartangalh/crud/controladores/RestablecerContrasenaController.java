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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;

public class RestablecerContrasenaController {

    private static final Logger LOGGER = Logger.getLogger(RestablecerContrasenaController.class.getName());

    private Stage stage;
    private Trabajador trabajador;
    private Cliente cliente;

    @FXML
    private TextField textEmail;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setTitle("Restablecer contraseña");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void enviarRestablecimiento() {
        String emailIngresado = textEmail.getText();

        if (emailIngresado.isEmpty()) {
            mostrarAlerta("Error", "El campo de correo no puede estar vacío.", Alert.AlertType.ERROR);
            return;
        }

        Trabajador trabajador = null;
        Cliente cliente = null;

        try {
            trabajador = TrabajadorFactoria.get().buscarTrabajador(new GenericType<Trabajador>() {
            }, emailIngresado);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "No se encontró un trabajador con este correo.", e);
        }

        try {
            cliente = ClienteFactoria.get().buscarCliente(new GenericType<Cliente>() {
            }, emailIngresado);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "No se encontró un cliente con este correo.", e);
        }

        if (trabajador != null) {
            try {
                TrabajadorFactoria.get().resetarContrasena(trabajador);
                mostrarAlerta("Éxito", "Se ha enviado un correo con instrucciones para restablecer la contraseña.", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error al restablecer la contraseña del trabajador.", e);
                mostrarAlerta("Error", "Hubo un problema al restablecer la contraseña del trabajador.", Alert.AlertType.ERROR);
            }
        } else if (cliente != null) {
            try {
                ClienteFactoria.get().resetarContrasena(cliente);
                mostrarAlerta("Éxito", "Se ha enviado un correo con instrucciones para restablecer la contraseña.", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error al restablecer la contraseña del cliente.", e);
                mostrarAlerta("Error", "Hubo un problema al restablecer la contraseña del cliente.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error", "No se encontró un usuario con este correo.", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
