package app.cambiosypermutas.cliente.activities;

import android.content.Intent;
import android.os.Bundle;
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
import app.cambiosypermutas.cliente.fragments.Contactanos;
import app.cambiosypermutas.cliente.fragments.FragmentInteres;
import app.cambiosypermutas.cliente.fragments.LugaresCercanos;
import app.cambiosypermutas.cliente.fragments.MetodosPago;
import app.cambiosypermutas.cliente.fragments.Perfil_Fragmen;
import app.cambiosypermutas.cliente.fragments.Share;
import app.cambiosypermutas.cliente.fragments.Triangulacion;
import app.cambiosypermutas.cliente.models.Datos;
import app.cambiosypermutas.cliente.models.ModelsDB.Phone;
import app.cambiosypermutas.cliente.notifications.Alert;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Ari on 01/05/2021.
 */

public class PrincipalIntereses extends BaseActivity  implements NavigationView.OnNavigationItemSelectedListener {

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
        navigationView.setCheckedItem(R.id.nav_perfil);

        View hView = navigationView.getHeaderView(0);

        List<Phone> list1 = Phone.listAll(Phone.class);
        for (Phone pho : list1) {
            String phone = "";

            phone = pho.getPhone();
            phon = phone;
        }


        nombre = (TextView) hView.findViewById(R.id.nombre);
        email = (TextView) hView.findViewById(R.id.email);
        phone = (TextView) hView.findViewById(R.id.phone);

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
            public void onFailure (Call < List < Datos >> call, Throwable t){
                //  L.error("getOficios " + t.getMessage());
            }

        });


        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.conten, new Triangulacion());
        fragmentTransaction.commit();

    }

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