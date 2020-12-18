package com.example.kdproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ItemInfoActivity extends AppCompatActivity {

    Button btmem,bthome,btcar;
    Button bt;
    ImageView iv;
    TextView tv,tv2;
    OpenShopDB db;

    //public static ArrayList<ShopCar> carlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);

        btmem = (Button)findViewById(R.id.button4);
        bthome = (Button)findViewById(R.id.button2);
        btcar = (Button)findViewById(R.id.button3);
        bt = (Button)findViewById(R.id.button9);
        tv = (TextView)findViewById(R.id.textView);
        tv2 = (TextView)findViewById(R.id.textView12);
        iv = (ImageView)findViewById(R.id.imageView);

        iv.setImageResource(MainActivity.SELECTPIMGM);


        String str="品牌 "+MainActivity.SELECTNAME+"\n"+"型號 : "+MainActivity.SELECTTYPE;
        String strp="$ "+MainActivity.SELECTPRICE;
        tv.setText(str);
        tv2.setText(strp);

        bt.setOnClickListener(btlistenerlist);
        btmem.setOnClickListener(btlistenerlist);
        btcar.setOnClickListener(btlistenerlist);
        bthome.setOnClickListener(btlistenerlist);
    }

//    private void addshopcar(){
//            try{
//                OpenShopDB od = new OpenShopDB(ItemInfoActivity.this);
//                SQLiteDatabase sd = od.getWritableDatabase();
//                String name = MainActivity.SELECTNAME;
//                String type = MainActivity.SELECTTYPE;
//                int price = MainActivity.SELECTPRICE;
//
//                od.insertData(sd,name,type, String.valueOf(price),"10");
//
//                sd.close();
//                od.close();
//            }catch(Exception e){
//                AlertDialog.Builder aler1 = new AlertDialog.Builder(ItemInfoActivity.this);
//                aler1.setTitle("about");
//                aler1.setMessage("商品已加入購物車");
//                aler1.setPositiveButton("ok", null);
//                aler1.show();
//            }
//    }

    Button.OnClickListener btlistenerlist = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.button3: //car
                    if(!(MainActivity.carlist.isEmpty())){
                        Intent i1= new Intent(ItemInfoActivity.this,SharpCarActivity.class);
                        startActivity(i1);
                    }else{
                        AlertDialog.Builder aler1 = new AlertDialog.Builder(ItemInfoActivity.this);
                        aler1.setTitle("about");
                        aler1.setMessage("沒有商品");
                        aler1.show();
                    }
                    break;
                case R.id.button4:
                    Intent i2= new Intent(ItemInfoActivity.this,LoginActivity.class);
                    startActivity(i2);
                    break;
                case R.id.button2:
                    Intent i4= new Intent(ItemInfoActivity.this,MainActivity.class);
                    startActivity(i4);
                    break;
                case R.id.button9: //加入購物車

                    if(MainActivity.ISLOGIN){

                        boolean ck=true;
                        String name=MainActivity.SELECTNAME;
                        int price= MainActivity.SELECTPRICE;
                        String type=MainActivity.SELECTTYPE;
                        int img= MainActivity.SELECTPIMGS;

                        if(!(MainActivity.carlist.isEmpty())){
                            for(ShopCar sc : MainActivity.carlist){
                                if(sc.type.equals(type)){
                                    AlertDialog.Builder aler1 = new AlertDialog.Builder(ItemInfoActivity.this);
                                    aler1.setTitle("about");
                                    aler1.setMessage("此型號限購一組");
                                    aler1.show();
                                    ck=false;
                                }
                            }
                        }

                        if(ck){
                            MainActivity.carlist.add(new ShopCar(name,type,price,img));
                            Toast.makeText(ItemInfoActivity.this,"已加入購物車", Toast.LENGTH_LONG).show();
                        }


//                        try{
//
//                            OpenShopDB od = new OpenShopDB(ItemInfoActivity.this);
//                            SQLiteDatabase sd = od.getWritableDatabase(); //元件執行模式
//                            String sql="SELECT * FROM SHOPCAR";
//                            Cursor c = sd.rawQuery(sql,null);// 建立資料表指標器 加入sql指令
//
//                            int ct= c.getColumnCount();
//                            int rt= c.getCount();
//
//                            Toast.makeText(ItemInfoActivity.this, "總欄位數:"+String.valueOf(ct)+
//                                    " 資料數:"+String.valueOf(rt), Toast.LENGTH_LONG).show();
//
//                            c.close();
//                            sd.close();
//                            od.close();
//                            addshopcar();
//                        }catch (Exception e){
//                            Toast.makeText(ItemInfoActivity.this,e.toString(), Toast.LENGTH_LONG).show();
//                        }
                    }else {
                        Toast.makeText(ItemInfoActivity.this,"請登入", Toast.LENGTH_LONG).show();
                    }

                    break;
            }
        }
    };
}