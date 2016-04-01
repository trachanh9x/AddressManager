/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am;

import am.model.Address;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author VINH
 */
public class AddressManager extends Application {
    public static Stage primaryStage;
    public static Address address;
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.getIcons().add(new Image("/am/Home.png"));
        this.primaryStage.setTitle("Address Manager");
        try {
            FXMLLoader load = new FXMLLoader();
            load.setLocation(AddressManager.class.getResource("/am/view/ListAddress.fxml"));
            Scene scene = new Scene(load.load());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(AddressManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
