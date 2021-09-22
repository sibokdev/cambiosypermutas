package app.oficiodigital.cliente.models;

/**
 * Created by Ari on 12/04/2021.
 */

public class DatosCodigo {

    String cp;
    String asentamiento;
    String municipio;
    String estado;

    public String getCp() {
        return cp;
    }

    public String getAsentamiento() {
        return asentamiento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public void setAsentamiento(String asentamiento) {
        this.asentamiento = asentamiento;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
