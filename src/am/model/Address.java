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
    private Place ward = new Place(); //  ward name
    private Place district = new Place();
    private Place province = new Place();
    
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
    public void setWard(Place ward) {
        this.ward = ward;
    }
    
    //set address's district
    public void setDistrict(Place district) {
        this.district = district;
    }
    
    //set address's district
    public void setProvince(Place province) {
        this.province = province;
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

    public Place getWard() {
        return ward;
    }
    
    //get address's district
    public Place getDistrict() {
        return district;
    }
    
    //get address's province
    public Place getProvince() {
        return province;
    }
       
}
