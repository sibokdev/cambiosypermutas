package app.cambiosypermutas.cliente.models.ModelsDB;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Unique;

public class Phone extends SugarRecord {

    @Unique
    @Column(name="phone")
    private String phone= " ";

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
