/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.controladores;

import eus.tartangalh.crud.entidades.CategoriaProducto;
import eus.tartangalh.crud.entidades.ProductoFarmaceutico;
import eus.tartangalh.crud.interfaces.ProductoInterfazFactoria;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

/**
 * FXML Controller class
 *
 * @author Oscar
 */
public class ProductoFarmaceuticoUIController {

    @FXML
    private DatePicker desdeDatePicker;

    @FXML
    private DatePicker hastaDatePicker;

    @FXML
    private TextField idField;

    @FXML
    private TextField nombreField;

    @FXML
    private TextField loteField;

    @FXML
    private TextField catField;

    @FXML
    private Button searchButton;

    @FXML
    private TableView<ProductoFarmaceutico> tableView;

    @FXML
    private TableColumn<ProductoFarmaceutico, String> idColumn;

    @FXML
    private TableColumn<ProductoFarmaceutico, String> nombreColumn;

    @FXML
    private TableColumn<ProductoFarmaceutico, String> loteColumn;

    @FXML
    private TableColumn<ProductoFarmaceutico, Date> caducidadColumn;

    @FXML
    private TableColumn<ProductoFarmaceutico, String> descripcionColumn;

    @FXML
    private TableColumn<ProductoFarmaceutico, CategoriaProducto> categoriaColumn;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button confirmButton;

    private Stage stage;

    private static final Logger LOGGER = Logger.getLogger("productoFarmaceuticoControlador.view");

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) {
        LOGGER.info("Inicializando controlador de producto farmaceutico");

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        configureTableColumns();

        // Llamada al servicio para obtener los productos
        try {
            List<ProductoFarmaceutico> productosRecibidos = ProductoInterfazFactoria.get()
                    .encontrarTodos_XML(new GenericType<List<ProductoFarmaceutico>>() {
                    });

            ObservableList<ProductoFarmaceutico> productos = FXCollections.observableArrayList(productosRecibidos);
            tableView.setItems(productos);
            configureTableEditable();

        } catch (Exception e) {
            LOGGER.severe("Error al cargar los productos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void configureTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        loteColumn.setCellValueFactory(new PropertyValueFactory<>("loteProducto"));
        caducidadColumn.setCellValueFactory(new PropertyValueFactory<>("fechaCaducidad"));
        descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));
    }

    private void configureTableEditable() {
        tableView.setEditable(true);

        nombreColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nombreColumn.setOnEditCommit(event -> {
            ProductoFarmaceutico producto = event.getRowValue();
            producto.setNombreProducto(event.getNewValue());
            ProductoInterfazFactoria.get().actualizarProducto_XML(producto);
        });

        loteColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        loteColumn.setOnEditCommit(event -> {
            ProductoFarmaceutico producto = event.getRowValue();
            producto.setLoteProducto(event.getNewValue());
            ProductoInterfazFactoria.get().actualizarProducto_XML(producto);
        });

        categoriaColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(
                FXCollections.observableArrayList(CategoriaProducto.values())
        ));
        categoriaColumn.setOnEditCommit(event -> {
            ProductoFarmaceutico producto = event.getRowValue();
            try {
                CategoriaProducto nuevaCategoria = event.getNewValue();
                producto.setCategoria(nuevaCategoria);
                ProductoInterfazFactoria.get().actualizarProducto_XML(producto);
            } catch (Exception e) {
                LOGGER.warning("Error al actualizar categoría: " + e.getMessage());
            }
        });

        caducidadColumn.setCellFactory(col -> new TableCell<ProductoFarmaceutico, Date>() {
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
                ProductoFarmaceutico producto = getTableView().getItems().get(getIndex());  // Obtener el objeto correspondiente

                if (newValue != null) {
                    producto.setFechaCaducidad(newValue); // Actualizar la fecha en el objeto Producto
                }

                System.out.println(producto.toString());  // Mostrar los cambios en consola

                // Llamar al servicio para actualizar el producto
                ProductoInterfazFactoria.get().actualizarProducto_XML(producto);
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
        ProductoFarmaceutico nuevoProducto = new ProductoFarmaceutico();
        nuevoProducto.setIdProducto(0); // ID por defecto
        nuevoProducto.setNombreProducto("");
        nuevoProducto.setLoteProducto("");
        nuevoProducto.setFechaCaducidad(null);
        nuevoProducto.setDescription("");
        nuevoProducto.setCategoria(null);
        nuevoProducto.setPrecio(0.0f);

        // Obtener la lista observable de la tabla y añadir la nueva fila vacía
        ObservableList<ProductoFarmaceutico> productos = tableView.getItems();
        productos.add(nuevoProducto);

        LOGGER.info("Nueva fila vacía añadida.");
    }

    @FXML
    private void handleConfirmEdit() {
        tableView.getItems().forEach(producto -> {
            // Validación simple para asegurar que los campos esenciales no estén vacíos
            if (producto.getNombreProducto() == null || producto.getNombreProducto().isEmpty()
                    || producto.getLoteProducto() == null || producto.getLoteProducto().isEmpty()
                    || producto.getFechaCaducidad() == null
                    || producto.getCategoria() == null
                    || producto.getPrecio() == null || producto.getPrecio() <= 0) {

                LOGGER.warning("Fila con datos incompletos o inválidos: " + producto);
            } else {
                LOGGER.info("Producto guardado: " + producto);
                // Aquí puedes agregar la lógica para guardar en la base de datos si es necesario
                ProductoInterfazFactoria.get().actualizarProducto_XML(producto);
            }
        });
    }

    @FXML
    private void handleDeleteRow() {
        ProductoFarmaceutico productoSeleccionado = tableView.getSelectionModel().getSelectedItem();

        if (productoSeleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("Eliminar producto");
            confirmacion.setContentText("¿Estás seguro de que deseas eliminar el producto con ID: "
                    + productoSeleccionado.getIdProducto() + "?");

            confirmacion.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        ProductoInterfazFactoria.get().borrarProducto(String.valueOf(productoSeleccionado.getIdProducto()));
                        tableView.getItems().remove(productoSeleccionado);
                        LOGGER.info("Producto eliminado correctamente.");
                    } catch (WebApplicationException e) {
                        LOGGER.severe("Error al eliminar el producto: " + e.getMessage());
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Error");
                        error.setHeaderText("Error al eliminar producto");
                        error.setContentText("No se pudo eliminar el producto. Por favor, inténtelo de nuevo.");
                        error.show();
                    }
                }
            });
        } else {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Advertencia");
            warning.setHeaderText("Ninguna fila seleccionada");
            warning.setContentText("Por favor, seleccione un producto para eliminar.");
            warning.show();
        }
    }
}
