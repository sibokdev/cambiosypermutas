package app.oficiodigital.cliente.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

public class Intereses  {
    }
