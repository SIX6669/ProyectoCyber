package modelo;

public class Computadora {
    public int ID_Computadora;
    public int estado;
    public int ID_Usuario;

    public Computadora(int ID_Computadora, int estado, int ID_Usuario) {
        this.ID_Computadora = ID_Computadora;
        this.estado = estado;
        this.ID_Usuario = ID_Usuario;
    }

    public Computadora() {
    }

    public int getID_Computadora() {
        return ID_Computadora;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getID_Usuario() {
        return ID_Usuario;
    }

    public void setID_Usuario(int nroUsuario) {
        this.ID_Usuario = nroUsuario;
    }

    @Override
    public String toString() {
        return "Computadora [idComputadora=" + ID_Computadora + ", estado=" + estado + ", nroLegajo=" + "]";
    }
}
