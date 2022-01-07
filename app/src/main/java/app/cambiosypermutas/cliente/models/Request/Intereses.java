package app.cambiosypermutas.cliente.models.Request;
public class Intereses {
    private String id = "";
    private String codigo="";
    private String colonia="";
    private String municipio="";
    private String estado="";
    private String telefono="";



    public Intereses() {
        this.id = id;
        this.codigo = codigo;
        this.colonia = colonia;
        this.municipio = municipio;
        this.estado = estado;
        this.telefono = telefono;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
