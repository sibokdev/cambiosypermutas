package app.cambiosypermutas.cliente.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import app.cambiosypermutas.cliente.R;
import app.cambiosypermutas.cliente.notifications.LoadingDialog;

public class AddMetodosPago extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_metodos_pago);
    }

    public void cambiar(View view){
        String msg = getString(R.string.AgregandoMetodo_msj);
        LoadingDialog.show(this, msg);
        startActivity(new Intent(this, PrincipalMetodos.class));
    }
}
