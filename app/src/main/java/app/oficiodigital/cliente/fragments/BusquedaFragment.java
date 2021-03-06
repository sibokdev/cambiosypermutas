package app.oficiodigital.cliente.fragments;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.activities.AdapterUsuarios;
import app.oficiodigital.cliente.activities.Details;
import app.oficiodigital.cliente.clients.BovedaClient;
import app.oficiodigital.cliente.models.Busqueda;
import app.oficiodigital.cliente.models.Ejemplo;
import app.oficiodigital.cliente.models.ModelsDB.Phone;
import app.oficiodigital.cliente.models.Request.Estados;
import app.oficiodigital.cliente.models.Request.RespuestaPreguntaSecreta;
import app.oficiodigital.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BusquedaFragment extends Fragment implements SearchView.OnQueryTextListener{

    private RecyclerView lista;
    private TextView list1, phone, nombres;
    private SearchView buscador;
    private EditText busc;
    private Spinner spinner_estados;
    ArrayAdapter<String> adapter;
    String phon;

    String rol;
    String tipo = "";
    String nivel = "";
    String estado = "", estado2 = "";
    String roles;
    List<Busqueda> listUsuarios;
    // BusquedaCP datosEnviar = new BusquedaCP();
    List<Busqueda> ejemplo;
    List<String> list;
    AdapterUsuarios adapterUsuarios;


  //  List<Busqueda> busquedaFiltrada = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_busqueda, container, false);

        lista = (RecyclerView) view.findViewById(R.id.lista);
        nombres = (TextView) view.findViewById(R.id.nombre);
        //buscador = (SearchView) view.findViewById(R.id.buscador);
        phone = (TextView) view.findViewById(R.id.phone);

        spinner_estados = (Spinner) view.findViewById(R.id.spinner_estados);




        //child spinner process ends

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
        //  buscador.setOnQueryTextListener(this);

        return view;
    }

    public void getOficios(){

        HashMap<String, String> params = new HashMap<>();
        params.put("phone", phon);

        Call<List<Busqueda>> callVersiones = BovedaClient.getInstanceClient().getApiClient().getCp(params);
        callVersiones.enqueue(new Callback<List<Busqueda>>() {
            @Override
            public void onResponse(Call<List<Busqueda>> call, Response<List<Busqueda>> response) {
                List<Busqueda> ejemplo = response.body();

                List<String> list2 = new ArrayList<String>();
                list = new ArrayList<String>();
                List<String> listTipo = new ArrayList<String>();
                List<String> listNivel = new ArrayList<String>();
                List<String> listestado = new ArrayList<String>();

                for (Busqueda eje : ejemplo) {

                    if (eje.getEstado() != null) {
                        list.add(eje.getRol());
                        listTipo.add(eje.getTipo_plantel());
                        listNivel.add(eje.getNivel_escolar());
                        listestado.add(eje.getEstado());

                        rol = "" + list.get(0);
                        tipo = "" + listTipo.get(0);
                        nivel = "" + listNivel.get(0);


                        for (int i = 0; i < list.size(); i++) {
                            if (i == 0) {
                                estado = " " + listestado.get(i);
                            } else if (i == 1) {
                                estado2 = " " + listestado.get(i);
                            }
                        }
                    }
                }

                getdatoss(estado, estado2, tipo, nivel, rol);

            }

            @Override
            public void onFailure(Call<List<Busqueda>> call, Throwable t) {
                L.error("getOficios " + t.getMessage());
            }
        });



    }

    public void getdatoss(String e1, String e2,String ti, String ni, String ro){

        HashMap<String, String> params = new HashMap<>();
        params.put("rol", ro);
        params.put("nivel_escolar", ni);
        params.put("tipo_plantel", ti);
        //params.put("estado", estado);

        List<Estados> listaRespuestas = new ArrayList<Estados>();
        Estados respuesta = new Estados();
        respuesta.setEstado(e1);

        listaRespuestas.add(respuesta);

        Estados respuesta2 = new Estados();
        respuesta2.setEstado(e2);
        listaRespuestas.add(respuesta2);


        JSONObject jResult = new JSONObject();
        JSONArray jArray = new JSONArray();

        for (int i = 0; i < listaRespuestas.size(); i++) {
            JSONObject jGroup = new JSONObject();
            try {
                jGroup.put("estado", listaRespuestas.get(i).getEstado());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jArray.put(jGroup);
        }

        params.put("estados", jArray.toString());

        Call<List<Busqueda>> callVersiones = BovedaClient.getInstanceClient().getApiClient().getInfo(params);
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
                //  bundle.putString("phone",phone.getText().toString());
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