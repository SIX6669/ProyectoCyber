package controlador;

import BD.util.DBConnection;
import modelo.Computadora;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ComputadoraController {
    public void crear(Computadora computadora) throws SQLException {
        String sql = "INSERT INTO computadoras (estado, ID_Usuario) VALUES (0, NULL)";
        try (Connection con = DBConnection.setConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
    }

    public void agregarComputadora() {
        Computadora computadora = new Computadora();
        try {
            crear(computadora);
            System.out.println("Computadora creada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al crear la computadora: " + e.getMessage());
        }
    }

    public List<Computadora> leer() throws SQLException {
        List<Computadora> computadoras = new ArrayList<>();
        String sql = "SELECT * FROM computadoras";
        try (Connection con = DBConnection.setConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int ID_Computadora = rs.getInt("ID_Computadora");
                int estado = rs.getInt("estado");
                int ID_Usuario = rs.getInt("ID_Usuario");
                Computadora computadora = new Computadora(estado, ID_Usuario);
                computadoras.add(computadora);
            }
        }
        return computadoras;
    }

    public void verComputadoras() {
        try {
            List<Computadora> computadoras = leer();
            if (computadoras.isEmpty()) {
                System.out.println("No se encontraron computadoras.");
            } else {
                System.out.println("Lista de computadoras:");
                for (Computadora computadora : computadoras) {
                    String estadoTexto = convertirEstado(computadora.getEstado());
                    String usuarioTexto = (computadora.getEstado() == 1)
                            ? ", Usuario ID: " + computadora.getID_Usuario()
                            : "";
                    System.out.println("ID: " + computadora.getID_Computadora() + ", Estado: " + estadoTexto + usuarioTexto);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener las computadoras: " + e.getMessage());
        }
    }

    public void actualizar(int ID_Computadora, int nuevoEstado) throws SQLException {
        String sql = "UPDATE computadoras SET estado = ? WHERE ID_Computadora = ?";
        try (Connection con = DBConnection.setConnection();
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

    public void actualizarComputadora() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el ID de la computadora a actualizar: ");
        int ID_Computadora = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Seleccione el nuevo estado de la computadora:");
        System.out.println("0 - Disponible");
        System.out.println("1 - Ocupado");
        System.out.println("2 - Fuera de servicio");
        System.out.print("Ingrese la opción (0, 1, o 2): ");
        int nuevoEstado = scanner.nextInt();

        if (nuevoEstado >= 0 && nuevoEstado <= 2) {
            try {
                actualizar(ID_Computadora, nuevoEstado);
            } catch (SQLException e) {
                System.out.println("Error al actualizar la computadora: " + e.getMessage());
            }
        } else {
            System.out.println("Opción no válida. Intente nuevamente con 0, 1 o 2.");
        }

        scanner.close();
    }

    public void eliminar(int ID_Computadora) throws SQLException {
        String sql = "DELETE FROM computadoras WHERE ID_Computadora = ?";
        try (Connection con = DBConnection.setConnection();
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

    public void eliminarComputadora() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el ID de la computadora a eliminar: ");
        int ID_Computadora = scanner.nextInt();

        try {
            eliminar(ID_Computadora);
        } catch (SQLException e) {
            System.out.println("Error al eliminar la computadora: " + e.getMessage());
        }
        scanner.close();
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
