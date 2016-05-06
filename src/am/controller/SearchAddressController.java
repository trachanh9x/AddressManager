/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am.controller;

import am.AddressManager;
import am.ConnectToDatabase;
import am.controller.AddAddressController;
import am.model.Address;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Duc
 */
public class SearchAddressController implements Initializable {

    @FXML
    private TextField searchText;  // text fiel input name of address.
    @FXML
    private ListView<String> listPlace = new ListView<String>(); // list show address avaliable.

    private ObservableList<String> addressData = FXCollections.observableArrayList(); // observable list of address.
    private Address addre = new Address();// create model address.
    ConnectToDatabase con;
    ResultSet rs;
    private int count = 0;
    private int sel = 0;

    /**
     * Initializes the controller class.
     */
    public void initDataSearch(Address address, int flag) throws SQLException {
        addre = address;
        sel = flag;                 // sel = flag  sel use to chose what data will be input to address data.
        addAddressData();           // add data to addresslist.
        if (addressData.isEmpty()) {// if no have ward value. program will be call dialog and back to AddAddress.fxml and wardfield= none.
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Cảnh Báo");
                    alert.setHeaderText(null);
                    alert.setContentText("Huyện này không có phường!");
                    alert.showAndWait();
                    addre.setWard("None");
                    addre.setNumber(null);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/am/view/AddAddress.fxml")); // load scene AddAddress.
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(SearchAddressController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    AddAddressController controller = loader.getController();
                    controller.initDataAdd(addre); // send address data to AddAddressController.java.
                    Scene scene = new Scene(root);
                    AddressManager.getStage().setScene(scene);
                }
        else searchTable(addressData);   //search address by name.
    }

    public void searchTable(ObservableList<String> data) {
        FilteredList<String> filteredData = new FilteredList<>(data, s -> true); // create filtered list input data.

        listPlace.setCellFactory(ComboBoxListCell.forListView(data));
        listPlace.setItems(filteredData);                                         // add filtered list to list.
        searchText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(s -> {
                if (newValue == null && newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (s.toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        listPlace.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            // khi chon vao 1 item trong list view.

            switch (sel) {
                case 1: // click on province field will input province name and reset number,ward,district data.
                    addre.setProvince(newValue);// set province = newvalue.
                    addre.setDistrict(null); // set district =null , district field will empty.
                    addre.setWard(null);// set ward =null , ward field will empty.
                    addre.setNumber(null);// set number =null , number field will empty.
                    break;
                case 2: // click on district field will input district name and reset number  and ward data.
                    addre.setDistrict(newValue);// set district = newvalue, districtfield will display newvalue
                    addre.setWard(null);// set ward =null , ward field will empty.
                    addre.setNumber(null);// set number =null , number field will empty.
                    break;
                case 3: // click on ward field will input ward name and reset number.
                    addre.setWard(newValue);// set ward= newvalue, ward will display newvalue
                    addre.setNumber(null);// set number =null , number field will empty.
                    break;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/am/view/AddAddress.fxml")); // load scene AddAddress.
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(SearchAddressController.class.getName()).log(Level.SEVERE, null, ex);
            }
            AddAddressController controller = loader.getController();
            controller.initDataAdd(addre); // send address data to AddAddressController.java.
            Scene scene = new Scene(root);
            AddressManager.getStage().setScene(scene);
        });

    }

    private void addAddressData() throws SQLException {
        count = 0;
        con = new ConnectToDatabase(); // connect to Database.
        addressData.clear();// clear addressData.
        String sql;
        switch (sel) {  // sel is number of flag. with 1 as province, 2 as district, 3 as ward.
            case 1:
                sql = "select name from province order by name";// sql question
                rs = con.getRS(sql);// get rs have province name.
                while (rs.next()) {
                    addressData.add(rs.getString("name"));// add to data.
                    //count++;
                }
                sql = null;
                break;
            case 2:
                sql = "select district.name from district,province where province.provinceid=district.provinceid and province.name = ";
                sql = sql.concat("'");
                sql = sql.concat(addre.getProvince());
                sql = sql.concat("'");
                sql = sql.concat(" order by district.name");// sql question

                rs = con.getRS(sql); // send sql get rs have district name into province.

                while (rs.next()) {
                    addressData.add(rs.getString("name")); // add to data.
                    //count++;
                }
                sql = null;
                break;
            case 3:
                sql = "select ward.name from ward,district where district.districtid=ward.districtid and district.name = ";
                sql = sql.concat("'");
                sql = sql.concat(addre.getDistrict());
                sql = sql.concat("'");
                sql = sql.concat(" order by ward.name");// sql question

                rs = con.getRS(sql);// send sql get rs have ward name into district .

                while (rs.next()) {
                    addressData.add(rs.getString("name")); // add to data.
                    //count++;
                }
                
                sql = null;// reset sql question.
                break;
            default:
                break;
        }

        con.close();// close connect to database.
    }

    @FXML
    private void handleButtonX(ActionEvent event) {
        searchText.setText(""); // press button x then reset text field
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
