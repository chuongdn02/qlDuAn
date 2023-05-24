package com.jovanovic.stefan.sqlitetutorial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import com.stripe.android.view.CardInputWidget;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    private Button button;
    private Stripe stripe;
    private String PublishableKey = "pk_test_51N9EnzJ1BZGwt3qhXXRaNzkmK1q1NuLaZAn0eeqFU9tS6XR7sYMDWRX9BcKzfVuEirUC1XBD27gpoaI0meKIQYzZ00BgNnS1No";
    private String SecretKey = "sk_test_51N9EnzJ1BZGwt3qh4XlBjQpqTpMFn2EU9ebV53L1YrSY1X3eytMid50Q7X9rWmwi9odN4ylgWSRtgwiPota8B73e00qpLwOK0Z";
    String CustomerId;
    String EphericalKey;
    private String ClientSecret;

    private PaymentSheet paymentSheet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        button = findViewById(R.id.pay);

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

                            Toast.makeText(PaymentActivity.this,CustomerId,Toast.LENGTH_SHORT).show();

                            getEmphericalKey();

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(PaymentActivity.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

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

                            Toast.makeText(PaymentActivity.this,CustomerId,Toast.LENGTH_SHORT).show();

                            getClientSecret(CustomerId,EphericalKey);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(PaymentActivity.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

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

                            Toast.makeText(PaymentActivity.this,ClientSecret,Toast.LENGTH_SHORT).show();



                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(PaymentActivity.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

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
                params.put("amount", "500"+"00");
                params.put("currency", "USD");
                params.put("automatic_payment_methods[enabled]", "true");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }


    }





