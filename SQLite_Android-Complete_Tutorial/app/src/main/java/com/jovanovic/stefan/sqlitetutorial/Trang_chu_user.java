package com.jovanovic.stefan.sqlitetutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jovanovic.stefan.sqlitetutorial.Function.Dang_nhap;
import com.jovanovic.stefan.sqlitetutorial.Function.Lap_bien_ban;
import com.jovanovic.stefan.sqlitetutorial.Function.TraCuu;
import com.jovanovic.stefan.sqlitetutorial.Model.Tai_khoan;

public class Trang_chu_user extends AppCompatActivity {

    private RelativeLayout R1,R2,R3,R4;
    private TextView nameUser,id_cccd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu_user);

        R1 = findViewById(R.id.Relative1);
        R2 = findViewById(R.id.Relative2);
        R3 = findViewById(R.id.Relative3);
        R4 = findViewById(R.id.Relative4);
        nameUser = findViewById(R.id.nameUser);
        id_cccd = findViewById(R.id.id_cccd);

        String userJson = getIntent().getStringExtra("acc");
        Gson gson = new Gson();

        Tai_khoan account = gson.fromJson(userJson, Tai_khoan.class);
        String username = account.getUsername();

        String id = account.getCccd();

        nameUser.setText(username);
        id_cccd.setText("ID: "+id);



        R1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trang_chu_user.this, Trang_ca_nhan.class);
                intent.putExtra("cccd",id);
                startActivity(intent);

            }
        });
        R2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Trang_chu_user.this, TraCuu.class);
                startActivity(intent);

            }
        });
        R3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        R4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }
}