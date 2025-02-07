package controller;

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
 * Test de la pantalla de inicio de sesión.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InicioSesionComprobarCampos extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        // Cargar la ventana de Inicio de Sesión
        new RetoCrudCliente().start(stage);
        sleep(1000);  // Esperar para asegurarse de que la interfaz está completamente cargada
    }

    @Test
    public void test1_CamposVacios() {
        // Hacer clic en el botón "Iniciar sesión" con los campos vacíos
        clickOn("#btnIniciarSesion");
        sleep(1000);  // Esperar un poco antes de verificar

        // Verificar que aparece el mensaje de error por campos vacíos
        verifyThat("El campo DNI no puede estar vacío", isVisible());
        clickOn("Aceptar");  // Cerrar el alerta
    }

    @Test
    public void test2_CamposIncorrectos() {
        // Ingresar un DNI incorrecto y una contraseña incorrecta
        clickOn("#textDni");
        write("12345678A");  // DNI incorrecto
        clickOn("#pswContrasena");
        write("1234");  // Contraseña incorrecta

        // Hacer clic en "Iniciar sesión"
        clickOn("#btnIniciarSesion");
        sleep(1000);  // Esperar antes de verificar

        // Verificar que aparece el mensaje de error de usuario no encontrado
        verifyThat("Este usuario no existe", isVisible());
        clickOn("Aceptar");  // Cerrar el alerta
    }

    @Test
    public void test3_DniYContrasenaCorrectos() {
        // Ingresar un DNI y contraseña correctos (se usan valores ficticios para la prueba)
        clickOn("#textDni");
        write("dintrabajador");  // DNI correcto
        clickOn("#pswContrasena");
        write("abcd*1234");  // Contraseña correcta

        // Hacer clic en "Iniciar sesión"
        clickOn("#btnIniciarSesion");
        sleep(1000);  // Esperar antes de verificar

        // Verificar que se redirige a la pantalla de menú de trabajador (asumimos que existe un botón)
        verifyThat("#btnMenuTrabajador", isVisible());
    }

    @Test
    public void test4_MostrarContrasena() {
        // Ingresar una contraseña y luego hacer clic en el botón para mostrarla
        clickOn("#pswContrasena");
        write("abcd*1234");  // Escribir una contraseña

        // Hacer clic en el botón "Mostrar contraseña"
        clickOn("#btnMostrarContra");

        // Verificar que la contraseña es visible ahora
        verifyThat("#tfxContrasena", isVisible());
        verifyThat("#pswContrasena", isInvisible());
    }

    @Test
    public void test5_VolverARegistro() {
        // Hacer clic en "Registrarse" para ir a la pantalla de registro
        clickOn("#btnRegistrate");
        sleep(1000);  // Esperar que la pantalla de registro se cargue

        // Verificar que la pantalla de registro se muestra (suponiendo que tiene un botón específico para ello)
        verifyThat("#btnRegistrarNuevo", isVisible());
    }
}
