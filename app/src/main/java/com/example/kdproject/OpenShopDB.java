package com.example.kdproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class OpenShopDB extends SQLiteOpenHelper {

    private static final int databaseversion=1; //資料庫版本
    private static final String databaseneme = "productdata2.db";//資料庫名稱
    private Context mycontext;
    private String strcreate="CREATE TABLE SHOPCAR (_id INTEGER PRIMARY KEY," +
            "name TEXT, type TEXT , price INTEGER, count INTEGER )";

    public OpenShopDB(Context context) {
        super(context, databaseneme, null, databaseversion);

        mycontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //開始創造資料表
        db.execSQL(strcreate);
        Toast.makeText(mycontext, "SC資料庫建立完成", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        //判斷資料庫存在,或更新資料
        Toast.makeText(mycontext, "資料庫已開啟", Toast.LENGTH_LONG).show();
    }

    public void insertData(SQLiteDatabase db, String name, String type ,
                                  String price, String count){

        try{

            String sql="INSERT INTO SHOPCAR (name, type, price,count ) " +
                    "VALUES ( '"+name+"' , '"+type+"',"+price+","
                    + count+")";

            db.execSQL(sql);
            Toast.makeText(mycontext, "已加入購物車", Toast.LENGTH_LONG).show();

        }catch(Exception e){
            Toast.makeText(mycontext, e.toString() , Toast.LENGTH_LONG).show();
        }

    }

    public Cursor queryData(SQLiteDatabase db){

        String sql="SELECT * FROM SHOPCAR ";
        Cursor c=db.rawQuery(sql,null);
        return c;

    }
}
