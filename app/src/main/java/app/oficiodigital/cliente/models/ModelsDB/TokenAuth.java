package app.oficiodigital.cliente.models.ModelsDB;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Unique;

public class TokenAuth extends SugarRecord {

    @Unique
    @Column(name="token")
    private String token= " ";
    @Column(name="device_session_id")
    private String device_session_id= " ";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name="userId")
    private String userId= " ";

    public String getDevice_session_id() {
        return device_session_id;
    }

    public void setDevice_session_id(String device_session_id) {
        this.device_session_id = device_session_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
