package app.cambiosypermutas.cliente.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import app.cambiosypermutas.cliente.R;
import app.cambiosypermutas.cliente.activities.AdapterTriangulacion;
import app.cambiosypermutas.cliente.activities.AdapterUsuarios;
import app.cambiosypermutas.cliente.clients.BovedaClient;
import app.cambiosypermutas.cliente.models.Busqueda;
import app.cambiosypermutas.cliente.models.Ejemplo;
import app.cambiosypermutas.cliente.models.ModelsDB.Phone;
import app.cambiosypermutas.cliente.models.Request.Estados;
import app.cambiosypermutas.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BusquedaTriangulacion extends Fragment implements SearchView.OnQueryTextListener{

    private RecyclerView lista;
    private TextView list1, phone, nombres, estados, resultados,noresultados;
    private SearchView buscador;
    private EditText busc;
    private Spinner spinner_estados,sp_child;
    private ProgressBar progressBar2;
    ArrayAdapter<String> adapter;
    String phon,c1,c2,c3,tel;


    private ImageView imagenSinConexion;

    private RecyclerView datosescuela;
    private CardView noti_inter;


    String rol;
    String tipo = "";
    String nivel = "";
    String estado = "", estado2 = "", estado3 = "";
    String roles;
    List<Busqueda> listUsuarios;
    // BusquedaCP datosEnviar = new BusquedaCP();
    List<Busqueda> ejemplo;
    List<String> list;
    AdapterTriangulacion adapterUsuarios;


  //  List<Busqueda> busquedaFiltrada = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_busqueda_triangulacion, container, false);

        lista = (RecyclerView) view.findViewById(R.id.lista);
        nombres = (TextView) view.findViewById(R.id.nombre);
       // buscador = (SearchView) view.findViewById(R.id.buscador);
        phone = (TextView) view.findViewById(R.id.phone);
        estados = (TextView) view.findViewById(R.id.estado);
        resultados = (TextView) view.findViewById(R.id.resultados);
        noresultados=(TextView) view.findViewById(R.id.noresultados);
        progressBar2 = (ProgressBar) view.findViewById(R.id.progressBar2);

       // sp_parent = (Spinner) view.findViewById(R.id.sp_parent);//relacion spinnner
        sp_child = (Spinner) view.findViewById(R.id.sp_child);//relacion spinnner

        c1 = getActivity().getIntent().getStringExtra("codigo1");
        c2 = getActivity().getIntent().getStringExtra("codigo2");
        c3 = getActivity().getIntent().getStringExtra("codigo3");
        tel = getActivity().getIntent().getStringExtra("phone");

              //  String codigo = codigop4.getText().toString();



        datosescuela = (RecyclerView) view.findViewById(R.id.lista);
        noti_inter = (CardView) view.findViewById(R.id.noti_inter);

        imagenSinConexion = (ImageView) view.findViewById(R.id.imagenSinConexion);
        imagenSinConexion.setVisibility(View.INVISIBLE);

        ConnectivityManager con = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = con.getActiveNetworkInfo();

        if (networkInfo!=null && networkInfo.isConnected()) {
            datosescuela.setVisibility(View.VISIBLE);
            noti_inter.setVisibility(View.INVISIBLE);

        }else {
            noti_inter.setVisibility(View.VISIBLE);
            datosescuela.setVisibility(View.INVISIBLE);
            imagenSinConexion.setVisibility(View.VISIBLE);
            //mensaje
            Toast.makeText(getContext(), "No se ha podido establecer la conexión a internet, verifique el acceso a internet e intentelo nuevamente", Toast.LENGTH_SHORT).show();
        }

        //array filtro estados
       // arrayList_parent=new ArrayList<>();
       // arrayList_parent.add("Estados");

       // arrayAdapter_parent = new ArrayAdapter<>(getActivity().getApplication(), android.R.layout.simple_selectable_list_item,arrayList_parent);
       // sp_parent.setAdapter(arrayAdapter_parent);


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
        getEstados();
        //buscador.setOnQueryTextListener(this);
        sp_child.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String slect = sp_child.getSelectedItem().toString();
                int sele = sp_child.getSelectedItemPosition();
                String se = "" + sele;
                estados.setText(slect);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return view;
    }

    public void getEstados(){
        List<Phone> list1 = Phone.listAll(Phone.class);
        String phone = "";
        for (Phone pho : list1) {

            phone = pho.getPhone();

        }
        String phon = phone;
       // noresultados.setVisibility(View.VISIBLE);
        progressBar2.setVisibility(View.VISIBLE);
        Call<List<app.cambiosypermutas.cliente.models.Estados>> call = BovedaClient.getInstanceClient().getApiClient().getEstados(tel);
        call.enqueue(new Callback<List<app.cambiosypermutas.cliente.models.Estados>>() {
            @Override
            public void onResponse(Call<List<app.cambiosypermutas.cliente.models.Estados>> call, Response<List<app.cambiosypermutas.cliente.models.Estados>> response) {
                List<app.cambiosypermutas.cliente.models.Estados> ejemplo = response.body();
                List<String> list = new ArrayList<String>();
                String estado1 ="", estado2 = "", estado3 = "", estadoss = "";
                String[] estados = new String[0];

                for (app.cambiosypermutas.cliente.models.Estados estado : ejemplo) {
                    list.add(estado.getEstado());
                    noresultados.setVisibility(View.GONE);
                   for (int i = 0; i < list.size(); i++) {
                        if (i == 2) {

                            if(list.get(0).contains(list.get(1)) && list.get(0).contains(list.get(2))){
                                estado1 = list.get(0);
                                estados = new String[]{"Todos", estado1};
                            }else if(list.get(1).contains(list.get(2))){
                                //interes.setText(list.get(0) + " " + list.get(1));
                                estado1 = list.get(0);
                                estado3 = list.get(2);
                                estados = new String[]{"Todos", estado1, estado3};
                            }else if(list.get(0).contains(list.get(1))){
                                estado2 = list.get(1);
                                estado3 = list.get(2);
                                estados = new String[]{"Todos", estado2, estado3};
                            }else if(list.get(0).contains(list.get(2))){
                                estado1 = list.get(0);
                                estado2 = list.get(1);
                                estados = new String[]{"Todos", estado1, estado2};
                            }else{
                               
                                estado1 = list.get(0);
                                estado2 = list.get(1);
                                estado3 = list.get(2);

                                estados = new String[]{"Todos", estado1, estado2, estado3};
                            }

                        }
                    }
                     try{
                         ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_colonia, estados);
                         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                         sp_child.setAdapter(adapter);
                     }catch (Exception e){

                     }


                }


            }

            @Override
            public void onFailure(Call<List<app.cambiosypermutas.cliente.models.Estados>> call, Throwable t) {
                progressBar2.setVisibility(View.GONE);
                noresultados.setVisibility(View.VISIBLE);
                // Toast.makeText(getApplicationContext(), "Telefono guardado", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(InsertCode.this, ProveedorDeServicios.class));

            }
        });

    }

    public void getOficios(){

        HashMap<String, String> params = new HashMap<>();
        params.put("phone", tel);

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
                            }else if (i == 2) {
                                estado3 = " " + listestado.get(i);
                            }
                        }
                    }
                }

                getdatoss(estado, estado2, estado3, tipo, nivel, rol);

            }

            @Override
            public void onFailure(Call<List<Busqueda>> call, Throwable t) {
                L.error("getOficios " + t.getMessage());
            }
        });



    }

    public void getdatoss(String e1, String e2, String e3,String ti, String ni, String ro){

        List<Phone> list1 = Phone.listAll(Phone.class);
        String phone = "";
        for (Phone pho : list1) {

            phone = pho.getPhone();

        }
        String phon = phone;


        HashMap<String, String> params = new HashMap<>();
        params.put("rol", ro);
        params.put("nivel_escolar", ni);
        params.put("tipo_plantel", ti);
        params.put("phone", phon);

        List<Estados> listaRespuestas = new ArrayList<Estados>();

        Estados respuesta = new Estados();
        respuesta.setEstado(e1);
        listaRespuestas.add(respuesta);

        Estados respuesta2 = new Estados();
        respuesta2.setEstado(e2);
        listaRespuestas.add(respuesta2);

        Estados respuesta3 = new Estados();
        respuesta3.setEstado(e3);
        listaRespuestas.add(respuesta3);

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

        Call<List<Busqueda>> callVersiones = BovedaClient.getInstanceClient().getApiClient().getInfo2(params);
        callVersiones.enqueue(new Callback<List<Busqueda>>() {
            @Override
            public void onResponse(Call<List<Busqueda>> call, Response<List<Busqueda>> response) {

                listUsuarios = response.body();

                if (listUsuarios.size() == 0) {
                    resultados.setVisibility(View.VISIBLE);
                    sp_child.setClickable(false);
                    noresultados.setVisibility(View.GONE);
                    progressBar2.setVisibility(View.GONE);

                }else{
                    progressBar2.setVisibility(View.GONE);
                    estados.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                            adapterUsuarios.filtrar(s);
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });


                    adapterUsuarios = new AdapterTriangulacion(listUsuarios);

                    BusquedaTriangulacion f = new BusquedaTriangulacion();
                    Bundle bundle = new Bundle();
                    //  bundle.putString("phone",phone.getText().toString());
                    f.setArguments(bundle);
                    progressBar2.setVisibility(View.GONE);
                    noresultados.setVisibility(View.GONE);
                    lista.setAdapter(adapterUsuarios);


                }

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

        return false;
    }
}