package app.oficiodigital.cliente.storage.ModelsBD;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Unique;

/**
 * Created by Ari on 16/04/2021.
 */

public class VersionCatalogo extends SugarRecord {

    @Unique
    @Column(name = "nombreTabla")
    private String nombreTabla = "";
    @Column(name = "version")
    private String version="";

    public VersionCatalogo( String nombreTabla, String version) {
        this.nombreTabla = nombreTabla;
        this.version = version;
    }

    public VersionCatalogo() {

    }


    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version= version;
    }
}
