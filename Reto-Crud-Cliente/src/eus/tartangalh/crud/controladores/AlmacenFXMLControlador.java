package eus.tartangalh.crud.controladores;

import eus.tartangalh.crud.entidades.Almacen;
import eus.tartangalh.crud.entidades.Trabajador;
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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import javax.swing.JFrame;
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
 * Además, configura la tabla de visualización de almacenes y maneja las
 * interacciones del usuario con la interfaz.
 *
 * @author Andoni
 */
public class AlmacenFXMLControlador {
    
    @FXML
    private VBox idVBox;

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
    
    private ContextMenuManager contextMenuManager;

    private static final Logger LOGGER = Logger.getLogger("ProveedorControlador.view");
    private Trabajador trabajador;

    /**
     * Establece el escenario principal de la aplicación. Se utiliza para
     * manipular el escenario de la ventana principal.
     *
     * @param stage El escenario de la aplicación.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    /**
     * Inicializa la interfaz gráfica de la ventana de gestión de almacenes.
     * Configura las propiedades de la ventana principal, las columnas de la
     * tabla y los eventos de interacción del usuario.
     *
     * @param root Elemento raíz de la escena.
     */
    public void initStage(Parent root) {
        LOGGER.info("Inicializando controlador de almacen");

        Scene scene = new Scene(root, 1366, 768);

        // Cargar y aplicar el estilo CSS de la interfaz.
        scene.getStylesheets().add(getClass().getResource("/recursos/EstiloAlmacen.css").toExternalForm());

        // Establecer el icono de la ventana.
        stage.getIcons().add(new Image("recursos/iconoFarmacia.png"));

        // Configurar el título de la ventana y evitar redimensionamiento.
        stage.setTitle("Gestion de almacenes");
        stage.setResizable(false);

        // Asignar la escena a la ventana y mostrarla.
        stage.setScene(scene);
        stage.show();

        // Establecer un evento para el cierre de la ventana, con manejo personalizado.
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();  // Consumir el evento para evitar el cierre predeterminado
                manejoCierre();
            }
        });

        // Configuración de las columnas de la tabla.
        configurarColumnasTabla();

        // Cargar y mostrar los almacenes en la tabla.
        mostrarAlmacenes();

        // Hacer las celdas de la tabla editables.
        configureTableEditable();

        // Ocultar filtros al inicio y mostrar los filtros configurados.
        ocultarTodosLosFiltros();
        listarFiltros();
        
        contextMenuManager = new ContextMenuManager(idVBox);
    }

    /**
     * Configura las columnas de la tabla de almacenes. Establece cómo se van a
     * mostrar los datos de cada columna en la tabla.
     */
    private void configurarColumnasTabla() {
        idAlmacenColumna.setCellValueFactory(new PropertyValueFactory<>("idAlmacen"));
        paisColumna.setCellValueFactory(new PropertyValueFactory<>("pais"));
        ciudadColumna.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        metrosCuadradosColumna.setCellValueFactory(new PropertyValueFactory<>("metrosCuadrados"));
        fechaAdquisicionColumna.setCellValueFactory(new PropertyValueFactory<>("fechaAdquisicion"));
    }

    /**
     * Configura la tabla para ser editable y maneja los eventos de edición.
     * Permite editar las celdas de la tabla y actualiza la base de datos cuando
     * el usuario realiza cambios.
     */
    private void configureTableEditable() {
        almacenTableView.setEditable(true);

        // Configuración de la columna ID de almacen.
        idAlmacenColumna.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        idAlmacenColumna.setOnEditCommit(event -> {
            Almacen almacen = event.getRowValue();
            Integer newId = event.getNewValue();
            // Validación del ID para asegurarse de que es un número válido y no excede la longitud.
            if (newId != null && String.valueOf(newId).length() <= 10) {
                almacen.setIdAlmacen(newId);
                AlmacenFactoria.get().actualizarAlmacen_XML(almacen);
            } else {
                mostrarMensajeError("El ID debe ser un número entero y no exceder 10 caracteres.");
                almacenTableView.refresh(); // Cancelar edición y refrescar la tabla
            }
        });

        // Configuración de la columna de País.
        paisColumna.setCellFactory(TextFieldTableCell.forTableColumn());
        paisColumna.setOnEditCommit(event -> {
            Almacen almacen = event.getRowValue();
            String newPais = event.getNewValue();
            // Validación de la entrada para asegurar que solo contiene letras y no excede la longitud permitida.
            if (newPais != null && newPais.matches("[a-zA-Z\\s]+") && newPais.length() <= 50) {
                almacen.setPais(newPais);
                AlmacenFactoria.get().actualizarAlmacen_XML(almacen);
            } else {
                mostrarMensajeError("El país debe contener solo letras y no exceder 50 caracteres.");
                almacenTableView.refresh(); // Cancelar edición y refrescar la tabla
            }
        });

        // Configuración de la columna de Ciudad.
        ciudadColumna.setCellFactory(TextFieldTableCell.forTableColumn());
        ciudadColumna.setOnEditCommit(event -> {
            Almacen almacen = event.getRowValue();
            String newCiudad = event.getNewValue();
            // Validación de la entrada para asegurar que solo contiene letras y no excede la longitud permitida.
            if (newCiudad != null && newCiudad.matches("[a-zA-Z\\s]+") && newCiudad.length() <= 50) {
                almacen.setCiudad(newCiudad);
                AlmacenFactoria.get().actualizarAlmacen_XML(almacen);
            } else {
                mostrarMensajeError("La ciudad debe contener solo letras y no exceder 50 caracteres.");
                almacenTableView.refresh(); // Cancelar edición y refrescar la tabla
            }
        });

        // Configuración de la columna de Metros Cuadrados.
        metrosCuadradosColumna.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                return value != null ? value.toString() : "";
            }

            @Override
            public Integer fromString(String text) {
                if (text == null || text.trim().isEmpty()) {
                    return null; // Permitir valores vacíos sin error
                }
                if (!text.matches("\\d+")) { // Verificar si es un número válido
                    mostrarMensajeError("La entrada debe ser un número entero válido.");
                    return null;
                }
                return Integer.parseInt(text);
            }
        }));

        metrosCuadradosColumna.setOnEditCommit(event -> {
            Almacen almacen = event.getRowValue();
            Integer newValue = event.getNewValue();

            if (newValue == null) {
                almacenTableView.refresh(); // Evitar que se actualice la tabla con un valor inválido
                return;
            }

            if (newValue >= 0) {
                almacen.setMetrosCuadrados(newValue);
                AlmacenFactoria.get().actualizarAlmacen_XML(almacen);
            } else {
                mostrarMensajeError("Los metros cuadrados deben ser un número entero positivo.");
                almacenTableView.refresh();
            }
        });

        // Configuración de la columna de Fecha de Adquisición.
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

                // Validación de la fecha de adquisición para asegurarse de que es anterior a la fecha actual.
                if (newValue != null && newValue.before(new Date())) {
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
        }
        );
    }

    /**
     * Añade un nuevo almacén a la base de datos. Este método crea un nuevo
     * objeto Almacen y lo guarda en la base de datos a través de la factoría
     * AlmacenFactoria.
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
     * Borra el almacén seleccionado en la tabla. Se muestra una ventana de
     * confirmación antes de proceder con la eliminación. Si el usuario
     * confirma, se intenta eliminar el almacén tanto en la base de datos como
     * de la vista. En caso de error, se captura la excepción y se muestra un
     * mensaje en los logs.
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
                        // Se realiza la eliminación del almacén en la base de datos
                        AlmacenFactoria.get().borrarAlmacen(String.valueOf(almacenSeleccionado.getIdAlmacen()));
                        // Se elimina también de la tabla en la interfaz
                        almacenTableView.getItems().remove(almacenSeleccionado);
                        LOGGER.info("Almacén eliminado correctamente.");
                    } catch (WebApplicationException e) {
                        // En caso de error, se captura y se muestra en el log
                        LOGGER.severe("Error al eliminar el almacén: " + e.getMessage());
                    }
                }
            });
        }
    }

    /**
     * Obtiene y muestra los almacenes desde la base de datos. Carga los
     * almacenes mediante una llamada al servicio de la base de datos y los
     * muestra en la tabla. Si ocurre un error, se captura la excepción y se
     * registra en el log.
     */
    private void mostrarAlmacenes() {
        try {
            List<Almacen> almacenesEncontrados = AlmacenFactoria.get().findAll_XML(new GenericType<List<Almacen>>() {
            });
            ObservableList<Almacen> almacenes = FXCollections.observableArrayList(almacenesEncontrados);
            almacenTableView.setItems(almacenes); // Actualiza la vista con los almacenes obtenidos
        } catch (Exception e) {
            LOGGER.severe("Error al cargar los almacenes: " + e.getMessage());
        }
    }

    /**
     * Configura el comboBox con los filtros disponibles y muestra el campo
     * adecuado según el filtro seleccionado. Si el usuario elige un filtro, el
     * campo de entrada correspondiente se hace visible.
     */
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
            ocultarTodosLosFiltros(); // Oculta todos los campos de filtro antes de mostrar el seleccionado

            // Muestra el campo de filtro correspondiente dependiendo de la opción seleccionada
            switch (combo.getValue().toString()) {
                case "ID":
                    txtFiltro.setVisible(true);
                    txtFiltro.setPromptText("Introduce ID");
                    break;
                case "Fecha":
                    txtFechaDesde.setVisible(true);
                    txtFechaHasta.setVisible(true);
                    filtrarPorFecha(); // Llama al método para filtrar por fechas
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

    /**
     * Oculta todos los campos de filtro para evitar que se muestren cuando no
     * son necesarios. Este método es útil cuando se cambia la opción de filtro
     * en el comboBox.
     */
    private void ocultarTodosLosFiltros() {
        txtFiltro.setVisible(false);
        txtFechaDesde.setVisible(false);
        txtFechaHasta.setVisible(false);
    }

    /**
     * Filtra los almacenes según el rango de fechas seleccionado. Se obtiene el
     * valor de las fechas desde y hasta, y se realiza el filtrado (aunque en
     * este fragmento solo se imprime).
     */
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

    /**
     * Realiza la búsqueda según el filtro seleccionado en el comboBox.
     * Dependiendo del tipo de filtro, se validan los valores y se llaman a los
     * métodos correspondientes. Si el valor es inválido, se muestra un mensaje
     * de error al usuario.
     */
    @FXML
    private void buscarFiltro() {
        String filtroSeleccionado = combo.getValue();

        // Dependiendo del filtro seleccionado, recoger el valor adecuado
        if (filtroSeleccionado != null) {
            try {
                switch (filtroSeleccionado) {
                    case "ID":
                        // Validación del ID
                        String idFiltro = txtFiltro.getText();
                        if (!idFiltro.isEmpty()) {
                            if (idFiltro.length() <= 50) {
                                try {
                                    int id = Integer.parseInt(idFiltro); // Validar que sea un número
                                    buscarPorId(String.valueOf(id)); // Llamada al método de búsqueda por ID
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
                        // Validación de las fechas
                        LocalDate fechaDesde = txtFechaDesde.getValue();
                        LocalDate fechaHasta = txtFechaHasta.getValue();
                        LocalDate fechaHoy = LocalDate.now();
                        if (fechaDesde != null && fechaHasta != null) {
                            if (!fechaDesde.isAfter(fechaHasta) && !fechaDesde.isAfter(fechaHoy) && !fechaHasta.isAfter(fechaHoy)) {
                                buscarPorFecha(fechaDesde, fechaHasta);
                            } else {
                                mostrarMensajeError("Fechas no válidas.");
                            }
                        } else {
                            mostrarAlmacenes(); // Si no hay fechas, mostrar todos
                        }
                        break;

                    // Validación para los demás filtros: País, Ciudad, y Metros...
                    // ...
                }
            } catch (Exception e) {
                LOGGER.severe("Error al buscar almacén: " + e.getMessage());
            }
        }
    }

    /**
     * Recarga la tabla de almacenes mostrando los datos más recientes de la
     * base de datos.
     */
    @FXML
    private void recargarTabla() {
        mostrarAlmacenes(); // Llama al método para mostrar los almacenes
    }

    /**
     * Método para buscar almacenes por ID. Este método consulta un almacén a
     * partir de su ID y actualiza la vista de la tabla.
     */
    private void buscarPorId(String id) {
        try {
            // Se busca el almacén por su ID usando la fábrica
            Almacen almacen = AlmacenFactoria.get().encontrar_XML(Almacen.class, id);

            // Se crea una lista observable con el resultado
            ObservableList<Almacen> resultado = FXCollections.observableArrayList(almacen);

            // Se asignan los resultados a la tabla en la interfaz de usuario
            almacenTableView.setItems(resultado);
        } catch (Exception e) {
            // En caso de error, se registra el error en el log
            LOGGER.severe("Error al buscar almacén por ID: " + e.getMessage());
        }
    }

    /**
     * Método para buscar almacenes por fecha de adquisición. Este método filtra
     * los almacenes por un rango de fechas.
     */
    private void buscarPorFecha(LocalDate fechaDesde, LocalDate fechaHasta) {
        try {
            // Se obtiene la lista completa de almacenes
            List<Almacen> almacenes = AlmacenFactoria.get().findAll_XML(new GenericType<List<Almacen>>() {
            });

            // Se filtra la lista de almacenes por la fecha de adquisición
            List<Almacen> resultado = almacenes.stream()
                    .filter(almacen -> {
                        LocalDate fecha = almacen.getFechaAdquisicion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        // Se filtra según el rango de fechas
                        return !fecha.isBefore(fechaDesde) && !fecha.isAfter(fechaHasta);
                    })
                    .collect(Collectors.toList());

            // Se actualiza la tabla con los resultados filtrados
            ObservableList<Almacen> observableResultado = FXCollections.observableArrayList(resultado);
            almacenTableView.setItems(observableResultado);
        } catch (Exception e) {
            // En caso de error, se registra el error en el log
            LOGGER.severe("Error al buscar almacenes por fecha: " + e.getMessage());
        }
    }

    /**
     * Método para buscar almacenes por país. Este método filtra los almacenes
     * que contienen el nombre del país especificado.
     */
    private void buscarPorPais(String pais) {
        try {
            // Se obtiene la lista completa de almacenes
            List<Almacen> almacenes = AlmacenFactoria.get().findAll_XML(new GenericType<List<Almacen>>() {
            });

            // Se filtra la lista de almacenes por el nombre del país (ignora mayúsculas/minúsculas)
            List<Almacen> resultado = almacenes.stream()
                    .filter(almacen -> almacen.getPais().toLowerCase().contains(pais.toLowerCase()))
                    .collect(Collectors.toList());

            // Se actualiza la tabla con los resultados filtrados
            ObservableList<Almacen> observableResultado = FXCollections.observableArrayList(resultado);
            almacenTableView.setItems(observableResultado);
        } catch (Exception e) {
            // En caso de error, se registra el error en el log
            LOGGER.severe("Error al buscar almacenes por país: " + e.getMessage());
        }
    }

    /**
     * Método para buscar almacenes por ciudad. Este método filtra los almacenes
     * por el nombre de la ciudad especificada.
     */
    private void buscarPorCiudad(String ciudad) {
        try {
            // Se obtiene la lista completa de almacenes
            List<Almacen> almacenes = AlmacenFactoria.get().findAll_XML(new GenericType<List<Almacen>>() {
            });

            // Se filtra la lista de almacenes por el nombre de la ciudad (ignora mayúsculas/minúsculas)
            List<Almacen> resultado = almacenes.stream()
                    .filter(almacen -> almacen.getCiudad().toLowerCase().contains(ciudad.toLowerCase()))
                    .collect(Collectors.toList());

            // Se actualiza la tabla con los resultados filtrados
            ObservableList<Almacen> observableResultado = FXCollections.observableArrayList(resultado);
            almacenTableView.setItems(observableResultado);
        } catch (Exception e) {
            // En caso de error, se registra el error en el log
            LOGGER.severe("Error al buscar almacenes por ciudad: " + e.getMessage());
        }
    }

    /**
     * Método para buscar almacenes por metros cuadrados. Este método filtra los
     * almacenes por el valor exacto de los metros cuadrados.
     */
    private void buscarPorMetros(int metros) {
        try {
            // Se obtiene la lista completa de almacenes
            List<Almacen> almacenes = AlmacenFactoria.get().findAll_XML(new GenericType<List<Almacen>>() {
            });

            // Se filtra la lista de almacenes por el valor de metros cuadrados
            List<Almacen> resultado = almacenes.stream()
                    .filter(almacen -> almacen.getMetrosCuadrados() == metros)
                    .collect(Collectors.toList());

            // Se actualiza la tabla con los resultados filtrados
            ObservableList<Almacen> observableResultado = FXCollections.observableArrayList(resultado);
            almacenTableView.setItems(observableResultado);
        } catch (Exception e) {
            // En caso de error, se registra el error en el log
            LOGGER.severe("Error al buscar almacenes por metros: " + e.getMessage());
        }
    }

    /**
     * Método para manejar el cierre de la aplicación con una confirmación. Al
     * intentar cerrar la aplicación, se muestra un diálogo de confirmación.
     */
    private void manejoCierre() {
        // Se crea un cuadro de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Está seguro de que desea cerrar la aplicación?");
        alert.setContentText("Todos los cambios no guardados se perderán.");

        // Se espera la respuesta del usuario
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Si el usuario confirma, se cierra la ventana
            Stage stage = (Stage) añadirBtn.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Método para imprimir el informe en formato Jasper. Este método compila y
     * genera un informe en formato PDF basado en los datos de la tabla.
     */
    @FXML
    private void imprimirInforme() {
        try {
            // Compilar el reporte desde el archivo JRXML
            JasperReport report = JasperCompileManager.compileReport("src/recursos/informeAlmacen.jrxml");

            // Obtener los datos de la tabla como fuente de datos
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource(
                    (Collection<Almacen>) this.almacenTableView.getItems()
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

    /**
     * Método para mostrar un mensaje de error en un cuadro de alerta. Este
     * método se usa para mostrar errores generales.
     */
    private void mostrarMensajeError(String mensaje) {
        // Crear un cuadro de alerta de tipo ERROR
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Datos inválidos");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Método para verificar si un valor es un número válido. Este método
     * comprueba si una cadena de texto puede ser convertida a un número entero.
     */
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
