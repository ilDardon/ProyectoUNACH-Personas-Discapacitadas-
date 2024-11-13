package Controlador;
import Modelo.CRUD;
import Modelo.Persona;
import Vista.Formulario;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Luis Dardon
 */
public class ControladorEditar implements ActionListener, MouseListener, MouseMotionListener, KeyListener{
    
    private int idPersona;
    private Formulario VistaFormulario;
    private Persona persona;
    private CRUD objCRUD;
    private int xMouse, yMouse;
    boolean camposVacios; 
    private ControladorConsulta conCon;
    
    public ControladorEditar(int idSeleccionado, ControladorConsulta conCon){
        this.conCon = conCon;
        idPersona = idSeleccionado;
        VistaFormulario = new Formulario();
        objCRUD = new CRUD();
        persona = objCRUD.read("Select * from persona where id = " + idSeleccionado).getFirst();
        VistaFormulario.header.addMouseListener(this);
        VistaFormulario.header.addMouseMotionListener(this);
        VistaFormulario.textoCerrar.addMouseListener(this);
        VistaFormulario.textoAgregar.addMouseListener(this);
        VistaFormulario.botonesSexo.add(VistaFormulario.opcionMasculino);
        VistaFormulario.botonesSexo.add(VistaFormulario.opcionFemenino);
        VistaFormulario.opcionMasculino.addMouseListener(this);
        VistaFormulario.opcionFemenino.addMouseListener(this);
        VistaFormulario.tipoDiscapacidad.addActionListener(this);
        VistaFormulario.estado.addActionListener(this);
        for(int i = 0; i < 6; i++){
            VistaFormulario.tipoDiscapacidad.addItem(objCRUD.getStringList("select nombre_tipo from tipo_discapacidad").get(i));
        }
        for(int i = 0; i < 32; i++){
            VistaFormulario.estado.addItem(objCRUD.getStringList("select nombre_estado from estado").get(i));
        }
        VistaFormulario.nombre.addKeyListener(this);
        VistaFormulario.apellidoPaterno.addKeyListener(this);
        VistaFormulario.apellidoMaterno.addKeyListener(this);
        VistaFormulario.nss.addKeyListener(this);
        VistaFormulario.curp.addKeyListener(this);
        VistaFormulario.rfc.addKeyListener(this);
        VistaFormulario.colonia.addKeyListener(this);
        VistaFormulario.calle.addKeyListener(this);
        VistaFormulario.codigoPostal.addKeyListener(this);
        VistaFormulario.numCasa.addKeyListener(this);
    }
    
    public void iniciar(){
        VistaFormulario.setVisible(true);
        VistaFormulario.setSize(800, 700);
        VistaFormulario.setLocationRelativeTo(null);
        VistaFormulario.textoAgregar.setText("GUARDAR");
        VistaFormulario.nombre.setText(persona.getNombre());
        VistaFormulario.apellidoPaterno.setText(persona.getApellidoPaterno());
        VistaFormulario.apellidoMaterno.setText(persona.getApellidoMaterno());
        VistaFormulario.dateChooserField.setText(persona.getFechaNacimiento());
        VistaFormulario.nss.setText(persona.getNss());
        VistaFormulario.curp.setText(persona.getCurp());
        VistaFormulario.rfc.setText(persona.getRfc());
        VistaFormulario.colonia.setText(persona.getColonia());
        VistaFormulario.calle.setText(persona.getCalle());
        VistaFormulario.codigoPostal.setText(persona.getCodigoPostal());
        VistaFormulario.numCasa.setText(persona.getNumCasa());
        VistaFormulario.descripcion.setText(persona.getDescripcionDiscapacidad());
        VistaFormulario.habilidades.setText(persona.getHabilidades());
        if (persona.getSexo().equalsIgnoreCase("Masculino")) VistaFormulario.opcionMasculino.doClick();
        else VistaFormulario.opcionFemenino.doClick();
        actualizarDiscapacidades();
        actualizarMunicipios();
        
        String tipoDisc = objCRUD.getStringList(String.format(
            "select t.nombre_tipo from tipo_discapacidad t, discapacidad d where d.id_discapacidad = %d and t.id_tipo = d.id_tipo", 
            persona.getDiscapacidad())).getFirst();
        

        String disc = objCRUD.getStringList(String.format(
            "select nombre_discapacidad from discapacidad where id_discapacidad = %d", 
            persona.getDiscapacidad())).getFirst();

        String nombreEstado = objCRUD.getStringList(String.format(
            "select e.nombre_estado from estado e, municipio m where m.id_municipio = %d and e.id_estado = m.id_estado", 
            persona.getMunicipio())).getFirst();
        
        String nombreMunicipio = objCRUD.getStringList(String.format(
            "select nombre_municipio from municipio where id_municipio = %d", 
            persona.getMunicipio())).getFirst();
        
        
        seleccionarElemento(VistaFormulario.tipoDiscapacidad, tipoDisc);
        seleccionarElemento(VistaFormulario.especificarDiscapacidad, disc);
        seleccionarElemento(VistaFormulario.estado, nombreEstado);
        seleccionarElemento(VistaFormulario.municipio, nombreMunicipio);
    }
    
