package com.jovanovic.stefan.sqlitetutorial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.jovanovic.stefan.sqlitetutorial.Handle.DBHelper;
import com.jovanovic.stefan.sqlitetutorial.Handle.TaiKhoanAdapter;
import com.jovanovic.stefan.sqlitetutorial.Model.Tai_khoan;

import java.util.List;

public class ql_tk_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TaiKhoanAdapter adapter;
    private DBHelper dbHelper;

    private EditText et_Find;
    private Button btn_add,btn_delete;

    private Spinner spinnerChucVu;
    private ArrayAdapter<CharSequence> adapterChucVu;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ql_tk);
        recyclerView = findViewById(R.id.recyclerViewAccountList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        btn_delete = findViewById(R.id.btn_delete);
        et_Find = findViewById(R.id.et_find);
        Button btnFind= findViewById(R.id.btn_find);
        spinnerChucVu = findViewById(R.id.chucvu);
        btn_add= findViewById(R.id.btn_add);


        adapterChucVu = ArrayAdapter.createFromResource(this, R.array.chucvu, android.R.layout.simple_spinner_item);
        adapterChucVu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChucVu.setAdapter(adapterChucVu);

        dbHelper = new DBHelper(this);
        List<Tai_khoan> dataList = dbHelper.getAllTaiKhoan();
        adapter = new TaiKhoanAdapter(dataList);
        recyclerView.setAdapter(adapter);
        //capTk


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ql_tk_Activity.this,CapTK.class);
                startActivity(intent);
                adapter.notifyDataSetChanged();


            }

        });


//tim kiem
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_Find.getText().toString();
                adapter.getFilter().filter(username);
            }


        });


        spinnerChucVu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    adapter.getFilter().filter("");
                } else {
                    String chucVu = adapterView.getItemAtPosition(i).toString();
                    adapter.getFilter().filter(chucVu);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    //reload data
    @Override
    protected void onResume() {
        super.onResume();
        List<Tai_khoan> dataList = dbHelper.getAllTaiKhoan();
        adapter = new TaiKhoanAdapter(dataList);
        recyclerView.setAdapter(adapter);
    }
}
