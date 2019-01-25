package com.example.administrator.myapplication.text.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.myapplication.text.bean.TimeCustomerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\9\14 0014.
 * 时间目录下的客户资料
 */

public class TimeCustomerDao {
    private TimeCustomerOpenHelper helper;
    private SQLiteDatabase db;

    public TimeCustomerDao(Context context) {
        helper = new TimeCustomerOpenHelper(context);
    }

    //写增删改查的方法
    public void init() {
        //打开数据库
        db = helper.getReadableDatabase();
    }

    //添加的方法
    public boolean insert(TimeCustomerBean timeCustomerBean) {
        boolean isExist = isNewsExist(timeCustomerBean);
        if (isExist) {
            db.close();
            return false; //返回添加失败
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("time", timeCustomerBean.getTime());
            contentValues.put("name", timeCustomerBean.getName());
            contentValues.put("phone", timeCustomerBean.getPhone());
            contentValues.put("beizhu", timeCustomerBean.getBeizhu());
            db.insert("TimeCustomerBiao", null, contentValues);
            db.close();
            return true;//返回添加成功
        }
    }


    //修改的方法
    public boolean xiugai(String name,String phone) {
        init();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("phone", phone);
        db.update("TimeCustomerBiao",  contentValues,null,null);
        db.close();
        return true;//返回添加成功
//        }
    }


    //删除的方法
    public void delete(String phone) {
        init();
        //根据newsURL进行数据删除
        db.delete("TimeCustomerBiao", "phone = ? ", new  String[]{phone});
        db.close();
    }


    //查询的方法-根据传入时间查询所在的客户资料
    public List<TimeCustomerBean> selectTime(String str) {
        init();
        List<TimeCustomerBean> list = new ArrayList<>();
        Cursor cursor = db.query("TimeCustomerBiao", null, "time = ? ", new String[]{str}, null, null, null);
        while (cursor.moveToNext()) {
            String time = cursor.getString(cursor.getColumnIndex("time"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String beizhu = cursor.getString(cursor.getColumnIndex("beizhu"));
            TimeCustomerBean timeCustomerBean=new TimeCustomerBean(time,name,phone,beizhu);
            list.add(timeCustomerBean);
        }
        return list;
    }


    //判断是否存在
    public boolean isNewsExist(TimeCustomerBean bean) {
        init();
        Cursor cursor = db.query("TimeCustomerBiao", null, "phone = ? and time = ? ", new String[]{bean.getPhone(),bean.getTime()}, null, null, null);
//        Log.i("Tag",newsInfo.getUrl());
        if (cursor.moveToFirst()) {
            return true; // 已经存在该数据
        } else {
            return false;//不存在
        }
    }
}
