package vista;

import controlador.*;

public class MenuUsuarioTransaccion {
    public static void main(String[] args) {
        TransaccionController transaccionController = new TransaccionController();
        ClienteController clienteController = new ClienteController();
        clienteController.crearUsuario();
        transaccionController.crearTransaccion(12);
    }
}