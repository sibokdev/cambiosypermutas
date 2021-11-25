package app.oficiodigital.cliente.models;

import android.app.Service;
import android.location.Address;

import com.google.gson.annotations.SerializedName;

import org.w3c.dom.Document;

import java.sql.Types;
import java.util.ArrayList;

import app.oficiodigital.cliente.models.Request.DatosIntereses;

/**
 * Created by Roberasd on 25/11/16.
 */

public class Response {

    private User user;
    private Auth auth;
    private Document document;
    private Service service;
    private Payment payment;
    private DatosIntereses datosIntereses;
    private ArrayList<Service> services;
    private ArrayList<Document> documents;
    //private ArrayList<Movements> movements;
    @SerializedName("emails")
    //private ArrayList<Friend> friends;
    //private ArrayList<Audit> audits;
    //private Audit audit;
     private ArrayList<CreditCard> cards;
    private CreditCard card;
    private int cost;
    private ArrayList<Types> types;
    private ArrayList<Address> addresses;
    private Address address;
    private DatosPago datosPago;
    //private Clarification clarification;
    //private ArrayList<Clarification> clarifications;


    public DatosPago getDatosPago() {
        return datosPago;
    }

    public void setDatosPago(DatosPago datosPago) {
        this.datosPago = datosPago;
    }

    public DatosIntereses getDatosIntereses() {
        return datosIntereses;
    }

    public void setDatosIntereses(DatosIntereses datosIntereses) {
        this.datosIntereses = datosIntereses;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public ArrayList<CreditCard> getCards() {
        return cards;
    }

    public void setCards(ArrayList<CreditCard> cards) {
        this.cards = cards;
    }

    public CreditCard getCard() {
        return card;
    }

    public void setCard(CreditCard card) {
        this.card = card;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ArrayList<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<Address> addresses) {
        this.addresses = addresses;
    }

    public ArrayList<Types> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<Types> types) {
        this.types = types;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }


    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }


}
