package com.laironlf.kitchen_master.DB;

import java.sql.Time;
import java.util.Objects;

public class Recipe {
    public int recipeID;
    public String recipeName;
    public String description;
    public String complexityName;
    public String typeName;
    public Time time;
    public String imageMainURL;

    public Recipe(int recipeID,String recipeName, String description, String complexityName, String typeName, Time time, String imageMainURL){
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.description = description;
        this.complexityName = complexityName;
        this.typeName = typeName;
        this.time = time;
        this.imageMainURL = imageMainURL;
    }

    public String getTimeString(){
        String[] timeString = time.toString().split(":");
        int minuteCount = Integer.parseInt(timeString[1]) + Integer.parseInt(timeString[0]) * 60;
        return minuteCount + " мин";
    }

    public void pr(){
        System.out.println(recipeID + " " + recipeName + " " + description + " " + complexityName + " " + typeName + " " + time + " " + imageMainURL);

    }
}
