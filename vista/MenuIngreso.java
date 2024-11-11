package vista;

import controlador.*;
import modelo.*;

import java.util.Scanner;
import java.sql.*;

import static BD.util.DBConnection.setConnection;

public class MenuIngreso {
    public static void main(String[] args) throws SQLException {
        MenuIngreso menuIngreso = new MenuIngreso();
        try {
            menuIngreso.iniciarSesionAdmin();
        } catch (SQLException e) {
            System.err.println("Error en la base de datos: " + e.getMessage());
        }
    }

    private Administrador a = new Administrador();
    private AdministradorController ac = new AdministradorController();
    private Cliente cl = new Cliente();
    private ClienteController c = new ClienteController();
    private int tipoUsuario;
    private Scanner sc = new Scanner(System.in);
    private static boolean ejecutar = true;
    private static boolean bandera = true;

    public MenuIngreso() throws SQLException {
        mostrarMenuPrincipal();
    }

    public void mostrarMenuPrincipal() throws SQLException {
        System.out.println("¿Bienvenido desea ingresar como cliente o administrador? Ingrese 0 para Cliente - 1 para adm");
        tipoUsuario = sc.nextInt();
        sc.nextLine(); 
        if (tipoUsuario == 0) {
            ingresarComoCliente();
        } else {
            iniciarSesionAdmin();
        }
    }

    private void ingresarComoCliente() throws SQLException {
        int opcion = 0;
        System.out.println("Si ya tiene usuario creado ingrese 0, en caso contrario, para registrarse ingrese 1");
        opcion = sc.nextInt();
        sc.nextLine(); 
        if (opcion == 0) {
            do {
                System.out.println("Ingrese su usuario");
                this.cl.setUsuario(sc.nextLine());
                System.out.println("Ingrese su clave");
                this.cl.setClave(sc.nextLine());
                validarCliente(cl.getUsuario(), cl.getClave());
            } while (bandera);
            System.out.println("Bienvenido: " + cl.getNombre());
            MenuUsuario m = new MenuUsuario(cl);
            m.MenuLogueado();
        } else {
            cl = registroUsuario();
            System.out.println("Bienvenido: " + cl.getNombre());
            MenuUsuario m = new MenuUsuario(cl);
            m.MenuLogueado();
        }
    }

    private Cliente registroUsuario() throws SQLException {
        Cliente nuevoCliente = new Cliente();

        System.out.println("Ingrese su nombre:");
        nuevoCliente.setNombre(sc.nextLine());

        System.out.println("Ingrese su apellido:");
        nuevoCliente.setApellido(sc.nextLine());

        System.out.println("Ingrese su teléfono:");
        String telefono;
        while (true) {
        telefono = sc.nextLine();
        if (telefono.matches("[0-9]+")) {
            nuevoCliente.setTelefono(telefono);
            break;
        } else {
            System.out.println("Por favor, ingrese un número de teléfono válido (solo números):");
        }
    }

        System.out.println("Ingrese su usuario:");
        nuevoCliente.setUsuario(sc.nextLine());

        System.out.println("Ingrese su clave:");
        nuevoCliente.setClave(sc.nextLine());

        String sql = "INSERT INTO Cliente (Nombre, Apellido, Telefono, usuario, clave) VALUES (?, ?, ?, ?, ?)";
        String generatedColumns[] = { "ID_Usuario" };

        try (Connection connection = setConnection();
             PreparedStatement ps = connection.prepareStatement(sql, generatedColumns)) {

            ps.setString(1, nuevoCliente.getNombre());
            ps.setString(2, nuevoCliente.getApellido());
            ps.setString(3, nuevoCliente.getTelefono());
            ps.setString(4, nuevoCliente.getUsuario());
            ps.setString(5, nuevoCliente.getClave());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idUsuario = rs.getInt(1);
                        nuevoCliente.setID_Usuario(idUsuario);
                        System.out.println("Registro exitoso. Su ID de usuario es: " + idUsuario);
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al registrar el usuario: " + e.getMessage());
            e.printStackTrace();
        }

        return nuevoCliente;
    }

    private void validarCliente(String usuario, String clave) throws SQLException {
        Cliente cliente = c.autenticarUsuario(usuario, clave);
        if (cliente != null) {
            cl.setNombre(cliente.getNombre());
            cl.setApellido(cliente.getApellido());
            cl.setID_Usuario(cliente.getID_Usuario());
            cl.setTelefono(cliente.getTelefono());
            cl.setClave(cliente.getClave());
            cl.setUsuario(cliente.getUsuario());
            cl.setTiempoAdquirido(cliente.getTiempoAdquirido());
            bandera = false;
        } else {
            System.out.println("Reintente nuevamente, credenciales erroneas");
            bandera = true;
        }
    }

    /* private void ingresarComoAdministrador() throws SQLException {
        do {
            System.out.println("Ingrese su usuario");
            this.a.setUsuario(sc.nextLine());
            System.out.println("Ingrese su clave");
            this.a.setClave(sc.nextLine());
            validarAdministrador(a.getUsuario(), a.getClave());
        } while (bandera);
        MenuAdm m = new MenuAdm(a);
        m.MenuLogueado();
    } /* */

    private void iniciarSesionAdmin() throws SQLException {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== Bienvenido al Sistema de Administrador ===");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrarse como nuevo administrador");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            
            int opcion = sc.nextInt();
            sc.nextLine(); 
            
            switch (opcion) {
                case 1:
                    ingresarComoAdministrador();
                    salir = true;
                    break;
                case 2:
                    registrarNuevoAdministrador();
                    break;
                case 3:
                    salir = true;
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        }
    }

    private void ingresarComoAdministrador() throws SQLException {
        do {
            System.out.println("Ingrese su usuario");
            this.a.setUsuario(sc.nextLine());
            System.out.println("Ingrese su clave");
            this.a.setClave(sc.nextLine());
            validarAdministrador(a.getUsuario(), a.getClave());
        } while (bandera);
            MenuAdm m = new MenuAdm(a);
            m.MenuLogueado();
    }

private void registrarNuevoAdministrador() {
    System.out.println("\n=== Registro de Nuevo Administrador ===");
    Administrador nuevoAdmin = new Administrador();

    System.out.print("Ingrese un nombre de usuario: ");
    nuevoAdmin.setUsuario(sc.nextLine().trim());

    System.out.print("Ingrese su nombre: ");
    nuevoAdmin.setNombre(sc.nextLine().trim());

    System.out.print("Ingrese su apellido: ");
    nuevoAdmin.setApellido(sc.nextLine().trim());

    System.out.print("Ingrese una clave: ");
    nuevoAdmin.setClave(sc.nextLine().trim());

    AdministradorController adminC = new AdministradorController();
    try {
        adminC.crearAdmin(nuevoAdmin);
        System.out.println("Administrador registrado exitosamente. Ahora puede iniciar sesión.");
    } catch (SQLException e) {
        System.err.println("Error al registrar el administrador: " + e.getMessage());
        if (e.getMessage().contains("duplicate")) {
            System.out.println("El nombre de usuario ya está en uso. Intente con otro.");
        }
    }
}

    private void validarAdministrador(String usuario, String clave) throws SQLException {
        Administrador admin = ac.autenticarAdmin(usuario, clave);
        if (admin != null) {
            a.setNombre(admin.getNombre());
            a.setApellido(admin.getApellido());
            a.setNroLegajo(admin.getNroLegajo());
            a.setUsuario(admin.getUsuario());
            a.setClave(admin.getClave());
            bandera = false;
        } else {
            System.out.println("Reintente nuevamente, credenciales erroneas");
            bandera = true;
        }
    }
}