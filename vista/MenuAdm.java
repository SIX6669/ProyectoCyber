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
            System.out.println("1. Ver detalle de su cuenta");//cuenta del adm, falta hacer un read
            System.out.println("2. Modificar sus datos");// cuienta del adm, falta hacer el update
            System.out.println("3. Registrar transaccion");
            System.out.println("4. Crear nuevo cliente"); //esta en el controlador del adm
            System.out.println("5. listar Clientes"); //esta en el controlador del adm
            System.out.println("6. Buscar cliente por ID"); //esta en el controlador del adm
            System.out.println("7. Eliminar cliente");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = sc.nextInt();
            try {
                switch (opcion) {
                    case 1:
                        ac.seleccAdminLegajo(12);
                        break;
                    case 2:
                        c.modificarUsuario();
                        break;
                    case 3: //ver logica de juampi
                        break;
                    case 4:
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
                    case 5:
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
                    case 6:
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
                    case 7:
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
                    case 8:
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
