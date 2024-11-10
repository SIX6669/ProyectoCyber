package vista;

import controlador.ClienteController;
import modelo.Administrador;

import java.util.Scanner;

public class MenuIngreso {
    protected Administrador a = new Administrador();
    protected ClienteController c = new ClienteController();
    protected int tipoUsuario;
    Scanner sc = new Scanner(System.in);
    protected static boolean ejecutar = true;

    public MenuIngreso() {
        mostrarMenuPrincipal();
    }

    public void mostrarMenuPrincipal() {
        System.out.println("¿Bienvenido desea ingresar como cliente o administrador? Ingrese 0 para Cliente - 1 para adm");
        tipoUsuario = sc.nextInt();
        sc.nextLine();
        if (tipoUsuario == 0) {
            System.out.println("Ingrese su usuario");
            this.a.usuario = sc.nextLine();
            System.out.println("Ingrese su clave");
            this.a.clave = sc.nextLine();
            MenuUsuario m = new MenuUsuario();
        } else {
            System.out.println("Ingrese su usuario");
            this.a.usuario = sc.nextLine();
            System.out.println("Ingrese su clave");
            this.a.clave = sc.nextLine();
            // Aquí podrías manejar la lógica para el administrador
        }
    }
}


