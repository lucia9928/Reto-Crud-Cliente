package eus.tartangalh.crud.controladores;

import eus.tartangalh.crud.entidades.ProductoFarmaceutico;
import eus.tartangalh.crud.entidades.RecetaMedica;
import eus.tartangalh.crud.interfaces.RecetaMedicaFactoria;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;

public class RecetaMedicaFXMLController {

    @FXML
    private DatePicker desde;
    @FXML
    private DatePicker hasta;
    @FXML
    private TableView<RecetaMedica> tableRecetas;
    @FXML
    private TableColumn<RecetaMedica, String> colId;
    @FXML
    private TableColumn<RecetaMedica, String> colCliente;
    @FXML
    private TableColumn<RecetaMedica, String> colDescripcion;
    @FXML
    private TableColumn<RecetaMedica, Date> colFecha;
    @FXML
    private TableColumn<RecetaMedica, String> colProductos;

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
       /** colCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
       listaProductos.setCellValueFactory(new PropertyValueFactory<>("productos"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaReceta"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            List<RecetaMedica> recetas = RecetaMedicaFactoria.get().encontrarTodasLasRecetas_XML(new GenericType<List<RecetaMedica>>(){});
            ObservableList<RecetaMedica> recetaList = FXCollections.observableArrayList(recetas);
            tableRecetas.setItems(recetaList);
*/
        configurarTabla();
        cargarRecetas();
    }

    private void configurarTabla() {
        // Configuración de columnas
        Integer num=1;
        colId.setCellValueFactory(new PropertyValueFactory<>("idReceta"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
      List<ProductoFarmaceutico> producto= buscarProductosPorReceta(num.toString());
     //colProductos.setCellFactory((Callback<TableColumn<RecetaMedica, String>, TableCell<RecetaMedica, String>>) (producto));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaReceta"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        
    }
    
    
    private void cargarRecetas() {
        try {
            // Obtener todas las recetas médicas desde el servicio REST
            
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
         ProductoFarmaceutico lista=null;
    try {
        // Llamar al método REST para obtener los productos farmacéuticos
         lista = RecetaMedicaFactoria.get().obtenerProductosPorReceta(new GenericType<ProductoFarmaceutico>() {
            }, idReceta);
        if (lista != null && lista.getNombreProducto() != null) {
            return FXCollections.observableArrayList(lista);
            
        }
        LOGGER.log(Level.WARNING, "No se encontraron productos para la receta con ID {0}", idReceta);
    } catch (WebApplicationException e) {
        LOGGER.log(Level.SEVERE, "Error al buscar productos para la receta con ID {0}: {1}", new Object[]{idReceta, e.getMessage()});
    }
    return (List<ProductoFarmaceutico>) lista;
}
    
 /**  private Cliente buscarCliente (){
        Cliente cliente = null;
        cliente = ClienteFactoria.get().encontrarTodosLosClientes_XML(Cliente.class);
        ObservableList<Cliente> clienteList = FXCollections.observableArrayList(cliente);
        for (Cliente cliente1 : clienteList) {
            cliente1.getNombre();
        }
    return cliente;
    }*/
    @FXML
    private void handleBuscar() {
        LOGGER.info("Buscando recetas médicas por filtros.");
        // Implementar lógica de búsqueda aquí si se necesitan filtros (como por fechas)
    }
    
}
