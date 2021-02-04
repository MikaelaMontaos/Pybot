package com.FoodShop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import Models.CheckoutModel;
import Models.MenuModel;

public class Cart extends AppCompatActivity {

    CheckoutModel c1= new CheckoutModel(1,"Burrito",17);
    CheckoutModel c2= new CheckoutModel(1,"Burrito bowl",12);
    CheckoutModel c3= new CheckoutModel(2,"Mexican pizza",22);
    CheckoutModel c4= new CheckoutModel(4,"Salad",8);
    CheckoutModel[] clist={c1,c2,c3,c4};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        RecyclerView lst=findViewById(R.id.cartListView);

        CheckoutAdapter menuAdapter=new CheckoutAdapter(Cart.this,clist);
        lst.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        lst.setAdapter(menuAdapter);

    }
}