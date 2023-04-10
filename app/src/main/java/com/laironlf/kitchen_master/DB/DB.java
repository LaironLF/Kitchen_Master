package com.laironlf.kitchen_master.DB;


import android.util.Log;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DB {

    static private final String server = "host1857461.hostland.pro";
    static private final String database = "host1857461_kitchenmaster";
    static private final int port = 3306;
    static private final String user = "host1857461_app";
    static private final String pass = "7DikHCmD";
    static private final String driver = "com.mysql.jdbc.Driver";
    static private final String url = String.format("jdbc:mysql://%s:%d/%s", server, port, database);

    static Connection connection = null;
    static private boolean conStatus;
    static public Connect connectionToDataBase = new Connect();

    public static class Connect extends Thread {
        public void run() {
            try {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, pass);
                conStatus = true;
                Log.i("DB", "connectionToDataBase: connection true");
            } catch (SQLException e) {
                Log.e("DB", "connectionToDataBase: connection false");
                conStatus = false;
            } catch (ClassNotFoundException e) {
                Log.e("DB", "connectionToDataBase: connection false");
                e.printStackTrace();
                conStatus = false;
            }
        }
    }

    public static abstract class Query extends Thread{
        protected String sql = null;
        protected boolean queryStatus;
        protected ResultSet rs;
        protected abstract void logic() throws SQLException;

        public void run() {
            if (!conStatus) {
                return;
            }
            if (sql == null){
                Log.e("DB", "run: sql query is null");
                return;
            }
            try(Statement stmt = connection.createStatement() ) {
                rs = stmt.executeQuery(sql);
                this.logic();
                queryStatus = true;
            } catch (SQLException e) {
                queryStatus = false;
                Log.e("DB", "query: \n" + e);
            }
        }
    }

