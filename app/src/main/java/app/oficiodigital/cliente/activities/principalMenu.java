package app.oficiodigital.cliente.activities;

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

import com.google.android.gms.maps.MapView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.clients.BovedaClient;
import app.oficiodigital.cliente.fragments.BusquedaFragment;
import app.oficiodigital.cliente.fragments.Historial;
import app.oficiodigital.cliente.fragments.HomeFragment;
import app.oficiodigital.cliente.fragments.MetodosPago;
import app.oficiodigital.cliente.fragments.Perfil_Fragmen;
import app.oficiodigital.cliente.fragments.PublicarEmpleo;
import app.oficiodigital.cliente.fragments.Solicitudes_cotizaciones;
import app.oficiodigital.cliente.models.ModelsDB.Phone;
import app.oficiodigital.cliente.notifications.Alert;
import butterknife.ButterKnife;


public class principalMenu extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private MapView mapa;
    private String phon;
    private BovedaClient.APIBovedaClient apiBovedaClient;

    private ImageView imagen;
    private TextView nombre, email, nombramiento, laborando;

    private TextView salida, phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_menu);
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

        View hView = navigationView.getHeaderView(0);

        List<Phone> list = Phone.listAll(Phone.class);
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

//-----------------------------------------------------------------------------
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
            case R.id.nav_home:
                ft.replace(R.id.conten, new HomeFragment()).commit();
                break;
            case R.id.nav_perfil:
                ft.replace(R.id.conten, new Perfil_Fragmen()).commit();
                break;
            case R.id.nav_busqueda:
                ft.replace(R.id.conten, new BusquedaFragment()).commit();
                break;
            case R.id.nav_metodos:
                ft.replace(R.id.conten, new MetodosPago()).commit();
                break;
            case R.id.nav_solicitud:
                ft.replace(R.id.conten, new Solicitudes_cotizaciones()).commit();
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
}
