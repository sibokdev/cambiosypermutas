package app.oficiodigital.cliente.models;

/**
 * Created by Ari on 12/04/2021.
 */

public class Ejemplo {

    private boolean erro;
    private int code_error;
    private String error_message;
    private DatosCodigo response;
    String asentamiento;
    String asentamiento2;
    String asentamiento3;
    String municipio;
    String municipio2;
    String municipio3;
    String estado;
    String estado2;
    String estado3;

    public String getAsentamiento() {

        return asentamiento;
    }
    public String getAsentamiento2() {
        return asentamiento2;
    }
    public String getAsentamiento3() {
        return asentamiento3;
    }

    public String getMunicipio() {

        return municipio;
    }
    public String getMunicipio2() {
        return municipio2;
    }
    public String getMunicipio3() {
        return municipio3;
    }

    public String getEstado() {

        return estado;
    }
    public String getEstado2() {
        return estado2;
    }
    public String getEstado3() {
        return estado3;
    }


    public void setErro(boolean erro) {
        this.erro = erro;
    }

    public void setCode_error(int code_error) {
        this.code_error = code_error;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }


    public void setAsentamiento(String asentamiento) {
        this.asentamiento = asentamiento;
    }
        public void setAsentamiento2(String asentamiento2) {
            this.asentamiento2 = asentamiento2;
        }
            public void setAsentamiento3(String asentamiento3) {
        this.asentamiento3 = asentamiento3;
    }


    public boolean isError() {
        return erro;
    }

    public int getCode_error() {
        return code_error;
    }

    public String getError_message() {
        return error_message;
    }

    public boolean isErro() {
        return erro;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
    public void setMunicipio2(String municipio2) {
        this.municipio2 = municipio2;
    }
    public void setMunicipio3(String municipio3) {
        this.municipio3 = municipio3;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    public void setEstado2(String estado2) {
        this.estado2 = estado2;
    }
    public void setEstado3(String estado3) {
        this.estado3 = estado3;
    }

    public DatosCodigo getResponse() {
        return response;
    }
}
