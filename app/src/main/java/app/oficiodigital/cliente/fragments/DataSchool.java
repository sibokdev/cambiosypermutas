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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.InputType;
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
import app.oficiodigital.cliente.models.ModelsDB.Phone;
import app.oficiodigital.cliente.models.Request.DatosSchool;
import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.utils.L;
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
    private TextView salida, phone, viewnivesco;
    private Spinner onivel_esc, oturno, ocategoria, otipo_plantel, spinombramiento, onota, oprocedimiento, ocolonia;
    private SeekBar seekBar;
    private Button  guardar, modificar;
    private int datos;
    ArrayAdapter<String> adapter, adapter_tn, adapter_ct, adapter_tp, adapter_nombra, adapter_nota, adapter_proce, adapter_cln;


    String phones = "", id = "";

    FragmentInteres fragment_interes;
    ViewDtSchool viewDtSchool;



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
       /* viewnivesco =(TextView) view.findViewById(R.id.tv_nivesco);*/

        oturno = (Spinner) view.findViewById(R.id.sp_turno);
        ocategoria = (Spinner) view.findViewById(R.id.sp_categoria);
        otipo_plantel = (Spinner) view.findViewById(R.id.sp_plantel);
        ocolonia = (Spinner) view.findViewById(R.id.sp_colonia);

        spinombramiento = (Spinner) view.findViewById(R.id.sp_nombramiento);
        onota = (Spinner) view.findViewById(R.id.sp_not_desf);
        oprocedimiento = (Spinner) view.findViewById(R.id.sp_proc_Adm);


        seekBar = (SeekBar) view.findViewById(R.id.seekBar_anios);


        guardar = (Button) view.findViewById(R.id.guardar);
        modificar = (Button) view.findViewById(R.id.modificar);
        getDataSchool();

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.conten,fragment_interes).addToBackStack(null).commit();

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



                //Estos métodos se ejecutará cuando se presione el botón
                String seleccion = onivel_esc.getSelectedItem().toString();
               /* Log.d("Here-----", "Seleccion-----------------::: " + seleccion);*/
               if (seleccion.equals("Preescolar")) {
                } else if (seleccion.equals("Primaria")) {
                }else if (seleccion.equals("Secundaria")) {
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

                  String id = "1218";

                Call<Responses> call = BovedaClient.getInstanceClient().getApiClient().registroEscuela(params,id);
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

                        adapter_cln = new ArrayAdapter<String>(getActivity(), R.layout.spinner_colonia, list);
                        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), R.layout.sp_colonia, list);*/
                        adapter_cln.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        ocolonia.setAdapter(adapter_cln);

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
                    guardar.setEnabled(false);
                } else if (progress > 2) {
                    Toast.makeText(getContext(), "Eres candidato a cambio", Toast.LENGTH_SHORT).show();
                    guardar.setEnabled(true);
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

        /*Integer[] opciones_labor = {0,1,2,3,4,5,6,7,8,9};*/

        String[] opciones_nombra = {"Si", "No"};
        String[] opciones_nota = {"Si", "No"};
        String[] opciones_proced = {"Si", "No"};


        // NUeva clase comunicacion para spinner - layout
         adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones_ne);
        onivel_esc.setAdapter(adapter);
        onivel_esc.setPrompt("Selecciona una opción");

        adapter_tn = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones_tn);
        oturno.setAdapter(adapter_tn);

        adapter_ct = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones_cat);
        ocategoria.setAdapter(adapter_ct);

        adapter_tp = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones_tp);
        otipo_plantel.setAdapter(adapter_tp);


        /*seekBar = new ArrayAdapter<Integer>(getActivity(), android.R.layout., opciones_labor);*/
        /*seekBar = new SeekBarAdapter(this, R.layout., opciones_labor);*/


        adapter_nombra = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones_nombra);
        /*adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/
        spinombramiento.setAdapter(adapter_nombra);

        adapter_nota = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones_nota);
        onota.setAdapter(adapter_nota);

        adapter_proce = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones_proced);
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
                            guardar.setEnabled(false);
                        } else {
                            Toast.makeText(getContext(), "Eres candidato a cambio", Toast.LENGTH_SHORT).show();
                            guardar.setEnabled(true);
                        }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nada fue seleccionado. Por cierto, no he visto que este método se desencadene
            }
        });

        return view;
    }

    private void getDataSchool() {
        List<Phone> list1 = Phone.listAll(Phone.class);
        for (Phone pho : list1) {


            phones = pho.getPhone();

        }
        Call<List<DatosSchool>> callVersiones = BovedaClient.getInstanceClient().getApiClient().getDataSchool(phones);
        callVersiones.enqueue(new Callback<List<DatosSchool>>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<List<DatosSchool>> call, Response<List<DatosSchool>> response) {

                if (!response.isSuccessful()) {
                    return;
                }

                List<DatosSchool> respuestas = response.body();
                List<String> list = new ArrayList<String>();

                for (DatosSchool res : respuestas) {

                    String nombre_esc = ""+ res.getNombre_esc();
                    oescuela.setText(nombre_esc);
                   /* oescuela.setEnabled(false);*/
                    oescuela.setInputType(InputType.TYPE_NULL);

                    String claveesc = ""+ res.getClave_esc();
                    oclave.setText(claveesc);
                    oclave.setInputType(InputType.TYPE_NULL);

                    String nivel_escolar = res.getNivel_escolar();
                    int spinnerPosition = adapter.getPosition(nivel_escolar);
                    onivel_esc.setSelection(spinnerPosition);
                    onivel_esc.setEnabled(false);

                    String turno = ""+ res.getTurno();
                    int sp_turno = adapter_tn.getPosition(turno);
                    oturno.setSelection(sp_turno);
                    oturno.setEnabled(false);

                    String zona = ""+ res.getZon_esc();
                    ozona.setText(claveesc);
                    ozona.setInputType(InputType.TYPE_NULL);

                    String phone = ""+ res.getTelefono();
                    otel.setText(phone);
                    otel.setInputType(InputType.TYPE_NULL);

                    String cp = ""+ res.getC_postal();
                    codigop.setText(cp);
                    codigop.setInputType(InputType.TYPE_NULL);

                    String sl = ""+ res.getColonia();
                    select.setText(sl);
                    ocolonia.setEnabled(false);

                    String municipio = ""+ res.getMunicipio();
                    muni.setText(municipio);
                    muni.setInputType(InputType.TYPE_NULL);

                    String estad = ""+ res.getEstado();
                    estado.setText(estad);
                    estado.setInputType(InputType.TYPE_NULL);

                    String nomdir = ""+ res.getNombre_direc();
                    onom_dir.setText(nomdir);
                    onom_dir.setInputType(InputType.TYPE_NULL);

                    String catego = ""+ res.getCategoria();
                    int sp_catego = adapter_ct.getPosition(catego);
                    ocategoria.setSelection(sp_catego);
                    ocategoria.setEnabled(false);

                    String tp_plantel = ""+ res.getTipo_plantel();
                    int sp_tp_plantel = adapter_tp.getPosition(tp_plantel);
                    otipo_plantel.setSelection(sp_tp_plantel);
                    otipo_plantel.setEnabled(false);

                    String nombramiento= ""+ res.getNombramiento();
                    int sp_nombramiento = adapter_nombra.getPosition(nombramiento);
                    spinombramiento.setSelection(sp_nombramiento);
                    spinombramiento.setEnabled(false);

                    //-----------------------------------------------------------------------

                    int sb_labor = seekBar.getProgress(pro);
                    seekBar.setProgress(sb_labor);
                    seekBar.setEnabled(false);

                    String lbsl = ""+ res.getLabor();
                    salida.setText(lbsl);
                    salida.setInputType(InputType.TYPE_NULL);


                    String nota= ""+ res.getNota();
                    int sp_nota = adapter_nota.getPosition(nota);
                    onota.setSelection(sp_nota);
                    onota.setEnabled(false);

                    String procedimiento= ""+ res.getNota();
                    int sp_proced = adapter_proce.getPosition(procedimiento);
                    oprocedimiento.setSelection(sp_proced);
                    oprocedimiento.setEnabled(false);



                }
            }
            @Override
            public void onFailure(Call<List<DatosSchool>> call, Throwable t) {
                L.error("getDataSchool " + t.getMessage());
            }
        });

    }



}
