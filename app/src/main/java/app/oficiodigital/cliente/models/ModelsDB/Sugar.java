package app.oficiodigital.cliente.models.ModelsDB;


import com.orm.SugarApp;
import com.orm.SugarContext;

/**
 * Created by Ari on 14/04/2021.
 */

public class Sugar extends SugarApp {

    @Override
    public void onCreate(){
        super.onCreate();
        SugarContext.init(getApplicationContext());
    }

    @Override
    public void onTerminate(){
        super.onTerminate();
        SugarContext.terminate();
    }
}
