package app.oficiodigital.cliente.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.clients.BovedaClient;
import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.storage.ModelsBD.Oficios;
import app.oficiodigital.cliente.storage.ModelsBD.Preguntas1;
import app.oficiodigital.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ari on 10/03/2021.
 */

public class Register extends BaseActivity implements View.OnClickListener {

    private EditText et_nombre, et_ap, et_am, et_numtel1, et_numtel2, et_cedula, et_mail, et_password, et_password2;
    private TextView tv_edad, FechaN, phone, resul, resul1, tv_edadm,
            pregunta1, pregunta2, poci1, poci2, tv_sexo, token1;
    private Button  bt_fecha;
    private RadioButton rb_fem, rb_masc;
    private Spinner p1, p2;
    private TextInputLayout pw, pw2, ti_res1, ti_res2, ti_password, ti_password2;

    private static final String CARPETA_PRINCIPAL = "app.oficiodigita.proveedor"; //directorio principal
    private static final String CARPETA_IMAGEN = "Pictures"; //carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;


    //private CompleteRegisterPresenter mPresenter;

    private int aa = 0, ma = 0, da = 0, años = 0, mes = 0, dia = 0, año = 0, messs = 0, dias = 0, años1 = 0;

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
                    String diaFormato = (diaa < 10) ? "0" + String.valueOf(diaa) : String.valueOf(diaa);
                    String mesFormato = (mesActual < 10) ? "0" + String.valueOf(mesActual) : String.valueOf(mesActual);
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
                //pregunta1.setText(slect);
               // poci1.setText(se);
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
                //pregunta2.setText(slect);
                //poci2.setText(se);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }



    public void siguiente(View view) {
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

//        int pars_tlf1 = Integer.parseInt((tl1);
//        int pars_tlf2 = Integer.parseInt((tl2);
//        int pars_edad = Integer.parseInt(ed);


        HashMap<String, String> params = new HashMap<>();
        params.put("nombre", name);
        params.put("apaterno", ap);
        params.put("amaterno", am);
        params.put("telefono1", tl1);
        params.put("telefono2", tl2);

        params.put("sexo", sex);
        params.put("edad", ed);

        params.put("cedula_prof", ced);
        params.put("email", ema);
        params.put("password", pass);


        Call<Responses> call = BovedaClient.getInstanceClient().getApiClient().registrarClientes(params);
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

