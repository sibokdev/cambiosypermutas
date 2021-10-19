package app.oficiodigital.cliente.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import app.oficiodigital.cliente.R;

public class ViewDSchool extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dschool);

        TextView viewescuela =(TextView) findViewById(R.id.tv_nombre_escuela);
        TextView viewclaveesc =(TextView) findViewById(R.id.tv_clave);
        TextView viewnivesco =(TextView) findViewById(R.id.tv_nivesco);
        TextView viewturno =(TextView) findViewById(R.id.tv_turno);
        TextView viewzona =(TextView) findViewById(R.id.tv_zona);
        TextView viewtelefono =(TextView) findViewById(R.id.tv_tel);
        TextView viewcp =(TextView) findViewById(R.id.tv_codigop);
        TextView viewcolonia =(TextView) findViewById(R.id.tv_colonia);
        TextView viewmunicipio =(TextView) findViewById(R.id.tv_municipio);
        TextView viewestado =(TextView) findViewById(R.id.tv_estado);
        TextView viewnom_esc =(TextView) findViewById(R.id.tv_nom_dir);
        TextView viewcategoria =(TextView) findViewById(R.id.tv_cat);
        TextView viewplantel =(TextView) findViewById(R.id.tv_plantel);
        TextView viewnombramiento =(TextView) findViewById(R.id.tv_nombramiento);
        TextView viewanioslab =(TextView) findViewById(R.id.tv_anios);
        TextView viewnota =(TextView) findViewById(R.id.tv_nota);
        TextView viewproced =(TextView) findViewById(R.id.tv_proced);


        Intent intent = getIntent();

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
        viewproced.setText(intent.getStringExtra("procedimiento"));
    }
}