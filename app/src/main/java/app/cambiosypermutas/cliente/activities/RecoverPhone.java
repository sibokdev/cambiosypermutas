package app.cambiosypermutas.cliente.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.regex.Pattern;

import app.cambiosypermutas.cliente.R;
import app.cambiosypermutas.cliente.clients.BovedaClient;
import app.cambiosypermutas.cliente.models.Responses;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ari on 11/04/2021.
 */

public class RecoverPhone extends BaseActivity {

    private EditText phone;
    private TextInputLayout pho;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover);

        phone = (EditText)findViewById(R.id.phone);
        pho = (TextInputLayout)findViewById(R.id.ti_phone);
        back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(getApplicationContext(), " si sale", Toast.LENGTH_SHORT).show();
                onBackPressed();// regresar a activity anterior al presionar icon back en toolbar
            }
        });
    }

    public void enviar(View view){
        String phon;
        phon = phone.getText().toString();

        Pattern pat = Pattern.compile("[0-9]{10}");

        if (pat.matcher(phon).matches() == false) {
            pho.setError("Ingrese teléfono correctamente");
        } else {
            pho.setErrorEnabled(false);

            HashMap<String, String> params = new HashMap<>();
            params.put("phone", phon);

            Call<Responses> call = BovedaClient.getInstanceClient().getApiClient().recover(params);
            call.enqueue(new Callback<Responses>() {
                @Override
                public void onResponse(Call<Responses> call, Response<Responses> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getCode() == 404) {
                            //LoadingDialog.show(InsertCode.this, getString(R.string.validando_code));
                            pho.setError("   Número no existente");
                        }else{
                            openLoadingDialog();
                            Intent inte = new Intent(RecoverPhone.this, RecoverCode.class);
                            inte.putExtra("phone", phone.getText().toString());

                            startActivity(inte);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Responses> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Telefono guardado", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(InsertCode.this, ProveedorDeServicios.class));

                }
            });

        }

    }
    public void openLoadingDialog() {
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
