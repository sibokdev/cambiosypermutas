package app.oficiodigital.cliente.models.ModelsDB;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Unique;

public class TokenAuth extends SugarRecord {

    @Unique
    @Column(name="tokenauth")
    private String tokenauth= " ";
    @Column(name="device_session_id")
    private String device_session_id= " ";

    public String getDevice_session_id() {
        return device_session_id;
    }

    public void setDevice_session_id(String device_session_id) {
        this.device_session_id = device_session_id;
    }

    public String getTokenauth() {
        return tokenauth;
    }

    public void setTokenauth(String token) {
        this.tokenauth = token;
    }
}
