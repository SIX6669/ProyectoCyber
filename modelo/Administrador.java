package modelo;

public class Administrador {
    public int nroLegajo;
    public String nombre;
    public String apellido;
    public String usuario;
    public String clave;

    public Administrador(int nroLegajo, String nombre, String apellido, String usuario, String clave) {
        this.nroLegajo = nroLegajo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.clave = clave;
    }

    public int getNroLegajo() {
        return nroLegajo;
    }

    public void setNroLegajo(int nroLegajo) {
        this.nroLegajo = nroLegajo;
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

    @Override
    public String toString() {
        return "Administrador [nroLegajo=" + nroLegajo + ", nombre=" + nombre + ", apellido=" + apellido + ", usuario=" + usuario + ", clave=" + clave + "]";
    }
}
