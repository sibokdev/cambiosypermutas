package app.oficiodigital.cliente.repos;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.os.Bundle;
;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import app.oficiodigital.cliente.R;
import app.oficiodigital.cliente.clients.DOXClient;
import app.oficiodigital.cliente.models.CreditCard;
import app.oficiodigital.cliente.models.Responses;
import app.oficiodigital.cliente.presenters.AddCreditCardPresenter;
import app.oficiodigital.cliente.presenters.AddPaymentPresenter;
import app.oficiodigital.cliente.presenters.DeleteCreditCardPresenter;
import app.oficiodigital.cliente.presenters.FinancialDataPresenter;
import app.oficiodigital.cliente.presenters.GetUrgentCostPresenter;
import app.oficiodigital.cliente.storage.CustomerDataPersistence;
import app.oficiodigital.cliente.storage.daohandlers.CreditCardDao;
import app.oficiodigital.cliente.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Roberasd on 11/01/17.
 */

public class FinancialRepo {

    private Context mContext;
    private AddCreditCardPresenter mAddCreditCardPresenter;
    private FinancialDataPresenter mFinancialDataPresenter;
    private AddPaymentPresenter mAddPaymentPresenter;
    private GetUrgentCostPresenter mGetUrgentCostPresenter;
    private DeleteCreditCardPresenter mDeleteCreditCardPresenter;
    private CreditCardDao mCreditCardDao;
    private String mToken;
    private String token;


    public FinancialRepo(Context context, AddCreditCardPresenter addPaymentPresenter){
        mContext = context;
        mAddCreditCardPresenter = addPaymentPresenter;
        mToken = "Bearer " + new CustomerDataPersistence(context).getToken();

    }

    public FinancialRepo(Context context, FinancialDataPresenter financialDataPresenter){
        mContext = context;
        mCreditCardDao = new CreditCardDao(context);
        mFinancialDataPresenter = financialDataPresenter;
        mToken = "Bearer " + new CustomerDataPersistence(context).getToken();
    }

    public FinancialRepo(Context context, AddPaymentPresenter addPaymentPresenter){
        mContext = context;
        mAddPaymentPresenter = addPaymentPresenter;
        mToken = "Bearer " + new CustomerDataPersistence(context).getToken();

    }

    public FinancialRepo(Context context, GetUrgentCostPresenter getUrgentCostPresenter){
        mContext = context;
        mGetUrgentCostPresenter = getUrgentCostPresenter;
        mToken = "Bearer " + new CustomerDataPersistence(context).getToken();
    }

    public FinancialRepo(Context context, DeleteCreditCardPresenter deleteCreditCardPresenter){
        mContext = context;
        mDeleteCreditCardPresenter = deleteCreditCardPresenter;
        mToken = "Bearer " + new CustomerDataPersistence(context).getToken();


    }

        public void addCreditCard(CreditCard creditCard) {

        String token1  = "Bearer"+mToken;

            Call<Responses> call = DOXClient.getInstanceClient()
                    .getApiClient().addCard(creditCard, token1);


            call.enqueue(new Callback<Responses>() {
                @Override
                public void onResponse(Call<Responses> call, Response<Responses> response) {
                    if (response.body() != null) {
                        if (response.code() == 200) {
                            mAddCreditCardPresenter.onCreditCardAdded();
                        } else
                            mAddCreditCardPresenter.onCreditCardAddedFail(response.body().getMessage());

                    } else {
                        mAddCreditCardPresenter.onCreditCardAddedFail(mContext.getString(R.string.error_adding_credit_card));

                    }
                }

                @Override
                public void onFailure(Call<Responses> call, Throwable t) {
                    L.error("Add Credit Card " + t.getMessage());
                    mAddCreditCardPresenter.onCreditCardAddedFail(mContext.getString(R.string.error_adding_credit_card));
                }
            });
        }

        public void getCards() {
            Call<Responses> call = DOXClient.getInstanceClient().getApiClient().getCards(mToken);

            call.enqueue(new Callback<Responses>() {
                @Override
                public void onResponse(Call<Responses> call, Response<Responses> response) {
                    if (response.body() != null) {
                        if (response.code() == 200) {
                            mCreditCardDao.deleteAll();
                            mFinancialDataPresenter.onGetCards(response.body().getResponse().getCards());
                            mCreditCardDao.add(response.body().getResponse().getCards());
                        } else
                            mFinancialDataPresenter.onGetCardsFail(response.body().getMessage());
                    } else
                        mFinancialDataPresenter.onGetCardsFail(mContext.getString(R.string.error_getting_cards_list));
                }

                @Override
                public void onFailure(Call<Responses> call, Throwable t) {
                    L.error("Get cards " + t.getMessage());
                    mFinancialDataPresenter.onGetCardsFail(mContext.getString(R.string.error_getting_cards_list));
                }
            });
        }

