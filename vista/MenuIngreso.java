package vista;

import controlador.ClienteController;
import modelo.Administrador;
import modelo.Cliente;

import java.util.Scanner;

public class MenuIngreso {
    private Administrador a = new Administrador();
    private Cliente cl = new Cliente();
    private ClienteController c = new ClienteController();
    private int tipoUsuario;
    private Scanner sc = new Scanner(System.in);
    private static boolean ejecutar = true;

    public MenuIngreso() {
        mostrarMenuPrincipal();
    }

    public void mostrarMenuPrincipal() {
        System.out.println("¿Bienvenido desea ingresar como cliente o administrador? Ingrese 0 para Cliente - 1 para adm");
        tipoUsuario = sc.nextInt();
        sc.nextLine();
        if (tipoUsuario == 0) {
            ingresarComoCliente();
        } else {
            ingresarComoAdministrador();
        }
    }

    private void ingresarComoCliente() {
        System.out.println("Ingrese su usuario");
        this.cl.setUsuario(); = sc.nextLine();
        System.out.println("Ingrese su clave");
        this.cl.setClave(sc.nextLine());
        MenuUsuario m = new MenuUsuario(a.getUsuario(), a.getClave());
        m.MenuLogueado();
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




