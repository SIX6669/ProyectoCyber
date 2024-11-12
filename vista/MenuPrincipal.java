package vista;

import java.sql.*;
//menu principal donde se ejecuta el menu de ingreso y su metodo para mostrar el menu
public class MenuPrincipal {
    public static void main(String[] args) throws SQLException {
        MenuIngreso menu = new MenuIngreso();
        menu.mostrarMenuPrincipal();
    }
}