    public void seleccionarElemento(JComboBox comboBox, String objetivo){
         for (int i = 0; i < comboBox.getItemCount(); i++) {
            if (comboBox.getItemAt(i).equals(objetivo)) {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == VistaFormulario.textoCerrar) VistaFormulario.dispose();
        else if (e.getSource() == VistaFormulario.opcionMasculino) persona.setSexo("Masculino");
        else if (e.getSource() == VistaFormulario.opcionFemenino) persona.setSexo("Femenino");
        else if (e.getSource() == VistaFormulario.textoAgregar){
            if(actualizarPersona()){
                if(JOptionPane.showConfirmDialog(VistaFormulario, "DESEA GUARDAR NUEVOS DATOS", "CONFIRMAR", 0) == 0){
                    objCRUD.update(this.persona);
                    JOptionPane.showMessageDialog(VistaFormulario, "LA PERSONA HA SIDO ACTUALIZADA EXITOSAMENTE", "PERSONA EDITADA", 1);
                    System.out.println("Persona agregada");
                    conCon.cargarTabla();
                    VistaFormulario.dispose();
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == VistaFormulario.header) {
            xMouse = e.getX();
            yMouse = e.getY();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == VistaFormulario.textoCerrar) {
            VistaFormulario.fondoCerrar.setBackground(Color.red);
        } else if (e.getSource() == VistaFormulario.textoAgregar) VistaFormulario.fondoAgregar.setBackground(new Color(0,51,153)); 
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == VistaFormulario.textoCerrar) {
            VistaFormulario.fondoCerrar.setBackground(new Color(51,153,255));
        } else if (e.getSource() == VistaFormulario.textoAgregar) VistaFormulario.fondoAgregar.setBackground(new Color(51,153,255));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == VistaFormulario.header) {
            int x = e.getXOnScreen();
            int y = e.getYOnScreen();
            VistaFormulario.setLocation(x - xMouse, y - yMouse);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == VistaFormulario.tipoDiscapacidad) {
            actualizarDiscapacidades();
        } else if (e.getSource() == VistaFormulario.estado) {
            actualizarMunicipios();
        }
    }
    
    public void actualizarDiscapacidades() {
        VistaFormulario.especificarDiscapacidad.removeAllItems();
        int idTipo = VistaFormulario.tipoDiscapacidad.getSelectedIndex();
        ArrayList<String> discapacidades = objCRUD.getStringList("select nombre_discapacidad from discapacidad where id_tipo = " + idTipo);
        for(int i = 0; i < discapacidades.size(); i++){
            VistaFormulario.especificarDiscapacidad.addItem(discapacidades.get(i));
        }
    }
    
    public void actualizarMunicipios() {
        VistaFormulario.municipio.removeAllItems();
        int idEstado = VistaFormulario.estado.getSelectedIndex();
        ArrayList<String> municipios = objCRUD.getStringList("select nombre_municipio from municipio where id_estado = " + idEstado);
        for(int i = 0; i < municipios.size(); i++){
            VistaFormulario.municipio.addItem(municipios.get(i));
        }
    }
    
    public boolean actualizarPersona(){
        camposVacios = false;
        if (!campoVacio(VistaFormulario.nombre)) persona.setNombre(VistaFormulario.nombre.getText());
        if (!campoVacio(VistaFormulario.apellidoPaterno)) persona.setApellidoPaterno(VistaFormulario.apellidoPaterno.getText());
        if (!campoVacio(VistaFormulario.apellidoMaterno)) persona.setApellidoMaterno(VistaFormulario.apellidoMaterno.getText());
        camposVacios = setFechaNacimiento();
        setIdDiscapacidad();
        camposVacios = setNss();
        camposVacios = setCURP();
        camposVacios = setRFC();
        setIdMunicipio();
        if (!campoVacio(VistaFormulario.colonia)) persona.setColonia(VistaFormulario.colonia.getText());
        if (!campoVacio(VistaFormulario.calle)) persona.setCalle(VistaFormulario.calle.getText());
        camposVacios = setCodigoPostal();
        if (!campoVacio(VistaFormulario.numCasa)) persona.setNumCasa(VistaFormulario.numCasa.getText());
        if (!textVacio(VistaFormulario.descripcion)) persona.setDescripcionDiscapacidad(VistaFormulario.descripcion.getText());
        if (!textVacio(VistaFormulario.habilidades)) persona.setHabilidades(VistaFormulario.habilidades.getText());

        if (camposVacios) {
            JOptionPane.showMessageDialog(VistaFormulario, "HAY CAMPOS VACIOS", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            return true;
        }
    }
    
    public boolean campoVacio(JTextField campo) {
        if (campo.getText().isBlank()) {
            campo.setBorder(javax.swing.BorderFactory.createLineBorder(Color.red));
            camposVacios = true; 
            return true;
        } else {
            return false;
        }
    }
    
    
    public boolean textVacio(JTextArea campo) {
        if (campo.getText().isBlank()) {
            campo.setBorder(javax.swing.BorderFactory.createLineBorder(Color.red));
            camposVacios = true; 
            return true;
        } else {
            return false;
        }
    }
    
    public boolean setFechaNacimiento(){
        if(campoVacio(VistaFormulario.dateChooserField)){
            VistaFormulario.dateChooserField.setBorder(javax.swing.BorderFactory.createLineBorder(Color.red));
            return true;
        }
        LocalDate fechaNacimiento = LocalDate.parse(VistaFormulario.dateChooserField.getText());
        if(fechaNacimiento.compareTo(LocalDate.now()) <= 0){
            persona.setFechaNacimiento(VistaFormulario.dateChooserField.getText());
        }else{
            JOptionPane.showMessageDialog(VistaFormulario, "FECHA MAYOR A LA ACTUAL", "FECHA DE NACIMIENTO INVALIDA", 0);
            VistaFormulario.dateChooserField.setText("");
            VistaFormulario.dateChooserField.setBorder(javax.swing.BorderFactory.createLineBorder(Color.red));
        }
        return false;
    }
    
    public void setIdDiscapacidad(){
        try{
            String discapacidad = VistaFormulario.especificarDiscapacidad.getSelectedItem().toString();
            int id = Integer.parseInt(objCRUD.getStringList("select id_discapacidad from discapacidad where nombre_discapacidad = '" + discapacidad +"'").getFirst());
            persona.setDiscapacidad(id);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(VistaFormulario, "NO SE HA SELECCIONADO DISCAPACIDAD", "DISCAPACIDAD", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean setNss(){
        if(campoVacio(VistaFormulario.nss)){
            VistaFormulario.nss.setBorder(javax.swing.BorderFactory.createLineBorder(Color.red));
            return true;
        }
        String nss = VistaFormulario.nss.getText();
        if(nss.matches("\\d{11}")){
            persona.setNss(nss);
            return false;
        }else{
            JOptionPane.showMessageDialog(VistaFormulario, "UTILIZA 11 DIGITOS SIN LETRAS",  "NSS INVALIDO", 0);
            VistaFormulario.nss.setText("");
            VistaFormulario.nss.setBorder(javax.swing.BorderFactory.createLineBorder(Color.red));
            return true;
        }
        
    }
    
    public boolean setCURP(){
        if(campoVacio(VistaFormulario.curp)){
            VistaFormulario.curp.setBorder(javax.swing.BorderFactory.createLineBorder(Color.red));
            return true;
        }
        String curp = VistaFormulario.curp.getText();
        if(curp.length() == 18){
            persona.setCurp(curp.toUpperCase());
        }else{
            JOptionPane.showMessageDialog(VistaFormulario, "UTILIZA 18 CARACTERES",  "CURP INVALIDA", 0);
            VistaFormulario.curp.setText("");
            VistaFormulario.curp.setBorder(javax.swing.BorderFactory.createLineBorder(Color.red));
        }
        return false;
    }
    
    public boolean setRFC(){
        if(campoVacio(VistaFormulario.rfc)){ 
            VistaFormulario.rfc.setBorder(javax.swing.BorderFactory.createLineBorder(Color.red));
            return true;
        }
        String rfc = VistaFormulario.rfc.getText();
        if(rfc.length() == 13){
            persona.setRfc(rfc.toUpperCase());
        }else{
            JOptionPane.showMessageDialog(VistaFormulario, "UTILIZA 13 CARACTERES",  "RFC INVALIDO", 0);
            VistaFormulario.rfc.setText("");
            VistaFormulario.rfc.setBorder(javax.swing.BorderFactory.createLineBorder(Color.red));
        }
        return false;
    }
    
    public void setIdMunicipio(){
        try{
            String municipio = VistaFormulario.municipio.getSelectedItem().toString();
            int id = Integer.parseInt(objCRUD.getStringList("select id_municipio from municipio where nombre_municipio = '" + municipio +"'").getFirst());
            persona.setMunicipio(id);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(VistaFormulario, "NO SE HA SELECCIONADO MUNICIPIO", "MUNICIPIO", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean setCodigoPostal(){
        if(campoVacio(VistaFormulario.codigoPostal)){
            VistaFormulario.codigoPostal.setBorder(javax.swing.BorderFactory.createLineBorder(Color.red));
            return true;
        }
        String codigoPostal = VistaFormulario.codigoPostal.getText();
        if(codigoPostal.matches("\\d{5}")){
            persona.setCodigoPostal(codigoPostal);
            return false;
        }else{
            JOptionPane.showMessageDialog(VistaFormulario, "UTILIZA 5 DIGITOS",  "CODIGO POSTAL INVALIDO", 0);
            VistaFormulario.codigoPostal.setText("");
            VistaFormulario.codigoPostal.setBorder(javax.swing.BorderFactory.createLineBorder(Color.red));
            return true;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == VistaFormulario.nombre){if(VistaFormulario.nombre.getText().length() == 30) e.consume();}
        else if (e.getSource() == VistaFormulario.apellidoPaterno){if(VistaFormulario.apellidoPaterno.getText().length() == 15) e.consume();}
        else if (e.getSource() == VistaFormulario.apellidoMaterno){if(VistaFormulario.apellidoMaterno.getText().length() == 15) e.consume();}
        else if (e.getSource() == VistaFormulario.nss){if(VistaFormulario.nss.getText().length() == 11) e.consume();}
        else if (e.getSource() == VistaFormulario.curp){if(VistaFormulario.curp.getText().length() == 18) e.consume();}
        else if (e.getSource() == VistaFormulario.rfc){if(VistaFormulario.rfc.getText().length() == 13) e.consume();}
        else if (e.getSource() == VistaFormulario.colonia){if(VistaFormulario.colonia.getText().length() == 30) e.consume();}
        else if (e.getSource() == VistaFormulario.calle){if(VistaFormulario.calle.getText().length() == 50) e.consume();}
        else if (e.getSource() == VistaFormulario.codigoPostal){if(VistaFormulario.codigoPostal.getText().length() == 5) e.consume();}
        else if (e.getSource() == VistaFormulario.numCasa){if(VistaFormulario.numCasa.getText().length() == 5) e.consume();}
    }

    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    
    
    
    
    
    
    
    
    
    
    
    
}
