package com.example.administrator.myapplication.text.bean;

/**
 * Created by Administrator on 2018\9\14 0014.
 */

public class TimeCustomerBean {
    String time;
    String name;
    String phone;

    public TimeCustomerBean(String time, String name, String phone) {
        this.time = time;
        this.name = name;
        this.phone = phone;
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

    @Override
    public String toString() {
        return "TimeCustomerBean{" +
                "time='" + time + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
