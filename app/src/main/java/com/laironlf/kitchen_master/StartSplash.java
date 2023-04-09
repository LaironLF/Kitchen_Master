package com.laironlf.kitchen_master;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.laironlf.kitchen_master.DB.DB;

public class StartSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        startActivity(intent);
        finish();
    }
}
