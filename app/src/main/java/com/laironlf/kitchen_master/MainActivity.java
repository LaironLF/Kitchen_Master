
//          ▐▄▄▄▄▄▄ .▄ •▄  ▄▄▄·    ▄▄▄·  ▐ ▄ ·▄▄▄▄    ▄▄▌   ▄▄▄· ▪  ▄▄▄         ▐ ▄
//          ▪·██▀▄.▀·█▌▄▌▪▐█ ▀█   ▐█ ▀█ •█▌▐███· ██   ██•  ▐█ ▀█ ██ ▀▄ █· ▄█▀▄ •█▌▐█
//         ▪▄ ██▐▀▀▪▄▐▀▀▄·▄█▀▀█   ▄█▀▀█ ▐█▐▐▌▐█▪ ▐█▌  ██ ▪ ▄█▀▀█ ▐█·▐▀▀▄ ▐█▌.▐▌▐█▐▐▌
//         ▐▌▐█▌▐█▄▄▌▐█.█▌▐█▪ ▐▌  ▐█▪ ▐▌██▐█▌██. ██   ▐█▌ ▄▐█▪ ▐▌▐█▌▐█•█▌▐█▌.▐▌██▐█▌
//          ▀▀▀• ▀▀▀ ·▀  ▀ ▀  ▀    ▀  ▀ ▀▀ █▪▀▀▀▀▀•   .▀▀▀  ▀  ▀ ▀▀▀.▀  ▀ ▀█▄▀▪▀▀ █▪

package com.laironlf.kitchen_master;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.laironlf.kitchen_master.DB.DB;
import com.laironlf.kitchen_master.DB.UserProducts;
import com.laironlf.kitchen_master.databinding.ActivityMainNavBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainNavBinding binding;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.appBarMain.toolbar;
        setSupportActionBar(toolbar);

        drawer = binding.drawerLayout;
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    public static void menuSetClickListeners(){

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserProducts.writeData();
        DB.close();
    }
}