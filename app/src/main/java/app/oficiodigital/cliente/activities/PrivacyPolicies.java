package app.oficiodigital.cliente.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.List;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.models.ModelsDB.TokenAuth;


/**
 * Created by Ari on 27/04/2021.
 */

public class PrivacyPolicies extends BaseActivity {
    private CheckBox terminos;
    private TextView token1, device;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policies);

        back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getApplicationContext(), " si sale", Toast.LENGTH_SHORT).show();
                onBackPressed();// regresar a activity anterior al presionar icon back en toolbar
            }
        });


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, 1000);

        }

        LocationManager ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(!ubicacion.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Activar Ubicación");
            builder.setMessage("Para continuar tu debes activar tu ubicación y es recomentable estar en tu hogar");
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        terminos = (CheckBox) findViewById(R.id.terminos);
        token1 = (TextView) findViewById(R.id.token);

        String token = getIntent().getStringExtra("tokenPhone");

        token1.setText(token);

    }

    public void siguiente(View view) {

        if (terminos.isChecked() == false) {
            terminos.setError("acepta terminos y condiciones");

        } else {

            Intent sig1 = new Intent(this, InsertPhone.class);
            sig1.putExtra("tokenPhone", token1.getText().toString());
            startActivity(sig1);
        }
    }

}
