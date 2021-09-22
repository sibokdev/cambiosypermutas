package app.oficiodigital.cliente.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import app.oficiodigital.cliente.R;


public class HomeFragment extends Fragment {

    private TextView phone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

       phone = (TextView)  view.findViewById(R.id.phone);

        //String phon = getActivity().getIntent().getExtras().getString("phone");
        //phone.setText(phon);

        return view;
    }
}
