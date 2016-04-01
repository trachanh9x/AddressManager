/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am.controller;

import am.AddressManager;
import am.model.Address;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author VINH
 */
public class ListAddressController implements Initializable {
    @FXML
    private VBox listAddress;
    private ObservableList<Address> addressData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        addAddressData();
        initListAddress();
    }    

    public void addAddressData(){
        for (int i =0; i<8;i++){
            Address address = new Address();
            address.setDiaChi("ababkas");
            address.setPhuong("aiaiaiui");
            address.setQuan("iiiii");
            address.setTinh("uuuu");
            addressData.add(address);
        }
    }
    public void initListAddress() {
        for (int i = 0; i<addressData.size() ;i++){
            try {
                AddressManager.address = addressData.get(i);
                FXMLLoader load = new FXMLLoader(getClass().getResource("/am/view/ListElement.fxml"));
                GridPane pane = load.load();
                listAddress.getChildren().add(pane);
                
            } catch (IOException ex) {
                Logger.getLogger(ListAddressController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

