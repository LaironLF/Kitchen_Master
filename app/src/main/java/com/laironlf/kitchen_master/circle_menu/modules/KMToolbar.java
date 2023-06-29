package com.laironlf.kitchen_master.circle_menu.modules;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
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
    private KMToolbar kmToolbar;
    private Boolean previousIsMenu = true; // типо для оптимизации, хотя и выиграю я немного)
    private ImageButton btn_menu;
    private TextView txt_menu;
    private Activity activity;
    private AppNavigation appNavigation;

    private EditText text;
    private SearchView searchView;
    private ImageButton buttonStartSearch;

    private static final int FAVORITES = 2;
    private static final int RECIPES = 3;
    private static final int FRIDGE = 5;

    private NavDestination currentDestination;

    // -- Constructors --

    public KMToolbar(View toolbar, Activity activity, AppNavigation appNavigation){
        this.btn_menu = toolbar.findViewById(R.id.btn_menu);
        this.txt_menu = toolbar.findViewById(R.id.txt_menu);
        this.text = toolbar.findViewById(R.id.et_search);
        this.activity = activity;
        this.appNavigation = appNavigation;

        appNavigation.getNavController().addOnDestinationChangedListener(this);
        initMenuClick();
        initSearchSegment();
    }
    private void initMenuClick(){
        btn_menu.setOnClickListener(view -> {
            if(!appNavigation.currentDestinationIsMenu()) {
                activity.onBackPressed();
                return;
            }
            if(AppCircleNavigation.isDrawerOpen())
                AppCircleNavigation.closeDrawer();
            else
                AppCircleNavigation.openDrawer();
        });
    }
    private void initSearchSegment(){

    }

    // -- main logic --

    @SuppressLint("UseCompatLoadingForDrawables")
    private void updateMenuIcon(){
        boolean currentDestinationIsMenu = appNavigation.currentDestinationIsMenu();
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
        this.currentDestination = destination;
        txt_menu.setText(currentDestination.getLabel());
    }
    interface SearchToolbarListener {
        boolean onSearchClicked(TextView textView, int i, KeyEvent keyEvent);
    }

}