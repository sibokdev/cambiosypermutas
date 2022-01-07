package app.cambiosypermutas.cliente.models;

import java.io.Serializable;

public class Tarjetas implements Serializable {

    private String number;
    private String id_api_card;
    private int main;
    Response response;

    public Tarjetas(String number, String id_api_card, int main) {
        this.number = number;
        this.id_api_card = id_api_card;
        this.main = main;

    }

    public Tarjetas() {

    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public int getMain() {
        return main;
    }

    public void setMain(int main) {
        this.main = main;
    }

    public String getId_api_card() {
        return id_api_card;
    }

    public void setId_api_card(String id_api_card) {
        this.id_api_card = id_api_card;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
