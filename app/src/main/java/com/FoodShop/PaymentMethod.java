package com.FoodShop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PaymentMethod extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        CircularProgressButton editBtn =findViewById(R.id.payEditBtn);
        CircularProgressButton PlaceBtn =findViewById(R.id.payContinueBtn);

        // get ids
        Bundle extras = this.getIntent().getExtras();
        final int userId = extras.getInt("userId");
        final int orderId = extras.getInt("orderId");

        // edit order
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PaymentMethod.this,CartActivity.class);

                // send ids to cart activity
                Bundle bundle = new Bundle();
                bundle.putInt("userId", userId);
                bundle.putInt("orderId", orderId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        // place order
        PlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PaymentMethod.this, PaymentDetails.class);

                // send ids to payment details
                Bundle bundle = new Bundle();
                bundle.putInt("userId", userId);
                bundle.putInt("orderId", orderId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}