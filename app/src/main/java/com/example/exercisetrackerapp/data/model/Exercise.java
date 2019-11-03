package com.example.exercisetrackerapp.data.model;

import android.media.Image;

public class Exercise {
    private String name;
    private Type type;
    private Calorie calories;
    private Date time;
    private Image image;
    private int Thumbnail;

    public Exercise(String name, Type type, Calorie calories, Date time, Image image) {
        this.name = name;
        this.type = type;
        this.calories = calories;
        this.time = time;
        this.image = image;
    }

    public Exercise(String name, Type type, int thumbnail) {
        this.name = name;
        this.type = type;
        this.calories = calories;
        Thumbnail = thumbnail;
    }

    public int getThumbnail() { return Thumbnail; }

    public void setThumbnail(int thumbnail) { Thumbnail = thumbnail; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Calorie getCalories() {
        return calories;
    }

    public void setCalories(Calorie calories) {
        this.calories = calories;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
