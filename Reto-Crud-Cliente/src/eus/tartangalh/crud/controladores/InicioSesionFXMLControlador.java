/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.controladores;

import eus.tartangalh.crud.entidades.Cliente;
import eus.tartangalh.crud.entidades.Trabajador;
import eus.tartangalh.crud.interfaces.ClienteFactoria;
import eus.tartangalh.crud.interfaces.ClienteInterfaz;
import eus.tartangalh.crud.interfaces.TrabajadorFactoria;
import eus.tartangalh.crud.interfaces.TrabajadorInterfaz;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author markel
 */
public class InicioSesionFXMLControlador {

    @FXML
    private PasswordField pswContrasena;
    @FXML
    private Button btnIniciarSesion;
    @FXML
    private Button btnRegistrate;
    @FXML
    private Button btnMostrarContra;
    @FXML
    private TextField textDni;
    @FXML
    private TextField tfxContrasena;

    private final TrabajadorInterfaz trabajaInterfaz = TrabajadorFactoria.get();
    private final ClienteInterfaz clienteInterfaz = ClienteFactoria.get();

    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
    private Button btnShowPassword;

    private boolean esPasswordVisible = false;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setTitle("Inicio de sesion");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        btnIniciarSesion.setOnAction(this::iniciarSesion);
        btnRegistrate.setOnAction(this::irARegistrar);
        btnMostrarContra.setOnAction(this::mostrarContra);

    }

    private void irARegistrar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto/crud/cliente/RegistroClienteFXML.fxml"));
            Parent root = loader.load();
            RegistroClienteFXMLControlador registroCliente = loader.getController();
            registroCliente.setStage(stage);
            registroCliente.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(InicioSesionFXMLControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void mostrarContra(ActionEvent event) {
        if (esPasswordVisible) {
            // Ocultar contraseña
            pswContrasena.setText(tfxContrasena.getText());
            pswContrasena.setVisible(true);
            tfxContrasena.setVisible(false);
            btnMostrarContra.setText("👁");
        } else {
            // Mostrar contraseña
            tfxContrasena.setText(pswContrasena.getText());
            tfxContrasena.setVisible(true);
            pswContrasena.setVisible(false);
            btnMostrarContra.setText("👁");
        }
        esPasswordVisible = !esPasswordVisible;
    }

    private void iniciarSesion(ActionEvent event) {
        Trabajador trabajador = null;
        Cliente cliente = null;
        String dni = textDni.getText();
        String contrasena = pswContrasena.getText();

        // Puerta trasera: Acceso directo sin consultar la base de datos
        if (dni.equals("dincliente") && contrasena.equals("abcd*1234")) {
            abrirMenuCliente(new Cliente()); // Cliente falso para acceso inmediato
            return;
        }
        if (dni.equals("dintrabajador") && contrasena.equals("abcd*1234")) {
            abrirMenuTrabajador(new Trabajador()); // Trabajador falso para acceso inmediato
            return;
        }

        // Verifica que el campo DNI no esté vacío
        if (!dni.isEmpty()) {
            // Consultar en la base de datos
            trabajador = trabajaInterfaz.iniciarSesion(new GenericType<Trabajador>() {
            }, dni, contrasena);
            cliente = clienteInterfaz.iniciarSesion(new GenericType<Cliente>() {
            }, dni, contrasena);

            // Verifica si es cliente
            if (cliente != null) {
                abrirMenuCliente(cliente);
            } else if (trabajador != null) {
                abrirMenuTrabajador(trabajador);
            } else {
                mostrarAlerta("Error", "Este usuario no existe");
            }
        } else {
            mostrarAlerta("Error", "El campo DNI no puede estar vacío");
        }
    }

// Método para abrir el menú de cliente
    private void abrirMenuCliente(Cliente cliente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto/crud/cliente/MenuClienteFXML.fxml"));
            Parent root = loader.load();
            MenuClienteFXMLController menuCliente = loader.getController();
            menuCliente.setStage(stage);
            menuCliente.setCliente(cliente);
            menuCliente.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(InicioSesionFXMLControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

// Método para abrir el menú de trabajador
    private void abrirMenuTrabajador(Trabajador trabajador) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto/crud/cliente/MenuTrabajadorFXML.fxml"));
            Parent root = loader.load();
            MenuTrabajadorFXMLController menuTrabajador = loader.getController();
            menuTrabajador.setStage(stage);
            menuTrabajador.setTrabajador(trabajador);
            menuTrabajador.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(InicioSesionFXMLControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    private void handleClose() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Está seguro de que desea cerrar la aplicación?");
        alert.setContentText("Todos los cambios no guardados se perderán.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) textDni.getScene().getWindow();
            stage.close();
        }
    }

}
