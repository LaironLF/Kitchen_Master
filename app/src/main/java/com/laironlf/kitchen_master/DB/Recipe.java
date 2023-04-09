package com.laironlf.kitchen_master.DB;

import java.sql.Time;

public class Recipe {
    public int recipeID;
    public String recipeName;
    public String description;
    public String complexityName;
    public String typeName;
    public Time time;

    public Recipe(int recipeID,String recipeName, String description, String complexityName, String typeName, Time time){
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.description = description;
        this.complexityName = complexityName;
        this.typeName = typeName;
        this.time = time;
    }

    public void pr(){
        System.out.println(recipeID + " " + recipeName + " " + description + " " + complexityName + " " + typeName + " " + time);

    }
}
