/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.controladores;

import eus.tartangalh.crud.entidades.Proveedor;
import eus.tartangalh.crud.interfaces.ProveedorFactoria;
import eus.tartangalh.crud.interfaces.ProveedorInterfaz;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import javax.ws.rs.core.GenericType;

/**
 * FXML Controller class
 *
 * @author Markel
 */
public class ProveedorFXMLController {

    private final ProveedorInterfaz proInterfaz = ProveedorFactoria.get();

    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnCrearFila;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnBorrar;
    @FXML
    private Button btnAtras;

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

    List<Proveedor> proveedores;
    ObservableList<Proveedor> proveedoresData;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) {
        LOGGER.info("Inicializando controlador de proveedor");

        Scene scene = new Scene(root);
        stage.setTitle("Gestion de proveedores");

        stage.show();
        stage.setScene(scene);

        tableView.setEditable(true);

        mostrarProveedor();

        btnCrearFila.setVisible(true);
        btnCrearFila.setDisable(false);
        btnCrearFila.setOnAction(this::crearProveedor);
        btnBorrar.setDisable(true);

        idProveedorColumna.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
        calleColumna.setCellValueFactory(new PropertyValueFactory<>("calle"));
        calleColumna.setCellFactory(TextFieldTableCell.forTableColumn());
        calleColumna.setOnEditCommit(event -> {
            Proveedor proveedor = event.getRowValue();
            proveedor.setCalle(event.getNewValue());
            proInterfaz.actualizarProveedor_XML(proveedor);
        });
        cifColumna.setCellValueFactory(new PropertyValueFactory<>("cif"));
        cifColumna.setCellFactory(TextFieldTableCell.forTableColumn());
        cifColumna.setOnEditCommit(event -> {
            Proveedor proveedor = event.getRowValue();
            proveedor.setCif(event.getNewValue());
            proInterfaz.actualizarProveedor_XML(proveedor);
        });

        ciudadColumna.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        ciudadColumna.setCellFactory(TextFieldTableCell.forTableColumn());
        ciudadColumna.setOnEditCommit(event -> {
            Proveedor proveedor = event.getRowValue();
            proveedor.setCiudad(event.getNewValue());
            proInterfaz.actualizarProveedor_XML(proveedor);
        });

        codPostalColumna.setCellValueFactory(new PropertyValueFactory<>("codPostal"));
        codPostalColumna.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        codPostalColumna.setOnEditCommit(event -> {

            Proveedor proveedor = event.getRowValue();
            proveedor.setCodPostal(event.getNewValue());
            proInterfaz.actualizarProveedor_XML(proveedor);
            

        });

        fechaContratacionColumna.setCellValueFactory(new PropertyValueFactory<>("fechaContratacion"));
        
        nombreProveedorColumna.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));
        nombreProveedorColumna.setCellFactory(TextFieldTableCell.forTableColumn());

        if (tableView.getSelectionModel().getSelectedItem() != null) {
            btnBorrar.setDisable(false);
        } else {

        }

    }

    public void crearProveedor(ActionEvent event) {

        try {
            Proveedor proveedor = new Proveedor();
            proInterfaz.crearProveedor_XML(proveedor);
            mostrarProveedor();

        } catch (Exception e) {
            LOGGER.info("Da error en crear");
        }
    }

    public void mostrarProveedor() {
        proveedores = ProveedorFactoria.get().mostrarTodosProveedores_XML(new GenericType<List<Proveedor>>() {
        });

        // Convertir la lista de proveedores en ObservableList para la TableView
        proveedoresData = FXCollections.observableArrayList(proveedores);

        // Establecer los datos en la tabla
        tableView.setItems(proveedoresData);
    }

    /*public void borrarProveedor(ActionEvent event) {
        Proveedor proveedorSeleccionado = tableView.getSelectionModel().getSelectedItem();

        // Mostrar una alerta de confirmación antes de proceder con el borrado
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("Eliminar almacén");
        confirmacion.setContentText("¿Estás seguro de que deseas eliminar el almacén con ID: " + proveedorSeleccionado.getIdProveedor() + "?");

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Llamada al método para borrar el almacén en el servicio REST
                    AlmacenFactoria.get().borrarAlmacen(String.valueOf(proveedorSeleccionado.getIdProveedor()));

                    // Remover la fila de la tabla después de la eliminación exitosa
                    tableView.getItems().remove(proveedorSeleccionado);

                    LOGGER.info("Almacén eliminado correctamente.");
                } catch (WebApplicationException e) {
                    LOGGER.severe("Error al eliminar el almacén: " + e.getMessage());
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error");
                    error.setHeaderText("Error al eliminar almacén");
                    error.setContentText("No se pudo eliminar el almacén. Por favor, inténtelo de nuevo.");
                    error.show();
                }
            }
        });

    }*/
}
