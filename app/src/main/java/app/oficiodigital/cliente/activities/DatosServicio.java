package app.oficiodigital.cliente.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.clients.BovedaClient;
import app.oficiodigital.cliente.clients.DOXClient;
import app.oficiodigital.cliente.models.Datos;
import app.oficiodigital.cliente.models.Direccion;
import app.oficiodigital.cliente.models.Ejemplo;
import app.oficiodigital.cliente.models.ModelsDB.Phone;
import app.oficiodigital.cliente.models.ModelsDB.Preguntas1;
import app.oficiodigital.cliente.models.ModelsDB.TokenAuth;
import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatosServicio extends BaseActivity implements View.OnClickListener, OnMapReadyCallback  {

    private TextView direction, phone, phone2, selecFecha, selecHora,oficio,nombre, token1,
    ap1, ap2,phoneprovee, phone3, email;
    private Button hora, fecha;
    private int dia, mes, año, horas, minu;
    private MapView mapa;
    private double longitude , latitude;
    GoogleMap googleMap;
    String phon;
    String tok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_servicio);

        direction = (TextView) findViewById(R.id.direcion);
        phone = (TextView) findViewById(R.id.phone);
        phone2 = (TextView) findViewById(R.id.phone2);
        hora = (Button) findViewById(R.id.hora);
        fecha = (Button) findViewById(R.id.fecha);
        selecFecha = (TextView) findViewById(R.id.selecFecha);
        selecHora = (TextView) findViewById(R.id.selecHora);
        oficio = (TextView)findViewById(R.id.oficio);
        token1 = (TextView)findViewById(R.id.token);
        nombre = (TextView) findViewById(R.id.nombre);
        ap1 = (TextView) findViewById(R.id.ap1);
        ap2 = (TextView) findViewById(R.id.ap2);
        phoneprovee = (TextView) findViewById(R.id.phoneprovee);
        phone3 = (TextView) findViewById(R.id.phone3);
        email = (TextView) findViewById(R.id.email);

        getPhone();

        String dato = getIntent().getStringExtra("oficio");
        oficio.setText(dato);
        String nomb = getIntent().getStringExtra("nombre");
        nombre.setText(nomb);
        String ape1 = getIntent().getStringExtra("ap1");
        ap1.setText(ape1);
        String ape2 = getIntent().getStringExtra("ap2");
        ap2.setText(ape2);
        String token = getIntent().getStringExtra("tokenPhone");
        token1.setText(token);
        String phon1 = getIntent().getStringExtra("phone");
        phoneprovee.setText(phon1);
        String phon3 = getIntent().getStringExtra("phone2");
        phone3.setText(phon3);


        hora.setOnClickListener(this);
        fecha.setOnClickListener(this);


        mapa = (MapView) findViewById(R.id.mapa);

        mapa.onCreate(savedInstanceState);
        mapa.getMapAsync(this);
        //googleMap = mapa.getMap();
        //googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }



        Call<List<Datos>> callVersiones = BovedaClient.getInstanceClient().getApiClient().getDatos(email.getText().toString());
        callVersiones.enqueue(new Callback<List<Datos>>() {
            @Override
            public void onResponse(Call<List<Datos>> call, Response<List<Datos>> response) {

                if (!response.isSuccessful()) {
                    //colonia.("Code: " + response.code());
                    return;
                }

                List<Datos> respuestas = response.body();
                List<String> list = new ArrayList<String>();

                for (Datos res : respuestas) {

                    String phon = "";
                    String phon2 = "";
                    String direc =" ";

                    phon = "" + res.getPhone();
                    phone.setText(phon);

                    phon2 = "" + res.getPhone2();
                    phone2.setText(phon2);

                    direc += " "+res.getCalle();
                    direc += ", "+res.getColonia();
                    direc += ", "+res.getMunicipio();

                    direction.setText(direc);

                    if(res.getPhone2() == null){
                        phone2.setText("No ha agregado otro teléfono");
                    }else{
                        phon2 = "" + res.getPhone2();
                        phone2.setText(phon2);
                    }



                }
            }

            @Override
            public void onFailure(Call<List<Datos>> call, Throwable t) {
                //  L.error("getOficios " + t.getMessage());
            }
        });


    }


    public void cambiaDireccion(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        builder.setTitle("Cambiar de dirección");
        builder.setMessage("¿Desea continuar con el cambio de dirección?");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Intent intent = new Intent(getApplication(), ChangeAddress.class);
                //startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if (v == fecha) {
            final Calendar c = Calendar.getInstance();

            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            año = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    selecFecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                }
            }
                    , año, mes, dia);
            datePickerDialog.show();
        }

        if (v == hora) {
            final Calendar c = Calendar.getInstance();
            horas = c.get(Calendar.HOUR_OF_DAY);
            minu = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    selecHora.setText(hourOfDay + ":" + minute);
                }
            }, horas, minu, false);
            timePickerDialog.show();

        }


    }

    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
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

                //longi.setText("" + location.getLongitude());
                //lati.setText("" + location.getLatitude());

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

    public void getPhone() {
        List<Phone> list1 = Phone.listAll(Phone.class);
        Phone phon = new Phone();
        for (Phone pho : list1) {
            String phone = "";

            phone += "" + pho.getPhone();
            email.setText("" + phone);
        }
    }

    public void requerir(View view) {

        if(selecFecha.length() == 0){
            Toast.makeText(getApplication(),"Selecciona fecha solicitada",Toast.LENGTH_SHORT).show();
        }else if(selecHora.length()==0){
            Toast.makeText(getApplication(),"Selecciona hora solicitada",Toast.LENGTH_SHORT).show();
        }else {

            tarjetass();
        }

    }

    public void tarjetass(){

        List<TokenAuth> list1 = TokenAuth.listAll(TokenAuth.class);
        for (TokenAuth pho : list1) {
            String phone = "";

            phone = pho.getToken();
            tok = phone;
        }
        String mToken = "Bearer " + tok;
        Call<Responses> call = DOXClient.getInstanceClient().getApiClient().getCard(mToken);

        call.enqueue(new Callback<Responses>() {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {

                    if (response.code() == 200) {
                        enviarDatos();
                    } else if (response.code() == 202){
                        agregarTarjetass();
                    }
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                L.error("Get cards " + t.getMessage());
                //mFinancialDataPresenter.onGetCardsFail(mContext.getString(R.string.error_getting_cards_list));
            }
        });


    }

    public void enviarDatos(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setTitle("Solicitud de cotización");
        builder.setMessage("¿Desea continuar con la solicitud de cotización?");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                Intent intent = new Intent(getApplication(), EnvioSolicitud.class);
                intent.putExtra("oficio",oficio.getText().toString());
                intent.putExtra("nombre", nombre.getText().toString());
                intent.putExtra("ap1", ap1.getText().toString());
                intent.putExtra("ap2", ap2.getText().toString());
                intent.putExtra("hora", selecHora.getText().toString());
                intent.putExtra("fecha", selecFecha.getText().toString());
                intent.putExtra("tokenPhone", token1.getText().toString());
                intent.putExtra("phone",phoneprovee.getText().toString());
                intent.putExtra("phoneCliente", phone.getText().toString());
                intent.putExtra("email", email.getText().toString());
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }
    public void agregarTarjetass(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setTitle("Agregar Tarjeta");
        builder.setMessage("¿Para continuar debe agregar una tarjeta de credito o debito?");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(getApplication(), AddCard.class);
                intent.putExtra("oficio",oficio.getText().toString());
                intent.putExtra("nombre", nombre.getText().toString());
                intent.putExtra("ap1", ap1.getText().toString());
                intent.putExtra("ap2", ap2.getText().toString());
                intent.putExtra("hora", selecHora.getText().toString());
                intent.putExtra("fecha", selecFecha.getText().toString());
                intent.putExtra("tokenPhone", token1.getText().toString());
                intent.putExtra("phone",phoneprovee.getText().toString());
                intent.putExtra("phoneCliente", phone.getText().toString());
                intent.putExtra("email", email.getText().toString());
                startActivity(intent);
            }
        });

        builder.show();

    }


}

