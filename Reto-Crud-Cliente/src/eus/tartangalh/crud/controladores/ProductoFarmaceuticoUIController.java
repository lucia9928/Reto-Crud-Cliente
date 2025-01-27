/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.controladores;

import eus.tartangalh.crud.entidades.Almacen;
import eus.tartangalh.crud.entidades.CategoriaProducto;
import eus.tartangalh.crud.entidades.ProductoFarmaceutico;
import eus.tartangalh.crud.interfaces.ProductoInterfazFactoria;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Oscar
 */
public class ProductoFarmaceuticoUIController {

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
        LOGGER.info("Inicializando controlador de producto farmacéutico");

        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("recursos/iconoFarmacia.png"));
        stage.setTitle("GESTION PRODUCTOS FARMACEUTICOS");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();  // Consumir el evento para manejarlo manualmente
                manejoCierre();
            }
        });

        configurarColumnasTabla();
        mostrarProductos();
        configurarTablaEditable();
        ocultarTodosLosFiltros();
        listarFiltros();

        try {
            List<ProductoFarmaceutico> productosRecibidos = ProductoInterfazFactoria.get()
                    .encontrarTodos_XML(new GenericType<List<ProductoFarmaceutico>>() {
                    });

            ObservableList<ProductoFarmaceutico> productos = FXCollections.observableArrayList(productosRecibidos);
            tableView.setItems(productos);
        } catch (Exception e) {
            LOGGER.severe("Error al cargar los productos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void configurarColumnasTabla() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        loteColumn.setCellValueFactory(new PropertyValueFactory<>("loteProducto"));
        caducidadColumn.setCellValueFactory(new PropertyValueFactory<>("fechaCaducidad"));
        descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));
    }

    private void configurarTablaEditable() {
        tableView.setEditable(true);

        // Configurar columna para Nombre
        nombreColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nombreColumn.setOnEditCommit(event -> {
            ProductoFarmaceutico producto = event.getRowValue();
            producto.setNombreProducto(event.getNewValue());
            ProductoInterfazFactoria.get().actualizarProducto_XML(producto);
        });

        // Configurar columna para Descripción
        descripcionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descripcionColumn.setOnEditCommit(event -> {
            ProductoFarmaceutico producto = event.getRowValue();
            producto.setDescripcion(event.getNewValue());
            ProductoInterfazFactoria.get().actualizarProducto_XML(producto);
        });

        // Configurar columna para Lote
        loteColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        loteColumn.setOnEditCommit(event -> {
            ProductoFarmaceutico producto = event.getRowValue();
            producto.setLoteProducto(event.getNewValue());
            ProductoInterfazFactoria.get().actualizarProducto_XML(producto);
        });

        // Configurar columna para Fecha de Caducidad con auto-guardado al perder el foco
        caducidadColumn.setCellFactory(col -> new TableCell<ProductoFarmaceutico, Date>() {
            private final DatePicker datePicker = new DatePicker();

            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null); // No mostrar nada si la celda está vacía
                } else {
                    if (item != null) {
                        LocalDate localDate = item.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); // Convertir java.util.Date a LocalDate
                        datePicker.setValue(localDate); // Establecer el valor actual en el DatePicker
                    }
                    setGraphic(datePicker); // Mostrar el DatePicker en la celda
                }
            }

            @Override
            public void commitEdit(Date newValue) {
                super.commitEdit(newValue);
                ProductoFarmaceutico producto = getTableView().getItems().get(getIndex()); // Obtener el producto correspondiente

                if (newValue != null) {
                    producto.setFechaCaducidad(newValue); // Actualizar el valor de fecha en el objeto
                }

                // Llamar al servicio para actualizar la base de datos
                try {
                    ProductoInterfazFactoria.get().actualizarProducto_XML(producto);
                    LOGGER.info("Producto actualizado correctamente: " + producto.toString());
                } catch (Exception e) {
                    LOGGER.severe("Error al actualizar producto: " + e.getMessage());
                }
            }

            // Forzar el commit cuando el DatePicker pierde el foco
            {
                datePicker.focusedProperty().addListener((obs, oldFocus, newFocus) -> {
                    if (!newFocus) {
                        if (datePicker.getValue() != null) {
                            // Convertir LocalDate a java.util.Date
                            Date newDate = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                            commitEdit(newDate); // Guardar los cambios al perder el foco
                        }
                    }
                });
            }
        });

        // Configurar columna para Categoría
        categoriaColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(
                FXCollections.observableArrayList(CategoriaProducto.values())
        ));
        categoriaColumn.setOnEditCommit(event -> {
            ProductoFarmaceutico producto = event.getRowValue();
            try {
                producto.setCategoria(event.getNewValue());
                ProductoInterfazFactoria.get().actualizarProducto_XML(producto);
            } catch (Exception e) {
                LOGGER.warning("Error al actualizar categoría: " + e.getMessage());
            }
        });
    }

    @FXML
    private void añadirFila() {
        try {
            ProductoFarmaceutico producto = new ProductoFarmaceutico();
            ProductoInterfazFactoria.get().crearProducto_XML(producto);
            mostrarProductos();
        } catch (Exception e) {
            LOGGER.severe("Error al crear producto farmacéutico: " + e.getMessage());
        }
    }

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
                "ID", "Fecha de caducidad", "Categoría", "Nombre", "Lote"
        );
        combo.setItems(opcionesFiltro);

        combo.setOnAction(event -> {
            ocultarTodosLosFiltros();
            String filtroSeleccionado = combo.getValue();

            if (filtroSeleccionado != null) {
                switch (filtroSeleccionado) {
                    case "ID":
                    case "Nombre":
                    case "Lote":
                        txtFiltro.setVisible(true);
                        txtFiltro.setPromptText("Introduce " + filtroSeleccionado.toLowerCase());
                        break;
                    case "Fecha de caducidad":
                        txtFechaDesde.setVisible(true);
                        txtFechaHasta.setVisible(true);
                        break;
                    case "Categoría":
                        catField.setVisible(true);
                        catField.setItems(FXCollections.observableArrayList(CategoriaProducto.values()));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void ocultarTodosLosFiltros() {
        txtFiltro.setVisible(false);
        txtFechaDesde.setVisible(false);
        txtFechaHasta.setVisible(false);
        catField.setVisible(false);
    }

    @FXML
    private void buscarFiltro() {
        String filtroSeleccionado = combo.getValue();

        if (filtroSeleccionado == null) {
            mostrarProductos();
            return;
        }

        try {
            switch (filtroSeleccionado) {
                case "ID":
                    handleIdFilter();
                    break;

                case "Fecha de caducidad":
                    handleFechaFilter();
                    break;

                case "Categoría":
                    handleCategoriaFilter();
                    break;

                case "Nombre":
                    handleNombreFilter();
                    break;

                case "Lote":
                    handleLoteFilter();
                    break;

                default:
                    mostrarProductos();
                    break;
            }
        } catch (Exception e) {
            LOGGER.severe("Error al buscar productos: " + e.getMessage());
        }
    }

    private void handleIdFilter() {
        String id = txtFiltro.getText();
        if (id != null && !id.isEmpty()) {
            buscarPorId(id);
        } else {
            mostrarProductos();
        }
    }

    private void handleFechaFilter() {
        LocalDate fechaDesde = txtFechaDesde.getValue();
        LocalDate fechaHasta = txtFechaHasta.getValue();

        // Validar que ambas fechas sean proporcionadas
        if (fechaDesde == null || fechaHasta == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Fechas no válidas");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecciona ambas fechas: Fecha Desde y Fecha Hasta.");
            alert.showAndWait();
            return;
        }

        // Validar que la fecha "Desde" no sea posterior a la fecha "Hasta"
        if (fechaDesde.isAfter(fechaHasta)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Rango de fechas inválido");
            alert.setHeaderText(null);
            alert.setContentText("La fecha 'Desde' no puede ser posterior a la fecha 'Hasta'.");
            alert.showAndWait();
            return;
        }

        // Realizar la búsqueda por rango de fechas
        buscarPorFecha(fechaDesde, fechaHasta);
    }

    private void handleCategoriaFilter() {
        CategoriaProducto categoria = catField.getValue();

        if (categoria != null) {
            buscarPorCategoria(categoria);
        } else {
            mostrarProductos();
        }
    }

    private void handleNombreFilter() {
        String nombre = txtFiltro.getText();
        if (nombre != null && !nombre.isEmpty()) {
            buscarPorNombre(nombre);
        } else {
            mostrarProductos();
        }
    }

    private void handleLoteFilter() {
        String lote = txtFiltro.getText();
        if (lote != null && !lote.isEmpty()) {
            buscarPorLote(lote);
        } else {
            mostrarProductos();
        }
    }

    private void buscarPorId(String id) {
        try {
            ProductoFarmaceutico producto = ProductoInterfazFactoria.get().encontrarProducto_XML(
                    new GenericType<ProductoFarmaceutico>() {
            }, id);
            if (producto != null) {
                tableView.setItems(FXCollections.observableArrayList(producto));
            }
        } catch (Exception e) {
            LOGGER.severe("Error al buscar producto por ID: " + e.getMessage());
        }
    }

    private void buscarPorFecha(LocalDate desde, LocalDate hasta) {
        try {
            List<ProductoFarmaceutico> productos = ProductoInterfazFactoria.get().encontrarTodos_XML(
                    new GenericType<List<ProductoFarmaceutico>>() {
            });
            List<ProductoFarmaceutico> filtrados = productos.stream()
                    .filter(p -> !p.getFechaCaducidad().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    .isBefore(desde) && !p.getFechaCaducidad().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    .isAfter(hasta))
                    .collect(Collectors.toList());
            tableView.setItems(FXCollections.observableArrayList(filtrados));
        } catch (Exception e) {
            LOGGER.severe("Error al buscar por fecha: " + e.getMessage());
        }
    }

    private void buscarPorCategoria(CategoriaProducto categoria) {
        try {
            List<ProductoFarmaceutico> productos = ProductoInterfazFactoria.get().encontrarTodos_XML(
                    new GenericType<List<ProductoFarmaceutico>>() {
            });
            List<ProductoFarmaceutico> filtrados = productos.stream()
                    .filter(p -> p.getCategoria() == categoria)
                    .collect(Collectors.toList());
            tableView.setItems(FXCollections.observableArrayList(filtrados));
        } catch (Exception e) {
            LOGGER.severe("Error al buscar por categoría: " + e.getMessage());
        }
    }

    private void buscarPorNombre(String nombre) {
        try {
            List<ProductoFarmaceutico> productos = ProductoInterfazFactoria.get().encontrarTodos_XML(
                    new GenericType<List<ProductoFarmaceutico>>() {
            });
            List<ProductoFarmaceutico> filtrados = productos.stream()
                    .filter(p -> p.getNombreProducto().toLowerCase().contains(nombre.toLowerCase()))
                    .collect(Collectors.toList());
            tableView.setItems(FXCollections.observableArrayList(filtrados));
        } catch (Exception e) {
            LOGGER.severe("Error al buscar por nombre: " + e.getMessage());
        }
    }

    @FXML
    private void recargarTabla() {
        mostrarProductos();
    }

    private void buscarPorLote(String lote) {
        try {
            List<ProductoFarmaceutico> productos = ProductoInterfazFactoria.get().encontrarTodos_XML(
                    new GenericType<List<ProductoFarmaceutico>>() {
            });
            List<ProductoFarmaceutico> filtrados = productos.stream()
                    .filter(p -> p.getLoteProducto().toLowerCase().contains(lote.toLowerCase()))
                    .collect(Collectors.toList());
            tableView.setItems(FXCollections.observableArrayList(filtrados));
        } catch (Exception e) {
            LOGGER.severe("Error al buscar por lote: " + e.getMessage());
        }
    }

    private void manejoCierre() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Está seguro de que desea cerrar la aplicación?");
        alert.setContentText("Todos los cambios no guardados se perderán.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();
        }
    }
    @FXML
    private void imprimirInforme() {
        try {
            
            JasperReport report=JasperCompileManager.compileReport("src/recursos/ProductoReport.jrxml");

            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<ProductoFarmaceutico>)this.tableView.getItems());
            
            Map<String, Object> parameters = new HashMap<>();
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            
            JasperViewer jasperViewer = new JasperViewer(jasperPrint);
            
            jasperViewer.setVisible(true);

        } catch (JRException e) {
            LOGGER.severe("Error al generar el reporte: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al generar el reporte.");
            alert.showAndWait();
        }
    }
}
