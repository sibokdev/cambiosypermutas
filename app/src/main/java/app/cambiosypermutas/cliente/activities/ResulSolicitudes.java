package app.cambiosypermutas.cliente.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import app.cambiosypermutas.cliente.R;
import app.cambiosypermutas.cliente.clients.BovedaClient;
import app.cambiosypermutas.cliente.clients.CatalogsClient;
import app.cambiosypermutas.cliente.models.Responses;
import app.cambiosypermutas.cliente.models.Solicitudes;
import app.cambiosypermutas.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResulSolicitudes extends AppCompatActivity {

    private TextView fecha, nombre, oficio, hora, tok,id;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resul_solicitudes);

        fecha = (TextView) findViewById(R.id.fecha);
        nombre = (TextView) findViewById(R.id.nombre);
        oficio = (TextView) findViewById(R.id.oficio);
        hora = (TextView) findViewById(R.id.hora);
        tok = (TextView) findViewById(R.id.token);
        id = (TextView) findViewById(R.id.ids);


        Solicitudes fec = (Solicitudes) getIntent().getExtras().getSerializable("datos");
        fecha.setText(fec.getFecha());
        Solicitudes nom = (Solicitudes) getIntent().getExtras().getSerializable("datos");
        nombre.setText(nom.getName() + " " + nom.getSurname());
        Solicitudes hor = (Solicitudes) getIntent().getExtras().getSerializable("datos");
        hora.setText(hor.getHora());
        Solicitudes ids = (Solicitudes) getIntent().getExtras().getSerializable("datos");
        id.setText(ids.getId());

    }

    public void aceptacion(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setTitle("Comienzo cotización");
        builder.setMessage("¿El proveedor de servicios ya se encuantra en su domicilio?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String token = "fc6vqN3KTS2_yZssv8bu34:APA91bHcQJ3_hAINRfbUhVbaQ9kRxa_qzRlOITSkD6DmWdmGzilQwOGGjm-qRilnvpi0i6WDdgfY01TBdSpeb2xyCXLkdNFIWjgPQhfGySNbR7qguLxk7ncck9wbE6xhqs4vWgxc0RV6";

                String nom = nombre.getText().toString();

                HashMap<String, String> params = new HashMap<>();
                params.put("tokenPhone", token);
                params.put("name",nom);

                Call<Responses> call = CatalogsClient.getInstanceClient().getApiClient().notificacionesCoti(params);
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
                String phon = id.getText().toString();
                String sta = "En proceso";

                HashMap<String, String> params1 = new HashMap<>();

                params1.put("id",phon);
                params1.put("status",sta);

                Call<Responses> call1 = BovedaClient.getInstanceClient().getApiClient().Getsolicitud(params1);
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

                Intent intent = new Intent(getApplication(),EsperaCotizacion.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplication(),"Espera a que llegue al trabajador",Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
}

