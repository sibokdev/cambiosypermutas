package app.oficiodigital.cliente.storage.ModelsBD;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Unique;

/**
 * Created by Ari on 14/04/2021.
 */

public class Preguntas1 extends SugarRecord {
    @Unique
    @Column(name="preguntas")
    private  String preguntas = "";

    public Preguntas1() {}

    public Preguntas1(String preguntas) {
        this.preguntas = preguntas;
    }

    public String getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(String preguntas) {
        this.preguntas = preguntas;
    }
}
