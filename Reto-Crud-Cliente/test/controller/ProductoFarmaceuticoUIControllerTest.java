/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import eus.tartangalh.crud.controladores.ProductoFarmaceuticoUIController;
import eus.tartangalh.crud.entidades.CategoriaProducto;
import eus.tartangalh.crud.entidades.ProductoFarmaceutico;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import static mondrian.olap.Util.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.junit.Assert.assertNotNull;
import org.testfx.api.FxRobot;
import org.testfx.util.WaitForAsyncUtils;

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
        controller = new ProductoFarmaceuticoUIController();
        controller.setStage(stage);
        controller.initStage(new javafx.scene.layout.VBox());
    }

    @Test
    public void setUp() {
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
    public void testAgregarEliminarYBuscarProducto(FxRobot robot) {
        // Test 1: Agregar Producto
        int initialSize = tableView.getItems().size();
        robot.clickOn(addButton);
        assertEquals(initialSize + 1, tableView.getItems().size());

        // Test 2: Eliminar Producto
        ProductoFarmaceutico producto = tableView.getItems().get(0);
        robot.interact(() -> tableView.getSelectionModel().select(producto));
        robot.clickOn(deleteButton);
        assertFalse(tableView.getItems().contains(producto));

        // Test 3: Buscar Producto por Nombre
        robot.clickOn(addButton); // Re-adding a product for the search test
        ProductoFarmaceutico newProduct = tableView.getItems().get(0); // Let's assume the new product added has a name.
        txtFiltro.setText(newProduct.getNombreProducto());  // Using the newly added product name for search
        combo.getSelectionModel().select("Nombre");
        robot.clickOn(searchButton);
        assertTrue(tableView.getItems().stream().anyMatch(p -> p.getNombreProducto().contains(newProduct.getNombreProducto())),
                "La búsqueda por nombre no devolvió el producto esperado");
    }

    @Test
    public void testFiltrarPorFecha(FxRobot robot) {
        // Test 1: Filtrar productos por fecha
        LocalDate fechaDesde = LocalDate.of(2023, 1, 1);
        LocalDate fechaHasta = LocalDate.of(2023, 12, 31);
        robot.clickOn(combo);
        robot.clickOn("Fecha de caducidad");
        robot.write(fechaDesde.toString());
        robot.write(fechaHasta.toString());
        robot.clickOn(searchButton);

        // Verifica que los productos en la tabla estén dentro del rango de fechas
        assertTrue(tableView.getItems().stream().allMatch(p -> {
            LocalDate fechaCaducidad = p.getFechaCaducidad().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            return !fechaCaducidad.isBefore(fechaDesde) && !fechaCaducidad.isAfter(fechaHasta);
        }), "La búsqueda por fecha no devolvió los productos esperados");
    }

    @Test
    public void testFiltrarPorCategoria(FxRobot robot) {
        // Test 2: Filtrar productos por categoría
        robot.clickOn(combo);
        robot.clickOn("Categoría");
        CategoriaProducto categoria = CategoriaProducto.CAPSULAS;
        robot.clickOn(catField);
        robot.clickOn(categoria.toString());
        robot.clickOn(searchButton);

        // Verifica que los productos en la tabla tengan la categoría seleccionada
        assertTrue(tableView.getItems().stream().allMatch(p -> p.getCategoria() == categoria),
                "La búsqueda por categoría no devolvió los productos esperados");
    }

    @Test
    public void testRecargarTabla(FxRobot robot) {
        // Test 3: Recargar la tabla
        int initialSize = tableView.getItems().size();
        robot.clickOn(addButton); // Agregar un nuevo producto
        robot.clickOn(searchButton); // Recargar la tabla
        assertTrue(tableView.getItems().size() > initialSize, "La tabla no se recargó correctamente");
    }
}