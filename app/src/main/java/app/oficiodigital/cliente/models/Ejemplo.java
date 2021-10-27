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
    String municipio;
    String estado;

    public String getAsentamiento() {
        return asentamiento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getEstado() {
        return estado;
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

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public DatosCodigo getResponse() {
        return response;
    }
}
