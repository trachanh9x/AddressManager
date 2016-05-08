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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Duc
 */
public class AddAddressController implements Initializable {

    @FXML
    private TextField provinceField;// field to input Province name.
    @FXML
    private TextField districtField;// field to input dstrict name.
    @FXML
    private TextField wardField;// field to input ward name.
    @FXML
    private TextField numberField;// field to in put number name.
    private Address addr = new Address();// create model address.
    private ConnectToDatabase con;
    private ResultSet rs;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        numberField.setText(null);
        wardField.setText(null);
        districtField.setText(null);
        provinceField.setText(null);
    }

    public void initDataAdd(Address address) { // assigt data to AddAddress.
        addr = address;
        numberField.setText(address.getNumber());// show Number of Address from address by numberfield.
        if (address.getWard()!= null)
            wardField.setText(address.getWard().getName());// show Ward of Address from address by wardfield.
        else 
            wardField.setText(null);
        if (address.getDistrict() != null)
            districtField.setText(address.getDistrict().getName());// show District of Address from address by districtfield.
        else
            districtField.setText(null);
        if (address.getProvince()!= null)
            provinceField.setText(address.getProvince().getName());// show Province of Address from address by provincefield.
        else
            provinceField.setText(null);
    }

    @FXML
    private void clickedProvinceField(MouseEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/am/view/SearchAddress.fxml"));// load scene SearchAddress.
        Parent root = loader.load();
        SearchAddressController controller = loader.getController();
        controller.initDataSearch(addr, 1);
        Scene scene = new Scene(root);
        AddressManager.getStage().setScene(scene);
    }

    @FXML
    private void clickedDistrictField(MouseEvent event) throws IOException, SQLException {
        if (addr.getProvince() == null) { // dialog if field province is empty.
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Cảnh Báo");
            alert.setHeaderText(null);
            alert.setContentText("Điền đầy đủ thông tin phía trên");
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/am/view/SearchAddress.fxml"));// load scene SearchAddress.
            Parent root = loader.load();
            SearchAddressController controller = loader.getController();
            controller.initDataSearch(addr, 2);
            Scene scene = new Scene(root);
            AddressManager.getStage().setScene(scene);
        }
    }

    @FXML
    private void clickedWardField(MouseEvent event) throws IOException, SQLException {
        if (addr.getProvince() == null || addr.getDistrict() == null) { //dialog if field district and province is empty.
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Cảnh Báo");
            alert.setHeaderText(null);
            alert.setContentText("Điền đầy đủ thông tin phía trên");
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/am/view/SearchAddress.fxml"));// load scene SearchAddress.
            Parent root = loader.load();
            SearchAddressController controller = loader.getController();
            Scene scene = new Scene(root);
            AddressManager.getStage().setScene(scene);
            controller.initDataSearch(addr, 3);
        }
    }

    @FXML
    private void clickedNumberField(MouseEvent event) {
        if (addr.getProvince() == null || addr.getDistrict() == null || addr.getWard() == null) {// dialog if field province district ward is empty.
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Cảnh Báo");
            alert.setHeaderText(null);
            alert.setContentText("Điền đầy đủ thông tin phía trên");
            alert.showAndWait();
        } else {
            addr.setNumber(numberField.getText());//if province/district/ward have value-> input number.
        }

    }
// process when user clicked button to add new address
    @FXML
    private void handleButtonAddAddress2(ActionEvent event) throws IOException, SQLException {
        addr.setNumber(numberField.getText()); // get value of address's number
        //check if address has null field
        if (addr.getProvince() == null || addr.getDistrict() == null || addr.getWard() == null || addr.getNumber() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Bạn nhập thiếu thông tin!");
            alert.showAndWait();
        } else {
            
            con = new ConnectToDatabase(); // connect to database
            // check if database had this address
            String sql = "select * from address "
                    + "where provinceid = '"+ addr.getProvince().getId() + "' "
                    + "and districtid = '" + addr.getDistrict().getId() + "'"
                    + "and wardid = '" + addr.getWard().getId() + "'"
                    + "and number = '"+ addr.getNumber() + "';";
            rs = con.getRS(sql);
            if (rs.next()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Địa chỉ đã tồn tại!");
                alert.showAndWait();
            }
            // if this is a new address -> insert it to database
            else{
               // sql commant to insert new address to database
                sql = "insert into address values(NULL, '"
                        + addr.getProvince().getId() + "','"
                        + addr.getDistrict().getId() + "','"
                        + addr.getWard().getId() + "','"
                        + addr.getNumber() + "');";
                con.update(sql);
                

                //turn back to List Address scene
                Parent root = FXMLLoader.load(getClass().getResource("/am/view/ListAddress.fxml"));
                Scene scene = new Scene(root);
                AddressManager.getStage().setScene(scene); 
            }
            con.close(); // close connection to database
            
        }

    }

}
