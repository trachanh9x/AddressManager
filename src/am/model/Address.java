/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am.model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author VINH
 */
public class Address {
    private SimpleStringProperty number = new SimpleStringProperty();
    private SimpleStringProperty ward = new SimpleStringProperty();
    private SimpleStringProperty district = new SimpleStringProperty();
    private SimpleStringProperty province = new SimpleStringProperty();

    public void setNumber(String address) {
        this.number.set(address);
    }

    public void setWard(String street) {
        this.ward.set(street);
    }

    public void setDistrict(String district) {
        this.district.set(district);
    }

    public void setProvince(String city) {
        this.province.set(city);
    }

    
    
    
    public String getNumber() {
        return number.get();
    }

    public String getWard() {
        return ward.get();
    }

    public String getDistrict() {
        return district.get();
    }

    public String getProvince() {
        return province.get();
    }
    
    
   
    
}
