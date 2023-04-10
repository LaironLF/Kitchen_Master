package com.laironlf.kitchen_master.ui.productList;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.laironlf.kitchen_master.DB.DB;
import com.laironlf.kitchen_master.DB.Product;
import com.laironlf.kitchen_master.DB.UserProducts;

import java.util.ArrayList;

public class AddProductViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Product>> productList;
    private ArrayList<Product> products;

    public AddProductViewModel(){
        initializeProductList();
    }

    private void initializeProductList() {
        //Создаём локальную копию указывая капасити
        products = new ArrayList<>(DB.products.size());
        //заполняем локальную копию
        for(Product product: DB.products) products.add(product);
        // удаляем из локальной копии те продукты, что уже есть в юзерпродуктс
        for(Product userProduct : UserProducts.userProducts){
            for (Product localProduct : products){
                if(localProduct.productID == userProduct.productID){
                    products.remove(localProduct);
                    break;
                }
            }
        }

        productList = new MutableLiveData<>();
        productList.setValue(products);
    }

    public void deleteProduct(int i){
        Log.d("VMADDPROD", "продукт: "+ products.get(i).name + " удалён");
        UserProducts.add(products.get(i));
        UserProducts.writeData();
        products.remove(i);
    }


    public LiveData<ArrayList<Product>> getProducts()
    {
        return productList;
    }

}