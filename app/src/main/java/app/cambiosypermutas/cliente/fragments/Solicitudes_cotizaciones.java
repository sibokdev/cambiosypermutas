package app.cambiosypermutas.cliente.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.cambiosypermutas.cliente.R;
import app.cambiosypermutas.cliente.activities.AdapterSolicitudes;
import app.cambiosypermutas.cliente.clients.BovedaClient;
import app.cambiosypermutas.cliente.models.ModelsDB.Phone;
import app.cambiosypermutas.cliente.models.Solicitudes;
import app.cambiosypermutas.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Solicitudes_cotizaciones extends Fragment {
    private RecyclerView lista;
    private TextView list1, phone, nombres,escuela;
    private SearchView buscador;
    private EditText busc;
    ArrayAdapter<String> adapter;
    String phon;

    List<Solicitudes> listUsuarios;
    AdapterSolicitudes adapterUsuarios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_solicitudes_cotizaciones, container, false);
        lista = (RecyclerView) view.findViewById(R.id.lista);
        nombres = (TextView) view.findViewById(R.id.nombre);
        phone = (TextView) view.findViewById(R.id.phone);
        escuela = (TextView) view.findViewById(R.id.nombreE) ;


        List<Phone> list1 = Phone.listAll(Phone.class);
        for (Phone pho : list1) {
            String phone = "";

            phone = pho.getPhone();
            phon = phone;
        }

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        lista.setLayoutManager(manager);

        getOficios();
       /* getDataSchool();*/

        return view;
    }

    public void getOficios(){



        //String phon = "2461633409";
        Call<List<Solicitudes>> callVersiones = BovedaClient.getInstanceClient().getApiClient().getSolicitudes(phon);
        callVersiones.enqueue(new Callback<List<Solicitudes>>() {
            @Override
            public void onResponse(Call<List<Solicitudes>> call, Response<List<Solicitudes>> response) {

                if (!response.isSuccessful()) {
                    return;
                }

                listUsuarios = response.body();
                adapterUsuarios = new AdapterSolicitudes(listUsuarios);

                lista.setAdapter(adapterUsuarios);
            }

            @Override
            public void onFailure(Call<List<Solicitudes>> call, Throwable t) {
                L.error("getOficios " + t.getMessage());
            }
        });

    }

/*    public void getDataSchool(){

        //String phon = "2461633409";
        Call<List<DatosSchool>> callVersiones = BovedaClient.getInstanceClient().getApiClient().getDataSchool(phone.getText().toString());
        callVersiones.enqueue(new Callback<List<DatosSchool>>() {
            @Override
            public void onResponse(Call<List<DatosSchool>> call, Response<List<DatosSchool>> response) {

                if (!response.isSuccessful()) {
                    return;
                }

                List<DatosSchool> respuestas = response.body();
                List<String> list = new ArrayList<String>();

                for (DatosSchool res : respuestas) {

                    String nombre = "";
                    String codigop = "";
                    String direc = " ";

                    *//*nombre = "" + res.getEscuela();
                    escuela.setText(nombre);
                    codigop = res.getCP();*//*


                }
            }
            @Override
            public void onFailure(Call<List<DatosSchool>> call, Throwable t) {
                L.error("getDataSchool " + t.getMessage());
            }
        });

    }*/
}