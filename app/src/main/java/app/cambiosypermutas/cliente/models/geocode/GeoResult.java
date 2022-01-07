package app.cambiosypermutas.cliente.models.geocode;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NandoVelazquez on 8/17/16.
 */
public class GeoResult {

    private Geometry geometry;

    @SerializedName("formatted_address")
    private String formattedAddress;


    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }
}
