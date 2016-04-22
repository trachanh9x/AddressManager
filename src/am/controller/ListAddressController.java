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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    //private static Address address = new Address();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        addAddressData();
        initListAddress();
    }    

    private void addAddressData(){
        addressData.clear();
        for (int i =0; i<8;i++){
            Address address = new Address();
            address.setDiaChi("ababkas" + i);
            address.setPhuong("aiaiaiui" +i);
            address.setQuan("iiiii" + i);
            address.setTinh("uuuu" + i);
            addressData.add(address);
        }
    }
    /*public static Address getAddress(){
        return address;
    }*/
    private void initListAddress() {
        listAddress.getChildren().clear();
        for (int i = 0; i<addressData.size() ;i++){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/am/view/ListElement.fxml"));
                GridPane pane = loader.load();
                ListElementController controller = loader.getController();
                controller.initData(addressData.get(i));
                Button bt = new Button("X");
                pane.getChildren().add(bt);
                pane.setConstraints(bt, 3, 1);
                bt.setOnAction(e -> {
                    listAddress.getChildren().remove(pane);
                    addressData.remove(controller.getData());
                        });
                listAddress.getChildren().add(pane);
                
            } catch (IOException ex) {
                Logger.getLogger(ListAddressController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    @FXML
    private void handleButtonAddAddress(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/am/view/AddAddress.fxml"));
        Scene scene = new Scene(root);
        AddressManager.getStage().setScene(scene);
        
    }
}

