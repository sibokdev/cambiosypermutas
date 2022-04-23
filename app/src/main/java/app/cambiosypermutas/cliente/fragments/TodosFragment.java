package app.cambiosypermutas.cliente.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.Touch;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.cambiosypermutas.cliente.R;
import app.cambiosypermutas.cliente.activities.AdapterTodos;
import app.cambiosypermutas.cliente.activities.AdapterUsuarios;
import app.cambiosypermutas.cliente.activities.PrincipalSolicitud;
import app.cambiosypermutas.cliente.clients.BovedaClient;
import app.cambiosypermutas.cliente.contracts.AddPaymentContract;
import app.cambiosypermutas.cliente.models.Busqueda;
import app.cambiosypermutas.cliente.models.Datos;
import app.cambiosypermutas.cliente.models.ModelsDB.Phone;
import app.cambiosypermutas.cliente.models.Request.Estados;
import app.cambiosypermutas.cliente.models.Responses;
import app.cambiosypermutas.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TodosFragment extends Fragment implements SearchView.OnQueryTextListener{

    private RecyclerView lista;
    private TextView list1, phone, nombres, estados, resultados,noresultados,a,t,vpunto,punto;
    private SearchView buscador;
    private EditText busc;
    private Spinner spinner_estados,sp_child;
    private ProgressBar progressBar2;
    ArrayAdapter<String> adapter;
    String phon;
    private Paint mPaint;
    private Paint mPaint2;
    Button publi;

    private ImageView imagenSinConexion;
    private RewardedAd mRewardedAd;
    private final String TAG = "MainActivity";
    private RecyclerView datosescuela;
    private CardView noti_inter;
    String puntos="";

    String rol;
    String tipo = "";
    String nivel = "";
    String estado = "", estado2 = "", estado3 = "";
    String roles;
    List<Busqueda> listUsuarios;
    // BusquedaCP datosEnviar = new BusquedaCP();
    List<Busqueda> ejemplo;
    List<String> list;
    AdapterTodos adapterUsuarios;


    //  List<Busqueda> busquedaFiltrada = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todos, container, false);

        lista = (RecyclerView) view.findViewById(R.id.lista);
        nombres = (TextView) view.findViewById(R.id.nombre);
        buscador = (SearchView) view.findViewById(R.id.buscador);
        phone = (TextView) view.findViewById(R.id.phone);
        estados = (TextView) view.findViewById(R.id.estado);
        resultados = (TextView) view.findViewById(R.id.resultados);
        noresultados=(TextView) view.findViewById(R.id.noresultados);
        progressBar2 = (ProgressBar) view.findViewById(R.id.progressBar2);
        publi = (Button) view.findViewById(R.id.publicidad);
        punto = (TextView) view.findViewById(R.id.puntos);
        vpunto = (TextView) view.findViewById(R.id.punto);

       // sp_parent = (Spinner) view.findViewById(R.id.sp_parent);//relacion spinnner
        sp_child = (Spinner) view.findViewById(R.id.sp_child);//relacion spinnner

//        datosescuela = (RecyclerView) view.findViewById(R.id.lista);
        noti_inter = (CardView) view.findViewById(R.id.noti_inter);

        imagenSinConexion = (ImageView) view.findViewById(R.id.imagenSinConexion);
        imagenSinConexion.setVisibility(View.INVISIBLE);

        publi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowRewardAd();
                onRequestAd();
            }
        });


        ConnectivityManager con = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = con.getActiveNetworkInfo();

        if (networkInfo!=null && networkInfo.isConnected()) {
           // datosescuela.setVisibility(View.VISIBLE);
            noti_inter.setVisibility(View.INVISIBLE);

        }else {
            noti_inter.setVisibility(View.VISIBLE);
          //  datosescuela.setVisibility(View.INVISIBLE);
            imagenSinConexion.setVisibility(View.VISIBLE);
            //mensaje
            Toast.makeText(getContext(), "No se ha podido establecer la conexión a internet, verifique el acceso a internet e intentelo nuevamente", Toast.LENGTH_SHORT).show();
        }

        //array filtro estados
       // arrayList_parent=new ArrayList<>();
       // arrayList_parent.add("Estados");

       // arrayAdapter_parent = new ArrayAdapter<>(getActivity().getApplication(), android.R.layout.simple_selectable_list_item,arrayList_parent);
       // sp_parent.setAdapter(arrayAdapter_parent);

        getdatoss();
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
        buscador.setOnQueryTextListener(this);

        //getPuntos();
        return view;
    }
    public void getPuntos(){
        List<Phone> list1 = Phone.listAll(Phone.class);
        String phone = "";
        for (Phone pho : list1) {

            phone = pho.getPhone();

        }
        phon = phone;
        Call<List<Datos>> callVersiones = BovedaClient.getInstanceClient().getApiClient().getPuntos(phon);
        callVersiones.enqueue(new Callback<List<Datos>>() {
            @Override
            public void onResponse(Call<List<Datos>> call, Response<List<Datos>> response) {
                List<Datos> ejemplo = response.body();
                List<String> Puntos = new ArrayList<String>();

                for (Datos eje : ejemplo) {
                    Puntos.add(eje.getPuntos());
                    puntos = Puntos.get(0);
                    vpunto.setText(puntos);
                }

                if(puntos.equals("0")) {
                    Toast.makeText(getContext(), "No tiene puntos", Toast.LENGTH_SHORT).show();
                    lista.setVisibility(View.GONE);
                    punto.setVisibility(View.VISIBLE);
                    buscador.setVisibility(View.GONE);
                }else{
                    int punto =  Integer.valueOf(puntos);
                    int resultado = punto - 50;
                    // Handle the reward.
                    HashMap<String, String> params = new HashMap<>();
                    params.put("phone",phon);
                    params.put("puntos", String.valueOf(resultado));

                    Call<Responses> callVersiones = BovedaClient.getInstanceClient().getApiClient().updatePuntos(params);
                    callVersiones.enqueue(new Callback<Responses>() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onResponse(Call<Responses> call, Response<Responses> response) {

                        }

                        @Override
                        public void onFailure(Call<Responses> call, Throwable t) {

                        }

                    });

                }

            }

            @Override
            public void onFailure(Call<List<Datos>> call, Throwable t) {
                L.error("getOficios " + t.getMessage());
            }
        });
    }

    public void getEstados(){
        List<Phone> list1 = Phone.listAll(Phone.class);
        String phone="";
        for (Phone pho : list1) {

            phone = pho.getPhone();

        }
        String phon = phone;
       // noresultados.setVisibility(View.VISIBLE);
        progressBar2.setVisibility(View.VISIBLE);
        Call<List<app.cambiosypermutas.cliente.models.Estados>> call = BovedaClient.getInstanceClient().getApiClient().getEstados(phon);
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
                            }else if (i == 2) {
                                estado3 = " " + listestado.get(i);
                            }
                        }
                    }
                }

                //getdatoss(estado, estado2, estado3, tipo, nivel, rol);

            }

            @Override
            public void onFailure(Call<List<Busqueda>> call, Throwable t) {
                L.error("getOficios " + t.getMessage());
            }
        });



    }

    public void getdatoss(){
        List<Phone> list1 = Phone.listAll(Phone.class);
        String phone="";
        for (Phone pho : list1) {

            phone = pho.getPhone();

        }
        String phon = phone;

        Call<List<Busqueda>> callVersiones = BovedaClient.getInstanceClient().getApiClient().getTodos(phon);
        callVersiones.enqueue(new Callback<List<Busqueda>>() {
            @Override
            public void onResponse(Call<List<Busqueda>> call, Response<List<Busqueda>> response) {

                listUsuarios = response.body();
                    progressBar2.setVisibility(View.GONE);

                    adapterUsuarios = new AdapterTodos(listUsuarios);

                    TodosFragment f = new TodosFragment();
                    Bundle bundle = new Bundle();
                    //  bundle.putString("phone",phone.getText().toString());
                    f.setArguments(bundle);
                    progressBar2.setVisibility(View.GONE);
                    noresultados.setVisibility(View.GONE);
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
        adapterUsuarios.filtrar(newText);
        return false;
    }

    void onRequestAd() {

        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(getContext(), "ca-app-pub-5254622764364933/5349793743", adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error.
                Log.d(TAG, loadAdError.getMessage());
                mRewardedAd = null;
               // Toast.makeText(getContext().getApplicationContext(), "onAdFailedToLoad", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                mRewardedAd = rewardedAd;
                Log.d(TAG, "Ad was loaded.");
                //Toast.makeText(getContext().getApplicationContext(), "onAdLoaded",Toast.LENGTH_SHORT).show();
            }
        });
    }

    void onShowRewardAd(){
        if (mRewardedAd != null) {
            TodosFragment activityContext = TodosFragment.this;
            mRewardedAd.show((Activity) getContext(), new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

                    int punto =  Integer.valueOf(puntos);
                    int resultado = punto + 200;
                    // Handle the reward.
                    HashMap<String, String> params = new HashMap<>();
                    params.put("phone",phon);
                    params.put("puntos", String.valueOf(resultado));

                    Call<Responses> callVersiones = BovedaClient.getInstanceClient().getApiClient().updatePuntos(params);
                    callVersiones.enqueue(new Callback<Responses>() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onResponse(Call<Responses> call, Response<Responses> response) {

                        }

                        @Override
                        public void onFailure(Call<Responses> call, Throwable t) {

                        }

                    });
                    Intent intent = new Intent(getActivity(), PrincipalSolicitud.class);
                    startActivity(intent);
                   // Toast.makeText(getContext(), "onUserEarnedReward",Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "The user earned the reward.");
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();
                }
            });
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.");
        }
    }

}