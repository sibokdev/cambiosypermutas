package app.cambiosypermutas.cliente.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import app.cambiosypermutas.cliente.clients.BovedaClient;
import app.cambiosypermutas.cliente.models.Datos;
import app.cambiosypermutas.cliente.models.ModelsDB.Phone;
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
    String phon;

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

        ll_telefono.setOnClickListener(new View.OnClickListener() {
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

        });

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


       /* fotos.setOnClickListener(new View.OnClickListener() {
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
        });*/

        String per = foto2.getText().toString();
        HashMap<String, String> params = new HashMap<>();
        params.put("perfil",per);

        Call<Responses> call = BovedaClient.getInstanceClient().getApiClient().photoPerfil(phone.getText().toString(),params);
        call.enqueue(new Callback<Responses>()
        {
            @Override
            public void onResponse (Call < Responses > call, Response < Responses > response){
            }

            @Override
            public void onFailure (Call < Responses > call, Throwable t){


            }
        });


        return view;
    }

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_SELECT_IMAGEN = 2;

    private void abrircamara() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(getActivity(),
                    "app.oficioDigital.cliente.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

        }
        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

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

                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), img);
                        roundedBitmapDrawable.setCircular(true);
                        fotos.setImageDrawable(roundedBitmapDrawable);

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ancho, alto);
                        fotos.setLayoutParams(params);
                        fotos.setImageBitmap(img);
                        fotos.setImageBitmap(img);


                        String imagenString = convertirImgString(img);

                        foto2.setText(imagenString);
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
                                    RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                                    roundedBitmapDrawable.setCircular(true);
                                    fotos.setImageDrawable(roundedBitmapDrawable);

                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ancho, alto);
                                    fotos.setLayoutParams(params);
                                    fotos.setImageURI(path);
                                    fotos.setImageURI(path);

                                    String imagenString1 = convertirImgString1(bitmap);

                                    foto2.setText(imagenString1);
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
        params.put("perfil",per);

        Call<Responses> call = BovedaClient.getInstanceClient().getApiClient().photoPerfil(phone.getText().toString(),params);
        call.enqueue(new Callback<Responses>()
        {
            @Override
            public void onResponse (Call < Responses > call, Response < Responses > response){
            }

            @Override
            public void onFailure (Call < Responses > call, Throwable t){


            }
        });

    }


}




