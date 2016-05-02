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
    private Text number;
    @FXML
    private Text ward;
    @FXML
    private Text district;
    @FXML
    private Text province;
    private Address addr = new Address();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        /*address = ListAddressController.getAddress();
        address.setText(address.getDiaChi());
        street.setText(address.getPhuong());
        district.setText(address.getQuan());
        city.setText(address.getTinh());*/
        
    }
    public void initData( Address address){
        addr = address;
        number.setText(address.getNumber());
        ward.setText(address.getWard());
        district.setText(address.getDistrict());
        province.setText(address.getProvince());
    }
    public Address getData(){
        return addr;
    }
    


}
