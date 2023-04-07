package com.laironlf.kitchen_master.DB;

import android.annotation.SuppressLint;
import android.util.Log;

import java.sql.Array;
import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

    static private final String server = "host1857461.hostland.pro";
    static private final String database = "host1857461_kitchenmaster";
    static private final int port = 3306;
    static private final String user = "host1857461_add";
    static private final String pass = "YlqloRJ2";
    static private final String driver = "com.mysql.jdbc.Driver";
    static private final String url = String.format("jdbc:mysql://%s:%d/%s", server, port, database);
    static private boolean status;
    static Connection connection = null;

    static public Connect connectionToDataBase = new Connect();

    public static class Connect extends Thread {
        public void run() {
            try {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, pass);
                status = true;
                Log.i("DB", "connectionToDataBase: connection true");


            } catch (SQLException e) {
                Log.e("DB", "connectionToDataBase: connection false");
                status = false;
            } catch (ClassNotFoundException e) {
                Log.e("DB", "connectionToDataBase: connection false");
                e.printStackTrace();
                status = false;
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
    static public Query query = new Query();

    public static class Query extends Thread {
        private String sql;
        private ResultSet rs;
        private boolean tStatus;

        public void setSql(String sql) {
            this.sql = sql;
        }

        public ResultSet getRs(){
            return rs;
        }

        public void run() {
            if (!status) {
                return;
            }
            if (sql == null) {
                return;
            }

            try (Statement stmt = connection.createStatement()) {

                rs = stmt.executeQuery(sql);
                rs.close();
                tStatus = true;
            } catch (SQLException e) {
                tStatus = false;
                Log.e("DB", "query: \n" + e);
            }

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

//    public void close() {
//        try {
//            connection.close();
//        } catch (SQLException e) {
//            Log.e("BD", "close: \n" + e);
//        }
//    }
}

