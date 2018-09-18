package com.example.administrator.myapplication.text.bean;

/**
 * Created by Administrator on 2018\9\18 0018.
 */

public class KehuEventXiugai {
    private String name;
    private  String phone;
    private  String address;
    private String beizhu;

    public KehuEventXiugai(String name, String phone, String address, String beizhu) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.beizhu = beizhu;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
