package controlador;

import BD.util.DBConnection;
import modelo.Cliente;

import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;

public class ClienteController {
    DBConnection db = new DBConnection();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String sql = "";
    public ClienteController() {
        try {
            db.setConnection();
            db.getDatos();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void agregarCliente (String Nombre, String apellido, int Telefono){
        try {
            sql = "INSERT INTO Clientes (Nombre, Apellido, Telefono) VALUES ('" + Nombre + "', " + apellido + ", " + Telefono + ");";
            int rowCount = stmt.executeUpdate(sql);
            System.out.println("Número de filas afectadas: " + rowCount);
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
    public void listarClientes (){
        try {
            sql = "Select ID_Usuario, Nombre, Apellido, Telefono from Clientes";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                Double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");
                System.out.println( id + "\t" + nombre + "\t" + precio + "\t" + stock);
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
}
