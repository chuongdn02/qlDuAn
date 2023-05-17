package com.jovanovic.stefan.sqlitetutorial.Function;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.jovanovic.stefan.sqlitetutorial.Handle.DBHelper;
import com.jovanovic.stefan.sqlitetutorial.Model.Tai_khoan;
import com.jovanovic.stefan.sqlitetutorial.R;

public class Lap_bien_ban extends AppCompatActivity {
    private EditText etCCCD;
    private Spinner spnLoaiViPham, spnLoaiXe, spnDiaDiem;
    private EditText etNoiDungViPham;
    private EditText etTienPhat;
    private Button btnLapBienBan;
    private String[] loaiViPhamArr, viTriArr, loaiXeArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_vp);

        // Ánh xạ view
        etCCCD = findViewById(R.id.etCCCD);
        etNoiDungViPham = findViewById(R.id.etNoiDungViPham);
        etTienPhat = findViewById(R.id.etTienPhat);

        Spinner spvitriVP = findViewById(R.id.vitriVP);
        Spinner spLoaiViPham = findViewById(R.id.spLoaiViPham);
        Spinner spnLoaiXe = findViewById(R.id.loaixe);

        viTriArr = getResources().getStringArray(R.array.khu_vuc);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, viTriArr);
        spvitriVP.setAdapter(adapter1);

        loaiViPhamArr = getResources().getStringArray(R.array.loai_vi_pham);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, loaiViPhamArr);
        spLoaiViPham.setAdapter(adapter2);

        loaiXeArr = getResources().getStringArray(R.array.vehicle_types);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, loaiXeArr);
        spnLoaiXe.setAdapter(adapter3);

        btnLapBienBan = findViewById(R.id.btnLapBienBan);

        // Thiết lập sự kiện khi click vào button
        btnLapBienBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cccd = etCCCD.getText().toString().trim();
                String viTri = spvitriVP.getSelectedItem().toString();
                String loaiViPham = spLoaiViPham.getSelectedItem().toString();
                String loaiXe = spnLoaiXe.getSelectedItem().toString();
                String BienSo = etNoiDungViPham.getText().toString().trim();
                String tienPhat = etTienPhat.getText().toString().trim();

                // Kiểm tra các trường nhập liệu có rỗng hay không
                if (TextUtils.isEmpty(cccd)) {
                    etCCCD.setError("Vui lòng nhập CCCD/CMND");
                    etCCCD.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(viTri)) {
                    spvitriVP.requestFocus();
                    Toast.makeText(Lap_bien_ban.this, "Vui lòng chọn vị trí vi phạm", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(BienSo)) {
                    etNoiDungViPham.setError("Vui lòng nhập BienSo vi phạm");
                    etNoiDungViPham.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(tienPhat)) {
                    etTienPhat.setError("Vui lòng nhập số tiền phạt");
                    etTienPhat.requestFocus();
                    return;
                }

                String userJson = getIntent().getStringExtra("acc");
                Gson gson = new Gson();
                Tai_khoan account = gson.fromJson(userJson, Tai_khoan.class);

                String idCanBo = account.getCccd().toString().trim();

                // Lưu thông tin vào CSDL
                DBHelper dbHelper = new DBHelper(Lap_bien_ban.this);
                dbHelper.insertBien_ban(cccd, loaiViPham, loaiXe, BienSo, tienPhat, idCanBo, viTri);
                Toast.makeText(Lap_bien_ban.this, "Lập biên bản thành công", Toast.LENGTH_SHORT).show();

//                boolean isSuccess = dbHelper.addAcc(new account(name, password, cccd,BacTK));
//                if (isSuccess) {
//                    Toast.makeText(Report_VP.this, "Registration successful", Toast.LENGTH_SHORT).show();
//                    finish();
//                } else {
//                    Toast.makeText(Report_VP.this, "Registration failed", Toast.LENGTH_SHORT).show();
//                }

            }
        });
    }
}

