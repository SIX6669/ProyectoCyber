package controlador;
import modelo.Administrador;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static BD.util.DBConnection.setConnection;

public class AdministradorController {
    //controlador para extrae datos de la tabla del Admin, usando sentencuas SQL
    private static final String INSERT_ADMIN_SQL = "INSERT INTO Admin (NroLegajo, Usuario, Clave, Nombre, Apellido) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_ADMIN = "SELECT * FROM Admin";
    private static final String DELETE_ADMIN_SQL = "DELETE FROM Admin WHERE NroLegajo = ?";
//Prepared Statemen es un tipo de interfaz que se usa pra poder ejecutar sentencias SQL
//Metodo para crear una nueva fila en la tabla de admin donde se obtienen los valores de cada columna
    public void crearAdmin( Administrador admin) throws SQLException {
        try(Connection connection = setConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_ADMIN_SQL)) {
            ps.setInt(1,admin.getNroLegajo());
            ps.setString(2,admin.getUsuario());
            ps.setString(3, admin.getClave());
            ps.setString(4,admin.getNombre());
            ps.setString(5, admin.getApellido());
            ps.executeUpdate();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
//Utilizamos un array list para generar un listado de todos los administradores cargados en el sistema
    public ArrayList<Administrador> seleccTodosAdmin() throws SQLException{
        ArrayList<Administrador> admins = new ArrayList<>();
        try(Connection connection = setConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_ADMIN)){

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Administrador admin = new Administrador(rs.getInt("NroLegajo"), rs.getString("Nombre"), 
                rs.getString("Apellido"), rs.getString("Usuario"), 
                rs.getString("Clave"));
                admins.add(admin);
            }
        }
        return admins;
    }
//para poder cambiar un valor especifico
    public boolean cambiar(Administrador admin) throws SQLException {
        StringBuilder sql = new StringBuilder("UPDATE Admin SET ");

        boolean isFirst = true;
        if (admin.getNombre() != null) {
            sql.append(isFirst ? "" : ", ").append("Nombre = ?");
            isFirst = false;
        }
        if (admin.getApellido() != null) {
            sql.append(isFirst ? "" : ", ").append("Apellido = ?");
            isFirst = false;
        }
        if (admin.getUsuario() != null) {
            sql.append(isFirst ? "" : ", ").append("Usuario = ?");
            isFirst = false;
        }
        if (admin.getClave() != null) {
            sql.append(isFirst ? "" : ", ").append("Clave = ?");
            isFirst = false;
        }

        sql.append(" WHERE NroLegajo = ?");

        try (Connection connection = setConnection();
             PreparedStatement ps = connection.prepareStatement(sql.toString())) {

            int paramIndex = 1;

            if (admin.getNombre() != null) {
                ps.setString(paramIndex++, admin.getNombre());
            }
            if (admin.getApellido() != null) {
                ps.setString(paramIndex++, admin.getApellido());
            }
            if (admin.getUsuario() != null) {
                ps.setString(paramIndex++, admin.getUsuario());
            }
            if (admin.getClave() != null) {
                ps.setString(paramIndex++, admin.getClave());
            }

            ps.setInt(paramIndex, admin.getNroLegajo());

            return ps.executeUpdate() > 0;
        }
    }
//selecciona un admin segun el id que se le ingrese como parametro
    public Administrador seleccAdminPorId(int idAdmin) throws SQLException {
        String sql = "SELECT NroLegajo, usuario, nombre, apellido FROM admin WHERE NroLegajo = ?";
        Administrador admin = null;
    
        try (Connection connection = setConnection(); 
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
    
            pstmt.setInt(1, idAdmin);
            ResultSet rs = pstmt.executeQuery();
    
            if (rs.next()) {
                admin = new Administrador();
                admin.setNroLegajo(rs.getInt("NroLegajo"));
                admin.setUsuario(rs.getString("usuario"));
                admin.setNombre(rs.getString("nombre"));
                admin.setApellido(rs.getString("apellido"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el administrador por ID: " + e.getMessage());
            throw e;
        }
    
        return admin; // retorna null si no se encuentra el administrador
    }
//segun el numero de Legajo busca al admin y lo borra de la tabla
    public boolean borrarAdmin(int nroLegajo)throws SQLException{
        boolean filaBorrada = false;

        try(Connection connection = setConnection();
            PreparedStatement ps = connection.prepareStatement(DELETE_ADMIN_SQL)) {
            ps.setInt(1, nroLegajo);
            filaBorrada = ps.executeUpdate() > 0;
        }
        return filaBorrada;
    }
//forma de autenticar el admin para el login selecciona los atributos de la tabla y los compara con los paramateros
    //del admin que quiere ingresar
    public Administrador autenticarAdmin(String usuario, String clave) throws SQLException {
        String sql = "SELECT NroLegajo, Nombre, Apellido, usuario, clave FROM Admin WHERE usuario = ? AND clave = ?";

        try (Connection connection = setConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, clave);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Administrador admin = new Administrador();
                    admin.setNroLegajo(rs.getInt("NroLegajo"));
                    admin.setNombre(rs.getString("Nombre"));
                    admin.setApellido(rs.getString("Apellido"));
                    admin.setUsuario(rs.getString("usuario"));
                    admin.setClave(rs.getString("clave"));
                    return admin;
                } else {
                    return null;
                }
            }
        }
    }
}