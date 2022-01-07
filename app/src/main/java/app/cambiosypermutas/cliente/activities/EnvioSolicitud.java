package app.cambiosypermutas.cliente.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import app.cambiosypermutas.cliente.R;
import app.cambiosypermutas.cliente.clients.BovedaClient;
import app.cambiosypermutas.cliente.clients.CatalogsClient;
import app.cambiosypermutas.cliente.models.Busqueda;
import app.cambiosypermutas.cliente.models.ModelsDB.TokenAuth;
import app.cambiosypermutas.cliente.models.Responses;
import app.cambiosypermutas.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EnvioSolicitud extends BaseActivity {
    private TextView nombre, oficio,token1, ap1, ap2, hora, fecha,phone,phone2,email;
    private Busqueda busqueda;
    private String tok;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio_solicitud);
        nombre = (TextView) findViewById(R.id.nombre);
        oficio = (TextView) findViewById(R.id.oficio);
        token1 = (TextView) findViewById(R.id.token);
        ap1 = (TextView) findViewById(R.id.ap1);
        ap2 = (TextView) findViewById(R.id.ap2);
        hora = (TextView) findViewById(R.id.hora);
        fecha = (TextView) findViewById(R.id.fecha);
        phone = (TextView) findViewById(R.id.phone);
        phone2 = (TextView) findViewById(R.id.phone2);
        email = (TextView) findViewById(R.id.email);

        String dato = getIntent().getStringExtra("oficio");
        oficio.setText(dato);
        String nom = getIntent().getStringExtra("nombre");
        nombre.setText(nom);
        String ape1 = getIntent().getStringExtra("ap1");
        ap1.setText(ape1);
        String ape2 = getIntent().getStringExtra("ap2");
        ap2.setText(ape2);
        String token = getIntent().getStringExtra("tokenPhone");
        token1.setText(token);
        String fe = getIntent().getStringExtra("fecha");
        fecha.setText(fe);
        String hor = getIntent().getStringExtra("hora");
        hora.setText(hor);
        String phon = getIntent().getStringExtra("phone");
        phone.setText(phon);
        String phonCliente = getIntent().getStringExtra("phoneCliente");
        phone2.setText(phonCliente);
        String ema = getIntent().getStringExtra("email");
        email.setText(ema);



    }
    public void cancelar(View view){

        Intent intent = new Intent(getApplication(), principalMenu.class);
        intent.putExtra("phone", email.getText().toString());
        intent.putExtra("token", tok);
        startActivity(intent);

    }

    public void aceptar(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setTitle("Enviar Solicitud");
        builder.setMessage("Se ha envida la solicitud al trabajador");
        builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String token = token1.getText().toString();
                String nom = nombre.getText().toString();

                HashMap<String, String> params = new HashMap<>();
                params.put("tokenPhone", token);
                params.put("name",nom);

                Call<Responses> call = CatalogsClient.getInstanceClient().getApiClient().notificaciones(params);
                call.enqueue(new Callback<Responses>() {

                    @Override
                    public void onResponse(Call<Responses> call, Response<Responses> response) {
                        if (response.isSuccessful()) {

                        }
                    }

                    @Override
                    public void onFailure(Call<Responses> call, Throwable t) {
                        L.error("login " + t.getMessage());

                    }
                });

                String con = nombre.getText().toString();
                String pho = ap1.getText().toString();
                String hor = hora.getText().toString();
                String fe = fecha.getText().toString();
                String pho1 = phone.getText().toString();
                String phonCliente = email.getText().toString();
                String ofi = oficio.getText().toString();

                HashMap<String, String> params1 = new HashMap<>();
                params1.put("fecha", fe);
                params1.put("hora", hor);
                params1.put("name", con);
                params1.put("surname",pho);
                params1.put("phone",pho1);
                params1.put("tokenPhone",token);
                params1.put("phoneCliente",phonCliente);
                params1.put("oficio",ofi);


                Call<Responses> call1 = BovedaClient.getInstanceClient().getApiClient().solicitud(params1);
                call1.enqueue(new Callback<Responses>() {
                    @Override
                    public void onResponse(Call<Responses> call, Response<Responses> response) {

                    }

                    @Override
                    public void onFailure(Call<Responses> call, Throwable t) {
                        L.error("login " + t.getMessage());
                        //mPresenter.loginFailure(mContext.getString(R.string.login_error));
                    }
                });

                List<TokenAuth> list1 = TokenAuth.listAll(TokenAuth.class);
                for (TokenAuth p : list1) {
                    String phone = "";

                    phone = p.getTokenauth();
                    tok = phone;
                }


                Intent intent = new Intent(getApplication(), principalMenu.class);
                intent.putExtra("phone", email.getText().toString());
                intent.putExtra("token", tok);
                startActivity(intent);
            }
        });

        builder.show();


    }




}
