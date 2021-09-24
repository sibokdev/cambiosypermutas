package app.oficiodigital.cliente.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.clients.BovedaClient;
import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.storage.ModelsBD.Oficios;
import app.oficiodigital.cliente.storage.ModelsBD.Preguntas1;
import app.oficiodigital.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ari on 10/03/2021.
 */

public class Register extends BaseActivity implements View.OnClickListener {

    private EditText et_nombre, et_ap, res1, res2;
    private TextView tv_edad, FechaN, tv_ofi, tv_inefront, phone, tv_ineback, resul, resul1, tv_edadm,
            pregunta1, pregunta2, poci1, poci2, token1;
    private Button bt_fecha, bt_inefront, bt_ineback;
    private RadioButton rb_si, rb_no, rb_si1, rb_no1;
    private ImageView img_inefront, img_ineback;
    private Spinner p1, p2;
    private TextInputLayout pw, pw2, n, ap1, ap2, ine, m, ti_res1, ti_res2;

    private static final String CARPETA_PRINCIPAL = "app.oficiodigita.proveedor"; //directorio principal
    private static final String CARPETA_IMAGEN = "Pictures"; //carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;


    //private CompleteRegisterPresenter mPresenter;

    private int aa = 0, ma = 0, da = 0, años = 0, mes = 0, dia = 0, año = 0, messs = 0, dias = 0, años1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_nombre = (EditText) findViewById(R.id.et_nombre);

        bt_fecha = (Button) findViewById(R.id.boton_fecha);
        tv_edad = (TextView) findViewById(R.id.tvEdad);
        tv_edadm = (TextView) findViewById(R.id.tvApareceEdad);

        tv_ofi = (TextView) findViewById(R.id.oficios);
        res1 = (EditText) findViewById(R.id.res1);
        res2 = (EditText) findViewById(R.id.res2);

        pw2 = (TextInputLayout) findViewById(R.id.ti_password2);
        pw = (TextInputLayout) findViewById(R.id.ti_password);
        pregunta1 = (TextView) findViewById(R.id.pregunta1);
        pregunta2 = (TextView) findViewById(R.id.pregunta2);

        n = (TextInputLayout) findViewById(R.id.ti_nombre);
        ap1 = (TextInputLayout) findViewById(R.id.ti_ap1);
        ap2 = (TextInputLayout) findViewById(R.id.ti_ap2);
        m = (TextInputLayout) findViewById(R.id.ti_mail);
        ti_res1 = (TextInputLayout) findViewById(R.id.ti_res1);
        ti_res2 = (TextInputLayout) findViewById(R.id.ti_res2);



        phone = (TextView) findViewById(R.id.phone);


        p1 = (Spinner) findViewById(R.id.preguntas);
        p2 = (Spinner) findViewById(R.id.preguntas2);

