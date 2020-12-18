package com.example.kdproject;

import androidx.annotation.NonNull;
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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MagagerActivity extends AppCompatActivity {

    Button btmem,bthome,btcar;
    Button bt1,bt2,bt3,bt4;
    ListView lv;
    EditText et1,et2,et3,et4,et5,et6,et7,et8;
    Spinner sp1,sp2;

    OpenDB db;
    public int lisize;
    public String name;

    DatabaseReference dr;
    myadapterlist myad;
    myadapterlist2 ormyad;

    private String pname[];
    private String ptype[];
    private String imgs[];
    private String imgm[];
    private String other[];
    private int price[];
    private int count[];
    private int pprice[];

    private String oraccount[];
    private String oroinfo[];
    private int orprice[];

    ArrayList<OrderInfo> ordarlist ;
    ArrayList<ProductSet> alist = new ArrayList<ProductSet>(); ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magager);

        btmem = (Button)findViewById(R.id.button4);
        bthome = (Button)findViewById(R.id.button2);
        btcar = (Button)findViewById(R.id.button3);

        myad = new myadapterlist(MagagerActivity.this);
        ormyad = new myadapterlist2(MagagerActivity.this);

        sp1 = (Spinner)findViewById(R.id.spinnerShop);
        sp2 = (Spinner)findViewById(R.id.spinnerShop1);

        bt1 = (Button)findViewById(R.id.button5);
        bt2 = (Button)findViewById(R.id.button6);
        bt3 = (Button)findViewById(R.id.button7);
        bt4 = (Button)findViewById(R.id.button8);
        et1 = (EditText)findViewById(R.id.editText1);
        et2 = (EditText)findViewById(R.id.editText2);
        et3 = (EditText)findViewById(R.id.editText3);
        et4 = (EditText)findViewById(R.id.editText4);
        et5 = (EditText)findViewById(R.id.editText5);
        et6 = (EditText)findViewById(R.id.editText6);
        et7 = (EditText)findViewById(R.id.editText7);
        //et8 = (EditText)findViewById(R.id.editText8);

        lv = (ListView)findViewById(R.id.listView1);

        final DatabaseReference dr;
        //設定讀取路徑
