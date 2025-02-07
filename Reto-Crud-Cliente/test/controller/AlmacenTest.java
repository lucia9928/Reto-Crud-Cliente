/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.lowagie.text.pdf.TextField;
import eus.tartangalh.crud.controladores.AlmacenFXMLControlador;
import eus.tartangalh.crud.controladores.ContextMenuManager;
import eus.tartangalh.crud.entidades.Almacen;
import eus.tartangalh.crud.entidades.Trabajador;
import java.awt.Button;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import reto.crud.cliente.RetoCrudCliente;
import java.util.List;
import java.util.Random;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import javafx.stage.Stage;
import static org.hamcrest.Matchers.greaterThan;
import static org.hibernate.criterion.Projections.rowCount;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Ignore;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.ButtonMatchers.isCancelButton;
import static org.testfx.matcher.control.ButtonMatchers.isDefaultButton;
import static org.testfx.matcher.control.ComboBoxMatchers.hasSelectedItem;
import org.testfx.matcher.control.TableViewMatchers;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 *
 * @author andoni
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AlmacenTest extends ApplicationTest {

    private TableView<Almacen> almacenTableView;
    private TableColumn<Almacen, Integer> idAlmacenColumna;
    private TableColumn<Almacen, String> paisColumna;
    private TableColumn<Almacen, String> ciudadColumna;
    private TableColumn<Almacen, Date> fechaAdquisicionColumna;
    private TableColumn<Almacen, Integer> metrosCuadradosColumna;
    private Button añadirBtn;
    private Button borrarBtn;
    private ComboBox<String> combo;
    private TextField txtFiltro;
    private DatePicker txtFechaDesde;
    private DatePicker txtFechaHasta;
    private Stage stage;
    private ContextMenuManager contextMenuManager;

    @Override
    public void start(Stage stage) throws Exception {
        //new RetoCrudCliente().start(stage);
        Trabajador trabajador = new Trabajador();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto/crud/cliente/AlmacenFXML.fxml"));
        Parent root = loader.load();
        AlmacenFXMLControlador almacen = loader.getController();
        almacen.setStage(stage);
        almacen.setTrabajador(trabajador);
        almacen.initStage(root);

        // Inicializar la tabla y otros componentes
        almacenTableView = (TableView<Almacen>) lookup("#almacenTableView").query();
        combo = (ComboBox<String>) lookup("#combo").query();
        txtFechaDesde = (DatePicker) lookup("#txtFechaDesde").query();
        txtFechaHasta = (DatePicker) lookup("#txtFechaHasta").query();
    }

    @Test
    public void testA_inicializacionVentana() {
        // Comprobar el estado inicial de la ventana
        verifyThat("#combo", isVisible());
        verifyThat("#almacenTableView", isVisible());
        verifyThat("#añadirBtn", isVisible());
        verifyThat("#borrarBtn", isVisible());
        verifyThat("#btnImprimir", isVisible());
    }

    @Test
    public void testB_borrado() {
        //get row count
        int rowCount = almacenTableView.getItems().size();
        assertNotEquals("La tabla no tiene informacion, no se puede probar",
                rowCount, 0);
        //look for 1st row in table view and click it
        Node row = lookup(".table-row-cell").nth(0).query(); // Obtiene la primera fila
        Node cell = lookup(".table-cell").nth(0).query(); // Obtiene la primera celda de la fila
        assertNotNull("Cell is null: table has not that cell.", cell);
        clickOn(cell); // Hace clic en la primera celda
        verifyThat("#borrarBtn", isEnabled());//note that id is used instead of fx:id
        clickOn("#borrarBtn");
        clickOn(isDefaultButton());
        assertEquals("The row has not been deleted!!!",
                rowCount - 1, almacenTableView.getItems().size());
    }

    @Test
    public void testC_creado() {
        // Paso 1: Hacer clic en el botón "Añadir fila"
        clickOn("#añadirBtn");
        sleep(1000);
        // Paso 2: Verificar que hay una fila más en la tabla
        int rowCountBefore = almacenTableView.getItems().size();
        assertNotEquals("No se añadió ninguna fila.", rowCountBefore, 0);
    }

    @Test
    public void testD_modificado() {
        int rowCountBefore = almacenTableView.getItems().size();

        Node cell = lookup(".table-cell").nth(1).query(); // Obtiene la primera celda de la fila
        assertNotNull("Cell is null: table has not that cell.", cell);
        doubleClickOn(cell); // Hace clic en la primera celda
        write("Rusia");
        push(KeyCode.ENTER);

        cell = lookup(".table-cell").nth(2).query(); // Obtiene la primera celda de la fila
        assertNotNull("Cell is null: table has not that cell.", cell);
        doubleClickOn(cell); // Hace clic en la primera celda
        write("Moscu");
        push(KeyCode.ENTER);

        cell = lookup(".table-cell").nth(3).query(); // Obtiene la primera celda de la fila
        assertNotNull("Cell is null: table has not that cell.", cell);
        doubleClickOn(cell); // Hace clic en la primera celda
        write("400");
        push(KeyCode.ENTER);

        cell = lookup(".table-cell").nth(4).query(); // Obtiene la primera celda de la fila
        assertNotNull("Cell is null: table has not that cell.", cell);
        doubleClickOn(cell); // Hace clic en la primera celda
        for (int i = 0; i < 10; i++) {
            push(KeyCode.BACK_SPACE); // Presiona la tecla "Borrar"
        }
        write("24/05/2024");
        push(KeyCode.ENTER);
        cell = lookup(".table-cell").nth(1).query();
        clickOn(cell);
    }

    @Test
    public void testE_listado() {
        clickOn("#combo");
        push(KeyCode.DOWN);  // Navega hacia el primer elemento (presiona hacia abajo)
        push(KeyCode.ENTER);
        clickOn("#txtFiltro");
        write("7");
        clickOn("#searchButton");

        // Obtener el TableView y verificar el primer elemento
        TableView<Almacen> tableView = lookup("#tableView").query();
        ObservableList<Almacen> items = almacenTableView.getItems();
        assertFalse("La tabla no debería estar vacía", items.isEmpty());

        clickOn("#combo");
        push(KeyCode.DOWN);
        push(KeyCode.ENTER);
        clickOn("#txtFechaDesde");
        write("11/08/2011");
        clickOn("#txtFechaHasta");
        write("06/02/2025");
        clickOn("#searchButton");

        // Verificar que los resultados están en el rango de fechas
        assertFalse("La tabla debería tener resultados", almacenTableView.getItems().isEmpty());

        clickOn("#combo");
        push(KeyCode.DOWN);
        push(KeyCode.ENTER);
        clickOn("#txtFiltro");
        for (int i = 0; i < 10; i++) {
            push(KeyCode.BACK_SPACE);
        }
        write("Rusia");
        clickOn("#searchButton");

        // Verificar que los resultados contienen "Rusia"
        assertFalse("La tabla debería tener resultados para Rusia", almacenTableView.getItems().isEmpty());

        clickOn("#combo");
        push(KeyCode.DOWN);
        push(KeyCode.ENTER);
        clickOn("#txtFiltro");
        for (int i = 0; i < 10; i++) {
            push(KeyCode.BACK_SPACE);
        }
        write("Moscu");
        clickOn("#searchButton");

        // Verificar que los resultados contienen "Moscu"
        assertFalse("La tabla debería tener resultados para Moscu", almacenTableView.getItems().isEmpty());

        clickOn("#combo");
        push(KeyCode.DOWN);
        push(KeyCode.ENTER);
        clickOn("#txtFiltro");
        for (int i = 0; i < 10; i++) {
            push(KeyCode.BACK_SPACE);
        }
        write("300");
        clickOn("#searchButton");

        // Verificar que los resultados contienen "300"
        assertFalse("La tabla debería tener resultados para 300", almacenTableView.getItems().isEmpty());
    }
}
