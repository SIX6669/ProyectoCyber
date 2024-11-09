package controlador;
import modelo.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static BD.util.DBConnection.setConnection;

public class AdministradorController {
    private static final String INSERT_USER_SQL = "INSERT INTO Users (id, username, password) VALUES (?, ?)";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM Users WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM Users";
    private static final String DELETE_USER_SQL = "DELETE FROM Users WHERE id = ?";
    private static final String UPDATE_USER_SQL = "UPDATE Users SET username = ?, password = ? WHERE id = ?";

    public void crearUsuario( Cliente cliente) throws SQLException {
       try(Connection connection = setConnection();
       PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL)) {
           ps.setInt(1,cliente.getNroUsuario());
           ps.setString(2,cliente.getNombre());
           ps.setString(3, cliente.getApellido());
           ps.setInt(5,cliente.getTelefono());
           ps.executeUpdate();
       } catch (RuntimeException e) {
           throw new RuntimeException(e);
       }
    }

    public static String getSelectUserById() {
        return SELECT_USER_BY_ID;
    }
}

