/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am.controller;

import am.AddressManager;
import am.controller.AddAddressController;
import am.model.Address;
import java.io.IOException;
import java.net.URL;
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
    private TextField searchText;  // text fiel input name of address
    @FXML
    private ListView<String> listPlace = new ListView<String>(); // list show address avaliable

    private ObservableList<String> addressData = FXCollections.observableArrayList(); // observable list of address
    private Address addre = new Address();
    // private enum Select{PROVINCE,DISTRICT,WARD;};
    // Select sel;
    int sel = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addAddressData();           // add data to addresslist.
        searchTable(addressData);   //search address by name.
    }

    public void initDataSearch(Address address, int flag) {
        addre = address;
        sel = flag;
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
            // Your action here

            switch (sel) {
                case 1:
                    addre.setProvince(newValue);
                    addre.setDistrict(null);
                    addre.setWard(null);
                    addre.setNumber(null);
                    break;
                case 2:
                    addre.setDistrict(newValue);
                    addre.setWard(null);
                    addre.setNumber(null);
                    break;
                case 3:
                    addre.setWard(newValue);
                    addre.setNumber(null);
                    break;
                default:
                    break;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/am/view/AddAddress.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(SearchAddressController.class.getName()).log(Level.SEVERE, null, ex);
            }
            AddAddressController controller = loader.getController();
            controller.initDataAdd(addre);
            Scene scene = new Scene(root);
            AddressManager.getStage().setScene(scene);
        });

    }

    private void addAddressData() {

        addressData.add("2");
        addressData.add("3");
        addressData.add("4");
        addressData.add("5");
        addressData.add("6");
        addressData.add("7");
        addressData.add("8");
        addressData.add("9");
        addressData.add("10");
        addressData.add("11");

    }

    @FXML
    private void handleButtonX(ActionEvent event) {
        searchText.setText("");
    }

}
