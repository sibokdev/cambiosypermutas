package app.oficiodigital.cliente.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.clients.BovedaClient;
import app.oficiodigital.cliente.models.Request.DatosSchool;
import app.oficiodigital.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewDSchool extends AppCompatActivity {
    private RecyclerView lista;
    private TextView viewescuela, viewclaveesc, viewnivesc,viewturno, viewzona, viewtelefono, viewcp, viewcolonia, viewmunicipio, viewestado, viewnom_esc;
    private TextView viewcategoria, viewplantel, viewnombramiento, viewanioslab, viewnota, viewproced,viewnom_dir, viewnivesco;
    private SearchView buscador;
    private EditText busc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dschool);

        viewescuela =(TextView) findViewById(R.id.tv_nombre_escuela);
        viewclaveesc =(TextView) findViewById(R.id.tv_clave);
        viewnivesco =(TextView) findViewById(R.id.tv_nivesco);
        viewturno =(TextView) findViewById(R.id.tv_turno);
        viewzona =(TextView) findViewById(R.id.tv_zona);
        viewtelefono =(TextView) findViewById(R.id.tv_tel);
        viewcp =(TextView) findViewById(R.id.tv_codigop);
        viewcolonia =(TextView) findViewById(R.id.tv_colonia);
        viewmunicipio =(TextView) findViewById(R.id.tv_municipio);
        viewestado =(TextView) findViewById(R.id.tv_estado);
        viewnom_dir =(TextView) findViewById(R.id.tv_nom_dir);
        viewcategoria =(TextView) findViewById(R.id.tv_cat);
        viewplantel =(TextView) findViewById(R.id.tv_plantel);
        viewnombramiento =(TextView) findViewById(R.id.tv_nombramiento);
        viewanioslab =(TextView) findViewById(R.id.tv_anios);
        viewnota =(TextView) findViewById(R.id.tv_nota);
        viewproced =(TextView) findViewById(R.id.tv_proced);


        getDataSchool();

        /*Intent intent = getIntent();

        viewescuela.setText(intent.getStringExtra("nombre"));
        viewclaveesc.setText(intent.getStringExtra("clave"));
        viewnivesco.setText(intent.getStringExtra("niescolar"));
        viewturno.setText(intent.getStringExtra("turno"));
        viewzona.setText(intent.getStringExtra("zona"));
        viewtelefono.setText(intent.getStringExtra("telefono"));
        viewcp.setText(intent.getStringExtra("cp"));
        viewcolonia.setText(intent.getStringExtra("colonia"));
        viewmunicipio.setText(intent.getStringExtra("municipio"));
        viewestado.setText(intent.getStringExtra("estado"));
        viewnom_esc.setText(intent.getStringExtra("nom_direc"));
        viewcategoria.setText(intent.getStringExtra("categoria"));
        viewplantel.setText(intent.getStringExtra("plantel"));
        viewnombramiento.setText(intent.getStringExtra("nombramiento"));
        viewanioslab.setText(intent.getStringExtra("anios"));
        viewnota.setText(intent.getStringExtra("nota"));
        viewproced.setText(intent.getStringExtra("procedimiento"));*/
    }

    private void getDataSchool() {
        String phon = "6265656565";
        Call<List<DatosSchool>> callVersiones = BovedaClient.getInstanceClient().getApiClient().getDataSchool(phon);
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