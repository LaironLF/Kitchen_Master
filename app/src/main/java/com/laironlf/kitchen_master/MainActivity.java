
//          ▐▄▄▄▄▄▄ .▄ •▄  ▄▄▄·    ▄▄▄·  ▐ ▄ ·▄▄▄▄    ▄▄▌   ▄▄▄· ▪  ▄▄▄         ▐ ▄
//          ▪·██▀▄.▀·█▌▄▌▪▐█ ▀█   ▐█ ▀█ •█▌▐███· ██   ██•  ▐█ ▀█ ██ ▀▄ █· ▄█▀▄ •█▌▐█
//         ▪▄ ██▐▀▀▪▄▐▀▀▄·▄█▀▀█   ▄█▀▀█ ▐█▐▐▌▐█▪ ▐█▌  ██ ▪ ▄█▀▀█ ▐█·▐▀▀▄ ▐█▌.▐▌▐█▐▐▌
//         ▐▌▐█▌▐█▄▄▌▐█.█▌▐█▪ ▐▌  ▐█▪ ▐▌██▐█▌██. ██   ▐█▌ ▄▐█▪ ▐▌▐█▌▐█•█▌▐█▌.▐▌██▐█▌
//          ▀▀▀• ▀▀▀ ·▀  ▀ ▀  ▀    ▀  ▀ ▀▀ █▪▀▀▀▀▀•   .▀▀▀  ▀  ▀ ▀▀▀.▀  ▀ ▀█▄▀▪▀▀ █▪

package com.laironlf.kitchen_master;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.laironlf.kitchen_master.DB.DB;
import com.laironlf.kitchen_master.DB.UserProducts;
import com.laironlf.kitchen_master.DB.test;
import com.laironlf.kitchen_master.databinding.ActivityMainNavBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainNavBinding binding;

    @NonNull
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_gallery,
                R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


//        test t = new test();
//        t.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserProducts.writeData();
        DB.close();
    }
}