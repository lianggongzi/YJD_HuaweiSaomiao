package com.example.administrator.myapplication.text.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018\9\5 0005.
 */

public class CustomerBean implements Serializable {

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


    //必须以这种方式才能检测表
    public CustomerBean() {
        // TODO Auto-generated constructor stub
    }

    private String name;//姓名
    private String phone;//电话
    private String address;//地址

    public static void setSerialVersionUID(long serialVersionUID) {
        CustomerBean.serialVersionUID = serialVersionUID;
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

    public String getAddres() {
        return address;
    }

    public void setAddres(String addres) {
        this.address = addres;
    }

    @Override
    public String toString() {
        return "CustomerBean{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", addres='" + address + '\'' +
                '}';
    }
}
