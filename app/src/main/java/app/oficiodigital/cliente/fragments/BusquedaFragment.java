package app.oficiodigital.cliente.fragments;

import android.content.Intent;
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

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.activities.AdapterUsuarios;
import app.oficiodigital.cliente.activities.Details;
import app.oficiodigital.cliente.clients.BovedaClient;
import app.oficiodigital.cliente.models.Busqueda;
import app.oficiodigital.cliente.models.ModelsDB.Phone;
import app.oficiodigital.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BusquedaFragment extends Fragment implements SearchView.OnQueryTextListener{

    private RecyclerView lista;
    private TextView list1, phone, nombres;
    private SearchView buscador;
    private EditText busc;
      ArrayAdapter<String> adapter;
      String phon;

    List<Busqueda> listUsuarios;
    AdapterUsuarios adapterUsuarios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_busqueda, container, false);

        lista = (RecyclerView) view.findViewById(R.id.lista);
        nombres = (TextView) view.findViewById(R.id.nombre);
        buscador = (SearchView) view.findViewById(R.id.buscador);
        phone = (TextView) view.findViewById(R.id.phone);


        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        lista.setLayoutManager(manager);

         //phon = getActivity().getIntent().getExtras().getString("phone");
        List<Phone> list1 = Phone.listAll(Phone.class);
        for (Phone pho : list1) {
            String phone = "";

            phone = pho.getPhone();
            phon = phone;
        }
        phone.setText(phon);


        getOficios();
        // listUsuarios = GetData();
    //adapterUsuarios = new AdapterUsuarios(listUsuarios);
      //  lista.setAdapter(adapterUsuarios);


         //adapterUsuarios = new AdapterUsuarios(getActivity(), GetData());
        //lista.setAdapter(adapterUsuarios);

        buscador.setOnQueryTextListener(this);

        return view;
    }
    /*private List<Busqueda>  GetData() {
        List<Busqueda> bus  = new ArrayList<>();

        bus.add(new Busqueda("ARIANA TEPEPA CERVANTES","plomero"));
        bus.add(new Busqueda("bere","carpintero"));
        bus.add(new Busqueda("daniel","bombero"));
        bus.add(new Busqueda("esteban","lechero"));
        bus.add(new Busqueda("yoshio","mecanico"));

        return bus;
    }*/

    public void getOficios(){

        Call<List<Busqueda>> callVersiones = BovedaClient.getInstanceClient().getApiClient().getOficios();
        callVersiones.enqueue(new Callback<List<Busqueda>>() {
            @Override
            public void onResponse(Call<List<Busqueda>> call, Response<List<Busqueda>> response) {

                if (!response.isSuccessful()) {
                    return;
                }



                listUsuarios = response.body();

               adapterUsuarios = new AdapterUsuarios(listUsuarios);

                BusquedaFragment f = new BusquedaFragment();
                Bundle bundle = new Bundle();
                bundle.putString("phone",phone.getText().toString());
                f.setArguments(bundle);

                lista.setAdapter(adapterUsuarios);
            }

            @Override
            public void onFailure(Call<List<Busqueda>> call, Throwable t) {
                L.error("getOficios " + t.getMessage());
            }
        });

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        //adapterUsuarios.filtrar(newText);
        return false;
    }
}
