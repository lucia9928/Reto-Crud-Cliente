/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.controladores;

import eus.tartangalh.crud.entidades.Trabajador;
import eus.tartangalh.crud.interfaces.TrabajadorFactoria;
import eus.tartangalh.crud.interfaces.TrabajadorInterfaz;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 2dam-
 */
public class RegistroFXMLControlador {

    private final TrabajadorInterfaz trabajaInterfaz = TrabajadorFactoria.get();
    /**
     * Initializes the controller class.
     *
     * @param root
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
        Trabajador trabajador = new Trabajador();

        trabajador.setDni(tfxDni.getText());
        trabajador.setNombre(tfxNombre.getText());
        trabajador.setApellido(tfxApellido.getText());
        trabajador.setEmail(tfxEmail.getText());
        trabajador.setContrasena(tfxContrasena.getText());
        trabajador.setCalle(tfxCalle.getText());
        trabajador.setCodigoPosta(Integer.parseInt(tfxCodigoPostal.getText()));
        trabajador.setCidudad(tfxCiudad.getText());

        trabajador.setFechaNacimiento(dateFechaNcimiento.getValue());

        trabajaInterfaz.crearTrabajador_XML(trabajador);
    }

}
