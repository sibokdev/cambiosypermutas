package app.oficiodigital.cliente.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import app.oficiodigital.cliente.R;


/**
 * Created by Ari on 22/03/2021.
 */

public class Welcome extends BaseActivity {

    private TextView phone, token1;
    private CheckBox terminos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        terminos = (CheckBox) findViewById(R.id.terminos);
        token1 = (TextView) findViewById(R.id.token);

        String token = getIntent().getStringExtra("tokenPhone");
        token1.setText(token);

        phone = (TextView) findViewById(R.id.phone);
        String phon = getIntent().getStringExtra("phone");
        phone.setText(phon);


    }

    public void siguiente(View view) {

        //insertOficio();

        if (terminos.isChecked() == false) {
            terminos.setError("acepta terminos y condiciones");

        } else {

            Intent sig1 = new Intent(this, ClienteRegister.class);
            sig1.putExtra("phone", phone.getText().toString());
            sig1.putExtra("tokenPhone", token1.getText().toString());
            startActivity(sig1);
        }
    }

}
