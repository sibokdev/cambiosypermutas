package app.oficiodigital.cliente.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by roberasd on 05/04/17.
 */

public class Paypal {

    private String id;
    private String state;
    @SerializedName("create_time")
    private String createdTime;
    private String intent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }
}
