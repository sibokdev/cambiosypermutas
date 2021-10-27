package app.oficiodigital.cliente.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.clients.BovedaClient;
import app.oficiodigital.cliente.models.Ejemplo;
import app.oficiodigital.cliente.models.ModelsDB.Preguntas1;
import app.oficiodigital.cliente.models.Responses;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataSchool extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener{




        private BovedaClient.APIBovedaClient apiBovedaClient;
        private EditText oescuela,oclave, ozona, otel, ocp, onom_dir;
        private TextView oestado,omunicipio,select;
        private Spinner onivel_esc, oturno, ocolonia, orol, otipo_plantel ;
        private TextInputLayout ti_codigo;





        @SuppressLint("MissingPermission")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_address_cliente);
            //setContentView(R.layout.activity_principal_menu);
            //primerPregunta();
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
           // orol = (Spinner) findViewById(R.id.sp_rol);
            otipo_plantel = (Spinner) findViewById(R.id.sp_plantel);

            ti_codigo = (TextInputLayout) findViewById(R.id.ti_codigop);

            //Creamos un arreglo o un vector que nos permita agregar todos lo textos que querramos
            String [] opciones = {"Preescolar", "Primaria", "Secundaria"};

            // NUeva clase
            // Cremos la comunicacion para el spinner en layout
            ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,opciones);
            //le decimos que coloque dtodo esto dentro del spinner
            onivel_esc.setAdapter(adapter);





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
  /*  public void primerPregunta() {
        List<Preguntas1> list = Preguntas1.listAll(Preguntas1.class);
        List<String> lis = new ArrayList<String>();
        Preguntas1 preguntas = new Preguntas1();
        for (int i = 0; i < list.size(); i++) {
            preguntas = list.get(i);
            lis.add(preguntas.getPreguntas());
            //Preguntas1.deleteAll(Preguntas1.class);
        }
        String [] opciones = {"Preescolar", "Primaria", "Secundaria"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplication(), R.layout.sp, opciones);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        onivel_esc.setAdapter(adapter1);

    }*/

        //para validar que se metieron datos en los campos
        public void sig_lugares_intereses(View view) throws JSONException {


                String esc = oescuela.getText().toString();
                String clv = oclave.getText().toString();
                String zon = ozona.getText().toString();
                String tl = otel.getText().toString();
                String cp = ocp.getText().toString();
                String nom_direc= onom_dir.getText().toString();
                String estd = oestado.getText().toString();
                String mun = omunicipio.getText().toString();
                String colo = select.getText().toString();


                //guardar la seleccion del usuario del spinner
                String seleccion = onivel_esc.getSelectedItem().toString();
                if (seleccion.equals("Preescolar")){
                    /*int suma = valor1_int + valor2_int;
                    String resultado = String.valueOf(suma);
                    tv1.setText(resultado);*/
                }else if (seleccion.equals("Primaria")){
                }else if (seleccion.equals("Secundaria")){
                }




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


