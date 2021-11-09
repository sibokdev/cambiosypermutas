package app.oficiodigital.cliente;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import app.oficiodigital.cliente.activities.AdapterSolicitudes;
import app.oficiodigital.cliente.activities.LoginActivity;
import app.oficiodigital.cliente.activities.PrincipalMetodos;
import app.oficiodigital.cliente.activities.PrincipalSolicitud;
import app.oficiodigital.cliente.activities.SplashActivity;
import app.oficiodigital.cliente.activities.principalMenu;
import app.oficiodigital.cliente.clients.BovedaClient;
import app.oficiodigital.cliente.clients.CatalogsClient;
import app.oficiodigital.cliente.clients.DOXClient;
import app.oficiodigital.cliente.models.Ejemplo;
import app.oficiodigital.cliente.models.ModelsDB.DeviceId;
import app.oficiodigital.cliente.models.ModelsDB.Phone;
import app.oficiodigital.cliente.models.ModelsDB.TokenAuth;
import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.models.Solicitudes;
import app.oficiodigital.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private NotificationManager notificationManager;
    private static final String ADMIN_CHANNEL_ID ="admin_channel";
    private static final String CHANNEL_ID = "Notificacion";
    private static final int notificationId = 0;
    private PendingIntent pendingIntent;
    String tok,dev, phone,nombre,tokenPhone,id,nombres;
    @Override
    public void onNewToken(String token) {
        //Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        //sendRegistrationToServer(token);
        //Get phone number from android db and update token on remote db
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // En este método recibimos el mensaje
         pagos();
         getDatosSoli();
        Intent notificationIntent;
        if(LoginActivity.isAppRunning){
        // Qué hacemos si la aplicación está en primer plano
            notificationIntent = new Intent(this, principalMenu.class);
           // pagos();
        }else{
        // Qué hacemos si la aplicación está en background
           // pagos();
            notificationIntent = new Intent(this, PrincipalSolicitud.class);
        }
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_ONE_SHOT);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Configuramos la notificación para Android Oreo o superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels();
        }
        int notificationId = new Random().nextInt(60000);
        // Creamos la notificación en si
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                //.setSmallIcon(R.drawable.ic_notifications_active_black_24dp)  //a resource for your custom small icon
                .setContentTitle(remoteMessage.getData().get("title")) //the "title" value you sent in your notification
                .setContentText(remoteMessage.getData().get("message")) //ditto
                .setAutoCancel(true)  //dismisses the notification on click
                .setSmallIcon(R.drawable.logo)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(remoteMessage.getData().get("message")))
                .setShowWhen(true)
                .setContentIntent(pendingIntent)
                .setSound(defaultSoundUri);



        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(){
        CharSequence adminChannelName = "requests";//getString(R.string.notifications_admin_channel_name);
        String adminChannelDescription = "Job requests";//getString(R.string.notifications_admin_channel_description);
        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_HIGH);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }

    public void token(){




    }

    public void pagos(){
        List<TokenAuth> list1 = TokenAuth.listAll(TokenAuth.class);
        for (TokenAuth p : list1) {
            String phone = "";

            phone = p.getTokenauth();
            tok = phone;
        }

        List<DeviceId> list = DeviceId.listAll(DeviceId.class);
        for (DeviceId p : list) {
            String devi = "";

            dev = p.getDevice_session_id();

        }


        String deviceSessionId = dev;
        String mToken = "Bearer " + tok;

        Call<Responses> call = DOXClient.getInstanceClient().getApiClient()
                .addPayment(deviceSessionId, mToken);

        call.enqueue(new Callback<Responses>() {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                    if (response.code() == 200) {
                        // mAddPaymentPresenter.onAddPayment();
                        PagoSuccess();
                       // Toast.makeText(getApplication(), "success", Toast.LENGTH_SHORT).show();
                    } else {
                        // mAddPaymentPresenter.onAddPaymentFail(response.body().getMessage());
                        pagoFaile();
                        notiChane();
                        notificaciones();
                        Toast.makeText(getApplication(), "Ocurrio un error al realizar el pago intente de nuevo", Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                L.error("Add payment " + t.getMessage());
                //mAddPaymentPresenter.onAddPaymentFail(mContext.getString(R.string.error_doing_payment));
            }
        });
    }
    public void getDatosSoli(){
        List<Phone> list1 = Phone.listAll(Phone.class);
        for (Phone pho : list1) {
             phone = "";

            phone = pho.getPhone();
            //phon = phone;
        }
        Call<List<Solicitudes>> callVersiones = BovedaClient.getInstanceClient().getApiClient().getSolicitudes(phone);
        callVersiones.enqueue(new Callback<List<Solicitudes>>() {
            @Override
            public void onResponse(Call<List<Solicitudes>> call, Response<List<Solicitudes>> response) {

                if (!response.isSuccessful()) {
                    return;
                }
                List<Solicitudes> ejemplo = response.body();
                for (Solicitudes eje : ejemplo) {

                    nombre = "" + eje.getName();

                    tokenPhone = "" + eje.getTokenPhone();

                    id = "" + eje.getId();
                }
            }

            @Override
            public void onFailure(Call<List<Solicitudes>> call, Throwable t) {
                L.error("getOficios " + t.getMessage());
            }
        });
    }

    public void PagoSuccess(){

        String nom = nombre;
        String token = tokenPhone;
        HashMap<String, String> params = new HashMap<>();
        params.put("tokenPhone", token);
        params.put("name",nom);

        Call<Responses> call = CatalogsClient.getInstanceClient().getApiClient().notificaSuccess(params);
        call.enqueue(new Callback<Responses>() {

            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                L.error("login " + t.getMessage());

            }
        });

        String ids = id;
        HashMap<String, String> params1 = new HashMap<>();
        params1.put("id",ids);

        Call<Responses> call1 = BovedaClient.getInstanceClient().getApiClient().updatePago(params1);
        call1.enqueue(new Callback<Responses>() {

            @Override
            public void onResponse(Call<Responses> call1, Response<Responses> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<Responses> call1, Throwable t) {
                L.error("login " + t.getMessage());

            }
        });



    }

    public void pagoFaile(){
            String nom = nombre;
            String token = tokenPhone;
            HashMap<String, String> params = new HashMap<>();
            params.put("tokenPhone", token);
            params.put("name",nom);

            Call<Responses> call = CatalogsClient.getInstanceClient().getApiClient().notificaFaile(params);
            call.enqueue(new Callback<Responses>() {

                @Override
                public void onResponse(Call<Responses> call, Response<Responses> response) {
                    if (response.isSuccessful()) {

                    }
                }

                @Override
                public void onFailure(Call<Responses> call, Throwable t) {
                    L.error("login " + t.getMessage());

                }
            });

    }

    private void notiChane(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "notificaciones";
            NotificationChannel notificationChannel  = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void notificaciones(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("NO SE REALIZO PAGO")
                .setContentText("Se produjo un error al realizar su pago por favor intente nuevamente")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Se produjo un error al realizar su pago por favor intente nuevamente"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
    }
}