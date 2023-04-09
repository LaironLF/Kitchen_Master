package com.laironlf.kitchen_master.DB;

public class test {
    public void start() {
//        DB database = new DB();


        DB.connectionToDataBase.start();
        try {
            DB.connectionToDataBase.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        DB.query.setSql("SELECT * FROM Products");
//        DB.query.start();
//        try {
//            DB.query.join();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }


        DB.getProducts.start();

        try {
            DB.getProducts.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (Product i : DB.products) {
            i.pr();
        }


        DB.getRecipe.start();

        try {
            DB.getRecipe.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (Recipe i : DB.recipes) {
            i.pr();
        }
    }
}
