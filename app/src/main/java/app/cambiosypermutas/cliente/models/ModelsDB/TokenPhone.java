package app.cambiosypermutas.cliente.models.ModelsDB;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Unique;

public class TokenPhone extends SugarRecord {

    @Unique
    @Column(name="TokenPhone")
    private String TokenPhone= " ";

    public String getTokenPhone() {
        return TokenPhone;
    }

    public void setTokenPhone(String tokenPhone) {
        TokenPhone = tokenPhone;
    }
}
