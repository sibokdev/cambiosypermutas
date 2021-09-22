package app.oficiodigital.cliente.models;

import java.util.ArrayList;

/**
 * Created by Roberasd on 16/01/17.
 */

public class Types {
    String id;
    String name;
    ArrayList<SubTypes> subtypes;

    public ArrayList<SubTypes> getSubtypes() {
        return subtypes;
    }

    public void setSubtypes(ArrayList<SubTypes> subtypes) {
        this.subtypes = subtypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
