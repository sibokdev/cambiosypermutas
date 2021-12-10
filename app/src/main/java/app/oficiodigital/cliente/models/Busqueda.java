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
    String estado;
    String municipio;
    String tokenPhone;
    String phone;
    String Description;
    String rol;
    String nivel_escolar;
    String tipo_plantel;
    String nombre_esc;
    String turno;


    public Busqueda(String name, String surname1,String surname2, String office,String municipio,
                    String estado, String tipo, String tokenPhone, String phone, String Description, String rol) {
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
        this.estado = estado;
        this.tipo_plantel = tipo;
        this.office = office;
        this.tokenPhone=tokenPhone;
        this.phone=phone;
        this.Description=Description;

    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Busqueda() {

    }

    public String getNombre_esc() {
        return nombre_esc;
    }

    public void setNombre_esc(String nombre_esc) {
        this.nombre_esc = nombre_esc;
    }

    public String getNivel_escolar() {
        return nivel_escolar;
    }

    public void setNivel_escolar(String nivel_escolar) {
        this.nivel_escolar = nivel_escolar;
    }

    public String getTipo_plantel() {
        return tipo_plantel;
    }

    public void setTipo_plantel(String tipo_plantel) {
        this.tipo_plantel = tipo_plantel;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setOffice(String office) {
        this.office = office;
    }
}