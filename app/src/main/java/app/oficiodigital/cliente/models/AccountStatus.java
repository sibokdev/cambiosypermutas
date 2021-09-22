package app.oficiodigital.cliente.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Roberasd on 28/12/16.
 */

public class AccountStatus {
    private String id;
    private String date;
    private String amount;
    @SerializedName("payment_method")
    private String paymentMethod;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
