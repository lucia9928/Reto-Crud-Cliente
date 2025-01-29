/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.controladores;

import eus.tartangalh.crud.entidades.Cliente;
import eus.tartangalh.crud.entidades.Trabajador;
import eus.tartangalh.crud.entidades.Usuario;
import eus.tartangalh.crud.interfaces.ClienteFactoria;
import eus.tartangalh.crud.interfaces.TrabajadorFactoria;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.List;
import javax.ws.rs.core.GenericType;

public class RestablecerContrasenaController {

    @FXML
    private TextField textEmail;

    // Servicio remoto para trabajadore
    @FXML
    private void enviarRestablecimiento() {
        String emailIngresado = textEmail.getText();

        if (emailIngresado.isEmpty()) {
            mostrarAlerta("Error", "El campo de correo no puede estar vacío.", Alert.AlertType.ERROR);
            return;
        }

        try {
            Usuario usuarioEncontrado = buscarUsuarioPorEmail(emailIngresado);

            if (usuarioEncontrado == null) {
                mostrarAlerta("Error", "No se encontró un usuario con ese correo.", Alert.AlertType.ERROR);
            } else {
                // Simulación de cambio de contraseña
                usuarioEncontrado.setContrasena("nueva_contrasena");
                mostrarAlerta("Éxito", "Se ha restablecido la contraseña. Nueva contraseña: nueva_contrasena", Alert.AlertType.INFORMATION);

                // Aquí deberías enviar los cambios al servidor
                actualizarUsuario(usuarioEncontrado);
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "Hubo un problema al buscar usuarios: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private Usuario buscarUsuarioPorEmail(String email) {
        // Buscar en Trabajadores
        List<Trabajador> trabajadores = TrabajadorFactoria.get().encontrarTodosLosTrabajdores_XML(new GenericType<List<Trabajador>>() {
        });
        for (Trabajador t : trabajadores) {
            if (t.getEmail().equalsIgnoreCase(email)) {
                return t;
            }
        }

        // Buscar en Clientes
        List<Cliente> clientes = ClienteFactoria.get().encontrarTodosLosClientes_XML(new GenericType<List<Cliente>>() {
        });
        for (Cliente cli : clientes) {
            if (cli.getEmail().equalsIgnoreCase(email)) {
                return cli;
            }
        }

        return null; // No encontrado
    }

    private void actualizarUsuario(Usuario usuario) {
        if (usuario instanceof Trabajador) {
            TrabajadorFactoria.get().modificarTrabajador_XML((Trabajador) usuario);
        } else if (usuario instanceof Cliente) {
            ClienteFactoria.get().modificarCliente_XML((Cliente) usuario);
        }
    }
    private void actualizarUsuario(Usuario usuario) {
    if (usuario instanceof Trabajador) {
        TrabajadorFactoria.get().actualizarTrabajador((Trabajador) usuario);
    } else if (usuario instanceof Cliente) {
        ClienteFactoria.get().actualizarCliente((Cliente) usuario);
    }
}

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
