package com.example.exercisetrackerapp.ui.registro;

import com.example.exercisetrackerapp.data.model.Date;

public class DatosRegistro {
    String id,nombre,correo,contrasena,vcontrasena,ocupacion,genero;
    Date fecha, creacionCuenta;
    float altura,peso,hsueno,hextras,calQuemadas,calConsumidas, pesoInicial;

    public DatosRegistro(String id, String nombre, String correo, String contrasena, String vcontrasena, String ocupacion, Date fecha, float altura, float peso, float hsueno, float calQuemadas, float calConsumidas, String genero) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.vcontrasena = vcontrasena;
        this.ocupacion = ocupacion;
        this.fecha = fecha;
        this.altura = altura;
        this.peso = peso;
        this.hsueno = hsueno;
        this.calQuemadas = calQuemadas;
        this.calConsumidas = calConsumidas;
        this.genero = genero;
    }

    public DatosRegistro(String id, String nombre, String correo, String contrasena,
                         String ocupacion, Date fecha, float altura, float peso, float hsueno,
                         float calQuemadas, float calConsumidas, String genero, Date creacionCuenta,
                         float pesoInicial) {

        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.ocupacion = ocupacion;
        this.fecha = fecha;
        this.altura = altura;
        this.peso = peso;
        this.hsueno = hsueno;
        this.calQuemadas = calQuemadas;
        this.calConsumidas = calConsumidas;
        this.genero = genero;

        this.pesoInicial = pesoInicial;
        this.creacionCuenta = creacionCuenta;
    }

    public DatosRegistro(String correo){
        this.correo=correo;
    }
    public DatosRegistro() {
    }

    public DatosRegistro(float hsueno, float hextras) {
        this.hsueno = hsueno;
        this.hextras = hextras;
    }
    public DatosRegistro(float calQuemadas) {
        this.calQuemadas = calQuemadas;
    }

    public float getHSueno() {
        return hsueno;
    }

    public void setHSueno(float hSueno) {
        this.hsueno = hsueno;
    }

    public float getCalQuemadas() {
        return calQuemadas;
    }

    public void setCalQuemadas(float calQuemadas) {
        this.calQuemadas = calQuemadas;
    }

    public float getCalConsumidas() {
        return calConsumidas;
    }

    public void setCalConsumidas(float calConsumidas) {
        this.calConsumidas = calConsumidas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
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

    public Date getCreacionCuenta() {
        return creacionCuenta;
    }

    public void setCreacionCuenta(Date creacionCuenta) {
        this.creacionCuenta = creacionCuenta;
    }

    public float getPesoInicial() {
        return pesoInicial;
    }

    public void setPesoInicial(float pesoInicial) {
        this.pesoInicial = pesoInicial;
    }
}
