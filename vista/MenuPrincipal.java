package vista;

import java.sql.*;

public class MenuPrincipal {
    public static void main(String[] args) throws SQLException {
        MenuIngreso menu = new MenuIngreso();
        menu.mostrarMenuPrincipal();
    }
}