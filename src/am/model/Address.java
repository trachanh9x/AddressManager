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
    private SimpleStringProperty street = new SimpleStringProperty();
    private SimpleStringProperty district = new SimpleStringProperty();
    private SimpleStringProperty city = new SimpleStringProperty();

    public void setNumber(String address) {
        this.number.set(address);
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public void setDistrict(String district) {
        this.district.set(district);
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    
    
    
    public String getNumber() {
        return number.get();
    }

    public String getStreet() {
        return street.get();
    }

    public String getDistrict() {
        return district.get();
    }

    public String getCity() {
        return city.get();
    }
    
    
   
    
}
