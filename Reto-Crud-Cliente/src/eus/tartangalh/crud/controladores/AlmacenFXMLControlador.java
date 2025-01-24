package eus.tartangalh.crud.controladores;

import eus.tartangalh.crud.entidades.Almacen;
import eus.tartangalh.crud.interfaces.AlmacenFactoria;
import java.util.List;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javax.ws.rs.core.GenericType;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.ws.rs.WebApplicationException;

/**
 * Controlador para la interfaz FXML de la entidad Almacen. Permite la gestión
 * de almacenes mediante operaciones de creación, actualización y eliminación.
 *
 * @author Andoni
 */
public class AlmacenFXMLControlador {

    @FXML
    private TableView<Almacen> almacenTableView;

    @FXML
    private TableColumn<Almacen, Integer> idAlmacenColumna;

    @FXML
    private TableColumn<Almacen, String> paisColumna;

    @FXML
    private TableColumn<Almacen, String> ciudadColumna;

    @FXML
    private TableColumn<Almacen, Date> fechaAdquisicionColumna;

    @FXML
    private TableColumn<Almacen, Integer> metrosCuadradosColumna;

    @FXML
    private Button añadirBtn;

    @FXML
    private Button borrarBtn;

    @FXML
    private ComboBox<String> combo;

    @FXML
    private TextField txtCiudad;

    @FXML
    private TextField txtPais;

    @FXML
    private TextField txtMetros;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtFechaDesde;

    @FXML
    private TextField txtFechaHasta;

    private Stage stage;

    private static final Logger LOGGER = Logger.getLogger("ProveedorControlador.view");

    /**
     * Establece el escenario principal de la aplicación.
     *
     * @param stage El escenario de la aplicación.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Inicializa la interfaz gráfica.
     *
     * @param root Elemento raíz de la escena.
     */
    public void initStage(Parent root) {
        LOGGER.info("Inicializando controlador de almacen");

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        configurarColumnasTabla();
        mostrarAlmacenes();
        configureTableEditable();
        ocultarTodosLosFiltros();
        configurarPlaceHolders();
        listarFiltros();
    }

    /**
     * Configura las columnas de la tabla de almacenes.
     */
    private void configurarColumnasTabla() {
        idAlmacenColumna.setCellValueFactory(new PropertyValueFactory<>("idAlmacen"));
        paisColumna.setCellValueFactory(new PropertyValueFactory<>("pais"));
        ciudadColumna.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        metrosCuadradosColumna.setCellValueFactory(new PropertyValueFactory<>("metrosCuadrados"));
        fechaAdquisicionColumna.setCellValueFactory(new PropertyValueFactory<>("fechaAdquisicion"));
    }

