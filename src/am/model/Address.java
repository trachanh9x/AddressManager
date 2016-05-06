/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author VINH
 * 
 * class save data of address
 */
public class Address {
    private SimpleIntegerProperty addressid = new SimpleIntegerProperty(); // address's id in database
    private SimpleStringProperty number = new SimpleStringProperty(); // number field save : home number, street ...
    private SimpleStringProperty ward = new SimpleStringProperty(); //  ward name
    private SimpleStringProperty district = new SimpleStringProperty(); // district name
    private SimpleStringProperty province = new SimpleStringProperty(); // province name
    
    // methods to insert information of address
    // set address's id
    public void setAddressid(int addressid) {
        this.addressid.set(addressid);
    }

    // set address's number
    public void setNumber(String address) {
        this.number.set(address);
    }
    
    // set address's ward
    public void setWard(String street) {
        this.ward.set(street);
    }
    // set address's district
    public void setDistrict(String district) {
        this.district.set(district);
    }

    // set address's province
    public void setProvince(String city) {
        this.province.set(city);
    }

    
    // methods to get information of address
    
    // get address's id
    public int getAddressid() {
        return addressid.get();
    }
    // get address's number
    public String getNumber() {
        return number.get();
    }
    // get address's ward
    public String getWard() {
        return ward.get();
    }
    // get address's district
    public String getDistrict() {
        return district.get();
    }
    // get address's province
    public String getProvince() {
        return province.get();
    }
    
    
   
    
}
