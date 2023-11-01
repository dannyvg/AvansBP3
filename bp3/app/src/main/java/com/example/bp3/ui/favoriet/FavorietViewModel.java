package com.example.bp3.ui.favoriet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FavorietViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FavorietViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}