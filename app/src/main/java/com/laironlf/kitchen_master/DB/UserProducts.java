package com.laironlf.kitchen_master.DB;

import java.util.ArrayList;

public class UserProducts {
    public static ArrayList<Product> userProducts = new ArrayList<>();

    public static void capacity(int capacity){
        userProducts.ensureCapacity(capacity);
    }
    public static void add(Product product){
        userProducts.add(product);
    }
    public static void remove(Product product){
        userProducts.remove(product);
    }
    public static void remove(int index){
        userProducts.remove(index);
    }
    public static String getString(){
        StringBuilder res = new StringBuilder();
        for (Product i : userProducts) {
            res.append(i.productID).append(",");
        }
        res.delete(res.length()-1,res.length());
        return res.toString();
    }
}
