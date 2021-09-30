package app.oficiodigital.cliente.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.activities.BaseActivity;
import app.oficiodigital.cliente.clients.BovedaClient;
import app.oficiodigital.cliente.models.Ejemplo;
import app.oficiodigital.cliente.models.Request.RespuestaPreguntaSecreta;
import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.notifications.LoadingDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ari on 23/04/2021.
 */

//cuando usamos el escuchadorse implementa el adapterview y los metodos con el foquito y se colocan abajo los mtds
public class AddressCliente extends AppCompatActivity implements OnMapReadyCallback,AdapterView.OnItemSelectedListener {



    private BovedaClient.APIBovedaClient apiBovedaClient;
    private EditText oescuela,oclave, ozona, otel, ocp, onom_dir;
    private TextView oestado,omunicipio,select;
    private Spinner onivel_esc, oturno, ocolonia, orol, otipo_plantel ;
    private TextInputLayout ti_codigo;

    //para que relacione el spinner y sus contenidos con el layout
    ArrayAdapter<String> aacl, aane, aatn, aarl, aatp;

    //declarar un arreglo con los elemntos que contiene el spinner
    //String [] arreglo_cl = new String [] {"",""};
    String [] arreglo_ne = new String [] {"Preescolar", "Primaria", "Secundaria"};
    String [] arreglo_tn = new String [] {"Matutino", "Vespertino"};
    String [] arreglo_rl = new String [] {"", ""};
    String [] arreglo_tp = new String [] {"Municipal","Estatal", "Federal", "Federalizado"};



    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_cliente);

        //asociamos lode arriba con esto
        //casteo
        oescuela = (EditText) findViewById(R.id.et_nombre_escuela);
        oclave = (EditText) findViewById(R.id.et_clave);
        ozona = (EditText) findViewById(R.id.et_zona);
        otel = (EditText) findViewById(R.id.et_tel);
        ocp = (EditText) findViewById(R.id.et_codigop);


        oestado = (TextView) findViewById(R.id.tv_estado);
        omunicipio = (TextView) findViewById(R.id.tv_municipio);

        onom_dir = (EditText) findViewById(R.id.et_nom_dir);

        //definimos el spinner
        onivel_esc = (Spinner) findViewById(R.id.sp_nivel_esc);
        oturno = (Spinner) findViewById(R.id.sp_turno);
        ocolonia = (Spinner) findViewById(R.id.sp_colonia);
        orol = (Spinner) findViewById(R.id.sp_rol);
        otipo_plantel = (Spinner) findViewById(R.id.sp_plantel);


        //creamos un escucha para que pueda mostrar el contenidodel spinner
        //ocolonia.setOnItemSelectedListener(this);
        onivel_esc.setOnItemSelectedListener(this);
        oturno.setOnItemSelectedListener(this);
        orol.setOnItemSelectedListener(this);
        otipo_plantel.setOnItemSelectedListener(this);

        //faltaria relacionar el arrayadapter con el contendio que tendra el spinner con la vista
        aane = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arreglo_ne);
        //llenamos el contenido del sppiner con el nuevo arrayadapter
        onivel_esc.setAdapter(aane);
        //
        aatn = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arreglo_tn);
        oturno.setAdapter(aatn);
        //
        aarl = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arreglo_rl);
        orol.setAdapter(aarl);
        //
        aatp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arreglo_tp);
        otipo_plantel.setAdapter(aatp);


        ti_codigo = (TextInputLayout) findViewById(R.id.ti_codigop) ;




        ocp.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String codigop = ocp.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api-sepomex.hckdrk.mx/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                apiBovedaClient = retrofit.create(BovedaClient.APIBovedaClient.class);

                Call<List<Ejemplo>> call = apiBovedaClient.getcode(codigop);
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
                            String munic = "";
                            String estado = "";

                            list.add(eje.getResponse().getAsentamiento());

                            munic += "" + eje.getResponse().getMunicipio();
                            omunicipio.setText("" + munic);

                            estado += "" + eje.getResponse().getEstado();
                            oestado.setText("" + estado);
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), R.layout.spinner_colonia, list);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        ocolonia.setAdapter(adapter);

                        ocolonia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapter, View view,
                                                       int position, long id) {
                                String slect = ocolonia.getSelectedItem().toString();
                                select.setText(slect);


                            }

                            public void onNothingSelected(AdapterView<?> arg0) {
                            }
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

    //para validar que se metieron datos en los campos
    public void sig_lugares_intereses(View view) throws JSONException {
       if(ocp.length() == 0){
           ti_codigo.setErrorEnabled(false);
            ti_codigo.setError("Ingresa codigo postal");
        }else {
            ti_codigo.setErrorEnabled(false);

            String esc = oescuela.getText().toString();
            String clv = oclave.getText().toString();
            String zon = ozona.getText().toString();
            String tl = otel.getText().toString();
            String cp = ocp.getText().toString();
            String nom_direc= onom_dir.getText().toString();
            String estd = oestado.getText().toString();
            String mun = omunicipio.getText().toString();


           //para spinner se hace el arreglo para el indie que se seleccione
           int indice_ne = onivel_esc.getSelectedItemPosition();
           String I = "";
           if(indice_ne ==0 ){
               I = "Preescolar";
           }else if (indice_ne == 1){
               I = "Primaria";
           }else if (indice_ne == 2) {
               I = "Secundaria";
           }

           int indice_tn = oturno.getSelectedItemPosition();
           String Itn = "";
           if(indice_tn ==0 ){
               Itn = "Matutino";
           }else if (indice_tn == 1){
               Itn = "Vespertino";
           }

           int indice_rl = orol.getSelectedItemPosition();
           String Irl = "";
           if(indice_rl ==0 ){
               Irl = "";
           }else if (indice_rl == 1){
               Irl = "";
           }

           int indice_tp = oturno.getSelectedItemPosition();
           String Itp = "";
           if(indice_tp ==0 ){
               Itp = "Municipal";
           }else if (indice_tp == 1){
               Itp = "Estatal";
           }else if (indice_tp == 2){
               Itp = "Federal";
           }else if (indice_tp == 3){
               Itp = "Federalizado";
           }


          // String ticodpo = ti_codigo.getText().toString();//textinputlayout

            String colo = select.getText().toString();





            HashMap<String, String> params = new HashMap<>();
            params.put("nombre_esc", esc);
            params.put("clave_esc", clv);
            params.put("zona_esc", zon);
            params.put("telefono1", tl);
           params.put("c_postal", cp);
           params.put("nombre_direc", nom_direc);
           params.put("estado", estd);
           params.put("municipio", mun);
           params.put("nivel_esc", tl);
           params.put("turno", tl);



            Call<Responses> call = BovedaClient.getInstanceClient().getApiClient().registrarClientes(params);
            call.enqueue(new Callback<Responses>() {

                @Override
                public void onResponse(Call<Responses> call, Response<Responses> response) {
                }

                @Override
                public void onFailure(Call<Responses> call, Throwable t) {

                }
            });

            alerta();
            startActivity(new Intent(this, LoginActivity.class));
        }

    }

    public void alerta(){
        String msg = getString(R.string.creando);
        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Guardando datos");
        progress.setMessage(msg);
        progress.show();
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
