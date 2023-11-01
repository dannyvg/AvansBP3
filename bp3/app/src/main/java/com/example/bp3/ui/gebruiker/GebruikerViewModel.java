package com.example.bp3.ui.gebruiker;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GebruikerViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GebruikerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}