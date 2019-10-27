package com.example.exercisetrackerapp.data.model;

public class Sleep {
    private float timeSleep;
    private float timeNap;
    private Calorie calories;

    public Sleep(float timeSleep) {
        this.timeSleep = timeSleep;
    }

    public float getTimeSleep() {
        return timeSleep;
    }

    public void setTimeSleep(float timeSleep) {
        this.timeSleep = timeSleep;
    }

    public float getTimeNap() {
        return timeNap;
    }

    public void setTimeNap(float timeNap) {
        this.timeNap = timeNap;
    }

    public Calorie getCalories() {
        return calories;
    }

    public void setCalories(Calorie calories) {
        this.calories = calories;
    }
}
