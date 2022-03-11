package app.cambiosypermutas.cliente.models.ModelsDB;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Unique;

public class Estado extends SugarRecord {

    @Unique
    @Column(name="estado")
    private String estado= " ";

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
