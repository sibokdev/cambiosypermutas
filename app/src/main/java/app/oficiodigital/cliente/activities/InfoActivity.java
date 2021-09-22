package app.oficiodigital.cliente.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import app.oficiodigital.cliente.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoActivity extends BaseActivity {

    @BindView(R.id.btnDone) Button doneBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);

    }


    public void listo(View view) {
        Intent inte = new Intent(this, LoginActivity.class);
        startActivity(inte);

    }
}
