package app.oficiodigital.cliente.models.Request;

public class DatosIntereses {


    String codigo;
    String colonia;
    String municipio;
    String estado;

    public DatosIntereses(String codigo, String colonia, String municipio, String estado) {
        this.codigo = codigo;
        this.colonia = colonia;
        this.municipio = municipio;
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
