package vista;

import controlador.*;
import modelo.*;
import java.sql.*;
import java.util.Scanner;

public class MenuAdm implements MenuAdmCliente, MenuAdmManager, MenuAdmComputadora, MenuUsuarioTransaccion {
    public Administrador a = new Administrador();
    public ClienteController c = new ClienteController();
    public AdministradorController ac = new AdministradorController();
    public TransaccionController transaccionController = new TransaccionController();
    public int nroLegajo;
    public Scanner sc = new Scanner(System.in);
    public static boolean ejecutar = true;
    
    public MenuAdm(Administrador a) {
        this.a.setNroLegajo(a.getNroLegajo());
        this.a.setNombre(a.getNombre());
        this.a.setApellido(a.getApellido());
        this.a.setUsuario(a.getUsuario());
        this.a.setClave(a.getClave());

    }

    public MenuAdm(String usuario, String clave) throws SQLException {
        this.a = ac.autenticarAdmin(usuario, clave); 
        if (a != null) {
            this.nroLegajo = a.getNroLegajo();
            System.out.println("Bienvenido " + a.getUsuario());
        } else {
            System.out.println("Error: usuario o clave incorrectos.");
            ejecutar = false;
        }
    }

    public MenuAdm() {}

    public void MenuLogueado() throws SQLException {
        System.out.println("Bienvenido " + a.usuario);
        
        while (ejecutar) {
            System.out.println("\n=== MENÚ DE ADMINISTRADOR ===");
            System.out.println("1. Administradores");
            System.out.println("2. Clientes");
            System.out.println("3. Computadoras");
            System.out.println("4. Ver transacciones");
            System.out.println("5. Salir");
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
                    verTransacciones(transaccionController);
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

    public void menuAdminAdmin() throws SQLException {
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

    public void menuAdminCliente() throws SQLException {
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
                modificarUsuarioPorAdmin(sc);
                break;
            case 6:
                break; 
            default:
                System.out.println("Opción no válida. Por favor, intente nuevamente.");
                break;
        }
    }

    public void menuAdminComputadoras() throws SQLException {
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