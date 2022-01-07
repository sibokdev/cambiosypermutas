package app.cambiosypermutas.cliente.models;

import java.io.Serializable;

public class Solicitudes implements Serializable {

    String hora;
    String fecha;
    String name;
    String surname;
    String tokenPhone;
    String phoneCliente;
    String phone;
    String status;
    String oficio;
    String created_at;
    String id;

    public Solicitudes(String hora, String fecha, String name, String surname, String tokenPhone,
                       String phoneCliente, String phone, String status, String oficio, String created_at,
                       String id) {
        this.hora = hora;
        this.fecha = fecha;
        this.name = name;
        this.surname = surname;
        this.tokenPhone=tokenPhone;
        this.phoneCliente=phoneCliente;
        this.phone=phone;
        this.status=status;
        this.oficio=oficio;
        this.created_at=created_at;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getOficio() {
        return oficio;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneCliente() {
        return phoneCliente;
    }

    public void setPhoneCliente(String phoneCliente) {
        this.phoneCliente = phoneCliente;
    }

    public String getTokenPhone() {
        return tokenPhone;
    }

    public void setTokenPhone(String tokenPhone) {
        this.tokenPhone = tokenPhone;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
