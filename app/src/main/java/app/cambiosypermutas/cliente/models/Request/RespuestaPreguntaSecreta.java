package app.cambiosypermutas.cliente.models.Request;

/**
 * Created by Ari on 16/04/2021.
 */

public class RespuestaPreguntaSecreta {
    private String respuesta="";
    private String pregunta="";

    public RespuestaPreguntaSecreta() {
        this.respuesta = respuesta;
        this.pregunta = pregunta;

    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }
}
