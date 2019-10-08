package com.example.exercisetrackerapp.ui.registro;

import java.sql.Date;

public class DatosRegistro {
    String id,nombre,correo,contrasena,vcontrasena,ocupacion;
    int fecha;
    float altura,peso;

    public DatosRegistro(String id,String nombre, String correo, String contrasena, String vcontrasena, String ocupacion, int fecha, float altura, float peso) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.vcontrasena = vcontrasena;
        this.ocupacion = ocupacion;
        this.fecha = fecha;
        this.altura = altura;
        this.peso = peso;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getVcontrasena() {
        return vcontrasena;
    }

    public void setVcontrasena(String vcontrasena) {
        this.vcontrasena = vcontrasena;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public int getFecha() {
        return fecha;
    }

    public void setFecha(int fecha) {
        this.fecha = fecha;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }
}
