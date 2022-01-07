package app.cambiosypermutas.cliente.storage.ModelsBD;

import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Unique;

/**
 * Created by Ari on 14/04/2021.
 */

public class Preguntas extends SugarRecord {

    @Unique
    @Column(name="pregunta1")
    private  String pregunta1 = "";

    public Preguntas() {}

    public Preguntas(String pregunta1) {
        this.pregunta1 = pregunta1;
    }

    public String getPregunta1() {
        return pregunta1;
    }

    public void setPregunta1(String pregunta1) {
        this.pregunta1 = pregunta1;
    }

}
