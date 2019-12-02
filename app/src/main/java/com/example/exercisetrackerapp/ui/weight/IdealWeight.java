package com.example.exercisetrackerapp.ui.weight;

public class IdealWeight {
    String usuario;
    int pesoIdeal;

    public IdealWeight(String usuario, int pesoIdeal) {
        this.usuario = usuario;
        this.pesoIdeal = pesoIdeal;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getPesoIdeal() {
        return pesoIdeal;
    }

    public void setPesoIdeal(int pesoIdeal) {
        this.pesoIdeal = pesoIdeal;
    }

}
