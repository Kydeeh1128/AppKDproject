package com.example.kdproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class OpenDB extends SQLiteOpenHelper {

    private static final int databaseversion=1; //資料庫版本
    private static final String databaseneme = "productdata.db";//資料庫名稱
    private Context mycontext;
    private String strcreate="CREATE TABLE PRODUCT(_id INTEGER PRIMARY KEY," +
            "name TEXT, type TEXT , price INTEGER, count INTEGER ," +
            " simg TEXT , limg TEXT, other TEXT)";


    public OpenDB(Context context) {
        super(context, databaseneme, null, databaseversion);

        mycontext = context;
        //Context context :正在執行中類別元件本文
        //String name:資料庫名稱
        //CursorFactory: 指標操作棄,一般為NULL
        //version: 資料庫版本
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    //開始創造資料表
        db.execSQL(strcreate);
        Toast.makeText(mycontext, "資料庫建立完成", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        //判斷資料庫存在,或更新資料
        Toast.makeText(mycontext, "資料庫已開啟", Toast.LENGTH_LONG).show();
    }
    // execSQL()方法可以執行insert、delete、update和CREATE TABLE之類有更改行為的SQL語句；
    // rawQuery()方法用於執行select語句。

    public void onCreateShopCar(SQLiteDatabase db) {
        String sql ="CREATE TABLE SHOPCAR(_id INTEGER PRIMARY KEY," +
                "name TEXT, type TEXT , price INTEGER, count INTEGER )";
        db.execSQL(sql);
        Toast.makeText(mycontext, "購物車建立完成", Toast.LENGTH_LONG).show();
    }



    public Cursor queryData(SQLiteDatabase db, String id){

        String sql="SELECT * FROM PRODUCT WHERE _id="+id;
        Cursor c=db.rawQuery(sql,null);
        return c;

    }

    public Cursor queryData(SQLiteDatabase db){

        String sql="SELECT * FROM PRODUCT ";
        Cursor c=db.rawQuery(sql,null);
        return c;

    }

    public Cursor queryDataList(SQLiteDatabase db, String name){

        String sql="SELECT name,type,price,simg,limg FROM PRODUCT WHERE name= '"+name+"'";
        Cursor c=db.rawQuery(sql,null);
        return c;

    }

    public void insertData(SQLiteDatabase db, String name, String type ,
                           String price, String count, String simg, String limg, String other){

        try{

            String sql="INSERT INTO PRODUCT (name, type, price,count ,simg ,limg ,other) " +
                    "VALUES ( '"+name+"' , '"+type+"',"+price+","
                    + count+", '"+simg+"' ,'"+limg+"', '"+other+"')";

            db.execSQL(sql);
            Toast.makeText(mycontext, "已新增資料", Toast.LENGTH_LONG).show();

        }catch(Exception e){
            Toast.makeText(mycontext, e.toString() , Toast.LENGTH_LONG).show();
        }

    }

    public void updateData(SQLiteDatabase db, String id, String name, String type ,
                           String price, String count, String simg, String limg, String other){

        try{

            String sql="UPDATE PRODUCT SET "
                    +"name= '"+name+"',type= '"+type+"', price="+price+" , count="+count+"," +
                    " simg = '"+simg+"', limg = '"+limg+"', other= '"+other+"' " +
                    "WHERE _id="+id+";";
            db.execSQL(sql);

            Toast.makeText(mycontext, "已修改資料", Toast.LENGTH_LONG).show();

        }catch(Exception e){
            Toast.makeText(mycontext, e.toString() , Toast.LENGTH_LONG).show();
        }
    }




}
