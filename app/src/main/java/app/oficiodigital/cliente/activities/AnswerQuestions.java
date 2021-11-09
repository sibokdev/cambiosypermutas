package app.oficiodigital.cliente.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.clients.BovedaClient;
import app.oficiodigital.cliente.models.ModelsDB.Phone;
import app.oficiodigital.cliente.models.Respuestas;
import app.oficiodigital.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ari on 12/04/2021.
 */

public class AnswerQuestions extends BaseActivity {
    private TextView tv_p1,tv_p2,phone,r1,r2;
    private EditText p1,p2;
    private TextInputLayout ti_p1,ti_p2;
    String phon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respuestas_seguridad);

        tv_p1 = (TextView) findViewById(R.id.tv_pregunta1);
        tv_p2 = (TextView) findViewById(R.id.tv_pregunta2);

        phone = (TextView)findViewById(R.id.phone);
        p1 = (EditText)findViewById(R.id.pregunta1);
        p2 = (EditText)findViewById(R.id.pregunta2);
        r1 = (TextView)findViewById(R.id.r1);
        r2 = (TextView)findViewById(R.id.r2);

        ti_p1 = (TextInputLayout)findViewById(R.id.ti_p1);
        ti_p2 = (TextInputLayout)findViewById(R.id.ti_p2);

        List<Phone> list1 = Phone.listAll(Phone.class);
        for (Phone pho : list1) {
            String phone = "";

            phone = pho.getPhone();
            phon = phone;
        }

        phone.setText(phon);
        Call<List<Respuestas>> callVersiones = BovedaClient.getInstanceClient().getApiClient().getRespuestas(phone.getText().toString());
        callVersiones.enqueue(new Callback<List<Respuestas>>() {
            @Override
            public void onResponse(Call<List<Respuestas>> call, Response<List<Respuestas>> response) {

                if (!response.isSuccessful()) {
                    //colonia.("Code: " + response.code());
                    return;
                }

                List<Respuestas> respuestas = response.body();
                List<String> list = new ArrayList<String>();

                for (Respuestas res: respuestas){

                    list.add(res.getPregunta());

                }

                String cade = list.get(0);
                tv_p1.setText(cade);
                String cade1 = list.get(1);
                tv_p2.setText(cade1);

                List<String> lis = new ArrayList<String>();

                for (Respuestas res: respuestas){

                    lis.add(res.getRespuesta());

                }

                String res = lis.get(0);
                r1.setText(res);
                String res1 = lis.get(1);
                r2.setText(res1);

            }

            @Override
            public void onFailure(Call<List<Respuestas>> call, Throwable t) {
                L.error("getOficios " + t.getMessage());
            }
        });





    }

    public void siguiente(View view){

        String re1 = p1.getText().toString();
        String re2 = p2.getText().toString();

            if(re1.equals(r1.getText().toString())){
            ti_p1.setErrorEnabled(false);
            }else {
            ti_p1.setError("Respuesta incorrecta");
            }

            if (re2.equals(r2.getText().toString())) {
                ti_p2.setErrorEnabled(false);
            } else {
                ti_p2.setError("Respuesta incorrecta");
            }

            if (re1.equals(r1.getText().toString()) || re2.equals(r2.getText().toString())){
                Intent intent = new Intent(this, ChangePassword.class);
                intent.putExtra("phone", phone.getText().toString());
                startActivity(intent);
                //Toast.makeText(getApplication(),"alguno esta maal pero pasa",Toast.LENGTH_SHORT).show();
            }else{
                //Toast.makeText(getApplication(),"los dos estan mal",Toast.LENGTH_SHORT).show();
            }


       // Intent intent = new Intent(this, ChangePassword.class);
        //startActivity(intent);
    }
}
