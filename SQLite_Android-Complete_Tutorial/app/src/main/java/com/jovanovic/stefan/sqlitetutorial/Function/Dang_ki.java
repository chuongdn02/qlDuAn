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

import com.jovanovic.stefan.sqlitetutorial.Handle.DBHelper;
import com.jovanovic.stefan.sqlitetutorial.Model.Tai_khoan;
import com.jovanovic.stefan.sqlitetutorial.R;

public class Dang_ki extends AppCompatActivity {
    private EditText etName, et_cccd, etPassword, etConfirmPassword;
    private Button btnRegister;
    private TextView tvLogin;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize views and database helper
        etName = findViewById(R.id.et_name);
        et_cccd = findViewById(R.id.et_cccd);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnRegister = findViewById(R.id.btn_register);
        tvLogin = findViewById(R.id.tv_login);
        dbHelper = new DBHelper(this);

        // Set click listener for register button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String name = etName.getText().toString().trim();
                String cccd = et_cccd.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                String confirmPassword = etConfirmPassword.getText().toString().trim();
                String BacTK = "Người dùng";


                // Validate user input
                if (TextUtils.isEmpty(name)) {
                    etName.setError("Please enter your name");
                    return;
                }
                if (TextUtils.isEmpty(cccd)) {
                    et_cccd.setError("Please enter your email");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Please enter your password");
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    etConfirmPassword.setError("Passwords do not match");
                    return;
                }

                // Check if email already exists in database
                Tai_khoan acc = dbHelper.getAcc(name);
                if (acc != null) {
                    Toast.makeText(Dang_ki.this, "username already exists", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Add user to database
                boolean isSuccess = dbHelper.addAcc(new Tai_khoan(name, password, cccd,BacTK));
                if (isSuccess) {
                    Toast.makeText(Dang_ki.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Dang_ki.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set click listener for login text view
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start LoginActivity
                Intent intent = new Intent(Dang_ki.this, Dang_nhap.class);
                startActivity(intent);
            }
        });
    }
}
