package Controlador;
import Modelo.CRUD;
import Modelo.Persona;
import Vista.Consulta;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Luis Dardon
 */
public class ControladorConsulta implements MouseListener, MouseMotionListener, KeyListener{
    
    private Consulta VistaConsulta;
    private int xMouse, yMouse;
    private CRUD objCRUD;
    private int idSeleccionado;

    public ControladorConsulta() {
        objCRUD = new CRUD();
        VistaConsulta = new Consulta();
        VistaConsulta.header.addMouseListener(this);
        VistaConsulta.header.addMouseMotionListener(this);
        VistaConsulta.textoCerrar.addMouseListener(this);
        VistaConsulta.buscar.addKeyListener(this);
        VistaConsulta.tablaPersonas.addMouseListener(this);
        VistaConsulta.textoDetalles.addMouseListener(this);
        VistaConsulta.textoEditar.addMouseListener(this);
        VistaConsulta.textoEliminar.addMouseListener(this);
        idSeleccionado = 0;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == VistaConsulta.textoCerrar) VistaConsulta.dispose();
        else if (e.getSource() == VistaConsulta.tablaPersonas){
            int row = VistaConsulta.tablaPersonas.rowAtPoint(e.getPoint());
            if (row >= 0) idSeleccionado = Integer.parseInt((String) VistaConsulta.tablaPersonas.getValueAt(row, 0));
        } else if (e.getSource() == VistaConsulta.textoDetalles){
            if(idSeleccionado != 0){
                ResultSet datos = objCRUD.getDatos(idSeleccionado);
                try {
                    ControladorDetalles objOpConsulta = new ControladorDetalles(idSeleccionado, datos, this);
                    objOpConsulta.iniciar();
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(ControladorConsulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }else JOptionPane.showMessageDialog(VistaConsulta, "PRIMERO SELECCIONA UNA PERSONA", "DETALLES", 1);
        } else if (e.getSource() == VistaConsulta.textoEditar) {
            if(idSeleccionado != 0){
                try{
                    ControladorEditar editar = new ControladorEditar(idSeleccionado, this);
                    editar.iniciar();
                }catch(Exception ex){
                    System.out.println(ex);
                }
            }else JOptionPane.showMessageDialog(VistaConsulta, "PRIMERO SELECCIONA UNA PERSONA", "EDITAR", 1);
        } else if (e.getSource() == VistaConsulta.textoEliminar) {
            if(idSeleccionado != 0){
                try{
                    if(JOptionPane.showConfirmDialog(VistaConsulta, "DESEA ELIMINAR A ESTA PERSONA", "CONFIRMACION ELIMINAR", 1) == 0){
                        objCRUD.delete(idSeleccionado);
                        JOptionPane.showMessageDialog(VistaConsulta, "PERSONA ELIMINADA CORRECTAMENTE", "PERSONA ELIMINADA", 1);
                        cargarTabla();
                    }
                }catch(Exception ex){
                    System.out.println(ex);
                }
            }else JOptionPane.showMessageDialog(VistaConsulta, "PRIMERO SELECCIONA UNA PERSONA", "ELIMINAR", 1);
        }
    }

    public void iniciar(){
        VistaConsulta.setVisible(true);
        VistaConsulta.setSize(980, 700);
        VistaConsulta.setLocationRelativeTo(null);
        cargarTabla();
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == VistaConsulta.header) {
            xMouse = e.getX();
            yMouse = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == VistaConsulta.textoCerrar) {
            VistaConsulta.fondoCerrar.setBackground(Color.red);
            VistaConsulta.textoCerrar.setForeground(Color.white);
        }else if (e.getSource() == VistaConsulta.textoDetalles) {
            VistaConsulta.fondoDetalles.setBackground(new Color(51,153,255));
            VistaConsulta.textoDetalles.setForeground(Color.white);
        }else if (e.getSource() == VistaConsulta.textoEditar) {
            VistaConsulta.fondoEditar.setBackground(new Color(51,153,255));
            VistaConsulta.textoEditar.setForeground(Color.white);
        }else if (e.getSource() == VistaConsulta.textoEliminar) {
            VistaConsulta.fondoEliminar.setBackground(Color.red);
            VistaConsulta.textoEliminar.setForeground(Color.white);
        } 
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == VistaConsulta.textoCerrar) {
            VistaConsulta.fondoCerrar.setBackground(new Color(51,153,255));
            VistaConsulta.textoCerrar.setForeground(Color.black);
        }else if (e.getSource() == VistaConsulta.textoDetalles) {
            VistaConsulta.fondoDetalles.setBackground(Color.white);
            VistaConsulta.textoDetalles.setForeground(Color.black);
        }else if (e.getSource() == VistaConsulta.textoEditar) {
            VistaConsulta.fondoEditar.setBackground(Color.white);
            VistaConsulta.textoEditar.setForeground(Color.black);
        }else if (e.getSource() == VistaConsulta.textoEliminar) {
            VistaConsulta.fondoEliminar.setBackground(Color.white);
            VistaConsulta.textoEliminar.setForeground(Color.black);
        } 
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == VistaConsulta.header) {
            int x = e.getXOnScreen();
            int y = e.getYOnScreen();
            VistaConsulta.setLocation(x - xMouse, y - yMouse);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == VistaConsulta.buscar){
            DefaultTableModel model = (DefaultTableModel) VistaConsulta.tablaPersonas.getModel();
            TableRowSorter<DefaultTableModel> ordenar = new TableRowSorter<>(model);
            VistaConsulta.tablaPersonas.setRowSorter(ordenar);
            ordenar.setRowFilter(RowFilter.regexFilter("(?i)" + VistaConsulta.buscar.getText()));
        }
    }
        
    public void cargarTabla(){
       
        ArrayList<Persona> lista = objCRUD.read("select * from persona");
        
        DefaultTableModel modeloTabla = (DefaultTableModel)VistaConsulta.tablaPersonas.getModel();
        modeloTabla.setRowCount(0);
        for(Persona persona: lista){
            String apellidos = persona.getApellidoPaterno() + " " + persona.getApellidoMaterno();
            String discapacidad = objCRUD.getStringList("select nombre_discapacidad from discapacidad where id_discapacidad = " + persona.getDiscapacidad()).get(0);
            String municipio = objCRUD.getStringList("select nombre_municipio from municipio where id_municipio = " + persona.getMunicipio()).get(0);
            String estado = objCRUD.getStringList("select e.nombre_estado from municipio m, estado e where m.id_municipio = " + persona.getMunicipio() +" AND e.id_estado = m.id_estado").get(0);
            String habilidades = persona.getHabilidades();
            modeloTabla.addRow(new String[]{Integer.toString(persona.getId()), persona.getNombre(), apellidos, estado, municipio,
                discapacidad, habilidades});
        }
    }
    
    public static void main(String[] args) {
        ControladorConsulta objInicial = new ControladorConsulta();
        objInicial.iniciar();
    }
    
}
