package vista;

import controlador.AdministradorController;
import controlador.ClienteController;
import modelo.Administrador;
import modelo.Cliente;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuAdm {
    private Administrador a = new Administrador();
    private ClienteController c = new ClienteController();
    private int tipoUsuario;
    private Scanner sc = new Scanner(System.in);
    private static boolean ejecutar = true;

    public MenuAdm(String usuario, String clave) {
        this.a.usuario = usuario;
        this.a.clave = clave;
    }

    public void MenuLogueado() {
        System.out.println("Bienvenido " + a.usuario);
        while (ejecutar) {
            System.out.println("\n=== MENÚ ADMINISTRADOR ===");
            System.out.println("1. Ver detalle de su cuenta");//cuenta del adm, falta hacer un read
            System.out.println("2. Modificar sus datos");// cuienta del adm, falta hacer el update
            System.out.println("3. Registrar transaccion");
            System.out.println("4. Crear nuevo cliente"); //esta en el controlador del adm
            System.out.println("5. listar Clientes"); //esta en el controlador del adm
            System.out.println("6. Buscar cliente por ID"); //esta en el controlador del adm
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = sc.nextInt();
            try {
                switch (opcion) {
                    case 1:
                        ; //no hay metodo read en administrador
                        break;
                    case 2:
                        c.modificarUsuario(); //este se puede hacer con el mismo metodo en ClienteController
                        break;
                    case 3: //ver logica de juampi
                        break;
                    case 4:
                        AdministradorController ac = new AdministradorController();
                        Cliente c = new Cliente();
                        System.out.println("Ingrese el nombre del usuario");
                        c.setNombre(sc.nextLine());
                        System.out.println("Ingrese el Apellido del usuario");
                        c.setApellido(sc.nextLine());
                        System.out.println("Ingrese el Telefono del usuario");
                        while (!sc.hasNextInt()) {
                            System.out.println("Por favor, ingrese un número de teléfono válido:");
                            sc.next(); // Descarta la entrada no válida
                        }
                        c.setTelefono(sc.nextInt());
                        sc.nextLine(); // Limpiar el buffer después de nextInt()
                        ac.crearUsuario(c);
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
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
