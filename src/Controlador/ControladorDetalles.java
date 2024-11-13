package Controlador;

import Modelo.CRUD;
import Modelo.GenerarImagenMapa;
import Vista.Detalles;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luis Dardon
 */
public class ControladorDetalles implements MouseListener, MouseMotionListener{
    
    private int id;
    private Detalles VistaOpciones;
    private GenerarImagenMapa geo;
    private ResultSet datos;
    private int xMouse, yMouse;
    private CRUD objCRUD;
    private ControladorConsulta visCon;
    
    public ControladorDetalles(int id, ResultSet datos, ControladorConsulta visCon) throws IOException {
        this.visCon = visCon;
        this.id = id;
        this.datos = datos;
        objCRUD = new CRUD();
        VistaOpciones = new Detalles();
        VistaOpciones.header.addMouseListener(this);
        VistaOpciones.header.addMouseMotionListener(this);
        VistaOpciones.textoCerrar.addMouseListener(this);
        geo = new GenerarImagenMapa(id);
        VistaOpciones.mapa.setIcon(geo.getMapImage());
    }
    
    
    public void setDatosPersona(){
        try {
            String NombreCompleto = datos.getString(1) +" "+ datos.getString(2) +" "+ datos.getString(3);
            String sexoSeleccionado = datos.getString(4);
            String fechaNacimiento = datos.getString(5);
            String discapacidad = datos.getString(6);
            String nss = datos.getString(7);
            String curp = datos.getString(8);
            String rfc = datos.getString(9);
            String estado = datos.getString(10);
            String municipio = datos.getString(11);
            String colonia = datos.getString(12);
            String calle = datos.getString(13);
            String CP = datos.getString(14);
            String noCasa = datos.getString(15);
            String descDisc = datos.getString(16);
            String habilidades = datos.getString(17);
            
            VistaOpciones.datosPersona.setText("Nombre: " + NombreCompleto +
                            "\nSexo: " + sexoSeleccionado +
                            "\nFecha de nacimiento: " + fechaNacimiento +
                            "\nDiscapacidad: " + discapacidad +
                            "\nNSS: " + nss +
                            "\nCURP: " + curp +
                            "\nRFC: " + rfc +
                            "\nEstado: " + estado +
                            "\nMunicipio: " + municipio +
                            "\nColonia: " + colonia +
                            "\nCalle: " + calle +
                            "\nCodigo Postal: " + CP +
                            "\nNumero de Casa: " + noCasa +
                            "\nDescripcion Discapacidad: " + descDisc +
                            "\nHabilidades: " + habilidades);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorDetalles.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
    }
    
    public void iniciar(){
        VistaOpciones.setVisible(true);
        VistaOpciones.setSize(970, 700);
        VistaOpciones.setLocationRelativeTo(null);
        setDatosPersona();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == VistaOpciones.textoCerrar) VistaOpciones.dispose();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == VistaOpciones.header) {
            xMouse = e.getX();
            yMouse = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == VistaOpciones.textoCerrar) {
            VistaOpciones.fondoCerrar.setBackground(Color.red);
            VistaOpciones.textoCerrar.setForeground(Color.white);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == VistaOpciones.textoCerrar) {
            VistaOpciones.fondoCerrar.setBackground(new Color(204, 255, 204));
            VistaOpciones.textoCerrar.setForeground(Color.black);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == VistaOpciones.header) {
            int x = e.getXOnScreen();
            int y = e.getYOnScreen();
            VistaOpciones.setLocation(x - xMouse, y - yMouse);
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {}
    
}
