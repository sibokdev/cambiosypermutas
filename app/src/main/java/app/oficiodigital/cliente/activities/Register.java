package app.oficiodigital.cliente.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.clients.BovedaClient;
import app.oficiodigital.cliente.fragments.DataSchool;
import app.oficiodigital.cliente.models.Request.RespuestaPreguntaSecreta;
import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.storage.ModelsBD.Preguntas1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ari on 10/03/2021.
 */

public class Register extends BaseActivity implements View.OnClickListener {

    private EditText et_nombre, et_ap, et_am, et_numtel1, et_numtel2, et_cedula, et_mail, et_password, et_password2
           , presult1,  presult2;
    private TextView tv_edad, FechaN, phone, resul, resul1, tv_edadm,
            pregunta1, pregunta2, poci1, poci2, tv_sexo, token1;
    private Button  bt_fecha;
    private RadioButton rb_fem, rb_masc;
    private Spinner p1, p2;
    private TextInputLayout tvN,tvp1,tvp2, tvt1, tvt2, tvc,tcpas1,tcpass2,tce,tcr1, tcr2,ti_res1, ti_res2, ti_password, ti_password2;

    private static final String CARPETA_PRINCIPAL = "app.oficiodigita.proveedor"; //directorio principal
    private static final String CARPETA_IMAGEN = "Pictures"; //carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;


    //private CompleteRegisterPresenter mPresenter;

    private int aa = 0;
    private int ma = 0;
    private int da = 0;
    private int años = 0;
    private int mes = 0;
    private int dia = 0;
    private int año = 0;
    private int messs = 0;
    private int dias = 0;
    private final int años1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_ap = (EditText) findViewById(R.id.et_ap);
        et_am = (EditText) findViewById(R.id.et_am);
        et_numtel1 = (EditText) findViewById(R.id.et_numtel1);
        et_numtel2 = (EditText) findViewById(R.id.et_numtel2);
        et_cedula = (EditText) findViewById(R.id.et_cedula);
        et_mail = (EditText) findViewById(R.id.et_mail);
        et_password = (EditText) findViewById(R.id.et_password) ;
        et_password2 = (EditText) findViewById(R.id.et_password2) ;

        bt_fecha = (Button) findViewById(R.id.boton_fecha);
        tv_edad = (TextView) findViewById(R.id.tvEdad);
        tv_edadm = (TextView) findViewById(R.id.tvApareceEdad);

        tv_sexo = (TextView) findViewById(R.id.tv_sexo);

        rb_fem = (RadioButton) findViewById(R.id.rb_fem);
        rb_masc = (RadioButton) findViewById(R.id.rb_masc);

        ti_password = (TextInputLayout) findViewById(R.id.ti_password);
        ti_password2 = (TextInputLayout) findViewById(R.id.ti_password2);

        pregunta1 = (TextView) findViewById(R.id.pregunta1);
        pregunta2 = (TextView) findViewById(R.id.pregunta2);

        ti_res1 = (TextInputLayout) findViewById(R.id.ti_res1);
        ti_res2 = (TextInputLayout) findViewById(R.id.ti_res2);



        phone = (TextView) findViewById(R.id.phone);


        p1 = (Spinner) findViewById(R.id.preguntas);
        p2 = (Spinner) findViewById(R.id.preguntas2);
        poci1 = (TextView) findViewById(R.id.poci1);
        poci2 = (TextView) findViewById(R.id.poci2);
        presult1 = (EditText) findViewById(R.id.res1);
        presult2 = (EditText) findViewById(R.id.res2);

        tvN = (TextInputLayout) findViewById(R.id.ti_nombre);
        tvp1 = (TextInputLayout) findViewById(R.id.ti_ap);
        tvp2 = (TextInputLayout) findViewById(R.id.ti_am);
        tvt1 = (TextInputLayout) findViewById(R.id.ti_numtel1);
        tvt2 = (TextInputLayout) findViewById(R.id.ti_numtel2);
        tvc = (TextInputLayout) findViewById(R.id.ti_cedula);
        tce = (TextInputLayout) findViewById(R.id.ti_mail);
        tcpas1 = (TextInputLayout) findViewById(R.id.ti_password);
        tcpass2 = (TextInputLayout) findViewById(R.id.ti_password2);
        tcr1 = (TextInputLayout) findViewById(R.id.ti_res1);
        tcr2 = (TextInputLayout) findViewById(R.id.ti_res2);



