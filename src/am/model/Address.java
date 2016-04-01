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
    private SimpleStringProperty diaChi = new SimpleStringProperty();
    private SimpleStringProperty phuong = new SimpleStringProperty();
    private SimpleStringProperty quan = new SimpleStringProperty();
    private SimpleStringProperty tinh = new SimpleStringProperty();

    public void setDiaChi(String diaChi) {
        this.diaChi.set(diaChi);
    }

    public void setPhuong(String phuong) {
        this.phuong.set(phuong);
    }

    public void setQuan(String quan) {
        this.quan.set(quan);
    }

    public void setTinh(String tinh) {
        this.tinh.set(tinh);
    }

    
    
    
    public String getDiaChi() {
        return diaChi.get();
    }

    public String getPhuong() {
        return phuong.get();
    }

    public String getQuan() {
        return quan.get();
    }

    public String getTinh() {
        return tinh.get();
    }
    
    
   
    
}
