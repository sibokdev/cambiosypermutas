package app.cambiosypermutas.cliente.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by NandoVelazquez on 8/2/16.
 */
public class Service {

    public static final String ONGOING = "ongoing";
    public static final String COMPLETED_CANCELED = "completed_canceled";

    public static final int STATUS_PENDING = 1;
    public static final int STATUS_PROGAMMED = 2;
    public static final int STATUS_ON_PROCESS = 3;
    public static final int STATUS_COMPLETED = 4;
    public static final int STATUS_CANCELED = 5;

    public static final int SERVICE_STATUS_DELIVERY = 1;
    public static final int SERVICE_STATUS_COLLECTION = 2;
    public static final int SERVICE_STATUS_MIX = 3;


    @SerializedName("id_client")
    private String idClient;
    @SerializedName("service_type")
    private int serviceType;
    private int urgent;
    private double latitude;
    private double longitude;
    private String date;
    private String address;
    private String notes;
    private ArrayList<Document> documents;
    private String docsInService;
    private String id;
    private int type;
    private int status;
    private String folio;
    private String description;
    private String createdAt;
    private String updatedAt;


    public String getDocsInService() {
        return docsInService;
    }

    public void setDocsInService(String docsInService) {
        this.docsInService = docsInService;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public int getUrgent() {
        return urgent;
    }

    public void setUrgent(int urgent) {
        this.urgent = urgent;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
