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
    private final String database = "kitchen_master";
    private final int port = 5432;
    private final String user = "sa";
    private final String pass = "Digispot2";
    private final String serverDriver = "net.sourceforge.jtds.jdbc.Driver";
    private final String postgreDriver = "org.postgresql.Driver";

    private final String driver = serverDriver;
//    private final String URL = "jdbc:postgresql://postgres:maxSQL145-max@127.0.0.1:5432/northwind";
    // postgres:maxSQL145-max@
    private final String URL = "jdbc:jtds:sqlserver://10.0.2.2:1433/kitchen_master";
    private boolean status;
    Connection connection = null;

    public void start(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run(){
                String connectionUrl = "jdbc:jtds:sqlserver://10.0.2.2:1433;encrypt=true;databaseName=AdventureWorks;user=Maxim;";

                try {
                    Class.forName(driver);
                    Connection con = DriverManager.getConnection(connectionUrl);
//                    Statement stmt = con.createStatement();
//                    String SQL = "SELECT TOP 10 * FROM Person.Contact";
//                    ResultSet rs = stmt.executeQuery(SQL);
                    System.out.println("connected: true");

                    // Iterate through the data in the result set and display it.
//            while (rs.next()) {
//                System.out.println(rs.getString("FirstName") + " " + rs.getString("LastName"));
//            }
                }
                // Handle any errors that may have occurred.
                catch (SQLException e) {
                    System.out.println("connected: false");
//                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
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

