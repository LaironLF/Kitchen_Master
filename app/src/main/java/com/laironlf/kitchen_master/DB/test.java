package com.laironlf.kitchen_master.DB;

public class test {
    public void start() {
        DB.getIngredients.setSettings(1);
        DB.getIngredients.start();
        try {
            DB.getIngredients.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (Ingredient i : DB.ingredients) {
            i.pr();
        }

//        UserProducts.add(DB.products.get(0));
//        UserProducts.add(DB.products.get(3));
//        UserProducts.add(DB.products.get(4));
//        UserProducts.add(DB.products.get(52));
//        UserProducts.add(DB.products.get(67));
//        UserProducts.add(DB.products.get(69));
//        UserProducts.add(DB.products.get(80));
//        UserProducts.add(DB.products.get(81));
//        UserProducts.add(DB.products.get(82));
//        UserProducts.add(DB.products.get(83));
//        UserProducts.add(DB.products.get(84));
//        UserProducts.add(DB.products.get(89));
//
//        UserProducts.writeData();
//        UserProducts.readData();
//        for (Product i : UserProducts.userProducts) {
//            i.pr();
//        }
//        DB.getRecipe.setSettings(UserProducts.getString());
//        DB.getRecipe.start();
//        try {
//            DB.getRecipe.join();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        for (Recipe i : DB.recipes) {
//            i.pr();
//        }
    }
}
