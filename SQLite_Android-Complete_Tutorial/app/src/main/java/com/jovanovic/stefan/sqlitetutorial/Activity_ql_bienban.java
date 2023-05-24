package com.jovanovic.stefan.sqlitetutorial;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jovanovic.stefan.sqlitetutorial.Function.Lap_bien_ban;
import com.jovanovic.stefan.sqlitetutorial.Handle.BienBanAdapter;
import com.jovanovic.stefan.sqlitetutorial.Handle.DBHelper;
import com.jovanovic.stefan.sqlitetutorial.Handle.TaiKhoanAdapter;
import com.jovanovic.stefan.sqlitetutorial.Model.Bien_ban;
import com.jovanovic.stefan.sqlitetutorial.Model.Tai_khoan;

import java.util.List;

public class Activity_ql_bienban extends AppCompatActivity {

    private EditText etFind;
    private Button btnFind;
    private static final int REQUEST_CODE_LAP_BIEN_BAN = 1;

    private Spinner spinnerKhuVuc, spinnerLoaiXe;
    private Button btnAdd;
    private RecyclerView recyclerView;

    private BienBanAdapter adapter;
    private List<Bien_ban> dataList;

    private DBHelper dbHelper;

    private String idCanBo;
    private ArrayAdapter<CharSequence> adapterLoaiXe;
    private ArrayAdapter<CharSequence> adapterKhuVuc;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ql_bienban);
        recyclerView = findViewById(R.id.recyclerV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        etFind = findViewById(R.id.et_find);
        btnFind = findViewById(R.id.btn_find);
        spinnerKhuVuc = findViewById(R.id.khu_vuc);
        spinnerLoaiXe = findViewById(R.id.loai_xe);
        btnAdd = findViewById(R.id.btn_add);

        adapterKhuVuc = ArrayAdapter.createFromResource(this, R.array.khuVuc, android.R.layout.simple_spinner_item);
        adapterKhuVuc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKhuVuc.setAdapter(adapterKhuVuc);

        adapterLoaiXe = ArrayAdapter.createFromResource(this, R.array.loaiXe, android.R.layout.simple_spinner_item);
        adapterLoaiXe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoaiXe.setAdapter(adapterLoaiXe);

        dbHelper = new DBHelper(this);
        dataList = dbHelper.getAllBienBan();
        adapter = new BienBanAdapter(dataList, position -> {
            Bien_ban selectedBienBan = dataList.get(position);
            // Handle item click event
            // ...
        });

        adapter = new BienBanAdapter(dataList, position -> {
            Bien_ban selectedBienBan = dataList.get(position);
            // Handle item click event
            openDetailPage(selectedBienBan);
        });

        recyclerView.setAdapter(adapter);



        String userJson = getIntent().getStringExtra("acc");
        Gson gson = new Gson();
        Tai_khoan account = gson.fromJson(userJson, Tai_khoan.class);
        if (account != null) {
            idCanBo = account.getCccd().toString().trim();
        } else {
            idCanBo = getIntent().getStringExtra("idCB2");
            // Xử lý khi không nhận được đối tượng Tai_khoan từ Intent
            // Ví dụ: Hiển thị thông báo lỗi hoặc thực hiện hành động khác
        }


        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_ql_bienban.this, Lap_bien_ban.class);
            intent.putExtra("acc", new Gson().toJson(account));
            intent.putExtra("id", idCanBo);
            startActivity(intent);
            adapter.notifyDataSetChanged();
        });

        btnFind.setOnClickListener(view -> {
            String keyword = etFind.getText().toString();
            adapter.getFilter().filter(keyword);
        });

        spinnerKhuVuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    adapter.getFilter().filter("");
                } else {
                    String selectedKhuVuc = adapterView.getItemAtPosition(i).toString();
                    adapter.getFilter().filter(selectedKhuVuc);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_ql_bienban.this, Lap_bien_ban.class);
            intent.putExtra("acc", new Gson().toJson(account));
            intent.putExtra("id", idCanBo);
            startActivityForResult(intent, REQUEST_CODE_LAP_BIEN_BAN);
        });

        spinnerLoaiXe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    adapter.getFilter().filter("");
                } else {
                    String selectedLoaiXe = adapterView.getItemAtPosition(i).toString();
                    adapter.getFilter().filter(selectedLoaiXe);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });

        adapter.setOnItemLongClickListener(position -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Activity_ql_bienban.this);
            builder.setTitle("Xóa biên bản");
            builder.setMessage("Bạn có chắc chắn muốn xóa biên bản này?");
            builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Get the selected bien ban
                    Bien_ban selectedBienBan = dataList.get(position);

                    // Delete the selected bien ban from the database
                    dbHelper.deleteBienBan(selectedBienBan.getId());

                    // Remove the selected bien ban from the adapter
                    adapter.deleteItem(position);
                }
            });
            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.create().show();
        });


    }
    private void openDetailPage(Bien_ban bienBan) {

        String bienBanJson = new Gson().toJson(bienBan);
        Intent intent = new Intent(Activity_ql_bienban.this, Sua_bien_ban.class);
        intent.putExtra("bienBan", bienBanJson);
        intent.putExtra("idCB",idCanBo);
        startActivity(intent);
    }
    //reload data
    public void reloadData() {
        dataList.clear();
        dataList.addAll(dbHelper.getAllBienBan());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadData();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LAP_BIEN_BAN && resultCode == RESULT_OK) {
            reloadData();
        }
    }


}
