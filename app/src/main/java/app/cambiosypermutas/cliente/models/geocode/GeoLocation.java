package app.cambiosypermutas.cliente.models.geocode;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NandoVelazquez on 8/17/16.
 */
public class GeoLocation {
    @SerializedName("lat")
    private String latitude;
    @SerializedName("lng")
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
