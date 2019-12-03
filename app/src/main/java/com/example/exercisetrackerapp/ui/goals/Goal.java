package com.example.exercisetrackerapp.ui.goals;

import com.example.exercisetrackerapp.data.model.Date;

public class Goal {

    String usuario, ejercicio;
    float tiempo,calorias;
    Date fechaIni, fechaFin;
    boolean cumplida;

    public Goal(String usuario, String ejercicio, Date fechaIni, Date fechaFin, float tiempo, float calorias, boolean cumplida) {
        this.usuario = usuario;
        this.ejercicio = ejercicio;
        this.fechaIni =  fechaIni;
        this.fechaFin = fechaFin;
        this.tiempo = tiempo;
        this.calorias = calorias;
        this.cumplida = cumplida;

    }

    public String getExercise() {
        return ejercicio;
    }
    public void setExercise(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setTiempo(float tiempo) {
        this.tiempo = tiempo;
    }
    public float getTiempo() {
        return tiempo;
    }

    public float getCalorias() {
        return calorias;
    }
    public void setCalorias(float calorias) {
        this.calorias = calorias;
    }

    public boolean getCumplida() {
        return cumplida;
    }
    public void setCumplida(boolean cumplida) {
        this.cumplida = cumplida;
    }

    public Date getFechaIni() {
        return fechaIni;
    }
    public void setFechaIni(Date fechaIni ) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(Date fechaFin ) {
        this.fechaFin = fechaFin;
    }


}
