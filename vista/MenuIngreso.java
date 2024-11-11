package vista;

import controlador.AdministradorController;
import controlador.ClienteController;
import modelo.Administrador;
import modelo.Cliente;

import java.sql.SQLException;
import java.util.Scanner;
import java.sql.*;

import static BD.util.DBConnection.setConnection;

public class MenuIngreso {
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
        sc.nextLine(); // Limpiar el buffer después de nextInt()
        if (tipoUsuario == 0) {
            ingresarComoCliente();
        } else {
            ingresarComoAdministrador();
        }
    }

    private void ingresarComoCliente() throws SQLException {
        int opcion = 0;
        System.out.println("Si ya tiene usuario creado ingrese 0, en caso contrario, para registrarse ingrese 1");
        opcion = sc.nextInt();
        sc.nextLine(); // Limpiar el buffer después de nextInt()
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
        while (!sc.hasNextInt()) {
            System.out.println("Por favor, ingrese un número de teléfono válido:");
            sc.next();
        }
        nuevoCliente.setTelefono(sc.nextInt());
        sc.nextLine();

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
            ps.setInt(3, nuevoCliente.getTelefono());
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
            bandera = false;
        } else {
            System.out.println("Reintente nuevamente, credenciales erroneas");
            bandera = true;
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
        System.out.println("Bienvenido: " + a.getNombre());
        MenuAdm m = new MenuAdm(a);
        m.MenuLogueado();
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





