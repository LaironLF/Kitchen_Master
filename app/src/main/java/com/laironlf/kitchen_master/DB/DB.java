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
//    static final String DB_URL = "jdbc:postgresql://postgresql:5432/northwind";
//    static final String USER = "postgresql";
//    static final String PASS = "maxSQL145-max";

    private final String host = "92.125.140.47";
    private final String database = "northwind";
    private final int port = 5432;
    private final String user = "postgres";
    private final String pass = "maxSQL145-max";
    private String url = "jdbc:postgresql://%s:%d/%s";
//    private final String URL = "jdbc:postgresql://postgres:maxSQL145-max@127.0.0.1:5432/northwind";
    // postgres:maxSQL145-max@
    private final String URL = "jdbc:postgresql://10.0.2.2:5432/northwind";
    private boolean status;
    Connection connection = null;

    public void dataBase()
    {
        this.url = String.format(this.url, this.host, this.port, this.database);
        connect();
        //this.disconnect();
        System.out.println("connection status:" + status);
    }

    private void connect()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    Class.forName("org.postgresql.Driver");
//                    connection = DriverManager.getConnection(url, user, pass);
                    connection = DriverManager.getConnection(URL, user, pass);
                    Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM orders limit 5");
                    while (rs.next()) {
                        String str = rs.getString("order_id") + ":" + rs.getString(2);
                        System.out.println("Contact:" + str);
                    }
                    rs.close();
                    stmt.close();
                    status = true;
                    System.out.println("connected:" + status);
                }
                catch (Exception e)
                {
                    status = false;
                    System.out.print(e.getMessage());
//                    System.out.print("говно\n");
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
            this.status = false;
        }
    }

    public Connection getExtraConnection()
    {
        Connection c = null;
        try
        {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, pass);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return c;
    }

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

