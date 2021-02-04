package com.FoodShop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Sign extends AppCompatActivity {

Button btnsignin,btnsignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        btnsignin = findViewById(R.id.btnLogin);
        btnsignup = findViewById(R.id.btnRegister);


            btnsignin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Sign.this, Login.class);
                    startActivity(intent);
                }
            });
            btnsignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Sign.this, Register.class);
                    startActivity(intent);
                }
            });

        }


    }
