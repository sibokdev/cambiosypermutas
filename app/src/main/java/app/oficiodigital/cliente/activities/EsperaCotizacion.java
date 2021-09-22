package app.oficiodigital.cliente.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.fragments.HomeFragment;

public class EsperaCotizacion extends AppCompatActivity {

    private Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espera_cotizacion);

        b1 = (Button) findViewById(R.id.Acoti);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), principalMenu.class);
                startActivity(intent);
            }
        });
    }


}