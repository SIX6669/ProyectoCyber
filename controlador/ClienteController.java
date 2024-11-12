package controlador;

import BD.util.DBConnection;
import modelo.Cliente;

import java.time.LocalTime;
import java.util.Scanner;
import java.sql.*;
import java.util.ArrayList;

import static BD.util.DBConnection.setConnection;

public class ClienteController {

    DBConnection db = new DBConnection();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String sql = "";
    //Este metodo es para cuando el usuario quiera ver su informacion (El usuario solo puede ver su info ver como agregar de forma dinamica el nro de usuario segun su ID)
    private static final String SELECT_USER = "SELECT ID_Usuario, Nombre, Apellido, Telefono, Tiempo FROM cliente WHERE ID_Usuario = ?";
    private static final String INSERT_USER_SQL = "INSERT INTO Cliente (Nombre, Apellido,Telefono) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_USERS = "SELECT * FROM Cliente";
    private static final String DELETE_USER_SQL = "DELETE FROM Cliente WHERE ID_Usuario = ?";
    private static final String UPDATE_TIEMPO_SQL = "UPDATE Cliente SET Tiempo = ? WHERE ID_Usuario = ?";

    public void crearUsuario(Cliente cliente) throws SQLException {
        try(Connection connection = setConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL)) {
            //ps.setInt(1,cliente.getID_Usuario()); lo comento pq es autoincremental
            ps.setString(1,cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3,cliente.getTelefono());
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
                        rs.getString("Apellido"), rs.getString("Telefono"));
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
                cliente.setTiempoAdquirido(rs.getTime("Tiempo"));
                System.out.println("Información del cliente:");
                System.out.println("Número de Usuario: " + cliente.getID_Usuario());
                System.out.println("Nombre: " + cliente.getNombre());
                System.out.println("Apellido: " + cliente.getApellido());
                System.out.println("Teléfono: " + cliente.getTelefono());
                System.out.println("Tiempo Adquirido: " + cliente.getTiempoAdquirido());
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


    /* public void modificarUsuario(int idUsuario) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("\n--- Modificar Usuario ---");

            System.out.print("Ingrese el nuevo nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese el nuevo apellido: ");
            String apellido = scanner.nextLine();

            System.out.print("Ingrese el nuevo teléfono: ");
            int telefono = 0;
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, ingrese un número de teléfono válido:");
                scanner.next();
            }
            telefono = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer después de nextInt()

            Connection connection = setConnection();
            String sql = "UPDATE Cliente SET Nombre = ?, Apellido = ?, Telefono = ? WHERE ID_Usuario = ?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, nombre);
                ps.setString(2, apellido);
                ps.setInt(3, telefono);
                ps.setInt(4, idUsuario);

                int resultado = ps.executeUpdate();

                if (resultado > 0) {
                    System.out.println("Usuario modificado exitosamente!");
                } else {
                    System.out.println("No se encontró el usuario con ID: " + idUsuario);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar el usuario: " + e.getMessage());
            throw e;
        }
    } /* */

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
        String sql = "SELECT ID_Usuario, Nombre, Apellido, Telefono, Tiempo, usuario, clave FROM Cliente WHERE usuario = ? AND clave = ?";

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

