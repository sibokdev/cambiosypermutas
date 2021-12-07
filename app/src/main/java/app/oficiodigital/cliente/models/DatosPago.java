package app.oficiodigital.cliente.models;

public class DatosPago {
    Boolean date;
    String user_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public boolean getDate() {
        return date;
    }

    public void setDate(Boolean date) {
        this.date = date;
    }
}
