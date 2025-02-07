package eus.tartangalh.crud.controladores;

import eus.tartangalh.crud.entidades.Almacen;
import eus.tartangalh.crud.entidades.Proveedor;
import eus.tartangalh.crud.entidades.Trabajador;
import eus.tartangalh.crud.interfaces.ProveedorFactoria;
import eus.tartangalh.crud.interfaces.ProveedorInterfaz;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import javax.swing.JFrame;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class ProveedorFXMLController {

    private final ProveedorInterfaz proInterfaz = ProveedorFactoria.get();

    // Campos de la interfaz
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
    private Button btnImprimir;

    // Tabla para mostrar los proveedores
    @FXML
    private TableView<Proveedor> tableView;

    // Columnas de la tabla 
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

    /**
     * Establece la instancia de la ventana (stage).
     *
     * @param stage La ventana principal de la aplicación.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Establece el trabajador actual.
     *
     * @param trabajador El trabajador que está gestionando la vista de
     * proveedores.
     */
    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    /**
     * Inicializa la interfaz gráfica de la ventana, configura los botones y las
     * tablas, y establece las acciones asociadas a ellos.
     *
     * @param root El nodo raíz que contiene la vista FXML.
     */
    public void initStage(Parent root) {
        LOGGER.info("Inicializando controlador de proveedor");
        stage.setResizable(false);  // Evita que la ventana se redimensione
        Scene scene = new Scene(root);
        stage.setTitle("Gestion de proveedores");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        tableView.setEditable(true);  // Hace que la tabla sea editable

        // Establecer los datos en la tabla
        tableView.setItems(mostrarProveedor());

        // Configuración de botones
        btnCrearFila.setVisible(true);
        btnCrearFila.setDisable(false);
        btnCrearFila.setOnAction(this::crearProveedor);
        btnBuscar.setOnAction(this::buscarProveedor);
        btnBorrar.setOnAction(this::borrarProveedor);
        btnBorrar.setDisable(true);  // Deshabilita el botón de borrar inicialmente

        btnAtras.setOnAction(this::menuTrabajador);  // Acción de retroceder al menú anterior
        btnImprimir.setOnAction(this::imprimirInforme);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();  // Consumir el evento para evitar el cierre predeterminado
                manejarCierre();
            }

            /**
             * Muestra un cuadro de confirmación cuando se intenta cerrar la
             * ventana. Permite confirmar si se desea salir y perder los cambios
             * no guardados.
             */
            private void manejarCierre() {

                // Se crea un cuadro de confirmación
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText("¿Está seguro de que desea cerrar la aplicación?");
                alert.setContentText("Todos los cambios no guardados se perderán.");

                // Se espera la respuesta del usuario
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Si el usuario confirma, se cierra la ventana
                    Stage stage = (Stage) btnCrearFila.getScene().getWindow();
                    stage.close();
                }

            }
        });

        // Configuración de columnas de la tabla
        idProveedorColumna.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
        calleColumna.setCellValueFactory(new PropertyValueFactory<>("calle"));
        calleColumna.setCellFactory(TextFieldTableCell.forTableColumn());
        calleColumna.setOnEditCommit(event -> {  // Permite la edición de la columna de calle
            Proveedor proveedor = event.getRowValue();
            proveedor.setCalle(event.getNewValue());
            proInterfaz.actualizarProveedor_XML(proveedor);  // Actualiza el proveedor en la base de datos
        });
        cifColumna.setCellValueFactory(new PropertyValueFactory<>("cif"));
        cifColumna.setCellFactory(TextFieldTableCell.forTableColumn());
        cifColumna.setOnEditCommit(event -> {  // Permite la edición de la columna CIF
            Proveedor proveedor = event.getRowValue();
            proveedor.setCif(event.getNewValue());
            proInterfaz.actualizarProveedor_XML(proveedor);
        });
        ciudadColumna.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        ciudadColumna.setCellFactory(TextFieldTableCell.forTableColumn());
        ciudadColumna.setOnEditCommit(event -> {  // Permite la edición de la columna de ciudad
            Proveedor proveedor = event.getRowValue();
            proveedor.setCiudad(event.getNewValue());
            proInterfaz.actualizarProveedor_XML(proveedor);
        });
        codPostalColumna.setCellValueFactory(new PropertyValueFactory<>("codPostal"));
        codPostalColumna.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        codPostalColumna.setOnEditCommit(event -> {  // Permite la edición de la columna de código postal
            Proveedor proveedor = event.getRowValue();
            proveedor.setCodPostal(event.getNewValue());
            proInterfaz.actualizarProveedor_XML(proveedor);
        });

        // Configuración de la columna de fecha de contratación
        fechaContratacionColumna.setCellValueFactory(new PropertyValueFactory<>("fechaContratacion"));
        fechaContratacionColumna.setCellFactory(event -> new TableCell<Proveedor, Date>() {
            private final DatePicker datePicker = new DatePicker();  // DatePicker para seleccionar fechas

            {
                datePicker.setOnAction(event -> {  // Actualiza la fecha del proveedor cuando se selecciona una nueva
                    int rowIndex = getIndex();
                    if (rowIndex >= 0 && rowIndex < tableView.getItems().size()) {
                        Proveedor proveedor = tableView.getItems().get(rowIndex);
                        LocalDate selectedDate = datePicker.getValue();
                        if (selectedDate != null) {
                            proveedor.setFechaContratacion(java.sql.Date.valueOf(selectedDate));  // Convierte a java.sql.Date
                            proInterfaz.actualizarProveedor_XML(proveedor);
                            tableView.refresh();  // Refresca la tabla después de la actualización
                        }
                    }
                });
            }

            // Método para actualizar la celda con la fecha seleccionada
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);  // Si no hay valor, no se muestra nada
                } else {
                    LocalDate localDate = new java.sql.Date(item.getTime()).toLocalDate();  // Convierte Date a LocalDate
                    datePicker.setValue(localDate);  // Establece el valor en el DatePicker
                    setGraphic(datePicker);  // Muestra el DatePicker en la celda
                }
            }
        });

        nombreProveedorColumna.setCellValueFactory(new PropertyValueFactory<>("nombreProveedor"));
        nombreProveedorColumna.setCellFactory(TextFieldTableCell.forTableColumn());

        // Acciones cuando se selecciona o deselecciona una fila
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnBorrar.setDisable(newValue == null);  // Habilita el botón de borrar solo si hay una fila seleccionada
        });

    }

    /**
     * Crea un nuevo proveedor y actualiza la tabla.
     *
     * @param event El evento asociado al botón de crear proveedor.
     */
    public void crearProveedor(ActionEvent event) {
        try {
            Proveedor proveedor = new Proveedor();
            proInterfaz.crearProveedor_XML(proveedor);  // Crea el nuevo proveedor en la base de datos
            tableView.setItems(mostrarProveedor());  // Actualiza la tabla con los proveedores
        } catch (Exception e) {
            LOGGER.info("Da error en crear");
        }
    }

    /**
     * Obtiene y muestra todos los proveedores en la tabla.
     *
     * @return Una lista observable de proveedores.
     */
    public ObservableList<Proveedor> mostrarProveedor() {
        todosProveedores = proInterfaz.mostrarTodosProveedores_XML(new GenericType<List<Proveedor>>() {
        });
        return todosProveedoresData = FXCollections.observableArrayList(todosProveedores);  // Convierte la lista en ObservableList
    }

    /**
     * Busca proveedores por ID o por fecha y actualiza la tabla.
     *
     * @param event El evento asociado al botón de búsqueda.
     */
    public void buscarProveedor(ActionEvent event) {
        Proveedor proveedor;

        if (!FieldIdProveedor.getText().equals("") && fechaFiltro.getValue() == null) {  // Si hay un ID de proveedor ingresado
            proveedor = ProveedorFactoria.get().mostrarProveedor_XML(Proveedor.class, FieldIdProveedor.getText());
            ProveedoresPorFechaData = FXCollections.observableArrayList(proveedor);
            tableView.setItems(ProveedoresPorFechaData);
        } else if (FieldIdProveedor.getText().equals("") && fechaFiltro.getValue() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fecha = fechaFiltro.getValue().format(formatter);  // Convierte la fecha del filtro en formato string
            ProveedoresPorFecha = ProveedorFactoria.get().mostrarsProveedoresFecha_XML(new GenericType<List<Proveedor>>() {
            }, fecha);
            ProveedoresPorFechaData = FXCollections.observableArrayList(ProveedoresPorFecha);  // Convierte la lista filtrada en ObservableList
            tableView.setItems(ProveedoresPorFechaData);
        } else if (!FieldIdProveedor.getText().equals("") && fechaFiltro.getValue() != null) {
            proveedor = ProveedorFactoria.get().mostrarProveedor_XML(Proveedor.class, FieldIdProveedor.getText());
            ProveedoresPorFechaData = FXCollections.observableArrayList(proveedor);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fecha = fechaFiltro.getValue().format(formatter);  // Convierte la fecha del filtro en formato string
            ProveedoresPorFecha = ProveedorFactoria.get().mostrarsProveedoresFecha_XML(new GenericType<List<Proveedor>>() {
            }, fecha);
            ProveedoresPorFechaData.addAll(ProveedoresPorFecha);

            tableView.setItems(ProveedoresPorFechaData);
        } else {
            tableView.setItems(mostrarProveedor());

        }

    }

    /**
     * Elimina el proveedor seleccionado de la tabla y la base de datos.
     *
     * @param event El evento asociado al botón de borrar.
     */
    public void borrarProveedor(ActionEvent event) {
        Proveedor proveedorSeleccionado = tableView.getSelectionModel().getSelectedItem();
        try {
            proInterfaz.borrarProveedor(String.valueOf(proveedorSeleccionado.getIdProveedor()));  // Elimina el proveedor en la base de datos
            tableView.getItems().remove(proveedorSeleccionado);  // Remueve la fila de la tabla
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

    /**
     * Vuelve al menú anterior del trabajador.
     *
     * @param event El evento asociado al botón de retroceso.
     */
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

       /**
     * Imprime un informe de los proveedores utilizando JasperReports.
     *
     * @param event El evento asociado al botón de imprimir informe.
     */
    @FXML
    private void imprimirInforme(ActionEvent event) {
        try {
            // Compilar el reporte desde el archivo JRXML
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/recursos/informeProveedor.jrxml"));

            // Obtener los datos de la tabla como fuente de datos
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource(
                    (Collection<Proveedor>) this.tableView.getItems()
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
            frame.setLocationRelativeTo(null);  // Centrar ventana
            frame.setVisible(true);

        } catch (JRException e) {
            // En caso de error al generar el reporte, se muestra un mensaje de error
            LOGGER.severe("Error al generar el reporte: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al generar el reporte.");
            alert.showAndWait();
        }
    }

}
