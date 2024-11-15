package controlador;

import BD.util.DBConnection;
import modelo.Cliente;
import java.util.Scanner;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;

import static BD.util.DBConnection.setConnection;

public class ClienteController {

    private static final String SELECT_USER = "SELECT ID_Usuario, Nombre, Apellido, Telefono, Tiempo FROM cliente WHERE ID_Usuario = ?";
    private static final String INSERT_USER_SQL = "INSERT INTO Cliente (Nombre, Apellido, Telefono, Tiempo, Usuario, Clave) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_USERS = "SELECT ID_Usuario, Nombre, Apellido, Telefono, Tiempo FROM Cliente";
    private static final String DELETE_USER_SQL = "DELETE FROM Cliente WHERE ID_Usuario = ?";

    public void crearUsuario(Cliente cliente) throws SQLException {
        try(Connection connection = setConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL)) {
            ps.setString(1,cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3,cliente.getTelefono());
            ps.setTime(4, Time.valueOf("00:00:00"));
            ps.setString(5,cliente.getUsuario());
            ps.setString(6,cliente.getClave());
            ps.executeUpdate();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Cliente> seleccTodoUsuario() throws SQLException{
        ArrayList<Cliente> clientes = new ArrayList<>();
        try(Connection connection = setConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_USERS)){

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Cliente cliente = new Cliente(rs.getInt("ID_Usuario"),rs.getString("Nombre"),
                        rs.getString("Apellido"), rs.getString("Telefono"), rs.getTime("Tiempo"));
                clientes.add(cliente);
            }
        }
        return clientes;
    }

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
                    rs.getString("Telefono"),
                    rs.getTime("Tiempo")
                );
                System.out.println("Información del cliente:");
                System.out.println("Número de Usuario: " + cliente.getID_Usuario());
                System.out.println("Nombre: " + cliente.getNombre());
                System.out.println("Apellido: " + cliente.getApellido());
                System.out.println("Teléfono: " + cliente.getTelefono());
                System.out.println("Tiempo comprado: " + cliente.getTiempo());
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

    public void modificarUsuario(int idUsuario, String nombre, String apellido, String telefono) throws SQLException {
        String sql = "UPDATE Cliente SET Nombre = ?, Apellido = ?, Telefono = ? WHERE ID_Usuario = ?";
        
        try (Connection connection = setConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, telefono);
            ps.setInt(4, idUsuario);

            int resultado = ps.executeUpdate();
            
            if (resultado > 0) {
                System.out.println("Usuario modificado exitosamente!");
            } else {
                System.out.println("No se encontró el usuario con ID: " + idUsuario);
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar el usuario: " + e.getMessage());
            throw e;
        }
    }

    public Cliente autenticarUsuario(String usuario, String clave) throws SQLException {
        String sql = "SELECT ID_Usuario, Nombre, Apellido, Telefono, usuario, clave FROM Cliente WHERE usuario = ? AND clave = ?";

        try (Connection connection = setConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, clave);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setID_Usuario(rs.getInt("ID_Usuario"));
                    cliente.setNombre(rs.getString("Nombre"));
                    cliente.setApellido(rs.getString("Apellido"));
                    cliente.setTelefono(rs.getString("Telefono"));
                    cliente.setUsuario(rs.getString("usuario"));
                    cliente.setClave(rs.getString("clave"));
                    return cliente;
                } else {
                    return null;
                }
            }
        }
    }

    public void eliminarUsuario(int idUsuario) throws SQLException {
        try (Connection connection = setConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_USER_SQL)) {
            ps.setInt(1, idUsuario);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("El usuario con ID " + idUsuario + " ha sido eliminado con éxito.");
            } else {
                System.out.println("No se encontró ningún usuario con el ID " + idUsuario);
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el usuario: " + e.getMessage());
            throw e;
        }
    }

    public void actualizarTiempo(int ID_Usuario, Time tiempoComprado) throws SQLException {
        String sql = "UPDATE Cliente SET Tiempo = ADDTIME(Tiempo, ?) WHERE ID_Usuario = ?";
    
        try (Connection con = DBConnection.setConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setTime(1, tiempoComprado);
            ps.setInt(2, ID_Usuario);
    
            int resultado = ps.executeUpdate();
            if (resultado > 0) {
                System.out.println("Tiempo del cliente actualizado correctamente.");
            } else {
                System.out.println("No se encontró el cliente con ID: " + ID_Usuario);
            }
        }
    }

    public void usarComputadora(int ID_Usuario) throws SQLException {
        Scanner sc = new Scanner(System.in);

        // verifica y resta el tiempo
        System.out.println("Seleccione el tiempo de uso de la computadora:");
        System.out.println("1. 30 minutos");
        System.out.println("2. 1 hora");
        int opcion = sc.nextInt();
        sc.nextLine();

        int tiempoUso = (opcion == 1) ? 30 : 60;

        try (Connection connection = setConnection();
             PreparedStatement selectTiempoStmt = connection.prepareStatement("SELECT Tiempo FROM Cliente WHERE ID_Usuario = ?");
             PreparedStatement updateTiempoStmt = connection.prepareStatement("UPDATE Cliente SET Tiempo = ? WHERE ID_Usuario = ?");
             PreparedStatement selectCompusStmt = connection.prepareStatement("SELECT ID_Computadora, estado FROM computadoras WHERE estado = 0");
             PreparedStatement updateCompuStmt = connection.prepareStatement("UPDATE computadoras SET estado = 1, ID_Usuario = ? WHERE ID_Computadora = ?");
             PreparedStatement resetCompuStmt = connection.prepareStatement("UPDATE computadoras SET estado = 0, ID_Usuario = NULL WHERE ID_Computadora = ?")) {

            // ovtene tiempo disponible del usuario
            selectTiempoStmt.setInt(1, ID_Usuario);
            ResultSet rsTiempo = selectTiempoStmt.executeQuery();

            if (rsTiempo.next()) {
                Time tiempoAdquirido = rsTiempo.getTime("Tiempo");
                int tiempoAdquiridoMinutos = tiempoAdquirido.toLocalTime().toSecondOfDay() / 60;

                if (tiempoAdquiridoMinutos >= tiempoUso) {
                    // restar el tiempo de uso y actualiza el tiempo en la bd
                    int nuevoTiempoMinutos = tiempoAdquiridoMinutos - tiempoUso;
                    Time tiempoActualizado = Time.valueOf(LocalTime.ofSecondOfDay(nuevoTiempoMinutos * 60));
                    updateTiempoStmt.setTime(1, tiempoActualizado);
                    updateTiempoStmt.setInt(2, ID_Usuario);
                    updateTiempoStmt.executeUpdate();

                    System.out.println("Tiempo actualizado. Tiempo restante: " + nuevoTiempoMinutos + " minutos.");


                    System.out.println("\n=== COMPUTADORAS DISPONIBLES ===");
                    ResultSet rsCompus = selectCompusStmt.executeQuery();
                    boolean hayCompusDisponibles = false;

                    while (rsCompus.next()) {
                        hayCompusDisponibles = true;
                        int idCompu = rsCompus.getInt("ID_Computadora");
                        System.out.println("ID Computadora: " + idCompu);
                    }

                    if (!hayCompusDisponibles) {
                        System.out.println("No hay computadoras disponibles en este momento.");
                        return;
                    }

                    // Selección de computadora
                    System.out.println("Ingrese el ID de la computadora que desea usar:");
                    int ID_Computadora = sc.nextInt();
                    sc.nextLine(); // Limpiar el buffer después de nextInt()

                    // Asignar la computadora al usuario
                    updateCompuStmt.setInt(1, ID_Usuario);
                    updateCompuStmt.setInt(2, ID_Computadora);
                    int filasActualizadas = updateCompuStmt.executeUpdate();

                    if (filasActualizadas > 0) {
                        System.out.println("Computadora " + ID_Computadora + " asignada con éxito. Ahora está ocupada.");

                        // Simular uso de la computadora con tres mensajes de "Usando..."
                        System.out.println("Usando...");
                        Thread.sleep(1000); // Pausa de 1 segundo
                        System.out.println("Usando...");
                        Thread.sleep(1000); // Pausa de 1 segundo
                        System.out.println("Usando...");
                        Thread.sleep(1000); // Pausa de 1 segundo

                        // Actualizar estado de la computadora a 0 (disponible) y ID_Usuario a NULL
                        resetCompuStmt.setInt(1, ID_Computadora);
                        resetCompuStmt.executeUpdate();

                        // Notificar al usuario que su tiempo ha terminado
                        System.out.println("Su tiempo de conexión ha finalizado. La computadora ahora está disponible.");
                    } else {
                        System.out.println("Error al asignar la computadora. Es posible que ya esté ocupada.");
                    }
                } else {
                    System.out.println("No tiene suficiente tiempo para esta sesión. Acredite más tiempo.");
                }
            } else {
                System.out.println("No se encontró el usuario con ID: " + ID_Usuario);
            }
        } catch (InterruptedException e) {
            System.err.println("Error durante la simulación del uso: " + e.getMessage());
            Thread.currentThread().interrupt(); // Restaurar el estado de interrupción del hilo
        } catch (SQLException e) {
            System.err.println("Error al usar la computadora: " + e.getMessage());
            throw e;
        }
    }
}