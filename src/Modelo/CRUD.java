package Modelo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luis Dardon
 */
public class CRUD {
    
    Conexion databaseLink;
    
    public CRUD() {
        databaseLink = new Conexion();
        databaseLink.connectDatabase();
    }
    
   public void create(Persona persona){
        try {
            databaseLink.connection.createStatement().execute("insert into persona values(DEFAULT, '"+persona.getNombre()+"', '"+
                    persona.getApellidoPaterno()+"', '"+persona.getApellidoMaterno()+"', '"+persona.getSexo()+"', '"+persona.getFechaNacimiento()+"', "+
                    persona.getDiscapacidad()+", '"+persona.getNss()+"', '"+persona.getCurp()+"', '"+persona.getRfc()+"', "+persona.getMunicipio()+", '"+
                    persona.getColonia()+"', '"+persona.getCalle()+"', '"+persona.getCodigoPostal()+"', '"+persona.getNumCasa()+"', '"+
                    persona.getDescripcionDiscapacidad()+"', '"+persona.getHabilidades()+"')");
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public ArrayList<Persona> read(String dato){  
        Persona objPersona;

        ArrayList<Persona> Lista = new ArrayList<>();
        ResultSet resultado;
        try {
            resultado = databaseLink.connection.createStatement().executeQuery(dato);
            while(resultado.next()){
                objPersona= new Persona(
                Integer.parseInt(resultado.getString(1)), resultado.getString(2), resultado.getString(3), resultado.getString(4),
                resultado.getString(5), resultado.getString(6), Integer.parseInt(resultado.getString(7)), resultado.getString(8),
                resultado.getString(9), resultado.getString(10), Integer.parseInt(resultado.getString(11)), resultado.getString(12),
                resultado.getString(13), resultado.getString(14), resultado.getString(15), resultado.getString(16), resultado.getString(17));
                Lista.add(objPersona);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Lista;
    }
   
    public void update(Persona persona){
        try {
            String update = "UPDATE persona SET " +
                    "nombre = '" + persona.getNombre() + "', " +
                    "apellido_paterno = '" + persona.getApellidoPaterno()+ "', " +
                    "apellido_materno = '" + persona.getApellidoMaterno()+ "', " +
                    "sexo = '" + persona.getSexo()+ "', " +
                    "fecha_nacimiento = '" + persona.getFechaNacimiento()+ "', " +
                    "id_discapacidad =  " + persona.getDiscapacidad()+ ", " +
                    "numero_seguridad_social = '" + persona.getNss()+ "', " +
                    "curp = '" + persona.getCurp()+ "', " +
                    "rfc = '" + persona.getRfc() + "', " +
                    "id_municipio =  " + persona.getMunicipio()+ ", " +
                    "colonia = '" + persona.getColonia()+ "', " +
                    "calle = '" + persona.getCalle()+ "', " +
                    "codigo_postal = '" + persona.getCodigoPostal()+ "', " +
                    "numero_casa = '" + persona.getNumCasa()+ "', " +
                    "descripcion_discapacidad = '" + persona.getDescripcionDiscapacidad()+ "', " +
                    "habilidades = '" + persona.getHabilidades()+ "' " +
                    "WHERE id =  " + persona.getId();
            databaseLink.connection.createStatement().executeUpdate(update);
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id){
        try {
            databaseLink.connection.createStatement().executeUpdate("delete from persona where id = " + id);
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
   
   
    public ArrayList<String> getStringList(String dato){
        ArrayList<String> Lista = new ArrayList<>();
        ResultSet resultado;
        try {
            resultado = databaseLink.connection.createStatement().executeQuery(dato);
            while(resultado.next()){
                Lista.add(resultado.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Lista;
    }
    
    public ResultSet getDatos(int id){
        ResultSet resultado = null;
        try {
            resultado = databaseLink.connection.createStatement().executeQuery("select p.nombre, p.apellido_paterno, p.apellido_materno, p.sexo, p.fecha_nacimiento, d.nombre_discapacidad, p.numero_seguridad_social, p.curp, p.rfc, e.nombre_estado, m.nombre_municipio, p.colonia, p.calle, p.codigo_postal, p.numero_casa, p.descripcion_discapacidad, p.habilidades from persona p, discapacidad d, estado e, municipio m where p.id = "+ id +" and d.id_discapacidad = p.id_discapacidad and m.id_municipio = p.id_municipio and e.id_estado = m.id_estado");
            resultado.next();
        }catch (SQLException ex) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
    
    
}
