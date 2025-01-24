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
import java.util.stream.Collectors;
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
    private TextField txtFiltro;

    @FXML
    private DatePicker txtFechaDesde;

    @FXML
    private DatePicker txtFechaHasta;

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
                    txtFiltro.setVisible(true);
                    txtFiltro.setPromptText("Introduce ID");
                    break;
                case "Fecha":
                    txtFechaDesde.setVisible(true);
                    txtFechaHasta.setVisible(true);
                    filtrarPorFecha();
                    break;
                case "País":
                    txtFiltro.setPromptText("Introduce Pais");
                    txtFiltro.setVisible(true);
                    break;
                case "Ciudad":
                    txtFiltro.setPromptText("Introduce Ciudad");
                    txtFiltro.setVisible(true);
                    break;
                case "Metros":
                    txtFiltro.setPromptText("Introduce Metros");
                    txtFiltro.setVisible(true);
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
        txtFiltro.setVisible(false);
        txtFiltro.setVisible(false);
        txtFiltro.setVisible(false);
    }

    private void filtrarPorFecha() {
        txtFechaDesde.setPromptText("Introduce Fecha Desde");
        txtFechaHasta.setPromptText("Introduce Fecha Hasta");
        LocalDate fechaDesde = txtFechaDesde.getValue();
        LocalDate fechaHasta = txtFechaHasta.getValue();

        if (fechaDesde != null && fechaHasta != null) {
            // Lógica para filtrar la tabla de productos por fecha
            System.out.println("Filtrar por fechas: Desde " + fechaDesde + " Hasta " + fechaHasta);
        }
    }

    @FXML
    private void buscarFiltro() {
        String filtroSeleccionado = combo.getValue();

        // Dependiendo del filtro seleccionado, recoger el valor adecuado
        if (filtroSeleccionado != null) {
            try {
                switch (filtroSeleccionado) {
                    case "ID":
                        String idFiltro = txtFiltro.getText();
                        if (!idFiltro.isEmpty()) {
                            buscarPorId(idFiltro);
                        } else {
                            mostrarAlmacenes(); // Si no hay texto en el filtro, mostrar todos
                        }
                        break;

                    case "Fecha":
                        LocalDate fechaDesde = txtFechaDesde.getValue();
                        LocalDate fechaHasta = txtFechaHasta.getValue();
                        if (fechaDesde != null && fechaHasta != null) {
                            buscarPorFecha(fechaDesde, fechaHasta);
                        } else {
                            mostrarAlmacenes(); // Si no hay fechas, mostrar todos
                        }
                        break;

                    case "País":
                        String paisFiltro = txtFiltro.getText();
                        if (!paisFiltro.isEmpty()) {
                            buscarPorPais(paisFiltro);
                        } else {
                            mostrarAlmacenes(); // Si no hay texto en el filtro, mostrar todos
                        }
                        break;

                    case "Ciudad":
                        String ciudadFiltro = txtFiltro.getText();
                        if (!ciudadFiltro.isEmpty()) {
                            buscarPorCiudad(ciudadFiltro);
                        } else {
                            mostrarAlmacenes(); // Si no hay texto en el filtro, mostrar todos
                        }
                        break;

                    case "Metros":
                        String metrosFiltro = txtFiltro.getText();
                        if (!metrosFiltro.isEmpty()) {
                            try {
                                int metros = Integer.parseInt(metrosFiltro);
                                buscarPorMetros(metros);
                            } catch (NumberFormatException e) {
                                // Si el valor no es un número válido, mostrar un mensaje de error
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText("Valor incorrecto");
                                alert.setContentText("Por favor, ingresa un valor numérico válido para los metros.");
                                alert.showAndWait();
                            }
                        } else {
                            mostrarAlmacenes(); // Si no hay texto en el filtro, mostrar todos
                        }
                        break;

                    default:
                        mostrarAlmacenes(); // Si no hay filtro seleccionado, mostrar todos
                        break;
                }
            } catch (Exception e) {
                LOGGER.severe("Error al buscar almacén: " + e.getMessage());
            }
        }
    }

    /**
     * Método para buscar almacenes por ID.
     */
    private void buscarPorId(String id) {
        try {
            Almacen almacen = AlmacenFactoria.get().encontrar_XML(Almacen.class, id);
            ObservableList<Almacen> resultado = FXCollections.observableArrayList(almacen);
            almacenTableView.setItems(resultado);
        } catch (Exception e) {
            LOGGER.severe("Error al buscar almacén por ID: " + e.getMessage());
        }
    }

    /**
     * Método para buscar almacenes por fecha de adquisición.
     */
    private void buscarPorFecha(LocalDate fechaDesde, LocalDate fechaHasta) {
        try {
            List<Almacen> almacenes = AlmacenFactoria.get().findAll_XML(new GenericType<List<Almacen>>() {
            });
            List<Almacen> resultado = almacenes.stream()
                    .filter(almacen -> {
                        LocalDate fecha = almacen.getFechaAdquisicion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        return !fecha.isBefore(fechaDesde) && !fecha.isAfter(fechaHasta);
                    })
                    .collect(Collectors.toList());
            ObservableList<Almacen> observableResultado = FXCollections.observableArrayList(resultado);
            almacenTableView.setItems(observableResultado);
        } catch (Exception e) {
            LOGGER.severe("Error al buscar almacenes por fecha: " + e.getMessage());
        }
    }

    /**
     * Método para buscar almacenes por país.
     */
    private void buscarPorPais(String pais) {
        try {
            List<Almacen> almacenes = AlmacenFactoria.get().findAll_XML(new GenericType<List<Almacen>>() {
            });
            List<Almacen> resultado = almacenes.stream()
                    .filter(almacen -> almacen.getPais().toLowerCase().contains(pais.toLowerCase()))
                    .collect(Collectors.toList());
            ObservableList<Almacen> observableResultado = FXCollections.observableArrayList(resultado);
            almacenTableView.setItems(observableResultado);
        } catch (Exception e) {
            LOGGER.severe("Error al buscar almacenes por país: " + e.getMessage());
        }
    }

    /**
     * Método para buscar almacenes por ciudad.
     */
    private void buscarPorCiudad(String ciudad) {
        try {
            List<Almacen> almacenes = AlmacenFactoria.get().findAll_XML(new GenericType<List<Almacen>>() {
            });
            List<Almacen> resultado = almacenes.stream()
                    .filter(almacen -> almacen.getCiudad().toLowerCase().contains(ciudad.toLowerCase()))
                    .collect(Collectors.toList());
            ObservableList<Almacen> observableResultado = FXCollections.observableArrayList(resultado);
            almacenTableView.setItems(observableResultado);
        } catch (Exception e) {
            LOGGER.severe("Error al buscar almacenes por ciudad: " + e.getMessage());
        }
    }

    /**
     * Método para buscar almacenes por metros cuadrados.
     */
    private void buscarPorMetros(int metros) {
        try {
            List<Almacen> almacenes = AlmacenFactoria.get().findAll_XML(new GenericType<List<Almacen>>() {
            });
            List<Almacen> resultado = almacenes.stream()
                    .filter(almacen -> almacen.getMetrosCuadrados() == metros)
                    .collect(Collectors.toList());
            ObservableList<Almacen> observableResultado = FXCollections.observableArrayList(resultado);
            almacenTableView.setItems(observableResultado);
        } catch (Exception e) {
            LOGGER.severe("Error al buscar almacenes por metros: " + e.getMessage());
        }
    }
}
