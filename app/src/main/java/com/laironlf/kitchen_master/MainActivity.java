
//          ▐▄▄▄▄▄▄ .▄ •▄  ▄▄▄·    ▄▄▄·  ▐ ▄ ·▄▄▄▄    ▄▄▌   ▄▄▄· ▪  ▄▄▄         ▐ ▄
//          ▪·██▀▄.▀·█▌▄▌▪▐█ ▀█   ▐█ ▀█ •█▌▐███· ██   ██•  ▐█ ▀█ ██ ▀▄ █· ▄█▀▄ •█▌▐█
//         ▪▄ ██▐▀▀▪▄▐▀▀▄·▄█▀▀█   ▄█▀▀█ ▐█▐▐▌▐█▪ ▐█▌  ██ ▪ ▄█▀▀█ ▐█·▐▀▀▄ ▐█▌.▐▌▐█▐▐▌
//         ▐▌▐█▌▐█▄▄▌▐█.█▌▐█▪ ▐▌  ▐█▪ ▐▌██▐█▌██. ██   ▐█▌ ▄▐█▪ ▐▌▐█▌▐█•█▌▐█▌.▐▌██▐█▌
//          ▀▀▀• ▀▀▀ ·▀  ▀ ▀  ▀    ▀  ▀ ▀▀ █▪▀▀▀▀▀•   .▀▀▀  ▀  ▀ ▀▀▀.▀  ▀ ▀█▄▀▪▀▀ █▪

package com.laironlf.kitchen_master;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainNavBinding binding = ActivityMainNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Menu menu = new PopupMenu(this, null).getMenu();
        getMenuInflater().inflate(R.menu.activity_main_nav_drawer, menu);

        // Создаём менюшку получается
        AppCircleNavigation.createCircleMenu(binding.drawerLayout, this, menu, binding.toolbar.getRoot());
    }

    @Override
    public void onBackPressed() {
        if(AppCircleNavigation.isDrawerOpen()){
            AppCircleNavigation.closeDrawer();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        UserProducts.writeData();
//        DB.close();
        DB.closeConnectionToDataBase.start();
        try {
            DB.closeConnectionToDataBase.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        super.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        AppCircleNavigation.DrawerLayoutGestures.getGestures().onTouch(ev);
//        Log.d(TAG, "dispatchTouchEvent: state " + b);
        return super.dispatchTouchEvent(ev);
    }
}