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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Duc
 */
public class SearchAddressController implements Initializable {

    @FXML
    private TextField searchText;  // text fiel input name of address.
    @FXML
    private ListView<Place> listPlace = new ListView<Place>(); // list show address avaliable.

    private ObservableList<Place> addressData = FXCollections.observableArrayList(); // observable list of address.
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
                    
                    String sql = "select * from ward where name = 'None'"; // get none from sql.
                    Place ward = new Place();
                    con = new ConnectToDatabase();
                    rs = con.getRS(sql);
                    rs.next();
                    ward.setId(rs.getString("wardid")); // get id of ward.
                    ward.setName(rs.getString("name")); // get name of ward.
                    ward.setType(rs.getString("type")); // get type of ward.
                    con.close();
                    
                    addre.setWard(ward);
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
        else searchTable(addressData);              //search address by name.
    }

    public void searchTable(ObservableList<Place> data) {
        FilteredList<Place> filteredData = new FilteredList<>(data, s -> true); // create filtered list input data.
        //listPlace.setCellFactory(ComboBoxListCell.forListView(data));
        listPlace.setCellFactory(new Callback<ListView<Place>, ListCell<Place>> () {
            @Override
            public ListCell<Place> call(ListView<Place> param) {
                ListCell<Place> cell = new ListCell<Place>(){
                    @Override
                    protected void updateItem(Place place, boolean empty){
                        super.updateItem(place, empty);
                        if (empty){
                            setText(null);
                        }
                        else if (place != null){
                            setText(place.getName());
                        }
                    }
                };
                return cell;
            }
        });
        listPlace.setItems(filteredData);              // add filtered list to list.
        searchText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(s -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (s.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        listPlace.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Place> observable, Place oldValue, Place newValue) -> {
            // khi chon vao 1 item trong list view.

            switch (sel) {
                case 1: // click on province field will input province name and reset number,ward,district data.
                    addre.setProvince(newValue);    // set province = newvalue.
                    addre.setDistrict(null);        // set district =null , district field will empty.
                    addre.setWard(null);            // set ward =null , ward field will empty.
                    addre.setNumber(null);          // set number =null , number field will empty.
                    break;
                case 2: // click on district field will input district name and reset number  and ward data.
                    addre.setDistrict(newValue);    // set district = newvalue, districtfield will display newvalue
                    addre.setWard(null);            // set ward =null , ward field will empty.
                    addre.setNumber(null);          // set number =null , number field will empty.
                    break;
                case 3: // click on ward field will input ward name and reset number.
                    addre.setWard(newValue);        // set ward= newvalue, ward will display newvalue
                    addre.setNumber(null);          // set number =null , number field will empty.
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
            controller.initDataAdd(addre);                 // send address data to AddAddressController.java.
            Scene scene = new Scene(root);
            AddressManager.getStage().setScene(scene);
        });

    }

    private void addAddressData() throws SQLException {
        Place tmp;
        count = 0;
        con = new ConnectToDatabase();                     // connect to Database.
        addressData.clear();                               // clear addressData.
        String sql;
        switch (sel) {                                     // sel is number of flag. with 1 as province, 2 as district, 3 as ward.
            case 1:
                sql = "select * from province order by name";// sql question
                rs = con.getRS(sql);                        // get rs have province name.
                while (rs.next()) {
                    tmp = new Place();
                    tmp.setId(rs.getString("provinceid")); // get provinceid from data
                    tmp.setName(rs.getString("name"));     // get name from data
                    tmp.setType(rs.getString("type"));     // get type from data
                    addressData.add(tmp);                  // add element place to data.
                    //count++;
                }
                sql = null;
                break;
            case 2:
                sql = "select * from district where provinceid = ";
                sql = sql.concat("'");
                sql = sql.concat(addre.getProvince().getId());
                sql = sql.concat("'");
                sql = sql.concat(" order by district.name");// sql question

                rs = con.getRS(sql);                        // send sql get rs have district name into province.

                while (rs.next()) {
                    tmp = new Place();
                    tmp.setId(rs.getString("districtid"));  // get district id from data
                    tmp.setName(rs.getString("name"));      // get name of district from data.
                    tmp.setType(rs.getString("type"));      // get type from data.
                    addressData.add(tmp);                   // add to element place data.
                    //count++;
                }
                sql = null;
                break;
            case 3:
                sql = "select * from ward where districtid = ";
                sql = sql.concat("'");
                sql = sql.concat(addre.getDistrict().getId());
                sql = sql.concat("'");
                sql = sql.concat(" order by ward.name");// sql question

                rs = con.getRS(sql);                    // send sql get rs have ward name into district .

                while (rs.next()) {
                    tmp = new Place();
                    tmp.setId(rs.getString("wardid"));  // get wardid from data
                    tmp.setName(rs.getString("name"));  // get name of ward form data
                    tmp.setType(rs.getString("type"));  // get type from data.
                    addressData.add(tmp);               // add to data.
                }
                
                sql = null;                             // reset sql question.
                break;
            default:
                break;
        }

        con.close();                                    // close connect to database.
    }

    @FXML
    private void handleButtonX(ActionEvent event) {
        searchText.setText("");                         // press button x then reset text field
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
