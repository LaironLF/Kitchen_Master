package com.laironlf.kitchen_master.DB;

public class Ingredient {
    public int ingredientID;
    public float count;
    public String unitName;
    public String productName;
    public int recipeID;

    public Ingredient(int ingredientID, float count, String unitName, String productName, int recipeID){
        this.ingredientID = ingredientID;
        this.count = count;
        this.unitName = unitName;
        this.productName = productName;
        this.recipeID = recipeID;
    }
    public void pr(){
        System.out.println(ingredientID + " " + count + " " + unitName + " " + productName + " " + recipeID);
    }
}
