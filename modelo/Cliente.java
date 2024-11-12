package modelo;

import java.sql.*;

public class Cliente {
    public int ID_Usuario;
    public String nombre;
    public String apellido;
    public String usuario;
    public String clave;
    public String telefono;
    public Time Tiempo;

    public Cliente() {}

    public Cliente(int ID_Usuario, String nombre, String apellido, String usuario, String clave, String telefono, Time Tiempo) {
        this.ID_Usuario = ID_Usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.clave = clave;
        this.telefono = telefono;
        this.Tiempo = Tiempo;
    }

    public Cliente(int ID_Usuario, String nombre, String apellido, String usuario, String clave, String telefono) {
        this.ID_Usuario = ID_Usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.clave = clave;
        this.telefono = telefono;
    }

    public Cliente(int ID_Usuario, String nombre, String apellido, String telefono) {
        this.ID_Usuario = ID_Usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    public Cliente(int ID_Usuario, String nombre, String apellido, String telefono, Time tiempo) {
        this.ID_Usuario = ID_Usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.Tiempo = tiempo;
    }

    public int getID_Usuario() {
        return ID_Usuario;
    }

    public void setID_Usuario(int iD_Usuario) {
        ID_Usuario = iD_Usuario;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Time getTiempo() {
        return Tiempo;
    }

    public void setTiempo(Time tiempo) {
        Tiempo = tiempo;
    }
}