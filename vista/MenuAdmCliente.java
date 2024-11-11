package vista;

import controlador.*;
import modelo.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public interface MenuAdmCliente {
    public default void crearUsuario(Scanner sc) throws SQLException {
        ClienteController ac = new ClienteController();
        Cliente cl = new Cliente();
        sc.nextLine();
        
        System.out.println("Ingrese el nombre del cliente:");
        cl.setNombre(sc.nextLine());

        System.out.println("Ingrese el apellido del cliente:");
        cl.setApellido(sc.nextLine());
        
        System.out.println("Ingrese el teléfono del cliente:");
        String telefono;
        while (true) {
            telefono = sc.nextLine();
            if (telefono.matches("[0-9]+")) {
                cl.setTelefono(telefono);
                break;
            } else {
                System.out.println("Por favor, ingrese un número de teléfono válido (solo números):");
            }
        }

        System.out.println("Ingrese el nombre de usuario:");
        String usuario = sc.nextLine();
        cl.setUsuario(usuario);

        System.out.println("Ingrese la clave del usuario:");
        String clave = sc.nextLine();
        cl.setClave(clave);

        try {
            ac.crearUsuario(cl);
            System.out.println("Usuario creado exitosamente.");
        } catch (SQLException e) {
            System.err.println("Error al crear el usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public default void mostrarUsuarios(){
        ClienteController clienteController = new ClienteController();

        try {
            ArrayList<Cliente> listaClientes = clienteController.seleccTodoUsuario();
            if (listaClientes.isEmpty()) {
                System.out.println("No hay clientes registrados en el sistema.");
            } else {
                System.out.println("\n=== LISTA DE CLIENTES ===");
                System.out.println("ID\tNombre\t\tApellido\t\tTeléfono\t\tTiempo");
                System.out.println("------------------------------------------------");
                for (Cliente cliente : listaClientes) {
                    System.out.printf("%-8d%-16s%-16s%-16s%-16s%n",
                            cliente.getID_Usuario(),
                            cliente.getNombre(),
                            cliente.getApellido(),
                            cliente.getTelefono(),
                            cliente.getTiempo() != null ? cliente.getTiempo().toString() : "00:00:00"); 
                }
                System.out.println("Total de clientes: " + listaClientes.size());
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la lista de clientes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public default void mostrarUsuarioID(Scanner sc){
        ClienteController cliente = new ClienteController();

        System.out.println("Ingrese el ID del cliente a buscar:");
        while (!sc.hasNextInt()) {
            System.out.println("Por favor, ingrese un ID válido (debe ser un número):");
            sc.next();
        }
        int idBusqueda = sc.nextInt();
        sc.nextLine();
        try {
            Cliente clienteEncontrado = cliente.mostrarInfoUsuario(idBusqueda);
            if (clienteEncontrado == null) {
                System.out.println("No se pudo completar la búsqueda.");
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public default void eliminarUsuario(Scanner sc, ClienteController c){
        System.out.println("\n=== ELIMINAR CLIENTE ===");
        System.out.println("Ingrese el ID del cliente a eliminar:");
        while (!sc.hasNextInt()) {
            System.out.println("Por favor, ingrese un ID válido (debe ser un número):");
            sc.next();
        }
        int idEliminar = sc.nextInt();
        sc.nextLine(); 

        System.out.println("¿Está seguro que desea eliminar al cliente con ID " + idEliminar + "? (S/N):");
        String confirmacion = sc.nextLine().trim().toUpperCase();

        if (confirmacion.equals("S")) {
            try {
                c.eliminarUsuario(idEliminar);
            } catch (SQLException e) {
                System.err.println("Error al eliminar el cliente: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Operación de eliminación cancelada.");
        }
    }

    public default void modificarUsuario(int ID_Usuario, Scanner scanner) {
        ClienteController cliente = new ClienteController();

        System.out.println("\n--- Modificar Usuario ---");
        scanner.nextLine();

        System.out.print("Ingrese el nuevo nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese el nuevo apellido: ");
        String apellido = scanner.nextLine();

        System.out.print("Ingrese el nuevo teléfono: ");
        String telefono = scanner.nextLine().trim();
        while (telefono.isEmpty() || !telefono.matches("[0-9]+")) {
            System.out.println("Por favor, ingrese un número de teléfono válido (solo dígitos):");
            telefono = scanner.nextLine().trim();
        }

        try {
            cliente.modificarUsuario(ID_Usuario, nombre, apellido, telefono);
        } catch (SQLException e) {
            System.out.println("Error al modificar el usuario: " + e.getMessage());
        }
    }
}