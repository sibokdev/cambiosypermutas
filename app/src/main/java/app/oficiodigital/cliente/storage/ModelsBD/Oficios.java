package app.oficiodigital.cliente.storage.ModelsBD;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Unique;

/**
 * Created by Ari on 16/04/2021.
 */

public class Oficios extends SugarRecord {

    @Unique
    @Column(name= "oficios")
    private String oficios = "";

    public Oficios( String oficios) {
        this.oficios = oficios;
    }

    public Oficios() {

    }

    public String getOficios() {
        return oficios;
    }

    public void setOficios(String oficios) {
        this.oficios = oficios;
    }
}
