package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.FoodShop.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import Models.MenuModel;
import Models.CheckoutModel;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyviewHolder> {
    private final Context context;
    private final ArrayList<MenuModel> menulist;

    public MenuAdapter(Context context, ArrayList<MenuModel> mlist) {
        this.context = context;
        this.menulist = mlist;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_list_item, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, final int position) {
        final String name = menulist.get(position).getName();
        holder.txtname.setText(name);
        holder.txtprice.setText("$" + menulist.get(position).getPrice());

        final double originalPrice = menulist.get(position).getPrice();

        // add item button
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // counts how many items are in the cart
                //Count.menucount++;

                // qty
                int q = menulist.get(position).getQuantity();
                q += 1;
                menulist.get(position).setQuantity(q);
                Toast.makeText(context, q + " item(s) of " + name + " added to cart", Toast.LENGTH_SHORT).show();

                // price
                double p = 0;
                if(q > 0) {
                    p = originalPrice * q;
                    menulist.get(position).setPrice(p);
                }
                //Toast.makeText(context, "$" + p, Toast.LENGTH_SHORT).show();

            }
        });
        Glide.with(context).load(menulist.get(position).getImage_url()).encodeQuality(40).into(holder.food_image);
    }

    @Override
    public int getItemCount() {
        return menulist.size();
    }
    public ArrayList<MenuModel> getMenuList() {return menulist;}
    // layout display restaurant menu
    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView txtname, txtprice;
        ImageView add, food_image;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            txtname = itemView.findViewById(R.id.menuItemName);
            txtprice = itemView.findViewById(R.id.menuItemPrice);
            add = itemView.findViewById(R.id.menuItemAdd);
            food_image = itemView.findViewById(R.id.food_image);
        }
    }
}