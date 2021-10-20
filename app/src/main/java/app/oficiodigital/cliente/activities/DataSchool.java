package app.oficiodigital.cliente.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.MapView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.clients.BovedaClient;
import app.oficiodigital.cliente.fragments.Historial;
import app.oficiodigital.cliente.fragments.HomeFragment;
import app.oficiodigital.cliente.fragments.MetodosPago;
import app.oficiodigital.cliente.fragments.Perfil_Fragmen;
import app.oficiodigital.cliente.fragments.PublicarEmpleo;
import app.oficiodigital.cliente.models.Ejemplo;
import app.oficiodigital.cliente.models.ModelsDB.Phone;
import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.notifications.Alert;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*import com.android.volley.toolbox.JsonArrayRequest;*/

public class DataSchool extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private TextView muni, estado, select;
    private EditText codigop;
    private MapView mapa;
    private String phon;
    private BovedaClient.APIBovedaClient apiBovedaClient;

    private ImageView imagen;
    private TextView nombre, email, nombramiento, laborando;
    private EditText oescuela,oclave, ozona, otel, onom_dir;
    private TextView salida, phone;
    private Spinner onivel_esc, oturno, ocategoria, otipo_plantel, spinombramiento, onota, oprocedimiento, colonia;
    private SeekBar seekBar;
    private Button lugares;
    private int datos;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_school);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        View hView = navigationView.getHeaderView(0);List<Phone> list = Phone.listAll(Phone.class);
        for (Phone p : list) {
            phon = p.getPhone();

        }

        imagen = (ImageView) hView.findViewById(R.id.foto);
        nombre = (TextView) hView.findViewById(R.id.nombre);
        email = (TextView) hView.findViewById(R.id.email);
        phone = (TextView) hView.findViewById(R.id.phone);



        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.conten, new HomeFragment());
        fragmentTransaction.commit();

        //primerPregunta();
        //asociamos lode arriba con esto
        //casteo
        codigop = (EditText) findViewById(R.id.et_codigop);

        estado = (TextView) findViewById(R.id.estado);
        muni = (TextView) findViewById(R.id.municipio);

        select = (TextView) findViewById(R.id.select);
        mapa = (MapView) findViewById(R.id.mapa);

        /*mapa.onCreate(savedInstanceState);*/

        oescuela = (EditText) findViewById(R.id.et_nombre_escuela);
        /*final EditText oescuela=(EditText) findViewById(R.id.et_nombre_escuela); */

        oclave = (EditText) findViewById(R.id.et_clave);
        ozona = (EditText) findViewById(R.id.et_zona);
        otel = (EditText) findViewById(R.id.et_tel);

        nombramiento = (TextView) findViewById(R.id.tv_nombra);
        salida = (TextView) findViewById(R.id.tv_salida);
        muni = (TextView) findViewById(R.id.tv_municipio);
        estado = (TextView) findViewById(R.id.tv_estado);

        onom_dir = (EditText) findViewById(R.id.et_nom_dir);

        //definimos el spinner
        onivel_esc = (Spinner) findViewById(R.id.sp_nivel_esc);
        oturno = (Spinner) findViewById(R.id.sp_turno);
        ocategoria = (Spinner) findViewById(R.id.sp_categoria);
        otipo_plantel = (Spinner) findViewById(R.id.sp_plantel);
        colonia = (Spinner) findViewById(R.id.sp_colonia);

        spinombramiento = (Spinner) findViewById(R.id.sp_nombramiento);
        onota = (Spinner) findViewById(R.id.sp_not_desf);
        oprocedimiento = (Spinner) findViewById(R.id.sp_proc_Adm);


        seekBar = (SeekBar) findViewById(R.id.seekBar_anios);

        lugares = (Button) findViewById(R.id.sig_lugares_intereses);


        /*lugares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(principalMenu.this, LugaresInteres.class));
                startActivity(new Intent(this, LoginActivity.class));
            }
        });*/


        /*Bundle parametros = this.getIntent().getExtras();
        String datos = parametros.getString("datos");
        oescuela.setText(datos)*/;
       /* Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            String datos = parametros.getString("datos");
            oescuela.setText(datos);
        }*/

