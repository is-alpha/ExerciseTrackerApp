package com.example.exercisetrackerapp.ui.sleep;

public class TimeSleep {
    private String correo;
    private float horas;
    private float horasExtras;

    public TimeSleep(String correo, float horas, float horasExtras) {
        this.correo=correo;
        this.horas = horas;
        this.horasExtras = horasExtras;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public float getHoras() {
        return horas;
    }

    public void setHoras(float horas) {
        this.horas = horas;
    }

    public float getHorasExtras() {
        return horasExtras;
    }

    public void setHorasExtras(float horasExtras) {
        this.horasExtras = horasExtras;
    }
}
