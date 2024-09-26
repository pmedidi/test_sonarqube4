package com.example.greenplate.ui.inputmeal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DataVisualViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public DataVisualViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Data fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}