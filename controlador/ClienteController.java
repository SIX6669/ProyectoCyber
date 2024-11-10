package controlador;

import BD.util.DBConnection;
import modelo.Cliente;
import java.util.Scanner;
import java.sql.SQLException;
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
                        rs.getInt("Telefono")
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
    //Metodo modificar (el usuario nada mas puede ver y modificar su propia info)
    private static final String UPDATE_USER = "UPDATE Cliente SET  Nombre = ?, Apellido = ?, Telefono = ? WHERE ID_Usuario = ?";

    public void modificarUsuario() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("\n--- Modificar Usuario ---");
            System.out.print("Ingrese el ID del usuario a modificar: ");
            int ID_Usuario = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Ingrese el nuevo nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese el nuevo apellido: ");
            String apellido = scanner.nextLine();
            System.out.print("Ingrese el nuevo teléfono: ");
            int telefono = scanner.nextInt();
            Connection connection = setConnection();
            PreparedStatement ps = connection.prepareStatement(UPDATE_USER);

            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setInt(3, telefono);
            ps.setInt(4, ID_Usuario);

            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                System.out.println("Usuario modificado exitosamente!");
            } else {
                System.out.println("No se encontró el usuario con ID: " + ID_Usuario);
            }

        } catch (SQLException e) {
            System.out.println("Error al modificar el usuario: " + e.getMessage());
            throw e;
        }
    }
}
