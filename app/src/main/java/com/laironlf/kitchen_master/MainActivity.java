
//          ▐▄▄▄▄▄▄ .▄ •▄  ▄▄▄·    ▄▄▄·  ▐ ▄ ·▄▄▄▄    ▄▄▌   ▄▄▄· ▪  ▄▄▄         ▐ ▄
//          ▪·██▀▄.▀·█▌▄▌▪▐█ ▀█   ▐█ ▀█ •█▌▐███· ██   ██•  ▐█ ▀█ ██ ▀▄ █· ▄█▀▄ •█▌▐█
//         ▪▄ ██▐▀▀▪▄▐▀▀▄·▄█▀▀█   ▄█▀▀█ ▐█▐▐▌▐█▪ ▐█▌  ██ ▪ ▄█▀▀█ ▐█·▐▀▀▄ ▐█▌.▐▌▐█▐▐▌
//         ▐▌▐█▌▐█▄▄▌▐█.█▌▐█▪ ▐▌  ▐█▪ ▐▌██▐█▌██. ██   ▐█▌ ▄▐█▪ ▐▌▐█▌▐█•█▌▐█▌.▐▌██▐█▌
//          ▀▀▀• ▀▀▀ ·▀  ▀ ▀  ▀    ▀  ▀ ▀▀ █▪▀▀▀▀▀•   .▀▀▀  ▀  ▀ ▀▀▀.▀  ▀ ▀█▄▀▪▀▀ █▪

package com.laironlf.kitchen_master;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;


import com.google.android.material.appbar.AppBarLayout;
import com.laironlf.kitchen_master.DB.DB;
import com.laironlf.kitchen_master.DB.UserProducts;
import com.laironlf.kitchen_master.circle_menu.AppCircleNavigation;
import com.laironlf.kitchen_master.databinding.ActivityMainNavBinding;

public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainNavBinding binding;
    private DrawerLayout drawer;
//    private RelativeLayout menu;
    private NavController navController;
    private AppCircleNavigation appCircleNavigation;
    private AppBarLayout btn_menu;

    private static final int SWIPE_THRESHOLD = 100;
    private float initialX;
    private boolean isDrawerOpen;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Menu menu = new PopupMenu(this, null).getMenu();
        getMenuInflater().inflate(R.menu.activity_main_nav_drawer, menu);

        for(int i = 0; i < menu.size(); i++)
            Log.d("Menu:", String.valueOf(menu.getItem(i).getTitle()));

        drawer = binding.drawerLayout;
        AppCircleNavigation.createCircleMenu(drawer, this, menu);

        btn_menu = binding.appBarMain.btnMenu;
//        btn_menu.setOnClickListener(view -> appCircleNavigation.openDrawer(GravityCompat.START));
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            return;
        }
        Toast.makeText(this, "BACK", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        UserProducts.writeData();
        DB.close();
        super.onDestroy();
    }

}