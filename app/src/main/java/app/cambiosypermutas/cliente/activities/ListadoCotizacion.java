package app.cambiosypermutas.cliente.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import app.cambiosypermutas.cliente.R;

public class ListadoCotizacion extends AppCompatActivity {

    TextView m1,m2,m3,m4,m5,m6,m7,m8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_cotizacion);

        m1 = (TextView) findViewById(R.id.m1);
        m2 = (TextView) findViewById(R.id.m2);
        m3 = (TextView) findViewById(R.id.m3);
        m4 = (TextView) findViewById(R.id.m4);
        m5 = (TextView) findViewById(R.id.m5);
        m6 = (TextView) findViewById(R.id.m6);
        m7 = (TextView) findViewById(R.id.m7);
        m8 = (TextView) findViewById(R.id.m8);

        String ma1 = getIntent().getStringExtra("m1");
        if(ma1.equals("")){
            m1.setVisibility(View.GONE);
        }else{
            m1.setText(ma1);
            m1.setVisibility(View.VISIBLE);
        }
        String ma2 = getIntent().getStringExtra("m2");
        if(ma2.equals("")){
            m2.setVisibility(View.GONE);
        }else{
            m2.setText(ma2);
            m2.setVisibility(View.VISIBLE);
        }
        String ma3 = getIntent().getStringExtra("m3");
        if(ma3.equals("") ){
            m3.setVisibility(View.GONE);
        }else{
            m3.setText(ma3);
            m3.setVisibility(View.VISIBLE);
        }
        String ma4 = getIntent().getStringExtra("m4");
        if(ma4.equals("")){
            m4.setVisibility(View.GONE);
        }else{
            m4.setText(ma4);
            m4.setVisibility(View.VISIBLE);
        }
        String ma5 = getIntent().getStringExtra("m5");
        if(ma5.equals("")){
            m5.setVisibility(View.GONE);
        }else{
            m5.setText(ma5);
            m5.setVisibility(View.VISIBLE);
        }
        String ma6 = getIntent().getStringExtra("m6");
        if(ma6.equals("")){
            m6.setVisibility(View.GONE);
        }else{
            m6.setText(ma6);
            m6.setVisibility(View.VISIBLE);
        }
        String ma7 = getIntent().getStringExtra("m7");
        if(ma7.equals("")){
            m7.setVisibility(View.GONE);
        }else{
            m7.setText(ma7);
            m7.setVisibility(View.VISIBLE);
        }
        String ma8 = getIntent().getStringExtra("m8");
        if(ma8.equals("")){
            m8.setVisibility(View.GONE);
        }else{
            m8.setText(ma8);
            m8.setVisibility(View.VISIBLE);
        }

    }
}