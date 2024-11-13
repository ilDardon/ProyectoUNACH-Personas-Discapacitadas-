package Modelo;

/**
 *
 * @author Luis Dardon
 */
public class Persona {
    
    private int id, discapacidad, municipio;
    private String nombre, apellidoPaterno, apellidoMaterno, sexo, nss, curp, rfc, colonia, calle, codigoPostal, numCasa, fechaNacimiento, descripcionDiscapacidad, habilidades;

    public Persona(int id, String nombre, String apellidoPaterno, String apellidoMaterno, String sexo, String fechaNacimiento, int discapacidad, String nss, String curp, String rfc, int municipio, String colonia, String calle, String codigoPostal, String numCasa, String descripcionDiscapacidad, String habilidades) {
        this.id = id;
        this.discapacidad = discapacidad;
        this.municipio = municipio;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.sexo = sexo;
        this.nss = nss;
        this.curp = curp;
        this.rfc = rfc;
        this.colonia = colonia;
        this.calle = calle;
        this.codigoPostal = codigoPostal;
        this.numCasa = numCasa;
        this.fechaNacimiento = fechaNacimiento;
        this.descripcionDiscapacidad = descripcionDiscapacidad;
        this.habilidades = habilidades;        
    }
    
    public Persona() {
        this.id = 0;
        this.discapacidad = 0;
        this.municipio = 0;
        this.nombre = null;
        this.apellidoPaterno = null;
        this.apellidoMaterno = null;
        this.sexo = null;
        this.nss = null;
        this.curp = null;
        this.rfc = null;
        this.colonia = null;
        this.calle = null;
        this.codigoPostal = null;
        this.numCasa = null;
        this.fechaNacimiento = null;
        this.descripcionDiscapacidad = null;
        this.habilidades = null;
    }

    public String getDescripcionDiscapacidad() {
        return descripcionDiscapacidad;
    }

    public void setDescripcionDiscapacidad(String descripcionDiscapacidad) {
        this.descripcionDiscapacidad = descripcionDiscapacidad;
    }

    public String getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(String habilidades) {
        this.habilidades = habilidades;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(int discapacidad) {
        this.discapacidad = discapacidad;
    }

    public int getMunicipio() {
        return municipio;
    }

    public void setMunicipio(int municipio) {
        this.municipio = municipio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }
    
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getNumCasa() {
        return numCasa;
    }

    public void setNumCasa(String numCasa) {
        this.numCasa = numCasa;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
            
    

}
