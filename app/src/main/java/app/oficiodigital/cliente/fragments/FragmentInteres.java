package app.oficiodigital.cliente.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.activities.principalMenu;
import app.oficiodigital.cliente.clients.BovedaClient;
import app.oficiodigital.cliente.models.Ejemplo;
import app.oficiodigital.cliente.models.ModelsDB.Phone;
import app.oficiodigital.cliente.models.ModelsDB.TokenAuth;
import app.oficiodigital.cliente.models.Request.DatosIntereses;
import app.oficiodigital.cliente.models.Request.DatosSchool;
import app.oficiodigital.cliente.models.Request.Intereses;
import app.oficiodigital.cliente.models.Request.RespuestaPreguntaSecreta;
import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentInteres extends Fragment {
    DrawerLayout drawerLayout;
    private TextView muni, muni2, muni3, estado, estado2, estado3, select, select2, select3;
    private EditText codigop, codigop2, codigop3;
    private BovedaClient.APIBovedaClient apiBovedaClient;
    private Spinner colonia, colonia2, colonia3;
    private Button guardar;
    /*private Button guardar, match;*/
    ArrayAdapter<String> adapter_cp;

    String phones = "", phone = "";

    FragmentInteres fragment_interes;
    DataSchool dataSchool;
    ViewDtSchool viewDtSchool;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interes, container, false);


