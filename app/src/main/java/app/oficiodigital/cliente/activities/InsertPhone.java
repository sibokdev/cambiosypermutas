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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import app.oficiodigital.cliente.R;

import app.oficiodigital.cliente.clients.BovedaClient;
import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.notifications.LoadingDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ari on 19/03/2021.
 */

public class InsertPhone extends BaseActivity {

    private EditText phone;
    private TextView token1;
    private TextInputLayout pho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_phone);

        phone = (EditText) findViewById(R.id.phone);
        pho = (TextInputLayout)findViewById(R.id.ti_phone);
        token1 = (TextView)findViewById(R.id.token);

        String token = getIntent().getStringExtra("tokenPhone");
        token1.setText(token);
        
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



    }


    public void siguiente(View view) {

        String phon;
        phon = phone.getText().toString();

        if (!phoneValid(phon)) {
            pho.setError("Ingresa un numero telefonico valido");
        } else {
            pho.setErrorEnabled(false);


            HashMap<String, String> params = new HashMap<>();
            params.put("phone", phon);


            Call<Responses> call = BovedaClient.getInstanceClient().getApiClient().generate(params);
            call.enqueue(new Callback<Responses>() {
                @Override
                public void onResponse(Call<Responses> call, Response<Responses> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getCode() == 202) {
                            //LoadingDialog.show(InsertCode.this, getString(R.string.validando_code));
                            pho.setError("Numero ya registrado");
                        }else{
                            Intent inte = new Intent(InsertPhone.this, InsertCode.class);
                            inte.putExtra("phone", phone.getText().toString());
                            inte.putExtra("tokenPhone", token1.getText().toString());
                            alerta();
                            startActivity(inte);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Responses> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Telefono guardado", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(InsertCode.this, ProveedorDeServicios.class));

                }
            });

        }

    }

    private void alerta() {
        String msg = getString(R.string.cargando_msg);
        LoadingDialog.show(this, msg);
    }
}
