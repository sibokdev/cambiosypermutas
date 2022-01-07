package app.cambiosypermutas.cliente.storage.ModelsBD;

import com.orm.SchemaGenerator;
import com.orm.SugarApp;
import com.orm.SugarContext;
import com.orm.SugarDb;

/**
 * Created by Ari on 14/04/2021.
 */

public class Sugar extends SugarApp {

    @Override
    public void onCreate(){
        super.onCreate();
        SugarContext.init(getApplicationContext());
        // create table if not exists
        SchemaGenerator schemaGenerator = new SchemaGenerator(this);
        schemaGenerator.createDatabase(new SugarDb(this).getDB());
    }

    @Override
    public void onTerminate(){
        super.onTerminate();
        SugarContext.terminate();
    }
}
