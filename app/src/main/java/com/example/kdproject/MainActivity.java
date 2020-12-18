package com.example.kdproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static boolean ISLOGIN=false;
    public static String ACCOUNT="";
    public static String SELECTNAME;
    public static String SELECTTYPE;
    public static int SELECTPRICE;
    public static int SELECTPIMGM;
    public static int SELECTPIMGS;
    public static ArrayList<ShopCar> carlist= new ArrayList<ShopCar>();
    public static String logoname[]={"Audiotechnica","AKG","Beyerdynamic","Sennheiser",
            "Sony",};

    ImageButton ibau,ibbyr,ibakg,ibson,ibsei;
    Button btmem,bthome,btcar;
    //ImageView imv;
    ViewFlipper vf;
    private int[] resId = {R.mipmap.ad_1,R.mipmap.ad_2,R.mipmap.ad_3};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btmem = (Button)findViewById(R.id.button4);
        bthome = (Button)findViewById(R.id.button2);
        btcar = (Button)findViewById(R.id.button3);
        //imv = (ImageView)findViewById(R.id.imageView1);

        ibau = (ImageButton)findViewById(R.id.imageButton1);
        ibbyr = (ImageButton)findViewById(R.id.imageButton2);
        ibakg = (ImageButton)findViewById(R.id.imageButton3);
        ibsei = (ImageButton)findViewById(R.id.imageButton4);
        ibson = (ImageButton)findViewById(R.id.imageButton5);
        vf=(ViewFlipper) findViewById(R.id.viewFlipper1);

        bthome.setOnClickListener(btlistener1);
        btcar.setOnClickListener(btlistener1);
        btmem.setOnClickListener(btlistener1);

        ibau.setOnClickListener(imbtlistener1);
        ibbyr.setOnClickListener(imbtlistener1);
        ibakg.setOnClickListener(imbtlistener1);
        ibsei.setOnClickListener(imbtlistener1);
        ibson.setOnClickListener(imbtlistener1);

        for (int i = 0; i < resId.length; i++  ) {
            vf.addView(getImageView(resId[i]));
        }

        vf.setInAnimation(this, R.anim.left_in);
        vf.setOutAnimation(this, R.anim.left_out);
        vf.setFlipInterval(3300);
        vf.startFlipping();
        //imv.setImageResource(R.mipmap.ad_1);


    }

    private ImageView getImageView(int resId){
        ImageView image = new ImageView(this);
        image.setBackgroundResource(resId);
        return image;
    }

    ImageButton.OnClickListener imbtlistener1 = new ImageButton.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.imageButton1:
                    SELECTNAME="Audiotechnica";
                    Intent i1= new Intent(MainActivity.this,ProductListActivity.class);
                    startActivity(i1);
                    break;
                case R.id.imageButton2:
                    SELECTNAME="AKG";
                    Intent i2= new Intent(MainActivity.this,ProductListActivity.class);
                    startActivity(i2);
                    break;
                case R.id.imageButton3:
                    SELECTNAME="Beyerdynamic";
                    Intent i3= new Intent(MainActivity.this,ProductListActivity.class);
                    startActivity(i3);
                    break;
                case R.id.imageButton4:
                    SELECTNAME="Sennheiser";
                    Intent i4= new Intent(MainActivity.this,ProductListActivity.class);
                    startActivity(i4);
                    break;
                case R.id.imageButton5:
                    SELECTNAME="Sony";
                    Intent i5= new Intent(MainActivity.this,ProductListActivity.class);
                    startActivity(i5);
                    break;
            }

        }
    };

    Button.OnClickListener btlistener1 = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.button2:

                    break;
                case R.id.button3:
                    if(!(carlist.isEmpty())){
                        Intent i1= new Intent(MainActivity.this,SharpCarActivity.class);
                        startActivity(i1);
                    }else{
                        AlertDialog.Builder aler1 = new AlertDialog.Builder(MainActivity.this);
                        aler1.setTitle("about");
                        aler1.setMessage("沒有商品");
                        aler1.show();
                    }

                    break;
                case R.id.button4:
                    Intent i2= new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(i2);
                    break;
            }
        }
    };


}