package modelo;

import java.sql.*;

public class Cliente {
    public int ID_Usuario;
    public String nombre;
    public String apellido;
    public int telefono;
    public Time tiempoAdquirido;
    public Cliente() {
    }

    public Cliente( String nombre, String apellido, int telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }
    
    public Cliente(int ID_Usuario, String nombre, String apellido, int telefono, Time tiempoAdquirido) {
        this.ID_Usuario = ID_Usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.tiempoAdquirido = tiempoAdquirido;
    }

    public Cliente(int ID_Usuario, String nombre, String apellido, int telefono) {
        this.ID_Usuario = ID_Usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    public int getID_Usuario() {
        return ID_Usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public Time getTiempoAdquirido() {
        return tiempoAdquirido;
    }

    public void setTiempoAdquirido(Time tiempoAdquirido) {
        this.tiempoAdquirido = tiempoAdquirido;
    }

    @Override
    public String toString() {
        return "Cliente [ID_Usuario=" + ID_Usuario + ", nombre=" + nombre + ", apellido=" + apellido + ", telefono=" + telefono + ", tiempoAdquirido=" + tiempoAdquirido + "]";
    }
}