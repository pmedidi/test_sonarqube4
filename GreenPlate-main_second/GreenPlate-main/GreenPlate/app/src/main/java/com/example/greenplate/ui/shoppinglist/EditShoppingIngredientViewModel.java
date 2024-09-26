package com.example.greenplate.ui.shoppinglist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class EditShoppingIngredientViewModel {
    private final MutableLiveData<String> mText;

    public EditShoppingIngredientViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ingredient fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
