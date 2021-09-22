package app.oficiodigital.cliente.models.geocode;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NandoVelazquez on 8/17/16.
 */
public class Geocode {
    public static final String STATUS_OK = "OK";

    @SerializedName("results")
    private GeoResult result[];
    private String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GeoResult[] getResult() {
        return result;
    }

    public void setResult(GeoResult[] result) {
        this.result = result;
    }
}
