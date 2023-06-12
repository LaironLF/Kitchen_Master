package com.laironlf.kitchen_master.circle_menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;

import com.laironlf.kitchen_master.R;
import com.laironlf.kitchen_master.circle_menu.AppCircleNavigation;

/**
 * <p>Класс моего кастомного тулбара</p>
 */
public class KMToolbar implements NavController.OnDestinationChangedListener{
    private static KMToolbar kmToolbar;
    private static Boolean previousIsMenu = true; // типо для оптимизации, хотя и выиграю я немного)
    private static View toolbar;
    private static ImageButton btn_menu;
    private static TextView txt_menu;
    private static Activity activity;

    public static void initNavCircleToolbar(View toolbar, Activity activity){
        kmToolbar = new KMToolbar(toolbar, activity);
    }
    private KMToolbar(View toolbar, Activity activity){
        KMToolbar.toolbar = toolbar;
        KMToolbar.btn_menu = toolbar.findViewById(R.id.btn_menu);
        KMToolbar.txt_menu = toolbar.findViewById(R.id.txt_menu);
        KMToolbar.activity = activity;

        AppCircleNavigation.AppNavigation.getNavController().addOnDestinationChangedListener(this);
        initMenuClick();
    }

    private static void initMenuClick(){
        btn_menu.setOnClickListener(view -> {
            if(!AppCircleNavigation.AppNavigation.currentDestinationIsMenu()) {
                activity.onBackPressed();
                return;
            }
            if(AppCircleNavigation.isDrawerOpen())
                AppCircleNavigation.closeDrawer();
            else
                AppCircleNavigation.openDrawer();
        });
    }

    private static void updateMenuIcon(){
        boolean currentDestinationIsMenu = AppCircleNavigation.AppNavigation.currentDestinationIsMenu();
        if(currentDestinationIsMenu && !previousIsMenu){
            previousIsMenu = true;
            btn_menu.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_menu, activity.getTheme()));
        }
        if(!currentDestinationIsMenu && previousIsMenu){
            previousIsMenu = false;
            btn_menu.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_menu_back, activity.getTheme()));
        }
    }

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        updateMenuIcon();
        txt_menu.setText(destination.getLabel());
    }
}