package app.cambiosypermutas.cliente.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import app.cambiosypermutas.cliente.R;
import app.cambiosypermutas.cliente.clients.DOXClient;
import app.cambiosypermutas.cliente.contracts.AddCreditCardContract;
import app.cambiosypermutas.cliente.models.CreditCard;
import app.cambiosypermutas.cliente.models.ModelsDB.Phone;
import app.cambiosypermutas.cliente.models.ModelsDB.TokenAuth;
import app.cambiosypermutas.cliente.models.Responses;
import app.cambiosypermutas.cliente.notifications.LoadingDialog;
import app.cambiosypermutas.cliente.presenters.AddCreditCardPresenter;
import app.cambiosypermutas.cliente.settings.Settings;
import app.cambiosypermutas.cliente.utils.L;
import butterknife.BindView;
import butterknife.ButterKnife;

import mx.openpay.android.*;
import mx.openpay.android.OperationCallBack;
import mx.openpay.android.OperationResult;

import mx.openpay.android.exceptions.OpenpayServiceException;
import mx.openpay.android.exceptions.ServiceUnavailableException;
import mx.openpay.android.model.Card;
import mx.openpay.android.model.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NandoVelazquez on 8/10/16.
 */
public class AddCard extends BaseActivity implements AddCreditCardContract.View {

