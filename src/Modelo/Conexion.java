package Modelo;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author Luis Dardon
 */
public class Conexion {
   
    Connection connection = null;
 
    public void connectDatabase() {
        try {
            try { 
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
            }
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/control_discapacitados",
                    "postgres", "holabuenastardes");
 
            boolean valid = connection.isValid(50000);
            System.out.println(valid ? "CONECTADO" : "ERROR EN LA CONEXION");
        } catch (java.sql.SQLException sqle) {
            System.out.println("Error: " + sqle);
        }
    } 
      public static void main(String[] args) {
        Conexion javaPostgreSQLBasic = new Conexion();
        javaPostgreSQLBasic.connectDatabase(); 
    }  
}