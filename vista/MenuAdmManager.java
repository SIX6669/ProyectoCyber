package vista;

import controlador.AdministradorController;
import modelo.Administrador;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public interface MenuAdmManager {

    default void modificarDatos(Scanner sc, AdministradorController ac, Administrador a) {
        System.out.println("\n=== MODIFICAR DATOS DE ADMINISTRADOR ===");
        System.out.println("Deje en blanco los campos que no desea modificar (presione Enter)");
        sc.nextLine();
        System.out.println("Nuevo nombre:");
        String nuevoNombre = sc.nextLine().trim();
        if (!nuevoNombre.isEmpty()) {
            a.setNombre(nuevoNombre);
        }

        System.out.println("Nuevo apellido:");
        String nuevoApellido = sc.nextLine().trim();
        if (!nuevoApellido.isEmpty()) {
            a.setApellido(nuevoApellido);
        }

        System.out.println("Nuevo usuario:");
        String nuevoUsuario = sc.nextLine().trim();
        if (!nuevoUsuario.isEmpty()) {
            a.setUsuario(nuevoUsuario);
        }

        System.out.println("Nueva clave:");
        String nuevaClave = sc.nextLine().trim();
        if (!nuevaClave.isEmpty()) {
            a.setClave(nuevaClave);
        }

        try {
            boolean actualizado = ac.cambiar(a);

            if (actualizado) {
                System.out.println("Datos actualizados correctamente.");
            } else {
                System.out.println("No se pudo actualizar los datos. Por favor, intente nuevamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar los datos: " + e.getMessage());
            e.printStackTrace();
        }
    }


    default void crearAdmin(Scanner sc){
        System.out.println("\n=== CREAR NUEVO ADMINISTRADOR ===");
        AdministradorController adminC = new AdministradorController();
        Administrador nuevoAdmin = new Administrador();

        try {
            sc.nextLine();
            System.out.println("Ingrese el nombre del administrador:");
            String nombre = sc.nextLine().trim();
            while (nombre.isEmpty()) {
                System.out.println("El nombre no puede estar vacío. Por favor, ingrese un nombre:");
                nombre = sc.nextLine().trim();
            }
            nuevoAdmin.setNombre(nombre);

            System.out.println("Ingrese el apellido del administrador:");
            String apellido = sc.nextLine().trim();
            while (apellido.isEmpty()) {
                System.out.println("El apellido no puede estar vacío. Por favor, ingrese un apellido:");
                apellido = sc.nextLine().trim();
            }
            nuevoAdmin.setApellido(apellido);

            System.out.println("Ingrese el nombre de usuario:");
            String usuario = sc.nextLine().trim();
            while (usuario.isEmpty()) {
                System.out.println("El usuario no puede estar vacío. Por favor, ingrese un usuario:");
                usuario = sc.nextLine().trim();
            }
            nuevoAdmin.setUsuario(usuario);

            System.out.println("Ingrese la clave:");
            String clave = sc.nextLine().trim();
            while (clave.isEmpty()) {
                System.out.println("La clave no puede estar vacía. Por favor, ingrese una clave:");
                clave = sc.nextLine().trim();
            }
            nuevoAdmin.setClave(clave);

            try {
                adminC.crearAdmin(nuevoAdmin);
                System.out.println("Administrador creado exitosamente.");
            } catch (SQLException e) {
                System.err.println("Error al crear el administrador: " + e.getMessage());
                if (e.getMessage().contains("duplicate")) {
                    System.out.println("Ya existe un administrador con ese número de legajo o usuario.");
                }
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }

    }

    default void mostrarAdmins(){
        AdministradorController adminController = new AdministradorController();
        try {
            ArrayList<Administrador> listaAdmins = adminController.seleccTodosAdmin();
            if (listaAdmins.isEmpty()) {
                System.out.println("No hay administradores registrados en el sistema.");
            } else {
                System.out.println("\n=== LISTA DE ADMINISTRADORES ===");
                System.out.println("Nro Legajo\tUsuario\t\tNombre\t\tApellido");
                System.out.println("--------------------------------------------------");
                for (Administrador admin : listaAdmins) {
                    System.out.printf("%-12d%-16s%-16s%s%n",
                            admin.getNroLegajo(),
                            admin.getUsuario(),
                            admin.getNombre(),
                            admin.getApellido()
                    );
                }
                System.out.println("\nTotal de administradores: " + listaAdmins.size());
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la lista de administradores: " + e.getMessage());
            e.printStackTrace();
        }
    }

    default void mostrarAdminPorId(Administrador a) {
        AdministradorController adminController = new AdministradorController();
        try {
            int nroLegajo = a.getNroLegajo();
            Administrador admin = adminController.seleccAdminPorId(nroLegajo);
            if (admin != null) {
                System.out.println("\n=== PERFIL DEL ADMINISTRADOR ===");
                System.out.printf("Nro Legajo: %d%n", admin.getNroLegajo());
                System.out.printf("Usuario: %s%n", admin.getUsuario());
                System.out.printf("Nombre: %s%n", admin.getNombre());
                System.out.printf("Apellido: %s%n", admin.getApellido());
            } else {
                System.out.println("No se encontró el administrador con el número de legajo proporcionado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el perfil del administrador: " + e.getMessage());
            e.printStackTrace();
        }
    }

    default void eliminarAdmin(Scanner sc, AdministradorController ac){
        System.out.println("\n=== BORRAR ADMINISTRADOR ===");
        System.out.println("Ingrese el número de legajo del administrador a borrar:");
        int nroLegajo = sc.nextInt();
        sc.nextLine(); // Limpiar el buffer después de nextInt()

        try {
            boolean resultado = ac.borrarAdmin(nroLegajo);
            if (resultado) {
                System.out.println("El administrador con número de legajo " + nroLegajo + " ha sido borrado exitosamente.");
            } else {
                System.out.println("No se encontró ningún administrador con el número de legajo " + nroLegajo);
            }
        } catch (SQLException e) {
            System.err.println("Error SQL al borrar el administrador: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
