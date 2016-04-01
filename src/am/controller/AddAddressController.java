/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am.controller;

import am.AddressManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author VINH
 */
public class AddAddressController implements Initializable {
    @FXML
    private TextField tinhField;
    @FXML
    private TextField quanField;
    @FXML
    private TextField PhuongField;
    @FXML
    private TextField diaChiField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clickedTinhField(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/am/view/SearchAddress.fxml"));
        Scene scene = new Scene(root);
        AddressManager.getStage().setScene(scene);
    }

    @FXML
    private void clickedQuanField(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/am/view/SearchAddress.fxml"));
        Scene scene = new Scene(root);
        AddressManager.getStage().setScene(scene);
    }

    @FXML
    private void clickedPhuongField(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/am/view/SearchAddress.fxml"));
        Scene scene = new Scene(root);
        AddressManager.getStage().setScene(scene);
    }

    @FXML
    private void handleButtonAddAddress2(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/am/view/AddAddress.fxml"));
        Scene scene = new Scene(root);
        AddressManager.getStage().setScene(scene);
    }
    
}