//    public void connectionToDataBase(){
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run(){
//
//                try {
//                    Class.forName(driver);
//                    connection = DriverManager.getConnection(url, user, pass);
//                    status = true;
//                    Log.i("DB", "connectionToDataBase: connection true");
//
//
//                }
//                catch (SQLException e) {
//                    Log.e("DB", "connectionToDataBase: connection false");
//                    status = false;
//                } catch (ClassNotFoundException e) {
//                    Log.e("DB", "connectionToDataBase: connection false");
//                    e.printStackTrace();
//                    status = false;
//                }
//
//            }
//        });
//
//        thread.start();
//        try
//        {
//            thread.join();
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            Log.e("DB", "connectionToDataBase: connection false" );
//            status = false;
//        }
//    }
    public void t(){}
//    static public Query query = new Query();
//
//    public static class Query extends Thread {
//
//        protected String sql;
//        protected ResultSet rs;
//        protected boolean queryStatus;
//
//        public Query(){
//
//        }
//        public Query(String sql){
//            this.sql = sql;
//        }
//
//        public void setSql(String sql) {
//            this.sql = sql;
//        }
//
//        public ResultSet getRs(){
//            return rs;
//        }
//
//        public void run() {
//            if (!conStatus) {
//                return;
//            }
//            if (sql == null) {
//                return;
//            }
//
//            try(Statement stmt = connection.createStatement() ) {
//                rs = stmt.executeQuery(sql);
//                while (rs.next()){
//                    System.out.println(rs.getInt(1) + " " + rs.getString(2));
//
//                }
////                rs.close();
//                queryStatus = true;
//            } catch (SQLException e) {
//                queryStatus = false;
//                Log.e("DB", "query: \n" + e);
//            }
//        }
//    }

    public static ArrayList<Product> products = new ArrayList<>();
    public static GetProducts getProducts = new GetProducts();
    public static class GetProducts extends Query {
        public GetProducts(){
            sql = "SELECT * FROM Products";
        }

        @Override
        protected void logic() throws SQLException {
            products.ensureCapacity(rs.getFetchSize());

            while (rs.next()) {
                products.add(new Product(rs.getInt("ProductID"), rs.getString("Name")));
            }
        }
    }

    public static ArrayList<Recipe> recipes = new ArrayList<>();
    public static GetRecipe getRecipe = new GetRecipe();
    public static class GetRecipe extends Query {
        String preSql;
        String preSql1;
        String settings;
        public GetRecipe(){
            preSql =
                    "SELECT " +
                    "	Recipes.RecipeID, " +
                    "	Recipes.Name, " +
                    "	Recipes.Description, " +
                    "	Сomplexity.Name, " +
                    "	Type_of_dish.Name, " +
                    "	Recipes.Time, " +
                    "	Images.URL " +
                    "FROM `Recipes` " +
                    "JOIN Сomplexity ON Recipes.RecipeID = Сomplexity.СomplexityID " +
                    "JOIN Type_of_dish ON Recipes.TypeID = Type_of_dish.TypeID " +
                    "LEFT JOIN Images ON Recipes.RecipeID = Images.RecipeID " +
                    "INNER JOIN (" +
                    "	SELECT " +
                    "		t1.tt1, " +
                    "		t1.`RecipeID` " +
                    "	FROM ( " +
                    "		SELECT " +
                    "			COUNT(`IngredientID`) AS tt1, " +
                    "			Ingredients.`RecipeID` " +
                    "		FROM `Ingredients` " +
                    "		GROUP BY Ingredients.RecipeID " +
                    "		HAVING COUNT(`IngredientID`) " +
                    "	) AS t1 " +
                    "	INNER JOIN ( " +
                    "		SELECT " +
                    "			COUNT(IngredientID) AS tt2, " +
                    "			RecipeID " +
                    "		FROM Ingredients " +
                    "		WHERE ProductID IN(%s) " +
                    "		GROUP BY RecipeID " +
                    "		HAVING COUNT(IngredientID) " +
                    "	) AS t2 ON t1.tt1 = t2.tt2 " +
                    ") AS t3 ON Recipes.RecipeID = t3.RecipeID";
            preSql1 =
                    "SELECT " +
                    "   Recipes.RecipeID, " +
                    "   Recipes.Name, " +
                    "   Recipes.Description, " +
                    "   Сomplexity.Name, " +
                    "   Type_of_dish.Name, " +
                    "   Recipes.Time, " +
                    "   Images.URL " +
                    "FROM Recipes " +
                    "JOIN Сomplexity ON Recipes.ComplexityID = Сomplexity.СomplexityID " +
                    "JOIN Type_of_dish ON Recipes.TypeID = Type_of_dish.TypeID " +
                    "LEFT JOIN Images ON Recipes.RecipeID = Images.RecipeID";


            sql = preSql1;
        }

        public void setSettings(String settings){
            this.settings = settings;
            sql = String.format(preSql, settings);
        }
        public void setSettings(){
            sql = preSql1;
        }

        @Override
        protected void logic() throws SQLException{
            recipes.clear();
            recipes.ensureCapacity(rs.getFetchSize());
            while (rs.next()) {
                recipes.add(new Recipe(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getTime(6),
                        rs.getString(7)
                ));
            }
            getRecipe = new GetRecipe();
        }
    }

    public static ArrayList<Ingredient> ingredients = new ArrayList<>();
    public static GetIngredients getIngredients = new GetIngredients();
    public static class GetIngredients extends Query{
        private String preSql;
        public GetIngredients(){
            sql =
                    "SELECT Ingredients.IngredientID, Ingredients.Count, Units_of_measurement.Name, Products.Name, Ingredients.RecipeID " +
                    "FROM Ingredients " +
                    "JOIN Units_of_measurement ON Units_of_measurement.UnitID = Ingredients.UnitID " +
                    "JOIN Products ON Products.ProductID = Ingredients.ProductID ";
            preSql = "WHERE Ingredients.RecipeID = %d";
        }

        public void setSettings(int recipeID){
            sql += String.format(preSql, recipeID);
        }

        @Override
        public void logic() throws SQLException{
            ingredients.ensureCapacity(rs.getFetchSize());
            while(rs.next()){
                ingredients.add(new Ingredient(
                        rs.getInt(1),
                        rs.getFloat(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5)
                ));
            }
            getIngredients = new GetIngredients();
        }
    }

//    public void query(String sql) {
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                if (!status) {
//                    return;
//                }
//
//                String SQL = "SELECT ProductID, Name FROM Products";
//
//                try (Statement stmt = connection.createStatement()) {
//
//                    ResultSet rs = stmt.executeQuery(SQL);
//                    while (rs.next()) {
//                        int productID = rs.getInt("ProductID");
//                        String name = rs.getString("Name");
//                        System.out.println(productID + " " + name);
//                    }
////            rs.close();
//                    stmt.close();
//                } catch (SQLException e) {
////            throw new RuntimeException(e);
//                    Log.e("DB", "query: \n" + e);
//                }
//
//            }
//        });
//        thread.start();
//        try {
//            thread.join();
//
//        } catch (Exception e) {
//            e.printStackTrace();
////            Log.e("DB", "connectionToDataBase: connection false" );
////            status = false;
//        }
//
//
//    }

    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            Log.e("BD", "close: \n" + e);
        }
    }
}

