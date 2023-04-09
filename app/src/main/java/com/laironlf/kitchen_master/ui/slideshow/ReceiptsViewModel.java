package com.laironlf.kitchen_master.ui.slideshow;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.laironlf.kitchen_master.DB.DB;
import com.laironlf.kitchen_master.DB.Recipe;
import com.laironlf.kitchen_master.DB.UserProducts;

public class ReceiptsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ReceiptsViewModel() {
        DB.getRecipe.setSettings(UserProducts.getString());
        DB.getRecipe.start();

        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");

        try {
            DB.getRecipe.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public LiveData<String> getText() {
        return mText;
    }
}