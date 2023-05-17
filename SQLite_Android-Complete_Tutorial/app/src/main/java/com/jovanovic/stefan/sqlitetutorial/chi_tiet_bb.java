package com.jovanovic.stefan.sqlitetutorial;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jovanovic.stefan.sqlitetutorial.Handle.DBHelper;
import com.jovanovic.stefan.sqlitetutorial.Model.Bien_ban;

public class chi_tiet_bb extends AppCompatActivity {

    private TextView tvCCCD;
    private TextView tvDiaDiem;
    private TextView tvLoaiXe;
    private TextView tvNoiDung;
    private TextView tvLoaiViPham;
    private TextView tvSoTienPhat;
    private TextView tvIdCanBo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_bb);

        tvCCCD = findViewById(R.id.tv_cccd);
        tvDiaDiem = findViewById(R.id.tv_diadiem);
        tvLoaiXe = findViewById(R.id.tv_loaixe);
        tvNoiDung = findViewById(R.id.tv_noidung);
        tvLoaiViPham = findViewById(R.id.tv_loaivipham);
        tvSoTienPhat = findViewById(R.id.tv_sotienphat);
        tvIdCanBo = findViewById(R.id.tv_idcanbo);
        Intent intent = getIntent();
        String bienSo = intent.getStringExtra("bien_so");
        String loaiXe = intent.getStringExtra("loai_xe");

        // Gọi phương thức getBienBanByBienSoAndLoaiXe để lấy thông tin biên bản
        DBHelper dbHelper = new DBHelper(this);
        Bien_ban bienBan = dbHelper.getBienBanByBienSoAndLoaiXe(bienSo, loaiXe);

        // Hiển thị thông tin biên bản lên TextViews
        if (bienBan != null) {
            tvCCCD.setText("CCCD: " + bienBan.getCccd());
            tvDiaDiem.setText("Địa điểm: " + bienBan.getVtri());
            tvLoaiXe.setText("Loại xe: " + bienBan.getLoaiXe());
            tvNoiDung.setText("Nội dung: " + bienBan.getBienso());
            tvLoaiViPham.setText("Loại vi phạm: " + bienBan.getLoaiViPham());
            tvSoTienPhat.setText("Số tiền phạt: " + bienBan.getSoTienPhat());
            tvIdCanBo.setText("ID cán bộ: " + bienBan.getId_can_bo());
        }
    }
}