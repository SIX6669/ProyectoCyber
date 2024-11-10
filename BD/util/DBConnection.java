package BD.util;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    public static String URL ="";
    public static String user = "";
    public static String pass = "";


    public static String[] getDatos(){
        String path = "C:/Users/Nebularmess/Documents/TUP/ProgII/TPIGH/BD/util/credenciales.txt";

        String[] datos = new String[3];

        try(FileReader file = new FileReader(path);
        BufferedReader buffer = new BufferedReader(file) ){
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