package com.FoodShop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class Register extends AppCompatActivity {
    CircularProgressButton btnRegister;

    // text field id
    EditText email, firstName, lastName, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnRegister = findViewById(R.id.registerBtnReg);

        // text field id
        email = findViewById(R.id.et_email);
        firstName = findViewById(R.id.et_firstname);
        lastName = findViewById(R.id.et_lastname);
        password = findViewById(R.id.et_password);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get user input
                String emailS = email.getText().toString();
                String firstNameS = firstName.getText().toString();
                String lastNameS = lastName.getText().toString();
                String passwordS = password.getText().toString();
                // if not empty
                if(emailS != null && firstNameS != null && lastNameS != null && passwordS != null){
                    // connect to server
                    OkHttpClient okHttpClient = new OkHttpClient();
                    // post
                    RequestBody formBody = new FormBody.Builder()
                            .add("email", emailS)
                            .add("firstname", firstNameS)
                            .add("lastname", lastNameS)
                            .add("password", passwordS)
                            .build();
                    Request request = new Request.Builder().url("http://10.0.2.2:5000/Users").post(formBody).build();
                    // async call to connect
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull final IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Register.this, "Cannot connect to server. Please try again.", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_LONG).show();
                        }
                    });
                    // todo: MainActivity goes back to homepage

                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);

                }
                else
                    Toast.makeText(Register.this, "Please complete form", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
