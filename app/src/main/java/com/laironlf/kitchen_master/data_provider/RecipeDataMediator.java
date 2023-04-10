package com.laironlf.kitchen_master.data_provider;

import com.laironlf.kitchen_master.DB.Recipe;

public class RecipeDataMediator {
    static Recipe recipe;

    public static void setRecipe(Recipe recipe) {
        RecipeDataMediator.recipe = recipe;
    }
    public static Recipe getRecipe() {
        return recipe;
    }
}
