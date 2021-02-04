package com.FoodShop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import Models.MenuModel;
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class ResMenu extends Fragment {

    String name="";

    MenuModel m1= new MenuModel("Burrito",17);
    MenuModel m2= new MenuModel("Burrito bowl",12);
    MenuModel m3= new MenuModel("Mexican pizza",22);
    MenuModel m4= new MenuModel("Salad",8);
MenuModel[] mlist={m1,m2,m3,m4};
    private AdapterView.OnItemClickListener mListener;

    public static ResMenu newInstance() {
        ResMenu fragment = new ResMenu();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
name=bundle.getString("name");
        return inflater.inflate(R.layout.fragment_res_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView title= view.findViewById(R.id.menuTitle);
        final CircularProgressButton checkoutBtn= view.findViewById(R.id.checkoutBtn);
        RecyclerView lst=view.findViewById(R.id.menuListView);
        title.setText(name +" Menu");
MenuAdapter menuAdapter=new MenuAdapter(getContext(),mlist);
        lst.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
lst.setAdapter(menuAdapter);
        lst.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), lst ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                                Count.menucount++;
                                Toast.makeText(getContext(), name +" Added to cart", Toast.LENGTH_SHORT).show();

                        if (Count.menucount>0) checkoutBtn.setHint("Checkout" +" ("+Count.menucount+")");
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(),Cart.class);
                startActivity(intent);
            }
        });





    }
}