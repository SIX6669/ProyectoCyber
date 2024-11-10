package modelo;

import java.sql.Time;

public class Transaccion {
    public int ID_Transaccion;
    public Time TiempoComprado;
    public double total;
    public int ID_Usuario;

    public Transaccion(Time TiempoComprado, double total, int ID_Usuario) {
        this.TiempoComprado = TiempoComprado;
        this.total = total;
        this.ID_Usuario = ID_Usuario;
    }

    public int getID_Transaccion() {
        return ID_Transaccion;
    }

    public Time getTiempoComprado() {
        return TiempoComprado;
    }

    public void setTiempoComprado(Time tiempoComprado) {
        TiempoComprado = tiempoComprado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getID_Usuario() {
        return ID_Usuario;
    }

    public void setID_Usuario(int iD_Usuario) {
        ID_Usuario = iD_Usuario;
    }

    @Override
    public String toString() {
        return "Transaccion [ID_Transaccion=" + ID_Transaccion + ", TiempoComprado=" + TiempoComprado + ", total=" + total + ", ID_Usuario=" + ID_Usuario + "]";
    }
}
