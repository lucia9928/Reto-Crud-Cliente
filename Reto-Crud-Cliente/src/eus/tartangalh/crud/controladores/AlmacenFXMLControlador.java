/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.controladores;

import eus.tartangalh.crud.entidades.Almacen;
import eus.tartangalh.crud.interfaces.AlmacenFactoria;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javax.ws.rs.core.GenericType;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javax.ws.rs.WebApplicationException;

/**
 * FXML Controller class
 *
 * @author Oscar
 */
public class AlmacenFXMLControlador {

    @FXML
    private DatePicker desdeDatePicker;

    @FXML
    private DatePicker hastaDatePicker;

    @FXML
    private TextField idField;

    @FXML
    private TextField nombreField;

    @FXML
    private TextField ciudadField;

    @FXML
    private TextField paisField;

    @FXML
    private Button searchButton;

    @FXML
    private TableView<Almacen> almacenTableView;

    @FXML
    private TableColumn<Almacen, Integer> idAlmacenColumn;

    @FXML
    private TableColumn<Almacen, String> paisColumn;

    @FXML
    private TableColumn<Almacen, String> ciudadColumn;

    @FXML
    private TableColumn<Almacen, Date> fechaAdquisicionColumn;

    @FXML
    private TableColumn<Almacen, Integer> metrosCuadradosColumn;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button confirmButton; // Hay que quitar esto 

    private Stage stage;

    private static final Logger LOGGER = Logger.getLogger("ProveedorControlador.view");

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) {
        LOGGER.info("Inicializando controlador de almacen");

        Scene scene = new Scene(root);

        stage.show();
        stage.setScene(scene);

        // Configurar las columnas de la tabla con el tipo correcto
        idAlmacenColumn.setCellValueFactory(new PropertyValueFactory<>("idAlmacen"));
        paisColumn.setCellValueFactory(new PropertyValueFactory<>("pais"));
        ciudadColumn.setCellValueFactory(new PropertyValueFactory<>("ciudad"));

        // Cambiar el tipo de la columna metrosCuadrados a Integer
        metrosCuadradosColumn.setCellValueFactory(new PropertyValueFactory<>("metrosCuadrados"));

        // Cambiar el tipo de la columna fechaAdquisicion a LocalDate
        fechaAdquisicionColumn.setCellValueFactory(new PropertyValueFactory<>("fechaAdquisicion"));

        // Llamada al servicio para obtener los productos
        try {
            // Obtener los productos desde la API o servicio
            List<Almacen> almacenesEncontrados = AlmacenFactoria.get()
                    .findAll_XML(new GenericType<List<Almacen>>() {
                    });

            // Convertir la lista de productos a un ObservableList
            ObservableList<Almacen> almacenes = FXCollections.observableArrayList(almacenesEncontrados);

            // Establecer los productos obtenidos en la TableView
            almacenTableView.setItems(almacenes);
            configureTableEditable();

        } catch (Exception e) {
            // Manejo de excepciones si ocurre un error en la conexión o en la obtención de productos
            System.out.println("Error al cargar los productos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void configureTableEditable() {
        almacenTableView.setEditable(true);

        paisColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        paisColumn.setOnEditCommit(event -> {
            Almacen almacen = event.getRowValue();
            almacen.setPais(event.getNewValue());
            AlmacenFactoria.get().actualizarAlmacen_XML(almacen);
        });

        ciudadColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ciudadColumn.setOnEditCommit(event -> {
            Almacen almacen = event.getRowValue();
            almacen.setCiudad(event.getNewValue());
            AlmacenFactoria.get().actualizarAlmacen_XML(almacen);
        });

        // Configurar la columna metrosCuadrados para manejar Integer
        // Configurar la columna metrosCuadrados para manejar Integer
        metrosCuadradosColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        metrosCuadradosColumn.setOnEditCommit(event -> {
            Almacen almacen = event.getRowValue();
            almacen.setMetrosCuadrados(event.getNewValue());
            System.out.println(almacen.toString());
            AlmacenFactoria.get().actualizarAlmacen_XML(almacen);
        });

        // Fecha
        fechaAdquisicionColumn.setCellFactory(col -> new TableCell<Almacen, Date>() {
            private final DatePicker datePicker = new DatePicker();

            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);  // No mostrar nada si la celda está vacía
                } else {
                    // Convertir java.sql.Date a LocalDate
                    if (item != null) {
                        LocalDate localDate = item.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); // Convertir java.util.Date a LocalDate
                        datePicker.setValue(localDate);  // Establecer la fecha en el DatePicker
                    }
                    setGraphic(datePicker);  // Mostrar el DatePicker en la celda
                }
            }

            @Override
            public void commitEdit(Date newValue) {
                super.commitEdit(newValue);
                Almacen almacen = getTableView().getItems().get(getIndex());  // Obtener el objeto correspondiente

                if (newValue != null) {
                    almacen.setFechaAdquisicion(newValue); // Actualizar la fecha en el objeto almacen
                }

                System.out.println(almacen.toString());  // Mostrar los cambios en consola

                // Llamar al servicio para actualizar el almacen
                AlmacenFactoria.get().actualizarAlmacen_XML(almacen);
            }

            // Forzar el commit cuando se pierde el foco
            {
                datePicker.focusedProperty().addListener((obs, oldFocus, newFocus) -> {
                    if (!newFocus) {
                        // Convertir LocalDate a java.util.Date
                        Date newDate = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        commitEdit(newDate);  // Ejecutar commit cuando el foco se pierde
                    }
                });
            }
        });

    }

    @FXML
    private void handleAddRow() {

        // Crear una fila vacía con valores por defecto o nulos
        Almacen nuevoAlmacen = new Almacen(0, "", "", 0, null);

        // Obtener la lista observable de la tabla y añadir la nueva fila vacía
        ObservableList<Almacen> almacenes = almacenTableView.getItems();
        almacenes.add(nuevoAlmacen);

        LOGGER.info("Nueva fila vacía añadida.");
    }

    @FXML
    private void handleConfirmEdit() {
        almacenTableView.getItems().forEach(almacen -> {
            // Validación simple para asegurar que los campos esenciales no estén vacíos
            if (almacen.getPais().isEmpty() || almacen.getCiudad().isEmpty()) {
                LOGGER.warning("Fila con datos incompletos.");
            } else {
                LOGGER.info("Fila guardada: " + almacen.toString());
                // Aquí puedes agregar la lógica para guardar en la base de datos si es necesario
                AlmacenFactoria.get().actualizarAlmacen_XML(almacen);
            }
        });
    }

    @FXML
    private void handleDeleteRow() {
        Almacen almacenSeleccionado = almacenTableView.getSelectionModel().getSelectedItem();

        if (almacenSeleccionado != null) {
            // Mostrar una alerta de confirmación antes de proceder con el borrado
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("Eliminar almacén");
            confirmacion.setContentText("¿Estás seguro de que deseas eliminar el almacén con ID: " + almacenSeleccionado.getIdAlmacen() + "?");

            confirmacion.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        // Llamada al método para borrar el almacén en el servicio REST
                        AlmacenFactoria.get().borrarAlmacen(almacenSeleccionado.getIdAlmacen());

                        // Remover la fila de la tabla después de la eliminación exitosa
                        almacenTableView.getItems().remove(almacenSeleccionado);

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
        } else {
            // Si no se ha seleccionado ninguna fila, mostrar un mensaje de advertencia
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Advertencia");
            warning.setHeaderText("Ninguna fila seleccionada");
            warning.setContentText("Por favor, seleccione un almacén para eliminar.");
            warning.show();
        }
    }

}
