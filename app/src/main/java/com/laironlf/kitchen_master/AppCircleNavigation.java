package com.laironlf.kitchen_master;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.laironlf.kitchen_master.DB.Recipe;
import com.laironlf.kitchen_master.ui.Fridge.FridgeFragment;
import com.laironlf.kitchen_master.ui.Recipes.ReceiptsFragment;
import com.laironlf.kitchen_master.ui.home.HomeFragment;

public class AppCircleNavigation implements View.OnTouchListener {

    public static DrawerLayout drawerLayout;
    public static AppCompatActivity activity;
    private float initialX;
    private final float SWIPE_THRESHOLD = 150;
    private LinearLayout btn_user, btn_home, btn_search, btn_favorites, btn_settings, btn_fridge;
    private NavController navController;
    private Fragment selectedFragment;

    @SuppressLint("ClickableViewAccessibility")
    public AppCircleNavigation(DrawerLayout drawerLayout, AppCompatActivity activity){
        if(AppCircleNavigation.drawerLayout != null) return;

        AppCircleNavigation.drawerLayout = drawerLayout;
        AppCircleNavigation.activity = activity;


        selectedFragment = activity.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        drawerLayout.findViewById(R.id.nav_view).setOnTouchListener(this);
        drawerLayout.setOnTouchListener(this);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.START);
        navController = Navigation.findNavController((Activity) activity, R.id.nav_host_fragment_content_main);
        ItemAnimations itemAnimations = new ItemAnimations();
        itemAnimations.SetNavController(navController);

        findViews();
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

    private void findViews(){
        btn_user = drawerLayout.findViewById(R.id.btn_user);
        btn_home = drawerLayout.findViewById(R.id.btn_home);
        btn_search = drawerLayout.findViewById(R.id.btn_search);
        btn_favorites = drawerLayout.findViewById(R.id.btn_favorite);
        btn_settings = drawerLayout.findViewById(R.id.btn_settings);
        btn_fridge = drawerLayout.findViewById(R.id.btn_fridge);
    }

    private void menuSetClickListeners(){
        btn_home.setOnClickListener(view -> NavigateFragment(R.id.nav_home));
        btn_search.setOnClickListener(view -> NavigateFragment(R.id.nav_recipes));
        btn_fridge.setOnClickListener(view -> NavigateFragment(R.id.nav_products));
    }

    private void NavigateFragment(int id){
        if(navController.getCurrentDestination().getId() != id){
            navController.popBackStack();
            navController.navigate(id);
        }
    }

    private static class ItemAnimations implements NavController.OnDestinationChangedListener {

        private static NavController navController;

        public void SetNavController(NavController navController){
            ItemAnimations.navController = navController;
            ItemAnimations.navController.addOnDestinationChangedListener(this);
        }

        @Override
        public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
            Toast.makeText(activity, "CHANGED", Toast.LENGTH_SHORT).show();
        }
    }


}