//------------------------------------------------------------------------------
        //primerPregunta();
        //asociamos lode arriba con esto
        //casteo
        codigop = (EditText) view.findViewById(R.id.et_codigop);
        codigop2 = (EditText) view.findViewById(R.id.et_codigop2);
        codigop3 = (EditText) view.findViewById(R.id.et_codigop3);
        estado = (TextView) view.findViewById(R.id.estado);
        estado2 = (TextView) view.findViewById(R.id.estado2);
        estado3 = (TextView) view.findViewById(R.id.estado3);
        muni = (TextView) view.findViewById(R.id.municipio);
        muni2 = (TextView) view.findViewById(R.id.municipio2);
        muni3 = (TextView) view.findViewById(R.id.municipio3);

        colonia = (Spinner) view.findViewById(R.id.sp_colonia);
        colonia2 = (Spinner) view.findViewById(R.id.sp_colonia2);
        colonia3 = (Spinner) view.findViewById(R.id.sp_colonia3);

        select = (TextView) view.findViewById(R.id.select);
        select2 = (TextView) view.findViewById(R.id.select2);
        select3 = (TextView) view.findViewById(R.id.select3);
        guardar = (Button) view.findViewById(R.id.btn_guardar);

        getDataIntereses();

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.conten, new DataSchool()).addToBackStack(null).commit();
                codigop.setError(null);

                String cp = codigop.getText().toString();
                String estd = estado.getText().toString();
                String mun = muni.getText().toString();
                String colo = select.getText().toString();

                String cp2 = codigop2.getText().toString();
                String estd2 = estado2.getText().toString();
                String mun2 = muni2.getText().toString();
                String colo2 = select2.getText().toString();

                String cp3 = codigop3.getText().toString();
                String estd3 = estado3.getText().toString();
                String mun3 = muni3.getText().toString();
                String colo3 = select3.getText().toString();

                /*String guard = guardar.*/


                if (TextUtils.isEmpty(cp)) {
                    codigop.setError(getString(R.string.error_campo_oblogatorio));
                    codigop.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(cp2)) {
                    codigop2.setError(getString(R.string.error_campo_oblogatorio));
                    codigop2.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(cp3)) {
                    codigop3.setError(getString(R.string.error_campo_oblogatorio));
                    codigop3.requestFocus();
                    return;
                }


                Toast.makeText(getContext(), "Se ha validado correctamente", Toast.LENGTH_SHORT).show();
                List<Phone> userId = Phone.listAll(Phone.class);
                for (Phone phon : userId) {


                    phone = phon.getPhone();
                }

                //Envio a BD
                HashMap<String, String> params = new HashMap<>();

                List<Intereses> intereses = new ArrayList<Intereses>();
                Intereses codigos = new Intereses();
                codigos.setCodigo(cp);
                codigos.setColonia(colo);
                codigos.setMunicipio(mun);
                codigos.setEstado(estd);
                codigos.setTelefono(phone);

                intereses.add(codigos);
                Intereses codigos1 = new Intereses();
                codigos1.setCodigo(cp2);
                codigos1.setColonia(colo2);
                codigos1.setMunicipio(mun2);
                codigos1.setEstado(estd2);
                codigos1.setTelefono(phone);

                intereses.add(codigos1);
                Intereses codigos2 = new Intereses();
                codigos2.setCodigo(cp3);
                codigos2.setColonia(colo3);
                codigos2.setMunicipio(mun3);
                codigos2.setEstado(estd3);
                codigos2.setTelefono(phone);

                intereses.add(codigos2);


                JSONObject jResult = new JSONObject();
                JSONArray jArray = new JSONArray();

                for (int i = 0; i < intereses.size(); i++) {
                    JSONObject jGroup = new JSONObject();
                    try {
                        jGroup.put("codigo", intereses.get(i).getCodigo());
                        jGroup.put("colonia", intereses.get(i).getColonia());
                        jGroup.put("municipio", intereses.get(i).getMunicipio());
                        jGroup.put("estado", intereses.get(i).getEstado());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        jGroup.put("telefono", phone);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jArray.put(jGroup);
                }

                params.put("intereses", jArray.toString());


                Call<Responses> call = BovedaClient.getInstanceClient().getApiClient().registroInte(params, phone);
                call.enqueue(new Callback<Responses>() {


                    @Override
                    public void onResponse(Call<Responses> call, Response<Responses> response) {
                    }

                    @Override
                    public void onFailure(Call<Responses> call, Throwable t) {

                    }
                });

            }
        });


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        codigop.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String codigo = codigop.getText().toString();
                Call<List<Ejemplo>> call = BovedaClient.getInstanceClient().getApiClient().getCP(codigo);
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

                            list.add(eje.getAsentamiento());

                            mu += "" + eje.getMunicipio();
                            muni.setText(" " + mu);

                            esta += "" + eje.getEstado();
                            estado.setText(" " + esta);
                        }

                        adapter_cp = new ArrayAdapter<String>(getContext(), R.layout.spinner_colonia, list);
                        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), R.layout.sp_colonia, list);*/
                        adapter_cp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        colonia.setAdapter(adapter_cp);

                        colonia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapter, View view,
                                                       int position, long id) {
                                String slect = colonia.getSelectedItem().toString();
                                select.setText(slect);
                            }

                            public void onNothingSelected(AdapterView<?> arg0) {
                            }
                        });

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

        codigop2.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String codigo = codigop2.getText().toString();
                Call<List<Ejemplo>> call = BovedaClient.getInstanceClient().getApiClient().getCP(codigo);
                call.enqueue(new Callback<List<Ejemplo>>() {
                    @Override
                    public void onResponse(Call<List<Ejemplo>> call, Response<List<Ejemplo>> response) {
                        if (!response.isSuccessful()) {
                            //colonia.("Code: " + response.code());
                            return;
                        }

                        List<Ejemplo> ejemplo2 = response.body();

                        List<String> list2 = new ArrayList<String>();

                        for (Ejemplo eje2 : ejemplo2) {
                            String mu2 = "";
                            String esta2 = "";

                            list2.add(eje2.getAsentamiento());

                            mu2 += "" + eje2.getMunicipio();
                            muni2.setText(" " + mu2);

                            esta2 += "" + eje2.getEstado();
                            estado2.setText(" " + esta2);
                        }

                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), R.layout.spinner_colonia, list2);
                        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), R.layout.sp_colonia, list);*/
                        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        colonia2.setAdapter(adapter2);

                        colonia2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapter2, View view,
                                                       int position2, long id2) {
                                String slect2 = colonia2.getSelectedItem().toString();
                                select2.setText(slect2);
                            }

                            public void onNothingSelected(AdapterView<?> arg0) {
                            }
                        });

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

        codigop3.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String codigo = codigop3.getText().toString();
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
                            muni3.setText(" " + mu3);

                            esta3 += "" + eje3.getEstado();
                            estado3.setText(" " + esta3);
                        }
                        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getContext(), R.layout.spinner_colonia, list3);
                        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), R.layout.sp_colonia, list);*/
                        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        colonia3.setAdapter(adapter3);

                        colonia3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapter3, View view,
                                                       int position3, long id3) {
                                String slect3 = colonia3.getSelectedItem().toString();
                                select3.setText(slect3);
                            }

                            public void onNothingSelected(AdapterView<?> arg0) {
                            }
                        });
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
        return view;
    }



    private void getDataIntereses() {
        codigop.setError(null);
        codigop2.setError(null);
        codigop3.setError(null);

        List<Phone> list1 = Phone.listAll(Phone.class);
        for (Phone pho : list1) {

            phones = pho.getPhone();

        }
        Call<List<DatosIntereses>> callVersiones = BovedaClient.getInstanceClient().getApiClient().getItereses(phones);
        callVersiones.enqueue(new Callback<List<DatosIntereses>>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<List<DatosIntereses>> call, Response<List<DatosIntereses>> response) {

                List<DatosIntereses> respuestas = response.body();
                List<String> list = new ArrayList<String>();


                for (DatosIntereses res : respuestas) {
                    if (res.getCodigo() == null) {
                        //guardarMetodo();
                    }else {

                        list.add(res.getCodigo());

                        for(int i = 0; i<list.size(); i++){
                            if(i==0){
                                codigop.setText(list.get(i));
                            }else if(i==1){
                                codigop2.setText(list.get(i));
                            } else if(i==2){
                                codigop3.setText(list.get(i));
                            }
                        }

                        //String cp = "" + res.getCodigo();
                        //codigop.setText(cp);
                        codigop.setEnabled(false);

                        String sl = "" + res.getColonia();
                        select.setText(sl);
                        colonia.setEnabled(false);


                        estado.setEnabled(false);

                        guardar.setText("modificar.");
                        guardar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });


                    }

                }
            }
            @Override
            public void onFailure(Call<List<DatosIntereses>> call, Throwable t) {
                L.error("getDataSchool " + t.getMessage());
            }
        });
    }
}

