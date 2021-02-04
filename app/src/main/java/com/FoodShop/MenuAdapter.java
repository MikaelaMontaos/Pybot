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


  import Models.MenuModel;

  class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyviewHolder> {


    private final Context context;
    private final MenuModel[] menulist;

    public MenuAdapter(Context context, MenuModel[] mlist) {
        this.context = context;
        this.menulist = mlist;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.menu_list_item,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
       final String name= menulist[position].getName();
        holder.txtname.setText(name);
        holder.txtprice.setText("$"+menulist[position].getPrice());
     holder.add.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Count.menucount++;
             Toast.makeText(context, name +" Added to cart", Toast.LENGTH_SHORT).show();

         }
     });
    }

    @Override
    public int getItemCount() {
        return menulist.length;
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{
        TextView txtname,txtprice;
    ImageView add;




    public MyviewHolder(@NonNull View itemView) {
        super(itemView);

        txtname=itemView.findViewById(R.id.menuItemName);
         txtprice=itemView.findViewById(R.id.menuItemPrice);
         add= itemView.findViewById(R.id.menuItemAdd);




    }
}




    }