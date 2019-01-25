package com.example.administrator.myapplication.text.bean;

/**
 * Created by Administrator on 2018\9\14 0014.
 */

public class TimeCustomerBean {
    String time;
    String name;
    String phone;
    String beizhu;

    public TimeCustomerBean(String time, String name, String phone,String beizhu) {
        this.time = time;
        this.name = name;
        this.phone = phone;
        this.beizhu=beizhu;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    @Override
    public String toString() {
        return "TimeCustomerBean{" +
                "time='" + time + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", beizhu='" + beizhu + '\'' +
                '}';
    }
}
