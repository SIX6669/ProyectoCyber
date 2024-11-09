package modelo;

import java.sql.*;

public class Transaccion {
    public int idTransaccion;
    public int nroLegajo;
    public int nroUsuario;
    public int tiempoAdquirido;
    public Date fecha;
    public double total;
    
    public Transaccion(int idTransaccion, int nroLegajo, int nroUsuario, int tiempoAdquirido, Date fecha, double total) {
        this.idTransaccion = idTransaccion;
        this.nroLegajo = nroLegajo;
        this.nroUsuario = nroUsuario;
        this.tiempoAdquirido = tiempoAdquirido;
        this.fecha = fecha;
        this.total = total;
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public int getNroLegajo() {
        return nroLegajo;
    }

    public void setNroLegajo(int nroLegajo) {
        this.nroLegajo = nroLegajo;
    }

    public int getNroUsuario() {
        return nroUsuario;
    }

    public void setNroUsuario(int nroUsuario) {
        this.nroUsuario = nroUsuario;
    }

    public int getTiempoAdquirido() {
        return tiempoAdquirido;
    }

    public void setTiempoAdquirido(int tiempoAdquirido) {
        this.tiempoAdquirido = tiempoAdquirido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Transaccion [idTransaccion=" + idTransaccion + ", nroLegajo=" + nroLegajo + ", nroUsuario=" + nroUsuario + ", tiempoAdquirido=" + tiempoAdquirido + ", fecha=" + fecha + ", total=" + total + "]";
    }
}