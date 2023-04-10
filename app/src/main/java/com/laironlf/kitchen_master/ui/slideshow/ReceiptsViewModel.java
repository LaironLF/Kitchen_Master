package com.laironlf.kitchen_master.ui.slideshow;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.laironlf.kitchen_master.DB.DB;
import com.laironlf.kitchen_master.DB.Recipe;
import com.laironlf.kitchen_master.DB.UserProducts;

import java.util.ArrayList;

public class ReceiptsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Recipe>> recipes;

    public ReceiptsViewModel() {
        DB.getRecipe.setSettings(UserProducts.getString(), 0);
        DB.getRecipe.start();

        try {
            DB.getRecipe.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        DB.getRecipe = new DB.GetRecipe();

        recipes = new MutableLiveData<>();
        recipes.setValue(DB.recipes);
    }

    public LiveData<ArrayList<Recipe>> getRecipes(){
        return recipes;
    }
}