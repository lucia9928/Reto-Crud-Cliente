import eus.tartangalh.crud.controladores.ProductoFarmaceuticoUIController;
import eus.tartangalh.crud.entidades.CategoriaProducto;
import eus.tartangalh.crud.entidades.ProductoFarmaceutico;
import java.time.LocalDate;
import java.time.ZoneId;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.testfx.api.FxRobot;

public class ProductoFarmaceuticoUIControllerTest extends ApplicationTest {

    private ProductoFarmaceuticoUIController controller;
    private TableView<ProductoFarmaceutico> tableView;
    private Button addButton, deleteButton, searchButton;
    private TextField txtFiltro;
    private DatePicker txtFechaDesde, txtFechaHasta;
    private ChoiceBox<CategoriaProducto> catField;
    private ComboBox<String> combo;

    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML file and set up the scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/ProductoFarmaceuticoUI.fxml"));
        Parent root = loader.load();

        // Get the controller from the FXML loader
        controller = loader.getController();

        // Set the stage and show the scene
        stage.setScene(new Scene(root));
        stage.show();

        // Initialize your controller if necessary
        controller.setStage(stage);
    }

    @Test
    public void setUp() {
        // Initialize the UI elements by looking them up
        tableView = lookup("#tableView").query();
        addButton = lookup("#addButton").query();
        deleteButton = lookup("#deleteButton").query();
        searchButton = lookup("#searchButton").query();
        txtFiltro = lookup("#txtFiltro").query();
        txtFechaDesde = lookup("#txtFechaDesde").query();
        txtFechaHasta = lookup("#txtFechaHasta").query();
        catField = lookup("#catField").query();
        combo = lookup("#combo").query();
    }

    @Test
    public void testAgregarEliminarYBuscarProducto() {
        // Test 1: Agregar Producto
        int initialSize = tableView.getItems().size();
        clickOn(addButton);
        assertEquals(initialSize + 1, tableView.getItems().size());

        // Test 2: Eliminar Producto
        ProductoFarmaceutico producto = tableView.getItems().get(0);
        interact(() -> tableView.getSelectionModel().select(producto));
        clickOn(deleteButton);
        assertFalse(tableView.getItems().contains(producto));

        // Test 3: Buscar Producto por Nombre
        clickOn(addButton); // Re-adding a product for the search test
        ProductoFarmaceutico newProduct = tableView.getItems().get(0); // Let's assume the new product added has a name.
        txtFiltro.setText(newProduct.getNombreProducto());  // Using the newly added product name for search
        combo.getSelectionModel().select("Nombre");
        clickOn(searchButton);
        assertTrue("La búsqueda por nombre no devolvió el producto esperado",
                tableView.getItems().stream().anyMatch(p -> p.getNombreProducto().contains(newProduct.getNombreProducto())));
    }

    @Test
    public void testFiltrarPorFecha() {
        // Test 1: Filtrar productos por fecha
        LocalDate fechaDesde = LocalDate.of(2023, 1, 1);
        LocalDate fechaHasta = LocalDate.of(2023, 12, 31);
        clickOn(combo);
        clickOn("Fecha de caducidad");
        write(fechaDesde.toString());
        write(fechaHasta.toString());
        clickOn(searchButton);

        // Verifica que los productos en la tabla estén dentro del rango de fechas
        assertTrue("La búsqueda por fecha no devolvió los productos esperados",
                tableView.getItems().stream().allMatch(p -> {
                    LocalDate fechaCaducidad = p.getFechaCaducidad().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    return !fechaCaducidad.isBefore(fechaDesde) && !fechaCaducidad.isAfter(fechaHasta);
                }));
    }

    @Test
    public void testFiltrarPorCategoria() {
        // Test 2: Filtrar productos por categoría
        clickOn(combo);
        clickOn("Categoría");
        CategoriaProducto categoria = CategoriaProducto.CAPSULAS;
        clickOn(catField);
        clickOn(categoria.toString());
        clickOn(searchButton);

        // Verifica que los productos en la tabla tengan la categoría seleccionada
        assertTrue("La búsqueda por categoría no devolvió los productos esperados",
                tableView.getItems().stream().allMatch(p -> p.getCategoria() == categoria));
    }

    @Test
    public void testRecargarTabla() {
        // Test 3: Recargar la tabla
        int initialSize = tableView.getItems().size();
        clickOn(addButton); // Agregar un nuevo producto
        clickOn(searchButton); // Recargar la tabla
        assertTrue("La tabla no se recargó correctamente", tableView.getItems().size() > initialSize);
    }
}