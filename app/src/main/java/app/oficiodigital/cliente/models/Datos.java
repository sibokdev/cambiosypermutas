package app.oficiodigital.cliente.models;

/**
 * Created by Ari on 29/04/2021.
 */

public class Datos {
    String id;
    String name;
    String surname1;
    String surname2;
    String email;
    String phone;
    String phone2;
    String tokenPhone;
    String municipio;
    String calle;
    String colonia;

    public Datos(String id, String name, String surname1, String surname2, String email,
                 String phone, String phone2, String tokenPhone, String municipio, String calle,
                 String colonia) {
        this.id = id;
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
        this.email = email;
        this.phone = phone;
        this.phone2 = phone2;
        this.tokenPhone = tokenPhone;
        this.municipio = municipio;
        this.calle = calle;
        this.colonia = colonia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getTokenPhone() {
        return tokenPhone;
    }

    public void setTokenPhone(String tokenPhone) {
        this.tokenPhone = tokenPhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname1() {
        return surname1;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public String getSurname2() {
        return surname2;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }
}
