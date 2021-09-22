package app.oficiodigital.cliente.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Roberasd on 16/01/17.
 */

public class SubTypes {
    String id;
    String name;
    @SerializedName("type_id")
    String typeId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String type_id) {
        this.typeId = type_id;
    }
}
