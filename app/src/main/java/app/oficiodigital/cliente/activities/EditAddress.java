package app.oficiodigital.cliente.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.textfield.TextInputLayout;
import com.orm.dsl.NotNull;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.activities.BaseActivity;
import app.oficiodigital.cliente.activities.PrincipalPerfil;
import app.oficiodigital.cliente.clients.BovedaClient;
import app.oficiodigital.cliente.models.Ejemplo;
import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.notifications.LoadingDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ari on 10/03/2021.
 */

public class EditAddress extends BaseActivity implements OnMapReadyCallback {

    private TextView id,
            lati, longi, muni, estado, select;

    private EditText calle, numero, codigop;
    private MapView mapa;
    private String phon;
    GoogleMap googleMap;
    private double longitude , latitude;
    private BovedaClient.APIBovedaClient apiBovedaClient;
    private Spinner colonia;
    private TextInputLayout ti_calle, ti_numero, ti_codigo;
    private ImageView back;

    private LocationManager ubicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);

        colonia = (Spinner) findViewById(R.id.colonia);
        lati = (TextView) findViewById(R.id.latitud);
        longi = (TextView) findViewById(R.id.longitud);
        muni = (TextView) findViewById(R.id.municipio);
        estado = (TextView) findViewById(R.id.estado);
        select = (TextView) findViewById(R.id.selec);
        //id = (TextView) findViewById(R.id.id);


        calle = (EditText) findViewById(R.id.calle);
        numero = (EditText) findViewById(R.id.numero);
        codigop = (EditText) findViewById(R.id.codigop);

        ti_calle = (TextInputLayout) findViewById(R.id.ti_calle);
        ti_numero = (TextInputLayout) findViewById(R.id.ti_numero);
        ti_codigo = (TextInputLayout) findViewById(R.id.ti_codigo);


        phon = getIntent().getStringExtra("id");

        mapa = (MapView) findViewById(R.id.mapa);

        back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(getApplicationContext(), " si sale", Toast.LENGTH_SHORT).show();
                onBackPressed();// regresar a activity anterior al presionar icon back en toolbar
            }
        });

        mapa.onCreate(savedInstanceState);

        mapa.getMapAsync(this);
        //googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }



        codigop.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String codigo = codigop.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api-sepomex.hckdrk.mx/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                apiBovedaClient = retrofit.create(BovedaClient.APIBovedaClient.class);

                Call<List<Ejemplo>> call = apiBovedaClient.getcode(codigo);
                call.enqueue(new Callback<List<Ejemplo>>() {
                    @Override
                    public void onResponse(Call<List<Ejemplo>> call, Response<List<Ejemplo>> response) {
                        if (!response.isSuccessful()) {
                            //colonia.("Code: " + response.code());
                            return;
                        }

                        List<Ejemplo> ejemplo = response.body();

                        List<String> list = new ArrayList<String>();

                        for (Ejemplo eje : ejemplo) {
                            String mu = "";
                            String esta = "";

                            list.add(eje.getResponse().getAsentamiento());

                            mu += "" + eje.getResponse().getMunicipio();
                            muni.setText("" + mu);

                            esta += "" + eje.getResponse().getEstado();
                            estado.setText("" + esta);
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), R.layout.spinner_colonia, list);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        colonia.setAdapter(adapter);

                        colonia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapter, View view,
                                                       int position, long id) {
                                String slect = colonia.getSelectedItem().toString();
                                select.setText(slect);
                            }
                            public void onNothingSelected(AdapterView<?> arg0) {}
                        });


                    }

                    @Override
                    public void onFailure(Call<List<Ejemplo>> call, Throwable t) {
                        Toast.makeText(getApplication(), "error de siempre", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapa.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapa.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapa.onPause();
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onLocationChanged(Location location) {

                longi.setText("" + location.getLongitude());
                lati.setText("" + location.getLatitude());

                if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15f));

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

    }


    public void siguiente2(View view) throws JSONException {
        if (calle.length() == 0){
            ti_calle.setError("Ingresar calle");
        }else if(numero.length() == 0){
            ti_calle.setErrorEnabled(false);
            ti_numero.setError("Ingresa numero");
        }else if(codigop.length() == 0){
            ti_numero.setErrorEnabled(false);
            ti_codigo.setError("Ingresa codigo postal");
        }else {
            ti_codigo.setErrorEnabled(false);

            String street = calle.getText().toString();
            String num = numero.getText().toString();
            String codepos = codigop.getText().toString();
            String colo = select.getText().toString();
            String munici = muni.getText().toString();
            String esta = estado.getText().toString();
            String lat = lati.getText().toString();
            String lo = longi.getText().toString();


            HashMap<String, String> params = new HashMap<>();
            params.put("calle", street);
            params.put("numero", num);
            params.put("codigopostal", codepos);
            params.put("colonia", colo);
            params.put("municipio", munici);
            params.put("estado", esta);
            params.put("latitud", lat);
            params.put("longitud", lo);


            Call<Responses> call = BovedaClient.getInstanceClient().getApiClient().updateAddress(phon,params);
            call.enqueue(new Callback<Responses>() {

                @Override
                public void onResponse(Call<Responses> call, Response<Responses> response) {
                }

                @Override
                public void onFailure(Call<Responses> call, Throwable t) {

                }
            });

            String msg = getString(R.string.EditAddress_msj);
            LoadingDialog.show(this, msg);
            Intent intent = new Intent(this, PrincipalPerfil.class);
            intent.putExtra("phone", phon);
            startActivity(intent);
        }

    }


}
