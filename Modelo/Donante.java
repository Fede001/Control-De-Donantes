package com.fede.nextu.controldedonantes.Modelo;

import java.io.Serializable;

/**
 * Created by JHOAN on 16/09/2017.
 */

public class Donante implements Serializable {

    private int Edad,Id,Peso;
    private double Estatura;
    private String Nombre,Apellido,Sangre,Rh;

    public Donante(int id, String nombre, String apellido, int edad, int peso, double estatura, String sangre, String rh) {
        Edad = edad;
        Id = id;
        Peso = peso;
        Estatura = estatura;
        Nombre = nombre;
        Apellido = apellido;
        Sangre = sangre;
        Rh = rh;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int edad) {
        Edad = edad;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getPeso() {
        return Peso;
    }

    public void setPeso(int peso) {
        Peso = peso;
    }

    public double getEstatura() {
        return Estatura;
    }

    public void setEstatura(double estatura) {
        Estatura = estatura;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getSangre() {
        return Sangre;
    }

    public void setSangre(String sangre) { Sangre = sangre; }

    public String getRh() {
        return Rh;
    }

    public void setRh(String rh) {
        Rh = rh;
    }


}
