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

    public Transaccion(int ID_Transaccion, Time TiempoComprado, double total, int ID_Usuario) {
        this.ID_Transaccion = ID_Transaccion;
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

    public double getTotal() {
        return total;
    }

    public int getID_Usuario() {
        return ID_Usuario;
    }

    @Override
    public String toString() {
        return "Transaccion [ID_Transaccion=" + ID_Transaccion + ", TiempoComprado=" + TiempoComprado + ", total=" + total + ", ID_Usuario=" + ID_Usuario + "]";
    }
}
