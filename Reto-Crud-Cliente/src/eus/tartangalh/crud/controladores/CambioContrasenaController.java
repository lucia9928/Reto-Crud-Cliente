/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.controladores;

import archivo.AsymmetricCliente;
import eus.tartangalh.crud.entidades.Cliente;
import eus.tartangalh.crud.entidades.Trabajador;
import eus.tartangalh.crud.entidades.Usuario;
import eus.tartangalh.crud.interfaces.ClienteFactoria;
import eus.tartangalh.crud.interfaces.TrabajadorFactoria;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.ws.rs.WebApplicationException;
import javax.xml.bind.DatatypeConverter;

/**
 * FXML Controller class
 *
 * @author oscar
 */
public class CambioContrasenaController {

    private static final Logger LOGGER = Logger.getLogger(CambioContrasenaController.class.getName());

    private Stage stage;
    private Trabajador trabajador;
    private Cliente cliente;

    @FXML
    private TextField txtNuevaContra1;

    @FXML
    private TextField textnuevaContra2;

    @FXML
    private Button btnAtras;

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
        stage.setTitle("Cambio de contraseña");
        stage.setScene(scene);
        stage.show();

        btnAtras.setOnAction(this::menu);
    }

    @FXML
    private void enviar() {
        String nuevaContra1 = txtNuevaContra1.getText();
        String nuevaContra2 = textnuevaContra2.getText();

        // Verifica si todos los campos están llenos
        if (nuevaContra1.isEmpty() || nuevaContra2.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos deben estar llenos.", Alert.AlertType.ERROR);
            return;
        }

        // Verifica si las nuevas contraseñas coinciden
        if (!nuevaContra1.equals(nuevaContra2)) {
            mostrarAlerta("Error", "Las nuevas contraseñas no coinciden.", Alert.AlertType.ERROR);
            return;
        }

        try {
            Cliente client = null;
            Trabajador trabaja = null;

            // Verificar si cliente no es null antes de acceder a su DNI
            if (cliente != null) {
                client = ClienteFactoria.get().encontrarPorId_XML(Cliente.class, cliente.getDni());
                LOGGER.log(Level.INFO, "Cliente encontrado");
            }

            // Si no se encontró un cliente, intentar buscar un trabajador
            if (client == null && trabajador != null) {
                trabaja = TrabajadorFactoria.get().encontrarPorId_XML(Trabajador.class, trabajador.getDni());
                LOGGER.log(Level.INFO, "Trabajador encontrado");
            }

            // Si se encontró un trabajador o cliente, actualizar la contraseña
            if (client != null) {
                byte[] passwordBytes = new AsymmetricCliente().cipher(nuevaContra1);
                client.setContrasena(DatatypeConverter.printHexBinary(passwordBytes));
                ClienteFactoria.get().actualizarContrasena(client);
            } else if (trabaja != null) {
                byte[] passwordBytes = new AsymmetricCliente().cipher(nuevaContra1);
                trabaja.setContrasena(DatatypeConverter.printHexBinary(passwordBytes));
                TrabajadorFactoria.get().actualizarContrasena(trabaja);
            } else {
                mostrarAlerta("Error", "Usuario no encontrado.", Alert.AlertType.ERROR);
                return;
            }

            // Muestra un Alert de éxito
            mostrarAlerta("Éxito", "Contraseña actualizada correctamente.", Alert.AlertType.INFORMATION);

            // Cierra la ventana después del éxito
            if (stage != null) {
                if (cliente != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto/crud/cliente/MenuClienteFXML.fxml"));
                        Parent root = loader.load();
                        MenuClienteFXMLController menuCliente = loader.getController();
                        menuCliente.setStage(stage);
                        menuCliente.setCliente(cliente);
                        menuCliente.initStage(root);
                    } catch (IOException ex) {
                        Logger.getLogger(MenuTrabajadorFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto/crud/cliente/MenuTrabajadorFXML.fxml"));
                        Parent root = loader.load();
                        MenuTrabajadorFXMLController menuTrabajador = loader.getController();
                        menuTrabajador.setStage(stage);
                        menuTrabajador.setTrabajador(trabajador);
                        menuTrabajador.initStage(root);
                    } catch (IOException ex) {
                        Logger.getLogger(MenuTrabajadorFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                LOGGER.log(Level.WARNING, "Stage es null, no se pudo cerrar la ventana.");
            }

        } catch (WebApplicationException ex) {
            mostrarAlerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }

    private void menu(ActionEvent event) {

        if (cliente != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto/crud/cliente/MenuClienteFXML.fxml"));
                Parent root = loader.load();
                MenuClienteFXMLController menuCliente = loader.getController();
                menuCliente.setStage(stage);
                menuCliente.setCliente(cliente);
                menuCliente.initStage(root);
            } catch (IOException ex) {
                Logger.getLogger(MenuTrabajadorFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto/crud/cliente/MenuTrabajadorFXML.fxml"));
                Parent root = loader.load();
                MenuTrabajadorFXMLController menuTrabajador = loader.getController();
                menuTrabajador.setStage(stage);
                menuTrabajador.setTrabajador(trabajador);
                menuTrabajador.initStage(root);
            } catch (IOException ex) {
                Logger.getLogger(MenuTrabajadorFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