        @SuppressWarnings("unchecked")
        public void getCardsInCache() {
            mFinancialDataPresenter.onGetCards((ArrayList<CreditCard>) mCreditCardDao.getAll());
        }

        String token2 = "hxuEMv39q48x4QFrNBjZ8lDCibqpRpRW5ySAboZf";

        public void deleteCrediCard(String cardId) {
            Call<Responses> call = DOXClient.getInstanceClient().getApiClient().dropCard(cardId, token2);
            call.enqueue(new Callback<Responses>() {
                @Override
                public void onResponse(Call<Responses> call, Response<Responses> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 202)
                            mDeleteCreditCardPresenter.onDeleteCardError(response.body().getMessage());
                        else
                            mDeleteCreditCardPresenter.onCardDeleted();
                    } else {
                        Gson gson = new Gson();
                        try {
                            Responses responses = gson.fromJson(response.errorBody().string(), Responses.class);
                            mDeleteCreditCardPresenter.onDeleteCardError(responses.getMessage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Responses> call, Throwable t) {
                    mDeleteCreditCardPresenter.onDeleteCardError(t.getMessage());
                }
            });
        }

        public void mainCard(String cardId) {
            Call<Responses> call = DOXClient.getInstanceClient().getApiClient().mainCard(cardId, mToken);
            call.enqueue(new Callback<Responses>() {
                @Override
                public void onResponse(Call<Responses> call, Response<Responses> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 202)
                            mDeleteCreditCardPresenter.onCardMadeMain();
                        else
                            mDeleteCreditCardPresenter.onMainCardError(response.body().getMessage());

                    } else {
                        Gson gson = new Gson();
                        try {
                            Responses responses = gson.fromJson(response.errorBody().string(), Responses.class);
                            mDeleteCreditCardPresenter.onMainCardError(responses.getMessage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Responses> call, Throwable t) {
                    mDeleteCreditCardPresenter.onMainCardError(t.getMessage());
                }
            });
        }

        public void getAnnualCost() {
            Call<Responses> call = DOXClient.getInstanceClient().getApiClient()
                    .getAnnualCost(mToken);

            call.enqueue(new Callback<Responses>() {
                @Override
                public void onResponse(Call<Responses> call, Response<Responses> response) {
                    if (response.body() != null) {
                        if (response.code() == 200)
                            mAddPaymentPresenter.onGetAnnualCost(response.body().getResponse().getCost());
                        else
                            mAddPaymentPresenter.onGetAnnualCostFail(response.body().getMessage());
                    } else
                        mAddPaymentPresenter.onGetAnnualCostFail(mContext.getString(R.string.error_getting_annual_cost));
                }

                @Override
                public void onFailure(Call<Responses> call, Throwable t) {
                    mAddPaymentPresenter.onGetAnnualCostFail(mContext.getString(R.string.error_getting_annual_cost));
                }
            });
        }

        public void getUrgentCost() {
            Call<Responses> call = DOXClient.getInstanceClient().getApiClient()
                    .getUrgentCost(mToken);

            call.enqueue(new Callback<Responses>() {
                @Override
                public void onResponse(Call<Responses> call, Response<Responses> response) {
                    if (response.body() != null) {
                        if (response.code() == 200)
                            mGetUrgentCostPresenter.onGetUrgenCost(response.body().getResponse().getCost());
                        else
                            mGetUrgentCostPresenter.onGetUrgentCostFail(response.body().getMessage());
                    } else
                        mGetUrgentCostPresenter.onGetUrgentCostFail(mContext.getString(R.string.error_getting_annual_cost));
                }

                @Override
                public void onFailure(Call<Responses> call, Throwable t) {
                    mGetUrgentCostPresenter.onGetUrgentCostFail(mContext.getString(R.string.error_getting_annual_cost));
                }
            });
        }

        public void addPayment(String deviceSessionId) {
            Call<Responses> call = DOXClient.getInstanceClient().getApiClient()
                    .addPayment(deviceSessionId, mToken);

            call.enqueue(new Callback<Responses>() {
                @Override
                public void onResponse(Call<Responses> call, Response<Responses> response) {
                    if (response.body() != null) {
                        if (response.code() == 200) {
                            mAddPaymentPresenter.onAddPayment();
                        } else {
                            mAddPaymentPresenter.onAddPaymentFail(response.body().getMessage());
                        }
                    } else
                        mAddPaymentPresenter.onAddPaymentFail(mContext.getString(R.string.error_doing_payment));
                }

                @Override
                public void onFailure(Call<Responses> call, Throwable t) {
                    L.error("Add payment " + t.getMessage());
                    mAddPaymentPresenter.onAddPaymentFail(mContext.getString(R.string.error_doing_payment));
                }
            });
        }

}
