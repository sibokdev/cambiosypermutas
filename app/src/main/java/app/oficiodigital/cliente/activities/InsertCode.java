package app.oficiodigital.cliente.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * Created by Ari on 19/03/2021.
 */

public class InsertCode extends BaseActivity {

    private EditText code;
    private TextView phone, token1;
    private TextInputLayout codigo;
private ImageView back;
    private TextView textView3;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_code);

        code = (EditText) findViewById(R.id.codigo);
        phone = (TextView) findViewById(R.id.phone);
        codigo = (TextInputLayout)findViewById(R.id.ti_code);
        token1 = (TextView)findViewById(R.id.token);

        textView3 = (TextView) findViewById(R.id.textView3);

        back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(getApplicationContext(), " si sale", Toast.LENGTH_SHORT).show();
                onBackPressed();// regresar a activity anterior al presionar icon back en toolbar
            }
        });


        String token = getIntent().getStringExtra("tokenPhone");
        token1.setText(token);


        String phon = getIntent().getStringExtra("phone");
        String cod = getIntent().getStringExtra("codigo");
        code.setText(cod);
        phone.setText(phon);

    }

    public void siguiente(View view) {

        String cod;
        cod = code.getText().toString();

        Pattern pat = Pattern.compile("[0-9]{6}");
        if (pat.matcher(cod).matches() == false) {
            codigo.setError("Ingresa codigo de 6 digitos");
        } else {
            codigo.setErrorEnabled(false);
            HashMap<String, String> params = new HashMap<>();
            params.put("code", cod);
            Call<Responses> call = BovedaClient.getInstanceClient().getApiClient().validate(params);
            call.enqueue(new Callback<Responses>() {
                @Override
                public void onResponse(Call<Responses> call, Response<Responses> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getCode() ==404) {
                            //LoadingDialog.show(InsertCode.this, getString(R.string.validando_code));
                            codigo.setError("Código incorrecto");
                        }
                    }
                }
                @Override
                public void onFailure(Call<Responses> call, Throwable t) {
                    L.error("login " + t.getMessage());
                   // Toast.makeText(getApplicationContext(), "codigo correcto", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(InsertCode.this, ProveedorDeServicios.class));
                    Intent inte = new Intent(InsertCode.this, Register.class);
                    inte.putExtra("tokenPhone", token1.getText().toString());
                    inte.putExtra("phone", phone.getText().toString());
                    openLoadingDialog();
                    startActivity(inte);
                }
            });
        }
    }




    public void reenviar(View view) {

        AlertDialog alertDialog = new AlertDialog.Builder(InsertCode.this).create();
        alertDialog.setTitle("Reenviar Codigo");
        alertDialog.setMessage("Deseas reenviar codigo de confirmacion");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "calcelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplication(), "Código enviado", Toast.LENGTH_SHORT).show();
                String phon = phone.getText().toString();

                  HashMap<String, String> params = new HashMap<>();
                   params.put("phone", phon);

                    Call<Responses> call = BovedaClient.getInstanceClient().getApiClient().reenviar(params);
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
        });
        alertDialog.show();

    }

    public void openLoadingDialog() {
        // textView3.setText("Validando código...");
        loadingDialog loadingDialog = new loadingDialog(this);
        loadingDialog.startLoadingDialog();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                loadingDialog.dismisDialog();
            }
        },5000); //You can change this time as you wish
    }


}

