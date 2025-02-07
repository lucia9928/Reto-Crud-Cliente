
import eus.tartangalh.crud.controladores.AlmacenFXMLControlador;
import eus.tartangalh.crud.controladores.RecetaMedicaFXMLController;
import eus.tartangalh.crud.entidades.Almacen;
import eus.tartangalh.crud.entidades.Cliente;
import eus.tartangalh.crud.entidades.RecetaMedica;
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
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.control.ButtonMatchers.isDefaultButton;
import static org.testfx.matcher.control.ComboBoxMatchers.hasSelectedItem;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RecetaTest extends ApplicationTest {

    private TableView<RecetaMedica> tableRecetas;
    private DatePicker desde;
    private DatePicker hasta;

    @Override
    public void start(Stage stage) throws Exception {
        Cliente cliente = new Cliente();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto/crud/cliente/RecetaMedicaFXML.fxml"));
        Parent root = loader.load();
        RecetaMedicaFXMLController receta = loader.getController();
        receta.setStage(stage);
        receta.setCliente(cliente);
        receta.initStage(root);

        // Inicializar la tabla y otros componentes
        tableRecetas = (TableView<RecetaMedica>) lookup("#tableRecetas").query();;
        desde = (DatePicker) lookup("#desde").query();
        hasta = (DatePicker) lookup("#hasta").query();
    }

    @Test
    public void testA_inicializacionVentana() {
        // Comprobar el estado inicial de la ventana
        verifyThat("#desde", isVisible());
        verifyThat("#hasta", isVisible());
        verifyThat("#tableRecetas", isVisible());
        verifyThat("#btnCrearReceta", isVisible());
        verifyThat("#btnDetalles", isVisible());
        verifyThat("#btnAtras", isVisible());
    }

    @Test
    public void testB_borrado() {
        int rowCount = tableRecetas.getItems().size();
        assertNotEquals("La tabla no tiene informacion, no se puede probar", rowCount, 0);

        Node row = lookup(".table-row-cell").nth(0).query(); // Obtiene la primera fila
        Node cell = lookup(".table-cell").nth(0).query(); // Obtiene la primera celda de la fila
        assertNotNull("Cell is null: table has not that cell.", cell);
        clickOn(cell); // Hace clic en la primera celda
        verifyThat("#btnDetalles", isEnabled()); //Boton de borrado
        clickOn("#btnDetalles");
        clickOn(isDefaultButton());
        assertEquals("The row has not been deleted!!!", rowCount - 1, tableRecetas.getItems().size());
    }

    @Test
    public void testC_creado() {
        clickOn("#btnCrearReceta");
        sleep(1000);
        int rowCountBefore = tableRecetas.getItems().size();
        assertNotEquals("No se añadió ninguna fila.", rowCountBefore, 0);
    }

    @Test
    public void testD_modificado() {
        int rowCountBefore = tableRecetas.getItems().size();

        Node cell = lookup(".table-cell").nth(3).query(); // Obtiene la primera celda de la fila
        assertNotNull("Cell is null: table has not that cell.", cell);
        doubleClickOn(cell); // Hace clic en la primera celda
        write("20/06/2013");
        push(KeyCode.ENTER);

        cell = lookup(".table-cell").nth(4).query(); // Obtiene la primera celda de la fila
        assertNotNull("Cell is null: table has not that cell.", cell);
        doubleClickOn(cell); // Hace clic en la primera celda
        write("Receta medica");
        push(KeyCode.ENTER);
    }

    @Test
    public void testE_listado() {
        clickOn("#desde");
        write("12/01/2005");
        clickOn("#hasta");
        write("07/02/2024");
        clickOn("#btnBuscar");
    }
}
