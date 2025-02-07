package controller;

import eus.tartangalh.crud.controladores.AlmacenFXMLControlador;
import eus.tartangalh.crud.controladores.MenuTrabajadorFXMLController;
import eus.tartangalh.crud.controladores.ProveedorFXMLController;
import eus.tartangalh.crud.controladores.RegistroTrabajadorFXMLControlador;
import eus.tartangalh.crud.entidades.Trabajador;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import reto.crud.cliente.RetoCrudCliente;

/**
 * Test de la pantalla de proveedor.
 */
public class ProveedorTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Trabajador trabajador = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto/crud/cliente/ProveedorFXML.fxml"));
            Parent root = loader.load();
            ProveedorFXMLController proveedor = loader.getController();
            proveedor.setStage(stage);
            proveedor.setTrabajador(trabajador);
            proveedor.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(MenuTrabajadorFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void test() {

        clickOn("#FieldIdProveedor");
        write("2");
        clickOn("#btnBuscar");
        sleep(1000);
        clickOn("#fechaFiltro");
        write("10/04/2019");
        clickOn("#btnBuscar");
        sleep(1000);
        clickOn("#FieldIdProveedor");
        eraseText(10);
        clickOn("#btnBuscar");
        sleep(1000);
        clickOn("#fechaFiltro");
        eraseText(10);
        clickOn("#btnBuscar");
        sleep(1000);

        doubleClickOn("#tableView");
        write("48015");
        type(KeyCode.ENTER);
        sleep(1000);
        doubleClickOn("#tableView");
        write("1231231312");
        type(KeyCode.ESCAPE);
        sleep(1000);

        clickOn("#btnCrearFila");
        sleep(1000);

        clickOn("#tableView");
        clickOn("#btnBorrar");

    }

}
