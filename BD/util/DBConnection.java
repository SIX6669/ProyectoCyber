package BD.util;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//Clase para conectar a la base de datos

public class DBConnection {
    public static String URL ="";
    public static String user = "";
    public static String pass = "";


    public static String[] getDatos(){
        //path del archivo.txt de donde se obtienen las credenciales
        String path = "/Users/simon/Documents/GitHub/ProyectoCyber/BD/util/credenciales.txt";
        //array donde se alojarn las credennciales
        String[] datos = new String[3];
        //abrimos y leemos el archivo con fileREader y BufferedReader
        try(FileReader file = new FileReader(path);
        BufferedReader buffer = new BufferedReader(file) ){
            //declaramos line como variable donde va el valor de cada linea del archivo txt
            String line;

            for(int i = 0; i<datos.length; i++){
                line  = buffer.readLine();

                if(line != null){
                    datos[i] = line;
                }else{
                    break;
                }
            }
        }catch(IOException e){
            System.out.println("Error");
            e.printStackTrace();
        }
        return datos;
    }

//Le asignamos los valores al vector datos por cada valor/una linea
    public static Connection setConnection() throws SQLException {
        String[] datos = getDatos();
        URL = datos[0];
        user = datos[1];
        pass = datos[2];

        return DriverManager.getConnection(URL, user, pass);
    }

    //Insertar(Tabla Y Columna)

    //Listar

    //Eliinar

    //REstar
}