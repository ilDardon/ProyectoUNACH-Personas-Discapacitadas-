package Modelo;

/**
 *
 * @author Luis Dardon
 */
import java.io.*;
import org.json.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.imageio.ImageIO;
import org.json.JSONArray;
import org.json.JSONObject;

public class GenerarImagenMapa {

    ResultSet datos;
    CRUD objCRUD;
    String domicilio;
    BufferedImage image;
    String mapToken;
    
    public GenerarImagenMapa(int id){
        objCRUD = new CRUD();
        datos = objCRUD.getDatos(id);
        domicilio = getDomicilio(datos);
        mapToken = "pk.eyJ1IjoibHVpc2RhcmRvbiIsImEiOiJjbHdnb2JvNHkwMHIwMmxvYXRhN2wxbzh4In0.o4vWiHYjHrBw-OcMp_EyBQ";
    }
    
    public Icon getMapImage() throws IOException, JSONException{
        ImageIcon imageIcon = null;
        try{
        String geocodeUrl = "https://nominatim.openstreetmap.org/search?format=json&q=" + URLEncoder.encode(domicilio, "UTF-8");

          
            URL url = new URL(geocodeUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONArray jsonArray = new JSONArray(response.toString());
            if (jsonArray.length() > 0) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String lat = jsonObject.getString("lat");
                String lon = jsonObject.getString("lon");

                String imageUrl = "https://api.mapbox.com/styles/v1/mapbox/streets-v11/static/"
                        + lon + "," + lat + ",16/530x610?access_token=" + mapToken;
                System.out.println("URL del mapa: " + imageUrl);

                URL mapUrl = new URL(imageUrl);
                HttpURLConnection mapConn = (HttpURLConnection) mapUrl.openConnection();
                mapConn.setRequestMethod("GET");
                mapConn.setRequestProperty("User-Agent", "Mozilla/5.0");

                int responseCode = mapConn.getResponseCode();
                if (responseCode == 200) {
                    image = ImageIO.read(mapConn.getInputStream());
                    if (image != null) {
                        imageIcon = new ImageIcon(image);
                    } else {
                        System.out.println("La imagen descargada es nula.");
                    }
                } else {
                    System.out.println("Error al obtener la imagen: Código de respuesta HTTP " + responseCode);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro la direccion", "EROR AL CARGAR LA IMAGEN", 0);
                System.out.println("No se encontraron coordenadas para la direccion proporcionada.");
            }
        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
        }
        return imageIcon;
    }
    
    
    public String getDomicilio(ResultSet datos){
        String domicilio = null;
        try {
            String estado = datos.getString(10);
            String municipio = datos.getString(11);
            String calle = datos.getString(13);
            String CP = datos.getString(14);
            domicilio = calle + " " + CP + " " + municipio + " " + estado;
            
        } catch (SQLException ex) {
            Logger.getLogger(GenerarImagenMapa.class.getName()).log(Level.SEVERE, null, ex);
        }
        return domicilio;
    }
}