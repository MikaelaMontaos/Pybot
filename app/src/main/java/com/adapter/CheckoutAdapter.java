package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.FoodShop.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import Models.CheckoutModel;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.MyviewHolder> {
    private final Context context;
    private final CheckoutModel[] checklist;

    public CheckoutAdapter(Context context, CheckoutModel[] clist) {
        this.context = context;
        this.checklist = clist;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.checkout_list_item, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyviewHolder holder, int position) {
        final CheckoutModel item = checklist[position];
        final String name = item.getName();
        holder.txtname.setText(name);
        holder.txtquantity.setText(String.valueOf(item.getQuantity()) + "x");

        if(item.getQuantity() > 0) holder.txtprice.setText("$" + item.getPrice());
        else holder.txtprice.setText("$0");

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // counts how many items are in the cart
                //Count.menucount--;

                // qty
                int q = item.getQuantity();
                if(q > 0) {
                    double temp = item.getPrice() / item.getQuantity();

                    q -= 1;
                    item.setQuantity(q);
                    holder.txtquantity.setText(String.valueOf(item.getQuantity()) + "x");

                    // price
                    double p = item.getPrice() - temp;
                    item.setPrice(p);
                    holder.txtprice.setText("$" + item.getPrice());
                    //Toast.makeText(context, "$" + p, Toast.LENGTH_SHORT).show();

                    Toast.makeText(context, "Removed 1 " + name + " from cart", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(context, "Cannot deduct quantity on items not in cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return checklist.length;
    }

    // layout display checkout
    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView txtname, txtprice, txtquantity;
        ImageView remove;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            txtname = itemView.findViewById(R.id.checkoutItemName);
            txtquantity = itemView.findViewById(R.id.checkoutItemQuantity);
            txtprice = itemView.findViewById(R.id.checkoutItemPrice);
            remove = itemView.findViewById(R.id.checkoutItemRemove);
        }
    }
}