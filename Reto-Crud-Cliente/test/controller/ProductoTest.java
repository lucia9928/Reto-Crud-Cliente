/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import eus.tartangalh.crud.controladores.ProductoFarmaceuticoUIController;
import eus.tartangalh.crud.entidades.Almacen;
import eus.tartangalh.crud.entidades.ProductoFarmaceutico;
import eus.tartangalh.crud.entidades.Trabajador;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testfx.framework.junit.ApplicationTest;
import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.control.ButtonMatchers.isDefaultButton;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductoTest extends ApplicationTest {

    private TableView<ProductoFarmaceutico> productoTableView;

    @Override
    public void start(Stage stage) throws Exception {
        Trabajador trabajador = new Trabajador();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto/crud/cliente/ProductoFarmaceuticoUI.fxml"));
        Parent root = loader.load();
        ProductoFarmaceuticoUIController producto = loader.getController();
        producto.setStage(stage);
        producto.setTrabajador(trabajador);
        producto.initStage(root);

        // Inicializar la tabla y otros componentes
        productoTableView = (TableView<ProductoFarmaceutico>) lookup("#tableView").query();
    }

    @Test
    public void testA_inicializacionVentana() {
        // Comprobar el estado inicial de la ventana
        verifyThat("#combo", isVisible());
        verifyThat("#tableView", isVisible());
        verifyThat("#addButton", isVisible());
        verifyThat("#deleteButton", isVisible());
        verifyThat("#btnImprimir", isVisible());
    }

    @Test
    public void testB_borrado() {
        int rowCount = productoTableView.getItems().size();
        assertTrue("La tabla no tiene información, no se puede probar", rowCount > 0);

        Node row = lookup(".table-row-cell").nth(0).query(); // Obtiene la primera fila
        Node cell = lookup(".table-cell").nth(0).query(); // Obtiene la primera celda de la fila
        assertNotNull("Cell is null: table has not that cell.", cell);
        clickOn(cell); // Hace clic en la primera celda
        verifyThat("#addButton", isEnabled());
        clickOn("#deleteButton");
        clickOn(isDefaultButton());
        assertEquals("La fila no ha sido eliminada", rowCount - 1, productoTableView.getItems().size());
    }

    @Test
    public void testC_creado() {
        clickOn("#addButton");
        sleep(1000); // Dar tiempo para que la UI se actualice
        int rowCountBefore = productoTableView.getItems().size();
        assertTrue("No se añadió ninguna fila.", rowCountBefore > 0); // Verifica que se añadió una fila
    }

    @Test
    public void testD_modificado() {
        int rowCountBefore = productoTableView.getItems().size();

        // Modificar la celda de la primera columna (nombre del producto)
        Node cell = lookup(".table-cell").nth(1).query();
        assertNotNull("Cell is null: table has not that cell.", cell);
        doubleClickOn(cell);
        write("Aposito");
        push(KeyCode.ENTER);

        // Modificar la celda de la segunda columna (código)
        cell = lookup(".table-cell").nth(2).query();
        assertNotNull("Cell is null: table has not that cell.", cell);
        doubleClickOn(cell);
        write("L0012");
        push(KeyCode.ENTER);

        // Modificar la celda de la tercera columna (fecha)
        cell = lookup(".table-cell").nth(3).query();
        assertNotNull("Cell is null: table has not that cell.", cell);
        doubleClickOn(cell);
        for (int i = 0; i < 10; i++) {
            push(KeyCode.BACK_SPACE); // Eliminar contenido de la celda
        }
        write("24/05/2026");
        push(KeyCode.ENTER);

        // Modificar la celda de la cuarta columna (descripcion)
        cell = lookup(".table-cell").nth(4).query();
        assertNotNull("Cell is null: table has not that cell.", cell);
        doubleClickOn(cell);
        for (int i = 0; i < 25; i++) {
            push(KeyCode.BACK_SPACE); // Eliminar contenido de la celda
        }
        write("Para el tratamiento de heridas pequeñas.");
        push(KeyCode.ENTER);

        cell = lookup(".table-cell").nth(5).query(); // Asumimos que la columna 5 es la de "Categoría"
        assertNotNull("La celda de categoría es null.", cell);

        clickOn(cell);
        doubleClickOn(cell);
        push(KeyCode.DOWN);
        push(KeyCode.ENTER);

    }

    @Test
    public void testE_listado() {
        clickOn("#combo");
        push(KeyCode.DOWN);  // Navegar hacia el primer elemento (presionar hacia abajo)
        push(KeyCode.ENTER); // Seleccionar el primer elemento
        clickOn("#txtFiltro");
        write("5");
        clickOn("#searchButton");

        clickOn("#combo");
        push(KeyCode.DOWN);  // Navegar nuevamente
        push(KeyCode.ENTER); // Seleccionar otro valor
        clickOn("#txtFechaDesde");
        write("01/01/2025");
        clickOn("#txtFechaHasta");
        write("15/12/2025");
        clickOn("#searchButton"); // Hacer la búsqueda con los filtros aplicados
    }
}
