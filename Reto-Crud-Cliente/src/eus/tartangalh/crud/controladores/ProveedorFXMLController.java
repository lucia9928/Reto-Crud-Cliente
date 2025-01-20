/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.controladores;

import eus.tartangalh.crud.entidades.Proveedor;
import eus.tartangalh.crud.interfaces.ProveedorManagerFactoria;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;

/**
 * FXML Controller class
 *
 * @author Markel
 */
public class ProveedorFXMLController {

    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnCrearFila;
    @FXML
    private Button btnGuardar;

    @FXML
    private TableView<Proveedor> tableView;

    @FXML
    private TableColumn<Proveedor, Integer> idProveedorColumna;
    @FXML
    private TableColumn<Proveedor, String> calleColumna;
    @FXML
    private TableColumn<Proveedor, String> cifColumna;
    @FXML
    private TableColumn<Proveedor, String> ciudadColumna;
    @FXML
    private TableColumn<Proveedor, Integer> codPostalColumna;
    @FXML
    private TableColumn<Proveedor, Date> fechaContratacionColumna;
    @FXML
    private TableColumn<Proveedor, String> nombreProveedorColumna;

    private Stage stage;

    private static final Logger LOGGER = Logger.getLogger("ProveedorControlador.view");

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) {
        LOGGER.info("Inicializando controlador de proveedor");

        Scene scene = new Scene(root);
        stage.setTitle("Gestion de proveedores");

        stage.show();
        stage.setScene(scene);

        idProveedorColumna.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
        calleColumna.setCellValueFactory(new PropertyValueFactory<>("calle"));
        cifColumna.setCellValueFactory(new PropertyValueFactory<>("cif"));
        ciudadColumna.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        codPostalColumna.setCellValueFactory(new PropertyValueFactory<>("codPostal"));
        fechaContratacionColumna.setCellValueFactory(new PropertyValueFactory<>("fechaContratacion"));
        nombreProveedorColumna.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));

        List<Proveedor> proveedores = ProveedorManagerFactoria.get().mostrarTodosProveedores_XML(new GenericType<List<Proveedor>>() {
        });

        // Convertir la lista de proveedores en ObservableList para la TableView
        ObservableList<Proveedor> proveedoresData = FXCollections.observableArrayList(proveedores);

        // Establecer los datos en la tabla
        tableView.setItems(proveedoresData);

    }

}
