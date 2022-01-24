package app.cambiosypermutas.cliente.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import android.widget.RadioButton;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import app.cambiosypermutas.cliente.R;
import app.cambiosypermutas.cliente.activities.AddCCPaymentActivity;
import app.cambiosypermutas.cliente.activities.PrincipalSolicitud;
import app.cambiosypermutas.cliente.activities.principalMenu;
import app.cambiosypermutas.cliente.clients.BovedaClient;
import app.cambiosypermutas.cliente.models.Ejemplo;
import app.cambiosypermutas.cliente.models.ModelsDB.Phone;
import app.cambiosypermutas.cliente.models.ModelsDB.Token;
import app.cambiosypermutas.cliente.models.Request.DatosSchool;
import app.cambiosypermutas.cliente.models.Responses;
import app.cambiosypermutas.cliente.notifications.LoadingDialog;
import app.cambiosypermutas.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataSchool extends Fragment {
    DrawerLayout drawerLayout;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private TextView muni, estado, select, tv_crr_magis;
    private MapView mapa;
    private EditText codigop;
    private ImageView imagen;
    private TextView nombre, email, nombramiento, laborando, s1, s2;
    private EditText oescuela, oclave, ozona, otel, onom_dir,mescuela, et_num_horas, et_materia;
    private TextView salida, phone, viewnivesco;
    private Spinner onivel_esc, oturno, ocategoria, otipo_plantel, spinombramiento, onota, oprocedimiento, ocolonia, ocarrera;

    private SeekBar seekBar;
    private Button  guardar;

    private ProgressBar progressBar2;
    private int datos;

    private TextInputLayout ti_nombre_escuela, ti_clave, ti_zona, ti_tel, ti_codigop, ti_nom_dir, ti_num_horas, ti_materia;

    ArrayAdapter<String> adapter, adapter_tn, adapter_ct, adapter_tp, adapter_nombra, adapter_nota, adapter_proce, adapter_cln, adapter_magisterial;

    private int aniosAntiguedad=0;
    private int nivel = 0;
    private int turno = 0;
    private int categoria = 0;
    private int tipo = 0;
    private int cont_nombramiento = 0;
    private int nota_des = 0;
    private int suj_proced = 0;

    private ImageView imagenSinConexion;
    
    private ScrollView datosescuela;
    private CardView noti_inter;

/*    String esc = "";
    String clv ="";
    String zon ="";
    String tl ="";
    String cp ="";
    String nom_direc ="";
    String estd ="";
    String mun ="";
    String colo ="";
    String sal ="";*/

    String phones = "", id = "";

    FragmentInteres fragment_interes;
    DataSchool dataSchool;
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
        mescuela = (EditText) view.findViewById(R.id.et_nombre_escuela);
        oclave = (EditText) view.findViewById(R.id.et_clave);
        ozona = (EditText) view.findViewById(R.id.et_zona);
        otel = (EditText) view.findViewById(R.id.et_tel);

        et_num_horas = (EditText) view.findViewById(R.id.et_num_horas);
        et_materia = (EditText) view.findViewById(R.id.et_materia);

        nombramiento = (TextView) view.findViewById(R.id.tv_nombra);
        salida = (TextView) view.findViewById(R.id.tv_salida);
        muni = (TextView) view.findViewById(R.id.tv_municipio);
        estado = (TextView) view.findViewById(R.id.tv_estado);
        progressBar2 = (ProgressBar) view.findViewById(R.id.progressBar2);

        onom_dir = (EditText) view.findViewById(R.id.et_nom_dir);

        //definimos el spinner
        onivel_esc = (Spinner) view.findViewById(R.id.sp_nivel_esc);

        oturno = (Spinner) view.findViewById(R.id.sp_turno);
        ocategoria = (Spinner) view.findViewById(R.id.sp_categoria);
        otipo_plantel = (Spinner) view.findViewById(R.id.sp_plantel);
        ocolonia = (Spinner) view.findViewById(R.id.sp_colonia);

        spinombramiento = (Spinner) view.findViewById(R.id.sp_nombramiento);
        onota = (Spinner) view.findViewById(R.id.sp_not_desf);
        oprocedimiento = (Spinner) view.findViewById(R.id.sp_proc_Adm);

        ocarrera = (Spinner) view.findViewById(R.id.sp_crr_magis);

        seekBar = (SeekBar) view.findViewById(R.id.seekBar_anios);


        ti_nombre_escuela = (TextInputLayout) view.findViewById(R.id.ti_nombre_escuela);
        ti_clave =(TextInputLayout) view.findViewById(R.id.ti_clave);
        ti_zona = (TextInputLayout) view.findViewById(R.id.ti_zona);
        ti_tel = (TextInputLayout) view.findViewById(R.id.ti_tel);
        ti_codigop = (TextInputLayout) view.findViewById(R.id.ti_codigop);
        ti_nom_dir = (TextInputLayout) view.findViewById(R.id.ti_nom_dir);

        s1 = (TextView) view.findViewById(R.id.select1);
        s2 = (TextView) view.findViewById(R.id.select2);

        guardar = (Button) view.findViewById(R.id.guardar);


        datosescuela = (ScrollView) view.findViewById(R.id.datosescuela);
        noti_inter = (CardView) view.findViewById(R.id.noti_inter);


        imagenSinConexion = (ImageView) view.findViewById(R.id.imagenSinConexion);
        imagenSinConexion.setVisibility(View.INVISIBLE);

        //Notificacion de no internet
        ConnectivityManager con = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = con.getActiveNetworkInfo();

        if (networkInfo!=null && networkInfo.isConnected()) {
            getDataSchool();
            datosescuela.setVisibility(View.VISIBLE);
            noti_inter.setVisibility(View.INVISIBLE);

        }else {
            noti_inter.setVisibility(View.VISIBLE);
            datosescuela.setVisibility(View.INVISIBLE);
            imagenSinConexion.setVisibility(View.VISIBLE);
            //mensaje
            Toast.makeText(getContext(), "No se ha podido establecer la conexión a internet, verifique el acceso a internet e intentelo nuevamente", Toast.LENGTH_SHORT).show();
        }
        //  guardar.setEnabled(true);
       /* getDataSchool();*/    // cambiado a arriba

        guardar.setEnabled(true);
        //Validaciones setError campos EditText
        //------------nombreescuela
        oescuela.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                validateEditText(editable);
            }
        });
        oescuela.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateEditText(((EditText) v).getText());
                }
            }
        });
        //------------claveescuela
        oclave.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                validateEditTextclaveesc(editable);
            }
        });
        oclave.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateEditTextclaveesc(((EditText) v).getText());
                }
            }
        });
        //------------zona
        ozona.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                validateEditTextzona(editable);
            }
        });
        ozona.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateEditTextzona(((EditText) v).getText());
                }
            }
        });
        //------------telesc
       /*otel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                validateEditTexttel(editable);
            }
        });
        otel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateEditTexttel(((EditText) v).getText());
                }
            }
        });*/
        //------------cp
        codigop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                validateEditTextcp(editable);
            }
        });
        codigop.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateEditTextcp(((EditText) v).getText());
                }
            }
        });
   /*     //------------nombredirec
        onom_dir.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                validateEditTextnomdir(editable);
            }
        });
        onom_dir.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateEditTextnomdir(((EditText) v).getText());
                }
            }
        });
*/


             otel.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    EditText input = ti_tel.getEditText();
                    Pattern tel = Pattern.compile("[0-9]{10}");
                    String inputText = input.getText().toString().trim();
                    if (tel.matcher(inputText).matches() == false) {
                        ti_tel.setError(" ");
                    } else {
                        ti_tel.setErrorEnabled(false);

                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

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

                        try{
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
                EditText input = ti_codigop.getEditText();
                Pattern cod = Pattern.compile("^[0-9]{4,5}?$");
                String inputText = input.getText().toString().trim();
                if (cod.matcher(inputText).matches() == false) {
                    ti_codigop.setError(" ");
                } else {
                    ti_codigop.setErrorEnabled(false);

                }
            }
        });


