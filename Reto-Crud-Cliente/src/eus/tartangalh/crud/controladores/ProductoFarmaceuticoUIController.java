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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
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
    private DatePicker txtFechaDesde;

    @FXML
    private DatePicker txtFechaHasta;
    @FXML
    private TextField txtFiltro;

    @FXML
    private ChoiceBox<CategoriaProducto> catField;
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
    private ComboBox<String> combo;

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
        mostrarProductos();
        configureTableEditable();
        ocultarTodosLosFiltros();
        listarFiltros();

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
        descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
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

        descripcionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descripcionColumn.setOnEditCommit(event -> {
            ProductoFarmaceutico producto = event.getRowValue();
            producto.setDescription(event.getNewValue());
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
    private void añadirFila() {
        try {
            ProductoFarmaceutico producto = new ProductoFarmaceutico();
            ProductoInterfazFactoria.get().crearProducto_XML(producto); // Crear el producto en la base de datos
            mostrarProductos();
        } catch (Exception e) {
            LOGGER.severe("Error al crear producto farmacéutico: " + e.getMessage());
        }
    }

    /**
     * Borra el producto farmacéutico seleccionado en la tabla.
     */
    @FXML
    private void borrarFila() {
        ProductoFarmaceutico productoSeleccionado = tableView.getSelectionModel().getSelectedItem();
        if (productoSeleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("Eliminar producto");
            confirmacion.setContentText("¿Estás seguro de que deseas eliminar el producto con ID: " + productoSeleccionado.getIdProducto() + "?");

            confirmacion.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        ProductoInterfazFactoria.get().borrarProducto(String.valueOf(productoSeleccionado.getIdProducto()));
                        tableView.getItems().remove(productoSeleccionado);
                        LOGGER.info("Producto eliminado correctamente.");
                    } catch (WebApplicationException e) {
                        LOGGER.severe("Error al eliminar el producto: " + e.getMessage());
                    }
                }
            });
        }
    }

    /**
     * Obtiene y muestra los productos farmacéuticos desde la base de datos.
     */
    private void mostrarProductos() {
        try {
            List<ProductoFarmaceutico> productosEncontrados = ProductoInterfazFactoria.get().encontrarTodos_XML(new GenericType<List<ProductoFarmaceutico>>() {
            });
            ObservableList<ProductoFarmaceutico> productos = FXCollections.observableArrayList(productosEncontrados);
            tableView.setItems(productos);
        } catch (Exception e) {
            LOGGER.severe("Error al cargar los productos farmacéuticos: " + e.getMessage());
        }
    }

    private void listarFiltros() {
        ObservableList<String> opcionesFiltro = FXCollections.observableArrayList(
                "ID",
                "Fecha de caducidad",
                "Categoría",
                "Nombre",
                "Lote"
        );

        combo.setItems(opcionesFiltro);

        combo.setOnAction(event -> {
            ocultarTodosLosFiltros();

            switch (combo.getValue().toString()) {
                case "ID":
                    txtFiltro.setVisible(true);
                    txtFiltro.setPromptText("Introduce ID");
                    break;
                case "Fecha de caducidad":
                    txtFechaDesde.setVisible(true);
                    txtFechaDesde.setPromptText("Introduce Fecha Hasta");
                    txtFechaHasta.setVisible(true);
                    txtFechaHasta.setPromptText("Introduce Fecha Desde");
                    break;
                case "Categoría":
                    catField.setVisible(true);
                    catField.setItems(FXCollections.observableArrayList(CategoriaProducto.values()));
                    break;
                case "Nombre":
                    txtFiltro.setVisible(true);
                    txtFiltro.setPromptText("Introduce Nombre");
                    break;
                case "Lote":
                    txtFiltro.setVisible(true);
                    txtFiltro.setPromptText("Introduce Lote");
                    break;
                default:
                    break;
            }
        });
    }

    private void ocultarTodosLosFiltros() {
        txtFiltro.setVisible(false);
        txtFechaDesde.setVisible(false);
        txtFechaHasta.setVisible(false);
        catField.setVisible(false);
        txtFiltro.setVisible(false);
        txtFiltro.setVisible(false);
    }

    private void filtrarPorFecha() {
        // Aquí puedes implementar la lógica para filtrar por fecha si es necesario
    }
}
