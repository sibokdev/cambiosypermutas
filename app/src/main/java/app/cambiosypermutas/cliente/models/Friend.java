package app.cambiosypermutas.cliente.models;


public class Friend {

    public static final int STATUS_INVITED = 1;
    public static final int STATUS_USER = 2;

    private String name;
    private String date;
    private String email;
    private int status;

    public Friend() {

    }

    public Friend(String name, String date, String email, int status) {
        this.name = name;
        this.date = date;
        this.email = email;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
