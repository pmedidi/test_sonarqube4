package com.example.greenplate.ui.recipe;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InputRecipeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public InputRecipeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("input recipe");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
