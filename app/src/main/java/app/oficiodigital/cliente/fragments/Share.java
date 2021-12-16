package app.oficiodigital.cliente.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.oficiodigital.cliente.R;

public class Share extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        compartirApp();
        return view;
    }
    private void compartirApp(){
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
            String aux = "Descarga la app\n";
            aux = aux + "https://play.google.com/store/apps/details?id=com.spotify.tv.android&hl=es" + getContext().getPackageName();
            i.putExtra(Intent.EXTRA_TEXT, aux);
            startActivity(i);
        }catch (Exception e){

        }
    }
}