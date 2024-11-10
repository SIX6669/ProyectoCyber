package vista;

import controlador.ClienteController;
import modelo.Administrador;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuUsuario  {
    private Administrador a = new Administrador();
    private ClienteController c = new ClienteController();
    private int tipoUsuario;
    private Scanner sc = new Scanner(System.in);
    private static boolean ejecutar = true;

    public MenuUsuario(String usuario, String clave) {
        this.a.usuario = usuario;
        this.a.clave = clave;
    }

    public void MenuLogueado() {
        System.out.println("Bienvenido " + a.usuario);
        while (ejecutar) {
            System.out.println("\n=== MENÚ USUARIOS ===");
            System.out.println("1. Ver detalle de su cuenta");
            System.out.println("2. Modificar sus datos");
            //Aca agregar opcion para realizar transaccion
            System.out.println("3. Realizar compra");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = sc.nextInt();
            try {
                switch (opcion) {
                    case 1:
                        c.mostrarInfoUsuario(1);
                        break;
                    case 2:
                        c.modificarUsuario();
                        break;
                    case 3:
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
