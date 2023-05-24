package com.jovanovic.stefan.sqlitetutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jovanovic.stefan.sqlitetutorial.Handle.DBHelper;
import com.jovanovic.stefan.sqlitetutorial.Model.Bien_ban;
import com.jovanovic.stefan.sqlitetutorial.Model.Tai_khoan;

public class Sua_bien_ban extends AppCompatActivity {
    private EditText etCCCD;
    private EditText etNoiDungViPham;
    private EditText etTienPhat;
    private Spinner spViTriVP;
    private Spinner spnLoaiViPham;
    private Spinner spnLoaiXe;
    private Button btnSua;

    private TextView sqd;

    private String[] loaiViPhamArr;
    private String[] viTriArr;
    private String[] loaiXeArr;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_bien_ban);

        dbHelper = new DBHelper(this);
        sqd =findViewById(R.id.sqd_id);
        etCCCD = findViewById(R.id.etCCCD);
        etNoiDungViPham = findViewById(R.id.etNoiDungViPham);
        etTienPhat = findViewById(R.id.etTienPhat);
        spViTriVP = findViewById(R.id.vitriVP);
        spnLoaiViPham = findViewById(R.id.spLoaiViPham);
        spnLoaiXe = findViewById(R.id.loaixe);
        btnSua = findViewById(R.id.btnSua);

        viTriArr = getResources().getStringArray(R.array.khu_vuc);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, viTriArr);
        spViTriVP.setAdapter(adapter1);

        loaiViPhamArr = getResources().getStringArray(R.array.loai_vi_pham);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, loaiViPhamArr);
        spnLoaiViPham.setAdapter(adapter2);

        loaiXeArr = getResources().getStringArray(R.array.vehicle_types);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, loaiXeArr);
        spnLoaiXe.setAdapter(adapter3);


        String bienBanJson = getIntent().getStringExtra("bienBan");
        Bien_ban bienBan = new Gson().fromJson(bienBanJson, Bien_ban.class);

        if (bienBan != null) {
            sqd.setText(bienBan.getSoQuyetDinh());
            etCCCD.setText(bienBan.getCccd());
            etNoiDungViPham.setText(bienBan.getBienso());
            etTienPhat.setText(String.valueOf(bienBan.getSoTienPhat()));
            spViTriVP.setSelection(getIndex(spViTriVP, bienBan.getVtri()));
            spnLoaiViPham.setSelection(getIndex(spnLoaiViPham, bienBan.getLoaiViPham()));
            spnLoaiXe.setSelection(getIndex(spnLoaiXe, bienBan.getLoaiXe()));
        }


        String idCanBoString = getIntent().getStringExtra("idCB");

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Sqd = sqd.getText().toString();
                String cccd = etCCCD.getText().toString();
                String noiDungViPham = etNoiDungViPham.getText().toString();
                String tienPhat = etTienPhat.getText().toString();
                String diaDiem = spViTriVP.getSelectedItem().toString();
                String loaiViPham = spnLoaiViPham.getSelectedItem().toString();
                String loaiXe = spnLoaiXe.getSelectedItem().toString();
                int idCanBo = Integer.parseInt(idCanBoString);




                //
                Bien_ban bienBan = new Bien_ban(cccd, diaDiem,loaiXe,noiDungViPham,loaiViPham, Sqd,tienPhat,idCanBo);

                // Cập nhật thông tin người dùng trong cơ sở dữ liệu
                boolean isUpdated = dbHelper.updateBienBan(bienBan);

                if (isUpdated) {
                    Toast.makeText(Sua_bien_ban.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Sua_bien_ban.this, Activity_ql_bienban.class);
                    intent.putExtra("idCB2",idCanBoString);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Sua_bien_ban.this, "Lưu thất bại", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private int getIndex(Spinner spinner, String value) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        return adapter.getPosition(value);
    }


}
