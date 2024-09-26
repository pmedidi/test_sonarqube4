package com.example.greenplate.ui.shoppinglist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddShopViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public AddShopViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("add shop");
    }

    public LiveData<String> getText() {
        return mText;
    }
}