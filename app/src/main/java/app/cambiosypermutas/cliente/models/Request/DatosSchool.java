package app.cambiosypermutas.cliente.models.Request;

public class DatosSchool {

    String nombre_esc;
    String clave_esc;
    String nivel_escolar;
    String turno;
    String zona_esc;
    String telefono;
    String c_postal;
    String estado;
    String municipio;
    String colonia;
    String nombre_direc;
    String categoria;
    String tipo_plantel;
    String nombramiento;
    String labor;
    String nota;
    String procedimiento;
    String rol;



    public DatosSchool(String nombre_esc, String clave_esc, String nivel_escolar, String turno, String zon_esc, String telefono, String c_postal, String estado, String municipio, String colonia, String nombre_direc, String tipo_plantel, String nombramiento, String labor, String nota, String procedimiento, String rol) {
        this.nombre_esc = nombre_esc;
        this.clave_esc = clave_esc;
        this.nivel_escolar = nivel_escolar;
        this.turno = turno;
        this.zona_esc = zon_esc;
        this.telefono = telefono;
        this.c_postal = c_postal;
        this.estado = estado;
        this.municipio = municipio;
        this.colonia = colonia;
        this.nombre_direc = nombre_direc;
        this.categoria = categoria;
        this.tipo_plantel = tipo_plantel;
        this.nombramiento = nombramiento;
        this.labor = labor;
        this.nota = nota;
        this.procedimiento = procedimiento;
        this.rol = rol;
    }

    public String getNombre_esc() {
        return nombre_esc; }

    public void setNombre_esc(String nombre_esc) {
        this.nombre_esc = nombre_esc;
    }

    public String getClave_esc() {
        return clave_esc;
    }

    public void setClave_esc(String clave_esc) {
        this.clave_esc = clave_esc;
    }

    public String getNivel_escolar() {
        return nivel_escolar;
    }

    public void setNivel_escolar(String nivel_escolar) {
        this.nivel_escolar = nivel_escolar;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getZon_esc() {
        return zona_esc;
    }

    public void setZon_esc(String zon_esc) {
        this.zona_esc = zon_esc;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getC_postal() {
        return c_postal;
    }

    public void setC_postal(String c_postal) {
        this.c_postal = c_postal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getNombre_direc() {
        return nombre_direc;
    }

    public void setNombre_direc(String nombre_direc) {
        this.nombre_direc = nombre_direc;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    public String getTipo_plantel() {
        return tipo_plantel;
    }

    public void setTipo_plantel(String tipo_plantel) {
        this.tipo_plantel = tipo_plantel;
    }

    public String getNombramiento() {
        return nombramiento;
    }

    public void setNombramiento(String nombramiento) {
        this.nombramiento = nombramiento;
    }

    public String getLabor() {
        return labor;
    }

    public void setLabor(String labor) {
        this.labor = labor;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
