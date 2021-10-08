package app.oficiodigital.cliente.activities;

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

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.models.ModelsDB.Preguntas1;

/**
 * Created by Ari on 23/04/2021.
 */

public class ClienteRegister extends BaseActivity {

    private EditText et_ap1, et_ap2, pass, mail, et_nombre, pass2, respuesta1, respuesta2, phone;
    private TextView pregunta1, pregunta2, token1;
    private Spinner sp_pregunta1, sp_pregunta2;
     TextInputLayout ti_pregunta, ti_pregunta2, ti_nombre, ti_ap1, ti_ap2, ti_phone, ti_email, ti_res1,
            ti_res2, ti_pass1,ti_pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_register);

        et_nombre = (EditText) findViewById(R.id.et_nombre);
        et_ap1 = (EditText) findViewById(R.id.et_ap1);
        et_ap2 = (EditText) findViewById(R.id.et_ap2);
        phone = (EditText) findViewById(R.id.phones);
        respuesta1 = (EditText) findViewById(R.id.res1);
        respuesta2 = (EditText) findViewById(R.id.res2);
        pass2 = (EditText) findViewById(R.id.password2);
        pass = (EditText) findViewById(R.id.password);
        mail = (EditText) findViewById(R.id.mail);
        pregunta1 = (TextView) findViewById(R.id.pregunta1);
        pregunta2 = (TextView) findViewById(R.id.pregunta2);
        sp_pregunta1 = (Spinner) findViewById(R.id.preguntas);
        sp_pregunta2 = (Spinner) findViewById(R.id.preguntas2);
        token1 = (TextView) findViewById(R.id.token);


        ti_nombre = (TextInputLayout)findViewById(R.id.ti_nombre);
        ti_ap1 = (TextInputLayout)findViewById(R.id.ti_ap1);
        ti_ap2 = (TextInputLayout)findViewById(R.id.ti_ap2);
        ti_phone = (TextInputLayout)findViewById(R.id.ti_phone);
        ti_email = (TextInputLayout)findViewById(R.id.ti_mail);
        ti_pass1 = (TextInputLayout)findViewById(R.id.ti_password);
        ti_pass2 = (TextInputLayout)findViewById(R.id.ti_password2);
        ti_res1 = (TextInputLayout) findViewById(R.id.ti_res1);
        ti_res2 = (TextInputLayout) findViewById(R.id.ti_res2);

        String token = getIntent().getStringExtra("tokenPhone");
        token1.setText(token);


        //primer pregunta
        primerPregunta();

        //segunda pregunta
        segundaPregunta();
        //

        pass2.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String strPass1 = pass.getText().toString();
                String strPass2 = pass2.getText().toString();
                if (strPass1.equals(strPass2)) {
                    ti_pass2.setErrorEnabled(false);

                } else {
                    ti_pass2.setError("contraseñas diferentes");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        pass.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText input = ti_pass1.getEditText();
                Pattern passw = Pattern.compile("[A-Z-a-z-0-9]{8,100}");
                String inputText = input.getText().toString().trim();
                if (passw.matcher(inputText).matches() == false) {
                    ti_pass1.setError("Debes ingresar al menos 8 caracteres");
                } else {
                    ti_pass1.setErrorEnabled(false);
                }
            }
        });


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
        sp_pregunta1.setAdapter(adapter1);

        sp_pregunta1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                String slect = sp_pregunta1.getSelectedItem().toString();
                int sele = sp_pregunta1.getSelectedItemPosition();
                String se = "" + sele;
                pregunta1.setText(slect);
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
        sp_pregunta2.setAdapter(adapter);


        sp_pregunta2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                String slect = sp_pregunta2.getSelectedItem().toString();
                int sele = sp_pregunta2.getSelectedItemPosition();
                String se = "" + sele;
                pregunta2.setText(slect);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }


    public void siguiente(View view) {
        String phon = phone.getText().toString();
        String mai = mail.getText().toString();

        if (et_nombre.length() == 0) {
            ti_nombre.setError("Ingresa nombre");
        } else if (et_ap1.length() == 0) {
            ti_nombre.setErrorEnabled(false);
            ti_ap1.setError("Ingresa apellido paterno");
        } else if (et_ap2.length() == 0) {
            ti_ap1.setErrorEnabled(false);
            ti_ap2.setError("Ingresa apellido materno");
        } else if (!phoneValid(phon)) {
            ti_ap2.setErrorEnabled(false);
            ti_phone.setError("Ingresa numero de telefono valido");
        } else if(!validateEmail(mai)) {
            ti_email.setError("Ingresa correo electronico valido");
            ti_phone.setErrorEnabled(false);
        }else if(pass.length() ==0) {
            ti_pass1.setError("Ingresa contraseña");
            ti_email.setErrorEnabled(false);
        }else if(pass2.length() == 0){
            ti_pass2.setError("Ingresa contraseña");
            ti_pass1.setErrorEnabled(false);
        }else if(respuesta1.length() == 0) {
            ti_res1.setError("Ingresa respuesta");
            ti_pass2.setErrorEnabled(false);
        }else if (respuesta2.length() == 0) {
            ti_res1.setErrorEnabled(false);
            ti_res2.setError("Ingresa respuesta");
        }else{
            ti_res2.setErrorEnabled(false);

            Intent sigue = new Intent(this, principalMenu.class);
            sigue.putExtra("nombre", et_nombre.getText().toString());
            sigue.putExtra("ape1", et_ap1.getText().toString());
            sigue.putExtra("ape2", et_ap2.getText().toString());
            sigue.putExtra("phone", phone.getText().toString());
            sigue.putExtra("email", mail.getText().toString());
            sigue.putExtra("password", pass.getText().toString());
            sigue.putExtra("respuesta1", respuesta1.getText().toString());
            sigue.putExtra("respuesta2", respuesta2.getText().toString());
            sigue.putExtra("pregunta1", pregunta1.getText().toString());
            sigue.putExtra("pregunta2", pregunta2.getText().toString());
            sigue.putExtra("tokenPhone", token1.getText().toString());
            startActivity(sigue);


        }
    }
}
