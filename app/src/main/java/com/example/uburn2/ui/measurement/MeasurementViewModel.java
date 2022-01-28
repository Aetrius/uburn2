package com.example.uburn2.ui.measurement;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uburn2.R;

public class MeasurementViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MeasurementViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is new measurement fragment");
    }



    public LiveData<String> getText() {
        return mText;
    }
}