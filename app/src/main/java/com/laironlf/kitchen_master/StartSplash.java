package com.laironlf.kitchen_master;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.laironlf.kitchen_master.DB.DB;
import com.laironlf.kitchen_master.DB.UserProducts;

public class StartSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        DB.connectionToDataBase.start();
        try {
            DB.connectionToDataBase.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        DB.getProducts.start();
        try {
            DB.getProducts.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        UserProducts.setContext(this);
        UserProducts.readData();
        startActivity(intent);
        finish();
    }
}
