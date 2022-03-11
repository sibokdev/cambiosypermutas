package app.cambiosypermutas.cliente.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import app.cambiosypermutas.cliente.R;
import app.cambiosypermutas.cliente.clients.BovedaClient;
import app.cambiosypermutas.cliente.models.Ejemplo;
import app.cambiosypermutas.cliente.models.ModelsDB.Phone;
import app.cambiosypermutas.cliente.models.Responses;
import app.cambiosypermutas.cliente.notifications.LoadingDialog;
import app.cambiosypermutas.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ari on 19/03/2021.
 */

public class ActivityTriangulacion extends BaseActivity {

    private EditText phone,codigop4,codigop5,codigop6;
    private TextView token1,estado4,estado5,estado6,muni4,muni5,muni6;
    private TextInputLayout pho;
    private Spinner colonia4,colonia5,colonia6;
    private Button guardar;
    String phones,c1,c2,c3,tel;

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triangulacion);


        c1 = getIntent().getStringExtra("codigo1");
        c2 = getIntent().getStringExtra("codigo2");
        c3 = getIntent().getStringExtra("codigo3");
        tel = getIntent().getStringExtra("phone");

        codigop4 = (EditText) findViewById(R.id.et_codigop_t);
        codigop5 = (EditText) findViewById(R.id.et_codigop_t2);
        codigop6 = (EditText) findViewById(R.id.et_codigop_t3);

        estado4 = (TextView) findViewById(R.id.estado4);
        estado5 = (TextView) findViewById(R.id.estado5);
        estado6 = (TextView) findViewById(R.id.estado6);

        muni4 = (TextView) findViewById(R.id.municipio4);
        muni5 = (TextView) findViewById(R.id.municipio5);
        muni6 = (TextView) findViewById(R.id.municipio6);

        colonia4 = (Spinner) findViewById(R.id.sp_colonia4);
        colonia5 = (Spinner) findViewById(R.id.sp_colonia5);
        colonia6 = (Spinner) findViewById(R.id.sp_colonia6);

        guardar = (Button) findViewById(R.id.btn_guardar);

        getDataIntereses();

        codigop4.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String codigo = codigop4.getText().toString();
                Call<List<Ejemplo>> call = BovedaClient.getInstanceClient().getApiClient().getCP(codigo);
                call.enqueue(new Callback<List<Ejemplo>>() {
                    @Override
                    public void onResponse(Call<List<Ejemplo>> call, Response<List<Ejemplo>> response) {
                        if (!response.isSuccessful()) {
                            //colonia.("Code: " + response.code());
                            return;
                        }

                        List<Ejemplo> ejemplo3 = response.body();

                        List<String> list3 = new ArrayList<String>();

                        for (Ejemplo eje3 : ejemplo3) {
                            String mu3 = "";
                            String esta3 = "";

                            list3.add(eje3.getAsentamiento());

                            mu3 += "" + eje3.getMunicipio();
                            muni4.setText(" " + mu3);

                            esta3 += "" + eje3.getEstado();
                            estado4.setText(" " + esta3);
                        }

                        try{
                            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getApplication(), R.layout.spinner_colonia, list3);
                            /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), R.layout.sp_colonia, list);*/
                            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            colonia4.setAdapter(adapter3);

                            colonia4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(AdapterView<?> adapter3, View view,
                                                           int position3, long id3) {
                                    String slect3 = colonia4.getSelectedItem().toString();
                                    // select4.setText(slect3);
                                }

                                public void onNothingSelected(AdapterView<?> arg0) {
                                }
                            });

                        }catch (Exception e){

                        }

                    }

                    @Override
                    public void onFailure(Call<List<Ejemplo>> call, Throwable t) {
                        // Toast.makeText(getApplication(), "error de siempre", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        codigop5.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String codigo = codigop5.getText().toString();
                Call<List<Ejemplo>> call = BovedaClient.getInstanceClient().getApiClient().getCP(codigo);
                call.enqueue(new Callback<List<Ejemplo>>() {
                    @Override
                    public void onResponse(Call<List<Ejemplo>> call, Response<List<Ejemplo>> response) {
                        if (!response.isSuccessful()) {
                            //colonia.("Code: " + response.code());
                            return;
                        }

                        List<Ejemplo> ejemplo3 = response.body();

                        List<String> list3 = new ArrayList<String>();

                        for (Ejemplo eje3 : ejemplo3) {
                            String mu3 = "";
                            String esta3 = "";

                            list3.add(eje3.getAsentamiento());

                            mu3 += "" + eje3.getMunicipio();
                            muni5.setText(" " + mu3);

                            esta3 += "" + eje3.getEstado();
                            estado5.setText(" " + esta3);
                        }

                        try{
                            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getApplication(), R.layout.spinner_colonia, list3);
                            /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), R.layout.sp_colonia, list);*/
                            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            colonia5.setAdapter(adapter3);

                            colonia5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(AdapterView<?> adapter3, View view,
                                                           int position3, long id3) {
                                    String slect3 = colonia5.getSelectedItem().toString();
                                    //  select5.setText(slect3);
                                }

                                public void onNothingSelected(AdapterView<?> arg0) {
                                }
                            });

                        }catch (Exception e){

                        }

                    }

                    @Override
                    public void onFailure(Call<List<Ejemplo>> call, Throwable t) {
                        // Toast.makeText(getApplication(), "error de siempre", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        codigop6.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String codigo = codigop6.getText().toString();
                Call<List<Ejemplo>> call = BovedaClient.getInstanceClient().getApiClient().getCP(codigo);
                call.enqueue(new Callback<List<Ejemplo>>() {
                    @Override
                    public void onResponse(Call<List<Ejemplo>> call, Response<List<Ejemplo>> response) {
                        if (!response.isSuccessful()) {
                            //colonia.("Code: " + response.code());
                            return;
                        }

                        List<Ejemplo> ejemplo3 = response.body();

                        List<String> list3 = new ArrayList<String>();

                        for (Ejemplo eje3 : ejemplo3) {
                            String mu3 = "";
                            String esta3 = "";

                            list3.add(eje3.getAsentamiento());

                            mu3 += "" + eje3.getMunicipio();
                            muni6.setText(" " + mu3);

                            esta3 += "" + eje3.getEstado();
                            estado6.setText(" " + esta3);
                        }

                        try{
                            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getApplication(), R.layout.spinner_colonia, list3);
                            /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), R.layout.sp_colonia, list);*/
                            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            colonia6.setAdapter(adapter3);

                            colonia6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(AdapterView<?> adapter3, View view,
                                                           int position3, long id3) {
                                    String slect3 = colonia6.getSelectedItem().toString();
                                    //
                                    // select6.setText(slect3);
                                }

                                public void onNothingSelected(AdapterView<?> arg0) {
                                }
                            });

                        }catch (Exception e){

                        }

                    }

                    @Override
                    public void onFailure(Call<List<Ejemplo>> call, Throwable t) {
                        // Toast.makeText(getApplication(), "error de siempre", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
    }

    private void getDataIntereses() {


        List<Phone> list1 = Phone.listAll(Phone.class);
        for (Phone pho : list1) {

            phones = pho.getPhone();


        }


        Call<Responses> callVersiones = BovedaClient.getInstanceClient().getApiClient().getIteresess(phones);
        callVersiones.enqueue(new Callback<Responses>() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                if (response.body().getCode() == 200) {

                    codigop4.setText(c1);
                    codigop4.setEnabled(false);
                    codigop5.setText(c2);
                    codigop5.setEnabled(false);
                    codigop6.setText(c3);
                    codigop6.setEnabled(false);
                    guardar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent inte = new Intent(ActivityTriangulacion.this, PrincipalTriangulacion.class);
                            inte.putExtra("phone", tel);
                            startActivity(inte);
                        }
                    });

                }

            }
            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                L.error("getDataIntereses " + t.getMessage());
            }
        });
    }
}
