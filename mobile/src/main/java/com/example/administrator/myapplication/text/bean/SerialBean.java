package com.example.administrator.myapplication.text.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018\8\31 0031.
 */

public class SerialBean implements Serializable{

    private String serialNumber;//条形编码

    String serialNumberTou;//条形编码的头
    int serialNumberTouMin;//条形编码最小值
    int serialNumberTouMax;//条形编码最大值

    private String number;//数量
    private String model;//型号n
    private String brand;//品牌

    public SerialBean() {
    }

    public String getSerialNumberTou() {
        return serialNumberTou;
    }

    public void setSerialNumberTou(String serialNumberTou) {
        this.serialNumberTou = serialNumberTou;
    }

    public int getSerialNumberTouMin() {
        return serialNumberTouMin;
    }

    public void setSerialNumberTouMin(int serialNumberTouMin) {
        this.serialNumberTouMin = serialNumberTouMin;
    }

    public int getSerialNumberTouMax() {
        return serialNumberTouMax;
    }

    public void setSerialNumberTouMax(int serialNumberTouMax) {
        this.serialNumberTouMax = serialNumberTouMax;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "SerialBean{" +
                "serialNumber='" + serialNumber + '\'' +
                ", serialNumberTou='" + serialNumberTou + '\'' +
                ", serialNumberTouMin='" + serialNumberTouMin + '\'' +
                ", serialNumberTouMax='" + serialNumberTouMax + '\'' +
                ", number='" + number + '\'' +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}
