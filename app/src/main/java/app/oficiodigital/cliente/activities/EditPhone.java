package app.oficiodigital.cliente.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.clients.BovedaClient;
import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.notifications.LoadingDialog;
import app.oficiodigital.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPhone extends BaseActivity {

    private EditText nuevo , confir, actual;
    private TextView id;
    private TextInputLayout ti_nuevo, ti_confir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_phone);

        nuevo = (EditText)findViewById(R.id.nuevo);
        actual = (EditText)findViewById(R.id.actual);
        confir = (EditText)findViewById(R.id.confirmacion);
        id = (TextView)findViewById(R.id.id);

        ti_nuevo = (TextInputLayout)findViewById(R.id.ti_nuevo);
        ti_confir = (TextInputLayout)findViewById(R.id.ti_confir);


        String idDato = getIntent().getStringExtra("id");
        id.setText(idDato);
        String phon = getIntent().getStringExtra("phone");
        actual.setText(phon);
    }

    public void cambiar(View view) {

        if (nuevo.length() == 0) {
            ti_nuevo.setError("Ingresa número de telefono");
        } else {
            ti_nuevo.setErrorEnabled(false);
            String con = nuevo.getText().toString();
            String pho = id.getText().toString();

            HashMap<String, String> params = new HashMap<>();
            params.put("phone", con);
            params.put("id", pho);

            Call<Responses> call = BovedaClient.getInstanceClient().getApiClient().updatePhone(params);
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

            String msg = getString(R.string.EditCorreo_msj);
            LoadingDialog.show(this, msg);
            Intent intent = new Intent(getApplication(), PrincipalPerfil.class);
            intent.putExtra("phone", nuevo.getText().toString());
            startActivity(intent);
        }
    }
}
