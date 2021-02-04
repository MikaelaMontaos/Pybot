package com.FoodShop;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(2000);

                            Intent intent= new Intent(SplashScreen.this,Sign.class);
                            startActivity(intent);
                            finish();







                } catch (InterruptedException e) {


                }
            }
        });
        thread.start();




    }

}
