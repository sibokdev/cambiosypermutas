package app.oficiodigital.cliente.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.activities.Intereses;
import app.oficiodigital.cliente.activities.ViewDSchool;
import app.oficiodigital.cliente.clients.BovedaClient;
import app.oficiodigital.cliente.models.Ejemplo;
import app.oficiodigital.cliente.models.Responses;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataSchool extends Fragment {
    DrawerLayout drawerLayout;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private TextView muni, estado, select;
    private MapView mapa;
    private EditText codigop;
    private ImageView imagen;
    private TextView nombre, email, nombramiento, laborando;
    private EditText oescuela, oclave, ozona, otel, onom_dir;
    private TextView salida, phone;
    private Spinner onivel_esc, oturno, ocategoria, otipo_plantel, spinombramiento, onota, oprocedimiento, colonia;
    private SeekBar seekBar;
    private Button lugares;
    private int datos;

    FragmentInteres fragment_interes;

    public DataSchool() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data_school, container, false);

        fragment_interes = new FragmentInteres();

        //primerPregunta();
        //asociamos lode arriba con esto
        //casteo
        codigop = (EditText) view.findViewById(R.id.et_codigop);

        estado = (TextView) view.findViewById(R.id.estado);
        muni = (TextView) view.findViewById(R.id.municipio);

        select = (TextView) view.findViewById(R.id.select);
        mapa = (MapView) view.findViewById(R.id.mapa);

        /*mapa.onCreate(savedInstanceState);*/

        oescuela = (EditText) view.findViewById(R.id.et_nombre_escuela);
        /*final EditText oescuela=(EditText) view.findViewById(R.id.et_nombre_escuela); */

        oclave = (EditText) view.findViewById(R.id.et_clave);
        ozona = (EditText) view.findViewById(R.id.et_zona);
        otel = (EditText) view.findViewById(R.id.et_tel);

        nombramiento = (TextView) view.findViewById(R.id.tv_nombra);
        salida = (TextView) view.findViewById(R.id.tv_salida);
        muni = (TextView) view.findViewById(R.id.tv_municipio);
        estado = (TextView) view.findViewById(R.id.tv_estado);

        onom_dir = (EditText) view.findViewById(R.id.et_nom_dir);

        //definimos el spinner
        onivel_esc = (Spinner) view.findViewById(R.id.sp_nivel_esc);
        oturno = (Spinner) view.findViewById(R.id.sp_turno);
        ocategoria = (Spinner) view.findViewById(R.id.sp_categoria);
        otipo_plantel = (Spinner) view.findViewById(R.id.sp_plantel);
        colonia = (Spinner) view.findViewById(R.id.sp_colonia);

        spinombramiento = (Spinner) view.findViewById(R.id.sp_nombramiento);
        onota = (Spinner) view.findViewById(R.id.sp_not_desf);
        oprocedimiento = (Spinner) view.findViewById(R.id.sp_proc_Adm);


        seekBar = (SeekBar) view.findViewById(R.id.seekBar_anios);

        lugares = (Button) view.findViewById(R.id.sig_lugares_intereses);


        lugares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Toast.makeText(getContext(),"Iniciar juego desde el fragment", Toast.LENGTH_SHORT).show();*/
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.conten,fragment_interes).addToBackStack(null).commit();
                /*switch (view.getId()){
                    case R.id.sig_lugares_intereses:
                        transaction.replace(R.id.conten, fragment_interes);*/

                /*FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction= fragmentManager.beginTransaction();
                *//*FragmentTransaction.add(R.id.conten, new app.oficiodigital.cliente.fragments.DataSchool());*//*
                fragmentTransaction.add(R.id.conten, new FragmentInteres());*/

                oescuela.setError(null);
                oclave.setError(null);
                ozona.setError(null);
                otel.setError(null);
                codigop.setError(null);
                onom_dir.setError(null);
                salida.setError(null);


                String esc = oescuela.getText().toString();
                String clv = oclave.getText().toString();
                String zon = ozona.getText().toString();
                String tl = otel.getText().toString();
                String cp = codigop.getText().toString();
                String nom_direc = onom_dir.getText().toString();
                String estd = estado.getText().toString();
                String mun = muni.getText().toString();
                String colo = select.getText().toString();
                String sal = salida.getText().toString();


                //guardar la seleccion del usuario del spinner de nivel escolar
                String seleccion = onivel_esc.getSelectedItem().toString();
                Log.d("Here-----", "Seleccion-----------------::: " + seleccion);
                if (seleccion.equals("Preescolar")) {
                    /*int suma = valor1_int + valor2_int;
                    String resultado = String.valueOf(suma);
                    tv1.setText(resultado);*/
                } else if (seleccion.equals("Primaria")) {
                } else if (seleccion.equals("Secundaria")) {
                }

                //guardado de seleccion spinnner turno
                String seleccion_tn = oturno.getSelectedItem().toString();
                if (seleccion_tn.equals("Matutino")) {
                } else if (seleccion_tn.equals("Vespertino")) {
                }

                //guardado de seleccion spinnner rol
                String seleccion_ct = ocategoria.getSelectedItem().toString();
                if (seleccion_ct.equals("Docente")) {
                } else if (seleccion_ct.equals("Subdirector")) {
                } else if (seleccion_ct.equals("Director")) {
                }

                //Sleccion spinnner tipo plantel
                String seleccion_tp = otipo_plantel.getSelectedItem().toString();
                if (seleccion_tp.equals("Municipal")) {
                } else if (seleccion_tp.equals("Estatal")) {
                } else if (seleccion_tp.equals("Federal")) {
                } else if (seleccion_tp.equals("Federalizado")) {
                }

                //Sleccion spinnner nombramiento
                String seleccion_nombram = spinombramiento.getSelectedItem().toString();
                if (seleccion_nombram.equals("No")) {
                    //Toast.makeText(DataSchool.this,"No puede aplicar", Toast.LENGTH_SHORT).show();
                    //   ((TextView)spinombramiento.getSelectedView()).setError("Error message");
                    // lugares.setEnabled(false);
                } else if (seleccion_nombram.equals("Si")) {
                    //Toast.makeText(DataSchool.this,"Eres candidato a cambio", Toast.LENGTH_SHORT).show();
                    // lugares.setEnabled(true);
                }
        /*int seleccion_nombram = spinombramiento.getSelectedItemPosition();
        if(seleccion_nombram==1){
            System.out.println("Si");
        }else if(seleccion_nombram==2){
            System.out.println("No");
        }*/

                //Sleccion spinnner nota
                String seleccion_nota = onota.getSelectedItem().toString();
                if (seleccion_nota.equals("Si")) {
                } else if (seleccion_nota.equals("No")) {
                }

                //Sleccion spinnner procedimiento
                String seleccion_proc = oprocedimiento.getSelectedItem().toString();
                if (seleccion_proc.equals("Si")) {
                } else if (seleccion_proc.equals("No")) {
                }


                //Validaciones campos
                if (TextUtils.isEmpty(esc)) {
                    oescuela.setError(getString(R.string.error_campo_oblogatorio));
                    oescuela.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(clv)) {
                    oclave.setError(getString(R.string.error_campo_oblogatorio));
                    oclave.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(zon)) {
                    ozona.setError(getString(R.string.error_campo_oblogatorio));
                    ozona.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(tl)) {
                    otel.setError(getString(R.string.error_campo_oblogatorio));
                    otel.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(cp)) {
                    codigop.setError(getString(R.string.error_campo_oblogatorio));
                    codigop.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(nom_direc)) {
                    onom_dir.setError(getString(R.string.error_campo_oblogatorio));
                    onom_dir.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(sal)) {
                    salida.setError(getString(R.string.error_campo_oblogatorio));
                    salida.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(seleccion_nombram)) {
                    ((TextView) spinombramiento.getSelectedView()).setError("Debes contar con nombramiento");
                    spinombramiento.requestFocus();
                    return;
                }


                Toast.makeText(getContext(), "Se han validado y guardado correctamente los datos", Toast.LENGTH_SHORT).show();


                //Envio a BD
                HashMap<String, String> params = new HashMap<>();
                params.put("nombre_esc", esc);
                params.put("clave_esc", clv);
                params.put("nivel_escolar", seleccion);
                params.put("turno", seleccion_tn);
                params.put("zona_esc", zon);
                params.put("telefono", tl);
                params.put("c_postal", cp);
                params.put("estado", estd);
                params.put("municipio", mun);
                params.put("colonia", colo);
                params.put("nombre_direc", nom_direc);
                params.put("rol", seleccion_ct);
                params.put("tipo_plantel", seleccion_tp);

                params.put("nombramiento", seleccion_nombram);
                params.put("labor", sal);
                params.put("nota", seleccion_nota);
                params.put("procedimiento", seleccion_proc);

                Call<Responses> call = BovedaClient.getInstanceClient().getApiClient().registroEscuela(params);
                call.enqueue(new Callback<Responses>() {

                    @Override
                    public void onResponse(Call<Responses> call, Response<Responses> response) {
                    }

                    @Override
                    public void onFailure(Call<Responses> call, Throwable t) {

                    }
                });

       /* Intent intent = new Intent(getApplicationContext(),Intereses.class);
        startActivity(intent);*/
        /*intent.putExtra("nombre",oescuela.getText().toString());
        startActivity(intent);*/
               /* startActivity(new Intent(getActivity(), Intereses.class));*/

            }
        });

        /*Bundle parametros = this.getIntent().getExtras();
        String datos = parametros.getString("datos");
        oescuela.setText(datos)*/
        ;
       /* Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            String datos = parametros.getString("datos");
            oescuela.setText(datos);
        }*/

