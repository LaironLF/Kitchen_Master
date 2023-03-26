package com.laironlf.kitchen_master.data_provider;

public class Receipt {
    private final String name;
    private final String dish_type;

    public Receipt(String name, String dish_type){
        this.name = name;
        this.dish_type = dish_type;
    }

    public String getName() {
        return name;
    }

    public String getDish_type() {
        return dish_type;
    }
}
