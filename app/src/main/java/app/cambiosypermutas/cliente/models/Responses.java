package app.cambiosypermutas.cliente.models;

/**
 * Created by Roberasd on 15/11/16.
 */

public class Responses {

    private int code;
    private String message;
    private Response response;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