//        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        codigop.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String codigo = codigop.getText().toString();
                Call<List<Ejemplo>> call = BovedaClient.getInstanceClient().getApiClient().getCP(codigo);
                call.enqueue(new Callback<List<Ejemplo>>() {
                    @Override
                    public void onResponse(Call<List<Ejemplo>> call, Response<List<Ejemplo>> response) {
                        if (!response.isSuccessful()) {
                            //colonia.("Code: " + response.code());
                            return;
                        }

                        List<Ejemplo> ejemplo = response.body();

                        List<String> list = new ArrayList<String>();

                        for (Ejemplo eje : ejemplo) {
                            String mu = "";
                            String esta = "";

                            list.add(eje.getAsentamiento());

                            mu += "" + eje.getMunicipio();
                            muni.setText(" " + mu);

                            esta += "" + eje.getEstado();
                            estado.setText(" " + esta);
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), R.layout.spinner_colonia, list);
                        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), R.layout.sp_colonia, list);*/
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        colonia.setAdapter(adapter);

                        colonia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapter, View view,
                                                       int position, long id) {
                                String slect = colonia.getSelectedItem().toString();
                                select.setText(slect);
                            }

                            public void onNothingSelected(AdapterView<?> arg0) {
                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<List<Ejemplo>> call, Throwable t) {
                        // Toast.makeText(getApplication(), "error de siempre", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

//Condicional seekbar antiguedad
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                salida.setText((progress + " Años"));

                if (progress <= 2){
                    Toast.makeText(DataSchool.this,"No puede aplicar", Toast.LENGTH_SHORT).show();
                    lugares.setEnabled(false);
                }else if (progress > 2){
                    Toast.makeText(DataSchool.this,"Eres candidato a cambio", Toast.LENGTH_SHORT).show();
                    lugares.setEnabled(true);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        //Arreglo, obtencion de valores de spinners
        String[] opciones_ne = {"Preescolar", "Primaria", "Secundaria"};//Arreglo spinner nivel escolar
        String[] opciones_tn = {"Matutino", "Vespertino"};//arreglo turno
        String[] opciones_cat = {"Docente", "Subdirector", "Director"};//arreglo rol
        String[] opciones_tp = {"Municipal", "Estatal", "Federal", "Federalizado"};//arreglo tipoplantel
        String[] opciones_nombra = {"Si", "No"};
        String[] opciones_nota = {"Si", "No"};
        String[] opciones_proced = {"Si", "No"};

        // NUeva clase comunicacion para spinner - layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opciones_ne);
        onivel_esc.setAdapter(adapter);

        ArrayAdapter<String> adapter_tn = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opciones_tn);
        oturno.setAdapter(adapter_tn);

        ArrayAdapter<String> adapter_ct = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opciones_cat);
        ocategoria.setAdapter(adapter_ct);

        ArrayAdapter<String> adapter_tp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opciones_tp);
        otipo_plantel.setAdapter(adapter_tp);

        ArrayAdapter<String> adapter_nombra = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opciones_nombra);
        /*adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/
        spinombramiento.setAdapter(adapter_nombra);


      /*  int selectionPosition= adapter_nombra.getPosition("No");
        spinombramiento.setSelection(selectionPosition);
        spinombramiento.setAdapter(adapter_nombra);
        if (adapter_nombra != null) {
            int spinnerPosition = adapter_nombra.getPosition(adapter_nombra);
            spinombramiento.setSelection(spinnerPosition);
        }*/

        ArrayAdapter<String> adapter_nota = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opciones_nota);
        onota.setAdapter(adapter_nota);

        ArrayAdapter<String> adapter_proce = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opciones_proced);
        oprocedimiento.setAdapter(adapter_proce);






        /*Spinner sp = findViewById(R.id.spinnerCategoriasReporteGastos);*/
        spinombramiento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getId()){
                    case R.id.sp_nombramiento:
                        int seleccionado=spinombramiento.getSelectedItemPosition();
                        if (seleccionado==1) {
                            Toast.makeText(DataSchool.this, "No puede aplicar", Toast.LENGTH_SHORT).show();
                            lugares.setEnabled(false);
                        } else{
                            Toast.makeText(DataSchool.this, "Eres candidato a cambio", Toast.LENGTH_SHORT).show();
                            lugares.setEnabled(true);
                        }}
                /* if (spinombramiento.equals ("No")){
                 *//*Toast.makeText(DataSchool.this,"No puede aplicar", Toast.LENGTH_SHORT).show();*//*
                    ((TextView)spinombramiento.getSelectedView()).setError("Error message");
                    lugares.setEnabled(false);
                }else if (spinombramiento.equals ("Si")){
                    Toast.makeText(DataSchool.this,"Eres candidato a cambio", Toast.LENGTH_SHORT).show();
                    lugares.setEnabled(true);
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nada fue seleccionado. Por cierto, no he visto que este método se desencadene
            }
        });
      /*  tuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int itemSeleccionado, long l) {
                if (itemSeleccionado==opcion) {
                    //Puedes hacer algo si se cumple la condición....
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        //Vista, datos entre activities
        lugares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ViewDSchool.class);
                /*startActivity(intent);*/
                intent.putExtra("nombre",oescuela.getText().toString());
                intent.putExtra("clave",oclave.getText().toString());
                intent.putExtra("niescolar",onivel_esc.getSelectedItem().toString());
                intent.putExtra("turno",oturno.getSelectedItem().toString());
                intent.putExtra("zona",ozona.getText().toString());
                intent.putExtra("telefono",otel.getText().toString());
                intent.putExtra("cp",codigop.getText().toString());
                intent.putExtra("colonia",colonia.getSelectedItem().toString());
                intent.putExtra("municipio",muni.getText().toString());
                intent.putExtra("estado",estado.getText().toString());
                intent.putExtra("nom_direc",onom_dir.getText().toString());
                intent.putExtra("categoria",ocategoria.getSelectedItem().toString());
                intent.putExtra("plantel",otipo_plantel.getSelectedItem().toString());

                intent.putExtra("nombramiento",spinombramiento.getSelectedItem().toString());
                intent.putExtra("anios",salida.getText().toString());
                intent.putExtra("nota",onota.getSelectedItem().toString());
                intent.putExtra("procedimiento",oprocedimiento.getSelectedItem().toString());


                startActivity(intent);
            }
        });

    }

  /*  public void segunda_pantalla(View view){
        Intent i=new Intent(this, ViewDSchool.class);
        startActivityForResult(i,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) { // el "1" es el numero que pasaste como parametro
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("datos");
                // tu codigo para continuar procesando
                oescuela.setText(datos);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // código si no hay resultado
            }
        }
    }//onActivityResult*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Alert dialog = new Alert(this);
            dialog.setPositiveListener(getString(android.R.string.ok), (dialogInterface, i) -> {
                startActivity(new Intent(this, LoginActivity.class));
            });

            dialog.setNegativeListener(getString(android.R.string.cancel), ((dialogInterface, i) -> {
                dialog.close();
            }));

            dialog.fire(getString(R.string.logout_message));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (item.getItemId()) {
            case R.id.nav_home:
                ft.replace(R.id.conten, new HomeFragment()).commit();
                break;
            case R.id.nav_perfil:
                ft.replace(R.id.conten, new Perfil_Fragmen()).commit();
                break;
            case R.id.nav_metodos:
                ft.replace(R.id.conten, new MetodosPago()).commit();
                break;
            case R.id.nav_publicar:
                ft.replace(R.id.conten, new PublicarEmpleo()).commit();
                break;
            case R.id.nav_historial:
                ft.replace(R.id.conten, new Historial()).commit();
                break;
        }
        setTitle(item.getTitle());
        drawerLayout.closeDrawers();
        return true;
    }

  /*  public void ejemplo(View view){
        Toast.makeText(getApplication(), "Si jala", Toast.LENGTH_SHORT).show();
    }*/

    private void alerta() {
        String msg = getString(R.string.creando);
        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Guardando datos");
        progress.setMessage(msg);
        progress.show();
    }

    public void sig_lugares_intereses(View view) {
        oescuela.setError(null);
        oclave.setError(null);
        ozona.setError(null);
        otel.setError(null);
        codigop.setError(null);
        onom_dir.setError(null);
        salida.setError(null);



        String esc = oescuela.getText().toString();
        String clv = oclave.getText().toString();
        String zon = ozona.getText().toString();
        String tl = otel.getText().toString();
        String cp = codigop.getText().toString();
        String nom_direc = onom_dir.getText().toString();
        String estd = estado.getText().toString();
        String mun = muni.getText().toString();
        String colo = select.getText().toString();
        String sal = salida.getText().toString();


        //guardar la seleccion del usuario del spinner de nivel escolar
        String seleccion = onivel_esc.getSelectedItem().toString();
        Log.d("Here-----", "Seleccion-----------------::: " + seleccion);
        if (seleccion.equals("Preescolar")) {
                    /*int suma = valor1_int + valor2_int;
                    String resultado = String.valueOf(suma);
                    tv1.setText(resultado);*/
        } else if (seleccion.equals("Primaria")) {
        } else if (seleccion.equals("Secundaria")) {
        }

        //guardado de seleccion spinnner turno
        String seleccion_tn = oturno.getSelectedItem().toString();
        if (seleccion_tn.equals("Matutino")) {
        } else if (seleccion_tn.equals("Vespertino")) {
        }

        //guardado de seleccion spinnner rol
        String seleccion_ct = ocategoria.getSelectedItem().toString();
        if (seleccion_ct.equals("Docente")) {
        } else if (seleccion_ct.equals("Subdirector")) {
        } else if (seleccion_ct.equals("Director")) {
        }

        //Sleccion spinnner tipo plantel
        String seleccion_tp = otipo_plantel.getSelectedItem().toString();
        if (seleccion_tp.equals("Municipal")) {
        } else if (seleccion_tp.equals("Estatal")) {
        } else if (seleccion_tp.equals("Federal")) {
        } else if (seleccion_tp.equals("Federalizado")) {
        }

        //Sleccion spinnner nombramiento
        String seleccion_nombram = spinombramiento.getSelectedItem().toString();
       if (seleccion_nombram.equals("No")) {
       //Toast.makeText(DataSchool.this,"No puede aplicar", Toast.LENGTH_SHORT).show();
         //   ((TextView)spinombramiento.getSelectedView()).setError("Error message");
           // lugares.setEnabled(false);
        } else if(seleccion_nombram.equals("Si")) {
            //Toast.makeText(DataSchool.this,"Eres candidato a cambio", Toast.LENGTH_SHORT).show();
           // lugares.setEnabled(true);
        }
        /*int seleccion_nombram = spinombramiento.getSelectedItemPosition();
        if(seleccion_nombram==1){
            System.out.println("Si");
        }else if(seleccion_nombram==2){
            System.out.println("No");
        }*/

        //Sleccion spinnner nota
        String seleccion_nota = onota.getSelectedItem().toString();
        if (seleccion_nota.equals("Si")) {
        } else if (seleccion_nota.equals("No")) {
        }

        //Sleccion spinnner procedimiento
        String seleccion_proc = oprocedimiento.getSelectedItem().toString();
        if (seleccion_proc.equals("Si")) {
        } else if (seleccion_proc.equals("No")) {
        }


        //Validaciones campos
        if (TextUtils.isEmpty(esc)) {
            oescuela.setError(getString(R.string.error_campo_oblogatorio));
            oescuela.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(clv)) {
            oclave.setError(getString(R.string.error_campo_oblogatorio));
            oclave.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(zon)) {
            ozona.setError(getString(R.string.error_campo_oblogatorio));
            ozona.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(tl)) {
            otel.setError(getString(R.string.error_campo_oblogatorio));
            otel.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(cp)) {
            codigop.setError(getString(R.string.error_campo_oblogatorio));
            codigop.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(nom_direc)) {
            onom_dir.setError(getString(R.string.error_campo_oblogatorio));
            onom_dir.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(sal)) {
            salida.setError(getString(R.string.error_campo_oblogatorio));
            salida.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(seleccion_nombram)) {
        ((TextView)spinombramiento.getSelectedView()).setError("Debes contar con nombramiento");
            spinombramiento.requestFocus();
            return;
        }



        Toast.makeText(getApplicationContext(), "Se han validado y guardado correctamente los datos", Toast.LENGTH_SHORT).show();



        //Envio a BD
        HashMap<String, String> params = new HashMap<>();
        params.put("nombre_esc", esc);
        params.put("clave_esc", clv);
        params.put("nivel_escolar", seleccion);
        params.put("turno", seleccion_tn);
        params.put("zona_esc", zon);
        params.put("telefono", tl);
        params.put("c_postal", cp);
        params.put("estado", estd);
        params.put("municipio", mun);
        params.put("colonia", colo);
        params.put("nombre_direc", nom_direc);
        params.put("rol", seleccion_ct);
        params.put("tipo_plantel", seleccion_tp);

        params.put("nombramiento", seleccion_nombram);
        params.put("labor", sal);
        params.put("nota", seleccion_nota);
        params.put("procedimiento", seleccion_proc);

        Call<Responses> call = BovedaClient.getInstanceClient().getApiClient().registroEscuela(params);
        call.enqueue(new Callback<Responses>() {

            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {

            }
        });

       /* Intent intent = new Intent(getApplicationContext(),Intereses.class);
        startActivity(intent);*/
        /*intent.putExtra("nombre",oescuela.getText().toString());
        startActivity(intent);*/
    }




    }





