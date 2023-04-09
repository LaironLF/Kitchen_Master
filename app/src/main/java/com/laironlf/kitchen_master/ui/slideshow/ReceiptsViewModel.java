package com.laironlf.kitchen_master.ui.slideshow;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReceiptsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ReceiptsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}