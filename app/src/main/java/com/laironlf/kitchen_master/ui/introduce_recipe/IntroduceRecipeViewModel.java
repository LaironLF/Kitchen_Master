package com.laironlf.kitchen_master.ui.introduce_recipe;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.laironlf.kitchen_master.DB.DB;
import com.laironlf.kitchen_master.DB.Ingredient;
import com.laironlf.kitchen_master.DB.Recipe;
import com.laironlf.kitchen_master.data_provider.RecipeDataMediator;

import java.util.ArrayList;

public class IntroduceRecipeViewModel extends ViewModel {
    MutableLiveData<Recipe> recipe;
    MutableLiveData<ArrayList<Ingredient>> ingredients;

    public IntroduceRecipeViewModel(){
        InitializeRecipeData();
    }

    private void InitializeRecipeData() {
        DB.getIngredients.start();
        recipe = new MutableLiveData<>();
        recipe.setValue(RecipeDataMediator.getRecipe());
        this.ingredients = new MutableLiveData<>();

        try {
            DB.getIngredients.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for(Ingredient ingredient: DB.ingredients){
            if(ingredient.recipeID == RecipeDataMediator.getRecipe().recipeID)
                ingredients.add(ingredient);
        }
        this.ingredients.setValue(ingredients);
        DB.getIngredients = new DB.GetIngredients();
    }

    public LiveData<ArrayList<Ingredient>> getIngredients() {
        return ingredients;
    }
    public LiveData<Recipe> getRecipe(){
        return recipe;
    }
}