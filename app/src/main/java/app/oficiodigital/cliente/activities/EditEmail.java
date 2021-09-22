package app.oficiodigital.cliente.activities;

import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.regex.Pattern;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.clients.BovedaClient;
import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.notifications.LoadingDialog;
import app.oficiodigital.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditEmail extends BaseActivity {

    private EditText nuevo , confir;
    private TextView id, phone;
    private TextInputLayout ti_nuevo, ti_confir;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_email);

        nuevo = (EditText)findViewById(R.id.nuevo);
        confir = (EditText)findViewById(R.id.confirmacion);
        id = (TextView)findViewById(R.id.id);
        phone = (TextView) findViewById(R.id.phone);


        ti_nuevo = (TextInputLayout)findViewById(R.id.ti_nuevo);
        ti_confir = (TextInputLayout)findViewById(R.id.ti_confir);

        String phon = getIntent().getStringExtra("phone");
        id.setText(phon);

        confir.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String strPass1 = nuevo.getText().toString();
                String strPass2 = confir.getText().toString();
                if (strPass1.equals(strPass2)) {
                    ti_confir.setErrorEnabled(false);

                } else {
                    ti_confir.setError("correos diferentes");

                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        nuevo.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText input = ti_nuevo.getEditText();
                Pattern passw = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                String inputText = input.getText().toString().trim();
                if (passw.matcher(inputText).matches() == false) {
                    ti_nuevo.setError("Debes ingresar un correo valido");
                } else {
                    ti_nuevo.setErrorEnabled(false);

                }
            }
        });



    }

    public void cambiar(View view){

        if (nuevo.length() == 0) {
            ti_nuevo.setError("Ingresa correo");
        } else {
            ti_nuevo.setErrorEnabled(false);
            String con = nuevo.getText().toString();
            String pho = id.getText().toString();

            HashMap<String, String> params = new HashMap<>();
            params.put("email", con);
            params.put("phone",pho);

            Call<Responses> call = BovedaClient.getInstanceClient().getApiClient().updateEmail(params);
            call.enqueue(new Callback<Responses>() {
                @Override
                public void onResponse(Call<Responses> call, Response<Responses> response) {

                }

                @Override
                public void onFailure(Call<Responses> call, Throwable t) {
                    L.error("login " + t.getMessage());
                    //mPresenter.loginFailure(mContext.getString(R.string.login_error));
                }
            });

        }


        Intent intent = new Intent(this, PrincipalPerfil.class);
        intent.putExtra("phone", id.getText().toString());
        String msg = getString(R.string.EditCorreo_msj);
        LoadingDialog.show(this, msg);
        startActivity(intent);



    }

}
