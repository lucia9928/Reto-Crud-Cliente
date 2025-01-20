/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto.crud.cliente;

import eus.tartangalh.crud.controladores.RecetaMedicaFXMLController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author 2dam
 */
public class RetoCrudCliente extends javafx.application.Application {
    private static Stage primaryStage;
   
    @Override
    public void start(Stage stage) throws IOException {
    
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RecetaMedicaFXML.fxml"));
         // Carga el archivo FXML para la pantalla de inicio de sesión
                   
             Parent root = loader.load();
             RecetaMedicaFXMLController receta = (RecetaMedicaFXMLController) loader.getController();
              receta.setStage(stage);
        receta.initStage(root);
        /**receta.initStage(root);
            root.setId("pane"); // Establece el ID del contenedor raíz
            Scene scene = new Scene(root); // Crea la escena con el contenido cargado
            primaryStage.setScene(scene); // Establece la escena en la ventana principal
            primaryStage.show(); // Muestra la ventana principal
            // Configura la ventana principal
            primaryStage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(RetoCrudCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
    }

    /**
     * @param args the command line arguments
     */
     public static void main(String[] args) {
        launch(args);
     }
}
