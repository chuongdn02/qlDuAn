package com.jovanovic.stefan.sqlitetutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jovanovic.stefan.sqlitetutorial.Function.Dang_ki;
import com.jovanovic.stefan.sqlitetutorial.Function.Dang_nhap;

public class bat_dau_activity extends AppCompatActivity {
    private Button btn_signin,btn_signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bat_dau);
        btn_signin = findViewById(R.id.btn_signin);
        btn_signup = findViewById(R.id.btn_signup);
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bat_dau_activity.this, Dang_nhap.class);
                startActivity(intent);

            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bat_dau_activity.this, Dang_ki.class);
                startActivity(intent);
            }
        });
    }
}