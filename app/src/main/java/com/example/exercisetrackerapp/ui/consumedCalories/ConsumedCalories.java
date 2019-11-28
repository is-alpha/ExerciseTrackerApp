package com.example.exercisetrackerapp.ui.consumedCalories;

import com.example.exercisetrackerapp.data.model.Calorie;
import com.example.exercisetrackerapp.data.model.Date;

public class ConsumedCalories extends Calorie {
    String usuario;
    int calExtra;

    public ConsumedCalories(String usuario, int calConsumidas, int calExtra, Date date) {
        super(calConsumidas,date);
        this.usuario = usuario;
        this.calExtra = calExtra;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getCalExtra() {
        return calExtra;
    }

    public void setCalExtra(int calExtra) {
        this.calExtra = calExtra;
    }
}
