  package com.FoodShop;

  import android.content.Context;
  import android.view.LayoutInflater;
  import android.view.View;
  import android.view.ViewGroup;
  import android.widget.ImageView;
  import android.widget.TextView;
  import android.widget.Toast;

  import androidx.annotation.NonNull;
  import androidx.recyclerview.widget.RecyclerView;

  import Models.CheckoutModel;
  import Models.MenuModel;

  class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.MyviewHolder> {


    private final Context context;
    private final CheckoutModel[] checklist;

    public CheckoutAdapter(Context context, CheckoutModel[] clist) {
        this.context = context;
        this.checklist = clist;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.checkout_list_item,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        CheckoutModel item= checklist[position];
       final String name= item.getName();
        holder.txtname.setText(name);
        holder.txtquantity.setText(String.valueOf(item.getQuantity())+"x");
        holder.txtprice.setText("$"+item.getPrice());

     holder.remove.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Count.menucount++;
             Toast.makeText(context, name +"Removed from cart", Toast.LENGTH_SHORT).show();

         }
     });
    }

    @Override
    public int getItemCount() {
        return checklist.length;
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{
        TextView txtname,txtprice, txtquantity;
    ImageView remove;




    public MyviewHolder(@NonNull View itemView) {
        super(itemView);

        txtname=itemView.findViewById(R.id.checkoutItemName);
        txtquantity=itemView.findViewById(R.id.checkoutItemQuantity);
         txtprice=itemView.findViewById(R.id.checkoutItemPrice);
         remove= itemView.findViewById(R.id.checkoutItemRemove);




    }
}




    }