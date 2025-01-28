/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.controladores;

import eus.tartangalh.crud.entidades.Trabajador;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.List;

public class RestablecerContrasenaController {

    @FXML
    private TextField textEmail;

    // Servicio remoto para trabajadores
/*
    @FXML
    private void enviarRestablecimiento() {
        String emailIngresado = textEmail.getText().trim();

        if (emailIngresado.isEmpty()) {
            mostrarAlerta("Error", "El campo de correo no puede estar vacío.", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Buscar trabajadores
            List<Trabajador> trabajadores = trabajadorService.encontrarTodosLosTrabajadores();

            // Buscar el trabajador por correo
            Trabajador trabajadorEncontrado = trabajadores.stream()
                    .filter(t -> t.getEmail().equalsIgnoreCase(emailIngresado))
                    .findFirst()
                    .orElse(null);

            if (trabajadorEncontrado == null) {
                mostrarAlerta("Error", "No se encontró un trabajador con ese correo.", Alert.AlertType.ERROR);
            } else {
                // Simulación de cambio de contraseña (esto normalmente se haría en el servidor)
                trabajadorEncontrado.setContrasena("nueva_contrasena");
                mostrarAlerta("Éxito", "Se ha restablecido la contraseña. Nueva contraseña: nueva_contrasena", Alert.AlertType.INFORMATION);

                // Aquí deberías enviar los cambios al servidor, si fuera necesario
                // trabajadorService.actualizarTrabajador(trabajadorEncontrado);
            }

        } catch (Exception e) {
            mostrarAlerta("Error", "Hubo un problema al buscar trabajadores: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
*/
    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}