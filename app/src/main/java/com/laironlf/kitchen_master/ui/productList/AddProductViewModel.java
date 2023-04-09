package com.laironlf.kitchen_master.ui.productList;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.laironlf.kitchen_master.DB.DB;
import com.laironlf.kitchen_master.DB.Product;

import java.util.ArrayList;

public class AddProductViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Product>> productList;


    public AddProductViewModel(){
        productList = new MutableLiveData<>();
        productList.setValue(DB.products);
    }


    public LiveData<ArrayList<Product>> getProducts()
    {
        return productList;
    }

}