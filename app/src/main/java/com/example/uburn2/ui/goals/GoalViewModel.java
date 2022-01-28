package com.example.uburn2.ui.goals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GoalViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GoalViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is a goal fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}