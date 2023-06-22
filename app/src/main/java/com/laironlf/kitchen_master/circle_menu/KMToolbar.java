package com.laironlf.kitchen_master.circle_menu;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;

import com.laironlf.kitchen_master.R;

/**
 * <p>Класс моего кастомного тулбара</p>
 */
public class KMToolbar implements NavController.OnDestinationChangedListener{
    private static KMToolbar kmToolbar;
    private Boolean previousIsMenu = true; // типо для оптимизации, хотя и выиграю я немного)
    private View toolbar;
    private ImageButton btn_menu;
    private EditText et_search;
    private TextView txt_menu;
    private Activity activity;
    private SearchToolbarListener searchToolbarListener;
    private static final int FAVORITES = 2;
    private static final int RECIPES = 3;
    private static final int FRIDGE = 5;
    private NavDestination currentDestination;

    public static void initNavCircleToolbar(View toolbar, Activity activity){
        if (kmToolbar == null)
            kmToolbar = new KMToolbar(toolbar, activity);
    }
    private KMToolbar(View toolbar, Activity activity){
        this.toolbar = toolbar;
        this.btn_menu = toolbar.findViewById(R.id.btn_menu);
        this.et_search = toolbar.findViewById(R.id.et_search);
        this.txt_menu = toolbar.findViewById(R.id.txt_menu);
        this.activity = activity;


        AppCircleNavigation.AppNavigation.getNavController().addOnDestinationChangedListener(this);
        initBaseSearchListeners();
        initMenuClick();
    }

    private void initBaseSearchListeners() {
        et_search.setOnFocusChangeListener((view, b) -> {
            if(b) txt_menu.setText("");
            else updateLabel();
        });
        et_search.setOnEditorActionListener((textView, i, keyEvent) -> {
            if(i == EditorInfo.IME_ACTION_SEARCH){
                View view = activity.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }

            return false;
        });
    }

    public static KMToolbar getKMToolbar(){
        return kmToolbar;
    }

     public void setSearchVisibility(int visibility){
        if(visibility == View.GONE || visibility == View.INVISIBLE){
            et_search.setText("");
            et_search.setFocusable(false);
            et_search.clearFocus();
        } else {
            et_search.setFocusableInTouchMode(true);
            et_search.setFocusable(true);
        }
        et_search.setVisibility(visibility);
     }

     public void clearSearchFocus(){
        et_search.clearFocus();
     }

    private void initMenuClick(){
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


    private void updateMenuIcon(){
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
        this.currentDestination = destination;
        Menu menu = AppCircleNavigation.getMenu();
        if (destination.getId() == menu.getItem(FAVORITES).getItemId()
        || destination.getId() == menu.getItem(RECIPES).getItemId()
        || destination.getId() == menu.getItem(FRIDGE).getItemId()) {
            setSearchVisibility(View.VISIBLE);
        } else {
            setSearchVisibility(View.INVISIBLE);
        }
        updateLabel();
    }

    private void updateLabel(){
        if(et_search.getText().toString().equals(""))
            txt_menu.setText(currentDestination.getLabel());
        else txt_menu.setText("");
    }


    public void setSearchToolbarListener(SearchToolbarListener searchToolbarListener){
        this.searchToolbarListener = searchToolbarListener;
        et_search.setOnEditorActionListener((textView, i, keyEvent) -> {
            et_search.clearFocus();
            return searchToolbarListener.onSearchClicked(textView, i, keyEvent);
        });

    }

    interface SearchToolbarListener {
        boolean onSearchClicked(TextView textView, int i, KeyEvent keyEvent);
    }

}