package com.laironlf.kitchen_master.DB;

public class Product {
    public int productID;
    public String name;

    public Product(int productID, String name){
        this.productID = productID;
        this.name = name;
    }

    public void pr(){
        System.out.println(productID + " " + name);
    }
}
