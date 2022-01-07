package app.cambiosypermutas.cliente.models;

/**
 * Created by Roberasd on 15/11/16.
 */

public class Auth {
    private String token;
    private String expires;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }
}
