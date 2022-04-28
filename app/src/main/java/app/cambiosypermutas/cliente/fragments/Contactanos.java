package app.cambiosypermutas.cliente.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import app.cambiosypermutas.cliente.R;
public class Contactanos extends Fragment {
    TextView tel, correo, link;
    private AdView mAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contactanos, container, false);

        //hiperlink
        link = view.findViewById(R.id.hyper_link2);
        link.setMovementMethod(LinkMovementMethod.getInstance());

        tel = (TextView) view.findViewById(R.id.abrir_tel);
        correo = (TextView) view.findViewById(R.id.abrir_correo);

        tel();
        correo();

        return view;
    }
public void tel(){
    tel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Toast.makeText(getContext(), "Remplazar por tu codigo", Toast.LENGTH_LONG).show();
            Intent i = new Intent(Intent.ACTION_DIAL);
            i.setData(Uri.parse("tel:2481857092"));
            startActivity(i);
        }
    });
}

public void correo(){
        correo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","ventas@web48.com.mx", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Android APP - ");
                startActivity(Intent.createChooser(emailIntent,  getActivity().getString(R.string.send_correo)));*/

                Intent email= new Intent(Intent.ACTION_SENDTO);
                email.setData(Uri.parse("mailto:ventas@nearshorecoders.com"));
                email.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                email.putExtra(Intent.EXTRA_TEXT, "My Email message");
                startActivity(email);
            }
        });
}



}