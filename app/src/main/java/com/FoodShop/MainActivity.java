package com.FoodShop;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.Slide;

import com.fragments.ResMenuFragment;
import com.fragments.RestaurantFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    NavigationView navview;
    DrawerLayout drawerLayout;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RestaurantFragment restaurantFragment = new RestaurantFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mainFrame, restaurantFragment);

        // get user id
        Bundle uExtras = this.getIntent().getExtras();
        int userId = uExtras.getInt("userId");
        //Toast.makeText(MainActivity.this, "user id " + userId, Toast.LENGTH_SHORT).show();

        // send user id to RestaurantFragment
        Bundle bundle = new Bundle();
        bundle.putInt("userId", userId);
        restaurantFragment.setArguments(uExtras);

        fragmentTransaction.commit();

        navview = findViewById(R.id.navview);
        drawerLayout = findViewById(R.id.drawerlayout);

        navview.setItemIconTintList(null);
        navview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                int size = navview.getMenu().size();

                for (int i = 0; i < size; i++) {
                    navview.getMenu().getItem(i).setChecked(false);
                }
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        item.setChecked(true);
                        Fragment fragment = new RestaurantFragment();
                        fragment.setEnterTransition(new Slide(Gravity.BOTTOM));
                        fragment.setExitTransition(new Slide(Gravity.TOP));
                        fragmentTransaction.replace(R.id.mainFrame, fragment);
                        fragmentTransaction.commit();
                        break;
                    case R.id.nav_order:
                        item.setChecked(true);
                        fragmentTransaction.replace(R.id.mainFrame, new RestaurantFragment());
                        fragmentTransaction.commit();
                        break;
                    case R.id.nav_cart:
                        item.setChecked(true);
                        fragmentTransaction.replace(R.id.mainFrame, new ResMenuFragment());
                        fragmentTransaction.commit();
                        break;
                    case R.id.nav_history:
                        Intent intent = new Intent(MainActivity.this, CartActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_my_account:
                        item.setChecked(true);
                        fragmentTransaction.replace(R.id.mainFrame, new RestaurantFragment());
                        fragmentTransaction.commit();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this).setMessage(getString(R.string.exitmassage)).setTitle(getString(R.string.exit)).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());

                    System.exit(0);
                }
            });
            dialog.show();
        }
    }
}
