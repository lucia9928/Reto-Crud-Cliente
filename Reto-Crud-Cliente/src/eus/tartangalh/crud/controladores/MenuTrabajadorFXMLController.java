/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.crud.controladores;

import eus.tartangalh.crud.entidades.Trabajador;
import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class MenuTrabajadorFXMLController {

    private Stage stage;

    private Trabajador trabajador;

    @FXML
    private Button btnRegistrarTrabajador;
    @FXML
    private Button btnProducto;
    @FXML
    private Button btnProveedor;
    @FXML
    private Button btnAlamcen;
    @FXML
    private Button btnCerrarSesion;

    /**
     * Initializes the controller class.
     *
     * @author markel
     *
     * @param root
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setTitle("Menu trabajador");
        stage.setScene(scene);
        stage.show();

        btnCerrarSesion.setOnAction(this::cerrarSesion);
        btnProveedor.setOnAction(this::tablaProveedor);
        btnAlamcen.setOnAction(this::tablaAlmacen);
        btnProducto.setOnAction(this::tablaProducto);
        btnRegistrarTrabajador.setOnAction(this::registroTrabajador);
    }

    private void cerrarSesion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto/crud/cliente/InicioSesionFXML.fxml"));
            Parent root = loader.load();
            InicioSesionFXMLControlador inicioSesion = loader.getController();
            inicioSesion.setStage(stage);
            inicioSesion.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(MenuTrabajadorFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    private void registroTrabajador(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto/crud/cliente/RegistroTrabajadorFXML.fxml"));
            Parent root = loader.load();
            RegistroTrabajadorFXMLControlador registroTrabajador = loader.getController();
            registroTrabajador.setStage(stage);
            registroTrabajador.setTrabajador(trabajador);
            registroTrabajador.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(MenuTrabajadorFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void tablaProveedor(ActionEvent event) {
        try {
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


    private void tablaAlmacen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto/crud/cliente/AlmacenFXML.fxml"));
            Parent root = loader.load();
            AlmacenFXMLControlador almacen = loader.getController();
            almacen.setStage(stage);
            almacen.setTrabajador(trabajador);
            almacen.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(MenuTrabajadorFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void tablaProducto(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto/crud/cliente/ProductoFarmaceuticoUI.fxml"));
            Parent root = loader.load();
            ProductoFarmaceuticoUIController producto = loader.getController();
            producto.setStage(stage);
            producto.setTrabajador(trabajador);
            producto.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(MenuTrabajadorFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
