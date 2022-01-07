package app.cambiosypermutas.cliente.models.ModelsDB;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Unique;

public class Token extends SugarRecord {
    @Unique
    @Column(name="userId")
    private String userId= " ";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
