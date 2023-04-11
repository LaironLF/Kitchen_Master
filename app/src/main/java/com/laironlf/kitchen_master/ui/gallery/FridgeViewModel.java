package com.laironlf.kitchen_master.ui.gallery;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.laironlf.kitchen_master.DB.Product;
import com.laironlf.kitchen_master.DB.UserProducts;

import java.util.ArrayList;

public class FridgeViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Product>> userProducts;

    public FridgeViewModel() {
        InitializeUserPoducts();
    }

    private void InitializeUserPoducts() {
        userProducts = new MutableLiveData<>();
        userProducts.setValue(UserProducts.userProducts);
    }

    public void deleteProduct(int i){
        Log.d("fridgeLog", String.valueOf(i));
        UserProducts.remove(i);
        UserProducts.writeData();
    }


    public LiveData<ArrayList<Product>> getUserProducts(){
        return userProducts;
    }

}