package com.laironlf.kitchen_master.DB;

import android.annotation.SuppressLint;
import android.util.Log;

import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

    private final String server = "host1857461.hostland.pro";
    private final String database = "host1857461_kitchenmaster";
    private final int port = 3306;
    private final String user = "host1857461_app";
    private final String pass = "7DikHCmD";

    private final String driver = "com.mysql.jdbc.Driver";
    private final String url = String.format("jdbc:mysql://%s:%d/%s", server, port, database);
    private boolean status;
    Connection connection = null;

    public void start(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run(){

                try {
                    Class.forName(driver);
                    connection = DriverManager.getConnection(url, user, pass);
                    status = true;
//                    Statement stmt = con.createStatement();
//                    String SQL = "SELECT TOP 10 * FROM Person.Contact";
//                    ResultSet rs = stmt.executeQuery(SQL);
                    System.out.println("connected:" + status);

                    // Iterate through the data in the result set and display it.
//            while (rs.next()) {
//                System.out.println(rs.getString("FirstName") + " " + rs.getString("LastName"));
//            }
                }
                // Handle any errors that may have occurred.
                catch (SQLException e) {
                    System.out.println("connected: false2");
//                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    System.out.println("connected: false3");
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try
        {
            thread.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("connected: false4");

            this.status = false;
        }

    }


//    public void dataBase()
//    {
////        this.url = String.format(this.url, this.host, this.port, this.database);
//        connect();
//        //this.disconnect();
//        System.out.println("connection status:" + status);
//    }

//    private void connect()
//    {
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run()
//            {
//                try
//                {
//                    Class.forName(driver);
////                    connection = DriverManager.getConnection(url, user, pass);
//                    connection = DriverManager.getConnection(URL, user, pass);
//                    status = true;
//                    System.out.println("connected:" + status);
//
//
////                    Statement stmt = connection.createStatement();
////                    ResultSet rs = stmt.executeQuery("SELECT * FROM Products limit 5");
////                    while (rs.next()) {
////                        String str = rs.getString(1) + ":" + rs.getString(2);
////                        System.out.println("\n\n\n\nContact:" + str + "\n\n\n\n");
////                    }
////                    rs.close();
////                    stmt.close();
//                }
//                catch (Exception e)
//                {
//                    status = false;
////                    System.out.print(e.getMessage());
////                    System.out.print("говно\n");
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.start();
//        try
//        {
//            thread.join();
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            this.status = false;
//        }
//    }

//    public Connection getExtraConnection()
//    {
//        Connection c = null;
//        try
//        {
//            Class.forName("org.postgresql.Driver");
//            c = DriverManager.getConnection(url, user, pass);
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//
//        return c;
//    }

//    public void connect(){
//        try {
//            Class.forName("org.postgresql.Driver");
//            System.out.println("tr;akljsnf;aklsjsnfp");
//
//        } catch (ClassNotFoundException e) {
//            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
//            e.printStackTrace();
//            return;
//        }
//        try {
//
//            connection = DriverManager.getConnection(DB_URL, USER, PASS);
//
//            Log.d("JeKa", "1");
//        } catch (SQLException e) {
//            System.out.println("Connection Failed");
//            e.printStackTrace();
//            Log.d("JeKa", "2");
//            return;
//        }
//    }

}

