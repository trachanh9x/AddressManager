/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am.controller;

import am.model.Address;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author VINH
 * controller of each address pane display  in main menu
 */
public class ListElementController implements Initializable {
    @FXML
    private Text number; //text to display number
    @FXML
    private Text ward;  //text to display ward
    @FXML
    private Text district; //text to display district
    @FXML
    private Text province; //text to display province
    private Address addr = new Address(); // create new address variable
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }
    // init address Data receive from main menu
    public void initData( Address address){
        addr = address; // save address variable received
        // display address's fields
        number.setText(address.getNumber());
        ward.setText(address.getWard().getName());
        district.setText(address.getDistrict().getName());
        province.setText(address.getProvince().getName());
    }
    // return address variable
    public Address getData(){
        return addr;
    }
    


}
