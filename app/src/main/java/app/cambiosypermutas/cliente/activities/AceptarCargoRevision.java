package app.cambiosypermutas.cliente.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import app.cambiosypermutas.cliente.R;
import app.cambiosypermutas.cliente.clients.DOXClient;
import app.cambiosypermutas.cliente.models.Responses;
import app.cambiosypermutas.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AceptarCargoRevision extends AppCompatActivity {

    private TextView api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aceptar_cargo_revision);

        api = (TextView) findViewById(R.id.api);
    }

    public void cargo(View view){
        String deviceSessionId = "6ad429f2ec0cAe3d89cb32ef07738b4f";
        String mToken = "Bearer " + "Mvkay0u4FLpYBQrraVbyl8056c7uFgEihPIuUy3O";

        Call<Responses> call = DOXClient.getInstanceClient().getApiClient()
                .addPayment(deviceSessionId, mToken);

        call.enqueue(new Callback<Responses>() {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                       // mAddPaymentPresenter.onAddPayment();
                        Toast.makeText(getApplication(),"success",Toast.LENGTH_SHORT).show();
                    } else {
                       // mAddPaymentPresenter.onAddPaymentFail(response.body().getMessage());
                        Toast.makeText(getApplication(),"ocurrio un error al realizar el pago",Toast.LENGTH_SHORT).show();
                    }
                } else{

                }
                   // mAddPaymentPresenter.onAddPaymentFail(mContext.getString(R.string.error_doing_payment));
                Toast.makeText(getApplication(),"no hay tarjetas",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                L.error("Add payment " + t.getMessage());
                //mAddPaymentPresenter.onAddPaymentFail(mContext.getString(R.string.error_doing_payment));
            }
        });
    }
}