/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto.crud.cliente;

import eus.tartangalh.crud.entidades.ProductoFarmaceutico;
import eus.tartangalh.crud.interfaces.ProductoInterfazFactoria;
import java.util.List;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;

/**
 * FXML Controller class
 *
 * @author Oscar
 */
public class ProductoFarmaceuticoUIController {


    @FXML
    private DatePicker desdeDatePicker;

    @FXML
    private DatePicker hastaDatePicker;

    @FXML
    private TextField idField;

    @FXML
    private TextField nombreField;

    @FXML
    private TextField loteField;

    @FXML
    private TextField catField;

    @FXML
    private Button searchButton;

    @FXML
    private TableView<ProductoFarmaceutico> tableView;

    @FXML
    private TableColumn<ProductoFarmaceutico, String> idColumn;

    @FXML
    private TableColumn<ProductoFarmaceutico, String> nombreColumn;

    @FXML
    private TableColumn<ProductoFarmaceutico, String> loteColumn;

    @FXML
    private TableColumn<ProductoFarmaceutico, String> caducidadColumn;

    @FXML
    private TableColumn<ProductoFarmaceutico, String> descripcionColumn;

    @FXML
    private TableColumn<ProductoFarmaceutico, String> categoriaColumn;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button confirmButton;

private Stage stage;

    private static final Logger LOGGER = Logger.getLogger("ProveedorControlador.view");

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) {
        LOGGER.info("Inicializando controlador de proveedor");

        Scene scene = new Scene(root);

        stage.show();
        stage.setScene(scene);
        // Configurar las columnas de la tabla
    idColumn.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
    nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
    loteColumn.setCellValueFactory(new PropertyValueFactory<>("loteProducto"));
    caducidadColumn.setCellValueFactory(new PropertyValueFactory<>("fechaCaducidad"));
    descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
    categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));

    // Llamada al servicio para obtener los productos
    try {
        // Obtener los productos desde la API o servicio
        List<ProductoFarmaceutico> productosRecibidos = ProductoInterfazFactoria.get()
                .encontrarTodos_XML(new GenericType<List<ProductoFarmaceutico>>() {
                });

        // Convertir la lista de productos a un ObservableList
        ObservableList<ProductoFarmaceutico> productos = FXCollections.observableArrayList(productosRecibidos);

        // Establecer los productos obtenidos en la TableView
        tableView.setItems(productos);

    } catch (Exception e) {
        // Manejo de excepciones si ocurre un error en la conexión o en la obtención de productos
        System.out.println("Error al cargar los productos: " + e.getMessage());
        e.printStackTrace();
    }
}
/*
    private void listarProductos() {
        try {
            // Llamar a la interfaz para obtener la lista de productos
            List<ProductoFarmaceutico> productosRecibidos = ProductoInterfazFactoria.get()
                    .encontrarTodos_XML(new GenericType<List<ProductoFarmaceutico>>() {});

            // Limpiar la lista actual y agregar los productos obtenidos
            productos.clear();
            productos.addAll(productosRecibidos);

            System.out.println("Productos cargados desde el servicio: " + productosRecibidos);

        } catch (Exception e) {
            System.out.println("Error al cargar los productos: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void onSearch() {
        // Lógica básica de búsqueda
        String desde = desdeDatePicker.getValue() != null ? desdeDatePicker.getValue().toString() : "N/A";
        String hasta = hastaDatePicker.getValue() != null ? hastaDatePicker.getValue().toString() : "N/A";
        String id = idField.getText().trim();
        String nombre = nombreField.getText().trim();
        String lote = loteField.getText().trim();
        String categoria = catField.getText().trim();

        System.out.println("Buscar productos:");
        System.out.printf("Desde: %s, Hasta: %s, ID: %s, Nombre: %s, Lote: %s, Categoría: %s%n",
                desde, hasta, id, nombre, lote, categoria);

    }

    private void onAdd() {
        // Lógica para añadir un nuevo producto
        ProductoFarmaceutico nuevoProducto = new ProductoFarmaceutico();
        productos.add(nuevoProducto);
        System.out.println("Producto añadido: " + nuevoProducto);
    }

    private void onDelete() {
        // Lógica para eliminar el producto seleccionado
        ProductoFarmaceutico seleccionado = tableView.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            productos.remove(seleccionado);
            System.out.println("Producto eliminado: " + seleccionado);
        } else {
            System.out.println("No hay producto seleccionado para eliminar.");
        }
    }

    private void onConfirm() {
        // Lógica para confirmar edición (muestra los datos actuales)
        for (ProductoFarmaceutico producto : productos) {
            System.out.println(producto);
        }
        System.out.println("Cambios confirmados.");
    }
*/
}
