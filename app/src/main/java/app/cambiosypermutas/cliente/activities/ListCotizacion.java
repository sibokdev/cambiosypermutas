package app.cambiosypermutas.cliente.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import app.cambiosypermutas.cliente.R;

public class ListCotizacion extends AppCompatActivity {

    LinearLayout ll2,ll3,ll4,ll5,ll6,ll7,ll8;
    EditText mate1,mate2,mate3,mate4,mate5,mate6,mate7,mate8;
    Button ap1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cotizacion);

        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        ll4 = (LinearLayout) findViewById(R.id.ll4);
        ll5 = (LinearLayout) findViewById(R.id.ll5);
        ll6 = (LinearLayout) findViewById(R.id.ll6);
        ll7 = (LinearLayout) findViewById(R.id.ll7);
        ll8 = (LinearLayout) findViewById(R.id.ll8);
        mate1 = (EditText) findViewById(R.id.mate1);
        mate2 = (EditText) findViewById(R.id.mate2);
        mate3 = (EditText) findViewById(R.id.mate3);
        mate4 = (EditText) findViewById(R.id.mate4);
        mate5 = (EditText) findViewById(R.id.mate5);
        mate6 = (EditText) findViewById(R.id.mate6);
        mate7 = (EditText) findViewById(R.id.mate7);
        mate8 = (EditText) findViewById(R.id.mate8);
        ap1 = (Button) findViewById(R.id.lista);

        ap1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), ListadoCotizacion.class);
                intent.putExtra("m1", mate1.getText().toString());
                intent.putExtra("m2", mate2.getText().toString());
                intent.putExtra("m3", mate3.getText().toString());
                intent.putExtra("m4", mate4.getText().toString());
                intent.putExtra("m5", mate5.getText().toString());
                intent.putExtra("m6", mate6.getText().toString());
                intent.putExtra("m7", mate7.getText().toString());
                intent.putExtra("m8", mate8.getText().toString());
                startActivity(intent);
            }
        });

    }

    public void material1(View view){
       ll2.setVisibility(View.VISIBLE);
    }
    public void material2(View view){
        ll3.setVisibility(View.VISIBLE);
    }
    public void material3(View view){
        ll4.setVisibility(View.VISIBLE);
    }
    public void material4(View view){
        ll5.setVisibility(View.VISIBLE);
    }
    public void material5(View view){
        ll6.setVisibility(View.VISIBLE);
    }
    public void material6(View view){
        ll7.setVisibility(View.VISIBLE);
    }
    public void material7(View view){
        ll8.setVisibility(View.VISIBLE);
    }

}

