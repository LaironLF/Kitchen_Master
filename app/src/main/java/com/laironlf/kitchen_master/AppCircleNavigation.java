package com.laironlf.kitchen_master;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class AppCircleNavigation implements View.OnTouchListener {

    public static DrawerLayout drawerLayout;
    public static Context context;
    private float initialX;
    private final float SWIPE_THRESHOLD = 150;
    private LinearLayout btn_user, btn_home, btn_search, btn_favorites, btn_settings, btn_fridge;
    private NavController navController;

    @SuppressLint("ClickableViewAccessibility")
    public AppCircleNavigation(DrawerLayout drawerLayout, Context context){
        if(AppCircleNavigation.drawerLayout != null) return;

        AppCircleNavigation.drawerLayout = drawerLayout;
        AppCircleNavigation.context = context;

        drawerLayout.findViewById(R.id.nav_view).setOnTouchListener(this);
        drawerLayout.setOnTouchListener(this);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.START);
        navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main);
        menuSetClickListeners();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                initialX = motionEvent.getX();
                Log.d("mymy", "start " + initialX);
                return true;
            case MotionEvent.ACTION_MOVE:
                float deltaX = motionEvent.getX() - initialX;
                return true;
            case MotionEvent.ACTION_UP:
                // Calculate delta X and velocity of finger movement
                float deltaXUp = motionEvent.getX() - initialX;
                Log.d("mymy", "final " + deltaXUp);

                // Check if swipe gesture meets threshold distance and velocity requirements
                if (deltaXUp > SWIPE_THRESHOLD && !drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    // If swipe gesture is towards right (positive delta X), open the drawer
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                if (deltaXUp < -SWIPE_THRESHOLD && drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
        }
        return false;
    }

    public void menuSetClickListeners(){
        btn_user = drawerLayout.findViewById(R.id.btn_user);
        btn_home = drawerLayout.findViewById(R.id.btn_home);
        btn_search = drawerLayout.findViewById(R.id.btn_search);
        btn_favorites = drawerLayout.findViewById(R.id.btn_favorite);
        btn_settings = drawerLayout.findViewById(R.id.btn_settings);
        btn_fridge = drawerLayout.findViewById(R.id.btn_fridge);

        btn_search.setOnClickListener(view -> {
            navController.navigate(R.id.nav_slideshow);
        });

        btn_fridge.setOnClickListener(view -> {
            navController.navigate(R.id.nav_gallery);
        });
    }
}
