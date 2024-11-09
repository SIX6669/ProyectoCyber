package vista;

import java.sql.SQLException;
import controlador.*;

public class MenuPrincipal {
    public static void main(String[] args) throws SQLException {
        ComputadoraController computadoraController = new ComputadoraController();
        computadoraController.verComputadoras();
    }
}