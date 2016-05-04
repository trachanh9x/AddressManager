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
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Duc
 */
public class AddAddressController implements Initializable {

    @FXML
    private TextField provinceField;
    @FXML
    private TextField districtField;
    @FXML
    private TextField wardField;
    @FXML
    private TextField numberField;
    private Address addr = new Address();
    private ConnectToDatabase con;
    private ResultSet rs;

    //public enum Select{PROVINCE,DISTRICT,WARD;}
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initDataAdd(Address address) { // assigt data 
        addr = address;
        numberField.setText(address.getNumber());
        wardField.setText(address.getWard());
        districtField.setText(address.getDistrict());
        provinceField.setText(address.getProvince());
    }

    @FXML
    private void clickedProvinceField(MouseEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/am/view/SearchAddress.fxml"));
        Parent root = loader.load();
        SearchAddressController controller = loader.getController();
        controller.initDataSearch(addr, 1);
        Scene scene = new Scene(root);
        AddressManager.getStage().setScene(scene);
    }

    @FXML
    private void clickedDistrictField(MouseEvent event) throws IOException, SQLException {
        if (addr.getProvince() == null) { // dialog if field province empty
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Cảnh Báo");
            alert.setHeaderText(null);
            alert.setContentText("Điền đầy đủ thông tin phía trên");
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/am/view/SearchAddress.fxml"));
            Parent root = loader.load();
            SearchAddressController controller = loader.getController();
            controller.initDataSearch(addr, 2);
            Scene scene = new Scene(root);
            AddressManager.getStage().setScene(scene);
        }
    }

    @FXML
    private void clickedWardField(MouseEvent event) throws IOException, SQLException {
        if (addr.getProvince() == null || addr.getDistrict() == null) { //dialog if field district and province empty
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Cảnh Báo");
            alert.setHeaderText(null);
            alert.setContentText("Điền đầy đủ thông tin phía trên");
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/am/view/SearchAddress.fxml"));
            Parent root = loader.load();
            SearchAddressController controller = loader.getController();
            controller.initDataSearch(addr, 3);
            Scene scene = new Scene(root);
            AddressManager.getStage().setScene(scene);
        }
    }

    @FXML
    private void clickedNumberField(MouseEvent event) {
        if (addr.getProvince() == null || addr.getDistrict() == null || addr.getWard() == null) {// dialog if field province district ward is empty
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Cảnh Báo");
            alert.setHeaderText(null);
            alert.setContentText("Điền đầy đủ thông tin phía trên");
            alert.showAndWait();
        } else {
            addr.setNumber(numberField.getText());
        }

    }

    @FXML
    private void handleButtonAddAddress2(ActionEvent event) throws IOException, SQLException {
        addr.setNumber(numberField.getText());
        if (addr.getProvince() == null || addr.getDistrict() == null || addr.getWard() == null || addr.getNumber() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Bạn nhập thiếu thông tin!");
            alert.showAndWait();
        } else {
            String sql = "select district.provinceid as provinceid , ward.districtid as districtid , ward.wardid as wardid "
                    + "from district, ward "
                    + "where ward.districtid = district.districtid and ward.name = '" + addr.getWard() + "'";
            String provinceid;
            String districtid;
            String wardid;
            con = new ConnectToDatabase();
            rs = con.getRS(sql);
            rs.next();
            provinceid = rs.getString("provinceid");
            districtid = rs.getString("districtid");
            wardid = rs.getString("wardid");

            sql = "insert into address values(NULL, '"
                    + provinceid + "','"
                    + districtid + "','"
                    + wardid + "','"
                    + addr.getNumber() + "');";
            con.update(sql);
            con.close();

            Parent root = FXMLLoader.load(getClass().getResource("/am/view/ListAddress.fxml"));
            Scene scene = new Scene(root);
            AddressManager.getStage().setScene(scene);
        }

    }

}
