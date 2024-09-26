package com.example.greenplate.ui.ingredient;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InputIngredientViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public InputIngredientViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("input ingredients");
    }

    public LiveData<String> getText() {
        return mText;
    }
}