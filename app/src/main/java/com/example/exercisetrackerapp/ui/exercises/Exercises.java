package com.example.exercisetrackerapp.ui.exercises;

import android.media.Image;

import com.example.exercisetrackerapp.data.model.Calorie;
import com.example.exercisetrackerapp.data.model.Date;
import com.example.exercisetrackerapp.data.model.Type;

public class Exercises {
    private String name;
    private String url;

    public Exercises(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
