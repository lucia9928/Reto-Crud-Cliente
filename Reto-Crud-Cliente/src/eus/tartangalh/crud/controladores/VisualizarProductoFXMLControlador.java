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
import eus.tartangalh.crud.interfaces.GestionaInterfaz;
import eus.tartangalh.crud.interfaces.GestionaManagerFactoria;
import eus.tartangalh.crud.interfaces.ProductoInterfaz;
import eus.tartangalh.crud.interfaces.ProductoInterfazFactoria;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
 * FXML Controller class para la visualización de productos farmacéuticos en una
 * interfaz gráfica. Permite al cliente visualizar los productos disponibles,
 * ver detalles como nombre, fecha de caducidad, descripción, categoría y
 * precio, y realizar compras seleccionando productos de la lista.
 *
 * <p>
 * La clase también incluye funcionalidades para confirmar compras con un modal,
 * manejar la cantidad de productos y navegar al menú del cliente.</p>
 *
 * @author markel
 */
public class VisualizarProductoFXMLControlador {

    /**
     * Interfaz para acceder a los productos farmacéuticos.
     */
    private final ProductoInterfaz proInterfaz = ProductoInterfazFactoria.get();

    /**
     * Interfaz para gestionar la cantidad de productos disponibles.
     */
    private final GestionaInterfaz gesInterfaz = GestionaManagerFactoria.get();

    @FXML
    private Button btnComprar; // Botón para comprar un producto.
    @FXML
    private Button btnAtras; // Botón para regresar al menú del cliente.

    @FXML
    private TableView<ProductoFarmaceutico> tableView; // Vista de tabla para mostrar los productos.

    @FXML
    private TableColumn<ProductoFarmaceutico, String> nombreColumn; // Columna para el nombre del producto.
    @FXML
    private TableColumn<ProductoFarmaceutico, Date> caducidadColumn; // Columna para la fecha de caducidad.
    @FXML
    private TableColumn<ProductoFarmaceutico, String> descripcionColumn; // Columna para la descripción del producto.
    @FXML
    private TableColumn<ProductoFarmaceutico, CategoriaProducto> categoriaColumn; // Columna para la categoría del producto.
    @FXML
    private TableColumn<ProductoFarmaceutico, Float> precioColumn; // Columna para el precio del producto.

    private Stage stage; // Ventana de la aplicación.
    private Cliente cliente; // Cliente que está visualizando los productos.

    private List<ProductoFarmaceutico> todosProductos; // Lista de todos los productos disponibles.
    private ObservableList<ProductoFarmaceutico> todosProductosData; // Lista observable de productos para la vista.

    /**
     * Establece la ventana principal (stage) para la vista de productos.
     *
     * @param stage El objeto Stage que representa la ventana principal.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Establece el cliente que está visualizando los productos.
     *
     * @param cliente El cliente que está visualizando los productos.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Inicializa la vista de productos farmacéuticos, configurando la escena,
     * los iconos y mostrando los productos en la tabla. También establece los
     * manejadores de eventos para los botones de compra y regresar al menú.
     *
     * @param root El nodo raíz de la interfaz de usuario.
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("recursos/iconoFarmacia.png"));
        stage.setTitle("Visualizar productos");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        // Configura los botones con sus respectivos manejadores de eventos
        btnComprar.setOnAction(this::comprarProductos);
        btnAtras.setOnAction(this::menuCliente);

        // Establece los productos en la tabla
        tableView.setItems(mostrarProductos());
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        caducidadColumn.setCellValueFactory(new PropertyValueFactory<>("fechaCaducidad"));
        descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));
    }

    /**
     * Muestra los productos farmacéuticos disponibles obtenidos desde la
     * interfaz de producto.
     *
     * @return Una lista observable de productos farmacéuticos.
     */
    public ObservableList<ProductoFarmaceutico> mostrarProductos() {
        todosProductos = proInterfaz.encontrarTodos_XML(new GenericType<List<ProductoFarmaceutico>>() {
        });

        // Convierte la lista de productos en una lista observable para la TableView
        return todosProductosData = FXCollections.observableArrayList(todosProductos);
    }

    /**
     * Maneja la acción de comprar un producto. Muestra un modal para confirmar
     * la compra y manejar la cantidad de productos. Verifica el stock
     * disponible y actualiza la cantidad en el sistema si la compra es
     * confirmada.
     *
     * @param event El evento que dispara la acción de compra (clic en el botón
     * de comprar).
     */
    public void comprarProductos(ActionEvent event) {
        ProductoFarmaceutico productoFarmaceutico = tableView.getSelectionModel().getSelectedItem();

        // Si se selecciona un producto
        if (productoFarmaceutico != null) {
            Stage modal = new Stage();
            modal.initModality(Modality.WINDOW_MODAL);
            modal.initOwner(stage);
            modal.setTitle("Confirmar Compra");

            // Mensaje con el nombre del producto
            Label lblMensaje = new Label("Producto: " + productoFarmaceutico.getNombreProducto());

            // Contenedor para la cantidad de productos
            Label lblCantidad = new Label("Cantidad:");
            Label lblCantidadValor = new Label("1");  // Valor por defecto de la cantidad
            lblCantidadValor.setStyle("-fx-font-size: 18px;");

            // Botones para incrementar o decrementar la cantidad
            Button btnMenos = new Button("-");
            Button btnMas = new Button("+");

            btnMenos.setOnAction(e -> {
                int cantidad = Integer.parseInt(lblCantidadValor.getText());
                if (cantidad > 1) {  // No permitir cantidad menor a 1
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

                // Verifica la cantidad disponible en stock
                List<Gestiona> gestiones = gesInterfaz.mostrarTodosGestiona_XML(new GenericType<List<Gestiona>>() {
                });
                for (Gestiona gestion : gestiones) {
                    if (gestion.getGestionaId().getIdProducto() == productoFarmaceutico.getIdProducto()) {
                        if (gestion.getCantidad() < cantidad) {
                            mostrarAlerta("Información", "Stock máximo: " + gestion.getCantidad() + " unidades");
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

            // Disposición de los elementos en el modal
            VBox modalLayout = new VBox(10, lblMensaje, lblCantidad, lblCantidadValor, btnMenos, btnMas, btnConfirmar);
            modalLayout.setAlignment(Pos.CENTER);

            Scene modalScene = new Scene(modalLayout, 250, 200);
            modal.setScene(modalScene);
            modal.showAndWait();
        } else {
            mostrarAlerta("Información", "Selecciona un producto.");
        }
    }

    /**
     * Navega hacia el menú del cliente cuando se hace clic en el botón "Atrás".
     *
     * @param event El evento que dispara esta acción (clic en el botón de
     * atrás).
     */
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

    /**
     * Muestra una alerta con el título y el mensaje especificado.
     *
     * @param titulo El título de la alerta.
     * @param mensaje El mensaje de la alerta.
     * @return true si el usuario hizo clic en el botón OK, false en caso
     * contrario.
     */
    private boolean mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        Optional<ButtonType> resultado = alert.showAndWait();
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }
}
