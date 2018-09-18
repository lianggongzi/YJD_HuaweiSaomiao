package com.example.administrator.myapplication.text.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.myapplication.text.bean.SerialBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\9\10 0010.
 * 导入资料的数据库
 */

public class SerialDao {
    private MyOpenHelper helper;
    private SQLiteDatabase db;

    public SerialDao(Context context) {
        helper = new MyOpenHelper(context);
    }

    //写增删改查的方法
    public void init() {
        //打开数据库
        db = helper.getReadableDatabase();
    }

    //添加的方法
    public boolean insert(SerialBean serialBean) {
        boolean isExist = isNewsExist(serialBean);

        if (isExist) {
            db.close();
            return false; //返回添加失败
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("serialNumber", serialBean.getSerialNumber());
            contentValues.put("serialNumberTou", serialBean.getSerialNumberTou());
            contentValues.put("serialNumberTouMin", serialBean.getSerialNumberTouMin());
            contentValues.put("serialNumberTouMax", serialBean.getSerialNumberTouMax());
            contentValues.put("number", serialBean.getNumber());
            contentValues.put("model", serialBean.getModel());
            contentValues.put("brand", serialBean.getBrand());
            db.insert("SerialNumberBiao1", null, contentValues);
            db.close();
            return true;//返回添加成功
        }
    }


    //删除的方法
    public void delete(String data) {
        init();
        //根据newsURL进行数据删除
        db.delete("SerialNumberBiao1", "serialNumber = ? ", new String[]{data});
        db.close();
    }
//    //查询的方法
//    public List<XLSInfor> select(String str){
//        init();
//        List<XLSInfor> list = new ArrayList<>();
//        Cursor cursor = db.query("SerialNumberBiao", null, "serialNumber = ?", new String[]{str}, null, null, null);
//        while (cursor.moveToNext()) {
//            String serialNumber = cursor.getString(cursor.getColumnIndex("serialNumber"));
//            String number = cursor.getString(cursor.getColumnIndex("number"));
//            String model = cursor.getString(cursor.getColumnIndex("model"));
//            String brand = cursor.getString(cursor.getColumnIndex("brand"));
//            XLSInfor newsInfo = new XLSInfor();
//            newsInfo.setXuhaoNumber(serialNumber);
//            newsInfo.setNumber(number);
//            newsInfo.setModel(model);
//            newsInfo.setBrand(brand);
//            list.add(newsInfo);
//        }
//        return  list;
//    }

    //查询的方法-多条件查询
    public List<SerialBean> select(String str) {
        init();
        List<SerialBean> list = new ArrayList<>();
        Cursor cursor = db.query("SerialNumberBiao1", null, "serialNumberTou = ?", new String[]{str}, null, null, null);
//        Cursor cursor = db.query("SerialNumberBiao1",null,"model = ? and brand = ?", new String[]{str,s},null,null,null);
        while (cursor.moveToNext()) {
            String serialNumber = cursor.getString(cursor.getColumnIndex("serialNumber"));
            String serialNumberTou = cursor.getString(cursor.getColumnIndex("serialNumberTou"));
            String serialNumberTouMin = cursor.getString(cursor.getColumnIndex("serialNumberTouMin"));
            String serialNumberTouMax = cursor.getString(cursor.getColumnIndex("serialNumberTouMax"));
            String number = cursor.getString(cursor.getColumnIndex("number"));
            String model = cursor.getString(cursor.getColumnIndex("model"));
            String brand = cursor.getString(cursor.getColumnIndex("brand"));
            SerialBean serialBean = new SerialBean();
            serialBean.setSerialNumber(serialNumber);
            serialBean.setSerialNumberTou(serialNumberTou);
            serialBean.setSerialNumberTouMin(Integer.parseInt(serialNumberTouMin));
            serialBean.setSerialNumberTouMax(Integer.parseInt(serialNumberTouMax));
            serialBean.setNumber(number);
            serialBean.setModel(model);
            serialBean.setBrand(brand);
            list.add(serialBean);
        }
        return list;
    }


    //判断是否存在
    public boolean isNewsExist(SerialBean serialBean) {
        init();
        Cursor cursor = db.query("SerialNumberBiao1", null, "serialNumber = ?", new String[]{serialBean.getSerialNumber()}, null, null, null);
//        Log.i("Tag",newsInfo.getUrl());
        if (cursor.moveToFirst()) {
            return true; // 已经存在该数据
        } else {
            return false;//不存在
        }
    }
}
