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

public class AddNewPhone extends BaseActivity {

    private EditText phone;
    private TextInputLayout ti_nuevo;
    private TextView id, phone2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_phone);

        phone = (EditText)findViewById(R.id.nuevo);
        ti_nuevo = (TextInputLayout)findViewById(R.id.ti_nuevo);
        id = (TextView)findViewById(R.id.id);
        phone2 = (TextView) findViewById(R.id.phone2);

        String phon2 = getIntent().getStringExtra("phone");
        phone2.setText(phon2);

        String phon = getIntent().getStringExtra("id");
        id.setText(phon);

    }

    public void cambiar(View view){

        if (phone.length() == 0) {
            ti_nuevo.setError("Ingresa telefono");
        } else {
            ti_nuevo.setErrorEnabled(false);
            String con = phone.getText().toString();

            HashMap<String, String> params = new HashMap<>();
            params.put("phone2",con);

            Call<Responses> call = BovedaClient.getInstanceClient().getApiClient().insertPhone(id.getText().toString(), params);
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

        String msg = getString(R.string.InsertarPhone_msj);
        LoadingDialog.show(this, msg);
        Intent intent = new Intent(this, PrincipalPerfil.class);
        intent.putExtra("phone", phone2.getText().toString());
        startActivity(intent);

    }
}
