package com.FoodShop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SigninActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        Button btnsignin = findViewById(R.id.btnLogin);
        Button btnsignup = findViewById(R.id.btnRegister);


            btnsignin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SigninActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
            btnsignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SigninActivity.this, Register.class);
                    startActivity(intent);
                }
            });

        }


    }
