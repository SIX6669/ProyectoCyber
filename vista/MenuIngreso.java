package vista;
import controlador.ClienteController;
import modelo.Administrador;

import java.util.Scanner;

public class MenuIngreso {
    private Administrador a = new Administrador();
    private ClienteController c = new ClienteController();
    Scanner sc = new Scanner(System.in);
    private static boolean ejecutar = true;

    public MenuIngreso(){
        System.out.println("Bienvenido ingrese su usuario");
        this.a.usuario = sc.nextLine();
        System.out.println("Ingrese su clave");
        this.a.clave = sc.nextLine();
    }
    public void MenuLogueado() {
        System.out.println("Bienvenido " + a.nombre);
        while(ejecutar) {
            System.out.println("\n=== MENÚ USUARIOS ===");
            System.out.println("1. Agregar Usuario");
            System.out.println("2. Listar Usuarios");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = sc.nextInt();

            switch(opcion) {
                case 1:
                    System.out.println("Ingrese Nombre: ");
                    String nombre = sc.nextLine();
                    String apellido = sc.nextLine();
                    int telefono = sc.nextInt();
                    c.agregarCliente(nombre, apellido, telefono);
                    break;

                case 2:
                    c.listarClientes();
                    break;

                case 3:
                    ejecutar = false;
                    System.out.println("¡Hasta pronto!");
                    break;

                default:
                    System.out.println("Opción no válida. Por favor, intente nuevamente.");
                    break;
            }
        }
    }
}

