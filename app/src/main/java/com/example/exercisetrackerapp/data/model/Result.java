package com.example.exercisetrackerapp.data.model;

import java.sql.Time;

public class Result {
    private Calorie caloriesBurned;

    public float calculateBurnedCal(TimeRange range){
        float totalCalBurned = 0;
        return totalCalBurned;
    }

    public void showConsumedBurned(float consumedCal, float burnedCal,TimeRange range) {
        /*Procedimiento que grafica calorias consumidas y gastadas (US10)*/
    }

    public void showExerciseCalories(TimeRange range) {
        /*Procedimiento que muestra las calorias de cada ejercicio*/
    }

    public float calculateAvergaeCalories(TimeRange range) {
        float avgCalories =0;
        return avgCalories;
    }

    public void showAverageCalories(TimeRange range){

    }

    public float calculateBasalMetabolism(){
        float basalMetab = 0;
        return basalMetab;
    }

    public void showWeightLoss(TimeRange range) {

    }

    public float calculateBMI(float height, float weight) {
        float actualBMI = 0;
        return actualBMI;
    }
}
