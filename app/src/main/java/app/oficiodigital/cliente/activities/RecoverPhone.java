package app.oficiodigital.cliente.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.regex.Pattern;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.clients.BovedaClient;
import app.oficiodigital.cliente.models.Responses;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ari on 11/04/2021.
 */

public class RecoverPhone extends BaseActivity {

    private EditText phone;
    private TextInputLayout pho;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover);

        phone = (EditText)findViewById(R.id.phone);
        pho = (TextInputLayout)findViewById(R.id.ti_phone);

    }

    public void enviar(View view){
        String phon;
        phon = phone.getText().toString();

        Pattern pat = Pattern.compile("[0-9]{10}");

        if (pat.matcher(phon).matches() == false) {
            pho.setError("Ingrese telefono correctamente");
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
                            pho.setError("Numero no existente");
                        }else{
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

}
