package vista;

import controlador.AdministradorController;
import controlador.ClienteController;
import modelo.Administrador;
import modelo.Cliente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuAdm implements MenuAdmCliente, MenuAdmManager, MenuAdmComputadora {
    private Administrador a = new Administrador();
    private ClienteController c = new ClienteController();
    AdministradorController ac = new AdministradorController();
    private int nroLegajo;
    private Scanner sc = new Scanner(System.in);
    private static boolean ejecutar = true;
    

    public MenuAdm(Administrador a) {
        this.a.setNroLegajo(a.getNroLegajo());
        this.a.setNombre(a.getNombre());
        this.a.setApellido(a.getApellido());
        this.a.setUsuario(a.getUsuario());
        this.a.setClave(a.getClave());

    }

    public MenuAdm(String usuario, String clave) throws SQLException {
        this.a = ac.autenticarAdmin(usuario, clave); // Método de autenticación que devuelve un Administrador
        if (a != null) {
            this.nroLegajo = a.getNroLegajo(); // Obtiene el ID del administrador autenticado
            System.out.println("Bienvenido " + a.getUsuario());
        } else {
            System.out.println("Error: usuario o clave incorrectos.");
            ejecutar = false;
        }
    }

    public MenuAdm(){
    }

    /*public void MenuLogueado() {
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
            System.out.println("10. Modificar cliente");
            System.out.println("11. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = sc.nextInt();
            try {
                switch (opcion) {
                    case 1:
                        ac.seleccAdminLegajo(12);//id que busca esta fijo hay que dinamizarlo una vez que inicie sesion con un usuario real
                        break;
                    case 2:
                       modificarDatos(sc, ac);
                       break;
                    case 3:
                      mostrarAdmins();
                      break;
                    case 4:
                       crearAdmin(sc);
                       break;
                    case 5:
                        eliminarAdmin(sc, ac);
                        break;
                    case 6:
                        crearUsuario(sc);
                        break;
                    case 7:
                        mostrarUsuarios();
                        break;
                    case 8:
                       mostrarUsuarioID(sc);
                       break;
                    case 9:
                        eliminarUsuario(sc, c);
                        break;
                    case 10:

                    case 11:
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
    } */

    public void MenuLogueado() throws SQLException {
        System.out.println("Bienvenido " + a.usuario);
        
        while (ejecutar) {
            System.out.println("\n=== MENÚ DE ADMINISTRADOR ===");
            System.out.println("1. Administradores");
            System.out.println("2. Clientes");
            System.out.println("3. Computadoras");
            System.out.println("4. Ver transacciones");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            
            int opcionPrincipal = sc.nextInt();
            
            switch (opcionPrincipal) {
                case 1:
                    menuAdminAdmin(); 
                    break;
                case 2:
                    menuAdminCliente();
                    break;
                case 3:
                    menuAdminComputadoras();
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
        }
    }

    private void menuAdminAdmin() throws SQLException {
        System.out.println("\n=== MENÚ PARA ADMINISTRADORES ===");
        System.out.println("1. Ver detalles de su cuenta");
        System.out.println("2. Modificar datos");
        System.out.println("3. Ver Administradores");
        System.out.println("4. Crear nuevo Administrador");
        System.out.println("5. Eliminar Administrador");
        System.out.println("6. Volver al menú principal");
        System.out.print("Seleccione una opción: ");

        int opcionAdmin = sc.nextInt();
        switch (opcionAdmin) {
            case 1:
                mostrarAdminPorId(a);
                break;
            case 2:
                modificarDatos(sc, ac, a);
                break;
            case 3:
                mostrarAdmins();
                break;
            case 4:
                crearAdmin(sc);
                break;
            case 5:
                eliminarAdmin(sc, ac);
                break;
            case 6:
                break; 
            default:
                System.out.println("Opción no válida. Por favor, intente nuevamente.");
                break;
        }
    }

    private void menuAdminCliente() throws SQLException {
        System.out.println("\n=== MENÚ PARA ADMINISTRAR CLIENTES ===");
        System.out.println("1. Crear nuevo cliente");
        System.out.println("2. Ver Clientes");
        System.out.println("3. Ver Cliente por ID");
        System.out.println("4. Eliminar Cliente");
        System.out.println("5. Modificar Cliente");
        System.out.println("6. Volver al menú principal");
        System.out.print("Seleccione una opción: ");

        int opcionCliente = sc.nextInt();
        switch (opcionCliente) {
            case 1:
                crearUsuario(sc);
                break;
            case 2:
                mostrarUsuarios();
                break;
            case 3:
                mostrarUsuarioID(sc);
                break;
            case 4:
                eliminarUsuario(sc, c);
                break;
            case 5:
                modificarUsuario(opcionCliente, sc);
                break;
            case 6:
                break; 
            default:
                System.out.println("Opción no válida. Por favor, intente nuevamente.");
                break;
        }
    }

    private void menuAdminComputadoras() throws SQLException {
        System.out.println("\n=== MENÚ PARA ADMINISTRAR COMPUTADORAS ===");
        System.out.println("1. Modificar Computadora");
        System.out.println("2. Listar Computadoras");
        System.out.println("3. Crear nueva Computadora");
        System.out.println("4. Eliminar Computadora");
        System.out.println("5. Volver al menú principal");
        System.out.print("Seleccione una opción: ");

        int opcionAdmin = sc.nextInt();
        switch (opcionAdmin) {
            case 1:
                actualizarComputadora(sc);
                break;
            case 2:
                verComputadoras();
                break;
            case 3:
                agregarComputadora(sc);
                break;
            case 4:
                eliminarComputadora(sc);
                break;
            case 5:
                break;
            default:
                System.out.println("Opción no válida. Por favor, intente nuevamente.");
                break;
        }
    }
}