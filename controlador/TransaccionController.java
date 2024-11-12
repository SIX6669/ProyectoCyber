package controlador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import BD.util.DBConnection;
import modelo.Transaccion;

public class TransaccionController {
    public void crear(Transaccion transaccion) throws SQLException {
        String sql = "INSERT INTO transaccion (TiempoComprado, total, ID_Usuario) VALUES (?, ?, ?)";
        String sqlActualizarTiempo = "UPDATE Cliente SET Tiempo = ADDTIME(Tiempo, ?) WHERE ID_Usuario = ?";
        try (Connection con = DBConnection.setConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             PreparedStatement stmtActualizarTiempo = con.prepareStatement(sqlActualizarTiempo)) {
            stmt.setTime(1, transaccion.getTiempoComprado());
            stmt.setDouble(2, transaccion.getTotal());
            stmt.setInt(3, transaccion.getID_Usuario());
            stmt.executeUpdate();
        }
    }

    public List<Transaccion> obtener() throws SQLException {
        List<Transaccion> transacciones = new ArrayList<>();
        String sql = "SELECT * FROM transaccion";
        try (Connection con = DBConnection.setConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int ID_Transaccion = rs.getInt("ID_Transaccion");
                Time tiempoComprado = rs.getTime("TiempoComprado");
                double total = rs.getDouble("total");
                int ID_Usuario = rs.getInt("ID_Usuario");
                Transaccion transaccion = new Transaccion(ID_Transaccion, tiempoComprado, total, ID_Usuario);
                transacciones.add(transaccion);
            }
        }
        return transacciones;
    }

}