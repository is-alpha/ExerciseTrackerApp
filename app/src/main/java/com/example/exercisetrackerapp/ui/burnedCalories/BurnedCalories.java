package com.example.exercisetrackerapp.ui.burnedCalories;

import com.example.exercisetrackerapp.data.model.Calorie;
import com.example.exercisetrackerapp.data.model.Date;

public class BurnedCalories extends Calorie {
    String usuario, ejercicio;
    int tiempo;

    public BurnedCalories(String usuario, float cantCalorias, Date date,String ejercicio, int tiempo) {
        super(cantCalorias,date);
        this.usuario = usuario;
        this.ejercicio = ejercicio;
        this.tiempo = tiempo;
    }

    public String getExercise() {
        return ejercicio;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setExercise(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public int getTiempo() {
        return tiempo;
    }
}
