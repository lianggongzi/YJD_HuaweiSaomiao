package com.example.administrator.myapplication.text.bean;

/**
 * Created by Administrator on 2018\9\13 0013.
 */

public class DirectoryBean {
  private   String time;
 private    String name;

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

    @Override
    public String toString() {
        return "DirectoryBean{" +
                "time='" + time + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
