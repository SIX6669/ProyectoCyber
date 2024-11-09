package modelo;

public class Computadora {
    public int idComputadora;
    public String estado;
    public int nroLegajo;

    public Computadora(int idComputadora, String estado, int nroLegajo) {
        this.idComputadora = idComputadora;
        this.estado = estado;
        this.nroLegajo = nroLegajo;
    }
    
    public int getIdComputadora() {
        return idComputadora;
    }

    public void setIdComputadora(int idComputadora) {
        this.idComputadora = idComputadora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getNroLegajo() {
        return nroLegajo;
    }

    public void setNroLegajo(int nroLegajo) {
        this.nroLegajo = nroLegajo;
    }

    @Override
    public String toString() {
        return "Computadora [idComputadora=" + idComputadora + ", estado=" + estado + ", nroLegajo=" + nroLegajo + "]";
    }
}