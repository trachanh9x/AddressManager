/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am.controller;

import am.AddressManager;
import am.model.Address;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author VINH
 */
public class ListElementController implements Initializable {
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private Text diaChi;
    @FXML
    private Text phuong;
    @FXML
    private Text quan;
    @FXML
    private Text tinh;
    private Address address = new Address();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initChoiceBox();
        address = ListAddressController.getAddress();
        diaChi.setText(address.getDiaChi());
        phuong.setText(address.getPhuong());
        quan.setText(address.getQuan());
        tinh.setText(address.getTinh());
        
    }   
    private void initChoiceBox(){
        choiceBox.getItems().addAll("Xoa" , "Sua");
    }


}
