package vista;

import controlador.ClienteController;
import modelo.Administrador;
import modelo.Cliente;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuIngreso {
    private Administrador a = new Administrador();
    private Cliente cl = new Cliente();
    private ClienteController c = new ClienteController();
    private int tipoUsuario;
    private Scanner sc = new Scanner(System.in);
    private static boolean ejecutar = true;
    private static boolean bandera = true;

    public MenuIngreso() throws SQLException {
        mostrarMenuPrincipal();
    }

    public void mostrarMenuPrincipal() throws SQLException {
        System.out.println("¿Bienvenido desea ingresar como cliente o administrador? Ingrese 0 para Cliente - 1 para adm");
        tipoUsuario = sc.nextInt();
        sc.nextLine();
        if (tipoUsuario == 0) {
            ingresarComoCliente();
        } else {
            ingresarComoAdministrador();
        }
    }

    private void ingresarComoCliente() throws SQLException {
        do {
            System.out.println("Ingrese su usuario");
            this.cl.setUsuario(sc.nextLine());
            System.out.println("Ingrese su clave");
            this.cl.setClave(sc.nextLine());
            validarCliente(cl.getUsuario(), cl.getClave());
        } while (bandera);
        System.out.println("Bienvenido: " + cl.getNombre());
        MenuUsuario m = new MenuUsuario(cl);
        m.MenuLogueado();
    }

    private void validarCliente(String usuario, String clave) throws SQLException {
        Cliente cliente = c.autenticarUsuario(usuario, clave);
        if (cliente != null) {
            cl.setNombre(cliente.getNombre());
            cl.setApellido(cliente.getApellido());
            cl.setID_Usuario(cliente.getID_Usuario());
            cl.setTelefono(cliente.getTelefono());
            cl.setClave(cliente.getClave());
            cl.setUsuario(cliente.getUsuario());
            bandera = false;
        } else {
            System.out.println("Reintente nuevamente, credenciales erroneas");
            bandera = true;
        }
    }


    private void ingresarComoAdministrador() {
        System.out.println("Ingrese su usuario");
        this.a.usuario = sc.nextLine();
        System.out.println("Ingrese su clave");
        this.a.clave = sc.nextLine();
        MenuAdm m =new MenuAdm(a.getUsuario(), a.getClave());
        m.MenuLogueado();
        // Aquí podrías manejar la lógica para el administrador
    }
}




