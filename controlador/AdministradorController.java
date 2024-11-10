package controlador;
import modelo.Administrador;
import modelo.Cliente;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static BD.util.DBConnection.setConnection;

public class AdministradorController {
    //Para Usuarios
    private static final String INSERT_ADMIN_SQL = "INSERT INTO Admin (NroLegajo, Usuario, Clave, Nombre, Apellido) VALUES (?, ?)";
    private static final String SELECT_ADMIN_BY_ID = "SELECT * FROM Admin WHERE NroLegajo = ?";
    private static final String SELECT_ALL_ADMIN = "SELECT * FROM Admin";
    private static final String DELETE_ADMIN_SQL = "DELETE FROM Admin WHERE NroLegajo = ?";
    //

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

    public void seleccAdminLegajo(int nroLegajo) throws SQLException{
        try(Connection connection = setConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT_ADMIN_BY_ID)) {
            ps.setInt(1, nroLegajo);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Administrador admin= new Administrador(rs.getInt("NroLegajo"),rs.getString("Usuario"),
                        rs.getString("Clave"),rs.getString("Nombre"),
                        rs.getString("Apellido"));
                System.out.println(admin.toString());
            }
        }
    }

    public ArrayList<Administrador> seleccTodosAdmin() throws SQLException{
        ArrayList<Administrador> admins = new ArrayList<>();
        try(Connection connection = setConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_ADMIN)){

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Administrador admin = new Administrador(rs.getInt("NroLegajo"),rs.getString("Usuario"),
                        rs.getString("Clave"),rs.getString("Nombre"),
                        rs.getString("Apellido"));
                admins.add(admin);
            }
        }
        return admins;
    }

    public boolean cambiar(Administrador admin)throws SQLException{
        StringBuilder sql = new StringBuilder("UPDATE Admin SET ");

        boolean isFirst = true;
        if(admin.getNombre() != null){
            sql.append("Nombre = ?");
            isFirst = false;
        }
        if(admin.getApellido() != null){
            sql.append("Apellido = ?");
            isFirst = false;
        }
        if (admin.getUsuario() != null) {
            sql.append("Usuario = ?");
            isFirst = false;
        }
        if (admin.getClave() != null){
            sql.append("Clave = ?");
            isFirst = false;
        }


        sql.append(" WHERE NroLegajo = ?");

        try(Connection connection = setConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_ADMIN)){

            int paramIndex = 1;

            if(admin.getUsuario() != null){
                ps.setString(paramIndex++, admin.getUsuario());
            }
            if(admin.getNombre() != null){
                ps.setString(paramIndex++,admin.getNombre());
            }
            if(admin.getApellido() != null){
                ps.setString(paramIndex++,admin.getApellido());
            }
            if (admin.getClave() != null){
                ps.setString(paramIndex++,admin.getClave());
            }
            ps.setInt(paramIndex,admin.getNroLegajo());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean borrarAdmin(int nroLegajo)throws SQLException{
        boolean filaBorrada = false;

        try(Connection connection = setConnection();
            PreparedStatement ps = connection.prepareStatement(DELETE_ADMIN_SQL)) {
            ps.setInt(1, nroLegajo);
            filaBorrada = ps.executeUpdate() > 0;
        }
        return filaBorrada;
    }


}