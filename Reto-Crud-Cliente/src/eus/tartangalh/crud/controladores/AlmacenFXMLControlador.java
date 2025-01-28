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
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
import javax.ws.rs.WebApplicationException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

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

        Scene scene = new Scene(root, 1366, 768);

        scene.getStylesheets().add(getClass().getResource("/recursos/EstiloAlmacen.css").toExternalForm());
        stage.getIcons().add(new Image("recursos/iconoFarmacia.png"));
        stage.setTitle("Gestion de almacenes");
        stage.setResizable(false);
        //stage.initModality(Modality.APPLICATION_MODAL);

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

        // Configurar la columna ID
        idAlmacenColumna.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        idAlmacenColumna.setOnEditCommit(event -> {
            Almacen almacen = event.getRowValue();
            Integer newId = event.getNewValue();
            if (newId != null && String.valueOf(newId).length() <= 10) { // Validar longitud del ID
                almacen.setIdAlmacen(newId);
                AlmacenFactoria.get().actualizarAlmacen_XML(almacen);
            } else {
                mostrarMensajeError("El ID debe ser un número entero y no exceder 10 caracteres.");
                almacenTableView.refresh(); // Cancelar edición y refrescar la tabla
            }
        });

        // Configurar la columna País
        paisColumna.setCellFactory(TextFieldTableCell.forTableColumn());
        paisColumna.setOnEditCommit(event -> {
            Almacen almacen = event.getRowValue();
            String newPais = event.getNewValue();
            if (newPais != null && newPais.matches("[a-zA-Z\\s]+") && newPais.length() <= 50) { // Validar solo letras y longitud
                almacen.setPais(newPais);
                AlmacenFactoria.get().actualizarAlmacen_XML(almacen);
            } else {
                mostrarMensajeError("El país debe contener solo letras y no exceder 50 caracteres.");
                almacenTableView.refresh(); // Cancelar edición y refrescar la tabla
            }
        });

        // Configurar la columna Ciudad
        ciudadColumna.setCellFactory(TextFieldTableCell.forTableColumn());
        ciudadColumna.setOnEditCommit(event -> {
            Almacen almacen = event.getRowValue();
            String newCiudad = event.getNewValue();
            if (newCiudad != null && newCiudad.matches("[a-zA-Z\\s]+") && newCiudad.length() <= 50) { // Validar solo letras y longitud
                almacen.setCiudad(newCiudad);
                AlmacenFactoria.get().actualizarAlmacen_XML(almacen);
            } else {
                mostrarMensajeError("La ciudad debe contener solo letras y no exceder 50 caracteres.");
                almacenTableView.refresh(); // Cancelar edición y refrescar la tabla
            }
        });

        // Configurar la columna Metros Cuadrados
        metrosCuadradosColumna.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        metrosCuadradosColumna.setOnEditCommit(event -> {
            Almacen almacen = event.getRowValue();
            String newValue = event.getNewValue().toString(); // Obtener el valor como String
            if (esNumeroValido(newValue)) { // Validar entrada
                int newMetros = Integer.parseInt(newValue); // Convertir a entero
                if (newMetros >= 0) { // Validar que sea positivo
                    almacen.setMetrosCuadrados(newMetros);
                    AlmacenFactoria.get().actualizarAlmacen_XML(almacen);
                } else {
                    mostrarMensajeError("Los metros cuadrados deben ser un número entero positivo.");
                    almacenTableView.refresh(); // Cancelar edición y refrescar la tabla
                }
            } else {
                mostrarMensajeError("La entrada debe ser un número entero válido.");
                almacenTableView.refresh(); // Cancelar edición y refrescar la tabla
            }
        });

        // Configurar la columna Fecha de Adquisición
        fechaAdquisicionColumna.setCellFactory(col -> new TableCell<Almacen, Date>() {
            private final DatePicker datePicker = new DatePicker();

            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null); // No mostrar nada si la celda está vacía
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
                Almacen almacen = getTableView().getItems().get(getIndex());

                if (newValue != null && newValue.before(new Date())) { // Validar que la fecha sea anterior a hoy
                    almacen.setFechaAdquisicion(newValue);
                    AlmacenFactoria.get().actualizarAlmacen_XML(almacen);
                } else {
                    mostrarMensajeError("La fecha de adquisición debe ser anterior a la fecha de hoy.");
                    almacenTableView.refresh(); // Cancelar edición y refrescar la tabla
                }
            }

            // Forzar el commit cuando se pierde el foco
            {
                datePicker.focusedProperty().addListener((obs, oldFocus, newFocus) -> {
                    if (!newFocus) {
                        Date newDate = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                        commitEdit(newDate);
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
            almacenTableView.scrollTo(almacenTableView.getItems().size() - 1);
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
                            if (idFiltro.length() <= 50) {
                                try {
                                    int id = Integer.parseInt(idFiltro); // Validar que sea un número
                                    buscarPorId(String.valueOf(id));    // Convertimos a String para buscar si es necesario
                                } catch (NumberFormatException e) {
                                    mostrarMensajeError("El ID debe ser un número válido.");
                                }
                            } else {
                                mostrarMensajeError("El ID no debe exceder los 50 caracteres.");
                            }
                        } else {
                            mostrarAlmacenes(); // Si no hay texto en el filtro, mostrar todos
                        }
                        break;

                    case "Fecha":
                        LocalDate fechaDesde = txtFechaDesde.getValue();
                        LocalDate fechaHasta = txtFechaHasta.getValue();
                        LocalDate fechaHoy = LocalDate.now(); // Obtener la fecha actual
                        if (fechaDesde != null && fechaHasta != null) {
                            if (!fechaDesde.isAfter(fechaHasta)) { // Verificar que 'Desde' no sea posterior a 'Hasta'
                                if (!fechaDesde.isAfter(fechaHoy) && !fechaHasta.isAfter(fechaHoy)) { // Verificar que ninguna fecha sea posterior a hoy
                                    buscarPorFecha(fechaDesde, fechaHasta);
                                } else {
                                    mostrarMensajeError("Las fechas no pueden ser posteriores a la fecha actual.");
                                }
                            } else {
                                mostrarMensajeError("La fecha 'Desde' no puede ser posterior a la fecha 'Hasta'.");
                            }
                        } else {
                            mostrarAlmacenes(); // Si no hay fechas, mostrar todos
                        }
                        break;

                    case "País":
                        String paisFiltro = txtFiltro.getText();
                        if (!paisFiltro.isEmpty()) {
                            if (paisFiltro.length() <= 50) {
                                if (paisFiltro.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) { // Validar que contenga solo letras y espacios
                                    buscarPorPais(paisFiltro);
                                } else {
                                    mostrarMensajeError("El nombre del país solo puede contener letras.");
                                }
                            } else {
                                mostrarMensajeError("El nombre del país no debe exceder los 50 caracteres.");
                            }
                        } else {
                            mostrarAlmacenes(); // Si no hay texto en el filtro, mostrar todos
                        }
                        break;

                    case "Ciudad":
                        String ciudadFiltro = txtFiltro.getText();
                        if (!ciudadFiltro.isEmpty()) {
                            if (ciudadFiltro.length() <= 50) {
                                if (ciudadFiltro.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) { // Validar que contenga solo letras y espacios
                                    buscarPorCiudad(ciudadFiltro);
                                } else {
                                    mostrarMensajeError("El nombre de la ciudad solo puede contener letras.");
                                }
                            } else {
                                mostrarMensajeError("El nombre de la ciudad no debe exceder los 50 caracteres.");
                            }
                        } else {
                            mostrarAlmacenes(); // Si no hay texto en el filtro, mostrar todos
                        }
                        break;

                    case "Metros":
                        String metrosFiltro = txtFiltro.getText();
                        if (!metrosFiltro.isEmpty()) {
                            try {
                                int metros = Integer.parseInt(metrosFiltro); // Validar que sea un número
                                if (metros >= 0 && metros <= 10000) { // Rango permitido
                                    buscarPorMetros(metros);
                                } else {
                                    mostrarMensajeError("Por favor, ingresa un valor entre 0 y 10,000 para los metros.");
                                }
                            } catch (NumberFormatException e) {
                                mostrarMensajeError("Por favor, ingresa un valor numérico válido para los metros.");
                            }
                        } else {
                            mostrarAlmacenes(); // Si no hay texto en el filtro, mostrar todos
                        }
                        break;
                }
            } catch (Exception e) {
                LOGGER.severe("Error al buscar almacén: " + e.getMessage());
            }
        }
    }

    @FXML
    private void recargarTabla() {
        mostrarAlmacenes();
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

    private void manejoCierre() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Está seguro de que desea cerrar la aplicación?");
        alert.setContentText("Todos los cambios no guardados se perderán.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) añadirBtn.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void imprimirInforme() {
        try {

            JasperReport report = JasperCompileManager.compileReport("src/recursos/informeAlmacen.jrxml");

            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Almacen>) this.almacenTableView.getItems());

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

    private void mostrarMensajeError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Datos inválidos");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private boolean esNumeroValido(String valor) {
        if (valor == null || valor.isEmpty()) {
            return false; // No es válido si está vacío o nulo
        }
        try {
            Integer.parseInt(valor); // Intentar convertir a entero
            return true; // Es válido si no lanza una excepción
        } catch (NumberFormatException e) {
            return false; // No es un número válido
        }
    }
}
