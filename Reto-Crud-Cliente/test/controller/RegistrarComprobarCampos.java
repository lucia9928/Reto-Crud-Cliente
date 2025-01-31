package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.framework.junit.ApplicationTest;
import reto.crud.cliente.RetoCrudCliente;

/**
 *
 * @author Markel
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegistrarComprobarCampos extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new RetoCrudCliente().start(stage);
    }

    /* @Test
    public void SinCampos() {
        clickOn("#btbRegistrarse");

        sleep(2000);
        verifyThat("Faltan campos por rellenar", isVisible());
        clickOn("Aceptar");

    }*/
   /* @Test
    public void EmailDniInvalido() {

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
    */
    @Test
    public void CamposValidos() {
         clickOn("#tfxDni");
        write("39333943R");
        clickOn("#tfxNombre");
        write("Markel");
        clickOn("#tfxApellido");
        write("arabio");
        clickOn("#tfxEmail");
        write("markel@gmail.com");
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
        clickOn("#dateFechaNcimiento");
        write("22/01/2001");

        clickOn("#btbRegistrarse");
       
    }
     
}
