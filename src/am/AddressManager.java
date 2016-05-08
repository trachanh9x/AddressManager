/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author VINH
 * main class of project
 */
public class AddressManager extends Application {
    private static Stage primaryStage;
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage; // set primary stage of program
        this.primaryStage.getIcons().add(new Image("/am/Home.png")); // set icon of stage
        this.primaryStage.setTitle("Address Manager"); // set title of stage
        initRootLayout();
    }
    // return primaryStage
    public static Stage getStage() {
        return primaryStage;
    }
    // load interface layout of program from fxml file when start
    // and show it
    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AddressManager.class.getResource("/am/view/ListAddress.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
