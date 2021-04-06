package com.FoodShop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adapter.CheckoutAdapter;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

import Models.CheckoutModel;

import Models.MenuModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CartActivity extends AppCompatActivity {

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        RecyclerView lst = findViewById(R.id.cartListView);
        Button paybtn = findViewById(R.id.payBtn1);

        // text view id
        TextView subtotal = (TextView) findViewById(R.id.tv_subtotal);
        TextView delivery = (TextView) findViewById(R.id.tv_delivery_fee);
        TextView tax = (TextView) findViewById(R.id.tv_tax);
        final TextView total = (TextView) findViewById(R.id.tv_total);
        final EditText address = (EditText) findViewById(R.id.delivery_addr);

        // get user id and menulist
        Bundle extras = this.getIntent().getExtras();
        final int userId = extras.getInt("userId");
        //Toast.makeText(CartActivity.this, "user id " + userId, Toast.LENGTH_SHORT).show();
        ArrayList<MenuModel> menuList = getIntent().getParcelableArrayListExtra("items");
        System.out.println(menuList.get(0));
        final CheckoutModel[] clist = new CheckoutModel[menuList.size()];
        for (int i = 0; i < menuList.size(); i++)
            clist[i] = (new CheckoutModel(menuList.get(i)));

        // calculate total and automatically display
        double sub = 0;
        for (int i = 0; i < clist.length; i++) {
            if (clist[i].getQuantity() > 0) sub += clist[i].getPrice();
        }
        subtotal.setText("$" + df2.format(sub));
        double delivery_fee = 3.99;
        delivery.setText("$" + df2.format(delivery_fee));
        double taxD = sub * 0.0725;
        tax.setText("$" + df2.format(taxD));
        final double totalD = sub + delivery_fee + taxD;
        total.setText("$" + df2.format(totalD));

        final double finalSub = sub;
        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // connect to server
                OkHttpClient okHttpClient = new OkHttpClient();

                // info for post request
                String[] item_id = new String[clist.length];
                for (int i = 0; i < clist.length; i++)
                    item_id[i] = Integer.toString(clist[i].getId());
                String[] item_price = new String[clist.length];
                for (int i = 0; i < clist.length; i++)
                    item_price[i] = String.valueOf(clist[i].getPrice());
                String[] item_qty = new String[clist.length];
                for (int i = 0; i < clist.length; i++)
                    item_qty[i] = Integer.toString(clist[i].getQuantity());
                String addressS = address.getText().toString();

                JSONObject jsonObject = new JSONObject();
                for (int i = 0; i < clist.length; i++) {
                    JSONArray jsArray = new JSONArray();
                    jsArray.put(clist[i].getId());
                    jsArray.put(clist[i].getQuantity());
                    try {
                        jsonObject.put("item"+(i+1), jsArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                // post request if checking out at least one item
                if (finalSub > 0 && !addressS.isEmpty()) {


                    RequestBody formBody = new FormBody.Builder()
                            .add("user_id", String.valueOf(userId))
                            .add("total", String.valueOf(totalD))
                            .add("item_detail", String.valueOf(jsonObject))
                            .add("delivery_address", addressS)
                            .build();
                    Request request = new Request.Builder().url("http://10.0.2.2:5000/order")
                            .post(formBody).build();

                    // async call to connect

                    okHttpClient.newCall(request).enqueue(new Callback() {

                        @Override
                        public void onFailure(@NotNull Call call, @NotNull final IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CartActivity.this, "Cannot connect to server. Please try again.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        @Override
                        public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // extract response if added all orders

                                    String res = null;

                                    Intent intent = new Intent(CartActivity.this, PaymentMethod.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("userId", userId);


                                    try {
                                        res = Objects.requireNonNull(Objects.requireNonNull(response.body()).string());
                                        bundle.putInt("orderId", Integer.parseInt(res));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            });
                        }
                    });
                }
                else if(addressS.isEmpty())
                    Toast.makeText(CartActivity.this, "Please add delivery address", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(CartActivity.this, "Empty cart. Please add items before paying.", Toast.LENGTH_SHORT).show();
            }
        });
        CheckoutAdapter menuAdapter = new CheckoutAdapter(CartActivity.this, clist);
        lst.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        lst.setAdapter(menuAdapter);
    }
}
