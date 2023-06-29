package com.laironlf.kitchen_master.circle_menu.modules;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import java.util.Objects;

/**
 * <p>Класс навигации меню, собственно больше нечего сказать</p>
 */
public class AppNavigation{
    private Menu menu;
    private NavController navController;

    public AppNavigation(Activity activity, Menu menu, int id){
        this.navController = Navigation.findNavController(activity, id);
        this.menu = menu;
    }
    public NavController getNavController(){
        return navController;
    }

    public int getCurrentDestinationID(){
        return Objects.requireNonNull(navController.getCurrentDestination()).getId();
    }
    public boolean currentDestinationIsMenu(){
        for(int i = 0; i < menu.size(); i++)
            if(menu.getItem(i).getItemId() == getCurrentDestinationID())
                return true;
        return false;
    }

    public void navigate(int idDestination){
        navController.popBackStack(navController.getGraph().getStartDestination(), false);
        navController.navigate(idDestination);
    }
}