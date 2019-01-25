package com.example.administrator.myapplication.text.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.myapplication.text.bean.CustomerBean;
import com.example.administrator.myapplication.text.bean.XLSInfor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\9\5 0005.
 * 导入客户资料的数据库
 */

public class CustomerDao {
    private Customer_OpenHelper helper;
    private SQLiteDatabase db;

    public CustomerDao(Context context) {
        helper = new Customer_OpenHelper(context);
    }

    //写增删改查的方法
    public void init() {
        //打开数据库
        db = helper.getReadableDatabase();
    }

    //添加的方法
    public boolean insert(CustomerBean customerBean) {
        boolean isExist = isNewsExist(customerBean);
        if (isExist) {
            db.close();
            return false; //返回添加失败
        } else {

            ContentValues contentValues = new ContentValues();
            contentValues.put("name", customerBean.getName());
            contentValues.put("phone", customerBean.getPhone());
            contentValues.put("address", customerBean.getAddres());

            db.insert("KehuBiao", null, contentValues);
            db.close();
            return true;//返回添加成功
        }
    }


    //删除的方法
    public void delete(String data) {
        init();
        //根据newsURL进行数据删除
        db.delete("KehuBiao", "name = ? ", new String[]{data});
        db.close();
    }

    //查询的方法
    public List<CustomerBean> select(String str) {
        init();
        List<CustomerBean> list = new ArrayList<>();
        Cursor cursor = db.query("KehuBiao", null, "name = ?", new String[]{str}, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String address = cursor.getString(cursor.getColumnIndex("address"));

            CustomerBean customerBean = new CustomerBean();
            customerBean.setName(name);
            customerBean.setPhone(phone);
            customerBean.setAddres(address);
            list.add(customerBean);
        }
        return list;
    }

    //判断是否存在
    public boolean isNewsExist(CustomerBean customerBean) {
        init();
        Cursor cursor = db.query("KehuBiao", null, "name = ? and phone = ?", new String[]{customerBean.getName(), customerBean.getPhone()}, null, null, null);
//        Log.i("Tag",newsInfo.getUrl());
        if (cursor.moveToFirst()) {
            return true; // 已经存在该数据
        } else {
            return false;//不存在
        }
    }
}
