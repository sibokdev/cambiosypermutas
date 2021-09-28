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

public class AddressCliente extends AppCompatActivity implements OnMapReadyCallback {



    private BovedaClient.APIBovedaClient apiBovedaClient;
    private EditText oescuela,oclave, ozona, otel, ocp, onom_dir;
    private TextView oestado,omunicipio,select;
    private Spinner onivel_esc, oturno, ocolonia, orol, oplantel ;
    private TextInputLayout ti_codigo;



    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_cliente);

        oescuela = (EditText) findViewById(R.id.nombre_escuela);
        oclave = (EditText) findViewById(R.id.clave);
        ozona = (EditText) findViewById(R.id.zona);
        otel = (EditText) findViewById(R.id.tel);
        ocp = (EditText) findViewById(R.id.codigop);
        onom_dir = (EditText) findViewById(R.id.nom_dir);

        oestado = (TextView) findViewById(R.id.estado);
        omunicipio = (TextView) findViewById(R.id.municipio);

        onivel_esc = (Spinner) findViewById(R.id.nivel_esc);
        oturno = (Spinner) findViewById(R.id.turno);
        ocolonia = (Spinner) findViewById(R.id.colonia);
        orol = (Spinner) findViewById(R.id.rol);
        oplantel = (Spinner) findViewById(R.id.plantel);
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
            String estd = oestado.getText().toString();
            String mun = omunicipio.getText().toString();

            String nom_dir = onom_dir.getText().toString();

            String colo = select.getText().toString();

           onivel_esc = (Spinner) findViewById(R.id.nivel_esc);
           oturno = (Spinner) findViewById(R.id.turno);
           ocolonia = (Spinner) findViewById(R.id.colonia);
           orol = (Spinner) findViewById(R.id.rol);
           oplantel = (Spinner) findViewById(R.id.plantel);

            HashMap<String, String> params = new HashMap<>();
            /*params.put("name", name);
            params.put("surname1", surname1);

            params.put("password", pass);
            params.put("email", mai);
            List<RespuestaPreguntaSecreta> listaRespuestas = new ArrayList<RespuestaPreguntaSecreta>();
            RespuestaPreguntaSecreta respuesta = new RespuestaPreguntaSecreta();
            respuesta.setPregunta(pre1);
            respuesta.setRespuesta(res1);

            listaRespuestas.add(respuesta);

            RespuestaPreguntaSecreta respuesta2 = new RespuestaPreguntaSecreta();
            respuesta2.setPregunta(pre2);*/


            /*listaRespuestas.add(respuesta2);


            JSONObject jResult = new JSONObject();
            JSONArray jArray = new JSONArray();

            for (int i = 0; i < listaRespuestas.size(); i++) {
                JSONObject jGroup = new JSONObject();
                jGroup.put("pregunta", listaRespuestas.get(i).getPregunta());
                jGroup.put("respuesta", listaRespuestas.get(i).getRespuesta());

                jArray.put(jGroup);
            }

            params.put("respuestas", jArray.toString());
            params.put("calle", street);

            params.put("colonia", colo);*/


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
        progress.setTitle("Creando cuenta");
        progress.setMessage(msg);
        progress.show();
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }
}
