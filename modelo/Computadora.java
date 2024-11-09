package modelo;

public class Computadora {
    public int idComputadora;
    public String estado;
    public int nroUsuario;

    public Computadora(int idComputadora, String estado, int nroUsuario) {
        this.idComputadora = idComputadora;
        this.estado = estado;
        this.nroUsuario = nroUsuario;
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

    public int getNroUsuario() {
        return nroUsuario;
    }

    public void setNroUsuario(int nroUsuario) {
        this.nroUsuario = nroUsuario;
    }

    @Override
    public String toString() {
        return "Computadora [idComputadora=" + idComputadora + ", estado=" + estado + ", nroLegajo=" + "]";
    }
}