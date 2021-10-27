package app.oficiodigital.cliente.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.activities.AdapterTarjetas;
import app.oficiodigital.cliente.activities.AdapterUsuarios;
import app.oficiodigital.cliente.activities.AddCCPaymentActivity;
import app.oficiodigital.cliente.clients.BovedaClient;
import app.oficiodigital.cliente.clients.DOXClient;
import app.oficiodigital.cliente.models.Busqueda;
import app.oficiodigital.cliente.models.Ejemplo;
import app.oficiodigital.cliente.models.ModelsDB.DeviceId;
import app.oficiodigital.cliente.models.ModelsDB.TokenAuth;
import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.models.Tarjetas;
import app.oficiodigital.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MetodosPago extends Fragment {

    public Button agregar;
    private TextView token,num,device;
    private RecyclerView pagos;
    private String tok;

    List<Tarjetas> listUsuarios;
    AdapterTarjetas adapterUsuarios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_metodos_pago, container, false);

        //String tok = getActivity().getIntent().getExtras().getString("token");
       List<TokenAuth> list1 = TokenAuth.listAll(TokenAuth.class);
        for (TokenAuth pho : list1) {
            String phone = "";

            phone = pho.getToken();
            tok = phone;
        }

        agregar = (Button) view.findViewById(R.id.agregar);
        token = (TextView) view.findViewById(R.id.token);
        pagos = (RecyclerView) view.findViewById(R.id.lista);
        num = (TextView) view.findViewById(R.id.num);
        device = (TextView) view.findViewById(R.id.device);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        pagos.setLayoutManager(manager);

        token.setText(tok);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddCCPaymentActivity.class);
                intent.putExtra("token", token.getText().toString());
                startActivity(intent);
            }
        });

        List<TokenAuth> list = TokenAuth.listAll(TokenAuth.class);
        for (TokenAuth p : list) {
            String devi = "";

            devi = p.getToken();
            device.setText(devi);
        }


         tok = device.getText().toString();

        getTarjetas();
        return view;


    }

    public void getTarjetas() {

        String authHeader = "Bearer " + tok;

            Call<List<Tarjetas>> call = DOXClient.getInstanceClient().getApiClient().getCards2(authHeader);

            call.enqueue(new Callback<List<Tarjetas>>() {
                @Override
                public void onResponse(Call<List<Tarjetas>> call, Response<List<Tarjetas>> response) {
                    if (response.body() != null) {

                      listUsuarios = response.body();
                       adapterUsuarios = new AdapterTarjetas(listUsuarios);
                       pagos.setAdapter(adapterUsuarios);

                        }

                }

                @Override
                public void onFailure(Call<List<Tarjetas>> call, Throwable t) {
                    L.error("Get cards " + t.getMessage());
                    //mFinancialDataPresenter.onGetCardsFail(mContext.getString(R.string.error_getting_cards_list));
                }
            });
    }


}
