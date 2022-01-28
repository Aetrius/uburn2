package com.example.uburn2.ui.splashscreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SplashViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SplashViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is a splashscreen fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}