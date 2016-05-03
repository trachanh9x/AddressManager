/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am.controller;

import am.AddressManager;
import am.ConnectToDatabase;
import am.model.Address;
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
    private ConnectToDatabase con;
    private ResultSet rs;
    //private static Address address = new Address();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            addAddressData();
        } catch (SQLException ex) {
            Logger.getLogger(ListAddressController.class.getName()).log(Level.SEVERE, null, ex);
        }
        initListAddress();
    }    

    private void addAddressData() throws SQLException{
        String sql = "select address.addressid, province.name as province , district.name as district, ward.name as ward , address.number as number "
                        + "from address, province, district, ward "
                        + "where address.provinceid = province.provinceid and address.districtid = district.districtid and address.wardid = ward.wardid";
        addressData.clear();
        con = new ConnectToDatabase();
        rs = con.getRS(sql);
        while (rs.next()){
            Address address = new Address();
            address.setAddressid(rs.getInt("addressid"));
            address.setNumber(rs.getString("number"));
            address.setWard(rs.getString("ward"));
            address.setDistrict(rs.getString("district"));
            address.setProvince(rs.getString("province"));
            addressData.add(address);
        }
        con.close();
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
                    try {
                        String sql = "delete from address where addressid = "+ controller.getData().getAddressid();
                        con = new ConnectToDatabase();
                        con.update(sql);
                        con.close();
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(ListAddressController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    addressData.remove(controller.getData());
                    listAddress.getChildren().remove(pane);
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

