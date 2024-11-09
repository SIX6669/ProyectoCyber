package BD;

import java.sql.*;
public class ClienteBD {
    private String url = "jdbc:sqlserver://localhost:1433;databaseName=CyberCafeDB;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";

    private String user = "Nebularmess";
    private String password = "110698";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String sql = "";


    public ClienteBD() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {
            System.out.println("Driver no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    public void cerrarConexion() {
        try {
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
    public void mostrarClientes() {
        try {
            sql = "SELECT * FROM Clientes";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int DNI = rs.getInt("DNI");
                String nombre = rs.getString("nombre");
                System.out.println(DNI + "\t" + nombre);
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
}