package app.cambiosypermutas.cliente.models;

public class Notificacion {
    String name;
    String surname1;
    String status;

    public Notificacion(String name, String surname1) {
        this.name = name;
        this.surname1 = surname1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname1() {
        return surname1;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }
}
