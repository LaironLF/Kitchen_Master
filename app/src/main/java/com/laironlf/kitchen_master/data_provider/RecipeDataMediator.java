package com.laironlf.kitchen_master.data_provider;

import com.laironlf.kitchen_master.DB.Recipe;

public class RecipeDataMediator {
    static int recipeID;

    public static void setRecipe(int recipeID) {
        RecipeDataMediator.recipeID = recipeID;
    }
    public static int getRecipe() {
        return recipeID;
    }
}
