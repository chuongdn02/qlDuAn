package com.jovanovic.stefan.sqlitetutorial;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jovanovic.stefan.sqlitetutorial.Handle.DBHelper;
import com.jovanovic.stefan.sqlitetutorial.Model.Bien_ban;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class chi_tiet_bb extends AppCompatActivity {

    private TextView tvCCCD;
    private TextView tvDiaDiem;
    private TextView tvLoaiXe;
    private TextView tvNoiDung;
    private TextView tvLoaiViPham;
    private TextView tvSoTienPhat;
    private TextView tvIdCanBo;
    private  String tienphat;
    private Button button;
    private Stripe stripe;
    private String PublishableKey = "pk_test_51N9EnzJ1BZGwt3qhXXRaNzkmK1q1NuLaZAn0eeqFU9tS6XR7sYMDWRX9BcKzfVuEirUC1XBD27gpoaI0meKIQYzZ00BgNnS1No";
    private String SecretKey = "sk_test_51N9EnzJ1BZGwt3qh4XlBjQpqTpMFn2EU9ebV53L1YrSY1X3eytMid50Q7X9rWmwi9odN4ylgWSRtgwiPota8B73e00qpLwOK0Z";
    private String CustomerId;
    private String EphericalKey;
    private String ClientSecret;
    private PaymentSheet paymentSheet;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_bb);

        tvCCCD = findViewById(R.id.tv_cccd);
        tvDiaDiem = findViewById(R.id.tv_vitri);
        tvLoaiXe = findViewById(R.id.tv_loaixe);
        tvNoiDung = findViewById(R.id.tv_bienso);
        tvLoaiViPham = findViewById(R.id.tv_loaivipham);
        tvSoTienPhat = findViewById(R.id.tv_sotienphat);
        tvIdCanBo = findViewById(R.id.tv_idcanbo);
        button = findViewById(R.id.payButton);

        //payment


//      end pay

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

         tienphat = bienBan.getSoTienPhat().toString().trim();

        //payment

        PaymentConfiguration.init(this, PublishableKey);

        paymentSheet = new PaymentSheet(this,paymentSheetResult ->{

            onPaymentResult(paymentSheetResult);

        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentFlow();
            }
        });

        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/customers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            CustomerId = object.getString("id");

                            Toast.makeText(chi_tiet_bb.this,CustomerId,Toast.LENGTH_SHORT).show();

                            getEmphericalKey();

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(chi_tiet_bb.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer "+ SecretKey);

                return  header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


//      end pay

    }

    private void paymentFlow() {

        paymentSheet.presentWithPaymentIntent(ClientSecret,new PaymentSheet.Configuration("Nguyen Van Chuong",
                new PaymentSheet.CustomerConfiguration(CustomerId,EphericalKey))) ;

    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {

        if (paymentSheetResult instanceof  PaymentSheetResult.Completed){
            Toast.makeText(this,"Thanh Toán Thành Công",Toast.LENGTH_SHORT).show();
        }

    }
    //-----------------------------------------

    private void getEmphericalKey() {

        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            EphericalKey = object.getString("id");

//                            Toast.makeText(chi_tiet_bb.this,CustomerId,Toast.LENGTH_SHORT).show();

                            getClientSecret(CustomerId,EphericalKey);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                Toast.makeText(chi_tiet_bb.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer "+ SecretKey);
                header.put("Stripe-Version", "2022-11-15");
                return  header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params =  new HashMap<>();
                params.put("customer", CustomerId);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
    //----------------------------------
    private void getClientSecret(String customerId, String ephericalKey) {

        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            ClientSecret =object.getString("client_secret");
//
//                            Toast.makeText(chi_tiet_bb.this,ClientSecret,Toast.LENGTH_SHORT).show();



                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                Toast.makeText(chi_tiet_bb.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer "+ SecretKey);

                return  header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params =  new HashMap<>();
                params.put("customer", CustomerId);
                params.put("amount", tienphat +"000");
                params.put("currency", "VND");
                params.put("automatic_payment_methods[enabled]", "true");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}