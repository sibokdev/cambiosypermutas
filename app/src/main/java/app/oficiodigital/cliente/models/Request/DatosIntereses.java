package app.oficiodigital.cliente.models.Request;

public class DatosIntereses {


    String c_postal;
    String colonia;
    String municipio;
    String estado;
    String c_postal2;
    String colonia2;
    String municipio2;
    String estado2;
    String c_postal3;
    String colonia3;
    String municipio3;
    String estado3;


    public DatosIntereses(String c_postal, String estado, String municipio, String colonia,
                          String c_postal2, String estado2, String municipio2, String colonia2,
                          String c_postal3, String estado3, String municipio3, String colonia3) {

        this.c_postal = c_postal;
        this.estado = estado;
        this.municipio = municipio;
        this.colonia = colonia;

        this.c_postal = c_postal2;
        this.estado = estado2;
        this.municipio = municipio2;
        this.colonia = colonia2;

        this.c_postal = c_postal3;
        this.estado = estado3;
        this.municipio = municipio3;
        this.colonia = colonia3;


    }

    public String getC_postal() {
        return c_postal;
    }

    public void setC_postal(String c_postal) {
        this.c_postal = c_postal;
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

    public String getC_postal2() {
        return c_postal2;
    }

    public void setC_postal2(String c_postal2) {
        this.c_postal2 = c_postal2;
    }

    public String getColonia2() {
        return colonia2;
    }

    public void setColonia2(String colonia2) {
        this.colonia2 = colonia2;
    }

    public String getMunicipio2() {
        return municipio2;
    }

    public void setMunicipio2(String municipio2) {
        this.municipio2 = municipio2;
    }

    public String getEstado2() {
        return estado2;
    }

    public void setEstado2(String estado2) {
        this.estado2 = estado2;
    }

    public String getC_postal3() {
        return c_postal3;
    }

    public void setC_postal3(String c_postal3) {
        this.c_postal3 = c_postal3;
    }

    public String getColonia3() {
        return colonia3;
    }

    public void setColonia3(String colonia3) {
        this.colonia3 = colonia3;
    }

    public String getMunicipio3() {
        return municipio3;
    }

    public void setMunicipio3(String municipio3) {
        this.municipio3 = municipio3;
    }

    public String getEstado3() {
        return estado3;
    }

    public void setEstado3(String estado3) {
        this.estado3 = estado3;
    }
}
