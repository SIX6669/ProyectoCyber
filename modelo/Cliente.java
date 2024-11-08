package modelo;

public class Cliente {
    public int nroUsuario;
    public String nombre;
    public String apellido;
    public String telefono;
    public String email;
    public String tiempoAdquirido;

    public Cliente(int nroUsuario, String nombre, String apellido, String telefono, String email, String tiempoAdquirido) {
        this.nroUsuario = nroUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTiempoAdquirido() {
        return tiempoAdquirido;
    }

    public void setTiempoAdquirido(String tiempoAdquirido) {
        this.tiempoAdquirido = tiempoAdquirido;
    }
}