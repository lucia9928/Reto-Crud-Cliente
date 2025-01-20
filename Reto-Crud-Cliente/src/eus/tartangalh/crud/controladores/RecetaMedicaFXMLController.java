package eus.tartangalh.crud.controladores;

import eus.tartangalh.crud.entidades.ProductoFarmaceutico;
import eus.tartangalh.crud.entidades.RecetaMedica;
import eus.tartangalh.crud.interfaces.RecetaMedicaFactoria;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;

public class RecetaMedicaFXMLController {

    @FXML
    private DatePicker desde;
    @FXML
    private DatePicker hasta;
    @FXML
    private TableView<RecetaMedica> tableRecetas;

    @FXML
    private TableColumn<RecetaMedica, String> colCliente;

    @FXML
    private TableColumn<RecetaMedica, String> colDescripcion;

 
    @FXML
    private TableColumn<RecetaMedica, Date> colFecha;
    
    @FXML
    private TableColumn<RecetaMedica, ProductoFarmaceutico> listaProductos;
    private Stage stage;
    public void setStage(Stage stage) {
             this.stage=stage;

    }
    public void initStage(Parent root) {
               

    
        Scene scene= new Scene(root);
        stage.setTitle("Recetas medicas");
        stage.show();
        stage.setScene(scene);
        // Configurar columnas
        colCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        listaProductos.setCellValueFactory(new PropertyValueFactory<>("listaProductos"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaReceta"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            List<RecetaMedica> recetas = RecetaMedicaFactoria.get().encontrarTodasLasRecetas_XML(new GenericType<List<RecetaMedica>>(){});
            ObservableList<RecetaMedica> recetaList = FXCollections.observableArrayList(recetas);
            tableRecetas.setItems(recetaList);

    }

    @FXML
    private void handleBuscar(ActionEvent event) {
        // Implementar lógica de búsqueda
    }
}

    
