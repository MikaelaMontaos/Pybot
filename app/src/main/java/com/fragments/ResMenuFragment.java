package com.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.FoodShop.CartActivity;
import com.FoodShop.MainActivity;
import com.FoodShop.R;
import com.adapter.MenuAdapter;

import java.util.ArrayList;

import Models.CheckoutModel;
import Models.MenuModel;
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class ResMenuFragment extends Fragment {
    String name = "";
    int position;

    private AdapterView.OnItemClickListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        name = bundle.getString("name");
        position = bundle.getInt("position");

        return inflater.inflate(R.layout.fragment_res_menu, container, false);
    }

    public int getUserId() {
        return getArguments().getInt("userId");
    }
    ArrayList<MenuModel> menuList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView title = view.findViewById(R.id.menuTitle);
        final CircularProgressButton checkoutBtn = view.findViewById(R.id.checkoutBtn);
        RecyclerView lst = view.findViewById(R.id.menuListView);
        title.setText(name + " Menu");

        // MenuAdapter shows the menu but this page is still active => takes user to cart page
        final MenuAdapter menuAdapter = new MenuAdapter(getContext(), getMenuModelArrayList(position));
        lst.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        lst.setAdapter(menuAdapter);

        menuList = menuAdapter.getMenuList();
//        // overrides add item button in MenuAdapter
//        lst.addOnItemTouchListener(
//                new RecyclerItemClickListener(getContext(), lst, new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        //Toast.makeText(getContext(), "user id " + getUserId(), Toast.LENGTH_SHORT).show();
//
//                        // this menucount is for the whole order not for each item
//                        Count.menucount++;
//                        // says chipotle added to cart
//                        //Toast.makeText(getContext(), name + " added to cart", Toast.LENGTH_SHORT).show();
//                        if (Count.menucount > 0)
//                            checkoutBtn.setHint("Checkout" + " (" + Count.menucount + ")");
//                    }
//                    @Override
//                    public void onLongItemClick(View view, int position) {
//                    }
//                })
//        );



        // go to cart page when clicking checkout button
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CartActivity.class);

                // send user id to CartActivity
                Bundle bundle = new Bundle();
                bundle.putInt("userId", getUserId());

                // send ArrayList checkout items to CartActivity
                intent.putParcelableArrayListExtra("items", menuList);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }

    // 1 = chipotle, 2 = dominos, 3 = panda
    ArrayList<MenuModel> getMenuModelArrayList(int position) {
        ArrayList<MenuModel> menuModels = new ArrayList<>();
        if (position == 1) {

            // MenuAdapter depends on this info to display the menu
            menuModels.add(new MenuModel(1, "Burrito", 0, 17, "https://i.ibb.co/68v6tsB/image-from-rawpixel-id-448073-jpeg.jpg"));
            menuModels.add(new MenuModel(2, "Burrito bowl", 0, 12, "https://i.ibb.co/KqHRcHp/image-from-rawpixel-id-2452144-png.png"));
            menuModels.add(new MenuModel(3, "Mexican pizza", 0, 22, "https://i.ibb.co/BNg81FT/image-from-rawpixel-id-444752-jpeg.jpg"));

        }

        // dominos and panda go back to previous page
        else if (position == 2) {

            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            Toast.makeText(getContext(), "Coming soon", Toast.LENGTH_LONG).show();

            /* replace images to royalty free if including this menu
            menuModels.add(new MenuModel("Pasta Primavera", 15, "https://www.twopeasandtheirpod.com/wp-content/uploads/2019/05/Pasta-Primavera-3.jpg"));
            menuModels.add(new MenuModel("Italian Sandwich", 10, "https://cutpcdnwimages.azureedge.net/images/products/95000/098716-600x600-A.jpg"));
            menuModels.add(new MenuModel("Pacific Veggie pizza", 30, "https://wellnessdove.com/wp-content/uploads/2020/06/Whole-Wheat-Veggie-Pizza-7.jpg"));
             */

        }
        else if (position == 3) {

            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            Toast.makeText(getContext(), "Coming soon", Toast.LENGTH_LONG).show();

            /* replace images to royalty free if including this menu
            menuModels.add(new MenuModel("Chow Mein", 20, "https://i2.wp.com/hipfoodiemom.com/wp-content/uploads/2015/02/chow-mein-chopsticks_4856-12.jpg"));
            menuModels.add(new MenuModel("Fried Rice", 15, "https://30minutesmeals.com/wp-content/uploads/2019/12/Shrimp-Fried-Rice-Recipe-3.jpg.webp"));
            menuModels.add(new MenuModel("Cheese Rangoon", 10, "https://dinnerthendessert.com/wp-content/uploads/2018/04/Crab-Rangoon-2.jpg"));
             */

        }
        return menuModels;
    }
}