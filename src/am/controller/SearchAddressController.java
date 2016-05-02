/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am.controller;

import am.ConnectToDatabase;
import am.model.Address;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Duc
 */
public class SearchAddressController implements Initializable {

    @FXML
    private TextField searchText;  // text fiel input name of address
    @FXML
    private ListView<String> listPlace = new ListView<String>(); // list show address avaliable
    
    private ObservableList<String> addressData = FXCollections.observableArrayList(); // observable list of address
    private ConnectToDatabase con;
    private ResultSet rs;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            addAddressData();  // add data to addresslist.
            searchTable(addressData);//search address by name.
        } catch (SQLException ex) {
            Logger.getLogger(SearchAddressController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void searchTable(ObservableList<String> data) {
        FilteredList<String> filteredData = new FilteredList<>(data, s -> true); // create filtered list input data.
        //TextField filterInput = new TextField();
        listPlace.setCellFactory(ComboBoxListCell.forListView(data));
        listPlace.setItems(filteredData);  // add filtered list to list.
        searchText.textProperty().addListener(obs -> {
            String filter = searchText.getText();
            if (filter == null || filter.length() == 0) {
                filteredData.setPredicate(s -> true);
            } else {
                filteredData.setPredicate(s -> s.contains(filter));
            }
        });
        listPlace.getSelectionModel().getSelectedItem();

    }

    private void addAddressData() throws SQLException {
        /*addressData.add("2");
        addressData.add("3");
        addressData.add("4");
        addressData.add("5");
        addressData.add("6");
        addressData.add("7");
        addressData.add("8");
        addressData.add("9");
        addressData.add("10");
        addressData.add("11");*/
        String sql = "Select name from province order by name";
        con = new ConnectToDatabase();
        rs = con.getRS(sql);
        
        while(rs.next()){
            String address;
            address = rs.getString("name");
            addressData.add(address);
            
        }
        con.close();

    }

    @FXML
    private void handleButtonX(ActionEvent event) {
    }

}
