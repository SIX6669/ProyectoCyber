package vista;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

import controlador.*;
import modelo.*;

public interface MenuUsuarioTransaccion {
    default void crearTransaccion(int ID_Usuario, TransaccionController transaccionController) {
        ClienteController clienteController = new ClienteController();
        Scanner scanner = new Scanner(System.in);
    
        System.out.println("¿Cuánto tiempo desea comprar?");
        System.out.println("1 - 30 minutos. ($1.000)");
        System.out.println("2 - 1 hora. ($2,000)");
        System.out.println("3 - 1:30 hs. ($3.000)");
        System.out.println("4 - 2 hs. ($4.000)");
        System.out.println("Seleccione una opción: ");
        
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
            transaccionController.crear(transaccion);
            System.out.println("Transacción creada con éxito.");
            clienteController.actualizarTiempo(ID_Usuario, tiempoComprado);
            System.out.println("Tiempo acumulado del cliente actualizado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al crear la transacción: " + e.getMessage());
        } 
    }

    default void verTransacciones(TransaccionController transaccionController) {
        try {
            List<Transaccion> transacciones = transaccionController.obtener();
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
}