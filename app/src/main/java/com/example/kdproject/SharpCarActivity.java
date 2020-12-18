package com.example.kdproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SharpCarActivity extends AppCompatActivity {


    myadapter2 mdapt;
    ListView lv;
    Button btmem,bthome,btcar;
    private int imgid[];
    private String pinfo[];
    private int pprice[];

    Button bt1,bt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharp_car);

        btmem = (Button)findViewById(R.id.button4);
        bthome = (Button)findViewById(R.id.button2);
        btcar = (Button)findViewById(R.id.button3);
        bt1 = (Button)findViewById(R.id.button10);
        bt2 = (Button)findViewById(R.id.button11);

        lv = (ListView)findViewById(R.id.listView1);

        btmem.setOnClickListener(btlistenerlist);
        btcar.setOnClickListener(btlistenerlist);
        bthome.setOnClickListener(btlistenerlist);

        bt1.setOnClickListener(btlistenerlist2);
        bt2.setOnClickListener(btlistenerlist2);

        updatecar();

//        mdapt = new SharpCarActivity.myadapter2(SharpCarActivity.this);
//        lv.setAdapter(mdapt);

    }

    public void updatecar(){

        int sizecar = MainActivity.carlist.size();
        pprice= new int[sizecar];
        pinfo= new String[sizecar];
        imgid= new int[sizecar];
        int n=0;

        for(ShopCar sc : MainActivity.carlist){
            String info= "品牌:\n"+sc.name+"\n型號:\n"+sc.type;
            pprice[n] = sc.price;
            pinfo[n] = info;
            imgid[n]= sc.img;
            n=n+1;
        }

        mdapt = new SharpCarActivity.myadapter2(SharpCarActivity.this);
        lv.setAdapter(mdapt);

    }

//    private void creatshopcar(){
//        try{
//            OpenShopDB od = new OpenShopDB(SharpCarActivity.this);
//            SQLiteDatabase sd = od.getWritableDatabase();
//            Cursor c= od.queryData(sd);
//            int columnCount = c.getColumnCount();
//            int countnum = c.getCount();
//            Toast.makeText(SharpCarActivity.this,String.valueOf(countnum)+" "+MainActivity.SELECTNAME, Toast.LENGTH_LONG).show();
//            if(countnum!=0){
//                c.moveToFirst();
//                simsid = new int[countnum];
//                pinfo = new String[countnum];
//                pprice = new String[countnum];
//
//                for(int i=0; i<countnum ;i++){
//                    pinfo[i] = c.getString(0).toString();
//                    pprice[i] = c.getString(1).toString();
//                    String imgstr = c.getString(3).toString();
//                    int mapid=getResources().getIdentifier(imgstr, "mipmap", "com.example.kdproject");
//                    simsid[i] = mapid;
//                    c.moveToNext();
//                }
//            }
//            String str = "name "+String.valueOf(pname.length)+"\n"+
//                    "type "+String.valueOf(ptype.length)+"\n"+
//                    "pprice "+String.valueOf(pprice.length)+"\n"+
//                    "img "+String.valueOf(imgsid.length)+"\n";
//            Toast.makeText(SharpCarActivity.this,str, Toast.LENGTH_LONG).show();
//            sd.close();
//            od.close();
//        }catch (Exception e){
//            AlertDialog.Builder aler1 = new AlertDialog.Builder(SharpCarActivity.this);
//            aler1.setTitle("about");
//            aler1.setMessage("1"+e.toString());
//            aler1.setPositiveButton("ok", null);
//            aler1.show();
//        }
//    }

    Button.OnClickListener btlistenerlist = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.button3: //car
                    if(!(MainActivity.carlist.isEmpty())){
                        Intent i1= new Intent(SharpCarActivity.this,SharpCarActivity.class);
                        startActivity(i1);
                    }else{
                        AlertDialog.Builder aler1 = new AlertDialog.Builder(SharpCarActivity.this);
                        aler1.setTitle("about");
                        aler1.setMessage("沒有商品");
                        aler1.show();
                    }
                    break;
                case R.id.button4:
                    Intent i2= new Intent(SharpCarActivity.this,LoginActivity.class);
                    startActivity(i2);
                    break;
                case R.id.button2:
                    Intent i4= new Intent(SharpCarActivity.this,MainActivity.class);
                    startActivity(i4);
                    break;
            }
        }
    };

    Button.OnClickListener btlistenerlist2 = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.button10: //下單
                    Intent i3= new Intent(SharpCarActivity.this,OrderActivity.class);
                    startActivity(i3);
                    break;

                case R.id.button11:

                    MainActivity.carlist.clear();
                    updatecar();

                    break;
            }
        }
    };


    class myadapter2 extends BaseAdapter {

        private LayoutInflater myinfla ;

        public myadapter2(Context c){
            myinfla = LayoutInflater.from(c);
        }
        @Override
        public int getCount() {
            return imgid.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View v, ViewGroup parent) {
            v=myinfla.inflate(R.layout.carlist, null);
            ImageView imgv=(ImageView) v.findViewById(R.id.imageView3);
            TextView tv1=(TextView) v.findViewById(R.id.textView14);
            TextView tv2=(TextView) v.findViewById(R.id.textView15);

            try{
                imgv.setImageResource(imgid[position]);
                tv1.setText(pinfo[position]);
                String pric = "$ " +String.valueOf(pprice[position]);
                tv2.setText(pric);

            }catch (Exception e){
                Toast.makeText(SharpCarActivity.this,e.toString(), Toast.LENGTH_LONG).show();
            }

            return v;
        }
    }
}