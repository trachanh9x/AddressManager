/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am.controller;

import am.AddressManager;
import am.ConnectToDatabase;
import am.model.Address;
import am.model.Place;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author VINH
 * 
 * controller of main menu interface of program
 * display all address inserted by user
 */
public class ListAddressController implements Initializable {
    @FXML
    private VBox listAddress; // Vbox to display address in database
    private ObservableList<Address> addressData = FXCollections.observableArrayList(); // list to save address inserted in database
    private ConnectToDatabase con; // to create connecttion to database
    private ResultSet rs; // to save result set from mysql

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            addAddressData(); // take addresses from database
        } catch (SQLException ex) {
            Logger.getLogger(ListAddressController.class.getName()).log(Level.SEVERE, null, ex);
        }
        initListAddress(); // display addresses
    }    
    // method take addresses inserted in database
    private void addAddressData() throws SQLException{
        // string to save sql commant to take addresses
        String sql = "select * "
                        + "from address, province, district, ward "
                        + "where address.provinceid = province.provinceid and address.districtid = district.districtid and address.wardid = ward.wardid";
        addressData.clear(); // reset list address
        con = new ConnectToDatabase(); // connect to database
        rs = con.getRS(sql); // get result set
        // take data from result set to addressData list
        while (rs.next()){
            Address address = new Address(); // creat new address variable
            Place ward = new Place();
            Place district = new Place();
            Place province = new Place();
            address.setAddressid(rs.getInt("address.addressid"));
            address.setNumber(rs.getString("address.number"));
            //get information of ward
            ward.setId(rs.getString("ward.wardid"));
            ward.setName(rs.getString("ward.name"));
            ward.setType(rs.getString("ward.type"));
            //get information of district
            district.setId(rs.getString("district.districtid"));
            district.setName(rs.getString("district.name"));
            district.setType(rs.getString("district.type"));
            //get information of province
            province.setId(rs.getString("province.provinceid"));
            province.setName(rs.getString("province.name"));
            province.setType(rs.getString("province.type"));
            
            address.setWard(ward);
            address.setDistrict(district);
            address.setProvince(province);
            addressData.add(address); // add address variable to list
        }
        con.close(); // close connection
    }
    
    // method to display address list
    private void initListAddress() {
        listAddress.getChildren().clear(); // clear Vbox
        for (int i = 0; i<addressData.size() ;i++){
            try {
                // load fxml to a new Grid pane
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/am/view/ListElement.fxml"));
                GridPane pane = loader.load();
                ListElementController controller = loader.getController(); // get Controller of fxml loaded
                controller.initData(addressData.get(i)); // insert address variable to pane
                Button bt = new Button("X"); // create button to delete address
                pane.getChildren().add(bt);
                pane.setConstraints(bt, 3, 1);
                // process when user clicked X Button
                bt.setOnAction(e -> {
                    try {
                        // sql commant to delete address
                        String sql = "delete from address where addressid = "+ controller.getData().getAddressid();
                        con = new ConnectToDatabase();
                        con.update(sql);
                        con.close();
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(ListAddressController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    addressData.remove(controller.getData()); // remove address variable from list
                    listAddress.getChildren().remove(pane); // remove address variable's pane from Vbox
                });
                listAddress.getChildren().add(pane); // add pane to VBox to display address variable
                
            } catch (IOException ex) {
                Logger.getLogger(ListAddressController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    // change scene to add address menu when user clicked button addAddress
    @FXML
    private void handleButtonAddAddress(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/am/view/AddAddress.fxml"));
        Scene scene = new Scene(root);
        AddressManager.getStage().setScene(scene);
        
    }
}

