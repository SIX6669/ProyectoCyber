package controlador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import BD.util.DBConnection;
import modelo.Transaccion;

public class TransaccionController {
    public void crear(Transaccion transaccion) throws SQLException {
        String sql = "INSERT INTO transaccion (TiempoComprado, total, ID_Usuario) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.setConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setTime(1, transaccion.getTiempoComprado());
            stmt.setDouble(2, transaccion.getTotal());
            stmt.setInt(3, transaccion.getID_Usuario());
            stmt.executeUpdate();
        }
    }
    
    public void crearTransaccion(int ID_Usuario) {
        Scanner scanner = new Scanner(System.in);
    
        System.out.println("Ingrese cuánto tiempo desea comprar: ");
        System.out.println("1 - 30 minutos.");
        System.out.println("2 - 1 hora.");
        System.out.println("3 - 1:30 hs.");
        System.out.println("4 - 2 hs.");
        
        int opcion = scanner.nextInt();
        Time tiempoComprado;
        double total;
    
        switch (opcion) {
            case 1:
                tiempoComprado = Time.valueOf("00:30:00");
                total = 1000;
                break;
            case 2:
                tiempoComprado = Time.valueOf("01:00:00");
                total = 2000;
                break;
            case 3:
                tiempoComprado = Time.valueOf("01:30:00");
                total = 3000;
                break;
            case 4:
                tiempoComprado = Time.valueOf("02:00:00");
                total = 4000;
                break;
            default:
                System.out.println("Opción inválida.");
                scanner.close();
                return;
        }

        Transaccion transaccion = new Transaccion(tiempoComprado, total, ID_Usuario);
    
        try {
            crear(transaccion);
            System.out.println("Transacción creada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al crear la transacción: " + e.getMessage());
        } finally {
            scanner.close();
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
                Transaccion transaccion = new Transaccion(tiempoComprado, total, ID_Usuario);
                transacciones.add(transaccion);
            }
        }
        return transacciones;
    }

    public void verTransacciones() {
        try {
            List<Transaccion> transacciones = obtener();
            if (transacciones.isEmpty()) {
                System.out.println("No se encontraron transacciones.");
            } else {
                System.out.println("Lista de transacciones:");
                for (Transaccion transaccion : transacciones) {
                    System.out.println("ID: " + transaccion.getID_Transaccion() +
                            ", Tiempo Comprado: " + transaccion.getTiempoComprado() +
                            ", Total: $" + transaccion.getTotal() +
                            ", ID Usuario: " + transaccion.getID_Usuario());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener las transacciones: " + e.getMessage());
        }
    }

    public Transaccion obtenerPorId(int idTransaccion) throws SQLException {
        String sql = "SELECT * FROM transaccion WHERE ID_Transaccion = ?";
        Transaccion transaccion = null;
        try (Connection con = DBConnection.setConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idTransaccion);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int ID_Transaccion = rs.getInt("ID_Transaccion");
                    Time tiempoComprado = rs.getTime("TiempoComprado");
                    double total = rs.getDouble("total");
                    int ID_Usuario = rs.getInt("ID_Usuario");
                    transaccion = new Transaccion(tiempoComprado, total, ID_Usuario);
                }
            }
        }
        return transaccion;
    }

    public void verTransaccionPorId() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el ID de la transacción a buscar: ");
        int idTransaccion = scanner.nextInt();
        try {
            Transaccion transaccion = obtenerPorId(idTransaccion);
            if (transaccion != null) {
                System.out.println("Transacción encontrada:");
                System.out.println("ID: " + transaccion.getID_Transaccion() +
                        ", Tiempo Comprado: " + transaccion.getTiempoComprado() +
                        ", Total: $" + transaccion.getTotal() +
                        ", ID Usuario: " + transaccion.getID_Usuario());
            } else {
                System.out.println("No se encontró una transacción con el ID: " + idTransaccion);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar la transacción: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    public void actualizar(int ID_Transaccion, Time nuevoTiempoComprado, double nuevoTotal) throws SQLException {
        String sql = "UPDATE transaccion SET TiempoComprado = ?, total = ? WHERE ID_Transaccion = ?";
        try (Connection con = DBConnection.setConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setTime(1, nuevoTiempoComprado);
            stmt.setDouble(2, nuevoTotal);
            stmt.setInt(3, ID_Transaccion);
            int filasActualizadas = stmt.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Transacción actualizada con éxito.");
            } else {
                System.out.println("No se encontró la transacción con ID: " + ID_Transaccion);
            }
        }
    }

    public void actualizarTransaccion() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el ID de la transacción a actualizar: ");
        int idTransaccion = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Ingrese el nuevo tiempo comprado (HH:MM): ");
        String nuevoTiempoCompradoStr = scanner.nextLine();
        Time nuevoTiempoComprado = Time.valueOf(nuevoTiempoCompradoStr + ":00");

        System.out.print("Ingrese el nuevo total de la transacción: ");
        double nuevoTotal = scanner.nextDouble();

        try {
            actualizar(idTransaccion, nuevoTiempoComprado, nuevoTotal);
        } catch (SQLException e) {
            System.out.println("Error al actualizar la transacción: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    public void eliminar(int ID_Transaccion) throws SQLException {
        String sql = "DELETE FROM transaccion WHERE ID_Transaccion = ?";
        try (Connection con = DBConnection.setConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, ID_Transaccion);
            int filasEliminadas = stmt.executeUpdate();
            if (filasEliminadas > 0) {
                System.out.println("Transacción eliminada con éxito.");
            } else {
                System.out.println("No se encontró la transacción con ID: " + ID_Transaccion);
            }
        }
    }

    public void eliminarTransaccion() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el ID de la transacción a eliminar: ");
        int idTransaccion = scanner.nextInt();

        try {
            eliminar(idTransaccion);
        } catch (SQLException e) {
            System.out.println("Error al eliminar la transacción: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}