    /**
     * Configura la tabla para ser editable y manejar eventos de edición.
     */
    private void configureTableEditable() {
        almacenTableView.setEditable(true);

        paisColumna.setCellFactory(TextFieldTableCell.forTableColumn());
        paisColumna.setOnEditCommit(event -> {
            Almacen almacen = event.getRowValue();
            almacen.setPais(event.getNewValue());
            AlmacenFactoria.get().actualizarAlmacen_XML(almacen);
        });

        ciudadColumna.setCellFactory(TextFieldTableCell.forTableColumn());
        ciudadColumna.setOnEditCommit(event -> {
            Almacen almacen = event.getRowValue();
            almacen.setCiudad(event.getNewValue());
            AlmacenFactoria.get().actualizarAlmacen_XML(almacen);
        });

        // Configurar la columna metrosCuadrados para manejar Integer
        // Configurar la columna metrosCuadrados para manejar Integer
        metrosCuadradosColumna.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        metrosCuadradosColumna.setOnEditCommit(event -> {
            Almacen almacen = event.getRowValue();
            almacen.setMetrosCuadrados(event.getNewValue());
            System.out.println(almacen.toString());
            AlmacenFactoria.get().actualizarAlmacen_XML(almacen);
        });

        // Fecha
        fechaAdquisicionColumna.setCellFactory(col -> new TableCell<Almacen, Date>() {
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

    /**
     * Añade un nuevo almacén a la base de datos.
     */
    @FXML
    private void añadirFila() {
        try {
            Almacen almacen = new Almacen();
            AlmacenFactoria.get().crearAlmacen_XML(almacen);
            mostrarAlmacenes();
        } catch (Exception e) {
            LOGGER.severe("Error al crear almacén: " + e.getMessage());
        }
    }

    /**
     * Borra el almacén seleccionado en la tabla.
     */
    @FXML
    private void borrarFila() {
        Almacen almacenSeleccionado = almacenTableView.getSelectionModel().getSelectedItem();
        if (almacenSeleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("Eliminar almacén");
            confirmacion.setContentText("¿Estás seguro de que deseas eliminar el almacén con ID: " + almacenSeleccionado.getIdAlmacen() + "?");

            confirmacion.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        AlmacenFactoria.get().borrarAlmacen(String.valueOf(almacenSeleccionado.getIdAlmacen()));
                        almacenTableView.getItems().remove(almacenSeleccionado);
                        LOGGER.info("Almacén eliminado correctamente.");
                    } catch (WebApplicationException e) {
                        LOGGER.severe("Error al eliminar el almacén: " + e.getMessage());
                    }
                }
            });
        }
    }

    /**
     * Obtiene y muestra los almacenes desde la base de datos.
     */
    private void mostrarAlmacenes() {
        try {
            List<Almacen> almacenesEncontrados = AlmacenFactoria.get().findAll_XML(new GenericType<List<Almacen>>() {
            });
            ObservableList<Almacen> almacenes = FXCollections.observableArrayList(almacenesEncontrados);
            almacenTableView.setItems(almacenes);
        } catch (Exception e) {
            LOGGER.severe("Error al cargar los almacenes: " + e.getMessage());
        }
    }

    private void listarFiltros() {
        ObservableList<String> opcionesFiltro = FXCollections.observableArrayList(
                "ID",
                "Fecha",
                "País",
                "Ciudad",
                "Metros"
        );

        combo.setItems(opcionesFiltro);

        combo.setOnAction(event -> {
            ocultarTodosLosFiltros();

            switch (combo.getValue().toString()) {
                case "ID":
                    txtId.setVisible(true);
                    break;
                case "Fecha":
                    txtFechaDesde.setVisible(true);
                    txtFechaHasta.setVisible(true);
                    filtrarPorFecha();
                    break;
                case "País":
                    txtPais.setVisible(true);
                    break;
                case "Ciudad":
                    txtCiudad.setVisible(true);
                    break;
                case "Metros":
                    txtMetros.setVisible(true);
                    break;
                default:
                    break;
            }
        });
    }

    private void ocultarTodosLosFiltros() {
        txtId.setVisible(false);
        txtFechaDesde.setVisible(false);
        txtFechaHasta.setVisible(false);
        txtPais.setVisible(false);
        txtCiudad.setVisible(false);
        txtMetros.setVisible(false);
    }

    private void configurarPlaceHolders() {
        // Establecer el texto por defecto (placeholder)
        txtId.setPromptText("Introduce ID");
        txtFechaDesde.setPromptText("Introduce Fecha Desde");
        txtFechaHasta.setPromptText("Introduce Fecha Hasta");
        txtPais.setPromptText("Introduce País");
        txtCiudad.setPromptText("Introduce Ciudad");
        txtMetros.setPromptText("Introduce Metros");
    }

    private void filtrarPorFecha() {
        DatePicker datePicker = new DatePicker();
        // Configurar el DatePicker para que actualice el TextField al seleccionar una fecha
        datePicker.setOnAction(event -> {
            LocalDate selectedDate = datePicker.getValue();
            if (selectedDate != null) {
                txtFechaDesde.setText(selectedDate.toString());  // Mostrar la fecha seleccionada en el TextField
                Date fechaSeleccionada = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                // Aquí puedes añadir la lógica de filtro con la fecha seleccionada
                System.out.println("Fecha seleccionada: " + fechaSeleccionada);
            }
        });
    }
}
