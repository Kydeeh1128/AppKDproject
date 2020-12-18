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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.spec.ECField;
import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    Button btmem,bthome,btcar;

    public static String PNAME;
    public static String PPRICE;
    public static String PNUM;

    private int imgsid[];
    private int imgmid[];
    private String pname[];
    private String ptype[];
    private int pprice[];
    public static int lisize;

    ImageView iv;
    public ArrayList<ProductCollection> alist;
    DatabaseReference dr;

    OpenDB db;
    public GridView gv;
    public myadapter myad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        btmem = (Button)findViewById(R.id.button4);
        bthome = (Button)findViewById(R.id.button2);
        btcar = (Button)findViewById(R.id.button3);

        gv = (GridView) findViewById(R.id.gridView1);
        iv = (ImageView)findViewById(R.id.imageView);

        //arraylist 放產品資料;
        settopimage();
        alist = new ArrayList<>();
        String childname = MainActivity.SELECTNAME;
        dr = FirebaseDatabase.getInstance().getReference("product").child(childname);
        dr.addListenerForSingleValueEvent(valueEventListener);

        btmem.setOnClickListener(btlistener1);
        btcar.setOnClickListener(btlistener1);
        bthome.setOnClickListener(btlistener1);
    }
    private void settopimage(){
        switch (MainActivity.SELECTNAME){
            case "Sony":
                iv.setImageResource(R.mipmap.m_sony);
                break;
            case "AKG":
                iv.setImageResource(R.mipmap.m_akg);
                break;
            case "Audiotechnica":
                iv.setImageResource(R.mipmap.m_audiotechnica);
                break;
            case "Beyerdynamic":
                iv.setImageResource(R.mipmap.m_beyerdynamic);
                break;
            case "Sennheiser":
                iv.setImageResource(R.mipmap.m_sennheiser);
                break;
        }
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            alist.clear();
            int i=0;
            int si;
            try{
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ProductCollection pset = snapshot.getValue(ProductCollection.class);
                        alist.add(pset);
                        i++;
                    }
                    lisize=alist.size();
                }else{
                    AlertDialog.Builder aler1 = new AlertDialog.Builder(ProductListActivity.this);
                    aler1.setTitle("about");
                    aler1.setMessage("無產品");
                    aler1.setPositiveButton("ok", null);
                    aler1.show();
                }

            }catch (Exception e){
                Toast.makeText(ProductListActivity.this,e.toString(), Toast.LENGTH_LONG).show();
            }

            if(lisize!=0){
                imgsid = new int[lisize];
                imgmid = new int[lisize];
                pname = new String[lisize];
                ptype = new String[lisize];
                pprice = new int[lisize];

                int n=0;
                for(ProductCollection a : alist){
                    ptype[n] = a.type;
                    pprice[n] = a.price;
                    pname[n] = MainActivity.SELECTNAME;
                    String img1 = a.simg;
                    String img2 = a.mimg;
                    int pids=getResources().getIdentifier(img1, "mipmap", "com.example.kdproject");
                    imgsid[n] = pids;
                    int pidm=getResources().getIdentifier(img2, "mipmap", "com.example.kdproject");
                    imgmid[n] = pidm;
                    n=n+1;
                }
                Toast.makeText(ProductListActivity.this,"產品數="+String.valueOf(n), Toast.LENGTH_SHORT).show();
            }
            myad = new myadapter(ProductListActivity.this);
            gv.setAdapter(myad);
            gv.setOnItemClickListener(gvlistener);
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };

//    private void creatdb(){
//
//        try{
//            OpenDB od = new OpenDB(ProductListActivity.this);
//            SQLiteDatabase sd = od.getWritableDatabase();
//            Cursor c= od.queryDataList(sd,MainActivity.SELECTNAME);
//            int columnCount = c.getColumnCount();
//            int countnum = c.getCount();
//            Toast.makeText(ProductListActivity.this,String.valueOf(countnum)+" "+MainActivity.SELECTNAME, Toast.LENGTH_LONG).show();
//            if(countnum!=0){
//                c.moveToFirst();
//                imgsid = new int[countnum];
//                imgmid = new int[countnum];
//                pname = new String[countnum];
//                ptype = new String[countnum];
//                //pprice = new String[countnum];
//                for(int i=0; i<countnum ;i++){
//                    pname[i] = c.getString(0).toString();
//                    ptype[i] = c.getString(1).toString();
//                    //pprice[i] = c.getString(2).toString();
//                    String imgstr = c.getString(3).toString();
//                    String imgmstr = c.getString(4).toString();
//                    int mapmid=getResources().getIdentifier(imgmstr, "mipmap", "com.example.kdproject");
//                    imgmid[i] = mapmid;
//                    int mapid=getResources().getIdentifier(imgstr, "mipmap", "com.example.kdproject");
//                    imgsid[i] = mapid;
//                    c.moveToNext();
//                }
//            }
//            String str = "name "+String.valueOf(pname.length)+"\n"+
//                    "type "+String.valueOf(ptype.length)+"\n"+
//                    "pprice "+String.valueOf(pprice.length)+"\n"+
//                    "img "+String.valueOf(imgsid.length)+"\n";
//            Toast.makeText(ProductListActivity.this,str, Toast.LENGTH_LONG).show();
//            sd.close();
//            od.close();
//        }catch (Exception e){
//            AlertDialog.Builder aler1 = new AlertDialog.Builder(ProductListActivity.this);
//            aler1.setTitle("about");
//            aler1.setMessage("1"+e.toString());
//            aler1.setPositiveButton("ok", null);
//            aler1.show();
//        }
//    }

    Button.OnClickListener btlistener1 = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch(v.getId()){

                case R.id.button3: //car
                    if(!(MainActivity.carlist.isEmpty())){
                        Intent i1= new Intent(ProductListActivity.this,SharpCarActivity.class);
                        startActivity(i1);
                    }else{
                        AlertDialog.Builder aler1 = new AlertDialog.Builder(ProductListActivity.this);
                        aler1.setTitle("about");
                        aler1.setMessage("沒有商品");
                        aler1.show();
                    }
                    break;
                case R.id.button4:
                    Intent i2= new Intent(ProductListActivity.this,LoginActivity.class);
                    startActivity(i2);
                    break;
                case R.id.button2:
                    Intent i4= new Intent(ProductListActivity.this,MainActivity.class);
                    startActivity(i4);
                    break;

            }
        }
    };

    GridView.OnItemClickListener gvlistener= new GridView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            MainActivity.SELECTTYPE=ptype[position];
            MainActivity.SELECTPRICE=pprice[position];
            MainActivity.SELECTPIMGM=imgmid[position];
            MainActivity.SELECTPIMGS=imgsid[position];
            Intent i4= new Intent(ProductListActivity.this,ItemInfoActivity.class);
            startActivity(i4);
        }
    };

    class myadapter extends BaseAdapter {

        private LayoutInflater myinfla ;

        public myadapter(Context c){
            myinfla = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            //return 0;
            return imgsid.length;
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
            v=myinfla.inflate(R.layout.item_list, null);
            ImageView imgv=(ImageView) v.findViewById(R.id.imageView2);
            TextView tv1=(TextView) v.findViewById(R.id.textView11);
            TextView tv2=(TextView) v.findViewById(R.id.textView14);
            TextView tv3=(TextView) v.findViewById(R.id.textView15);

            try{
                imgv.setImageResource(imgsid[position]);
                tv1.setText(pname[position]);
                tv2.setText(ptype[position]);
                String str="$"+ String.valueOf(pprice[position]);
                tv3.setText(str);

            }catch (Exception e){
                Toast.makeText(ProductListActivity.this,e.toString(), Toast.LENGTH_LONG).show();
            }

            return v;
        }
    }
}