        token1 = (TextView) findViewById(R.id.token);
        String token = getIntent().getStringExtra("tokenPhone");
        token1.setText(token);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }
        //primer pregunta
        primerPregunta();

        //segunda pregunta
        segundaPregunta();
        //

        String phon = getIntent().getStringExtra("phone");
        phone.setText(phon);


        bt_fecha.setOnClickListener(this);

        //Sexo fem masc
        rb_fem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tv_sexo.setText("femenino");
                } else {
                    tv_sexo.setText("masculino");
                }
            }
        });

        et_password2.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String strPass1 = et_password.getText().toString();
                String strPass2 = et_password2.getText().toString();
                if (strPass1.equals(strPass2)) {
                    tcpass2.setErrorEnabled(false);

                } else {
                    tcpass2.setError("contraseñas diferentes");

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        et_password.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText input = tcpas1.getEditText();
                Pattern passw = Pattern.compile("[A-Z-a-z-0-9]{8,100}");
                String inputText = input.getText().toString().trim();
                if (passw.matcher(inputText).matches() == false) {
                    tcpas1.setError("Debes ingresar al menos 8 caracteres");
                } else {
                    tcpas1.setErrorEnabled(false);

                }
            }
        });


        /*List<Oficios> list1 = Oficios.listAll(Oficios.class);
        List<String> lis1 = new ArrayList<String>();
        Oficios preguntas1 = new Oficios();
        for (int i = 0; i < list1.size(); i++) {
            preguntas1 = list1.get(i);
            lis1.add(preguntas1.getOficios());
            //Preguntas1.deleteAll(Preguntas1.class);
        }

        String[] oficiosArray = new String[lis1.size()];

        oficiosArray = lis1.toArray(oficiosArray);

        ArrayList<Integer> ofiList = new ArrayList<>();

        boolean[] selecOficio;

        selecOficio = new boolean[oficiosArray.length];

        String[] finalOficiosArray = oficiosArray;

        tv_ofi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);

                builder.setTitle("Seleccione oficio"); //titulo
                builder.setCancelable(false);
                AlertDialog.Builder builder1 = builder.setMultiChoiceItems(finalOficiosArray, selecOficio, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int a, boolean j) {
                        if (j) {
                            if (!ofiList.contains(a)) {
                                ofiList.add(a);
                                Collections.sort(ofiList);
                            } else {
                                ofiList.remove(a);
                            }

                        }
                    }
                });

                builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() { //metodo guardar
                    @Override
                    public void onClick(DialogInterface dialog, int a) {

                        String item = "";

                        for (int i = 0; i < ofiList.size(); i++) {
                            item = item + finalOficiosArray[ofiList.get(i)];
                            if (i != ofiList.size() - 1) {
                                item = item + ", ";
                            }
                        }

                        for (int i = 0; i < selecOficio.length; i++) {
                            selecOficio[i] = false;
                            ofiList.clear();

                        }

                        tv_ofi.setText(item);//datos ingresados
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int a) {

                        dialog.dismiss();

                    }
                });

                builder.show();

            }
        });*/

    }


    //Seleccionar fecha
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClick(View view) {
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        dias = today.monthDay;
        messs = today.month;
        años = today.year;


        if (view.getId() == R.id.boton_fecha)

        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date date = null;
            try {
                date = sdf.parse("1970/01/01");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar ca = Calendar.getInstance();
            ca.setTime(date);

            año = ca.get(Calendar.YEAR);
            mes = ca.get(Calendar.MONTH);
            dia = ca.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog recogerfecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int año, int mess, int diaa) {
                    final int mesActual = mess + 1;
                    String diaFormato = (diaa < 10) ? "0" + diaa : String.valueOf(diaa);
                    String mesFormato = (mesActual < 10) ? "0" + mesActual : String.valueOf(mesActual);
                    //FechaN.setText("" + diaFormato + "/" + mesFormato + "/" + año);
                    aa = año;
                    ma = Integer.parseInt(mesFormato);
                    da = Integer.parseInt(diaFormato);
                    tv_edadm.setText(calcular(años, (messs + 1), aa, ma, dias, da));
                    bt_fecha.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(244, 207, 62)));

                }

            }, año, messs, dias);

            recogerfecha.show();

        }

    }

    public String calcular(int a, int m, int aa, int ma, int d, int da) {
        int añoss = 0;
        if (ma <= m && da <= d) {

            añoss = a - aa;
        } else {
            añoss = a - aa - 1;
        }

        return +añoss + "";
    }

    public void primerPregunta() {
        List<Preguntas1> list = Preguntas1.listAll(Preguntas1.class);
        List<String> lis = new ArrayList<String>();
        Preguntas1 preguntas = new Preguntas1();
        for (int i = 0; i < list.size(); i++) {
            preguntas = list.get(i);
            lis.add(preguntas.getPreguntas());
            //Preguntas1.deleteAll(Preguntas1.class);
        }

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplication(), R.layout.spinner_colonia, lis);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        p1.setAdapter(adapter1);

        p1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                String slect = p1.getSelectedItem().toString();
                int sele = p1.getSelectedItemPosition();
                String se = "" + sele;
                pregunta1.setText(slect);
               poci1.setText(se);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }

    public void segundaPregunta() {
        List<Preguntas1> list1 = Preguntas1.listAll(Preguntas1.class);
        List<String> lis1 = new ArrayList<String>();
        Preguntas1 preguntas1 = new Preguntas1();
        for (int i = 0; i < list1.size(); i++) {
            preguntas1 = list1.get(i);
            lis1.add(preguntas1.getPreguntas());
            //Preguntas1.deleteAll(Preguntas1.class);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), R.layout.spinner_colonia, lis1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        p2.setAdapter(adapter);


        p2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                String slect = p2.getSelectedItem().toString();
                int sele = p2.getSelectedItemPosition();
                String se = "" + sele;
                pregunta2.setText(slect);
                poci2.setText(se);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }



    public void siguiente(View view) throws JSONException {
        et_nombre.setError(null);
        et_ap.setError(null);
        et_am.setError(null);
        et_numtel1.setError(null);
        et_numtel2.setError(null);
        et_cedula.setError(null);
        et_mail.setError(null);
        et_password.setError(null);
        et_password2.setError(null);
        tv_sexo.setError(null);
        tv_edad.setError(null);

        String name = et_nombre.getText().toString();
        String ap = et_ap.getText().toString();
        String am = et_am.getText().toString();
        String tl1 = et_numtel1.getText().toString();
        String tl2 = et_numtel2.getText().toString();
        String ced = et_cedula.getText().toString();
        String ema = et_mail.getText().toString();
        String pass = et_password.getText().toString();
        String pass2 = et_password2.getText().toString();
        String sex = tv_sexo.getText().toString();
        String ed = tv_edadm.getText().toString();
        String pre1 = pregunta1.getText().toString();
        String pre2 = pregunta2.getText().toString();
        String res1 = presult1.getText().toString();
        String res2 = presult1.getText().toString();

//Validaciones campos
        if (TextUtils.isEmpty(name)) {
            tvN.setError("Ingresa nombre");
            return;
        }
        if (TextUtils.isEmpty(ap)) {
            tvp1.setError("Ingresa apellido paterno");
            return;
        }
        if (TextUtils.isEmpty(am)) {
            tvp2.setError("Ingresa apellido materno");
            return;
        }
        if (TextUtils.isEmpty(tl1)) {
            tvt1.setError("Ingresa número de teléfono");
            return;
        }
        if (TextUtils.isEmpty(tl2)) {
            tvt2.setError("Ingresa número de teléfono");
            return;
        }
        if (TextUtils.isEmpty(ced)) {
            tvc.setError("Ingresa cedula profecional");
            return;
        }
        if (TextUtils.isEmpty(ema)) {
            tce.setError("Ingresa email");
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            tcpas1.setError("Ingresa contraseña");
            return;
        }
        if (TextUtils.isEmpty(pass2)) {
            tcpass2.setError("Ingresa confirmacion de contraseña");
            return;
        }
        if (TextUtils.isEmpty(sex)) {
            tv_sexo.setError(getString(R.string.error_campo_oblogatorio));
            tv_sexo.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(ed)) {
            tv_edad.setError(getString(R.string.error_campo_oblogatorio));
            tv_edad.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(res1)) {
            tcr1.setError("Ingresa respuesta");
            return;
        }
        if (TextUtils.isEmpty(res2)) {
            tcr2.setError("Ingresa respuesta");
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("nombre", name);
        params.put("apaterno", ap);
        params.put("amaterno", am);
        params.put("phone", tl1);
        params.put("phone2", tl2);

        params.put("sexo", sex);
        params.put("edad", ed);

        params.put("cedula_prof", ced);
        params.put("email", ema);
        params.put("password", pass);

        List<RespuestaPreguntaSecreta> listaRespuestas = new ArrayList<RespuestaPreguntaSecreta>();
        RespuestaPreguntaSecreta respuesta = new RespuestaPreguntaSecreta();
        respuesta.setPregunta(pre1);
        respuesta.setRespuesta(res1);

        listaRespuestas.add(respuesta);

        RespuestaPreguntaSecreta respuesta2 = new RespuestaPreguntaSecreta();
        respuesta2.setPregunta(pre2);
        respuesta2.setRespuesta(res2);

        listaRespuestas.add(respuesta2);


        JSONObject jResult = new JSONObject();
        JSONArray jArray = new JSONArray();

        for (int i = 0; i < listaRespuestas.size(); i++) {
            JSONObject jGroup = new JSONObject();
            try {
                jGroup.put("pregunta", listaRespuestas.get(i).getPregunta());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jGroup.put("respuesta", listaRespuestas.get(i).getRespuesta());
            jGroup.put("phone", tl1);
            jArray.put(jGroup);
        }

        params.put("respuesta", jArray.toString());


        Call<Responses> call = BovedaClient.getInstanceClient().getApiClient(). registrarClientes(params);
        call.enqueue(new Callback<Responses>() {

            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {

            }
        });

        startActivity(new Intent(this, LoginActivity.class));

    }
}

