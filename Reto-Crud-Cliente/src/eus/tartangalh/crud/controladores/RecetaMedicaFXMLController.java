package eus.tartangalh.crud.controladores;

import eus.tartangalh.crud.entidades.ProductoFarmaceutico;
import eus.tartangalh.crud.entidades.RecetaMedica;
import eus.tartangalh.crud.interfaces.RecetaMedicaFactoria;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

public class RecetaMedicaFXMLController {
   
    @FXML
    private DatePicker desde;
    @FXML
    private DatePicker hasta;
    @FXML
    private Button btnCrearReceta;
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
   

    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger("RecetaMedicaFXMLController.view");
   
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) {
        LOGGER.info("Inicializando controlador de receta médica");
        Scene scene = new Scene(root);
        stage.setTitle("Recetas Médicas");
        stage.setScene(scene);
        stage.show();
        configurarTabla();
    }
    private void configurarTabla() {
        // Configuración de columnas
          colId.setCellValueFactory(new PropertyValueFactory<>("idReceta"));
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
         configurarTablaEditable();
    }
    
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

   private List<ProductoFarmaceutico> buscarProductosPorReceta(String idReceta) {
    try {
        List<ProductoFarmaceutico> productos = RecetaMedicaFactoria.get().obtenerProductosPorReceta(
            new GenericType<List<ProductoFarmaceutico>>() {}, 
            idReceta);
        return productos;
    } catch (WebApplicationException e) {
        LOGGER.log(Level.SEVERE, "Error al buscar productos: {0}", e.getMessage());
        return Collections.emptyList();
    }
    }
    
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

        {
            datePicker.setOnAction(e -> {
                if (!isEmpty() && getTableRow() != null) {
                    LocalDate localDate = datePicker.getValue();
                    Date fecha = java.sql.Date.valueOf(localDate);
                    commitEdit(fecha);
                }
            });
        }

        @Override
        protected void updateItem(Date item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                LocalDate date = item.toInstant()
                                   .atZone(ZoneId.systemDefault())
                                   .toLocalDate();
                datePicker.setValue(date);
                setGraphic(datePicker);
            }
        }

        @Override
        public void commitEdit(Date newValue) {
            super.commitEdit(newValue);
            RecetaMedica receta = getTableView().getItems().get(getIndex());
            receta.setFechaReceta(newValue);
         RecetaMedicaFactoria.get().modificarRecetaMedica_XML(receta);
        }
    });
    // Columna Productos (Editable con diálogo personalizado)
    colProductos.setCellFactory(col -> new TableCell<RecetaMedica, String>() {
        private final Button editButton = new Button("Editar Productos");
        
        {
            editButton.setOnAction(e -> {
                RecetaMedica receta = getTableView().getItems().get(getIndex());
                
            });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(editButton);
                setText(item); // Muestra los productos actuales
            }
        }
    });
}
    @FXML
    private void aniadirUnaLinea() {

        // Crear una fila vacía con valores por defecto o nulos
        RecetaMedica receta = new RecetaMedica(0,null, null, 0, null );

        // Obtener la lista observable de la tabla y añadir la nueva fila vacía
        ObservableList<RecetaMedica> recetas = tableRecetas.getItems();
        recetas.add(receta);

        LOGGER.info("Nueva fila vacía añadida.");
    }

    @FXML
    private void handleBuscar() {
        LOGGER.info("Buscando recetas médicas por filtros.");
        // Implementar lógica de búsqueda aquí si se necesitan filtros (como por fechas)
    }
    
}
