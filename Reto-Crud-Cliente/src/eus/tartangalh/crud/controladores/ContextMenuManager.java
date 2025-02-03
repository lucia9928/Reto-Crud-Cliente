
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author andoni
 */
package eus.tartangalh.crud.controladores;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class ContextMenuManager {

    private final ContextMenu contextMenu;

    public ContextMenuManager(Region contenedorPrincipal) {
        contextMenu = new ContextMenu();

        // Elementos del menú contextual
        MenuItem cerrarSesion = new MenuItem("Cerrar Sesión");
        MenuItem salir = new MenuItem("Salir");

        // Añadir los items al menú
        contextMenu.getItems().addAll(cerrarSesion, salir);

        // Configurar acciones para cada opción del menú
        cerrarSesion.setOnAction(event -> {
            try {
                handleLogout();
            } catch (Exception ex) {
                Logger.getLogger(ContextMenuManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        salir.setOnAction(event -> salirAplicacion());

        // Detectar clic derecho en el AnchorPane para mostrar el menú contextual
        contenedorPrincipal.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(contenedorPrincipal, event.getScreenX(), event.getScreenY());
            }
        });
    }

    // Métodos para mostrar alertas y manejar acciones
    private void handleLogout() throws Exception {
        // Crear un Alert de tipo CONFIRMATION
        /*Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar Sesión");
        alert.setHeaderText(null);
        alert.setContentText("¿Está seguro de que desea cerrar sesión?");

        // Mostrar el Alert y esperar la respuesta
        Optional<ButtonType> result = alert.showAndWait();

        // Comprobar si se presionó "OK"
        if (result.isPresent() && result.get() == ButtonType.OK) {
            SignUpSignIn.navegarVentanas("SignInFXML.fxml");
        }*/
    }

    private void salirAplicacion() {
        // Implementación para cerrar la aplicación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Está seguro de que desea cerrar la aplicación?");
        alert.setContentText("Todos los cambios no guardados se perderán.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) contextMenu.getOwnerWindow();
            stage.close();
        }
    }
}
