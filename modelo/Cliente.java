package modelo;

import java.sql.*;

public class Cliente {
    public int ID_Usuario;
    public String nombre;
    public String apellido;
    public String usuario;
    public String clave;
    public String telefono;
    public Time tiempoAdquirido;

    public Cliente() {
    }

    public Cliente( String nombre, String apellido, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    public Cliente(int ID_Usuario, String nombre, String apellido, String telefono, Time tiempo) {
        this.ID_Usuario = ID_Usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }
    
    public Cliente(int ID_Usuario, String nombre, String apellido, String telefono) {
        this.ID_Usuario = ID_Usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getID_Usuario() {
        return ID_Usuario;
    }

    public void setID_Usuario(int ID_Usuario) {
        this.ID_Usuario = ID_Usuario;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
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