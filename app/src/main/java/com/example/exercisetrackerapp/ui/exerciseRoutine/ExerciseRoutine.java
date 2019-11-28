package com.example.exercisetrackerapp.ui.exerciseRoutine;

import com.example.exercisetrackerapp.data.model.Date;

public class ExerciseRoutine {
    String usuario, ejercicio;
    int tiempo;
    Date dInicial, dFinal;

    public ExerciseRoutine(String usuario, int tiempo, Date dInicial, Date dFinal, String ejercicio) {
        this.usuario = usuario;
        this.ejercicio = ejercicio;
        this.tiempo = tiempo;
        this.dInicial = dInicial;
        this.dFinal = dFinal;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public Date getdInicial() {
        return dInicial;
    }

    public void setdInicial(Date dInicial) {
        this.dInicial = dInicial;
    }

    public Date getdFinal() {
        return dFinal;
    }

    public void setdFinal(Date dFinal) {
        this.dFinal = dFinal;
    }
}
