package com.example.exercisetrackerapp.ui.exercises;

import com.example.exercisetrackerapp.data.model.Date;

public class ActualExercise {

    String usuario, ejercicio;
    float objTiempo, objDistancia;
    int objRep;

    public ActualExercise (String usuario, String ejercicio, float objTiempo, float objDistancia, int objRep) {
        this.usuario = usuario;
        this.ejercicio = ejercicio;
        this.objTiempo = objTiempo;
        this.objDistancia = objDistancia;
        this.objRep = objRep;
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

    public float getObjTiempo() {
        return objTiempo;
    }

    public float getObjDistancia() {
        return objDistancia;
    }

    public void setObjTiempo(float objTiempo) {
        this.objTiempo = objTiempo;
    }

    public void setObjDistancia (float objDistancia ) {
        this.objDistancia = objDistancia ;
    }

    public int getObjRep() {
        return objRep;
    }

    public void setObjRep(int objRep) {
        this.objRep = objRep;
    }

}
