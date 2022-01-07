package app.cambiosypermutas.cliente.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.regex.Pattern;

import app.cambiosypermutas.cliente.R;
import app.cambiosypermutas.cliente.clients.BovedaClient;
import app.cambiosypermutas.cliente.models.Responses;
import app.cambiosypermutas.cliente.notifications.LoadingDialog;
import app.cambiosypermutas.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ari on 13/04/2021.
 */

public class ChangePassword extends BaseActivity{

    private TextView phone;
    private EditText contra,confir;
    private TextInputLayout ti_pass, ti_con;
    private ImageView back;
    String phon = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        phone = (TextView)findViewById(R.id.phone);
        contra = (EditText)findViewById(R.id.password);
        confir = (EditText)findViewById(R.id.conformacion);
        ti_pass = (TextInputLayout)findViewById(R.id.ti_pass);
        ti_con = (TextInputLayout)findViewById(R.id.ti_confir);

        back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(getApplicationContext(), " si sale", Toast.LENGTH_SHORT).show();
                onBackPressed();// regresar a activity anterior al presionar icon back en toolbar
            }
        });

        phon = getIntent().getStringExtra("phone");

        phone.setText(phon);


        confir.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String strPass1 = contra.getText().toString();
                String strPass2 = confir.getText().toString();
                if (strPass1.equals(strPass2)) {
                    ti_con.setErrorEnabled(false);

                } else {
                    ti_con.setError("contraseñas diferentes");

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        contra.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText input = ti_pass.getEditText();
                Pattern passw = Pattern.compile("[A-Z-a-z-0-9]{8,100}");
                String inputText = input.getText().toString().trim();
                if (passw.matcher(inputText).matches() == false) {
                    ti_pass.setError("Debes ingresar al menos 8 caracteres");
                } else {
                    ti_pass.setErrorEnabled(false);

                }
            }
        });

    }

    public void cabiar(View view) {

        if (contra.length() == 0) {
            ti_pass.setError("Inserta Contraseña");
        } else {
            ti_pass.setErrorEnabled(false);
            String con = contra.getText().toString();

            HashMap<String, String> params = new HashMap<>();
            params.put("password", con);
            params.put("phone",phon);

            Call<Responses> call = BovedaClient.getInstanceClient().getApiClient().updatePass(params);
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


            Intent inte = new Intent(this, LoginActivity.class);
            //alerta();
            openLoadingDialog();
            startActivity(inte);
        }
    }

    private void openLoadingDialog() {
        loadingDialog loadingDialog = new loadingDialog(this);
        loadingDialog.startLoadingDialog();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                loadingDialog.dismisDialog();
                Toast.makeText(getApplication(),"Se ha actualizado contraseña", Toast.LENGTH_SHORT).show();
            }
        },5000); //You can change this time as you wish
    }

    private void alerta() {
        String msg = getString(R.string.contra_actu);
        LoadingDialog.show(getApplication(), msg);
    }

}