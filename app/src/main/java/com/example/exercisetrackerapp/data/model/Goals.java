package com.example.exercisetrackerapp.data.model;

public class Goals {
    private Exercise exercise;
    private Date startDate;
    private Date finalDate;
    private float time;

    public Goals(Exercise exercise, Date startDate, Date finalDate, float time) {
        this.exercise = exercise;
        this.startDate = startDate;
        this.finalDate = finalDate;
        this.time = time;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }
}
