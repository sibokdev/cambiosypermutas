package app.cambiosypermutas.cliente.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import app.cambiosypermutas.cliente.R;
import app.cambiosypermutas.cliente.clients.BovedaClient;
import app.cambiosypermutas.cliente.fragments.BusquedaFragment;
import app.cambiosypermutas.cliente.fragments.BusquedaTriangulacion;
import app.cambiosypermutas.cliente.fragments.Contactanos;
import app.cambiosypermutas.cliente.fragments.FragmentInteres;
import app.cambiosypermutas.cliente.fragments.LugaresCercanos;
import app.cambiosypermutas.cliente.fragments.MetodosPago;
import app.cambiosypermutas.cliente.fragments.Perfil_Fragmen;
import app.cambiosypermutas.cliente.fragments.Share;
import app.cambiosypermutas.cliente.fragments.TodosFragment;
import app.cambiosypermutas.cliente.fragments.Triangulacion;
import app.cambiosypermutas.cliente.models.Datos;
import app.cambiosypermutas.cliente.models.ModelsDB.Phone;
import app.cambiosypermutas.cliente.notifications.Alert;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Ari on 01/05/2021.
 */

public class PrincipalTriangulacion extends BaseActivity  implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    private ImageView imagen;
    String phon;
    private TextView nombre, email, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_menu);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String id = getIntent().getStringExtra("id");



        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_escact);
        //Para cambiar el color del texto del item de un menu.
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.verde)));
//Para cambiar el color del icono del item de un menu.
        navigationView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.verde)));

        View hView = navigationView.getHeaderView(0);List<Phone> list = Phone.listAll(Phone.class);
        for (Phone p : list) {
            phon = p.getPhone();

        }

        imagen = (ImageView) hView.findViewById(R.id.foto);
        nombre = (TextView) hView.findViewById(R.id.nombre);
        email = (TextView) hView.findViewById(R.id.email);
        phone = (TextView) hView.findViewById(R.id.phone);

        // imagenSinConexion = (ImageView) hView.findViewById(R.id.imagenSinConexion);
        //imagenSinConexion.setVisibility(View.INVISIBLE);


        phone.setText(phon);
        Call<List<Datos>> callVersiones = BovedaClient.getInstanceClient().getApiClient().getDatos(phone.getText().toString());
        callVersiones.enqueue(new Callback<List<Datos>>() {
            @Override
            public void onResponse(Call<List<Datos>> call, Response<List<Datos>> response) {

                if (!response.isSuccessful()) {
                    //colonia.("Code: " + response.code());
                    return;
                }

                List<Datos> respuestas = response.body();
                List<String> list = new ArrayList<String>();

                for (Datos res : respuestas) {

                    list.add(res.getName());
                    String name = "";
                    String correo = "";
                    name += " " + res.getName();
                    name += " " + res.getSurname1();
                    name += " " + res.getSurname2();
                    nombre.setText(name);

                    correo = "" + res.getEmail();
                    email.setText(correo);

                }
            }
            @Override
            public void onFailure (Call < List <Datos>> call, Throwable t){
                //  L.error("getOficios " + t.getMessage());
            }

        });



        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();


        ConnectivityManager con = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = con.getActiveNetworkInfo();

        //if (networkInfo!=null && networkInfo.isConnected()) {
        fragmentTransaction.add(R.id.conten, new BusquedaTriangulacion());
        fragmentTransaction.commit();
        // }else {
        //imagenSinConexion.setVisibility(View.VISIBLE);
//mensaje
        // Toast.makeText(this, "No se ha podido establecer la conexión a internet, verifique el acceso a internet e intentelo nuevamente", Toast.LENGTH_SHORT).show();
        //}

    }

    public void openLoadingDialog() {
        loadingDialog loadingDialog = new loadingDialog(this);
        loadingDialog.startLoadingDialog();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                loadingDialog.dismisDialog();
            }
        },3000); //You can change this time as you wish
    }

    //control de pulsacion, boton atras
/*    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea salir de Cambios y permutas?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();//ocultar dialogo
                        }
                    });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }*/


  /*  @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

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
                finish();
            });

            dialog.setNegativeListener(getString(android.R.string.cancel), ((dialogInterface, i) -> {
                dialog.close();
            }));

            dialog.fire(getString(R.string.logout_message));

            return true;
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (id == R.id.action_contacto) {
            ft.replace(R.id.conten, new Contactanos()).commit();
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
            case R.id.nav_escact:
                ft.replace(R.id.conten, new app.cambiosypermutas.cliente.fragments.DataSchool()).commit();
                break;
            case R.id.nav_perfil:
                ft.replace(R.id.conten, new Perfil_Fragmen()).commit();
                break;
            case R.id.nav_busqueda:
                ft.replace(R.id.conten, new BusquedaFragment()).commit();
                break;
            case R.id.nav_cerca:
                ft.replace(R.id.conten, new LugaresCercanos()).commit();
                break;
            case R.id.nav_interes:
                ft.replace(R.id.conten, new FragmentInteres()).commit();
                break;
            case R.id.nav_todos:
                ft.replace(R.id.conten, new TodosFragment()).commit();
                break;
            case R.id.nav_metodos:
                ft.replace(R.id.conten, new MetodosPago()).commit();
                break;
            case R.id.nav_compartir:
                ft.replace(R.id.conten, new Share()).commit();
                break;
            case R.id.nav_contacto:
                ft.replace(R.id.conten, new Contactanos()).commit();
                break;

        }
        setTitle(item.getTitle());
        drawerLayout.closeDrawers();
        return true;
    }
}