        token1 = (TextView) findViewById(R.id.token);
        String token = getIntent().getStringExtra("tokenPhone");
        token1.setText(token);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }
        //primer pregunta
        primerPregunta();

        //segunda pregunta
        segundaPregunta();
        //

        String phon = getIntent().getStringExtra("phone");
        phone.setText(phon);


        bt_fecha.setOnClickListener(this);

        //Centro de trabajo si o no
       /* rb_si.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    resul.setText("Si");
                } else {
                    resul.setText("No");
                }
            }
        });

        rb_si1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    resul1.setText("Si");
                } else {
                    resul1.setText("No");
                }
            }
        });*/

        /*List<Oficios> list1 = Oficios.listAll(Oficios.class);
        List<String> lis1 = new ArrayList<String>();
        Oficios preguntas1 = new Oficios();
        for (int i = 0; i < list1.size(); i++) {
            preguntas1 = list1.get(i);
            lis1.add(preguntas1.getOficios());
            //Preguntas1.deleteAll(Preguntas1.class);
        }

        String[] oficiosArray = new String[lis1.size()];

        oficiosArray = lis1.toArray(oficiosArray);

        ArrayList<Integer> ofiList = new ArrayList<>();

        boolean[] selecOficio;

        selecOficio = new boolean[oficiosArray.length];

        String[] finalOficiosArray = oficiosArray;

        tv_ofi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);

                builder.setTitle("Seleccione oficio"); //titulo
                builder.setCancelable(false);
                AlertDialog.Builder builder1 = builder.setMultiChoiceItems(finalOficiosArray, selecOficio, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int a, boolean j) {
                        if (j) {
                            if (!ofiList.contains(a)) {
                                ofiList.add(a);
                                Collections.sort(ofiList);
                            } else {
                                ofiList.remove(a);
                            }

                        }
                    }
                });

                builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() { //metodo guardar
                    @Override
                    public void onClick(DialogInterface dialog, int a) {

                        String item = "";

                        for (int i = 0; i < ofiList.size(); i++) {
                            item = item + finalOficiosArray[ofiList.get(i)];
                            if (i != ofiList.size() - 1) {
                                item = item + ", ";
                            }
                        }

                        for (int i = 0; i < selecOficio.length; i++) {
                            selecOficio[i] = false;
                            ofiList.clear();

                        }

                        tv_ofi.setText(item);//datos ingresados
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int a) {

                        dialog.dismiss();

                    }
                });

                builder.show();

            }
        });*/

    }


    //Seleccionar fecha
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClick(View view) {
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        dias = today.monthDay;
        messs = today.month;
        años = today.year;


        if (view.getId() == R.id.boton_fecha)

        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date date = null;
            try {
                date = sdf.parse("1970/01/01");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar ca = Calendar.getInstance();
            ca.setTime(date);

            año = ca.get(Calendar.YEAR);
            mes = ca.get(Calendar.MONTH);
            dia = ca.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog recogerfecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int año, int mess, int diaa) {
                    final int mesActual = mess + 1;
                    String diaFormato = (diaa < 10) ? "0" + String.valueOf(diaa) : String.valueOf(diaa);
                    String mesFormato = (mesActual < 10) ? "0" + String.valueOf(mesActual) : String.valueOf(mesActual);
                    FechaN.setText("" + diaFormato + "/" + mesFormato + "/" + año);
                    aa = año;
                    ma = Integer.parseInt(mesFormato);
                    da = Integer.parseInt(diaFormato);
                    tv_edadm.setText(calcular(años, (messs + 1), aa, ma, dias, da));
                    bt_fecha.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(244, 207, 62)));

                }

            }, año, messs, dias);

            recogerfecha.show();

        }

    }

    public String calcular(int a, int m, int aa, int ma, int d, int da) {
        int añoss = 0;
        if (ma <= m && da <= d) {

            añoss = a - aa;
        } else {
            añoss = a - aa - 1;
        }

        return +añoss + "";
    }

    //boton camara 1
    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    String currentPhotoPath1;

    private File createImageFile1() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath1 = image.getAbsolutePath();
        return image;
    }


    static final int REQUEST_TAKE_PHOTO = 1;

    private void abrircamara() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "app.oficiodigital.proveedor.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    //boton imagen 2
    static final int REQUEST_TAKE_PHOTOS = 1;

    private void abrircamara1() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile1();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "app.oficiodigital.proveedor.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTOS);
            }
        }
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_CAPTURES = 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURES) {
            if (resultCode == RESULT_OK) {

                try {
                    Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
                    bitmap = crupAndScale(bitmap, 10);
                    img_inefront.setImageBitmap(bitmap);
                    Toast.makeText(this, "Imagen Guardada", Toast.LENGTH_SHORT).show();
                    bt_inefront.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(244, 207, 62)));
                    String imagenString = convertirImgString(bitmap);

                    tv_inefront.setText(imagenString);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap1 = BitmapFactory.decodeFile(currentPhotoPath1);
                    bitmap1 = crupAndScale(bitmap1, 10);


                    img_ineback.setImageBitmap(bitmap1);
                    Toast.makeText(this, "Imagen Guardada2", Toast.LENGTH_SHORT).show();
                    bt_ineback.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(244, 207, 62)));


                    String imagenString1 = convertirImgString1(bitmap1);

                    tv_ineback.setText(imagenString1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
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
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, array);
        byte[] imagenByte = array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte, Base64.DEFAULT);

        return imagenString;
    }

    private String convertirImgString1(Bitmap bitmap) {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, array);
        byte[] imagenByte = array.toByteArray();
        String imagenString1 = Base64.encodeToString(imagenByte, Base64.DEFAULT);

        return imagenString1;
    }

    public void primerPregunta() {
        List<Preguntas1> list = Preguntas1.listAll(Preguntas1.class);
        List<String> lis = new ArrayList<String>();
        Preguntas1 preguntas = new Preguntas1();
        for (int i = 0; i < list.size(); i++) {
            preguntas = list.get(i);
            lis.add(preguntas.getPreguntas());
            //Preguntas1.deleteAll(Preguntas1.class);
        }

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplication(), R.layout.spinner_colonia, lis);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        p1.setAdapter(adapter1);

        p1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                String slect = p1.getSelectedItem().toString();
                int sele = p1.getSelectedItemPosition();
                String se = "" + sele;
                //pregunta1.setText(slect);
               // poci1.setText(se);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }

    public void segundaPregunta() {
        List<Preguntas1> list1 = Preguntas1.listAll(Preguntas1.class);
        List<String> lis1 = new ArrayList<String>();
        Preguntas1 preguntas1 = new Preguntas1();
        for (int i = 0; i < list1.size(); i++) {
            preguntas1 = list1.get(i);
            lis1.add(preguntas1.getPreguntas());
            //Preguntas1.deleteAll(Preguntas1.class);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), R.layout.spinner_colonia, lis1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        p2.setAdapter(adapter);


        p2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                String slect = p2.getSelectedItem().toString();
                int sele = p2.getSelectedItemPosition();
                String se = "" + sele;
                //pregunta2.setText(slect);
                //poci2.setText(se);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }


    //metodo boton Siguiente para pasar los datos
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void siguiente(View view) {

        Pattern pat = Pattern.compile("[A-Z-]{6}[0-9]{8}[A-Za-z]{1}[0-9]{3}");
        Pattern pater = Pattern.compile("[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{1,5}");
        EditText input = pw.getEditText();
        String inputText = input.getText().toString().trim();
        EditText input2 = pw2.getEditText();
        String inputText2 = input2.getText().toString().trim();

            if (et_nombre.length() == 0) {
                n.setError("Ingresa Nombre");
            } else {
                n.setErrorEnabled(false);
                if (res1.length() == 0) {
                    ti_res1.setError("Ingresa respuesta");
                } else {
                    ti_res1.setErrorEnabled(false);
                    if (res2.length() == 0) {
                        ti_res2.setError("Ingresa respuesta");
                    } else {
                                    if (inputText.isEmpty()) {
                                        pw.setError("Ingresa contraseña");
                                    } else {
                                        pw.setErrorEnabled(false);
                                        if (inputText2.isEmpty()) {
                                            pw2.setError("Ingresa contraseña");
                                        } else {
                                            pw2.setErrorEnabled(false);
                                            if (resul.length() == 0 && resul1.length() == 0) {
                                                Toast.makeText(this, "Ingresa datos de facturación ", Toast.LENGTH_SHORT).show();
                                            } else {
                                                if (tv_ofi.length() == 0 || tv_ofi == null) {
                                                    Toast.makeText(this, "Ingresa oficio ", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    if (tv_inefront.length() == 0 || tv_ineback.length() == 0) {
                                                        Toast.makeText(this, "Ingresa captura de INE ", Toast.LENGTH_SHORT).show();
                                                    } else {


                                                        HashMap<String, String> params = new HashMap<>();
                                                       // params.put("email", ma);

                                                        Call<Responses> call = BovedaClient.getInstanceClient().getApiClient().validateEmail(params);
                                                        call.enqueue(new Callback<Responses>() {
                                                            @Override
                                                            public void onResponse(Call<Responses> call, Response<Responses> response) {
                                                                if (response.isSuccessful()) {
                                                                    if (response.body().getCode() == 200) {
                                                                        //LoadingDialog.show(InsertCode.this, getString(R.string.validando_code));
                                                                        m.setError("Este Correo electronico ya ha sido registrado");
                                                                        Toast.makeText(getApplication(), "correo ya ingresado", Toast.LENGTH_SHORT).show();
                                                                    } else {
                                                                        m.setErrorEnabled(false);
                                                                        Intent sigue = new Intent(getApplication(), LoginActivity.class);
                                                                        sigue.putExtra("office", tv_ofi.getText().toString());
                                                                        sigue.putExtra("nombre", et_nombre.getText().toString());
                                                                        sigue.putExtra("birthday", FechaN.getText().toString());
                                                                        sigue.putExtra("age", tv_edadm.getText().toString());
                                                                        sigue.putExtra("phone", phone.getText().toString());
                                                                        sigue.putExtra("INEfront", tv_inefront.getText().toString());
                                                                        sigue.putExtra("INEBack", tv_ineback.getText().toString());
                                                                        sigue.putExtra("facturapropia", resul.getText().toString());
                                                                        sigue.putExtra("facturapp", resul1.getText().toString());
                                                                        sigue.putExtra("respuesta1", res1.getText().toString());
                                                                        sigue.putExtra("respuesta2", res2.getText().toString());
                                                                        sigue.putExtra("pocision1", poci1.getText().toString());
                                                                        sigue.putExtra("pocision2", poci2.getText().toString());
                                                                        sigue.putExtra("pregunta1", pregunta1.getText().toString());
                                                                        sigue.putExtra("pregunta2", pregunta2.getText().toString());
                                                                        sigue.putExtra("tokenPhone", token1.getText().toString());
                                                                        startActivity(sigue);
                                                                    }
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<Responses> call, Throwable t) {
                                                                L.error("login " + t.getMessage());
                                                                //mPresenter.loginFailure(mContext.getString(R.string.login_error));
                                                            }
                                                        });

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }




}

