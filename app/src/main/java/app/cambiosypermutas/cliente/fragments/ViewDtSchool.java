package app.cambiosypermutas.cliente.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.cambiosypermutas.cliente.R;
import app.cambiosypermutas.cliente.clients.BovedaClient;
import app.cambiosypermutas.cliente.models.ModelsDB.Phone;
import app.cambiosypermutas.cliente.models.Request.DatosSchool;
import app.cambiosypermutas.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ViewDtSchool extends Fragment {
    private RecyclerView lista;
    private TextView viewescuela, viewclaveesc, viewnivesc,viewturno, viewzona, viewtelefono, viewcp, viewcolonia, viewmunicipio, viewestado, viewnom_esc;
    private TextView viewcategoria, viewplantel, viewnombramiento, viewanioslab, viewnota, viewproced,viewnom_dir, viewnivesco;
    private SearchView buscador;
    private EditText busc;
    String phone = "";

    private Button guardar, modificar;

    private int id = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_dt_school, container, false);

        viewescuela =(TextView) view.findViewById(R.id.tv_nombre_escuela);
        viewclaveesc =(TextView) view.findViewById(R.id.tv_clave);
        viewnivesco =(TextView) view.findViewById(R.id.tv_nivesco);
        viewturno =(TextView) view.findViewById(R.id.tv_turno);
        viewzona =(TextView) view.findViewById(R.id.tv_zona);
        viewtelefono =(TextView) view.findViewById(R.id.tv_tel);
        viewcp =(TextView) view.findViewById(R.id.tv_codigop);
        viewcolonia =(TextView) view.findViewById(R.id.tv_colonia);
        viewmunicipio =(TextView) view.findViewById(R.id.tv_municipio);
        viewestado =(TextView) view.findViewById(R.id.tv_estado);
        viewnom_dir =(TextView) view.findViewById(R.id.tv_nom_dir);
        viewcategoria =(TextView) view.findViewById(R.id.tv_cat);
        viewplantel =(TextView) view.findViewById(R.id.tv_plantel);
        viewnombramiento =(TextView) view.findViewById(R.id.tv_nombramiento);
        viewanioslab =(TextView) view.findViewById(R.id.tv_anios);
        viewnota =(TextView) view.findViewById(R.id.tv_nota);
        viewproced =(TextView) view.findViewById(R.id.tv_proced);


        guardar = (Button) view.findViewById(R.id.guardar);
        modificar = (Button) view.findViewById(R.id.modificar);

        final SharedPreferences sharedPref = getActivity().getSharedPreferences("Actualizar", Context.MODE_PRIVATE);
        //sharedPref.edit().clear().apply();  // opcional si quieres que inicie como desabilitado el botón cada vez que se cree la activity
        /* guardar.setVisibility(View.INVISIBLE); //Tambien puedes hacerlo desde XML*/
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//Habilitar boton de modificar
                /*  view.setVisibility(View.INVISIBLE); //Si quieres que el boton se esconda*/
                view.setEnabled(false); //Si quieres que el boton se deshabilite
                SharedPreferences.Editor editor = sharedPref.edit();
                //colocas una clave (botón) con su valor(activar), luego commit o apply para que se guarde ese valor en SP
                editor.putString("boton", "activar");
                editor.commit();

                Intent intent = new Intent(getContext(), DataSchool.class);
                startActivity(intent);
            }
        });


    /*    guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        getDataSchool();



        return view;
    }


    private void getDataSchool() {
        List<Phone> list1 = Phone.listAll(Phone.class);
        for (Phone pho : list1) {


            phone = pho.getPhone();

        }
        Call<List<DatosSchool>> callVersiones = BovedaClient.getInstanceClient().getApiClient().getDataSchool(phone);
        callVersiones.enqueue(new Callback<List<DatosSchool>>() {
            @Override
            public void onResponse(Call<List<DatosSchool>> call, Response<List<DatosSchool>> response) {

                if (!response.isSuccessful()) {
                    return;
                }

                List<DatosSchool> respuestas = response.body();
                List<String> list = new ArrayList<String>();

                for (DatosSchool res : respuestas) {

                    String nombre_esc = ""+ res.getNombre_esc();
                    viewescuela.setText(nombre_esc);

                    String claveesc = ""+ res.getClave_esc();
                    viewclaveesc.setText(claveesc);

                    String nivel_escolar = ""+ res.getNivel_escolar();
                    viewnivesco.setText(nivel_escolar);

                    String turno = ""+ res.getTurno();
                    viewturno.setText(turno);

                    String zona = ""+ res.getZon_esc();
                    viewzona.setText(zona);

                    String phone = ""+ res.getTelefono();
                    viewtelefono.setText(phone);

                    String cp = ""+ res.getC_postal();
                    viewcp.setText(cp);

                    String colonia = ""+ res.getColonia();
                    viewcolonia.setText(colonia);

                    String muni = ""+ res.getMunicipio();
                    viewmunicipio.setText(muni);

                    String estado = ""+ res.getEstado();
                    viewestado.setText(estado);

                    String nomdir = ""+ res.getNombre_direc();
                    viewnom_dir.setText(nomdir);

                    String categoria = ""+ res.getCategoria();
                    viewcategoria.setText(estado);

                    String plantel = ""+res.getTipo_plantel();
                    viewplantel.setText(plantel);

                    String nombra = ""+res.getNombramiento();
                    viewnombramiento.setText(nombra);

                    String labor = ""+res.getLabor();
                    viewanioslab.setText(labor);

                    String nota = ""+res.getNota();
                    viewnota.setText(nota);


                    String procedimiento =""+ res.getProcedimiento();
                    viewproced.setText(procedimiento);


                }
            }
            @Override
            public void onFailure(Call<List<DatosSchool>> call, Throwable t) {
                L.error("getDataSchool " + t.getMessage());
            }
        });

    }
}