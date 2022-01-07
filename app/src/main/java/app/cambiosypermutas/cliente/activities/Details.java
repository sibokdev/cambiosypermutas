package app.cambiosypermutas.cliente.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.cambiosypermutas.cliente.R;
import app.cambiosypermutas.cliente.clients.BovedaClient;
import app.cambiosypermutas.cliente.models.Busqueda;
import app.cambiosypermutas.cliente.models.Estados;
import app.cambiosypermutas.cliente.models.ModelsDB.Phone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Details extends BaseActivity {

    private TextView nombre, apM, apP, nombreE,nivel,puesto,tipo,turno,telefono,lugar,interes;
    private Busqueda busqueda;
    String phone;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datosconpago);

        nombre = (TextView) findViewById(R.id.nombre);
        apM = (TextView) findViewById(R.id.apellido1);
        apP = (TextView) findViewById(R.id.apellido2);
        nombreE = (TextView) findViewById(R.id.et_nombre_escuela);
        nivel = (TextView) findViewById(R.id.et_nivel_escolar);
        puesto = (TextView) findViewById(R.id.puesto);
        tipo =(TextView) findViewById(R.id.tipo);
        turno = (TextView) findViewById(R.id.turno);
        telefono = (TextView) findViewById(R.id.telefono);
        lugar = (TextView) findViewById(R.id.lugar_actual);
        interes = (TextView) findViewById(R.id.interesado);

        back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(getApplicationContext(), " si sale", Toast.LENGTH_SHORT).show();
                onBackPressed();// regresar a activity anterior al presionar icon back en toolbar
            }
        });

        busqueda = (Busqueda) getIntent().getExtras().getSerializable("datos");

        nombre.setText(busqueda.getName().toUpperCase() +"  " + busqueda.getSurname1() +"  " + busqueda.getSurname2());

        nombreE.setText(busqueda.getNombre_esc().toUpperCase());
        nivel.setText(busqueda.getNivel_escolar().toLowerCase());
        puesto.setText(busqueda.getRol().toUpperCase());
        tipo.setText(busqueda.getTipo_plantel().toUpperCase());
        turno.setText(busqueda.getTurno().toUpperCase());
        telefono.setText(busqueda.getPhone().toUpperCase());
        lugar.setText(busqueda.getEstado().toUpperCase());

        List<Phone> list1 = Phone.listAll(Phone.class);
        for (Phone pho : list1) {

            phone = pho.getPhone();

        }
        String phon = phone;

        Call<List<Estados>> call = BovedaClient.getInstanceClient().getApiClient().getEstados(telefono.getText().toString());
        call.enqueue(new Callback<List<Estados>>() {
            @Override
            public void onResponse(Call<List<Estados>> call, Response<List<Estados>> response) {
                List<Estados> ejemplo = response.body();

                List<String> list = new ArrayList<String>();

                for (Estados estado : ejemplo) {
                    list.add(estado.getEstado());
                    for(int i = 0; i<list.size(); i++) {
                        if (i == 2) {
                            if(list.get(0).contains(list.get(1)) && list.get(0).contains(list.get(2))){
                                interes.setText(list.get(0));
                            }else if(list.get(1).contains(list.get(2))){
                                interes.setText(list.get(0) + " " + list.get(1));
                            }else if(list.get(0).contains(list.get(1))){
                                interes.setText(list.get(1) +" " + list.get(2));
                            }else if(list.get(0).contains(list.get(2))){
                                interes.setText(list.get(0) + " " + list.get(1));
                            }else{
                                interes.setText(list.get(0)+ " " + list.get(1) + " " + list.get(2));
                            }

                        }

                    }

                }
            }

            @Override
            public void onFailure(Call<List<Estados>> call, Throwable t) {
                //Toast.makeText(getApplicationContext(), "Telefono guardado", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(InsertCode.this, ProveedorDeServicios.class));

            }
        });

    }

}
