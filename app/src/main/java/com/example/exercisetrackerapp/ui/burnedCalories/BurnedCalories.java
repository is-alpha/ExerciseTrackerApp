package com.example.exercisetrackerapp.ui.burnedCalories;

import com.example.exercisetrackerapp.data.model.Calorie;
import com.example.exercisetrackerapp.data.model.Date;

public class BurnedCalories extends Calorie {
    String exercise;

    public BurnedCalories(float cantCalorie, Date date,String exercise) {
        super(cantCalorie, date);
        this.exercise = exercise;
    }

    public String getExercise() {
        return exercise;
    }
    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

}
