package Controlador;
import Vista.MenuPrincipal;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ControladorPersonasDiscapacitadas implements MouseListener, MouseMotionListener {

    private MenuPrincipal VistaMenuPrincipal;
    private int xMouse, yMouse;

    public ControladorPersonasDiscapacitadas() {
        VistaMenuPrincipal = new MenuPrincipal();
        VistaMenuPrincipal.header.addMouseListener(this);
        VistaMenuPrincipal.header.addMouseMotionListener(this);
        VistaMenuPrincipal.textoCerrar.addMouseListener(this);
        VistaMenuPrincipal.textoAgregar.addMouseListener(this);
        VistaMenuPrincipal.textoVerLista.addMouseListener(this);
    }

    public void iniciar() {
        VistaMenuPrincipal.setVisible(true);
        VistaMenuPrincipal.setSize(250, 400);
        VistaMenuPrincipal.setLocationRelativeTo(null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == VistaMenuPrincipal.textoCerrar) System.exit(0);
        else if (e.getSource() == VistaMenuPrincipal.textoAgregar) {
            ControladorFormulario objFormulario = new ControladorFormulario();
            objFormulario.iniciar();
        } else if (e.getSource() == VistaMenuPrincipal.textoVerLista) {
            ControladorConsulta objConsulta = new ControladorConsulta();
            objConsulta.iniciar();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == VistaMenuPrincipal.header) {
            xMouse = e.getX();
            yMouse = e.getY();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == VistaMenuPrincipal.textoCerrar) {
            VistaMenuPrincipal.fondoCerrar.setBackground(Color.red);
        } else if (e.getSource() == VistaMenuPrincipal.textoAgregar) {
            VistaMenuPrincipal.fondoAgregar.setBackground(new Color(51,153,255));
            VistaMenuPrincipal.textoAgregar.setForeground(Color.white);
        } 
        else if (e.getSource() == VistaMenuPrincipal.textoVerLista)  {
            VistaMenuPrincipal.fondoVerLista.setBackground(new Color(51,153,255));
            VistaMenuPrincipal.textoVerLista.setForeground(Color.white);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == VistaMenuPrincipal.textoCerrar) {
            VistaMenuPrincipal.fondoCerrar.setBackground(new Color(51,153,255));
        } else if (e.getSource() == VistaMenuPrincipal.textoAgregar) {
            VistaMenuPrincipal.fondoAgregar.setBackground(Color.white);
            VistaMenuPrincipal.textoAgregar.setForeground(Color.black);
        }
        else if (e.getSource() == VistaMenuPrincipal.textoVerLista){
            VistaMenuPrincipal.fondoVerLista.setBackground(Color.white);
            VistaMenuPrincipal.textoVerLista.setForeground(Color.black);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == VistaMenuPrincipal.header) {
            int x = e.getXOnScreen();
            int y = e.getYOnScreen();
            VistaMenuPrincipal.setLocation(x - xMouse, y - yMouse);
        }
    }
    
    public static void main(String[] args) {
        ControladorPersonasDiscapacitadas objInicial = new ControladorPersonasDiscapacitadas();
        objInicial.iniciar();
    }
    
    @Override
    public void mouseMoved(MouseEvent e) { }
    @Override
    public void mouseReleased(MouseEvent e) {}
}