//Condicional seekbar antiguedad
        seekBar.post(new Runnable() {//En este caso también significa que el oyente sólo se desencadena en un elemento cambiante.
            @Override
            public void run() {
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        salida.setText((String.valueOf(progress)));
                        aniosAntiguedad=progress;

                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
            }
        });

        onivel_esc.post(new Runnable() {
            @Override
            public void run() {
                onivel_esc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        nivel = i;
                        if ( nivel== 0) {
                            TextView errorText = (TextView) onivel_esc.getSelectedView();
                            errorText.setError("");
                            errorText.setTextColor(Color.RED);//just to highlight that this is an error
                            errorText.setText("Opción invalida!!");
                            guardar.setEnabled(false);
                        }else if (nivel > 0){
                            guardar.setEnabled(true);

                        }}
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }
        });

        oturno.post(new Runnable() {
            @Override
            public void run() {
                oturno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        turno = i;
                        if (turno == 0) {
                            TextView errorText = (TextView) oturno.getSelectedView();
                            errorText.setError("");
                            errorText.setTextColor(Color.RED);//just to highlight that this is an error
                            errorText.setText("Opción invalida!!");
                            guardar.setEnabled(false);
                        } else{
                            guardar.setEnabled(true);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }
        });

        ocarrera.post(new Runnable() {
            @Override
            public void run() {
                ocarrera.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });

        ocategoria.post(new Runnable() {
            @Override
            public void run() {
                ocategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        categoria = i;
                        if (categoria == 0) {
                            TextView errorText = (TextView) ocategoria.getSelectedView();
                            errorText.setError("");
                            errorText.setTextColor(Color.RED);//just to highlight that this is an error
                            errorText.setText("Opción invalida!!");
                            guardar.setEnabled(false);
                        } else{
                            guardar.setEnabled(true);
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }
        });

        otipo_plantel.post(new Runnable() {
            @Override
            public void run() {
                otipo_plantel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        tipo = i;
                        if (tipo == 0) {
                            TextView errorText = (TextView) otipo_plantel.getSelectedView();
                            errorText.setError("");
                            errorText.setTextColor(Color.RED);//just to highlight that this is an error
                            errorText.setText("Opción invalida!!");
                            guardar.setEnabled(false);
                        } else{
                            guardar.setEnabled(true);
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

            }
        });

        spinombramiento.post(new Runnable() {
            @Override
            public void run() {
                spinombramiento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        cont_nombramiento = position;
                        if (cont_nombramiento == 0) {
                            TextView errorText = (TextView) spinombramiento.getSelectedView();
                            errorText.setError("");
                            errorText.setTextColor(Color.RED);//just to highlight that this is an error
                            errorText.setText("Opción invalida!!");
                            guardar.setEnabled(false);

                        } else if (cont_nombramiento == 1) {
                            /*Toast.makeText(getContext(), "Eres candidato a cambio", Toast.LENGTH_SHORT).show();*/
                            guardar.setEnabled(true);
                        } else {
                            TextView errorText = (TextView) spinombramiento.getSelectedView();
                            errorText.setError("");
                            errorText.setTextColor(Color.RED);//just to highlight that this is an error
                            errorText.setText("Lo sentimos, no cumple con los requisitos...Revise la convocatoria");
                            guardar.setEnabled(false);
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Nada fue seleccionado. Por cierto, no he visto que este método se desencadene
                    }
                });
            }
        });

        onota.post(new Runnable() {
            @Override
            public void run() {
                onota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        nota_des = i;
                        if (nota_des == 0) {
                            TextView errorText = (TextView) onota.getSelectedView();
                            errorText.setError("");
                            errorText.setTextColor(Color.RED);//just to highlight that this is an error
                            errorText.setText("Opción invalida!!");
                            String slect = onota.getSelectedItem().toString();
                            int sele = onota.getSelectedItemPosition();
                            String se = "" + sele;
                            s1.setText(slect);
                            // guardar.setEnabled(false);
                        } else if (nota_des == 1){
                            onota.requestFocus();
                           // Toast.makeText(getContext(), "Lo sentimos, no cumple con los requisitos...Revise la convocatoria", Toast.LENGTH_LONG).show();
                       // guardar.setEnabled(false);
                        }else if (nota_des == 2){
                            onota.requestFocus();
                           // guardar.setEnabled(true);
                        }
                        }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });

        oprocedimiento.post(new Runnable() {
            @Override
            public void run() {
                oprocedimiento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        suj_proced = i;
                        if (suj_proced == 0){
                            TextView errorText = (TextView)oprocedimiento.getSelectedView();
                            errorText.setError("");
                            errorText.setTextColor(Color.RED);//just to highlight that this is an error
                            errorText.setText("Opción invalida!!");
                            String slect = oprocedimiento.getSelectedItem().toString();
                            int sele = oprocedimiento.getSelectedItemPosition();
                            String se = "" + sele;
                            s2.setText(slect);
                            //guardar.setEnabled(false);

                       /* } else if (suj_proced == 1){
                            Toast.makeText(getContext(), "Lo sentimos, no cumple con los requisitos...Revise la convocatoria", Toast.LENGTH_LONG).show();
                            //guardar.setEnabled(false);
                        }else{
                        guardar.setEnabled(true);*/
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }
        });

       /* onota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                String slect = onota.getSelectedItem().toString();
                int sele = onota.getSelectedItemPosition();
                String se = "" + sele;
                s1.setText(slect);
                //poci1.setText(se);
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        oprocedimiento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                String slect = oprocedimiento.getSelectedItem().toString();
                int sele = oprocedimiento.getSelectedItemPosition();
                String se = "" + sele;
                s2.setText(slect);
                //poci1.setText(se);
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });*/



        //Arreglo, obtencion de valores de spinners
        String[] opciones_ne = {"Seleccione","Preescolar", "Primaria", "Secundaria", "Secundaria Técnica", "Telesecundaria","Educación Especial"};//Arreglo spinner nivel escolar
        String[] opciones_tn = {"Seleccione","Matutino", "Vespertino", "Jornada completa","Jornada Extendida"};//arreglo turno
        String[] opciones_cat = {"Seleccione","Docente", "Técnico Docente","Asesor Técnico Pedagógico", "Director","Subdirector","Supervisor", "Administrativo", "Intendente"};//arreglo rol
        String[] opciones_tp = {"Seleccione","Municipal", "Estatal", "Federal", "Federalizado"};//arreglo tipoplantel
        String[] opciones_nombra = {"Seleccione","Si", "No"};
        String[] opciones_nota = {"Seleccione","Si", "No"};
        String[] opciones_proced = {"Seleccione","Si", "No"};

        String[] opciones_magisterial = {"No", "Si"};

        // NUeva clase comunicacion para spinner - layout
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones_ne);
        onivel_esc.setAdapter(adapter);
        onivel_esc.setPrompt("Seleccione una opción");


        /*adapter_magis = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones_magisterial);
        onivel_esc.setAdapter(adapter_magis);
        onivel_esc.setPrompt("Seleccione una opción");*/



        adapter_tn = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones_tn);
        oturno.setAdapter(adapter_tn);
        oturno.setPrompt("Seleccione una opción");

        adapter_ct = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones_cat);
        ocategoria.setAdapter(adapter_ct);
        ocategoria.setPrompt("Seleccione una opción");

        adapter_tp = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones_tp);
        otipo_plantel.setAdapter(adapter_tp);
        otipo_plantel.setPrompt("Seleccione una opción");

        adapter_nombra = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones_nombra);
        /*adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/
        spinombramiento.setAdapter(adapter_nombra);
        spinombramiento.setPrompt("Seleccione una opción");

        adapter_nota = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones_nota);
        onota.setAdapter(adapter_nota);
        onota.setPrompt("Seleccione una opción");

        adapter_proce = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones_proced);
        oprocedimiento.setAdapter(adapter_proce);
        oprocedimiento.setPrompt("Seleccione una opción");

        adapter_magisterial = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones_magisterial);
        ocarrera.setAdapter(adapter_magisterial);
        ocarrera.setPrompt("Seleccione una opción");


        return view;
    }


    private void validateEditText(Editable editable) {
        if (!TextUtils.isEmpty(editable)) {
            ti_nombre_escuela.setError(null);
        } else {
            ti_nombre_escuela.setError(" ");
        }
    }
    private void validateEditTextclaveesc(Editable editable) {
        if (!TextUtils.isEmpty(editable)) {
            ti_clave.setError(null);
        } else {
            ti_clave.setError(" ");
        }
    }
    private void validateEditTextzona(Editable editable) {
        if (!TextUtils.isEmpty(editable)) {
            ti_zona.setError(null);
        } else {
            ti_zona.setError(" ");
        }
    }
    private void validateEditTexttel(Editable editable) {
        if (!TextUtils.isEmpty(editable)) {
            ti_tel.setError(null);
        } else {
            ti_tel.setError(" ");
        }
    }
    private void validateEditTextcp(Editable editable) {
        if (!TextUtils.isEmpty(editable)) {
            ti_codigop.setError(null);
        } else {
            ti_codigop.setError(" ");
        }
    }
  /*  private void validateEditTextnomdir(Editable editable) {
        if (!TextUtils.isEmpty(editable)) {
            ti_nom_dir.setError(null);
        } else {
            ti_nom_dir.setError(" ");
        }
    }*/
    ///////

    private void alerta() {
        String msg = getString(R.string.register_msg);
        LoadingDialog.show(getActivity(), msg);
    }

    private void guardarMetodo() {
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // FragmentTransaction transaction = getFragmentManager().beginTransaction();

                oescuela.setError(null);
                oclave.setError(null);
                ozona.setError(null);
                otel.setError(null);
                codigop.setError(null);
                /*onom_dir.setError(null);*/
                salida.setError(null);

                Pattern cod = Pattern.compile("^[0-9]{4,5}?$");


                if (aniosAntiguedad < 2) {
                    Toast.makeText(getContext(), "No es valida la antiguedad", Toast.LENGTH_SHORT).show();
                    // guardar.setEnabled(false);
                } else if (nivel == 0) {
                    //guardar.setEnabled(false);
                    Toast.makeText(getContext(), "Seleccione nivel escolar", Toast.LENGTH_SHORT).show();
                } else if (turno == 0) {
                    //guardar.setEnabled(false);
                    Toast.makeText(getContext(), "Seleccione turno", Toast.LENGTH_SHORT).show();
                } else if (categoria == 0) {
                    //guardar.setEnabled(false);
                    Toast.makeText(getContext(), "Seleccione categoria", Toast.LENGTH_SHORT).show();
                } else if (tipo == 0) {
                    //guardar.setEnabled(false);
                    Toast.makeText(getContext(), "Seleccione tipo de plantel", Toast.LENGTH_SHORT).show();
                } else if (cont_nombramiento == 0) {
                    //guardar.setEnabled(false);
                    Toast.makeText(getContext(), "Seleccione nombramiento", Toast.LENGTH_SHORT).show();
                } else if (nota_des == 0) {
                    Toast.makeText(getContext(), "Seleccione nota", Toast.LENGTH_SHORT).show();
                } else if (suj_proced == 0) {
                    Toast.makeText(getContext(), "Seleccione procedimiento", Toast.LENGTH_SHORT).show();
                } else if (nota_des == 1 && suj_proced == 1) {
                    Toast.makeText(getContext(), "Lo sentimos, no cumple con los requisitos...Revise la convocatoria", Toast.LENGTH_SHORT).show();
                    onota.requestFocus();
                } else if (nota_des == 1 && suj_proced == 2) {
                    Toast.makeText(getContext(), "Lo sentimos, no cumple con los requisitos...Revise la convocatoria", Toast.LENGTH_SHORT).show();
                    onota.requestFocus();
                } else if (nota_des == 2 && suj_proced == 1) {
                    Toast.makeText(getContext(), "Lo sentimos, no cumple con los requisitos...Revise la convocatoria", Toast.LENGTH_SHORT).show();
                    oprocedimiento.requestFocus();
                } else if(cod.matcher(codigop.getText().toString()).matches() == false ){
                    ti_codigop.setError(" ");
                    codigop.requestFocus();
                }else {

                    String esc = oescuela.getText().toString().trim();
                    String clv = oclave.getText().toString().trim();

                    String nhoras = et_num_horas.getText().toString().trim();
                    String materia = et_materia.getText().toString().trim();

                    String zon = ozona.getText().toString().trim();
                    String tl = otel.getText().toString().trim();
                    String cp = codigop.getText().toString().trim();
                    /*String nom_direc = onom_dir.getText().toString().trim();*/
                    String estd = estado.getText().toString();
                    String mun = muni.getText().toString().trim();
                    String colo = select.getText().toString().trim();
                    String sal = salida.getText().toString().trim();


                    //Estos métodos se ejecutará cuando se presione el botón
                    String seleccion = onivel_esc.getSelectedItem().toString();
                    if (seleccion.equals("Preescolar")) {
                    } else if (seleccion.equals("Primaria")) {
                    } else if (seleccion.equals("Secundaria")) {
                    } else if (seleccion.equals("Secundaria Técnica")){
                    } else if (seleccion.equals("Telesecundaria")){
                    } else if (seleccion.equals("Educacación Especial")) {
                    }
                    //guardado de seleccion spinnner turno
                    String seleccion_tn = oturno.getSelectedItem().toString();
                    if (seleccion_tn.equals("Matutino")) {
                    } else if (seleccion_tn.equals("Vespertino")) {
                    } else if (seleccion_tn.equals("Jornada Completa")) {
                    } else if (seleccion_tn.equals("Jornada Extendida")) {
                    }
                    //guardado de seleccion spinnner rol
                    String seleccion_ct = ocategoria.getSelectedItem().toString();
                    if (seleccion_ct.equals("Docente")) {
                    } else if (seleccion_ct.equals("Técnico Docente")) {
                    } else if (seleccion_ct.equals("Asesor Técnico Pedagógico")) {
                    } else if (seleccion_ct.equals("Supervisor")) {
                    } else if (seleccion_ct.equals("Subdirector")) {
                    } else if (seleccion_ct.equals("Director")) {
                    } else if (seleccion_ct.equals("Administrativo")) {
                    } else if (seleccion_ct.equals("Intendente")) {
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
                    } else if (seleccion_nombram.equals("Si")) {
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


                    //Sleccion spinnner magisterial
                    String seleccion_magis = ocarrera.getSelectedItem().toString();
                    if (seleccion_magis.equals("No")) {
                    } else if (seleccion_magis.equals("Si")) {
                    }


                    //Validaciones campos
                    if (TextUtils.isEmpty(esc)) {
                        ti_nombre_escuela.setError(" ");
                        ti_nombre_escuela.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(clv)) {
                        ti_clave.setError(" ");
                        ti_clave.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(zon)) {
                        ti_zona.setError(" ");
                        ti_zona.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(tl)) {
                        ti_tel.setError(" ");
                        ti_tel.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(cp)) {
                        ti_codigop.setError(" ");
                        ti_codigop.requestFocus();
                        return;
                    }
                 /*   if (TextUtils.isEmpty(nom_direc)) {
                        ti_nom_dir.setError(" ");
                        ti_nom_dir.requestFocus();
                        return;
                    }*/

                    if (TextUtils.isEmpty(mun)) {
                        ti_codigop.setError(" ");
                        codigop.requestFocus();
                        return;
                    }
              /*  if (TextUtils.isEmpty(sal)) {
                    salida.setError(getString(R.string.error_campo_oblogatorio));
                    return;
                }*/

                    Toast.makeText(getContext(), "Se han validado y guardado correctamente los datos", Toast.LENGTH_SHORT).show();
                    ((principalMenu) getActivity()).openLoadingDialog();

                    //Envio a BD
                    HashMap<String, String> params = new HashMap<>();
                    params.put("nombre_esc", esc);
                    params.put("clave_esc", clv);
                    params.put("nivel_escolar", seleccion);
                    params.put("turno", seleccion_tn);

                    params.put("num_horas", nhoras);
                    params.put("materia", materia);
                    params.put("carrera_magisterial", seleccion_magis);

                    params.put("zona_esc", zon);
                    params.put("telefono", tl);
                    params.put("c_postal", cp);
                    params.put("estado", estd);
                    params.put("municipio", mun);
                    params.put("colonia", colo);
                    /*params.put("nombre_direc", nom_direc);*/
                    params.put("rol", seleccion_ct);
                    params.put("tipo_plantel", seleccion_tp);
                    params.put("nombramiento", seleccion_nombram);
                    params.put("labor", sal);
                    params.put("nota", seleccion_nota);
                    params.put("procedimiento", seleccion_proc);

                    List<Token> userId = Token.listAll(Token.class);
                    for (Token ids : userId) {

                        id = ids.getUserId();

                    }

                    Call<Responses> call = BovedaClient.getInstanceClient().getApiClient().registroEscuela(params, id);
                    call.enqueue(new Callback<Responses>() {

                        @Override
                        public void onResponse(Call<Responses> call, Response<Responses> response) {
                            // Toast.makeText(getContext(), "Guardando...", Toast.LENGTH_SHORT).show();
                            //alerta();
                            // ((principalMenu)getActivity()).openLoadingDialog();
                        }

                        @Override
                        public void onFailure(Call<Responses> call, Throwable t) {

                        }
                    });
                   /* getActivity().getSupportFragmentManager().beginTransaction().replace
                            (R.id.conten, fragment_interes).addToBackStack(null).commit();*/
                    Intent intent = new Intent(getActivity(), PrincipalSolicitud.class);
                    startActivity(intent);

                }
            }
        });
    }

    public void modificarMetodo(){
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.conten,dataSchool).addToBackStack(null).commit();
                oescuela.setError(null);
                oclave.setError(null);
                ozona.setError(null);
                otel.setError(null);
                codigop.setError(null);
               /* onom_dir.setError(null);*/
                salida.setError(null);

                // oescuela.setSelection(oescuela.length());
               int pro = Integer.parseInt(salida.getText().toString());
               if (pro < 2) {
                    Toast.makeText(getContext(), "No es valida la antiguedad", Toast.LENGTH_SHORT).show();
                    // guardar.setEnabled(false);
                } else if (nivel == 0) {
                    //guardar.setEnabled(false);
                    Toast.makeText(getContext(), "Seleccione nivel escolar", Toast.LENGTH_SHORT).show();
                } else if (turno == 0) {
                    //guardar.setEnabled(false);
                    Toast.makeText(getContext(), "Seleccione turno", Toast.LENGTH_SHORT).show();
                } else if (categoria == 0) {
                    //guardar.setEnabled(false);
                    Toast.makeText(getContext(), "Seleccione categoria", Toast.LENGTH_SHORT).show();
                } else if (tipo == 0) {
                    //guardar.setEnabled(false);
                    Toast.makeText(getContext(), "Seleccione tipo de plantel", Toast.LENGTH_SHORT).show();
                } else if (cont_nombramiento == 0) {
                    //guardar.setEnabled(false);
                    Toast.makeText(getContext(), "Valide nombramiento", Toast.LENGTH_SHORT).show();
                } else if (nota_des == 0) {
                    Toast.makeText(getContext(), "Valide nota", Toast.LENGTH_SHORT).show();
                } else if (suj_proced == 0) {
                    Toast.makeText(getContext(), "Valide procedimiento", Toast.LENGTH_SHORT).show();
                } else if (nota_des == 1 && suj_proced == 1) {
                    Toast.makeText(getContext(), "Lo sentimos, no cumple con los requisitos...Revise la convocatoria", Toast.LENGTH_SHORT).show();
                    onota.requestFocus();
                } else if (nota_des == 1 && suj_proced == 2) {
                    Toast.makeText(getContext(), "Lo sentimos, no cumple con los requisitos...Revise la convocatoria", Toast.LENGTH_SHORT).show();
                    onota.requestFocus();
                } else if (nota_des == 2 && suj_proced == 1) {
                    Toast.makeText(getContext(), "Lo sentimos, no cumple con los requisitos...Revise la convocatoria", Toast.LENGTH_SHORT).show();
                    oprocedimiento.requestFocus();
                } else {


                    String esc = oescuela.getText().toString();
                    String clv = oclave.getText().toString();


                   String nhoras = et_num_horas.getText().toString().trim();
                   String materia = et_materia.getText().toString().trim();


                    String zon = ozona.getText().toString();
                    String tl = otel.getText().toString();
                    String cp = codigop.getText().toString();
                    /*String nom_direc = onom_dir.getText().toString();*/
                    String estd = estado.getText().toString();
                    String mun = muni.getText().toString();
                    String colo = select.getText().toString();
                    String sal = salida.getText().toString();


                   //Estos métodos se ejecutará cuando se presione el botón
                   String seleccion = onivel_esc.getSelectedItem().toString();
                   if (seleccion.equals("Preescolar")) {
                   } else if (seleccion.equals("Primaria")) {
                   } else if (seleccion.equals("Secundaria")) {
                   } else if (seleccion.equals("Secundaria Técnica")){
                   } else if (seleccion.equals("Telesecundaria")) {
                   } else if (seleccion.equals("Educación Especial")) {
                   }
                    //guardado de seleccion spinnner turno
                    String seleccion_tn = oturno.getSelectedItem().toString();
                    if (seleccion_tn.equals("Matutino")) {
                    } else if (seleccion_tn.equals("Vespertino")) {
                    } else if (seleccion_tn.equals("Jornada Completa")) {
                    } else if (seleccion_tn.equals("Jornada Extendida")) {
                    }
                       //guardado de seleccion spinnner rol
                       String seleccion_ct = ocategoria.getSelectedItem().toString();
                       if (seleccion_ct.equals("Docente")) {
                       } else if (seleccion_ct.equals("Técnico Docente")) {
                       } else if (seleccion_ct.equals("Asesor Técnico Pedagógico")) {
                       } else if (seleccion_ct.equals("Supervisor")) {
                       } else if (seleccion_ct.equals("Subdirector")) {
                       } else if (seleccion_ct.equals("Director")) {
                       } else if (seleccion_ct.equals("Administrativo")) {
                       } else if (seleccion_ct.equals("Intendente")) {
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
                    } else if (seleccion_nombram.equals("Si")) {
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


                   //Sleccion spinnner magisterial
                   String seleccion_magis = ocarrera.getSelectedItem().toString();
                   if (seleccion_magis.equals("No")) {
                   } else if (seleccion_magis.equals("Si")) {
                   }



                   //Validaciones campos
                    if (TextUtils.isEmpty(esc)) {
                        ti_nombre_escuela.setError(" ");
                        ti_nombre_escuela.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(clv)) {
                        ti_clave.setError(" ");
                        ti_clave.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(zon)) {
                        ti_zona.setError(" ");
                        ti_zona.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(tl)) {
                        ti_tel.setError(" ");
                        ti_tel.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(cp)) {
                        ti_codigop.setError(" ");
                        ti_codigop.requestFocus();
                        return;
                    }
                   /* if (TextUtils.isEmpty(nom_direc)) {
                        ti_nom_dir.setError(" ");
                        ti_nom_dir.requestFocus();
                        return;

                    }*/
                /*else if (nota_des == 0) {
                    Toast.makeText(getContext(), "selecciona notas", Toast.LENGTH_SHORT).show();
                } else if (suj_proced == 0) {
                    Toast.makeText(getContext(), "selecciona proced", Toast.LENGTH_SHORT).show();
                } else if (nota_des == 1 && suj_proced == 1) {
                    Toast.makeText(getContext(), "Lo sentimos, no cumple con los requisitos...Revise la convocatoria", Toast.LENGTH_SHORT).show();
                } else if (nota_des == 1 && suj_proced == 2) {
                    Toast.makeText(getContext(), "Lo sentimos, no cumple con los requisitos...Revise la convocatoria", Toast.LENGTH_SHORT).show();
                } else if (nota_des == 2 && suj_proced == 1) {
                    Toast.makeText(getContext(), "Lo sentimos, no cumple con los requisitos...Revise la convocatoria", Toast.LENGTH_SHORT).show();
                } else {*/
              /*  if (TextUtils.isEmpty(sal)) {
                    salida.setError(getString(R.string.error_campo_oblogatorio));
                    return;
                }*/
                   Toast.makeText(getContext(), "Se han actualizado y guardado correctamente los datos", Toast.LENGTH_SHORT).show();
                   try {
                       ((principalMenu) getActivity()).openLoadingDialog();
                       //alerta1();
                   }catch (Exception e) {
                   }


                    //llamada de metodo de clase de activity a este fragment
                    //se declaro el metodo en loadingDialog.java y en principalMenu para este caso
                    //((principalMenu)getActivity().openLoadingDialog();


                    //Envio a BD
                    HashMap<String, String> params = new HashMap<>();
                    params.put("nombre_esc", esc);
                    params.put("clave_esc", clv);
                    params.put("nivel_escolar", seleccion);
                    params.put("turno", seleccion_tn);

                   params.put("num_horas", nhoras);
                   params.put("materia", materia);
                   params.put("carrera_magisterial", seleccion_magis);

                    params.put("zona_esc", zon);
                    params.put("telefono", tl);
                    params.put("c_postal", cp);
                    params.put("estado", estd);
                    params.put("municipio", mun);
                    params.put("colonia", colo);
                   /* params.put("nombre_direc", nom_direc);*/
                    params.put("rol", seleccion_ct);
                    params.put("tipo_plantel", seleccion_tp);
                    params.put("nombramiento", seleccion_nombram);
                    params.put("labor", sal);
                    params.put("nota", seleccion_nota);
                    params.put("procedimiento", seleccion_proc);

                    List<Token> userId = Token.listAll(Token.class);
                    for (Token ids : userId) {


                        id = ids.getUserId();

                    }

                    Call<Responses> call = BovedaClient.getInstanceClient().getApiClient().registroEscuela(params, id);
                    call.enqueue(new Callback<Responses>() {

                        @Override
                        public void onResponse(Call<Responses> call, Response<Responses> response) {
                            // getDataSchool();
                            //  Toast.makeText(getContext(), "Guardando...", Toast.LENGTH_SHORT).show();
                            //  alerta();
                        }

                        @Override
                        public void onFailure(Call<Responses> call, Throwable t) {

                        }
                    });
                    getDataSchool();
                    /*((principalMenu) getActivity()).openLoadingDialog();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.conten, fragment_interes).
                            addToBackStack(null).commit();*/
                   Intent intent = new Intent(getActivity(), PrincipalSolicitud.class);
                   startActivity(intent);

                }
            }
        });
    }
    private void getDataSchool() {
        oescuela.setError(null);
        oclave.setError(null);
        ozona.setError(null);
        otel.setError(null);
        codigop.setError(null);
       /* onom_dir.setError(null);*/
        salida.setError(null);

        List<Phone> list1 = Phone.listAll(Phone.class);
        for (Phone pho : list1) {


            phones = pho.getPhone();

        }

        Call<List<DatosSchool>> callVersiones = BovedaClient.getInstanceClient().getApiClient().getDataSchool(phones);
        callVersiones.enqueue(new Callback<List<DatosSchool>>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<List<DatosSchool>> call, Response<List<DatosSchool>> response) {
                List<DatosSchool> respuestas = response.body();
                List<String> list = new ArrayList<String>();

                for (DatosSchool res : respuestas) {
                    if (res.getNombre_esc() == null) {
                        guardarMetodo();
                        progressBar2.setVisibility(View.GONE);
                    }else{
                        progressBar2.setVisibility(View.GONE);
                        String nombre_esc = "" + res.getNombre_esc();
                        oescuela.setText(nombre_esc);
                        oescuela.setEnabled(false);
                        //oescuela.setInputType(InputType.TYPE_NULL);

                        String clave_esc = "" + res.getClave_esc();
                        oclave.setText(clave_esc);
                        oclave.setEnabled(false);


                        String nu_horas = "" + res.getNum_horas();
                        et_num_horas.setText(nu_horas);
                        et_num_horas.setEnabled(false);

                        String materia = "" + res.getMateria();
                        et_materia.setText(materia);
                        et_materia.setEnabled(false);


                        String nivel_escolar = res.getNivel_escolar();
                        int spinnerPosition = adapter.getPosition(nivel_escolar);
                        onivel_esc.setSelection(spinnerPosition);
                        onivel_esc.setEnabled(false);

                        String turno = "" + res.getTurno();
                        int sp_turno = adapter_tn.getPosition(turno);
                        oturno.setSelection(sp_turno);
                        oturno.setEnabled(false);

                        String zona = "" + res.getZon_esc();
                        ozona.setText(zona);
                        ozona.setEnabled(false);

                        String phone = "" + res.getTelefono();
                        otel.setText(phone);
                        otel.setEnabled(false);

                        String cp = "" + res.getC_postal();
                        codigop.setText(cp);
                        codigop.setEnabled(false);

                        String sl = "" + res.getColonia();
                        select.setText(sl);
                        ocolonia.setEnabled(false);

                        String municipio = "" + res.getMunicipio();
                        muni.setText(municipio);
                        muni.setEnabled(false);

                        String estad = "" + res.getEstado();
                        estado.setText(estad);
                        estado.setEnabled(false);

                     /*   String nomdir = "" + res.getNombre_direc();
                        onom_dir.setText(nomdir);
                        onom_dir.setEnabled(false);*/

                        String catego = "" + res.getRol();
                        int sp_catego = adapter_ct.getPosition(catego);
                        ocategoria.setSelection(sp_catego);
                        ocategoria.setEnabled(false);

                        String tp_plantel = "" + res.getTipo_plantel();
                        int sp_tp_plantel = adapter_tp.getPosition(tp_plantel);
                        otipo_plantel.setSelection(sp_tp_plantel);
                        otipo_plantel.setEnabled(false);

                        String nombramiento = "" + res.getNombramiento();
                        int sp_nombramiento = adapter_nombra.getPosition(nombramiento);
                        spinombramiento.setSelection(sp_nombramiento);
                        spinombramiento.setEnabled(false);

                        String carrera_magisterial = res.getMagisterial();
                        int sp_crr_magis = adapter_magisterial.getPosition(carrera_magisterial);
                        ocarrera.setSelection(sp_crr_magis);
                        ocarrera.setEnabled(false);

                        //-----------------------------------------------------------------------
                        String labor = seekBar + res.getLabor();
                        int sb_labor = seekBar.getProgress();
                        seekBar.setProgress(sb_labor);
                        seekBar.setEnabled(false);

                        String lbsl = "" + res.getLabor();
                        salida.setText(lbsl);
                        salida.setEnabled(false);


                        String nota = "" + res.getNota();
                        int sp_nota = adapter_nota.getPosition(nota);
                        onota.setSelection(sp_nota);
                        onota.setEnabled(false);

                        String procedimiento = "" + res.getNota();
                        int sp_proced = adapter_proce.getPosition(procedimiento);
                        oprocedimiento.setSelection(sp_proced);
                        oprocedimiento.setEnabled(false);

                        guardar.setText("modificar");
                        guardar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Modificar();
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<List<DatosSchool>> call, Throwable t) {
                L.error("getDataSchool " + t.getMessage());
            }
        });

    }


    public void Modificar() {
        Call<List<DatosSchool>> callVersiones = BovedaClient.getInstanceClient().getApiClient().getDataSchool(phones);
        callVersiones.enqueue(new Callback<List<DatosSchool>>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<List<DatosSchool>> call, Response<List<DatosSchool>> response) {

                List<DatosSchool> respuestas = response.body();

                for (DatosSchool res : respuestas) {

                    String nombreesc = "" + res.getNombre_esc();
                    oescuela.setText(nombreesc);
                    oescuela.setEnabled(true);

                    String claveesc = "" + res.getClave_esc();
                    oclave.setText(claveesc);
                    oclave.setEnabled(true);


                    //Agregados para ver.1.2
                    String num_horasmod = "" + res.getNum_horas();
                    et_num_horas.setText(num_horasmod);
                    et_num_horas.setEnabled(true);

                    String materiamod = "" + res.getMateria();
                    et_materia.setText(materiamod);
                    et_materia.setEnabled(true);



                    String labormod = seekBar + res.getLabor();
                    int sb_labormod = seekBar.getProgress();
                    seekBar.setProgress(sb_labormod);
                    seekBar.setEnabled(true);


                    String nivelescolar = res.getNivel_escolar();
                    int spinnerPosition = adapter.getPosition(nivelescolar);
                    onivel_esc.setSelection(spinnerPosition);
                    onivel_esc.setEnabled(true);


                    String turnomod = "" + res.getTurno();
                    int sp_turno = adapter_tn.getPosition(turnomod);
                    oturno.setSelection(sp_turno);
                    oturno.setEnabled(true);

                    String zonamod = "" + res.getZon_esc();
                    ozona.setText(zonamod);
                    ozona.setEnabled(true);

                    String phonemod = "" + res.getTelefono();
                    otel.setText(phonemod);
                    otel.setEnabled(true);

                    String cpmod = "" + res.getC_postal();
                    codigop.setText(cpmod);
                    codigop.setEnabled(true);

                    String slmod = "" + res.getColonia();
                    select.setText(slmod);
                    ocolonia.setEnabled(true);

                    String municipiomod = "" + res.getMunicipio();
                    muni.setText(municipiomod);
                    muni.setEnabled(true);

                    String estadmod = "" + res.getEstado();
                    estado.setText(estadmod);
                    estado.setEnabled(true);

                /*    String nomdirmod = "" + res.getNombre_direc();
                    onom_dir.setText(nomdirmod);
                    onom_dir.setEnabled(true);*/

                    String categomod = "" + res.getCategoria();
                    int sp_categomod = adapter_ct.getPosition(categomod);
                    ocategoria.setSelection(sp_categomod);
                    ocategoria.setEnabled(true);

                    String tp_plantelmod = "" + res.getTipo_plantel();
                    int sp_tp_plantel = adapter_tp.getPosition(tp_plantelmod);
                    otipo_plantel.setSelection(sp_tp_plantel);
                    otipo_plantel.setEnabled(true);

                    String nombramientomod = "" + res.getNombramiento();
                    int sp_nombramiento = adapter_nombra.getPosition(nombramientomod);
                    spinombramiento.setSelection(sp_nombramiento);
                    spinombramiento.setEnabled(true);

                  /*  String magisterialmod = "" + res.getMagisterial();
                    int sp_magisterial = adapter_magisterial.getPosition(magisterialmod);
                    sp_crr_magis.setSelection(sp_magisterial);
                    sp_crr_magis.setEnabled(true);*/

                    String carrera_magisterialmod = res.getMagisterial();
                    int spmagisterial = adapter_magisterial.getPosition(carrera_magisterialmod);
                    ocarrera.setSelection(spmagisterial);
                    ocarrera.setEnabled(true);

                    //-----------------------------------------------------------------------
                 /*   String labormod = seekBar + res.getLabor();
                    int sb_labormod = seekBar.getProgress();
                    seekBar.setProgress(sb_labormod);
                    seekBar.setEnabled(true);*/

                    String lbslmod = "" + res.getLabor();
                    salida.setText(lbslmod);
                    salida.setEnabled(true);


                    String notamod = "" + res.getNota();
                    int sp_nota = adapter_nota.getPosition(notamod);
                    onota.setSelection(sp_nota);
                    onota.setEnabled(true);

                    String procedimientomod = "" + res.getNota();
                    int sp_proced = adapter_proce.getPosition(procedimientomod);
                    oprocedimiento.setSelection(sp_proced);
                    oprocedimiento.setEnabled(true);

                    guardar.setText("guardar");
                    guardar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            modificarMetodo();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<List<DatosSchool>> call, Throwable t) {
                L.error("getDataSchool " + t.getMessage());
            }
        });

    }


    public void alerta1(){
        String msg = getString(R.string.act_msg);
        LoadingDialog.show(getContext(), msg);

    }


}