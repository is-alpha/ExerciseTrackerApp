package com.example.exercisetrackerapp.ui.exercises;

import com.example.exercisetrackerapp.data.model.Date;

public class ActualExercise {

    String usuario, ejercicio;

    public ActualExercise (String usuario, String ejercicio) {
        this.usuario = usuario;
        this.ejercicio = ejercicio;
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

}
