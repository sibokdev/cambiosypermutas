package app.oficiodigital.cliente.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by roberasd on 04/04/17.
 */

public class Clarification {
    private String clientId;
    @SerializedName("clarification_type")
    private int clarificationType;
    private String content;
    private String status;
    private String folio;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getClarificationType() {
        return clarificationType;
    }

    public void setClarificationType(int clarificationType) {
        this.clarificationType = clarificationType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }
}
