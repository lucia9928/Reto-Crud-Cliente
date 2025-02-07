package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import eus.tartangalh.crud.controladores.MenuTrabajadorFXMLController;
import eus.tartangalh.crud.controladores.RegistroTrabajadorFXMLControlador;
import eus.tartangalh.crud.entidades.Trabajador;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import reto.crud.cliente.RetoCrudCliente;

/**
 *
 * @author Markel
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegistrarTrabajadorComprobarCampos extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Trabajador trabajador=null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto/crud/cliente/RegistroTrabajadorFXML.fxml"));
            Parent root = loader.load();
            RegistroTrabajadorFXMLControlador registroTrabajador = loader.getController();
            registroTrabajador.setStage(stage);
            registroTrabajador.setTrabajador(trabajador);
            registroTrabajador.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(MenuTrabajadorFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        sleep(1000);
    }

    @Test
    public void test1_SinCampos() {
        

        clickOn("#btbRegistrarse");
        sleep(2000);
        verifyThat("Faltan campos por rellenar", isVisible());
        clickOn("Aceptar");
    }

    @Test
    public void test2_EmailDniInvalido() {
        clickOn("#tfxDni");
        write("111111");
        clickOn("#tfxNombre");
        write("Markel");
        clickOn("#tfxApellido");
        write("arabio");
        clickOn("#tfxEmail");
        write("111111");
        clickOn("#tfxContrasena");
        write("Abcd*1234");
        clickOn("#tfxConfirmarContrasena");
        write("Abcd*1234");
        clickOn("#tfxCalle");
        write("Orixe");
        clickOn("#tfxCodigoPostal");
        write("45614");
        clickOn("#tfxCiudad");
        write("Bilbao");

        clickOn("#btbRegistrarse");
        sleep(1000);
        verifyThat("Faltan campos por rellenar", isVisible());
        clickOn("Aceptar");
    }

    @Test
    public void test3_CamposValidos() {
        clickOn("#tfxDni");
        write("79003943R");
        clickOn("#tfxNombre");
        write("Markel");
        clickOn("#tfxApellido");
        write("arabio");
        clickOn("#tfxEmail");
        write("markelarabiourrutia@gmail.com");
        clickOn("#tfxContrasena");
        write("Abcd1234");
        clickOn("#tfxConfirmarContrasena");
        write("Abcd1234");
        clickOn("#tfxCalle");
        write("Orixe");
        clickOn("#tfxCodigoPostal");
        write("45614");
        clickOn("#tfxCiudad");
        write("Bilbao");
        clickOn("#dateFechaNcimiento");
        write("22/01/2001");

        clickOn("#btbRegistrarse");
    }
}
