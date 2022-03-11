package app.cambiosypermutas.cliente.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import app.cambiosypermutas.cliente.R;
import app.cambiosypermutas.cliente.activities.PrincipalSolicitud;
import app.cambiosypermutas.cliente.activities.PrincipalTriangulacion;
import app.cambiosypermutas.cliente.clients.BovedaClient;
import app.cambiosypermutas.cliente.models.Ejemplo;
import app.cambiosypermutas.cliente.models.ModelsDB.Phone;
import app.cambiosypermutas.cliente.models.Request.DatosIntereses;
import app.cambiosypermutas.cliente.models.Request.Intereses;
import app.cambiosypermutas.cliente.models.Responses;
import app.cambiosypermutas.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Triangulacion extends Fragment {
    DrawerLayout drawerLayout;
    private TextView muni, muni2, muni3, muni4, muni5, muni6, estado, estado2, estado3,estado4,estado5,estado6,
            select, select2, select3,select4,select5,select6;
    private EditText codigop, codigop2, codigop3,codigop4,codigop5,codigop6;
    private BovedaClient.APIBovedaClient apiBovedaClient;
    private Spinner colonia, colonia2, colonia3,colonia4,colonia5,colonia6;
    private Button guardar;
    private TextInputLayout cp1, ti_cp2, ti_cp3,ti_cp4,ti_cp5,ti_cp6;
    private ProgressBar progressBar2;

    private ImageView imagenSinConexion;
    private ScrollView datosescuela;
    private CardView noti_inter;
    /*private Button guardar, match;*/
    ArrayAdapter<String> adapter_cp;

    String phones = "", phone = "";
    String id = "", id2 = "", id3= "";
    String c1,c2,c3,tel;

    Triangulacion fragment_interes;
    DataSchool dataSchool;
    ViewDtSchool viewDtSchool;
    String busqueda;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_triangulacion, container, false);

//------------------------------------------------------------------------------
        //primerPregunta();
        //asociamos lode arriba con esto
        //casteo

     String c1 = getActivity().getIntent().getStringExtra("codigo1");
      c2 = getActivity().getIntent().getStringExtra("codigo2");
      c3 = getActivity().getIntent().getStringExtra("codigo3");
      tel = getActivity().getIntent().getStringExtra("phone");


        //codigop5.setText(c2);
        //codigop6.setText(c3);
        codigop4 = (EditText) view.findViewById(R.id.et_codigop_t);
        codigop5 = (EditText) view.findViewById(R.id.et_codigop_t2);
        codigop6 = (EditText) view.findViewById(R.id.et_codigop_t3);

        codigop4.setText(c1);

        estado4 = (TextView) view.findViewById(R.id.estado4);
        estado5 = (TextView) view.findViewById(R.id.estado5);
        estado6 = (TextView) view.findViewById(R.id.estado6);

        muni4 = (TextView) view.findViewById(R.id.municipio4);
        muni5 = (TextView) view.findViewById(R.id.municipio5);
        muni6 = (TextView) view.findViewById(R.id.municipio6);


        ti_cp4 = (TextInputLayout) view.findViewById(R.id.ti_codigop_t);
        ti_cp5 = (TextInputLayout) view.findViewById(R.id.ti_codigop_t2);
        ti_cp6 = (TextInputLayout) view.findViewById(R.id.ti_codigop_t3);


        colonia4 = (Spinner) view.findViewById(R.id.sp_colonia4);
        colonia5 = (Spinner) view.findViewById(R.id.sp_colonia5);
        colonia6 = (Spinner) view.findViewById(R.id.sp_colonia6);

        select = (TextView) view.findViewById(R.id.select);
        select2 = (TextView) view.findViewById(R.id.select2);
        select3 = (TextView) view.findViewById(R.id.select3);
        guardar = (Button) view.findViewById(R.id.btn_guardar);

        datosescuela = (ScrollView) view.findViewById(R.id.datosescuela);
        noti_inter = (CardView) view.findViewById(R.id.noti_inter);
        progressBar2 = (ProgressBar) view.findViewById(R.id.progressBar2);



        imagenSinConexion = (ImageView) view.findViewById(R.id.imagenSinConexion);
        imagenSinConexion.setVisibility(View.INVISIBLE);

        ConnectivityManager con = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = con.getActiveNetworkInfo();

        if (networkInfo!=null && networkInfo.isConnected()) {
            datosescuela.setVisibility(View.VISIBLE);
            noti_inter.setVisibility(View.INVISIBLE);

        }else {
            noti_inter.setVisibility(View.VISIBLE);
            datosescuela.setVisibility(View.INVISIBLE);
            imagenSinConexion.setVisibility(View.VISIBLE);
            //mensaje
            Toast.makeText(getContext(), "No se ha podido establecer la conexión a internet, verifique el acceso a internet e intentelo nuevamente", Toast.LENGTH_SHORT).show();
        }



        getDataIntereses();


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

        codigop4.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String codigo = codigop4.getText().toString();
                Call<List<Ejemplo>> call = BovedaClient.getInstanceClient().getApiClient().getCP(c1);
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
                            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(), R.layout.spinner_colonia, list3);
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
                EditText input = ti_cp4.getEditText();
                Pattern cod = Pattern.compile("^[0-9]{4,5}?$");
                String inputText = input.getText().toString().trim();
                if (cod.matcher(inputText).matches() == false) {
                    ti_cp4.setError(" ");
                } else {
                    ti_cp4.setErrorEnabled(false);

                }
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
                            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(), R.layout.spinner_colonia, list3);
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
                EditText input = ti_cp5.getEditText();
                Pattern cod = Pattern.compile("^[0-9]{4,5}?$");
                String inputText = input.getText().toString().trim();
                if (cod.matcher(inputText).matches() == false) {
                    ti_cp5.setError(" ");
                } else {
                    ti_cp5.setErrorEnabled(false);

                }
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
                            ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(), R.layout.spinner_colonia, list3);
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
                EditText input = ti_cp6.getEditText();
                Pattern cod = Pattern.compile("^[0-9]{4,5}?$");
                String inputText = input.getText().toString().trim();
                if (cod.matcher(inputText).matches() == false) {
                    ti_cp6.setError(" ");
                } else {
                    ti_cp6.setErrorEnabled(false);

                }
            }
        });


        return view;
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



                        guardar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), PrincipalTriangulacion.class);
                                intent.putExtra("codigo1", c1);
                                intent.putExtra("codigo2", c2);
                                intent.putExtra("codigo3", c3);
                                intent.putExtra("phone", tel);
                                startActivity(intent);
                            }
                        });



                    }else if(response.body().getCode() == 202){
                        progressBar2.setVisibility(View.GONE);

                    }

            }
            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                L.error("getDataIntereses " + t.getMessage());
            }
        });
    }



}

