package controlador;

import BD.util.DBConnection;
import modelo.Computadora;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static BD.util.DBConnection.setConnection;

public class ComputadoraController {
    /*public void crear(Computadora computadora) throws SQLException {
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
    } /* */

    public void crear(Computadora computadora) throws SQLException {
        // Cambiar la consulta para insertar valores dinámicamente desde el objeto 'computadora'
        String sql = "INSERT INTO computadoras (estado, ID_Usuario) VALUES (?, ?)";

        try (Connection con = setConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            // Establecer los parámetros de la consulta usando los valores de la computadora
            stmt.setInt(1, computadora.getEstado()); // Asumiendo que 'estado' es un int
            stmt.setInt(2, computadora.getID_Usuario()); // Asumiendo que 'ID_Usuario' es un int o null

            // Ejecutar la consulta
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
        String sql = "UPDATE computadoras SET estado = ?, WHERE ID_Computadora = ?";
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
    
    public void actualizar(int ID_Computadora, int nuevoEstado, int ID_Usuario) throws SQLException {
        String sql = "UPDATE computadoras SET estado = ?, ID_Usuario = ? WHERE ID_Computadora = ?";
        try (Connection con = setConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, nuevoEstado);
            stmt.setInt(2, ID_Usuario);
            stmt.setInt(3, ID_Computadora);
            int filasActualizadas = stmt.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Computadora actualizada con éxito.");
            } else {
                System.out.println("No se encontró la computadora con ID: " + ID_Computadora);
            }
        }
    }
    public void mostrarComputadoras() throws SQLException {
        List<Computadora> computadoras = leer();  // Utiliza el método leer para obtener la lista de computadoras
        System.out.println("\n=== Lista de Computadoras ===");
        for (Computadora computadora : computadoras) {
            System.out.println("ID Computadora: " + computadora.getID_Computadora());
            System.out.println("Estado: " + computadora.getEstado());
            System.out.println("-------------");
        }
    }
    public void actualizarTiempoComputadora(int ID_Usuario) throws SQLException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Seleccione el tiempo de uso de la computadora:");
        System.out.println("1. 30 minutos");
        System.out.println("2. 1 hora");
        int opcion = sc.nextInt();
        sc.nextLine(); // Limpiar el buffer después de nextInt()

        int tiempoUso = (opcion == 1) ? 30 : 60;

        try (Connection connection = setConnection();
             PreparedStatement selectStmt = connection.prepareStatement("SELECT Tiempo FROM Cliente WHERE ID_Usuario = ?");
             PreparedStatement updateStmt = connection.prepareStatement("UPDATE Cliente SET Tiempo = ? WHERE ID_Usuario = ?")) {

            // Verificar tiempo adquirido actual
            selectStmt.setInt(1, ID_Usuario);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                Time tiempoAdquirido = rs.getTime("Tiempo");
                int tiempoAdquiridoMinutos = tiempoAdquirido.toLocalTime().toSecondOfDay() / 60;

                if (tiempoAdquiridoMinutos >= tiempoUso) {
                    int nuevoTiempo = tiempoAdquiridoMinutos - tiempoUso;

                    // Convertir minutos a formato TIME para la base de datos
                    Time tiempoActualizado = Time.valueOf(LocalTime.ofSecondOfDay(nuevoTiempo * 60));

                    // Actualizar tiempo en la base de datos
                    updateStmt.setTime(1, tiempoActualizado);
                    updateStmt.setInt(2, ID_Usuario);
                    updateStmt.executeUpdate();

                    System.out.println("Tiempo actualizado. Tiempo restante: " + nuevoTiempo + " minutos.");
                } else {
                    System.out.println("No tiene suficiente tiempo, acredite más tiempo.");
                }
            } else {
                System.out.println("No se encontró el usuario con ID: " + ID_Usuario);
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el tiempo: " + e.getMessage());
            throw e;
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
    public void actualizarComputadora(int ID_Usuario) throws SQLException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese el ID de la computadora:");
        int ID_Computadora = sc.nextInt();
        sc.nextLine(); // Limpiar el buffer después de nextInt()

        String sqlUpdate = "UPDATE computadoras SET estado = ?, ID_Usuario = ? WHERE ID_Computadora = ?";
        String sqlReset = "UPDATE computadoras SET estado = 0, ID_Usuario = NULL WHERE ID_Computadora = ?";

        try (Connection con = DBConnection.setConnection();
             PreparedStatement stmtUpdate = con.prepareStatement(sqlUpdate);
             PreparedStatement stmtReset = con.prepareStatement(sqlReset)) {
            int nuevoEstado = 1; // Estado 1 (ocupado)
            stmtUpdate.setInt(1, nuevoEstado);
            stmtUpdate.setInt(2, ID_Usuario);
            stmtUpdate.setInt(3, ID_Computadora);
            int filasActualizadas = stmtUpdate.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Computadora actualizada con éxito.");
                System.out.println("La computadora nro: " + ID_Computadora + " está ocupada por ti ahora.");


            } else {
                System.out.println("No se encontró la computadora con ID: " + ID_Computadora);
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar la computadora: " + e.getMessage());
            throw e;
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