package controlador;
import modelo.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static BD.util.DBConnection.setConnection;

public class AdministradorController {
    private static final String INSERT_USER_SQL = "INSERT INTO Cliente (NroUsuario, Nombre, Apellido,Telefono) VALUES (?, ?)";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM Cliente WHERE NroUsuario = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM Cliente";
    private static final String DELETE_USER_SQL = "DELETE FROM Cliente WHERE NroUsuario = ?";
    private static final String UPDATE_USER_SQL = "UPDATE Cliente SET Nombre = ?, Apellido = ? WHERE NroUsuario = ?";

    public void crearUsuario( Cliente cliente) throws SQLException {
        try(Connection connection = setConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL)) {
            ps.setInt(1,cliente.getID_Usuario());
            ps.setString(2,cliente.getNombre());
            ps.setString(3, cliente.getApellido());
            ps.setInt(5,cliente.getTelefono());
            ps.executeUpdate();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void seleccUsuarioID(int nroUsuario) throws SQLException{
        try(Connection connection = setConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT_USER_BY_ID)) {
            ps.setInt(1, nroUsuario);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Cliente cliente = new Cliente(rs.getInt("ID_Usuario"),rs.getString("Nombre"),
                        rs.getString("Apellido"),rs.getInt("Tiempo"),
                        rs.getTime("Telefono"));
                System.out.println(cliente.toString());
            }
        }
    }

    public ArrayList<Cliente> seleccTodoUsuario() throws SQLException{
        ArrayList<Cliente> clientes = new ArrayList<>();
        try(Connection connection = setConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_USERS)){

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Cliente cliente = new Cliente(rs.getInt("ID_Usuario"),rs.getString("Nombre"),
                        rs.getString("Apellido"),rs.getInt("Tiempo"),
                        rs.getTime("Telefono"));
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    public boolean cambiar(Cliente cliente)throws SQLException{
        StringBuilder sql = new StringBuilder("UPDATE users SET ");

        boolean isFirst = true;
        if(cliente.getNombre() != null){
            sql.append("Nombre = ?");
            isFirst = false;
        }
        if(cliente.apellido != null){
            sql.append("Apellido = ?");
            isFirst = false;
        }
        if (cliente.telefono != 0) {
            sql.append("Telefono = ?");
            isFirst = false;
        }

        sql.append(" WHERE NroUsuario = ?");

        try(Connection connection = setConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_USERS)){

            int paramIndex = 1;

            if(cliente.getNombre() != null){
                ps.setString(paramIndex++,cliente.getNombre());
            }
            if(cliente.getApellido() != null){
                ps.setString(paramIndex++,cliente.getApellido());
            }
            if (cliente.getTelefono() != 0){
                ps.setInt(paramIndex++,cliente.getTelefono());
            }
            ps.setInt(paramIndex,cliente.getID_Usuario());
            return ps.executeUpdate() > 0;
        }
    }
}
