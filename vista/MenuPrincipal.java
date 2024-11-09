package vista;
import java.util.Scanner;

public class MenuPrincipal {
    Scanner sc = new Scanner(System.in);
    String usuario;
    String clave;
    boolean admin;

    public MenuPrincipal(String usuario, String clave, boolean Admin) {
        this.usuario = usuario;
        this.clave = clave;
        this.admin = admin;
        System.out.println("Bienvenido al Cyber, " + usuario);
    }

    public void MenuUsuario() {
        int opcion;
        do {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Comprar");
            System.out.println("2. Seleccionar Computadora");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = sc.nextInt();
            boolean bandera = true;
            switch(opcion) {
                case 1:
                    do {
                        System.out.println("Has seleccionado la opción Comprar");
                        //Insertar listarTransaccion o ver como le tomamos la cantidad de horas al usuario
                        // actualizar tiempo de usuario en la tabla
                        System.out.println("Desea comprar algo más? 1 Continua 0 Vuelve al menu principal");
                        if(sc.nextInt() == 0) {
                            bandera = false;
                        }
                    } while (bandera);
                    break;

                case 2:
                    do {
                        System.out.println("Has seleccionado la opción Seleccionar Computadora");
                        //Insertar listarComputadoras para mostrar las PCs disponibles y que el usuario elija cual quiere
                        //Actualizar el estado de la PC que seleccione.
                        //Agregar texto que apunte a la PC seleccionada.
                        System.out.println("Ingrese 1 para continuar usando 0 para volver al menu principal");
                        if(sc.nextInt() == 0) {
                            bandera = false;
                        }
                    } while (bandera);

                    break;

                case 3:
                    System.out.println("Gracias por usar nuestro sistema. ¡Hasta pronto!");
                    break;

                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción correcta");
                    break;
            }

        } while(opcion != 3);
    }
}
