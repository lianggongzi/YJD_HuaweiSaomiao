package com.example.administrator.myapplication.text.bean;

import java.io.Serializable;

public class XLSInfor implements Serializable {

    /**
     *
     */
    private static long serialVersionUID = 1L;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    private String riqi;
    private String serialNumber;//条形编码
    private String number;//数量
    private String model;//型号n
    private String brand;//品牌

    //必须以这种方式才能检测表
    public XLSInfor() {
        // TODO Auto-generated constructor stub

    }

    public static void setSerialVersionUID(long serialVersionUID) {
        XLSInfor.serialVersionUID = serialVersionUID;
    }

    public String getRiqi() {
        return riqi;
    }

    public void setRiqi(String riqi) {
        this.riqi = riqi;
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
        return "XLSInfor{" +
                "riqi='" + riqi + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", number='" + number + '\'' +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}

