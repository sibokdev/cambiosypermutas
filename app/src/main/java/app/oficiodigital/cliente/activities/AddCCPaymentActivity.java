package app.oficiodigital.cliente.activities;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;
import java.io.Serializable;
import java.text.BreakIterator;
import java.util.List;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.clients.DOXClient;
import app.oficiodigital.cliente.contracts.AddCreditCardContract;
import app.oficiodigital.cliente.models.CreditCard;
import app.oficiodigital.cliente.models.ModelsDB.DeviceId;
import app.oficiodigital.cliente.models.ModelsDB.Phone;
import app.oficiodigital.cliente.models.ModelsDB.TokenAuth;
import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.notifications.LoadingDialog;
import app.oficiodigital.cliente.notifications.Snack;
import app.oficiodigital.cliente.presenters.AddCreditCardPresenter;
import app.oficiodigital.cliente.presenters.AddPaymentPresenter;
import app.oficiodigital.cliente.settings.Settings;
import app.oficiodigital.cliente.storage.CustomerDataPersistence;
import app.oficiodigital.cliente.utils.L;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import mx.openpay.android.DeviceCollectorDefaultImpl;
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
public class AddCCPaymentActivity extends BaseActivity implements AddCreditCardContract.View {

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
    private String phon;
    private TextView txtDateEnd,txtCardName, txtCardNum,txtCVV,token1,device;

    private CreditCard creditCard;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cc_payment);
        ButterKnife.bind(this);

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
                    mDeviceSessionId = mOpenpay.getDeviceCollectorDefaultImpl().setup(AddCCPaymentActivity.this);
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

        DeviceId phon = new DeviceId();
        String phone = ""+mDeviceSessionId;
        phon.setDevice_session_id(phone);
        phon.save();

        addCreditCard();



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
        String msg = getString(R.string.agregando);
        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Agregando tarjeta");
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

        String authHeader = "Bearer " + tok;
        Call<Responses> call = DOXClient.getInstanceClient()
                .getApiClient().addCard(creditCard, authHeader);


        call.enqueue(new Callback<Responses>() {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {

                        alerta1();
                        Toast.makeText(getApplication(), "Tarjeta agregada",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplication(), PrincipalMetodos.class);
                        //intent.putExtra("token", tok);
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
