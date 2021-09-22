package app.oficiodigital.cliente.repos;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.clients.DOXClient;
import app.oficiodigital.cliente.models.Paypal;
import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.presenters.AddPaypalPaymentPresenter;
import app.oficiodigital.cliente.storage.CustomerDataPersistence;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by roberasd on 05/04/17.
 */

public class PaymentRepo {

    private AddPaypalPaymentPresenter mAddPaypalPaymentPresenter;
    private String mToken;
    private Context mContext;

    public PaymentRepo(Context context, AddPaypalPaymentPresenter paypalPaymentPresenter) {
        mContext = context;
        mAddPaypalPaymentPresenter = paypalPaymentPresenter;
        mToken = "Bearer " + new CustomerDataPersistence(context).getToken();
    }

    public void addPaypalPayment(Paypal paypal) {
        Call<Responses> call = DOXClient.getInstanceClient()
                .getApiClient()
                .addPaypalPayment(paypal, mToken);

        call.enqueue(new Callback<Responses>() {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResponse() != null) {
                        mAddPaypalPaymentPresenter.onPaymentAdded(response.body().getResponse().getPayment());
                    } else {
                        Gson gson = new Gson();
                        try {
                            Responses responses = gson.fromJson(response.errorBody().string(), Responses.class);
                            mAddPaypalPaymentPresenter.onAddPaymentError(responses.getMessage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else
                    mAddPaypalPaymentPresenter.onAddPaymentError(mContext.getString(R.string.error_add_paypal_payment));
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                mAddPaypalPaymentPresenter.onAddPaymentError(mContext.getString(R.string.error_add_paypal_payment));
            }
        });
    }
}
