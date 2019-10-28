package com.example.exercisetrackerapp.ui.achievements;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class AchievementsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AchievementsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is achievement fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}