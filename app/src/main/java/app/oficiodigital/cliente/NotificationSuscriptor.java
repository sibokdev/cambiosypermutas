package app.oficiodigital.cliente;

import com.google.firebase.messaging.FirebaseMessaging;
import com.orm.SugarApp;

public class NotificationSuscriptor extends SugarApp {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseMessaging.getInstance().subscribeToTopic("oficio_digital_proveedor");
    }
}
