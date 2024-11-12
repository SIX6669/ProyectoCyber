package controlador;

import modelo.Computadora;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import static BD.util.DBConnection.setConnection;

public class ComputadoraController {

    public void crear(Computadora computadora) throws SQLException {

        String sql = "INSERT INTO computadoras (estado) VALUES (?)";

        try (Connection con = setConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, computadora.getEstado());
            stmt.executeUpdate();
        }
    }

    public List<Computadora> leer() throws SQLException {
        List<Computadora> computadoras = new ArrayList<>();
        String sql = "SELECT * FROM computadoras";
        try (Connection con = setConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int ID_Computadora = rs.getInt("ID_Computadora");
                int estado = rs.getInt("estado");
                int ID_Usuario = rs.getInt("ID_Usuario");
                Computadora computadora = new Computadora(ID_Computadora, estado, ID_Usuario);
                computadoras.add(computadora);
            }
        }
        return computadoras;
    }

    public void actualizar(int ID_Computadora, int nuevoEstado) throws SQLException {
        String sql = "UPDATE computadoras SET estado = ? WHERE ID_Computadora = ?";
        try (Connection con = setConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, nuevoEstado);
            stmt.setInt(2, ID_Computadora);
            int filasActualizadas = stmt.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Computadora actualizada con éxito.");
            } else {
                System.out.println("No se encontró la computadora con ID: " + ID_Computadora);
            }
        }
    }


    public void eliminar(int ID_Computadora) throws SQLException {
        String sql = "DELETE FROM computadoras WHERE ID_Computadora = ?";
        try (Connection con = setConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, ID_Computadora);
            int filasEliminadas = stmt.executeUpdate();
            if (filasEliminadas > 0) {
                System.out.println("Computadora eliminada con éxito.");
            } else {
                System.out.println("No se encontró la computadora con ID: " + ID_Computadora);
            }
        }
    }

    public String convertirEstado(int estado) {
        switch (estado) {
            case 0:
                return "Disponible";
            case 1:
                return "Ocupado";
            case 2:
                return "Fuera de servicio";
            default:
                return "Estado desconocido";
        }
    }
}