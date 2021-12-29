package app.oficiodigital.cliente.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

    private EditText nuevo, actual, confirmacion;
    private TextInputLayout ti_nuevo;
    private TextView id, phone2;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_phone);

        nuevo = (EditText)findViewById(R.id.nuevo);
        actual = (EditText) findViewById(R.id.actual);
        ti_nuevo = (TextInputLayout)findViewById(R.id.ti_nuevo);
        id = (TextView)findViewById(R.id.id);
        phone2 = (TextView) findViewById(R.id.phone2);


        back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(getApplicationContext(), " si sale", Toast.LENGTH_SHORT).show();
                onBackPressed();// regresar a activity anterior al presionar icon back en toolbar
            }
        });

        String phon2 = getIntent().getStringExtra("phone");
        actual.setText(phon2);

        String phon = getIntent().getStringExtra("id");
        id.setText(phon);

    }

    public void cambiar(View view){


        if (nuevo.length() == 0) {
            ti_nuevo.setError("Ingresa número de telefono");
        } else {
            ti_nuevo.setErrorEnabled(false);
            String con = nuevo.getText().toString();
            String pho = id.getText().toString();

            HashMap<String, String> params = new HashMap<>();
            params.put("phone", con);
            params.put("id", pho);

            Call<Responses> call = BovedaClient.getInstanceClient().getApiClient().updatePhone2(params);
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
