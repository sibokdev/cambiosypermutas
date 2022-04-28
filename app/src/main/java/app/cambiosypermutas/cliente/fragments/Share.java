package app.cambiosypermutas.cliente.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import app.cambiosypermutas.cliente.R;

public class Share extends Fragment {
    ViewFlipper vf_carrusel;//
    TextView link,politics;
    private AdView mAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        compartirApp();

        //Inicializamos all
        int images[] = {R.drawable.mexico_gulf, R.drawable.annie_spratt,
                R.drawable.mantas_hesthaven};//


        vf_carrusel = view.findViewById(R.id.vf_carrusel);//

        //hiperlink
        link = view.findViewById(R.id.hyper_link);
        link.setMovementMethod(LinkMovementMethod.getInstance());

        politics = view.findViewById(R.id.politics);
        politics.setMovementMethod(LinkMovementMethod.getInstance());

        //creacion de sentencia par validacion
        for (int image: images){
            flipperImages(image);
        }

        return view;
    }

//metodo para el visor del carrusel
    public void flipperImages(int image){
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);

        vf_carrusel.addView(imageView);
        vf_carrusel.setFlipInterval(3000);//duracion
        vf_carrusel.setAutoStart(true);

        //activamos animacion
        vf_carrusel.setInAnimation(getContext(), android.R.anim.slide_out_right);
        //vf_carrusel.setOutAnimation(getContext(), android.R.anim.slide_in_left);
    }
    private void compartirApp(){
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
            String aux = "Descarga la app\n";
            aux = aux + "https://play.google.com/store/apps/details?id=app.oficiodigital.cliente";
            i.putExtra(Intent.EXTRA_TEXT, aux);
            startActivity(i);
        }catch (Exception e){

        }
    }
}