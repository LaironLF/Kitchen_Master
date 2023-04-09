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

//        for (Product i : DB.products) {
//            i.pr();
//        }


        UserProducts.add(DB.products.get(0));
        UserProducts.add(DB.products.get(3));
        UserProducts.add(DB.products.get(4));
        UserProducts.add(DB.products.get(52));
        UserProducts.add(DB.products.get(67));
        UserProducts.add(DB.products.get(69));
        UserProducts.add(DB.products.get(80));
        UserProducts.add(DB.products.get(81));
        UserProducts.add(DB.products.get(82));
        UserProducts.add(DB.products.get(83));
        UserProducts.add(DB.products.get(84));
        UserProducts.add(DB.products.get(89));

        DB.getRecipe.setSettings(UserProducts.getString());
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
