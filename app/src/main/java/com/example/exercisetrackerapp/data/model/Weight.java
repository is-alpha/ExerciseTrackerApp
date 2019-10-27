package com.example.exercisetrackerapp.data.model;

public class Weight {
    private float kilograms;
    private Date date;

    public Weight(float kilograms, Date date) {
        this.kilograms = kilograms;
        this.date = date;
    }

    public float getKilograms() {
        return kilograms;
    }

    public void setKilograms(float kilograms) {
        this.kilograms = kilograms;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
