package app.oficiodigital.cliente.models;

public class BusquedaCP {

    String rol;
    String nivel_escolar;
    String tipo_plantel;

    public BusquedaCP(String rol, String nivel_escolar, String tipo_plantel) {
        this.rol = rol;
        this.nivel_escolar = nivel_escolar;
        this.tipo_plantel = tipo_plantel;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNivel_escolar() {
        return nivel_escolar;
    }

    public void setNivel_escolar(String nivel_escolar) {
        this.nivel_escolar = nivel_escolar;
    }

    public String getTipo_plantel() {
        return tipo_plantel;
    }

    public void setTipo_plantel(String tipo_plantel) {
        this.tipo_plantel = tipo_plantel;
    }
}
