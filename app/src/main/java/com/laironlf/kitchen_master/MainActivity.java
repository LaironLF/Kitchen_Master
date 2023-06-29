
//          ▐▄▄▄▄▄▄ .▄ •▄  ▄▄▄·    ▄▄▄·  ▐ ▄ ·▄▄▄▄    ▄▄▌   ▄▄▄· ▪  ▄▄▄         ▐ ▄
//          ▪·██▀▄.▀·█▌▄▌▪▐█ ▀█   ▐█ ▀█ •█▌▐███· ██   ██•  ▐█ ▀█ ██ ▀▄ █· ▄█▀▄ •█▌▐█
//         ▪▄ ██▐▀▀▪▄▐▀▀▄·▄█▀▀█   ▄█▀▀█ ▐█▐▐▌▐█▪ ▐█▌  ██ ▪ ▄█▀▀█ ▐█·▐▀▀▄ ▐█▌.▐▌▐█▐▐▌
//         ▐▌▐█▌▐█▄▄▌▐█.█▌▐█▪ ▐▌  ▐█▪ ▐▌██▐█▌██. ██   ▐█▌ ▄▐█▪ ▐▌▐█▌▐█•█▌▐█▌.▐▌██▐█▌
//          ▀▀▀• ▀▀▀ ·▀  ▀ ▀  ▀    ▀  ▀ ▀▀ █▪▀▀▀▀▀•   .▀▀▀  ▀  ▀ ▀▀▀.▀  ▀ ▀█▄▀▪▀▀ █▪

package com.laironlf.kitchen_master;

import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;


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
        return super.dispatchTouchEvent(ev);
    }

}