//        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_colonia, list);
                        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), R.layout.sp_colonia, list);*/
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        colonia.setAdapter(adapter);

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

//Condicional seekbar antiguedad
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                salida.setText((progress + " Años"));

                if (progress <= 2) {
                    Toast.makeText(getContext(), "No puede aplicar", Toast.LENGTH_SHORT).show();
                    lugares.setEnabled(false);
                } else if (progress > 2) {
                    Toast.makeText(getContext(), "Eres candidato a cambio", Toast.LENGTH_SHORT).show();
                    lugares.setEnabled(true);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        //Arreglo, obtencion de valores de spinners
        String[] opciones_ne = {"Preescolar", "Primaria", "Secundaria"};//Arreglo spinner nivel escolar
        String[] opciones_tn = {"Matutino", "Vespertino"};//arreglo turno
        String[] opciones_cat = {"Docente", "Subdirector", "Director"};//arreglo rol
        String[] opciones_tp = {"Municipal", "Estatal", "Federal", "Federalizado"};//arreglo tipoplantel
        String[] opciones_nombra = {"Si", "No"};
        String[] opciones_nota = {"Si", "No"};
        String[] opciones_proced = {"Si", "No"};

        // NUeva clase comunicacion para spinner - layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones_ne);
        onivel_esc.setAdapter(adapter);

        ArrayAdapter<String> adapter_tn = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones_tn);
        oturno.setAdapter(adapter_tn);

        ArrayAdapter<String> adapter_ct = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones_cat);
        ocategoria.setAdapter(adapter_ct);

        ArrayAdapter<String> adapter_tp = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones_tp);
        otipo_plantel.setAdapter(adapter_tp);

        ArrayAdapter<String> adapter_nombra = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones_nombra);
        /*adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/
        spinombramiento.setAdapter(adapter_nombra);


      /*  int selectionPosition= adapter_nombra.getPosition("No");
        spinombramiento.setSelection(selectionPosition);
        spinombramiento.setAdapter(adapter_nombra);
        if (adapter_nombra != null) {
            int spinnerPosition = adapter_nombra.getPosition(adapter_nombra);
            spinombramiento.setSelection(spinnerPosition);
        }*/

        ArrayAdapter<String> adapter_nota = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones_nota);
        onota.setAdapter(adapter_nota);

        ArrayAdapter<String> adapter_proce = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones_proced);
        oprocedimiento.setAdapter(adapter_proce);






        /*Spinner sp = findViewById(R.id.spinnerCategoriasReporteGastos);*/
        spinombramiento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getId()) {
                    case R.id.sp_nombramiento:
                        int seleccionado = spinombramiento.getSelectedItemPosition();
                        if (seleccionado == 1) {
                            Toast.makeText(getContext(), "No puede aplicar", Toast.LENGTH_SHORT).show();
                            lugares.setEnabled(false);
                        } else {
                            Toast.makeText(getContext(), "Eres candidato a cambio", Toast.LENGTH_SHORT).show();
                            lugares.setEnabled(true);
                        }
                }
                /* if (spinombramiento.equals ("No")){
                 *//*Toast.makeText(DataSchool.this,"No puede aplicar", Toast.LENGTH_SHORT).show();*//*
                    ((TextView)spinombramiento.getSelectedView()).setError("Error message");
                    lugares.setEnabled(false);
                }else if (spinombramiento.equals ("Si")){
                    Toast.makeText(DataSchool.this,"Eres candidato a cambio", Toast.LENGTH_SHORT).show();
                    lugares.setEnabled(true);
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nada fue seleccionado. Por cierto, no he visto que este método se desencadene
            }
        });
        return view;
    }


   /* public void sig_lugares_intereses(View view) {
        oescuela.setError(null);
        oclave.setError(null);
        ozona.setError(null);
        otel.setError(null);
        codigop.setError(null);
        onom_dir.setError(null);
        salida.setError(null);


        String esc = oescuela.getText().toString();
        String clv = oclave.getText().toString();
        String zon = ozona.getText().toString();
        String tl = otel.getText().toString();
        String cp = codigop.getText().toString();
        String nom_direc = onom_dir.getText().toString();
        String estd = estado.getText().toString();
        String mun = muni.getText().toString();
        String colo = select.getText().toString();
        String sal = salida.getText().toString();


        //guardar la seleccion del usuario del spinner de nivel escolar
        String seleccion = onivel_esc.getSelectedItem().toString();
        Log.d("Here-----", "Seleccion-----------------::: " + seleccion);
        if (seleccion.equals("Preescolar")) {
                    *//*int suma = valor1_int + valor2_int;
                    String resultado = String.valueOf(suma);
                    tv1.setText(resultado);*//*
        } else if (seleccion.equals("Primaria")) {
        } else if (seleccion.equals("Secundaria")) {
        }

        //guardado de seleccion spinnner turno
        String seleccion_tn = oturno.getSelectedItem().toString();
        if (seleccion_tn.equals("Matutino")) {
        } else if (seleccion_tn.equals("Vespertino")) {
        }

        //guardado de seleccion spinnner rol
        String seleccion_ct = ocategoria.getSelectedItem().toString();
        if (seleccion_ct.equals("Docente")) {
        } else if (seleccion_ct.equals("Subdirector")) {
        } else if (seleccion_ct.equals("Director")) {
        }

        //Sleccion spinnner tipo plantel
        String seleccion_tp = otipo_plantel.getSelectedItem().toString();
        if (seleccion_tp.equals("Municipal")) {
        } else if (seleccion_tp.equals("Estatal")) {
        } else if (seleccion_tp.equals("Federal")) {
        } else if (seleccion_tp.equals("Federalizado")) {
        }

        //Sleccion spinnner nombramiento
        String seleccion_nombram = spinombramiento.getSelectedItem().toString();
        if (seleccion_nombram.equals("No")) {
            //Toast.makeText(DataSchool.this,"No puede aplicar", Toast.LENGTH_SHORT).show();
            //   ((TextView)spinombramiento.getSelectedView()).setError("Error message");
            // lugares.setEnabled(false);
        } else if (seleccion_nombram.equals("Si")) {
            //Toast.makeText(DataSchool.this,"Eres candidato a cambio", Toast.LENGTH_SHORT).show();
            // lugares.setEnabled(true);
        }
        *//*int seleccion_nombram = spinombramiento.getSelectedItemPosition();
        if(seleccion_nombram==1){
            System.out.println("Si");
        }else if(seleccion_nombram==2){
            System.out.println("No");
        }*//*

        //Sleccion spinnner nota
        String seleccion_nota = onota.getSelectedItem().toString();
        if (seleccion_nota.equals("Si")) {
        } else if (seleccion_nota.equals("No")) {
        }

        //Sleccion spinnner procedimiento
        String seleccion_proc = oprocedimiento.getSelectedItem().toString();
        if (seleccion_proc.equals("Si")) {
        } else if (seleccion_proc.equals("No")) {
        }


        //Validaciones campos
        if (TextUtils.isEmpty(esc)) {
            oescuela.setError(getString(R.string.error_campo_oblogatorio));
            oescuela.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(clv)) {
            oclave.setError(getString(R.string.error_campo_oblogatorio));
            oclave.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(zon)) {
            ozona.setError(getString(R.string.error_campo_oblogatorio));
            ozona.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(tl)) {
            otel.setError(getString(R.string.error_campo_oblogatorio));
            otel.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(cp)) {
            codigop.setError(getString(R.string.error_campo_oblogatorio));
            codigop.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(nom_direc)) {
            onom_dir.setError(getString(R.string.error_campo_oblogatorio));
            onom_dir.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(sal)) {
            salida.setError(getString(R.string.error_campo_oblogatorio));
            salida.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(seleccion_nombram)) {
            ((TextView) spinombramiento.getSelectedView()).setError("Debes contar con nombramiento");
            spinombramiento.requestFocus();
            return;
        }


        Toast.makeText(getContext(), "Se han validado y guardado correctamente los datos", Toast.LENGTH_SHORT).show();


        //Envio a BD
        HashMap<String, String> params = new HashMap<>();
        params.put("nombre_esc", esc);
        params.put("clave_esc", clv);
        params.put("nivel_escolar", seleccion);
        params.put("turno", seleccion_tn);
        params.put("zona_esc", zon);
        params.put("telefono", tl);
        params.put("c_postal", cp);
        params.put("estado", estd);
        params.put("municipio", mun);
        params.put("colonia", colo);
        params.put("nombre_direc", nom_direc);
        params.put("rol", seleccion_ct);
        params.put("tipo_plantel", seleccion_tp);

        params.put("nombramiento", seleccion_nombram);
        params.put("labor", sal);
        params.put("nota", seleccion_nota);
        params.put("procedimiento", seleccion_proc);

        Call<Responses> call = BovedaClient.getInstanceClient().getApiClient().registroEscuela(params);
        call.enqueue(new Callback<Responses>() {

            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {

            }
        });

       *//* Intent intent = new Intent(getApplicationContext(),Intereses.class);
        startActivity(intent);*//*
        *//*intent.putExtra("nombre",oescuela.getText().toString());
        startActivity(intent);*//*
        startActivity(new Intent(getActivity(), FragmentInteres.class));

    }*/


}
