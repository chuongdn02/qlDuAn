package com.jovanovic.stefan.sqlitetutorial.Function;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.jovanovic.stefan.sqlitetutorial.Handle.DBHelper;
import com.jovanovic.stefan.sqlitetutorial.MainActivity;
import com.jovanovic.stefan.sqlitetutorial.Model.Tai_khoan;
import com.jovanovic.stefan.sqlitetutorial.R;
import com.jovanovic.stefan.sqlitetutorial.ql_tk_Activity;

public class Dang_nhap extends AppCompatActivity {
    private EditText et_Username, etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views and database helper
        et_Username = findViewById(R.id.et_Username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tv_register);
        dbHelper = new DBHelper(this);

        // Set click listener for login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String username = et_Username.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Validate user input
                if (TextUtils.isEmpty(username)) {
                    et_Username.setError("Please enter your email");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Please enter your password");
                    return;
                }
                if(username.equals("admin") && password.equals("admin") ){
                    Intent intentad = new Intent(Dang_nhap.this, ql_tk_Activity.class);
                    startActivity(intentad);
                    return;
                }

                // Check if email exists in database
                Tai_khoan acc = dbHelper.getAcc(username);
                if (acc == null) {
                    Toast.makeText(Dang_nhap.this, "Username does not exist", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if password is correct
                if (!password.equals(acc.getPassword())) {
                    Toast.makeText(Dang_nhap.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                    return;
                }

                String userLevel = acc.getBacTK();
                switch(userLevel) {
                    case "Người dùng": // Bậc 1
                        Intent intent1 = new Intent(Dang_nhap.this, TraCuu.class);
                        intent1.putExtra("acc", new Gson().toJson(acc));
                        startActivity(intent1);
                        break;
                    case "Cảnh sát": // Bậc 2
                        Intent intent2 = new Intent(Dang_nhap.this, Lap_bien_ban.class);
                        intent2.putExtra("acc", new Gson().toJson(acc));
                        startActivity(intent2);
                        break;
                    case "Cán bộ": // Bậc 3
                        Intent intent3 = new Intent(Dang_nhap.this, MainActivity.class);
                        intent3.putExtra("acc", new Gson().toJson(acc));
                        startActivity(intent3);
                        break;
                    default: // Sai dữ liệu
                        Toast.makeText(Dang_nhap.this, "Unknown user level", Toast.LENGTH_SHORT).show();
                        return;
                }

// Login successful, finish activity
                finish();

            }
        });

        // Set click listener for register text view
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start RegisterActivity
                Intent intent = new Intent(Dang_nhap.this, Dang_ki.class);
                startActivity(intent);
            }
        });
    }
}
