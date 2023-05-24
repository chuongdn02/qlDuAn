package com.jovanovic.stefan.sqlitetutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jovanovic.stefan.sqlitetutorial.Handle.DBHelper;
import com.jovanovic.stefan.sqlitetutorial.Model.Tai_khoan;

public class Trang_ca_nhan extends AppCompatActivity {

    private TextView textViewCCCD;
    private EditText editTextHoTen;
    private EditText editTextDiaChi;
    private EditText editTextSDT;
    private EditText editTextEmail;
    private RadioGroup radioGroupGioiTinh;
    private Button buttonChinhSua;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_ca_nhan);

        // Khởi tạo đối tượng DBHelper
        dbHelper = new DBHelper(this);

        // Ánh xạ các thành phần giao diện
        textViewCCCD = findViewById(R.id.editTextCCCD);
        editTextHoTen = findViewById(R.id.editTextHoTen);
        editTextDiaChi = findViewById(R.id.editTextDiaChi);
        editTextSDT = findViewById(R.id.edtSDT);
        editTextEmail = findViewById(R.id.edtEmail);
        radioGroupGioiTinh = findViewById(R.id.rgAccountType);
        buttonChinhSua = findViewById(R.id.buttonChinhSua);

        String cccd = getIntent().getStringExtra("cccd");
        Tai_khoan account = dbHelper.getAccount(cccd);

        if (account != null) {
            textViewCCCD.setText(account.getCccd());
            String hoTen = account.getHo_ten();
            if (hoTen == null) {
                editTextHoTen.setText(""); // Đặt giá trị là chuỗi rỗng
            } else {
                editTextHoTen.setText(hoTen);
            }

            String diaChi = account.getDia_chi();
            if (diaChi == null) {
                editTextDiaChi.setText("");
            } else {
                editTextDiaChi.setText(diaChi);
            }

            String sdt = account.getSdt();
            if (sdt == null) {
                editTextSDT.setText("");
            } else {
                editTextSDT.setText(sdt);
            }

            String email = account.getEmail();
            if (email == null) {
                editTextEmail.setText("");
            } else {
                editTextEmail.setText(email);
            }
        }

        // Xác định giới tính mặc định khi text là "Nam"
        RadioButton radioButtonNam = findViewById(R.id.male);
        RadioButton radioButtonNu = findViewById(R.id.female);
        String gioiTinh = account.getGioi_tinh();
        if (gioiTinh != null) {
            if (gioiTinh.equals("Nam")) {
                radioButtonNam.setChecked(true);
            } else if (gioiTinh.equals("Nữ")) {
                radioButtonNu.setChecked(true);
            }
        }

        buttonChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cccd = textViewCCCD.getText().toString();
                String hoTen = editTextHoTen.getText().toString();
                String diaChi = editTextDiaChi.getText().toString();
                String sdt = editTextSDT.getText().toString();
                String email = editTextEmail.getText().toString();
                String gioiTinh = "";

                int selectedRadioButtonId = radioGroupGioiTinh.getCheckedRadioButtonId();
                if (selectedRadioButtonId == R.id.male) {
                    gioiTinh = "Nam";
                } else if (selectedRadioButtonId == R.id.female) {
                    gioiTinh = "Nữ";
                }

                // Tạo đối tượng Tai_khoan mới với các giá trị từ EditText
                Tai_khoan updatedAccount = new Tai_khoan(cccd, hoTen, diaChi, sdt, email, gioiTinh);

                // Cập nhật thông tin người dùng trong cơ sở dữ liệu
                boolean isUpdated = dbHelper.updateAccount(updatedAccount);

                if (isUpdated) {
                    Toast.makeText(Trang_ca_nhan.this, "Lưu thành công", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(Trang_ca_nhan.this, "Lưu thất bại", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
