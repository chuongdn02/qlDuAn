package com.jovanovic.stefan.sqlitetutorial.Function;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.jovanovic.stefan.sqlitetutorial.Handle.DBHelper;
import com.jovanovic.stefan.sqlitetutorial.Model.Bien_ban;
import com.jovanovic.stefan.sqlitetutorial.Model.Tai_khoan;
import com.jovanovic.stefan.sqlitetutorial.R;
import com.jovanovic.stefan.sqlitetutorial.chi_tiet_bb;
import com.jovanovic.stefan.sqlitetutorial.photo.Photo;
import com.jovanovic.stefan.sqlitetutorial.photo.PhotoAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class TraCuu extends AppCompatActivity {
    private TextView tvWelcome,et_Bienso,et_maXacNhan;
    private TextView textV;
    private Timer mTimer;
    private Button btnLogout;
    private SharedPreferences pref;
    private DBHelper dbHelper;
    private ViewPager viewPager;

    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;
    private List<Photo> mListPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ẩn thanh trạng thái



        setContentView(R.layout.activity_tra_cuu);


        viewPager = findViewById(R.id.view_paper);
        circleIndicator = findViewById(R.id.indicator);
        mListPhoto = getListPhoto();

        photoAdapter = new PhotoAdapter(TraCuu.this,mListPhoto);
        viewPager.setAdapter(photoAdapter);
        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSlideImages();


        // Initialize views and database helper


        et_Bienso =findViewById(R.id.et_Bienso);
        et_maXacNhan =findViewById(R.id.et_maXacNhan);
        textV = findViewById(R.id.textView);
        btnLogout = findViewById(R.id.btn_logout);
        pref = getSharedPreferences("user_details", MODE_PRIVATE);
        dbHelper = new DBHelper(this);





        Spinner vehicleTypeSpinner = findViewById(R.id.vehicle_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.vehicle_types, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleTypeSpinner.setAdapter(adapter);
        vehicleTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedVehicleType = parent.getItemAtPosition(position).toString();
                // Thực hiện xử lý với giá trị loại xe đã chọn
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì khi không có loại xe nào được chọn
            }
        });

        // Get User object from intent extras
        String userJson = getIntent().getStringExtra("acc");
        Gson gson = new Gson();
        Tai_khoan account = gson.fromJson(userJson, Tai_khoan.class);

        // Set welcome message
//        tvWelcome.setText("Xin chào: " + account.getUsername() + "!");

        //random ma xac nhan
        Random random = new Random();

        // Tạo chuỗi ngẫu nhiên 6 kí tự
        String randomString = "";
        for (int i = 0; i < 6; i++) {
            // Sinh một số nguyên ngẫu nhiên từ 0 đến 25, sau đó chuyển đổi thành ký tự chữ cái
            char c = (char) (random.nextInt(26) + 'a');
            randomString += c;
        }
        String rand = randomString;

        // Hiển thị chuỗi ngẫu nhiên lên TextView
        textV.setText(rand);

        // Set click listener for logout button
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear shared preferences
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.apply();
                String maXN = et_maXacNhan.getText().toString().trim();
                String maxacnhan= textV.getText().toString().trim();
                if(!maxacnhan.equals(maXN)){
                    textV.setError("Verification does not match");
                    return;
                }

                String bienSo = et_Bienso.getText().toString();
                String loaiXe = vehicleTypeSpinner.getSelectedItem().toString();

                Bien_ban bienBan = dbHelper.getBienBanByBienSoAndLoaiXe(bienSo, loaiXe);
                if (bienBan == null) {
                    // Hiển thị thông báo toast khi kết quả tra cứu là null
                    Toast.makeText(TraCuu.this, "Không tìm thấy biên bản", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(TraCuu.this, chi_tiet_bb.class);
                    intent.putExtra("bien_so", bienSo);
                    intent.putExtra("loai_xe", loaiXe);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
    private List<Photo> getListPhoto(){

        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.anh2));
        list.add(new Photo(R.drawable.anh1));
        list.add(new Photo(R.drawable.anh3));
        list.add(new Photo(R.drawable.anh4));
        list.add(new Photo(R.drawable.anh5));

        return list;
    }
    private void autoSlideImages(){
        if(mListPhoto == null || mListPhoto.isEmpty() || viewPager == null){
            return;
        }
        if (mTimer == null){
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = mListPhoto.size() -1;
                        if(currentItem<totalItem){
                            currentItem++;
                            viewPager.setCurrentItem(currentItem);
                        }
                        else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        },1000,4000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mTimer != null) {
            mTimer.cancel();
            mTimer=null;
        }
    }
}