//        dr = FirebaseDatabase.getInstance().getReference("product");
//        dr.addListenerForSingleValueEvent(valueEventListener);

        btmem.setOnClickListener(btlistenerlist);
        btcar.setOnClickListener(btlistenerlist);
        bthome.setOnClickListener(btlistenerlist);
        bt1.setOnClickListener(btlistenerlist2);
        bt3.setOnClickListener(btlistenerlist2);
        bt4.setOnClickListener(btlistenerlist2);
        bt2.setOnClickListener(btlistenerlist2);

        //lv.setAdapter(myad);
        sp1.setOnItemSelectedListener(spnOnItemSelected);
        sp2.setOnItemSelectedListener(spnOnItemSelected);
    }

    private AdapterView.OnItemSelectedListener spnOnItemSelected
            = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
        {
                et1.setText(sp1.getSelectedItem().toString());
                name = sp2.getSelectedItem().toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0)
        {
            // TODO Auto-generated method stub
        }
    };


    Button.OnClickListener btlistenerlist = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.button3: //car
                    Intent i3= new Intent(MagagerActivity.this,SharpCarActivity.class);
                    startActivity(i3);
                    break;
                case R.id.button4:
                    Intent i2= new Intent(MagagerActivity.this,LoginActivity.class);
                    startActivity(i2);
                    break;
                case R.id.button2:
                    Intent i4= new Intent(MagagerActivity.this,MainActivity.class);
                    startActivity(i4);
                    break;
            }
        }
    };


    Button.OnClickListener btlistenerlist2 = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId()){

                case R.id.button5: //新增

                    if(et1.getText().toString().trim().equals("")){
                        et1.setError("請選擇名稱");
                        et1.setFocusable(true);
                    }else{

                        try{
                            String name = sp1.getSelectedItem().toString();
                            String type = et2.getText().toString();
                            int price = Integer.parseInt(et3.getText().toString());
                            int count = Integer.parseInt(et4.getText().toString());
                            String simg = et5.getText().toString();
                            String limg = et6.getText().toString();
                            String other = et7.getText().toString();

                            ProductSet ps = new ProductSet(type,price,count,simg,limg,other);

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            final DatabaseReference myRef = database.getInstance().getReference("product").child(name);
                            myRef.push().setValue(ps);

                            Toast.makeText(MagagerActivity.this, "新增成功", Toast.LENGTH_LONG).show();

                        }catch(Exception e){
                            AlertDialog.Builder aler1 = new AlertDialog.Builder(MagagerActivity.this);
                            aler1.setTitle("about");
                            aler1.setMessage(e.toString());
                            aler1.setPositiveButton("ok", null);
                            aler1.show();
                        }
                    }

                    break;
                case R.id.button6://修改
                    et2.setText("");
                    et3.setText("");
                    et4.setText("");
                    et5.setText("");
                    et6.setText("");
                    et7.setText("");
                    break;
                case R.id.button7: //列表

                    DatabaseReference dr2 = FirebaseDatabase.getInstance().getReference("order");
                    dr2.addListenerForSingleValueEvent(valueEventListener2);


                    break;

                case R.id.button8: //資料庫資訊

                    name = sp2.getSelectedItem().toString();
                    try{
                        DatabaseReference dr = FirebaseDatabase.getInstance().getReference("product").child(name);
                        dr.addListenerForSingleValueEvent(valueEventListener);
                    }catch (Exception e){
                        AlertDialog.Builder aler1 = new AlertDialog.Builder(MagagerActivity.this);
                        aler1.setTitle("about");
                        aler1.setMessage("無品牌資料");
                        aler1.setPositiveButton("ok", null);
                        aler1.show();
                    }


                    et2.setText("");
                    et3.setText("");
                    et4.setText("");
                    et5.setText("");
                    et6.setText("");
                    et7.setText("");

            }
        }
    };

    ValueEventListener valueEventListener2 = new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot dss) {

            int orsize=0;
            ordarlist = new ArrayList<OrderInfo>();
            ordarlist.clear();

            if (dss.exists()) {
                for(DataSnapshot ds : dss.getChildren()){
                    OrderInfo or = ds.getValue(OrderInfo.class);
                    ordarlist.add(or);
                }
            }
            orsize=ordarlist.size();

            if(orsize!= 0){
                oraccount = new String[orsize];
                oroinfo = new String[orsize];
                orprice = new int[orsize];
                int n=0;
                for(OrderInfo oi : ordarlist){
                    oraccount[n] = oi.account;
                    oroinfo[n] = oi.info;
                    orprice[n] = oi.price;
                    n=n+1;
                }
                Toast.makeText(MagagerActivity.this,"訂單數="+String.valueOf(n), Toast.LENGTH_LONG).show();
            }

            ormyad = new MagagerActivity.myadapterlist2(MagagerActivity.this);
            lv.setAdapter(ormyad);

        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            alist.clear();
            int i=0;
            int pssize=0;
            try{
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ProductSet pset = snapshot.getValue(ProductSet.class);
                        alist.add(pset);
                        i++;
                    }

                    pssize = alist.size();
                    Toast.makeText(MagagerActivity.this,"數量="+String.valueOf(pssize), Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MagagerActivity.this,"無資料", Toast.LENGTH_LONG).show();
                }
                 }catch (Exception e){
                Toast.makeText(MagagerActivity.this,e.toString(), Toast.LENGTH_LONG).show();
            }

            if(pssize!=0){
                pname = new String[pssize];
                ptype = new String[pssize];
                pprice = new int[pssize];
                count = new int[pssize];
                imgm = new String[pssize];
                imgs = new String[pssize];
                other = new String[pssize];

                int n=0;
                for(ProductSet a : alist){
                    pname[n] = name;
                    ptype[n] = a.type;
                    pprice[n] = a.price;
                    count[n] = a.count;
                    imgs[n] = a.simg;
                    imgm[n] = a.mimg;
                    other[n] = a.other;
                    n=n+1;
                }
            }
            myad = new MagagerActivity.myadapterlist(MagagerActivity.this);
            lv.setAdapter(myad);
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };

    class myadapterlist2 extends BaseAdapter {

        private LayoutInflater myinfla ;
        public myadapterlist2(Context c){
            myinfla = LayoutInflater.from(c);

        }
        @Override
        public int getCount() {
            return oraccount.length;
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
            v=myinfla.inflate(R.layout.orderlist, null);

            TextView tv1=(TextView) v.findViewById(R.id.tv1);
            TextView tv2=(TextView) v.findViewById(R.id.tv3);
            TextView tv3=(TextView) v.findViewById(R.id.tv5);
            String p = String.valueOf(orprice[position]);

            tv1.setText(oraccount[position]);
            tv2.setText(oroinfo[position]);
            tv3.setText(p);
            return v;
        }
    }

    class myadapterlist extends BaseAdapter {

        private LayoutInflater myinfla ;

        public myadapterlist(Context c){
            myinfla = LayoutInflater.from(c);

        }
        @Override
        public int getCount() {
            return ptype.length;
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
            v=myinfla.inflate(R.layout.productslist, null);

            TextView tv1=(TextView) v.findViewById(R.id.tv1);
            TextView tv2=(TextView) v.findViewById(R.id.tv2);
            TextView tv3=(TextView) v.findViewById(R.id.tv3);
            TextView tv4=(TextView) v.findViewById(R.id.tv4);
            TextView tv5=(TextView) v.findViewById(R.id.tv5);
            TextView tv6=(TextView) v.findViewById(R.id.tv6);
            TextView tv7=(TextView) v.findViewById(R.id.tv7);

            try{
                String pri= String.valueOf(pprice[position]) ;
                String cou= String.valueOf(count[position]) ;
                tv1.setText(pname[position]);
                tv2.setText(ptype[position]);
                tv3.setText("$"+pri);
                tv4.setText(cou);
                tv5.setText(imgs[position]);
                tv6.setText(imgm[position]);
                tv7.setText(other[position]);


            }catch (Exception e){
                Toast.makeText(MagagerActivity.this,e.toString(), Toast.LENGTH_LONG).show();
            }

            return v;
        }
    }

}