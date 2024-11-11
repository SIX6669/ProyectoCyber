package vista;

import controlador.AdministradorController;
import controlador.ClienteController;
import modelo.Administrador;
import modelo.Cliente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuAdm implements MenuAdmCliente, MenuAdmManager{
    private Administrador a = new Administrador();
    private ClienteController c = new ClienteController();
    AdministradorController ac = new AdministradorController();
    private int tipoUsuario;
    private Scanner sc = new Scanner(System.in);
    private static boolean ejecutar = true;

    public MenuAdm(String usuario, String clave) {
        this.a.usuario = usuario;
        this.a.clave = clave;
    }
    public MenuAdm(){}

    public void MenuLogueado() {
        System.out.println("Bienvenido " + a.usuario);
        while (ejecutar) {
            System.out.println("\n=== MENÚ ADMINISTRADOR ===");
            System.out.println("1. Ver detalle de su cuenta");
            System.out.println("2. Modificar sus datos");
            System.out.println("3. Listar Administradores");
            System.out.println("4. Crear nuevo Admin");
            System.out.println("5. Eliminar admin");
            System.out.println("6. Crear nuevo cliente");
            System.out.println("7. listar Clientes");
            System.out.println("8. Buscar cliente por ID");
            System.out.println("9. Eliminar cliente");
            System.out.println("10. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = sc.nextInt();
            try {
                switch (opcion) {
                    case 1:
                        ac.seleccAdminLegajo(12);//id que busca esta fijo hay que dinamizarlo una vez que inicie sesion con un usuario real
                        break;
                    case 2:
                       modificarDatos(sc, ac);
                    case 3:
                      mostrarAdmins();
                    case 4:
                       crearAdmin(sc);
                    case 5:
                        eliminarAdmin(sc, ac);
                    case 6:
                        crearUsuario(sc);
                    case 7:
                        mostrarUsuarios();
                    case 8:
                       mostrarUsuarioID(sc);
                    case 9:
                        eliminarUsuario(sc, c);
                    case 10:
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