                    // Obtener el tiempo como java.sql.Time
                    Time tiempo = rs.getTime("Tiempo");
                    cliente.setTiempoAdquirido(tiempo);

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
    public void usarComputadora(int ID_Usuario) throws SQLException {
        Scanner sc = new Scanner(System.in);

        // Paso 1: Verificar y restar el tiempo del usuario
        System.out.println("Seleccione el tiempo de uso de la computadora:");
        System.out.println("1. 30 minutos");
        System.out.println("2. 1 hora");
        int opcion = sc.nextInt();
        sc.nextLine(); // Limpiar el buffer después de nextInt()

        int tiempoUso = (opcion == 1) ? 30 : 60;

        try (Connection connection = setConnection();
             PreparedStatement selectTiempoStmt = connection.prepareStatement("SELECT Tiempo FROM Cliente WHERE ID_Usuario = ?");
             PreparedStatement updateTiempoStmt = connection.prepareStatement("UPDATE Cliente SET Tiempo = ? WHERE ID_Usuario = ?");
             PreparedStatement selectCompusStmt = connection.prepareStatement("SELECT ID_Computadora, estado FROM computadoras WHERE estado = 0");
             PreparedStatement updateCompuStmt = connection.prepareStatement("UPDATE computadoras SET estado = 1, ID_Usuario = ? WHERE ID_Computadora = ?");
             PreparedStatement resetCompuStmt = connection.prepareStatement("UPDATE computadoras SET estado = 0, ID_Usuario = NULL WHERE ID_Computadora = ?")) {

            // Obtener tiempo disponible del usuario
            selectTiempoStmt.setInt(1, ID_Usuario);
            ResultSet rsTiempo = selectTiempoStmt.executeQuery();

            if (rsTiempo.next()) {
                Time tiempoAdquirido = rsTiempo.getTime("Tiempo");
                int tiempoAdquiridoMinutos = tiempoAdquirido.toLocalTime().toSecondOfDay() / 60;

                if (tiempoAdquiridoMinutos >= tiempoUso) {
                    // Restar el tiempo de uso y actualizar el tiempo en la base de datos
                    int nuevoTiempoMinutos = tiempoAdquiridoMinutos - tiempoUso;
                    Time tiempoActualizado = Time.valueOf(LocalTime.ofSecondOfDay(nuevoTiempoMinutos * 60));
                    updateTiempoStmt.setTime(1, tiempoActualizado);
                    updateTiempoStmt.setInt(2, ID_Usuario);
                    updateTiempoStmt.executeUpdate();

                    System.out.println("Tiempo actualizado. Tiempo restante: " + nuevoTiempoMinutos + " minutos.");

                    // Paso 2: Mostrar computadoras disponibles
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

    public void finalizarUsoComputadora(int ID_Computadora, int ID_Usuario) throws SQLException {
        try (Connection connection = setConnection();
             PreparedStatement resetStmt = connection.prepareStatement("UPDATE computadoras SET estado = 0, ID_Usuario = NULL WHERE ID_Computadora = ?")) {

            // Simular uso de la computadora con tres mensajes de "Usando..."
            System.out.println("Usando...");
            Thread.sleep(1000); // Pausa de 1 segundo
            System.out.println("Usando...");
            Thread.sleep(1000); // Pausa de 1 segundo
            System.out.println("Usando...");
            Thread.sleep(1000); // Pausa de 1 segundo

            // Actualizar estado de la computadora a 0 (disponible) y ID_Usuario a NULL
            resetStmt.setInt(1, ID_Computadora);
            resetStmt.executeUpdate();

            // Notificar al usuario que su tiempo ha terminado
            System.out.println("Su tiempo ha terminado. La computadora ahora está disponible.");
        } catch (InterruptedException e) {
            System.err.println("Error durante la simulación del uso: " + e.getMessage());
            Thread.currentThread().interrupt(); // Restaurar el estado de interrupción del hilo
        } catch (SQLException e) {
            System.err.println("Error al finalizar el uso de la computadora: " + e.getMessage());
            throw e;
        }
    }


    public void actualizarTiempoComputadora(int ID_Usuario, int tiempoUso) throws SQLException {
        try (Connection connection = setConnection();
             PreparedStatement selectStmt = connection.prepareStatement("SELECT Tiempo FROM Cliente WHERE ID_Usuario = ?");
             PreparedStatement updateStmt = connection.prepareStatement("UPDATE Cliente SET Tiempo = ? WHERE ID_Usuario = ?");
             PreparedStatement resetStmt = connection.prepareStatement("UPDATE computadoras SET estado = 0, ID_Usuario = NULL WHERE ID_Usuario = ?")) {

            // Verificar tiempo adquirido actual
            selectStmt.setInt(1, ID_Usuario);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                Time tiempoAdquirido = rs.getTime("Tiempo");
                int tiempoAdquiridoMinutos = tiempoAdquirido.toLocalTime().toSecondOfDay() / 60;

                int nuevoTiempoMinutos = tiempoAdquiridoMinutos - tiempoUso;

                // Convertir minutos a formato TIME para la base de datos
                Time tiempoActualizado = Time.valueOf(LocalTime.ofSecondOfDay(nuevoTiempoMinutos * 60));

                // Actualizar tiempo en la base de datos
                updateStmt.setTime(1, tiempoActualizado);
                updateStmt.setInt(2, ID_Usuario);
                updateStmt.executeUpdate();

                System.out.println("Finalizó su tiempo.");

                // Actualizar estado de la computadora a 0 (disponible)
                resetStmt.setInt(1, ID_Usuario);
                resetStmt.executeUpdate();

                System.out.println("La computadora que utilizó ahora está disponible nuevamente.");
            } else {
                System.out.println("No se encontró el usuario con ID: " + ID_Usuario);
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el tiempo: " + e.getMessage());
            throw e;
        }
    }



}