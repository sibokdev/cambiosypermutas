package app.oficiodigital.cliente.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.models.ModelsDB.Preguntas1;
import app.oficiodigital.cliente.storage.CustomerDataPersistence;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fabric.with(this, new Crashlytics());
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_splash);

        delay();

        ///creacion de tabla de preguntas
       Preguntas1 pre1 = new Preguntas1();
        String pregunta1 = "¿Cuál es nombre de tu primera mascota?";
        pre1.setPreguntas(pregunta1);
        pre1.save();
        Preguntas1 pre2 = new Preguntas1();
        String pregunta2 = "¿Cuál es tu color favorito?";
        pre2.setPreguntas(pregunta2);
        pre2.save();
        Preguntas1 pre3 = new Preguntas1();
        String pregunta3 = "¿Nombre de la primaria a la que asististe?";
        pre3.setPreguntas(pregunta3);
        pre3.save();
        Preguntas1 pre4 = new Preguntas1();
        String pregunta4 = "¿En que ciudad naciste?";
        pre4.setPreguntas(pregunta4);
        pre4.save();

        if(pre1.getPreguntas().equals(pre1.getPreguntas())){
            Preguntas1.deleteAll(Preguntas1.class);
            Preguntas1 pre11 = new Preguntas1();
            String pregunta11 = "¿Cuál es nombre de tu primera mascota?";
            pre11.setPreguntas(pregunta11);
            pre11.save();
            Preguntas1 pre22 = new Preguntas1();
            String pregunta22 = "¿Cuál es tu color favorito?";
            pre22.setPreguntas(pregunta22);
            pre22.save();
            Preguntas1 pre33 = new Preguntas1();
            String pregunta33 = "¿Nombre de la primaria a la que asististe?";
            pre33.setPreguntas(pregunta33);
            pre33.save();
            Preguntas1 pre44 = new Preguntas1();
            String pregunta44 = "¿En que ciudad naciste?";
            pre44.setPreguntas(pregunta44);
            pre44.save();

        }

    }


    private void delay() {
        new Handler().postDelayed(() -> {
            goToLoginOrHome();
            SplashActivity.this.finish();
        }, 2000);
    }

    private void goToLoginOrHome() {
        CustomerDataPersistence sesion = new CustomerDataPersistence(this);

        if (sesion.isLoggedIn()) {
            if (sesion.isSignCompleted()) {
                if (sesion.getPayStatus() == 1)
                    startActivity(new Intent(this, LoginActivity.class));
                else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    //intent.putExtra(AddCCPaymentActivity.IS_FIRST_PAYMENT, true);
                    startActivity(intent);
                }

            } else
                startActivity(new Intent(this, LoginActivity.class));

        } else
            startActivity(new Intent(this, LoginActivity.class));
    }
}