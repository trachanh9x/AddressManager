/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am.model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author vinh
 */
public class Place {
    private SimpleStringProperty id = new SimpleStringProperty(); // Place's id
    private SimpleStringProperty name = new SimpleStringProperty(); // Place's name
    private SimpleStringProperty type = new SimpleStringProperty(); // Place's type

    // methods to insert information of Place
    //set Place's id
    public void setId(String id) {
        this.id.set(id);
    }
    //set Place's name
    public void setName(String name) {
        this.name.set(name);
    }
    //set Place's type
    public void setType(String type) {
        this.type.set(type);
    }

    
    // methods to get information of address
    //get Place's id
    public String getId() {
        return id.get();
    }
    //get Place's name
    public String getName() {
        return name.get();
    }
    //get Place's type
    public String getType() {
        return type.get();
    }
    
    
}
