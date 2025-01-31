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
import eus.tartangalh.crud.interfaces.ClienteInterfaz;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author markel
 */
public class RegistroClienteFXMLControlador {

    private final ClienteInterfaz clienteInterfaz = ClienteFactoria.get();
    /**
     * Initializes the controller class.
     *
     * @param root
     * @author markel
     */
    @FXML
    private TextField tfxDni;
    @FXML
    private TextField tfxNombre;
    @FXML
    private TextField tfxApellido;
    @FXML
    private TextField tfxEmail;
    @FXML
    private PasswordField tfxContrasena;
    @FXML
    private PasswordField tfxConfirmarContrasena;
    @FXML
    private TextField tfxCalle;
    @FXML
    private TextField tfxCodigoPostal;
    @FXML
    private TextField tfxCiudad;
    @FXML
    private DatePicker dateFechaNcimiento;
    @FXML
    private Button btbRegistrarse;

    private Stage stage;

    List<Trabajador> trabajadores;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setTitle("Registro");
        stage.setScene(scene);
        stage.show();

        btbRegistrarse.setVisible(true);
        btbRegistrarse.setDisable(false);
        btbRegistrarse.setOnAction(this::crearTrabajador);

    }

    private void crearTrabajador(ActionEvent event) {

        if (validarCliente()) {
            Cliente cliente = new Cliente();

            cliente.setDni(tfxDni.getText());
            cliente.setNombre(tfxNombre.getText());
            cliente.setApellido(tfxApellido.getText());
            cliente.setEmail(tfxEmail.getText());
            cliente.setContrasena(tfxContrasena.getText());
            cliente.setCalle(tfxCalle.getText());
            cliente.setCodigoPosta(Integer.parseInt(tfxCodigoPostal.getText()));
            cliente.setCidudad(tfxCiudad.getText());

Date date= Date.from(dateFechaNcimiento.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
cliente.setFechaNacimiento(date);
            clienteInterfaz.crearCliente_XML(cliente);
            if(clienteInterfaz.encontrarPorId_XML(Cliente.class, tfxDni.getText())!=null){
                mostrarAlert("Confirmacion", "El Cliente se ha dado de alta");
            }
            
        }

    }

    private boolean validarCliente() {
        Pattern dniPatron = Pattern.compile("^\\d{8}[A-Z]$");
        Pattern emailPatron = Pattern.compile(
                "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        );
        String dni = tfxDni.getText().trim().toUpperCase();
        String email = tfxEmail.getText().trim().toLowerCase();
        Matcher matcherDni = dniPatron.matcher(dni);
        Matcher matcherEmail = emailPatron.matcher(email);

        if (tfxDni.getText().isEmpty() || tfxNombre.getText().isEmpty() || tfxEmail.getText().isEmpty() || tfxCiudad.getText().isEmpty() || dateFechaNcimiento.getValue() == null) {
            mostrarAlert("Error", "Faltan campos por rellenar");
            return false;
        }

        if (clienteInterfaz.encontrarPorId_XML(Cliente.class, dni) != null) {
            mostrarAlert("Error", "Este cliente ya existe");
            return false;
        }
        if (!matcherEmail.matches() && !matcherDni.matches()) {
            mostrarAlert("Error", "Hay campos con formato incorrecto");
            return false;
        }

        if (!validarContrasena(tfxContrasena.getText()) || !validarContrasena(tfxConfirmarContrasena.getText())) {
            if (!tfxContrasena.getText().equals(tfxConfirmarContrasena.getText())) {
                mostrarAlert("Error", "Las contraseñas no coinciden");

                return false;
            }
            mostrarAlert("Error", "La contraseña debe tener ocho caracteres y al menos una minúscula, una mayúscula y un dígito");
            return false;
        }

        return true;

    }

    private boolean validarContrasena(String contrasena) {

        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,}$";
        return contrasena.matches(passwordRegex);

    }

    private boolean mostrarAlert(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

}
