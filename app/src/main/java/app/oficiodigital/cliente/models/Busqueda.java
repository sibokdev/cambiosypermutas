package app.oficiodigital.cliente.models;

import java.io.Serializable;

/**
 * Created by Ari on 07/05/2021.
 */

public class Busqueda implements Serializable {

    String name;
    String surname1;
    String surname2;
    String office;
    String municipio;
    String tokenPhone;
    String phone;
    String Description;

    public Busqueda(String name, String surname1,String surname2, String office,String municipio,
                    String tokenPhone, String phone, String Description) {
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
        this.office = office;
        this.tokenPhone=tokenPhone;
        this.phone=phone;
        this.Description=Description;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTokenPhone() {
        return tokenPhone;
    }

    public void setTokenPhone(String tokenPhone) {
        this.tokenPhone = tokenPhone;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getSurname2() {
        return surname2;
    }

    public void setSurname2(String surname2) {
        this.surname2 = surname2;
    }

    public String getSurname1() {
        return surname1;
    }

    public void setSurname1(String surname1) {
        this.surname1 = surname1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }
}
