package app.oficiodigital.cliente.models;


public class Audit {

    public static final int STATUS_PENDING = 0; // default status
    public static final int STATUS_AUDIT = 1; // the user went to boveda
    public static final int STATUS_CANCELED = 2;

    private String id;
    private String client_id;
    private String date;
    private int status;
    private String notes;
    private int documents;

    public int getDocuments() {
        return documents;
    }

    public void setDocuments(int documents) {
        this.documents = documents;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
