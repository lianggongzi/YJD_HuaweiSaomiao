package com.example.administrator.myapplication.text.bean;

/**
 * Created by Administrator on 2018\9\5 0005.
 */

public class OutboundBean {

   private String xuhaoNumber;
    private  String time;
    private String barcodeNumber;
    private String model;
    private String quantity;
    private String customerName;
    private String brand;
    private String beizhu;
    private String phone;
    private String addres;

    public OutboundBean(String xuhaoNumber, String time, String barcodeNumber, String model, String quantity, String customerName, String brand, String beizhu, String phone, String addres) {
        this.xuhaoNumber = xuhaoNumber;
        this.time = time;
        this.barcodeNumber = barcodeNumber;
        this.model = model;
        this.quantity = quantity;
        this.customerName = customerName;
        this.brand = brand;
        this.beizhu = beizhu;
        this.phone = phone;
        this.addres = addres;
    }

    public String getXuhaoNumber() {
        return xuhaoNumber;
    }

    public void setXuhaoNumber(String xuhaoNumber) {
        this.xuhaoNumber = xuhaoNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBarcodeNumber() {
        return barcodeNumber;
    }

    public void setBarcodeNumber(String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    @Override
    public String toString() {
        return "OutboundBean{" +
                "xuhaoNumber='" + xuhaoNumber + '\'' +
                ", time='" + time + '\'' +
                ", barcodeNumber='" + barcodeNumber + '\'' +
                ", model='" + model + '\'' +
                ", quantity='" + quantity + '\'' +
                ", customerName='" + customerName + '\'' +
                ", brand='" + brand + '\'' +
                ", beizhu='" + beizhu + '\'' +
                ", phone='" + phone + '\'' +
                ", addres='" + addres + '\'' +
                '}';
    }
}
