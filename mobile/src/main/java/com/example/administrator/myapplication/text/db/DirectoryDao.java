package com.example.administrator.myapplication.text.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.myapplication.text.bean.OutboundBean;
import com.example.administrator.myapplication.text.bean.SerialBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\9\13 0013.
 * 总的数据库
 */

public class DirectoryDao {
    private DirectoryHelper helper;
    private SQLiteDatabase db;

    public DirectoryDao(Context context) {
        helper = new DirectoryHelper(context);
    }

    //写增删改查的方法
    public void init() {
        //打开数据库
        db = helper.getReadableDatabase();
    }

    //添加的方法
    public boolean insert(OutboundBean outboundBean) {
//        boolean isExist = isNewsExist();
//        Log.d("aaaaaaa",isExist+"------insert");
//        if (isExist) {
//            db.close();
//            return false; //返回添加失败
//        } else {
        init();
        Log.d("aaaaaaa", outboundBean.toString() + "-----------------dao");
        ContentValues contentValues = new ContentValues();
        contentValues.put("xuhaoNumber", outboundBean.getXuhaoNumber());
        contentValues.put("time", outboundBean.getTime());
        contentValues.put("barcodeNumber", outboundBean.getBarcodeNumber());
        contentValues.put("model", outboundBean.getModel());
        contentValues.put("quantity", outboundBean.getQuantity());
        contentValues.put("customerName", outboundBean.getCustomerName());
        contentValues.put("brand", outboundBean.getBrand());
        contentValues.put("beizhu", outboundBean.getBeizhu());
        contentValues.put("phone", outboundBean.getPhone());
        contentValues.put("addres", outboundBean.getAddres());
        db.insert("DirectoryBiao", null, contentValues);
        db.close();
        return true;//返回添加成功
//        }
    }


    //修改的方法
    public boolean xiugai(String name,String beizhu,String phone,String addres) {
        init();
        ContentValues contentValues = new ContentValues();
        contentValues.put("customerName",name);
        contentValues.put("beizhu", beizhu);
        contentValues.put("phone", phone);
        contentValues.put("addres",addres);
        db.update("DirectoryBiao",  contentValues,null,null);
        db.close();
        return true;//返回添加成功
//        }
    }


    //删除的方法
    public void delete(String time,String phone) {
        init();
        //根据newsURL进行数据删除
        db.delete("DirectoryBiao", "time = ? and phone = ? ", new String[]{time,phone});
        db.close();
    }

    //查询的方法-多条件查询
    public List<OutboundBean> select_time_name(String str,String sdg) {
        init();
        List<OutboundBean> list = new ArrayList<>();
        String table = "DirectoryBiao";
//        String[] columns = new String[]{"brand"};
        String selection = "time = ? and customerName = ?";
        String[] selectionArgs = new String[]{str,sdg};
        Cursor cursor = db.query(table, null, selection, selectionArgs, null, null, null);
           while (cursor.moveToNext()) {
               String xuhaoNumber = cursor.getString(cursor.getColumnIndex("xuhaoNumber"));
               String time = cursor.getString(cursor.getColumnIndex("time"));
               String barcodeNumber = cursor.getString(cursor.getColumnIndex("barcodeNumber"));
               String model = cursor.getString(cursor.getColumnIndex("model"));
               String quantity = cursor.getString(cursor.getColumnIndex("quantity"));
               String customerName = cursor.getString(cursor.getColumnIndex("customerName"));
               String brand = cursor.getString(cursor.getColumnIndex("brand"));
               String beizhu = cursor.getString(cursor.getColumnIndex("beizhu"));
               String phone = cursor.getString(cursor.getColumnIndex("phone"));
               String addres = cursor.getString(cursor.getColumnIndex("addres"));
               OutboundBean outboundBean = new OutboundBean(xuhaoNumber, time, barcodeNumber, model, quantity, customerName, brand, beizhu,phone,addres);
               list.add(outboundBean);

       }
        return list;
    }


    //判断是否存在
    public boolean isNewsExist() {
        init();
        Cursor cursor = db.query("DirectoryBiao", null, null, null, null, null, null);
//        Log.i("Tag",newsInfo.getUrl());
        if (cursor.moveToFirst()) {
            return true; // 已经存在该数据
        } else {
            return false;//不存在
        }
    }
}
