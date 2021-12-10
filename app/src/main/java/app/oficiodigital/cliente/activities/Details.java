package app.oficiodigital.cliente.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.models.Busqueda;


public class Details extends BaseActivity {

    private TextView nombre, apM, apP, nombreE,nivel,puesto,tipo,turno,telefono,lugar;
    private Busqueda busqueda;

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

        busqueda = (Busqueda) getIntent().getExtras().getSerializable("datos");

        nombre.setText(busqueda.getName().toUpperCase());
        apM.setText(busqueda.getSurname1().toUpperCase());
        apP.setText(busqueda.getSurname2().toLowerCase());
        nombreE.setText(busqueda.getNombre_esc().toUpperCase());
        nivel.setText(busqueda.getNivel_escolar().toLowerCase());
        puesto.setText(busqueda.getRol().toUpperCase());
        tipo.setText(busqueda.getTipo_plantel().toUpperCase());
        turno.setText(busqueda.getTurno().toUpperCase());
        telefono.setText(busqueda.getPhone().toUpperCase());
        lugar.setText(busqueda.getEstado().toUpperCase());




    }

}
