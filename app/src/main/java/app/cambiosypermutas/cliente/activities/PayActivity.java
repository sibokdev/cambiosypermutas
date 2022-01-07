package app.cambiosypermutas.cliente.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

import app.cambiosypermutas.cliente.R;
import app.cambiosypermutas.cliente.contracts.AddPaymentContract;
import app.cambiosypermutas.cliente.contracts.AddPaypalPaymentContract;
import app.cambiosypermutas.cliente.models.Payment;
import app.cambiosypermutas.cliente.models.Paypal;
import app.cambiosypermutas.cliente.notifications.Snack;
import app.cambiosypermutas.cliente.presenters.AddPaymentPresenter;
import app.cambiosypermutas.cliente.presenters.AddPaypalPaymentPresenter;
import app.cambiosypermutas.cliente.storage.CustomerDataPersistence;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



/**
 * Created by NandoVelazquez on 8/10/16.
 */
public class PayActivity extends BaseActivity implements AddPaymentContract.View, AddPaypalPaymentContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnPay) Button btnAddPaymentMethod;
    @BindView(R.id.amount) TextView mAnnualCost;

    public static final String DEVICE_ID = "device_id";
    public static final String IS_FROM_PAYPAL = "is_from_paypal";
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
    private static final String CONFIG_CLIENT_ID = "AQJiIsucYZGtO3MNUqMj5E_SK-yAVNhlVSlhRXAx8Hp-1ugFKot-QPFqX5jbFjGuYcFoM1qoXOv87Qtk";
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static final int REQUEST_CODE_PROFILE_SHARING = 3;

    private static final PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID);

    private AddPaypalPaymentPresenter mAddPaypalPaymentPresenter;
    private String mDeviceSessionId;
    private float mMembershipCost;
    private boolean mIsFromPayPal;

    private AddPaymentPresenter mAddPaymentPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);

        //setupUI();
        mAddPaymentPresenter = new AddPaymentPresenter(this, this);
        mAddPaypalPaymentPresenter = new AddPaypalPaymentPresenter(this, this);

        mDeviceSessionId = getIntent().getStringExtra(DEVICE_ID);
        mIsFromPayPal = getIntent().getBooleanExtra(IS_FROM_PAYPAL, false);

        mAddPaymentPresenter.getAnnualCost();
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {

                    Log.i("paymentExample", confirm.toJSONObject().getString("response"));
                    showLoadingMsg(true);
                    Gson gson = new Gson();
                    Paypal paypal = gson.fromJson(confirm.toJSONObject().getString("response"), Paypal.class);
                    mAddPaypalPaymentPresenter.addPaypalPayment(paypal);

                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
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

        String name = getString(R.string.make_payment);
        actionBar.setTitle(name);
    }

    @OnClick(R.id.btnPay)
    public void payAndContinue() {
        showLoadingMsg(true);
        if (mIsFromPayPal) {
            Intent intent = new Intent(this, PayPalService.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            startService(intent);
            onBuyPressed();
        } else
            mAddPaymentPresenter.addPayment(mDeviceSessionId);

    }

    public void onBuyPressed() {
        /*
         * PAYMENT_INTENT_SALE will cause the payment to complete immediately.
         * Change PAYMENT_INTENT_SALE to
         *   - PAYMENT_INTENT_AUTHORIZE to only authorize payment and capture funds later.
         *   - PAYMENT_INTENT_ORDER to create a payment for authorization and capture
         *     later via calls from your server.
         *
         * Also, to include additional payment details and an item list, see getStuffToBuy() below.
         */
        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);

        /*
         * See getStuffToBuy(..) for examples of some available payment options.
         */

        Intent intent = new Intent(this, PaymentActivity.class);

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    private PayPalPayment getThingToBuy(String paymentIntent) {
        return new PayPalPayment(new BigDecimal(mMembershipCost), "MXN", getString(R.string.payment_description),
                paymentIntent);
    }

    @Override
    public void onAddPayment() {
        showLoadingMsg(false);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //intent.putExtra(FIRST_TIME, true);
        CustomerDataPersistence persistence = new CustomerDataPersistence(this);
        persistence.setPayStatus(1);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAddPaymentFail(String error) {
        showLoadingMsg(false);
        Snack.show(findViewById(android.R.id.content), error);
    }

    @Override
    public void onGetAnnualCost(float cost) {
        mMembershipCost = cost;
        String total = "$" + cost + " MXN";
        mAnnualCost.setText(total);
    }

    @Override
    public void onGetAnnualCostFail(String error) {

    }

    @Override
    public void onPaymentAdded(Payment payment) {
        showLoadingMsg(false);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //intent.putExtra(FIRST_TIME, true);
        CustomerDataPersistence persistence = new CustomerDataPersistence(this);
        persistence.setPayStatus(1);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAddPaymentError(String error) {
        showLoadingMsg(false);
        Snack.show(findViewById(android.R.id.content), error);
    }
}
