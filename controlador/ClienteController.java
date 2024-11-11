package controlador;

import BD.util.DBConnection;
import modelo.Cliente;
import java.util.Scanner;
import java.sql.*;
import java.util.ArrayList;

import static BD.util.DBConnection.setConnection;

public class ClienteController {

    DBConnection db = new DBConnection();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String sql = "";
    //Este metodo es para cuando el usuario quiera ver su informacion (El usuario solo puede ver su info ver como agregar de forma dinamica el nro de usuario segun su ID)
    private static final String SELECT_USER = "SELECT ID_Usuario, Nombre, Apellido, Telefono FROM cliente WHERE ID_Usuario = ?";
    private static final String INSERT_USER_SQL = "INSERT INTO Cliente (Nombre, Apellido,Telefono) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_USERS = "SELECT * FROM Cliente";
    private static final String DELETE_USER_SQL = "DELETE FROM Cliente WHERE ID_Usuario = ?";

    public void crearUsuario(Cliente cliente) throws SQLException {
        try(Connection connection = setConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL)) {
            //ps.setInt(1,cliente.getID_Usuario()); lo comento pq es autoincremental
            ps.setString(1,cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3,cliente.getTelefono());
            ps.executeUpdate();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Cliente> seleccTodoUsuario() throws SQLException{
        ArrayList<Cliente> clientes = new ArrayList<>();
        try(Connection connection = setConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_USERS)){

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Cliente cliente = new Cliente(rs.getInt("ID_Usuario"),rs.getString("Nombre"),
                        rs.getString("Apellido"), rs.getString("Telefono"));
                clientes.add(cliente);
            }
        }
        return clientes;
    }
    public Cliente mostrarInfoUsuario(int ID_Usuario) throws SQLException {
        try (Connection connection = setConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_USER)) {
            ps.setInt(1, ID_Usuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("ID_Usuario"),
                        rs.getString("Nombre"),
                        rs.getString("Apellido"),
                        rs.getString("Telefono")
                );
                System.out.println("Información del cliente:");
                System.out.println("Número de Usuario: " + cliente.getID_Usuario());
                System.out.println("Nombre: " + cliente.getNombre());
                System.out.println("Apellido: " + cliente.getApellido());
                System.out.println("Teléfono: " + cliente.getTelefono());
                return cliente;
            } else {
                System.out.println("No se encontró ningún cliente con el número de usuario: " + ID_Usuario);
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el cliente: " + e.getMessage());
            throw e;
        }
    }

    /* public void modificarUsuario(int idUsuario) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("\n--- Modificar Usuario ---");

            System.out.print("Ingrese el nuevo nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese el nuevo apellido: ");
            String apellido = scanner.nextLine();

            System.out.print("Ingrese el nuevo teléfono: ");
            int telefono = 0;
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, ingrese un número de teléfono válido:");
                scanner.next();
            }
            telefono = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer después de nextInt()

            Connection connection = setConnection();
            String sql = "UPDATE Cliente SET Nombre = ?, Apellido = ?, Telefono = ? WHERE ID_Usuario = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, nombre);
                ps.setString(2, apellido);
                ps.setInt(3, telefono);
                ps.setInt(4, idUsuario);

                int resultado = ps.executeUpdate();

                if (resultado > 0) {
                    System.out.println("Usuario modificado exitosamente!");
                } else {
                    System.out.println("No se encontró el usuario con ID: " + idUsuario);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar el usuario: " + e.getMessage());
            throw e;
        }
    } /* */

    public void modificarUsuario(int idUsuario, String nombre, String apellido, String telefono) throws SQLException {
        String sql = "UPDATE Cliente SET Nombre = ?, Apellido = ?, Telefono = ? WHERE ID_Usuario = ?";
        
        try (Connection connection = setConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, telefono);
            ps.setInt(4, idUsuario);

            int resultado = ps.executeUpdate();
            
            if (resultado > 0) {
                System.out.println("Usuario modificado exitosamente!");
            } else {
                System.out.println("No se encontró el usuario con ID: " + idUsuario);
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar el usuario: " + e.getMessage());
            throw e;
        }
    }

    public Cliente autenticarUsuario(String usuario, String clave) throws SQLException {
        String sql = "SELECT ID_Usuario, Nombre, Apellido, Telefono, usuario, clave FROM Cliente WHERE usuario = ? AND clave = ?";

        try (Connection connection = setConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, clave);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setID_Usuario(rs.getInt("ID_Usuario"));
                    cliente.setNombre(rs.getString("Nombre"));
                    cliente.setApellido(rs.getString("Apellido"));
                    cliente.setTelefono(rs.getString("Telefono"));
                    cliente.setUsuario(rs.getString("usuario"));
                    cliente.setClave(rs.getString("clave"));
                    return cliente;
                } else {
                    return null;
                }
            }
        }
    }

    public void eliminarUsuario(int idUsuario) throws SQLException {
        try (Connection connection = setConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_USER_SQL)) {
            ps.setInt(1, idUsuario);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("El usuario con ID " + idUsuario + " ha sido eliminado con éxito.");
            } else {
                System.out.println("No se encontró ningún usuario con el ID " + idUsuario);
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el usuario: " + e.getMessage());
            throw e;
        }
    }

}