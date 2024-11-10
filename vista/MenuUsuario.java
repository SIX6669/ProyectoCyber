package vista;

import java.sql.SQLException;

public class MenuUsuario extends MenuIngreso {

    public MenuUsuario() {
        MenuLogueado();
    }
    public void MenuLogueado() {
        System.out.println("Bienvenido " + a.getUsuario());
        while(ejecutar) {
            System.out.println("\n=== MENÚ USUARIOS ===");
            System.out.println("1. Ver detalle de su cuenta");
            System.out.println("2. Modificar sus datos");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = sc.nextInt();
            try {
                switch(opcion) {
                    case 1:
                        c.mostrarInfoUsuario(1);
                        break;

                    case 2:
                        c.modificarUsuario();
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
            catch (SQLException e) {
                System.err.println("Error SQL: " + e.getMessage()); e.printStackTrace();
            }
        }
    }
}
