package com.FoodShop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoginActivity extends AppCompatActivity {
    CircularProgressButton btnlogin;

    // text field
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnlogin = findViewById(R.id.loginbtnlogin);

        // text field id
        email = findViewById(R.id.et_email_login);
        password = findViewById(R.id.et_password_login);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get user input
                String emailS = email.getText().toString();
                String passwordS = password.getText().toString();

                // if form is not empty
                if (!emailS.isEmpty() && !passwordS.isEmpty()) {

                    // connect to server
                    OkHttpClient okHttpClient = new OkHttpClient();

                    // post
                    RequestBody formBody = new FormBody.Builder()
                            .add("email", emailS)
                            .add("password", passwordS)
                            .build();
                    Request request = new Request.Builder().url("http://10.0.2.2:5000/login").post(formBody).build();

                    // async call to connect
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull final IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "Cannot connect to server. Please try again.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (response.code() != 404 && response.code() != 403) {
                                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                                        // extract response to get user id (UserId.java)
                                        String res = null;
                                        try {
                                            res = Objects.requireNonNull(Objects.requireNonNull(response.body()).string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        UserId userId = new UserId(res);
                                        int user_id = userId.getId();
                                        //Toast.makeText(LoginActivity.this, "user id " + user_id, Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                        // send user id to MainActivity
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("userId", user_id);
                                        intent.putExtras(bundle);

                                        startActivity(intent);
                                    }
                                    else Toast.makeText(LoginActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                } else
                    Toast.makeText(LoginActivity.this, "Please complete form", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
