/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.controladores;

import eus.tartangalh.crud.entidades.Proveedor;
import eus.tartangalh.crud.entidades.Trabajador;
import eus.tartangalh.crud.interfaces.ProveedorFactoria;
import eus.tartangalh.crud.interfaces.ProveedorInterfaz;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

/**
 * FXML Controller class
 *
 * @author Markel
 */
public class ProveedorFXMLController {

    private final ProveedorInterfaz proInterfaz = ProveedorFactoria.get();

    @FXML
    private TextField FieldIdProveedor;
    @FXML
    private DatePicker fechaFiltro;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnCrearFila;
    
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

    List<Proveedor> todosProveedores;
    List<Proveedor> ProveedoresPorFecha;
    ObservableList<Proveedor> todosProveedoresData;
    ObservableList<Proveedor> ProveedoresPorFechaData;
    private Trabajador trabajador;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public void initStage(Parent root) {
        LOGGER.info("Inicializando controlador de proveedor");

        Scene scene = new Scene(root);
        stage.setTitle("Gestion de proveedores");
        stage.setScene(scene);
        stage.show();

       

        tableView.setEditable(true);

        // Establecer los datos en la tabla
        tableView.setItems(mostrarProveedor());

        btnCrearFila.setVisible(true);
        btnCrearFila.setDisable(false);
        btnCrearFila.setOnAction(this::crearProveedor);
        btnBuscar.setOnAction(this::buscarProveedor);
        btnBorrar.setOnAction(this::borrarProveedor);
        btnBorrar.setDisable(true);
        btnAtras.setOnAction(this::menuTrabajador);
        

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
        fechaContratacionColumna.setCellFactory(event -> new TableCell<Proveedor, Date>() {
            private final DatePicker datePicker = new DatePicker();

            {
                datePicker.setOnAction(event -> {
                    int rowIndex = getIndex();
                    if (rowIndex >= 0 && rowIndex < tableView.getItems().size()) {
                        Proveedor proveedor = tableView.getItems().get(rowIndex);
                        // Convertir LocalDate a java.sql.Date
                        LocalDate selectedDate = datePicker.getValue();
                        if (selectedDate != null) {
                            proveedor.setFechaContratacion(java.sql.Date.valueOf(selectedDate));
                            proInterfaz.actualizarProveedor_XML(proveedor);
                            tableView.refresh(); // Refrescar la tabla después de la actualización
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null); // Si no hay valor, no mostramos nada
                } else {
                    // Convertir java.sql.Date a LocalDate sin usar toInstant()
                    LocalDate localDate = new java.sql.Date(item.getTime()).toLocalDate();
                    datePicker.setValue(localDate); // Establecer el valor en el DatePicker
                    setGraphic(datePicker); // Mostrar el DatePicker en la celda
                }
            }
        });

        nombreProveedorColumna.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));
        nombreProveedorColumna.setCellFactory(TextFieldTableCell.forTableColumn());

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnBorrar.setDisable(newValue == null); // Habilitar si hay una fila seleccionada, deshabilitar si no
        });

    }

    public void crearProveedor(ActionEvent event) {

        try {
            Proveedor proveedor = new Proveedor();
            proInterfaz.crearProveedor_XML(proveedor);
            tableView.setItems(mostrarProveedor());

        } catch (Exception e) {
            LOGGER.info("Da error en crear");
        }
    }

    public ObservableList<Proveedor> mostrarProveedor() {
        todosProveedores = proInterfaz.mostrarTodosProveedores_XML(new GenericType<List<Proveedor>>() {
        });

        // Convertir la lista de proveedores en ObservableList para la TableView
        return todosProveedoresData = FXCollections.observableArrayList(todosProveedores);

    }

    public void buscarProveedor(ActionEvent event) {
        Proveedor proveedor;
        if (!FieldIdProveedor.getText().equals("")) {
            proveedor = ProveedorFactoria.get().mostrarProveedor_XML(Proveedor.class, FieldIdProveedor.getText());

            ProveedoresPorFechaData = FXCollections.observableArrayList(proveedor);
            tableView.setItems(ProveedoresPorFechaData);

        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fecha = fechaFiltro.getValue().format(formatter);

            ProveedoresPorFecha = ProveedorFactoria.get().mostrarsProveedoresFecha_XML(new GenericType<List<Proveedor>>() {
            }, fecha);

            // Convertir la lista de proveedores en ObservableList para la TableView
            ProveedoresPorFechaData = FXCollections.observableArrayList(ProveedoresPorFecha);
            tableView.setItems(ProveedoresPorFechaData);
        }

    }

    public void borrarProveedor(ActionEvent event) {
        Proveedor proveedorSeleccionado = tableView.getSelectionModel().getSelectedItem();

        try {
            // Llamada al método para borrar el almacén en el servicio REST
            proInterfaz.borrarProveedor(String.valueOf(proveedorSeleccionado.getIdProveedor()));

            // Remover la fila de la tabla después de la eliminación exitosa
            tableView.getItems().remove(proveedorSeleccionado);

            LOGGER.info("Proveedor eliminado correctamente.");
        } catch (WebApplicationException e) {
            LOGGER.severe("Error al eliminar el almacén: " + e.getMessage());
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Error al eliminar almacén");
            error.setContentText("No se pudo eliminar el almacén. Por favor, inténtelo de nuevo.");
            error.show();
        }

    }

    private void menuTrabajador(ActionEvent event) {
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
