package app.oficiodigital.cliente.activities;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.util.HashMap;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.clients.DOXClient;
import app.oficiodigital.cliente.contracts.LoginContract;
import app.oficiodigital.cliente.models.ModelsDB.Phone;
import app.oficiodigital.cliente.models.ModelsDB.Preguntas1;
import app.oficiodigital.cliente.models.ModelsDB.Token;
import app.oficiodigital.cliente.models.ModelsDB.TokenAuth;
import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.notifications.LoadingDialog;
import app.oficiodigital.cliente.presenters.LoginPresenter;
import app.oficiodigital.cliente.storage.CustomerDataPersistence;
import app.oficiodigital.cliente.utils.L;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class
LoginActivity extends BaseActivity implements LoginContract.View {

    @BindView(R.id.login_email_til)
    TextInputLayout emailTil;

    @BindView(R.id.login_password_til)
    TextInputLayout passwordTil;
    @BindView(R.id.login_password_et)
    TextView recoverPasswordTv;
    @BindView(R.id.btnLogin)
    Button loginBt;
    @BindView(R.id.contract_service_button)
    Button mContractService;

    private LoginPresenter presenter;
    private TextView token1,tokena;
    private CustomerDataPersistence mCustomerDataPersistence;
    private EditText email, password;
    public static boolean isAppRunning = false;
    private static final String CHANNEL_ID = "Notificacion";
    private static final int notificationId = 0;


    @Override
    public void onStart() {
        super.onStart();
        isAppRunning = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        isAppRunning = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        email = (EditText)findViewById(R.id.login_email_et);
        password = (EditText)findViewById(R.id.login_password_et);
        token1 = (TextView)findViewById(R.id.token);
        tokena = (TextView) findViewById(R.id.tokenauth);


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        String token = task.getResult();
                        token1.setText(token);

                    }
                });
        FirebaseMessaging.getInstance().subscribeToTopic("oficio_digital_proveedor")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            //msg = getString(R.string.msg_subscribe_failed);
                        }
                        //Log.d("", msg);
                        //Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        if (mCustomerDataPersistence == null)
            mCustomerDataPersistence = new CustomerDataPersistence(this);

        if (presenter == null)
            presenter = new LoginPresenter(this, this);
    }


    public void recuperar(View view) {

        startActivity(new Intent(this, RecoverPhone.class));
    }

    /*@OnClick(R.id.btnLogin)
    public void login() {
        String email = emailEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();
        boolean fieldsOK = checkFields(new String[]{email, password});

        if (NetworkState.isConnectionAvailable(this)) {
            if (fieldsOK) {
                if (phoneValid(email)) {
                    displayLoadingMsg(true);
                    presenter.login(new User(email, password));
                }
            } else {
                Toast.makeText(this, R.string.check_internet_connection, Toast.LENGTH_SHORT).show();
            }
        }

    }*/


    public void info(View view) {
        Intent intent1 = new Intent(this, ViewDSchool.class);
        startActivity(intent1);
    }


    public void contratar(View view) {
        startActivity(new Intent(this, Register.class));
    }
    public void login(View view){
        HashMap<String, String> params = new HashMap<>();
        params.put("username", email.getText().toString());
        params.put("password", password.getText().toString());
        params.put("client_id", "g3b259fde3ed9ff3843839b");
        params.put("client_secret", "3d7f5f8f793d59c25502c0ae8c4a95b");
        params.put("grant_type", "password");

        Call<Responses> call = DOXClient.getInstanceClient().getApiClient().login(params);
        call.enqueue(new Callback<Responses>() {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                Responses userLogin = response.body();

                /*if(response.body().getCode() == 206){
                    Toast.makeText(getApplication(),"Usuario no existe",Toast.LENGTH_SHORT).show();
                }*/

                if (userLogin != null) {
                    if (response.body().getResponse() != null) {

                        String token = response.body().getResponse().getAuth().getToken();
                        String phone = response.body().getResponse().getUser().getPhone();
                        tokena.setText(token);
                        Phone phon = new Phone();
                        phon.setPhone(phone);
                        phon.save();

                        TokenAuth tokenauth = new TokenAuth();
                        tokenauth.setTokenauth(token);
                        tokenauth.save();

                        String id = response.body().getResponse().getUser().getId();
                        Token userId = new Token();
                        userId.setUserId(id);
                        userId.save();

                        alerta();
                        //pagos();
                        Intent intent = new Intent(getApplication(), principalMenu.class);
                        intent.putExtra("phone", email.getText().toString());
                        intent.putExtra("token",tokena.getText().toString());
                        intent.putExtra("id",id);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplication(), "usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplication(), "usuario o contraseña no existe", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                presenter.onLoginFail(t.getMessage());
            }
        });




    }

    private class OnTokenAcquired implements AccountManagerCallback<Bundle> {
        @Override
        public void run(AccountManagerFuture<Bundle> result) {
            // Get the result of the operation from the AccountManagerFuture.
            Bundle bundle = null;
            try {
                bundle = result.getResult();
            } catch (AuthenticatorException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (OperationCanceledException e) {
                e.printStackTrace();
            }

            // The token is a named value in the bundle. The name of the value
            // is stored in the constant AccountManager.KEY_AUTHTOKEN.
            String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
        }

    }
    @Override
    public void displayLoadingMsg(boolean display) {
        if (display) {
            String msg = getString(R.string.logging_msg);
            LoadingDialog.show(this, msg);
        } else {
            LoadingDialog.dismiss();
        }
    }
    public void alerta(){

        String msg = getString(R.string.logging_msg);
        LoadingDialog.show(this, msg);

    }

    @Override
    public void onLoginSuccess(Responses userLogin) {
        displayLoadingMsg(false);

        mCustomerDataPersistence.saveAuth(userLogin.getResponse().getUser().getId(),
                userLogin.getResponse().getUser().getEmail(), userLogin.getResponse().getAuth());

        if (userLogin.getResponse().getUser().getActiveStatus() != 0) {
            if (userLogin.getResponse().getUser().getProfileStatus() == 0) {
                mCustomerDataPersistence.saveUserData(userLogin.getResponse().getUser());
                mCustomerDataPersistence.setIsSignCompleted(true);

                if (userLogin.getResponse().getUser().getPayStatus() == 0)
                    // startActivity(new Intent(this, MainActivity.class));
                    Toast.makeText(this,"Hola mundo",Toast.LENGTH_LONG).show();
                else {
                    Intent intent = new Intent(this, MainActivity.class);
                    // intent.putExtra(AddCCPaymentActivity.IS_FIRST_PAYMENT, true);
                    startActivity(intent);
                    Toast.makeText(this,"Hola mundo",Toast.LENGTH_LONG).show();
                }

            } else
                startActivity(new Intent(this, MainActivity.class));
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("phone",email.getText().toString());
            startActivity(intent);
            Toast.makeText(this,"Hola mundo",Toast.LENGTH_LONG).show();

            finish();
        } else {
            //Snack.show(findViewById(android.R.id.content), getString(R.string.no_login_inactive_account));
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("phone",email.getText().toString());
            startActivity(intent);
            Toast.makeText(this,"Hola mundo",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoginFail(String error) {
        displayLoadingMsg(false);

        String failMsg = getString(R.string.logging_fail);
        //showFailMsg(error);
    }

    @Override
    public void onNeedCompleteRegister() {
        displayLoadingMsg(false);

        startActivity(new Intent(this, PrivacyPolicies.class));
    }

    public void pagos(){
        String deviceSessionId = "139e5a687c52A428b41e0f8cce2b5dba";
        String mToken = "Bearer " + "m4BcuNy7CLA7EoOdNd3g37QbSpEtxYnh2FYJwVK6";

        Call<Responses> call = DOXClient.getInstanceClient().getApiClient()
                .addPayment(deviceSessionId, mToken);

        call.enqueue(new Callback<Responses>() {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                if (response.body() != null) {
                    if (response.code() == 200) {
                        // mAddPaymentPresenter.onAddPayment();
                        Toast.makeText(getApplication(), "success", Toast.LENGTH_SHORT).show();
                    } else
                        // mAddPaymentPresenter.onAddPaymentFail(response.body().getMessage());
                        Toast.makeText(getApplication(), "ocurrio un error al realizar el pago", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplication(), "no hay tarjetas", Toast.LENGTH_SHORT).show();

                    // mAddPaymentPresenter.onAddPaymentFail(mContext.getString(R.string.error_doing_payment));
                    //
                }
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                L.error("Add payment " + t.getMessage());
                //mAddPaymentPresenter.onAddPaymentFail(mContext.getString(R.string.error_doing_payment));
            }
        });
    }

}