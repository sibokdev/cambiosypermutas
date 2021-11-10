package app.oficiodigital.cliente.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.models.Busqueda;


public class Details extends BaseActivity {

    private TextView nombre, estado,municipio,token1, apM, apP,phone,phone1, des, tipo;
    private Busqueda busqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        nombre = (TextView) findViewById(R.id.nombre);
        estado = (TextView) findViewById(R.id.estado);
        municipio = (TextView) findViewById(R.id.municipio);
        tipo = (TextView) findViewById(R.id.tipo);
        token1 = (TextView) findViewById(R.id.token);
        apM = (TextView) findViewById(R.id.apellido1);
        apP = (TextView) findViewById(R.id.apellido2);
        phone = (TextView) findViewById(R.id.phone);
        phone1 = (TextView) findViewById(R.id.phone2);
        des = (TextView) findViewById(R.id.des);

        busqueda = (Busqueda) getIntent().getExtras().getSerializable("datos");

        nombre.setText(busqueda.getName().toUpperCase());
        apM.setText(busqueda.getSurname1().toUpperCase());
        apP.setText(busqueda.getSurname2().toUpperCase());
        estado.setText(busqueda.getOffice().toUpperCase());
        tipo.setText(busqueda.getOffice().toUpperCase());
        municipio.setText(busqueda.getMunicipio().toUpperCase());
        token1.setText(busqueda.getTokenPhone());
        phone.setText(busqueda.getPhone());

        des.setText(busqueda.getDescription());


    }

    public void cotizacion(View view){
        Intent intent = new Intent(getApplication(), DatosServicio.class);
        intent.putExtra("estado", estado.getText().toString());
        intent.putExtra("nombre", nombre.getText().toString());
        intent.putExtra("ap1", apP.getText().toString());
        intent.putExtra("ap2", apM.getText().toString());
        intent.putExtra("tokenPhone", token1.getText().toString());
        intent.putExtra("phone",phone.getText().toString());
        startActivity(intent);
    }
}
