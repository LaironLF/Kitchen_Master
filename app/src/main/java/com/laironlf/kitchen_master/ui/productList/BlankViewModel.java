package com.laironlf.kitchen_master.ui.productList;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.widget.ArrayAdapter;

import com.laironlf.kitchen_master.DB.DB;
import com.laironlf.kitchen_master.DB.Product;

import java.util.ArrayList;

public class BlankViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<ArrayList<Product>> productList;


    public BlankViewModel(){
        productList = new MutableLiveData<>();
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

        productList.setValue(DB.products);
    }


    public LiveData<ArrayList<Product>> getProducts()
    {
        return productList;
    }

}