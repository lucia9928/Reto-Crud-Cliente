package eus.tartangalh.crud.controladores;

import eus.tartangalh.crud.entidades.Cliente;
import eus.tartangalh.crud.entidades.ProductoFarmaceutico;
import eus.tartangalh.crud.entidades.RecetaMedica;
import eus.tartangalh.crud.interfaces.ProductoInterfazFactoria;
import eus.tartangalh.crud.interfaces.RecetaMedicaFactoria;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JFrame;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;
import javax.xml.transform.Templates;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class RecetaMedicaFXMLController {

    @FXML
    private DatePicker desde;
    @FXML
    private DatePicker hasta;
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnCrearReceta;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnImprimir;
    @FXML
    private TableView<RecetaMedica> tableRecetas;
    @FXML
    private TableColumn<RecetaMedica, String> colId;
    @FXML
    private TableColumn<RecetaMedica, String> colCliente;
    @FXML
    private TableColumn<RecetaMedica, String> colProductos;
    @FXML
    private TableColumn<RecetaMedica, String> colDescripcion;
    @FXML
    private TableColumn<RecetaMedica, Date> colFecha;
    @FXML
    private Button añadirBtn;

    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger("RecetaMedicaFXMLController.view");
    private Cliente cliente;

    /**
     * Establece el cliente actual.
     *
     * @param cliente El cliente asociado al controlador.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Establece el escenario (ventana) actual.
     *
     * @param stage La ventana principal de la aplicación.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Inicializa la interfaz gráfica para la vista de recetas médicas.
     * Configura los botones, tablas y otras interacciones con el usuario.
     *
     * @param root El nodo raíz que contiene la vista FXML.
     */
    public void initStage(Parent root) {
        LOGGER.info("Inicializando controlador de receta médica");
        Scene scene = new Scene(root);
        stage.setTitle("Recetas Médicas");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                manejoCierre();
            }
        });

        configurarTabla();
        configurarTablaEditable();
    }

    /**
     * Configura las columnas de la tabla para mostrar las recetas médicas.
     * También define la lógica para mostrar los productos asociados a cada
     * receta.
     */
    private void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idReceta"));

        // Configuración de la columna de productos
        colProductos.setCellFactory(column -> new TableCell<RecetaMedica, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null) {
                    setText(null);
                } else {
                    RecetaMedica receta = (RecetaMedica) getTableRow().getItem();
                    if (receta != null) {
                        List<ProductoFarmaceutico> productos = buscarProductosPorReceta(receta.getIdReceta().toString());
                        String nombres = productos.stream()
                                .map(ProductoFarmaceutico::getNombreProducto)
                                .collect(Collectors.joining(", "));
                        setText(nombres);
                    }
                }
            }
        });

        colCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaReceta"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        cargarRecetas();
    }

    /**
     * Carga todas las recetas médicas desde el servidor y las muestra en la
     * tabla.
     */
    private void cargarRecetas() {
        try {
            List<RecetaMedica> recetas = RecetaMedicaFactoria.get().encontrarTodasLasRecetas_XML(new GenericType<List<RecetaMedica>>() {
            });
            if (recetas != null && !recetas.isEmpty()) {
                ObservableList<RecetaMedica> recetaList = FXCollections.observableArrayList(recetas);
                tableRecetas.setItems(recetaList);
            } else {
                LOGGER.warning("No se encontraron recetas médicas.");
            }
        } catch (WebApplicationException e) {
            LOGGER.log(Level.SEVERE, "Error al cargar recetas médicas: {0}", e.getMessage());
        }
    }

    /**
     * Busca los productos farmacéuticos asociados a una receta médica por su
     * ID.
     *
     * @param idReceta El ID de la receta médica.
     * @return Una lista de productos farmacéuticos asociados a la receta.
     */
    private List<ProductoFarmaceutico> buscarProductosPorReceta(String idReceta) {
        try {
            List<ProductoFarmaceutico> productos = RecetaMedicaFactoria.get().obtenerProductosPorReceta(new GenericType<List<ProductoFarmaceutico>>() {
            }, idReceta);
            return productos;
        } catch (WebApplicationException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar productos: {0}", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Configura las columnas de la tabla para que sean editables y definidas
     * por un formato específico.
     */
    private void configurarTablaEditable() {
        tableRecetas.setEditable(true);

        // Columna Descripción (Editable)
        colDescripcion.setCellFactory(TextFieldTableCell.forTableColumn());
        colDescripcion.setOnEditCommit(event -> {
            RecetaMedica receta = event.getRowValue();
            receta.setDescripcion(event.getNewValue());
            RecetaMedicaFactoria.get().modificarRecetaMedica_XML(receta);
        });
        // Columna Fecha Receta (Editable con DatePicker)
        colFecha.setCellFactory(col -> new TableCell<RecetaMedica, Date>() {
            private final DatePicker datePicker = new DatePicker();

            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    if (item != null) {
                        LocalDate localDate = item.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        datePicker.setValue(localDate);
                    }
                    setGraphic(datePicker);
                }
            }

            @Override
            public void commitEdit(Date newValue) {
                super.commitEdit(newValue);
                RecetaMedica receta = getTableView().getItems().get(getIndex());

                if (newValue != null && newValue.after(new Date())) {
                    receta.setFechaReceta(newValue);
                    ProductoInterfazFactoria.get().actualizarProducto_XML(receta);
                } else {

                    tableRecetas.refresh();
                }
            }

            {
                datePicker.focusedProperty().addListener((obs, oldFocus, newFocus) -> {
                    if (!newFocus && datePicker.getValue() != null) {
                        Date newDate = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        commitEdit(newDate);
                    }
                });
            }
        });
    }

    /**
     * Añade una nueva fila vacía a la tabla de recetas médicas.
     */
    @FXML
    private void aniadirUnaLinea() {
        try {
            tableRecetas.scrollTo(tableRecetas.getItems().size() - 1);
            RecetaMedica receta = new RecetaMedica();
            RecetaMedicaFactoria.get().crearRecetaMedica_XML(receta);
            mostrarRecetas();
        } catch (Exception e) {
            LOGGER.severe("Error al crear Redceta Medica: " + e.getMessage());
        }
    }

    /**
     * Maneja la acción de búsqueda de recetas médicas. Actualmente solo está
     * configurada para loguear la acción.
     */
    @FXML
    private void buscarRecetaPorFecha() {
        LOGGER.info("Buscando recetas médicas por filtros.");
        LocalDate fechaDesde = desde.getValue();
        LocalDate fechaHasta = hasta.getValue();

        if (fechaDesde != null && fechaHasta != null) {
            if (fechaDesde.isAfter(fechaHasta)) {
                LocalDate temp = fechaDesde;
                fechaDesde = fechaHasta;
                fechaHasta = temp;
            }
            buscarPorFecha(fechaDesde, fechaHasta);
        }
    }

    private void buscarPorFecha(LocalDate fechaDesde, LocalDate fechaHasta) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            String strFechaDesde = fechaDesde.format(formatter);
            String strFechaHasta = fechaHasta.format(formatter);
            List<RecetaMedica> recetas = RecetaMedicaFactoria.get().encontrarRecetasPorFecha_XML(new GenericType<List<RecetaMedica>>() {
            }, strFechaDesde, strFechaHasta);

            List<RecetaMedica> resultado = recetas.stream()
                    .filter(receta -> {
                        LocalDate fechaReceta = receta.getFechaReceta().toInstant()
                                .atZone(ZoneId.of("UTC"))
                                .toLocalDate();
                        return !fechaReceta.isBefore(fechaDesde) && !fechaReceta.isAfter(fechaHasta);
                    })
                    .collect(Collectors.toList());

            ObservableList<RecetaMedica> observableResultado = FXCollections.observableArrayList(resultado);
            tableRecetas.setItems(observableResultado);
        } catch (Exception e) {
            LOGGER.severe("Error al buscar almacenes por fecha: " + e.getMessage());
        }
    }

    /**
     * Vuelve al menú principal del cliente.
     *
     * @param event El evento asociado al botón "Atras".
     */
    @FXML
    private void menuCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto/crud/cliente/MenuClienteFXML.fxml"));
            Parent root = loader.load();
            MenuClienteFXMLController menuCliente = loader.getController();
            menuCliente.setStage(stage);
            menuCliente.setCliente(cliente);
            menuCliente.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(MenuTrabajadorFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void manejoCierre() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Está seguro de que desea cerrar la aplicación?");
        alert.setContentText("Todos los cambios no guardados se perderán.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) btnBuscar.getScene().getWindow();
            stage.close();
        }
    }

    private void mostrarRecetas() {
        try {
            List<RecetaMedica> recetasEncontradas = RecetaMedicaFactoria.get().encontrarTodasLasRecetas_XML(new GenericType<List<RecetaMedica>>() {
            });
            ObservableList<RecetaMedica> recetas = FXCollections.observableArrayList(recetasEncontradas);
            tableRecetas.setItems(recetas); // Actualiza la vista con los almacenes obtenidos
        } catch (Exception e) {
            LOGGER.severe("Error al cargar las Recetas: " + e.getMessage());
        }
    }

    @FXML
    private void recargarTabla() {
        mostrarRecetas(); // Llama al método para mostrar los almacenes
    }

    @FXML
    private void imprimirInforme() {
        try {
            // Compilar el reporte desde el archivo JRXML
            JasperReport report = JasperCompileManager.compileReport("src/recursos/RecetaReport.jrxml");

            // Obtener los datos de la tabla como fuente de datos
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource(
                    (Collection<RecetaMedica>) this.tableRecetas.getItems()
            );

            // Parámetros del informe (si los hay)
            Map<String, Object> parameters = new HashMap<>();

            // Llenar el reporte con los datos y parámetros
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);

            // Crear el visor del informe
            JasperViewer viewer = new JasperViewer(jasperPrint, false);

            // Crear un JFrame para mostrar la vista previa del informe
            JFrame frame = new JFrame("Vista previa del informe");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(viewer.getContentPane());
            frame.setSize(1200, 900);
            frame.setLocationRelativeTo(null); // Centrar ventana
            frame.setVisible(true);

        } catch (JRException e) {
            // En caso de error al generar el reporte, se muestra un mensaje de error
            LOGGER.severe("Error al generar el reporte: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al generar el reporte.");
            alert.showAndWait();
        }
    }

    @FXML
    private void borrarFila() {
        RecetaMedica recetaseleccionada = tableRecetas.getSelectionModel().getSelectedItem();
        if (recetaseleccionada != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("Eliminar Receta Medica");
            confirmacion.setContentText("¿Estás seguro de que deseas eliminar la receta con ID: " + recetaseleccionada.getIdReceta() + "?");

            confirmacion.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        // Se realiza la eliminación del almacén en la base de datos
                        RecetaMedicaFactoria.get().eliminarRecetamedica(String.valueOf(recetaseleccionada.getIdReceta()));
                        // Se elimina también de la tabla en la interfaz
                        tableRecetas.getItems().remove(recetaseleccionada);
                        LOGGER.info("Receta Medica eliminada correctamente.");
                    } catch (WebApplicationException e) {
                        // En caso de error, se captura y se muestra en el log
                        LOGGER.severe("Error al eliminar el Receta Medica: " + e.getMessage());
                    }
                }
            });
        }
    }
}
