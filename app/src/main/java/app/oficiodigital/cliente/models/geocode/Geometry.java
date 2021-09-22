package app.oficiodigital.cliente.models.geocode;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NandoVelazquez on 8/17/16.
 */
public class Geometry {

    @SerializedName("location")
    private GeoLocation geoLocation;

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }
}
