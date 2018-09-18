package com.example.administrator.myapplication.text.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2018\9\5 0005.
 */

public class Customer_OpenHelper extends SQLiteOpenHelper {
    private static final String CREATE_NEWS = "CREATE TABLE KehuBiao (name text,phone text," +
            "address text)";
    public static final String DB_NAME = "kehuexcel.db";

    public Customer_OpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据表（newsInfo 中有什么字段，数据库中就要有什么字段）
        db.execSQL(CREATE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
