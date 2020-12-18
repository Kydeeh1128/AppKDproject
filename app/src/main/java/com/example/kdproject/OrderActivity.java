package com.example.kdproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    Button btmem,bthome,btcar;
    Button bt1,bt2;
    TextView tv;
    String order;
    int sum=0;

    //ArrayList<OrderInfo> orderlist = new ArrayList<OrderInfo>();
    OrderInfo oi ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


        btmem = (Button)findViewById(R.id.button4);
        bthome = (Button)findViewById(R.id.button2);
        btcar = (Button)findViewById(R.id.button3);
        bt1 = (Button)findViewById(R.id.button9);
        bt2 = (Button)findViewById(R.id.button12);

        tv = (TextView)findViewById(R.id.textView20);
        String account=MainActivity.ACCOUNT;
        order = account+"的訂單:"+"\n\n\n";
        String orderinfo="";

        for(ShopCar sc : MainActivity.carlist){
            order += "品牌 :"+sc.name+"   "+"型號 :"+sc.type+"\n"+"價錢 :"+String.valueOf(sc.price)+" 元\n\n";
            orderinfo +="("+sc.name+"-"+sc.type+"-$"+String.valueOf(sc.price)+")\n";
            sum+=sc.price;
        }
        order += "\n總價 :"+String.valueOf(sum)+" 元";

        //orderlist.add( new OrderInfo(account, orderinfo , sum) );

        oi = new OrderInfo(account, orderinfo , sum);
        account +="總計 :"+String.valueOf(sum)+" 元整";
        tv.setText(order);

        bt1.setOnClickListener(btlistenerlist);
        bt2.setOnClickListener(btlistenerlist2);
        btmem.setOnClickListener(btlistenerlist);
        btcar.setOnClickListener(btlistenerlist);
        bthome.setOnClickListener(btlistenerlist);
    }

    Button.OnClickListener btlistenerlist2 = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getInstance().getReference("order");
            myRef.push().setValue(oi);

            AlertDialog.Builder aler1 = new AlertDialog.Builder(OrderActivity.this);
            aler1.setTitle("about");
            aler1.setMessage("訂單已送出,謝謝您");
            aler1.setPositiveButton("ok", null);
            aler1.show();

            Intent i4 = new Intent(OrderActivity.this, MainActivity.class);
            startActivity(i4);

        }
    };



    Button.OnClickListener btlistenerlist = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.button9: //car
                    Intent i3 = new Intent(OrderActivity.this, SharpCarActivity.class);
                    startActivity(i3);
                    break;
                case R.id.button3: //car
                    Intent i1 = new Intent(OrderActivity.this, SharpCarActivity.class);
                    startActivity(i1);
                    break;
                case R.id.button4:
                    Intent i2 = new Intent(OrderActivity.this, LoginActivity.class);
                    startActivity(i2);
                    break;
                case R.id.button2:
                    Intent i4 = new Intent(OrderActivity.this, MainActivity.class);
                    startActivity(i4);
                    break;
            }
        }
    };



}