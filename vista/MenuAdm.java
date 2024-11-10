package vista;

import controlador.AdministradorController;
import controlador.ClienteController;
import modelo.Administrador;
import modelo.Cliente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuAdm {
    private Administrador a = new Administrador();
    private ClienteController c = new ClienteController();
    AdministradorController ac = new AdministradorController();
    private int tipoUsuario;
    private Scanner sc = new Scanner(System.in);
    private static boolean ejecutar = true;

    public MenuAdm(String usuario, String clave) {
        this.a.usuario = usuario;
        this.a.clave = clave;
    }

    public void MenuLogueado() {
        System.out.println("Bienvenido " + a.usuario);
        while (ejecutar) {
            System.out.println("\n=== MENÚ ADMINISTRADOR ===");
            System.out.println("1. Ver detalle de su cuenta");
            System.out.println("2. Modificar sus datos");
            System.out.println("3. Listar Administradores");
            System.out.println("4. Crear nuevo Admin");
            System.out.println("5. Eliminar admin");
            System.out.println("6. Crear nuevo cliente");
            System.out.println("7. listar Clientes");
            System.out.println("8. Buscar cliente por ID");
            System.out.println("9. Eliminar cliente");
            System.out.println("10. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = sc.nextInt();
            try {
                switch (opcion) {
                    case 1:
                        ac.seleccAdminLegajo(12);//id que busca esta fijo hay que dinamizarlo una vez que inicie sesion con un usuario real
                        break;
                    case 2:
                        System.out.println("\n=== MODIFICAR DATOS DE ADMINISTRADOR ===");
                        Administrador adminModificar = new Administrador();

                        System.out.println("Ingrese el número de legajo del administrador a modificar:");
                        int nroLegajo = sc.nextInt();
                        sc.nextLine();
                        adminModificar.setNroLegajo(nroLegajo);

                        System.out.println("Deje en blanco los campos que no desea modificar (presione Enter)");

                        System.out.println("Nuevo nombre :");
                        String nuevoNombre = sc.nextLine().trim();
                        if (!nuevoNombre.isEmpty()) {
                            adminModificar.setNombre(nuevoNombre);
                        }

                        System.out.println("Nuevo apellido:");
                        String nuevoApellido = sc.nextLine().trim();
                        if (!nuevoApellido.isEmpty()) {
                            adminModificar.setApellido(nuevoApellido);
                        }

                        System.out.println("Nuevo usuario :");
                        String nuevoUsuario = sc.nextLine().trim();
                        if (!nuevoUsuario.isEmpty()) {
                            adminModificar.setUsuario(nuevoUsuario);
                        }

                        System.out.println("Nueva clave :");
                        String nuevaClave = sc.nextLine().trim();
                        if (!nuevaClave.isEmpty()) {
                            adminModificar.setClave(nuevaClave);
                        }

                        try {
                            boolean actualizado = ac.cambiar(adminModificar);

                            if (actualizado) {
                                System.out.println("Datos actualizados correctamente.");
                            } else {
                                System.out.println("No se pudo actualizar los datos. Por favor, intente nuevamente.");
                            }
                        } catch (SQLException e) {
                            System.err.println("Error al actualizar los datos: " + e.getMessage());
                            e.printStackTrace();
                        }
                        break;
                    case 3:
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
                        break;
                    case 4:
                        System.out.println("\n=== CREAR NUEVO ADMINISTRADOR ===");
                        AdministradorController adminC = new AdministradorController();
                        Administrador nuevoAdmin = new Administrador();

                        sc.nextLine();

                        try {
                            System.out.println("Ingrese el número de legajo:");
                            while (!sc.hasNextInt()) {
                                System.out.println("Por favor, ingrese un número de legajo válido:");
                                sc.next();
                            }
                            nroLegajo = sc.nextInt();
                            sc.nextLine();
                            nuevoAdmin.setNroLegajo(nroLegajo);

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
                        break;
                    case 5:
                        System.out.println("Ingrese el número de legajo del administrador a borrar:");
                        nroLegajo = sc.nextInt();
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
                        break;
                    case 6:
                        ClienteController ac = new ClienteController();
                        Cliente cl = new Cliente();
                        sc.nextLine();
                        System.out.println("Ingrese el nombre del usuario");
                        cl.setNombre(sc.nextLine());
                        System.out.println("Ingrese el Apellido del usuario");
                        cl.setApellido(sc.nextLine());
                        System.out.println("Ingrese el Telefono del usuario");
                        while (!sc.hasNextInt()) {
                            System.out.println("Por favor, ingrese un número de teléfono válido:");
                            sc.next();
                        }
                        cl.setTelefono(sc.nextInt());
                        sc.nextLine();
                        ac.crearUsuario(cl);
                    case 7:
                        ClienteController clienteController = new ClienteController();
                        try {
                            ArrayList<Cliente> listaClientes = clienteController.seleccTodoUsuario();
                            if (listaClientes.isEmpty()) {
                                System.out.println("No hay clientes registrados en el sistema.");
                            } else {
                                System.out.println("\n=== LISTA DE CLIENTES ===");
                                System.out.println("ID\tNombre\t\tApellido\t\tTeléfono");
                                System.out.println("------------------------------------------------");
                                for (Cliente cliente : listaClientes) {
                                    System.out.printf("%-8d%-16s%-16s%d%n",
                                            cliente.getID_Usuario(),
                                            cliente.getNombre(),
                                            cliente.getApellido(),
                                            cliente.getTelefono());
                                }
                                System.out.println("Total de clientes: " + listaClientes.size());
                            }
                        } catch (SQLException e) {
                            System.err.println("Error al obtener la lista de clientes: " + e.getMessage());
                            e.printStackTrace();
                        }
                        break;
                    case 8:
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
                        break;
                    case 9:
                        System.out.println("\n=== ELIMINAR CLIENTE ===");
                        System.out.println("Ingrese el ID del cliente a eliminar:");

                        while (!sc.hasNextInt()) {
                            System.out.println("Por favor, ingrese un ID válido (debe ser un número):");
                            sc.next(); // Descarta la entrada no válida
                        }
                        int idEliminar = sc.nextInt();
                        sc.nextLine(); // Limpiar buffer

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
                        break;
                    case 10:
                        ejecutar = false;
                        System.out.println("¡Hasta pronto!");
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, intente nuevamente.");
                        break;
                }
            } catch (SQLException e) {
                System.err.println("Error SQL: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
