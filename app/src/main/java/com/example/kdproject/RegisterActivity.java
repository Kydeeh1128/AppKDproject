package com.example.kdproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    Button btmem, bthome, btcar, bt1;
    EditText etac, etps, etph, etem;
    TextView tv1, tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btmem = (Button) findViewById(R.id.button4);
        bthome = (Button) findViewById(R.id.button2);
        btcar = (Button) findViewById(R.id.button3);

        bt1 = (Button) findViewById(R.id.button1);
        etac = (EditText) findViewById(R.id.editText1);
        etps = (EditText) findViewById(R.id.editText2);
        etph = (EditText) findViewById(R.id.editText3);
        etem = (EditText) findViewById(R.id.editText4);

        bt1.setOnClickListener(btlistener1);
        btmem.setOnClickListener(btlistener1);
        btcar.setOnClickListener(btlistener1);
        bthome.setOnClickListener(btlistener1);

    }

    private boolean checkInfo() {

        String account = etac.getText().toString();
        String password = etps.getText().toString();
        try {
            int phone = Integer.parseInt(etph.getText().toString());
            if (account.length() > 15) {
                etac.setError("帳號需小於15字");
                etac.setFocusable(true);
                return false;
            } else if (password.length() > 15) {
                etps.setError("密碼需小於15字");
                etps.setFocusable(true);
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            etph.setError("號碼不可為字元");
            etph.setFocusable(true);
            return false;
        }
    }

    private void updateMember() {

        final String account = etac.getText().toString();
        final String password = etps.getText().toString();
        final String phone = etph.getText().toString();
        final String email = etem.getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getInstance().getReference("member");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dss) {

                if(! (dss.child(account).exists())){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getInstance().getReference("member");

                    myRef.child(account).child("upassword").setValue(password);
                    myRef.child(account).child("uemail").setValue(email);
                    myRef.child(account).child("uphone").setValue(phone);
                    Toast.makeText(RegisterActivity.this, "註冊成功! 請登入", Toast.LENGTH_LONG).show();

                    Intent i1 = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i1);
                }else if(dss.child(account).exists()){
                    AlertDialog.Builder aler1 = new AlertDialog.Builder(RegisterActivity.this);
                    aler1.setTitle("about");
                    aler1.setMessage("帳號已註冊");
                    aler1.show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    Button.OnClickListener btlistener1 = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button1:
                    if (checkInfo()) {
                        updateMember();
                    }

                    break;
                case R.id.button2:
                    Intent i1 = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(i1);
                    break;
                case R.id.button3: //car
                    Intent i3 = new Intent(RegisterActivity.this, SharpCarActivity.class);
                    startActivity(i3);
                    break;
                case R.id.button4:
                    Intent i2 = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i2);
                    break;
            }
        }
    };
}