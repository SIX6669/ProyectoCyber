package vista;

import controlador.ClienteController;
import modelo.Administrador;
import modelo.Cliente;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuUsuario implements MenuAdmCliente {
    private Administrador a = new Administrador();
    private Cliente cl;
    private ClienteController c = new ClienteController();
    private Scanner sc = new Scanner(System.in);
    private static boolean ejecutar = true;

    public MenuUsuario(Cliente cl) {
        this.cl = new Cliente();
        this.cl.setID_Usuario(cl.getID_Usuario());
        this.cl.setNombre(cl.getNombre());
        this.cl.setApellido(cl.getApellido());
        this.cl.setTelefono(cl.getTelefono());
        this.cl.setUsuario(cl.getUsuario());
        this.cl.setClave(cl.getClave());
    }


    public void MenuLogueado() {
        while (ejecutar) {
            System.out.println("\n=== MENÚ USUARIOS ===");
            System.out.println("1. Ver detalle de su cuenta");
            System.out.println("2. Modificar sus datos");
            System.out.println("3. Realizar compra");
            System.out.println("4. Utilizar computadora");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = sc.nextInt();
            try {
                switch (opcion) {
                    case 1:
                        c.mostrarInfoUsuario(cl.getID_Usuario());
                        break;
                    case 2:
                        modificarUsuario(cl.getID_Usuario(), sc);
                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:
                        ejecutar = false;
                        System.out.println("¡Hasta pronto!");
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, intente nuevamente.");
                        break;
                }
            } catch (SQLException e) {
                System.err.println("Error SQL: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}