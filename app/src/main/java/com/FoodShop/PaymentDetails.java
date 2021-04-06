package com.FoodShop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PaymentDetails extends AppCompatActivity {

    // text field
    EditText card_num, name, exp, cvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        Button pay = findViewById(R.id.payDetailPlaceBtn);

        // text field id
        card_num = findViewById(R.id.et_cardnum);
        name = findViewById(R.id.et_name);
        exp = findViewById(R.id.et_exp);
        cvv = findViewById(R.id.et_cvv);

        // get ids
        Bundle extras = this.getIntent().getExtras();
        final int userId = extras.getInt("userId");
        final int orderId = extras.getInt("orderId");

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get info for post request
                String card_numS = card_num.getText().toString();
                String nameS = name.getText().toString();
                String expS = exp.getText().toString();
                String cvvS = cvv.getText().toString();

                // if form is not empty
                if(!nameS.isEmpty() && !expS.isEmpty() && !cvvS.isEmpty()) {

                    // connect to server
                    OkHttpClient okHttpClient = new OkHttpClient();

                    // todo: test
                    // post
                    RequestBody formBody = new FormBody.Builder()
                            .add("order_id", String.valueOf(orderId))
                            .add("card_num", card_numS)
                            .add("name_on_card", nameS)
                            .add("exp_date", expS)
                            .add("cvv", cvvS)
                            .build();
                    Request request = new Request.Builder().url("http://10.0.2.2:5000/payment")
                            .post(formBody).build();

                    // async call to connect
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull final IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(PaymentDetails.this, "Cannot connect to server. Please try again.", Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(PaymentDetails.this, "Payment successful", Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(PaymentDetails.this, Track.class);

                                    Bundle bundle = new Bundle();
                                    bundle.putInt("userId", userId);
                                    intent.putExtras(bundle);

                                    startActivity(intent);
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}