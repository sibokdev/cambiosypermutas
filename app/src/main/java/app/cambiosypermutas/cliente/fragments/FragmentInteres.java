package app.cambiosypermutas.cliente.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import app.cambiosypermutas.cliente.R;
import app.cambiosypermutas.cliente.clients.BovedaClient;
import app.cambiosypermutas.cliente.models.Busqueda;
import app.cambiosypermutas.cliente.models.Ejemplo;
import app.cambiosypermutas.cliente.models.ModelsDB.Phone;
import app.cambiosypermutas.cliente.models.Request.DatosIntereses;
import app.cambiosypermutas.cliente.models.Request.Intereses;
import app.cambiosypermutas.cliente.models.Responses;
import app.cambiosypermutas.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentInteres extends Fragment {
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
    String c1,c2,c3;

    FragmentInteres fragment_interes;
    DataSchool dataSchool;
    ViewDtSchool viewDtSchool;
    String busqueda;
    private AdView mAdView;


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

        cp1 = (TextInputLayout) view.findViewById(R.id.ti_codigop);
        ti_cp2 = (TextInputLayout) view.findViewById(R.id.ti_codigop2);
        ti_cp3 = (TextInputLayout) view.findViewById(R.id.ti_codigop3);

        colonia = (Spinner) view.findViewById(R.id.sp_colonia);
        colonia2 = (Spinner) view.findViewById(R.id.sp_colonia2);
        colonia3 = (Spinner) view.findViewById(R.id.sp_colonia3);

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

        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


      /*  codigop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                validateEditTextcp1(editable);
            }
        });
        codigop.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateEditTextcp1(((EditText) v).getText());
                }
            }
        });

        codigop2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                validateEditTextcp2(editable);
            }
        });
        codigop2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateEditTextcp2(((EditText) v).getText());
                }
            }
        });

        codigop3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                validateEditTextcp3(editable);
            }
        });
        codigop3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateEditTextcp3(((EditText) v).getText());
                }
            }
        });*/


        getDataIntereses();


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

                        try{
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
                EditText input = cp1.getEditText();
                Pattern cod = Pattern.compile("^[0-9]{4,5}?$");
                String inputText = input.getText().toString().trim();
                if (cod.matcher(inputText).matches() == false) {
                    cp1.setError(" ");
                } else {
                    cp1.setErrorEnabled(false);

                }
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

                        try {
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
                EditText input = ti_cp2.getEditText();
                Pattern cod = Pattern.compile("^[0-9]{4,5}?$");
                String inputText = input.getText().toString().trim();
                if (cod.matcher(inputText).matches() == false) {
                    ti_cp2.setError(" ");
                } else {
                    ti_cp2.setErrorEnabled(false);

                }
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

                        try{
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
                EditText input = ti_cp3.getEditText();
                Pattern cod = Pattern.compile("^[0-9]{4,5}?$");
                String inputText = input.getText().toString().trim();
                if (cod.matcher(inputText).matches() == false) {
                    ti_cp3.setError(" ");
                } else {
                    ti_cp3.setErrorEnabled(false);

                }
            }
        });



        return view;
    }


    /*private void validateEditTextcp3(Editable editable) {
        if (!TextUtils.isEmpty(editable)) {
            ti_cp3.setError(null);
        } else {
            ti_cp3.setError(" ");
        }
    }

    private void validateEditTextcp2(Editable editable) {
        if (!TextUtils.isEmpty(editable)) {
            ti_cp2.setError(null);
        } else {
            ti_cp2.setError(" ");
        }
    }

    private void validateEditTextcp1(Editable editable) {
        if (!TextUtils.isEmpty(editable)) {
            cp1.setError(null);
        } else {
            cp1.setError(" ");
        }
    }*/

    public void guardarMetodo(){
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // FragmentTransaction transaction = getFragmentManager().beginTransaction();
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.conten, new DataSchool()).addToBackStack(null).commit();

                codigop.setError(null);

                String cp = codigop.getText().toString();
                String estd = estado.getText().toString().trim();
                String mun = muni.getText().toString();
                String colo = select.getText().toString();

                String cp2 = codigop2.getText().toString();
                String estd2 = estado2.getText().toString().trim();
                String mun2 = muni2.getText().toString();
                String colo2 = select2.getText().toString();

                String cp3 = codigop3.getText().toString();
                String estd3 = estado3.getText().toString().trim();
                String mun3 = muni3.getText().toString();
                String colo3 = select3.getText().toString();

                Pattern cod = Pattern.compile("^[0-9]{4,5}?$");

                //tring guard = guardar


                if (TextUtils.isEmpty(cp)) {
                    cp1.setError(" ");
                    codigop.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(cp2)) {
                    ti_cp2.setError(" ");
                    codigop2.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(cp3)) {
                    ti_cp3.setError(" ");
                    codigop3.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(mun)){
                    cp1.setError(" ");
                    codigop.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(mun2)){
                    ti_cp2.setError(" ");
                    codigop2.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(mun3)){
                    ti_cp3.setError(" ");
                    codigop3.requestFocus();
                    return;
                }


                    if (cod.matcher(codigop.getText().toString()).matches() == false) {
                        cp1.setError(" ");
                        codigop.requestFocus();
                    } else if (cod.matcher(codigop2.getText().toString()).matches() == false) {
                        ti_cp2.setError(" ");
                        codigop2.requestFocus();
                    } else if (cod.matcher(codigop2.getText().toString()).matches() == false) {
                        ti_cp3.setError(" ");
                        codigop3.requestFocus();
                    } else {
                        Toast.makeText(getContext(), "Se ha guardado correctamente la información", Toast.LENGTH_SHORT).show();

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

                        getDataIntereses();
                    }
                }

        });

    }
    private void getDataIntereses() {

        codigop.setError(null);
        codigop2.setError(null);
        codigop3.setError(null);


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
                        Call<List<DatosIntereses>> callVersiones1 = BovedaClient.getInstanceClient().getApiClient().getItereses(phones);
                        callVersiones1.enqueue(new Callback<List<DatosIntereses>>() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onResponse(Call<List<DatosIntereses>> call, Response<List<DatosIntereses>> response) {
                                List<DatosIntereses> ejemplo = response.body();

                        List<String> list = new ArrayList<String>();
                        List<String> list2 = new ArrayList<String>();
                        for (DatosIntereses eje : ejemplo) {

                            list.add(eje.getCodigo());
                        list2.add(eje.getId());


                        for (int i = 0; i < list.size(); i++) {
                            if (i == 0) {
                                codigop.setText(list.get(i));
                                colonia.setEnabled(false);
                                codigop.setEnabled(false);
                                id = list2.get(i);
                            } else if (i == 1) {
                                codigop2.setText(list.get(i));
                                colonia2.setEnabled(false);
                                codigop2.setEnabled(false);
                                id2 = list2.get(i);
                            } else if (i == 2) {
                                codigop3.setText(list.get(i));
                                colonia3.setEnabled(false);
                                codigop3.setEnabled(false);
                                id3 = list2.get(i);
                            }

                        }

                        progressBar2.setVisibility(View.GONE);
                        guardar.setText("modificar");
                        guardar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                modificarMetodo();
                               /* Toast.makeText(getContext(),"Datos actualizados" +
                                        "correctamente",Toast.LENGTH_SHORT).show();*/
                            }
                        });

                        }
                            }

                            @Override
                            public void onFailure(Call<List<DatosIntereses>> call, Throwable throwable) {

                            }
                        });

                    }else if(response.body().getCode() == 202){
                        progressBar2.setVisibility(View.GONE);
                        guardarMetodo();
                    }

            }
            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                L.error("getDataIntereses " + t.getMessage());
            }
        });
    }

    private void modificar(){
        List<Phone> userId = Phone.listAll(Phone.class);
        for (Phone phon : userId) {


            phone = phon.getPhone();
        }
        Pattern cod = Pattern.compile("^[0-9]{4,5}?$");
       // FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.conten, new FragmentInteres()).addToBackStack(null).commit();
        codigop.setError(null);

        if(cod.matcher(codigop.getText().toString()).matches() == false){
            cp1.setError(" ");
            codigop.requestFocus();
        }else if(cod.matcher(codigop2.getText().toString()).matches() == false) {
            ti_cp2.setError(" ");
            codigop2.requestFocus();
        }else if(cod.matcher(codigop2.getText().toString()).matches() == false){
            ti_cp3.setError(" ");
            codigop3.requestFocus();
        }else {

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

            HashMap<String, String> params = new HashMap<>();

            List<Intereses> intereses = new ArrayList<Intereses>();
            Intereses codigos = new Intereses();
            codigos.setId(id);
            codigos.setCodigo(cp);
            codigos.setColonia(colo);
            codigos.setMunicipio(mun);
            codigos.setEstado(estd);
            codigos.setTelefono(phone);

            intereses.add(codigos);
            Intereses codigos1 = new Intereses();
            codigos1.setId(id2);
            codigos1.setCodigo(cp2);
            codigos1.setColonia(colo2);
            codigos1.setMunicipio(mun2);
            codigos1.setEstado(estd2);
            codigos1.setTelefono(phone);

            intereses.add(codigos1);
            Intereses codigos2 = new Intereses();
            codigos2.setId(id3);
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
                    jGroup.put("id", intereses.get(i).getId());
                    jGroup.put("codigo", intereses.get(i).getCodigo());
                    jGroup.put("colonia", intereses.get(i).getColonia());
                    jGroup.put("municipio", intereses.get(i).getMunicipio());
                    jGroup.put("estado", intereses.get(i).getEstado().trim());
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


            Call<Responses> callVersiones = BovedaClient.getInstanceClient().getApiClient().updateInte(params, phone);
            callVersiones.enqueue(new Callback<Responses>() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onResponse(Call<Responses> call, Response<Responses> response) {

                }

                @Override
                public void onFailure(Call<Responses> call, Throwable t) {

                }

            });
            Toast.makeText(getContext(), "Datos actualizados " +
                    "correctamente", Toast.LENGTH_SHORT).show();
            getDataIntereses();
        }
    }

    private void modificarMetodo(){
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
                List<String> list2 = new ArrayList<String>();


                for (DatosIntereses res : respuestas) {
                    if (res.getCodigo() == null) {
                        guardarMetodo();
                    }else {

                        list.add(res.getCodigo());
                        list2.add(res.getId());

                        for(int i = 0; i<list.size(); i++){
                            if(i==0){
                                codigop.setText(list.get(i));
                                codigop.setEnabled(true);
                                colonia.setEnabled(true);
                                id = list2.get(i);
                            }else if(i==1){
                                codigop2.setText(list.get(i));
                                codigop2.setEnabled(true);
                                colonia2.setEnabled(true);
                                id2 = list2.get(i);
                            } else if(i==2){
                                codigop3.setText(list.get(i));
                                codigop3.setEnabled(true);
                                colonia3.setEnabled(true);
                                id3 = list2.get(i);
                            }
                        }


                        guardar.setText("guardar");
                        guardar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                modificar();
                            }
                        });


                    }

                }
            }
            @Override
            public void onFailure(Call<List<DatosIntereses>> call, Throwable t) {
                L.error("getDataIntereses " + t.getMessage());
            }
        });

    }


}

