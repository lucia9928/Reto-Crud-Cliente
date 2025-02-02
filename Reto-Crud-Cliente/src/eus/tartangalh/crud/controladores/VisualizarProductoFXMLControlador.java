/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.controladores;

import eus.tartangalh.crud.entidades.CategoriaProducto;
import eus.tartangalh.crud.entidades.Cliente;
import eus.tartangalh.crud.entidades.Gestiona;
import eus.tartangalh.crud.entidades.ProductoFarmaceutico;
import eus.tartangalh.crud.entidades.Proveedor;
import eus.tartangalh.crud.interfaces.GestionaInterfaz;
import eus.tartangalh.crud.interfaces.GestionaManagerFactoria;
import eus.tartangalh.crud.interfaces.ProductoInterfaz;
import eus.tartangalh.crud.interfaces.ProductoInterfazFactoria;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    private final GestionaInterfaz gesInterfaz = GestionaManagerFactoria.get();

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
    //@FXML
    // private TableColumn<ProductoFarmaceutico, Float> precioColumn;
    private Stage stage;
    private Cliente cliente;
    List<ProductoFarmaceutico> todosProductos;
    ObservableList<ProductoFarmaceutico> todosProductosData;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("recursos/iconoFarmacia.png"));
        stage.setTitle("Visualizar productos");
        stage.setScene(scene);
        stage.show();

        btnComprar.setOnAction(this::comprarProductos);
        btnAtras.setOnAction(this::menuCliente);

        tableView.setItems(mostrarProductos());
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        caducidadColumn.setCellValueFactory(new PropertyValueFactory<>("fechaCaducidad"));
        descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        //precioColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));

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

            // Mensaje de producto
            Label lblMensaje = new Label("Producto: " + productoFarmaceutico.getNombreProducto());

            // Contenedor para la cantidad de productos
            Label lblCantidad = new Label("Cantidad:");
            Label lblCantidadValor = new Label("1");  // Por defecto 1
            lblCantidadValor.setStyle("-fx-font-size: 18px;");

            // Botones de incrementar y decrementar cantidad
            Button btnMenos = new Button("-");
            Button btnMas = new Button("+");

            btnMenos.setOnAction(e -> {
                int cantidad = Integer.parseInt(lblCantidadValor.getText());
                if (cantidad > 1) {  // No permitir menos de 1
                    lblCantidadValor.setText(String.valueOf(cantidad - 1));
                }
            });

            btnMas.setOnAction(e -> {
                int cantidad = Integer.parseInt(lblCantidadValor.getText());
                lblCantidadValor.setText(String.valueOf(cantidad + 1));
            });

            // Botón para confirmar la compra
            Button btnConfirmar = new Button("Confirmar");
            btnConfirmar.setOnAction(e -> {
                int cantidad = Integer.parseInt(lblCantidadValor.getText());
                // Aquí puedes manejar la compra, como enviar la cantidad al backend
                List<Gestiona> gestiones = gesInterfaz.mostrarTodosGestiona_XML(new GenericType<List<Gestiona>>() {
                });
                for (Gestiona gestion : gestiones) {
                    if (gestion.getGestionaId().getIdProducto() == productoFarmaceutico.getIdProducto()) {

                        if (gestion.getCantidad() < cantidad) {
                            mostrarAlerta("Informacion", "Stock maximo "+gestion.getCantidad()+" unidades");
                            break;
                        } else {
                            gestion.setCantidad(gestion.getCantidad() - cantidad);
                            gesInterfaz.actualizarGestiona_XML(gestion);
                            mostrarAlerta("Compra Confirmada", "Has comprado " + cantidad + " unidades de " + productoFarmaceutico.getNombreProducto());
                            modal.close();
                            break;
                        }

                    }
                }

                
                
            });

            // Disposición del contenido en el modal
            VBox modalLayout = new VBox(10, lblMensaje, lblCantidad, lblCantidadValor, btnMenos, btnMas, btnConfirmar);
            modalLayout.setAlignment(Pos.CENTER);

            Scene modalScene = new Scene(modalLayout, 250, 200);
            modal.setScene(modalScene);
            modal.showAndWait();
        } else {
            mostrarAlerta("Información", "Selecciona un producto");
        }
    }

    private void menuCliente(ActionEvent event) {
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

    private boolean mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }
}