    public final static String IS_FIRST_PAYMENT = "first_payment";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.main_credit_card)
    CheckBox mMainPayment;
    private AddCreditCardPresenter mAddPaymentPresenter;
    public  String mDeviceSessionId;
    private String mCardToken;
    private boolean mIsFirstPayment;
    private Openpay mOpenpay;
    private String token;
    private  String tok;
    private String phon,ofi;
    private TextView txtDateEnd,txtCardName, txtCardNum,txtCVV,token1;
    private TextView oficio,nombre,ap1,ap2,hora,fecha,tokenPhone,phone,phoneCliente,email;


    private CreditCard creditCard;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        ButterKnife.bind(this);

        oficio = (TextView) findViewById(R.id.ofi);
        nombre = (TextView) findViewById(R.id.nom);
        ap1 = (TextView) findViewById(R.id.ap1);
        ap2 = (TextView) findViewById(R.id.ap2);
        hora = (TextView) findViewById(R.id.hora);
        fecha = (TextView) findViewById(R.id.fecha);
        tokenPhone = (TextView) findViewById(R.id.tokenPhone);
        phone = (TextView) findViewById(R.id.phone);
        phoneCliente = (TextView) findViewById(R.id.phoneCliente);
        email = (TextView) findViewById(R.id.email);

        ofi = getIntent().getExtras().getString("oficio");
        String nom = getIntent().getExtras().getString("nombre");
        String ape1 = getIntent().getStringExtra("ap1");
        String ape2 = getIntent().getStringExtra("ap2");
        String phon = getIntent().getStringExtra("phone");
        String hor = getIntent().getStringExtra("hora");
        String mai = getIntent().getStringExtra("email");
        String fec = getIntent().getStringExtra("fecha");
        String phon1 = getIntent().getStringExtra("phoneCliente");
        String tok = getIntent().getStringExtra("tokenPhone");

        oficio.setText(ofi);
        nombre.setText(nom);
        ap1.setText(ape1);
        ap2.setText(ape2);
        hora.setText(hor);
        fecha.setText(fec);
        tokenPhone.setText(tok);
        phone.setText(phon);
        phoneCliente.setText(phon1);
        email.setText(mai);
        txtDateEnd = (TextView)findViewById(R.id.txtDateEnd);
        txtCardName = (TextView) findViewById(R.id.txtCardName);
        txtCardNum = (TextView) findViewById(R.id.txtCardNumber);
        txtCVV = (TextView) findViewById(R.id.txtCVV);
        token1  =(TextView) findViewById(R.id.token);

        token = getIntent().getStringExtra("token");
        token1.setText(token);

        //setupUI();
        mAddPaymentPresenter = new AddCreditCardPresenter(this, this);
        mIsFirstPayment = getIntent().getBooleanExtra(IS_FIRST_PAYMENT, false);

        txtDateEnd.addTextChangedListener(new TextWatcher() {
            int len = 0;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = txtDateEnd.getText().toString();
                len = str.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = txtDateEnd.getText().toString();
                if (str.length() == 2 && len < str.length()) {//len check for backspace
                    txtDateEnd.append("/");

                }
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupUI() {
        setToolbar();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.chevron_left);

        String name = getString(R.string.card_data);
        actionBar.setTitle(name);
    }


    public void addMethod(View view) {
        String cardName = txtCardName.getText().toString().trim();
        String cardNumber = txtCardNum.getText().toString().trim();
        String expiration = txtDateEnd.getText().toString().trim();
        String cvv = txtCVV.getText().toString().trim();
        String[] fields = new String[]{cardName, cardNumber, expiration, cvv};

        if (checkFields(fields)) {
            showLoadingMsg(true);
            final String expirationMonth = expiration.substring(0, 2);
            final String expirationYear = expiration.substring(3, 5);


            Card card = new Card();
            card.holderName(cardName);
            card.cardNumber(cardNumber);
            card.setExpirationMonth(expirationMonth);
            card.setExpirationYear(expirationYear);
            card.cvv2(cvv);


            mOpenpay = new Openpay(Settings.MERCHANTID, Settings.OP_PUBLIC_API_KEY, false);
            //mDeviceSessionId = mOpenpay.getDeviceCollectorDefaultImpl().setup(this);

            mOpenpay.createToken(card, new OperationCallBack<Token>() {
                @Override
                public void onError(OpenpayServiceException error) {
                    showLoadingMsg(false);
                    Toast.makeText(getApplication(), "error1", Toast.LENGTH_SHORT).show();
                    //Snack.show(findViewById(android.R.id.content), error.getMessage());
                }

                @Override
                public void onCommunicationError(ServiceUnavailableException error) {
                    showLoadingMsg(false);
                    //Snack.show(findViewById(android.R.id.content), error.getMessage());
                    Toast.makeText(getApplication(), "error2", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(OperationResult<Token> operationResult) {
                    mDeviceSessionId = mOpenpay.getDeviceCollectorDefaultImpl().setup(AddCard.this);
                    mCardToken = operationResult.getResult().getId();
                    addTheCard(expirationMonth, expirationYear);
                }
            });
        }
    }

    private void addTheCard(String expirationMonth, String expirationYear) {
        creditCard = new CreditCard();
        creditCard.setName(txtCardName.getText().toString());
        creditCard.setHolder_name(txtCardName.getText().toString());
        creditCard.setNumber(txtCardNum.getText().toString());
        creditCard.setCard_number(txtCardNum.getText().toString());
        creditCard.setExpirationMonth(expirationMonth);
        creditCard.setExpirationYear(expirationYear);
        creditCard.setToken(mCardToken);
        creditCard.setCvv(txtCVV.getText().toString());
        creditCard.setDeviceSessionId(mDeviceSessionId);

        addCreditCard();

        //if (mMainPayment.isChecked())
        //   creditCard.setMain(1);
        //creditCard.setCvv("729");

        //mAddPaymentPresenter.addCreditCard(creditCard);
    }

    public void phone(){
        List<Phone> list1 = Phone.listAll(Phone.class);
        for (Phone pho : list1) {
            String phone = "";

            phone = pho.getPhone();
            phon = phone;
        }

    }

    public void alerta1(){
        String msg = getString(R.string.creando);
        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Creando cuenta");
        progress.setMessage(msg);
        progress.show();
    }

    public void addCreditCard() {

        List<TokenAuth> list1 = TokenAuth.listAll(TokenAuth.class);
        for (TokenAuth pho : list1) {
            String phone = "";

            phone = pho.getTokenauth();
            tok = phone;
        }

        String authHeader = "Bearer " + tok ;
        Call<Responses> call = DOXClient.getInstanceClient()
                .getApiClient().addCard(creditCard, authHeader);


        call.enqueue(new Callback<Responses>() {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        //Toast.makeText(getApplication(), "Success",Toast.LENGTH_SHORT).show();
                        alerta1();
                        Intent intent = new Intent(getApplication(), EnvioSolicitud.class);
                        intent.putExtra("oficio",oficio.getText().toString());
                        intent.putExtra("nombre", nombre.getText().toString());
                        intent.putExtra("ap1", ap1.getText().toString());
                        intent.putExtra("ap2", ap2.getText().toString());
                        intent.putExtra("hora", hora.getText().toString());
                        intent.putExtra("fecha", fecha.getText().toString());
                        intent.putExtra("tokenPhone", tokenPhone.getText().toString());
                        intent.putExtra("phone",phoneCliente.getText().toString());
                        intent.putExtra("phoneCliente", phone.getText().toString());
                        intent.putExtra("email", email.getText().toString());
                        startActivity(intent);
                    } else
                        Toast.makeText(getApplication(), "Datos incorrectos",Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplication(), "No se agrego la tarjeta",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                L.error("Add Credit Card " + t.getMessage());
                Toast.makeText(getApplication(), "error2",Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onCreditCardAdded() {
        showLoadingMsg(false);
        if (mIsFirstPayment) {
            Intent intent = new Intent(this, PayActivity.class);
            intent.putExtra(PayActivity.DEVICE_ID, String.valueOf(mDeviceSessionId));
            startActivity(intent);

        } else
            finish();
    }

    @Override
    public void onCreditCardAddedFail(String error) {
        showLoadingMsg(false);
        Toast.makeText(getApplication(),R.string.error_adding_credit_card,Toast.LENGTH_SHORT).show();
        //Snack.show(findViewById(android.R.id.content), error);
    }

    public void alerta(){

        String msg = getString(R.string.agregarTarjeta);
        LoadingDialog.show(this, msg);

    }

    public void infor(View view){

        ImageView image = new ImageView(this);
        image.setImageResource(R.mipmap.cvv2);

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this).
                        setTitle("¿Qué es el CVV?").
                        setMessage("El cvv es un número de 3 digitos que se encuentra en el reverso de tu tarjeta").
                        setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).
                        setView(image);
        builder.create().show();
    }


}
