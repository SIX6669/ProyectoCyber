package modelo;

import java.sql.*;

public class Cliente {
    public int nroUsuario;
    public String nombre;
    public String apellido;
    public int telefono;
    public Time tiempoAdquirido;

    public Cliente(int nroUsuario, String nombre, String apellido, int telefono, Time tiempoAdquirido) {
        this.nroUsuario = nroUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.tiempoAdquirido = tiempoAdquirido;
    }

    public int getNroUsuario() {
        return nroUsuario;
    }

    public void setNroUsuario(int nroUsuario) {
        this.nroUsuario = nroUsuario;
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
        return "Cliente [nroUsuario=" + nroUsuario + ", nombre=" + nombre + ", apellido=" + apellido + ", telefono=" + telefono + ", tiempoAdquirido=" + tiempoAdquirido + "]";
    }
}