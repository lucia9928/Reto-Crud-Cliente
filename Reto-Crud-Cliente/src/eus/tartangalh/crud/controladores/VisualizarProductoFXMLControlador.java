/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.controladores;

import eus.tartangalh.crud.entidades.CategoriaProducto;
import eus.tartangalh.crud.entidades.ProductoFarmaceutico;
import eus.tartangalh.crud.entidades.Proveedor;
import eus.tartangalh.crud.interfaces.ProductoInterfaz;
import eus.tartangalh.crud.interfaces.ProductoInterfazFactoria;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;

/**
 * FXML Controller class
 *
 * @author markel
 */
public class VisualizarProductoFXMLControlador {

    private final ProductoInterfaz proInterfaz = ProductoInterfazFactoria.get();

    @FXML
    private Button btnComprar;
    @FXML
    private Button btnAtras;

    @FXML
    private TableView<ProductoFarmaceutico> tableView;

    @FXML
    private TableColumn<ProductoFarmaceutico, String> nombreColumn;

    @FXML
    private TableColumn<ProductoFarmaceutico, Date> caducidadColumn;

    @FXML
    private TableColumn<ProductoFarmaceutico, String> descripcionColumn;

    @FXML
    private TableColumn<ProductoFarmaceutico, CategoriaProducto> categoriaColumn;
    private Stage stage;
    List<ProductoFarmaceutico> todosProductos;
    ObservableList<ProductoFarmaceutico> todosProductosData;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("recursos/iconoFarmacia.png"));
        stage.setTitle("Visualizar productos");
        stage.setScene(scene);
        stage.show();

        btnComprar.setOnAction(this::comprarProductos);

        tableView.setItems(mostrarProductos());
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        caducidadColumn.setCellValueFactory(new PropertyValueFactory<>("fechaCaducidad"));
        descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));

    }

    public ObservableList<ProductoFarmaceutico> mostrarProductos() {
        todosProductos = proInterfaz.encontrarTodos_XML(new GenericType<List<ProductoFarmaceutico>>() {
        });

        // Convertir la lista de proveedores en ObservableList para la TableView
        return todosProductosData = FXCollections.observableArrayList(todosProductos);

    }

    public void comprarProductos(ActionEvent event) {
        ProductoFarmaceutico productoFarmaceutico = tableView.getSelectionModel().getSelectedItem();

        if (productoFarmaceutico != null) {
            Stage modal = new Stage();
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(stage);
            modal.setTitle("Confirmar Compra");

            Label lblMensaje = new Label("Producto: " + productoFarmaceutico.getNombreProducto());

            VBox modalLayout = new VBox(10, lblMensaje);
            modalLayout.setAlignment(Pos.CENTER);

            Scene modalScene = new Scene(modalLayout, 250, 150);
            modal.setScene(modalScene);
            modal.showAndWait();
        } else {
            mostrarAlerta("Informacion", "Selecciona un producto");
        }

    }

    private boolean mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }
}
