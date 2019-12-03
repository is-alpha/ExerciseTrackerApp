package com.example.exercisetrackerapp.ui.exerciseRoutine;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class ExerciseRoutineViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ExerciseRoutineViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is exercise routine fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}