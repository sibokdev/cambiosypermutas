package app.oficiodigital.cliente.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.activities.DetallesPublicarEmpleo;

public class PublicarEmpleo extends Fragment {
    private TextView phone;
    private Button publicar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_publicar_empleo, container, false);

        phone = (TextView)  view.findViewById(R.id.phone);
        publicar = (Button) view.findViewById(R.id.publicar);

        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetallesPublicarEmpleo.class);
                startActivity(intent);
            }
        });



        //String phon = getActivity().getIntent().getExtras().getString("phone");
        //phone.setText(phon);

        return view;
    }
}