package com.example.exercisetrackerapp.ui.exercises;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class ExercisesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ExercisesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is exercises fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}