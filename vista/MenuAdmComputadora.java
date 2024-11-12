package vista;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import controlador.*;
import modelo.*;

public interface MenuAdmComputadora {
    default void agregarComputadora(Scanner sc) {
        Computadora computadora = new Computadora();
        ComputadoraController computadoraController = new ComputadoraController();
        try {
            computadoraController.crear(computadora);
            System.out.println("Computadora creada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al crear la computadora: " + e.getMessage());
        }
    }

    default void verComputadoras() {
        ComputadoraController computadoraController = new ComputadoraController();
        try {
            List<Computadora> computadoras = computadoraController.leer();
            if (computadoras.isEmpty()) {
                System.out.println("No se encontraron computadoras.");
            } else {
                System.out.println("Lista de computadoras:");
                for (Computadora computadora : computadoras) {
                    String estadoTexto = computadoraController.convertirEstado(computadora.getEstado());
                    String usuarioTexto = (computadora.getEstado() == 1)
                            ? ", Usuario ID: " + computadora.getID_Usuario()
                            : "";
                    System.out.println("ID: " + computadora.getID_Computadora() + ", Estado: " + estadoTexto + usuarioTexto);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener las computadoras: " + e.getMessage());
        }
    }

    default void actualizarComputadora(Scanner scanner) {
        ComputadoraController computadoraController = new ComputadoraController();
        System.out.print("Ingrese el ID de la computadora a actualizar: ");
        int ID_Computadora = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Seleccione el nuevo estado de la computadora:");
        System.out.println("0 - Disponible");
        System.out.println("1 - Ocupado");
        System.out.println("2 - Fuera de servicio");
        System.out.print("Ingrese la opción (0, 1, o 2): ");
        
        int nuevoEstado = scanner.nextInt();
        if (nuevoEstado >= 0 && nuevoEstado <= 2) {
            try {
                computadoraController.actualizar(ID_Computadora, nuevoEstado);
            } catch (SQLException e) {
                System.out.println("Error al actualizar la computadora: " + e.getMessage());
            }
        } else {
            System.out.println("Opción no válida. Intente nuevamente con 0, 1 o 2.");
        }
    }

    default void eliminarComputadora(Scanner scanner) {
        ComputadoraController computadoraController = new ComputadoraController();

        System.out.print("Ingrese el ID de la computadora a eliminar: ");
        int ID_Computadora = scanner.nextInt();

        try {
            computadoraController.eliminar(ID_Computadora);
        } catch (SQLException e) {
            System.out.println("Error al eliminar la computadora: " + e.getMessage());
        }
    }
}