package app.cambiosypermutas.cliente.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import app.cambiosypermutas.cliente.R;
import app.cambiosypermutas.cliente.activities.AddNewPhone;
import app.cambiosypermutas.cliente.activities.EditAddress;
import app.cambiosypermutas.cliente.activities.EditEmail;
import app.cambiosypermutas.cliente.activities.EditPhone;
import app.cambiosypermutas.cliente.activities.principalMenu;
import app.cambiosypermutas.cliente.clients.BovedaClient;
import app.cambiosypermutas.cliente.models.Datos;
import app.cambiosypermutas.cliente.models.Foto;
import app.cambiosypermutas.cliente.models.ModelsDB.FotoPerfil;
import app.cambiosypermutas.cliente.models.ModelsDB.Phone;
import app.cambiosypermutas.cliente.models.ModelsDB.TokenPhone;
import app.cambiosypermutas.cliente.models.Responses;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Perfil_Fragmen extends Fragment {
    private static final int RESULT_OK = 0;
    private TextView nombre, phone1, phone2, email, phone, id, estado, id1, foto2;
    private LinearLayout ll_email, ll_telefono, ll_phone2, ll_direccion;
    private ImageView foto;
    private CircleImageView fotos;
    private EditText nuevo, confirmacion;
    private ProgressBar progressBar2;
    int SELEC_IMAGEN = 200;
    String phon,f1;
    Bitmap bitmap1;


    String p = "2220001111";

    private ImageView imagenSinConexion;

    private ScrollView datosescuela;
    private CardView noti_inter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil_, container, false);


        nombre = (TextView) view.findViewById(R.id.nombre);
        email = (TextView) view.findViewById(R.id.email);
        phone1 = (TextView) view.findViewById(R.id.phone1);
        phone2 = (TextView) view.findViewById(R.id.phone2);
        phone = (TextView) view.findViewById(R.id.phone);
        id = (TextView) view.findViewById(R.id.id_datos);
        id1 = (TextView) view.findViewById(R.id.id_datos1);
        estado = (TextView) view.findViewById(R.id.estado);
        ll_email = (LinearLayout) view.findViewById(R.id.ll_email);
        //nuevo = (EditText) view.findViewById(R.id.nuevo);
        //confirmacion = (EditText) view.findViewById(R.id.confirmacion);
        ll_telefono = (LinearLayout) view.findViewById(R.id.ll_telefono);
        ll_phone2 = (LinearLayout) view.findViewById(R.id.ll_phone2);
        progressBar2 = (ProgressBar) view.findViewById(R.id.progressBar2);

        ll_direccion = (LinearLayout) view.findViewById(R.id.ll_direccion);
        foto2 = (TextView) view.findViewById(R.id.foto2);
        fotos = (CircleImageView) view.findViewById(R.id.fotos);

        datosescuela = (ScrollView) view.findViewById(R.id.datosescuela);
        noti_inter = (CardView) view.findViewById(R.id.noti_inter);

        imagenSinConexion = (ImageView) view.findViewById(R.id.imagenSinConexion);
        imagenSinConexion.setVisibility(View.INVISIBLE);

        ConnectivityManager con = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = con.getActiveNetworkInfo();

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }

        if (networkInfo!=null && networkInfo.isConnected()) {
            datosescuela.setVisibility(View.VISIBLE);
            noti_inter.setVisibility(View.INVISIBLE);

        }else {
            noti_inter.setVisibility(View.VISIBLE);
            datosescuela.setVisibility(View.INVISIBLE);
            imagenSinConexion.setVisibility(View.VISIBLE);
            //mensaje
            Toast.makeText(getContext(), "No se ha podido establecer la conexión a internet, verifique el acceso a internet e intentelo nuevamente", Toast.LENGTH_SHORT).show();
        }

        List<Phone> list1 = Phone.listAll(Phone.class);
        for (Phone pho : list1) {
            String phone = "";

            phone = pho.getPhone();
            phon = phone;
        }


        ll_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                builder.setTitle("Editar Correo Electrónico");
                builder.setMessage("¿Deseas cambiar de correo electronico?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent inte = new Intent(getActivity(), EditEmail.class);
                        inte.putExtra("phone", phone.getText().toString());
                        inte.putExtra("email", email.getText().toString());
                        startActivity(inte);
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }

        });

        /*ll_telefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                builder.setTitle("Editar Número Telefónico");
                builder.setMessage("¿Deseas cambiar de número telefonico?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent inte = new Intent(getActivity(), EditPhone.class);
                        inte.putExtra("id", id.getText().toString());
                        inte.putExtra("phone", phone.getText().toString());
                        startActivity(inte);
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }

        });*/

        ll_phone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                builder.setTitle("Editar Número Telefónico");
                builder.setMessage("¿Deseas editar número telefonico?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent inte = new Intent(getActivity(), AddNewPhone.class);
                        inte.putExtra("id", id.getText().toString());
                        inte.putExtra("phone", phone2.getText().toString());
                        startActivity(inte);
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }

        });

        ll_direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                builder.setTitle("Cambiar de dirección");
                builder.setMessage("¿Deseas cambiar de direccion?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent inte = new Intent(getActivity(), EditAddress.class);
                        inte.putExtra("id", id.getText().toString());
                        inte.putExtra("phone", phone.getText().toString());
                        startActivity(inte);
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }

        });


        //id1.setText(nuevo.getText().toString());

        phone.setText(phon);
        Call<List<Datos>> callVersiones = BovedaClient.getInstanceClient().getApiClient().getDatos(phone.getText().toString());
        callVersiones.enqueue(new Callback<List<Datos>>() {
            @Override
            public void onResponse(Call<List<Datos>> call, Response<List<Datos>> response) {

                if (!response.isSuccessful()) {
                    //colonia.("Code: " + response.code());
                    return;
                }
                progressBar2.setVisibility(View.GONE);
                List<Datos> respuestas = response.body();
                List<String> list = new ArrayList<String>();

                for (Datos res : respuestas) {

                    list.add(res.getName());
                    String name = "";
                    String correo = "";
                    String phon = "";
                    String phon2 = "";
                    String id_datos = "";
                    String esta = "";
                    name += " " + res.getName();
                    name += " " + res.getSurname1();
                    name += " " + res.getSurname2();
                    nombre.setText(name);

                    correo = "" + res.getEmail();
                    email.setText(correo);

                    phon = "" + res.getPhone();
                    phone1.setText(phon);


                    if(res.getEstado() == null){
                        estado.setText("Estado");
                    }else{
                        esta  = "" + res.getEstado();
                        estado.setText(esta);
                    }



                    if(res.getPhone2() == null){
                        phone2.setText("Agregar nuevo telefono");
                    }else{
                        phon2 = "" + res.getPhone2();
                        phone2.setText(phon2);
                    }

                    id_datos = "" + res.getId();
                    id.setText(id_datos);
                }

              /*  Call<List<Direccion>> callVersiones1 = BovedaClient.getInstanceClient().getApiClient().getDirrecion(id.getText().toString());
                callVersiones1.enqueue(new Callback<List<Direccion>>() {
                    @Override
                    public void onResponse(Call<List<Direccion>> call, Response<List<Direccion>> response) {

                        if (!response.isSuccessful()) {
                            //colonia.("Code: " + response.code());
                            return;
                        }

                        List<Direccion> respuestas = response.body();

                        for (Direccion res : respuestas) {

                            String dire = "";
                            dire += "" + res.getCalle();
                            dire += " " + res.getNumero();
                            dire += " " + res.getColonia();
                            dire += " " + res.getMunicipio();
                            dire += " " + res.getEstado();
                            estado.setText(dire);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Direccion>> call, Throwable t) {
                        //  L.error("getOficios " + t.getMessage());
                    }
                });*/

            }

            @Override
            public void onFailure(Call<List<Datos>> call, Throwable t) {
                //  L.error("getOficios " + t.getMessage());
            }
        });


        Call<List<Foto>> callVersiones1 = BovedaClient.getInstanceClient().getApiClient().getphotoPerfil(phon);
        callVersiones1.enqueue(new Callback<List<Foto>>() {
            @Override
            public void onResponse(Call<List<Foto>> call, Response<List<Foto>> response) {
                List<Foto> ejemplo = response.body();

                List<String> listphoto = new ArrayList<String>();

                for (Foto eje : ejemplo) {

                    if (eje.getFperfil() != null) {
                        listphoto.add(eje.getFperfil());
                        f1 = "" +listphoto.get(0);
                    }
                }

                //String strBase64 = "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PPPPPPPPPPPPPPPPPPPPPPPPPPPPPNPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPFPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPFPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPFPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPvt/PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPZrnfPfufPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPLfPPPPPHtfPPPPOlvqvfPPPPPPPPPPPPPPPPPPPPPPPPPPJhkmvPPEfPPPO3fPPPPPPPPPPPPPPPPPPPPPPPPL3fNrG/GvPPPPPPvPPPPPPPPPPPPPPPPPPPPPPPPPvPLvPOPfPPPPPKvPPPPPPPPPPPPPPPPPPPPPPPNZiqcfPKNPPPPPPJvPPPPPPPPPPPPPPPPPPPPPPPTludrbunXvPPPPPDvPPPPPPPPPPPPPPPPPPPPPPPOvhvPPKtlPPPPPKffPPPPPPPPPPPPPPPPPPPPPPPPOVfPPP9WPPPPPGvPPPPPPPPPPPPPPPPPPPPPPPPPPJvPPLQRnvPPJvfPPPPPPPPPPPPPPPPPPPPPPPPPPPfvPPNPPtPPVfPPPPPPPPPPPPPPPPPPPPPPPPPPPPPDTjjnroLF/PPPPPPPPPPPPPPPPPPPPPPPPPPPPPKn0vPPPUPfXrvsvPPPPPPPPPPPPPPPPPPPPPPPPPOMbWsrjrvPPPPLPqhvPPPPPPPPPPPPPPPPPPPPPPPLbnjiutvPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP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";
                String strBase64 = f1;
                if (strBase64 == "" || strBase64 == null) {
                    fotos.setImageResource(R.drawable.icon_user2);
                }else {
                    //String strBase64 = "/9j/4AAQSkZJRgABAQEAYABgAAD/4SiaRXhpZgAATU0AKgAAAAgABgALAAIAAAAmAAAIYgESAAMAAAABAAEAAAExAAIAAAAmAAAIiAEyAAIAAAAUAAAIrodpAAQAAAABAAAIwuocAAcAAAgMAAAAVgAAEUYc6gAAAAgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAFdpbmRvd3MgUGhvdG8gRWRpdG9yIDEwLjAuMTAwMTEuMTYzODQAV2luZG93cyBQaG90byBFZGl0b3IgMTAuMC4xMDAxMS4xNjM4NAAyMDIyOjAxOjE3IDE1OjU3OjA2AAAGkAMAAgAAABQAABEckAQAAgAAABQAABEwkpEAAgAAAAMwMAAAkpIAAgAAAAMwMAAAoAEAAwAAAAEAAQAA6hwABwAACAwAAAkQAAAAABzqAAAACAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAMjAyMjowMToxNyAxNTo1MjowNAAyMDIyOjAxOjE3IDE1OjUyOjA0AAAAAAYBAwADAAAAAQAGAAABGgAFAAAAAQAAEZQBGwAFAAAAAQAAEZwBKAADAAAAAQAC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RGF0ZT4yMDIyLTAxLTE3VDE1OjUyOjA0PC94bXA6Q3JlYXRlRGF0ZT48L3JkZjpEZXNjcmlwdGlvbj48L3JkZjpSREY+PC94OnhtcG1ldGE+DQogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8P3hwYWNrZXQgZW5kPSd3Jz8+/9sAQwADAgIDAgIDAwMDBAMDBAUIBQUEBAUKBwcGCAwKDAwLCgsLDQ4SEA0OEQ4LCxAWEBETFBUVFQwPFxgWFBgSFBUU/9sAQwEDBAQFBAUJBQUJFA0LDRQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQU/8AAEQgAzAG2AwEiAAIRAQMRAf/EAB8AAAEFAQEBAQEBAAAAAAAAAAABAgMEBQYHCAkKC//EALUQAAIBAwMCBAMFBQQEAAAB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17f10+kynEVsyyLD4LL8asPVoylzJzcOaMm5J3W9r/n5Xm+Auk2GgftUa1pmlgrp9m99bwKWLYVG2gZPJxjrX0f8ZPjBZ/BzRbHUbzTrjUUu7j7Oq27Ku07S2ST9K+dv2Z/hn4v8I/Gsza7oOo2UUNtOkt5NCxhZyB0l+6+T6E5r6R+Mnwxg+LPge50OSYW1yHW4tLhhkRzKCAT7EFlPsxrbIYYyOT1/q0OSrzScU1ttpZ/cc/FFTL6nEOF+uVPaUVCClJO999br731KGr/D7wb8dvCemaxqejoW1GziuIbuPCXUIdAwUyL1K5xg5GR0r58+CMN78MP2m7vwTp2pTXeiySXEMysflcLA0iMwHG9SqqSMdx3xVPRY/j58K9Pfwxpmm30tkhZYJIbVLuOPJzmOTBwDycN0z0Br1P8AZx+A+seD9YvvGHjB9/iG8VljgeQSvHvOXkdwSC7dOCcAnnnjkjKea43C1KWGlTqwknUk48qst1frfz/zPQlCnkWW42lXxsK1CpFxpQUuZ3fwyt9nl620v52PoOiiiv1E/EQooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA/9k=";
                    byte[] decodedString = Base64.decode(strBase64, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    fotos.setImageBitmap(decodedByte);
                }
            }

            @Override
            public void onFailure(Call<List<Foto>> call, Throwable t) {

            }
        });


        fotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] opciones = {"Tomar foto", "Seleccionar imagen", "cancelar"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Eleige una opcion");
                builder.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (opciones[which] == "Tomar foto") {
                            abrircamara();
                        } else if (opciones[which] == "Seleccionar imagen") {
                            seleccionarImagen();

                        } else if (opciones[which] == "cancelar") {
                            dialog.dismiss();
                        }
                    }
                });

                builder.show();
            }
        });

      /* String per = foto2.getText().toString();
        HashMap<String, String> params = new HashMap<>();
        params.put("photo",per);

        Call<Responses> call = BovedaClient.getInstanceClient().getApiClient().photoPerfil(p,params);
        call.enqueue(new Callback<Responses>()
        {
            @Override
            public void onResponse (Call < Responses > call, Response < Responses > response){
            }

            @Override
            public void onFailure (Call < Responses > call, Throwable t){


            }
        });*/


        return view;
    }

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_SELECT_IMAGEN = 2;

    private void abrircamara() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

      /* File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(getContext(),
                    "app.cambiosypermutas.cliente.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

        }*/
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }

    public void seleccionarImagen() {
        Intent galeria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galeria, REQUEST_SELECT_IMAGEN);


    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_TAKE = 2;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {


                        Bitmap img = (Bitmap) data.getExtras().get("data");
                        //Bitmap img = (Bitmap) extas.get("data");

                        int ancho = 500;
                        int alto = 500;
                       // img = Bitmap.createScaledBitmap(img,ancho,alto,true);



                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), img);
                        roundedBitmapDrawable.setCircular(true);
                        fotos.setImageDrawable(roundedBitmapDrawable);

                     LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ancho, alto);
                        fotos.setLayoutParams(params);
                        fotos.setImageBitmap(img);
                        fotos.setImageBitmap(img);


                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String fotoEnBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

                foto2.setText(fotoEnBase64);

                        guardarImagenes();


                        break;
                }
            }

        case REQUEST_SELECT_IMAGEN:
                        if (resultCode == Activity.RESULT_OK) {
                            if (requestCode == REQUEST_IMAGE_TAKE) {
                                Uri path = data.getData();
                                Bitmap bitmap = null;

                                try {
                                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);

                                    int ancho = 500;
                                    int alto = 500;

                                    //Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap,ancho,alto,true);

                                    RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                                    roundedBitmapDrawable.setCircular(true);
                                    fotos.setImageDrawable(roundedBitmapDrawable);

                                   LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ancho, alto);
                                    fotos.setLayoutParams(params);
                                    fotos.setImageURI(path);
                                    fotos.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                    fotos.setImageURI(path);

                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                                    String fotoEnBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

                                    foto2.setText(fotoEnBase64);


                                    guardarImagenes();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            }
                        }
                        break;
        }
    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    public static Bitmap crupAndScale(Bitmap source, int scale) {
        int factor = source.getHeight() <= source.getWidth() ? source.getHeight() : source.getWidth();
        int longer = source.getHeight() >= source.getWidth() ? source.getHeight() : source.getWidth();
        int x = source.getHeight() >= source.getWidth() ? 0 : (longer - factor) / 2;
        int y = source.getHeight() <= source.getWidth() ? 0 : (longer - factor) / 2;
        source = Bitmap.createBitmap(source, x, y, factor, factor);
        source = Bitmap.createScaledBitmap(source, scale, scale, false);
        return source;
    }

    private String convertirImgString(Bitmap bitmap) {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 1, array);
        byte[] imagenByte = array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte, Base64.DEFAULT);

        return imagenString;
    }

    private String convertirImgString1(Bitmap bitmap) {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 1, array);
        byte[] imagenByte = array.toByteArray();
        String imagenString1 = Base64.encodeToString(imagenByte, Base64.DEFAULT);

        return imagenString1;
    }

    public void guardarImagenes(){
        String per = foto2.getText().toString();
        HashMap<String, String> params = new HashMap<>();
        params.put("photo",per);

        Call<Responses> call = BovedaClient.getInstanceClient().getApiClient().photoPerfil(phon,params);
        call.enqueue(new Callback<Responses>()
        {
            @Override
            public void onResponse (Call < Responses > call, Response < Responses > response){
            }

            @Override
            public void onFailure (Call < Responses > call, Throwable t){


            }
        });

        Intent intent = new Intent(getContext(), principalMenu.class);
        Toast.makeText(getContext(),"Imagen de perfil actualizada",Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }


}




