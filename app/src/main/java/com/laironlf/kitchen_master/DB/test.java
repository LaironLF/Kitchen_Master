package com.laironlf.kitchen_master.DB;

import android.util.Log;

import java.sql.SQLException;

public class test {
    public void start() {
//        DB database = new DB();


        DB.connectionToDataBase.start();
        try {
            DB.connectionToDataBase.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        DB.query.setSql("SELECT * FROM Products");
        DB.query.start();
        try {
            DB.query.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
