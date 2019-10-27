package com.example.exercisetrackerapp.data.model;

public class Calorie {
    private float cantCalorie;
    Date date;

    public Calorie(float cantCalorie, Date date) {
        this.cantCalorie = cantCalorie;
        this.date = date;
    }

    public float getCantCalorie() {
        return cantCalorie;
    }

    public void setCantCalorie(float cantCalorie) {
        this.cantCalorie = cantCalorie;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

