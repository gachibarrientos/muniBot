package com.bradleybossard.androidprogramabdemo.Modelo;

/**
 * Created by Gachi on 4/6/2016.
 */
public class User {
    private String nombre;
    private String apellido;
    private int edad;
    private String email;
    private String contraseña;
    private float educ;

    public User(String nombre, String contraseña, String email, int edad, String apellido) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.email = email;
        this.edad = edad;
        this.apellido = apellido;
    }

    public User() {
    }
    public User(int edad, float educ){
        this.edad = edad;
        this.educ = educ;
    }
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
