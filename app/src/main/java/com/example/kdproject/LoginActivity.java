package com.example.kdproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button btmem,bthome,btcar,bt1,bt2,bt3;
    EditText et1,et2;
    TextView tv1,tv2,tv3;
    private LinearLayout include_item1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btmem = (Button)findViewById(R.id.button4);
        bthome = (Button)findViewById(R.id.button2);
        btcar = (Button)findViewById(R.id.button3);

        if(!(MainActivity.ISLOGIN)){
            setContentView(R.layout.activity_login2);

            btmem = (Button)findViewById(R.id.button4);
            bthome = (Button)findViewById(R.id.button2);
            btcar = (Button)findViewById(R.id.button3);
            bt1 = (Button)findViewById(R.id.button1);
            bt2 = (Button)findViewById(R.id.button5);

            et1 = (EditText)findViewById(R.id.editText1);
            et2 = (EditText)findViewById(R.id.editText2);
            tv1 = (TextView)findViewById(R.id.textView3);
            tv2 = (TextView)findViewById(R.id.textView4);

            bt1.setOnClickListener(btlistener1);
            bt2.setOnClickListener(btlistener1);
            btmem.setOnClickListener(btlistener1);
            btcar.setOnClickListener(btlistener1);
            bthome.setOnClickListener(btlistener1);

        }else {
            setContentView(R.layout.logout);
            btmem = (Button)findViewById(R.id.button4);
            bthome = (Button)findViewById(R.id.button2);
            btcar = (Button)findViewById(R.id.button3);
            tv3 = (TextView)findViewById(R.id.textView18);
            bt3 = (Button)findViewById(R.id.button);
            tv3.setText(MainActivity.ACCOUNT);
            bt3.setOnClickListener(btlistener2);
            btmem.setOnClickListener(btlistener2);
            btcar.setOnClickListener(btlistener2);
            bthome.setOnClickListener(btlistener2);
        }

    }

    private void login(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getInstance().getReference("member");

        myRef.addValueEventListener(new ValueEventListener() {

            String account=et1.getText().toString();
            String password=et2.getText().toString();
            @Override
            public void onDataChange(@NonNull DataSnapshot dss) {
                if(dss.child(account).exists()){
                    String ps = dss.child(account).child("upassword").getValue().toString();
                    if(ps.equals(password)){
                        if(account.equals("admin") && password.equals("123456789")){ //管理員帳號
                            Intent i1= new Intent(LoginActivity.this,MagagerActivity.class);
                            startActivity(i1);
                            MainActivity.ISLOGIN=true;
                            MainActivity.ACCOUNT=account;
                        }else{
                            MainActivity.ISLOGIN=true;
                            MainActivity.ACCOUNT=account;
                            Intent i1= new Intent(LoginActivity.this,LoginActivity.class);
                            startActivity(i1);
                            Toast.makeText(LoginActivity.this, "登入成功", Toast.LENGTH_LONG).show();
                        }

                    }else {
                        AlertDialog.Builder aler1 = new AlertDialog.Builder(LoginActivity.this);
                        aler1.setTitle("about");
                        aler1.setMessage("密碼錯誤");
                        aler1.show();
                    }

                }else{
                    AlertDialog.Builder aler1 = new AlertDialog.Builder(LoginActivity.this);
                    aler1.setTitle("about");
                    aler1.setMessage("無此帳號");
                    aler1.show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    Button.OnClickListener btlistener2 = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.button:
                    setContentView(R.layout.activity_login2);
                    MainActivity.ISLOGIN=false;
                    MainActivity.ACCOUNT="";
                    MainActivity.carlist.clear();
                    Toast.makeText(LoginActivity.this, "登出成功", Toast.LENGTH_LONG).show();
                    Intent i0= new Intent(LoginActivity.this,LoginActivity.class);
                    startActivity(i0);
                    break;
                case R.id.button2:
                    Intent i1= new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i1);
                    break;
                case R.id.button3: //car
                    if(!(MainActivity.carlist.isEmpty())){
                        Intent i5= new Intent(LoginActivity.this,SharpCarActivity.class);
                        startActivity(i5);
                    }else{
                        AlertDialog.Builder aler1 = new AlertDialog.Builder(LoginActivity.this);
                        aler1.setTitle("about");
                        aler1.setMessage("沒有商品");
                        aler1.show();
                    }
                    break;
                case R.id.button4:
                    Intent i2= new Intent(LoginActivity.this,LoginActivity.class);
                    startActivity(i2);
                    break;
            }
        }
    };


    Button.OnClickListener btlistener1 = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch(v.getId()){

                case R.id.button1:

                    if(et1.getText().toString().trim().equals("")) {
                        et1.setError("請輸入帳號");
                        et1.setFocusable(true);
                    }else if(et2.getText().toString().trim().equals("")){
                        et2.setError("請輸入密碼");
                    }else {
                        login();
                    }

                    break;
                case R.id.button2:
                    Intent i1= new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i1);
                    break;
                case R.id.button3: //car
                    if(!(MainActivity.carlist.isEmpty())){
                        Intent i5= new Intent(LoginActivity.this,SharpCarActivity.class);
                        startActivity(i5);
                    }else{
                        AlertDialog.Builder aler1 = new AlertDialog.Builder(LoginActivity.this);
                        aler1.setTitle("about");
                        aler1.setMessage("沒有商品");
                        aler1.show();
                    }
                    break;
                case R.id.button4:
                    Intent i2= new Intent(LoginActivity.this,LoginActivity.class);
                    startActivity(i2);
                    break;
                case R.id.button5:
                    Intent i4= new Intent(LoginActivity.this,RegisterActivity.class);
                    startActivity(i4);
                    break;
            }
        }
    };
}